package com.devsuperior.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.devsuperior.demo.entities.Role;
import com.devsuperior.demo.entities.User;
import com.devsuperior.demo.projections.UserDetailsProjection;
import com.devsuperior.demo.repositories.UserRepository;

/**
 * Serviço responsável por carregar os dados dos usuários e suas permissões
 * durante o processo de autenticação do Spring Security.
 */
@Service
public class UserService implements UserDetailsService {

	@Autowired
	private UserRepository repository;
	

	/**
	 * Busca um usuário pelo email e carrega suas respectivas permissões.
	 *
	 * * Esse método é utilizado pelo Spring Security durante a autenticação.
	 * Caso o email não seja encontrado, uma exceção é lançada.
	 *
	 * @param username email utilizado para identificar o usuário.
	 * @return usuário contendo email, senha e suas autoridades.
	 * @throws UsernameNotFoundException caso não exista usuário com o email informado.
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		List<UserDetailsProjection> result = repository.searchUserAndRolesByEmail(username);
		if (result.size() == 0) {
			throw new UsernameNotFoundException("Email not found");
		}
		
		User user = new User();
		user.setEmail(result.get(0).getUsername());
		user.setPassword(result.get(0).getPassword());
		for (UserDetailsProjection projection : result) {
			user.addRole(new Role(projection.getRoleId(), projection.getAuthority()));
		}
		
		return user;
	}	
}
