package com.devsuperior.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.demo.dto.EmployeeDTO;
import com.devsuperior.demo.entities.Department;
import com.devsuperior.demo.entities.Employee;
import com.devsuperior.demo.repositories.EmployeeRepository;

/**
 * Serviço responsável pelas operações relacionadas aos funcionários.
 */
@Service
public class EmployeeService {

	@Autowired
	private EmployeeRepository repository;
	
	/**
	 * Busca os funcionários de forma paginada.
	 *
	 * * A consulta é executada em modo somente leitura.
	 *
	 * @param pageable informações de paginação e ordenação.
	 * @return página de funcionários convertidos para EmployeeDTO.
	 */
	@Transactional(readOnly = true)
	public Page<EmployeeDTO> findAll(Pageable pageable) {
		Page<Employee> page = repository.findAll(pageable);
		return page.map(x -> new EmployeeDTO(x));
	}

	/**
	 * Cadastra um novo funcionário com os dados recebidos.
	 *
	 * * Associa o funcionário ao departamento informado pelo identificador
	 * recebido no DTO e persiste os dados no banco de dados.
	 *
	 * @param dto objeto contendo nome, email e identificador do departamento.
	 * @return DTO do funcionário após a persistência.
	 */
	@Transactional
	public EmployeeDTO insert(EmployeeDTO dto) {
		Employee entity = new Employee();
		entity.setName(dto.getName());
		entity.setEmail(dto.getEmail());
		entity.setDepartment(new Department(dto.getDepartmentId(), null));
		entity = repository.save(entity);
		return new EmployeeDTO(entity);
	}
}
