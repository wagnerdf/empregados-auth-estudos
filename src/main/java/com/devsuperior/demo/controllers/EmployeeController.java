package com.devsuperior.demo.controllers;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.devsuperior.demo.dto.EmployeeDTO;
import com.devsuperior.demo.services.EmployeeService;

import jakarta.validation.Valid;

/**
 * Controller responsável por disponibilizar os endpoints relacionados aos funcionários.
 */
@RestController
@RequestMapping(value = "/employees")
public class EmployeeController {

	@Autowired
	private EmployeeService service;
	
	/**
	 * Retorna uma página de funcionários, ordenada pelo nome em ordem crescente.
	 *
	 * * Endpoint: GET /employees
	 *
	 * * Permissão: usuários com perfil ADMIN ou OPERATOR.
	 *
	 * @param pageable informações de paginação, como número da página e quantidade de registros.
	 * @return ResponseEntity contendo a página de funcionários e o status HTTP 200 (OK).
	 */
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_OPERATOR')")
	@GetMapping
	public ResponseEntity<Page<EmployeeDTO>> findAll(Pageable pageable) {
		PageRequest pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("name"));
		Page<EmployeeDTO> list = service.findAll(pageRequest);		
		return ResponseEntity.ok().body(list);
	}
	
	/**
	 * Cadastra um novo funcionário após validar os dados recebidos na requisição.
	 *
	 * * Endpoint: POST /employees
	 *
	 * * Permissão: somente usuários com perfil ADMIN.
	 *
	 * @param dto objeto contendo os dados do funcionário a ser cadastrado.
	 * @return ResponseEntity contendo os dados do funcionário criado, a URI do novo recurso
	 *         no cabeçalho Location e o status HTTP 201 (Created).
	 */
	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@PostMapping
	public ResponseEntity<EmployeeDTO> insert(@Valid @RequestBody EmployeeDTO dto) {
		dto = service.insert(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(dto.getId()).toUri();
		return ResponseEntity.created(uri).body(dto);
	}
}
