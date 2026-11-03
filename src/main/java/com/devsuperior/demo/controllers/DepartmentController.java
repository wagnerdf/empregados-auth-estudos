package com.devsuperior.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devsuperior.demo.dto.DepartmentDTO;
import com.devsuperior.demo.services.DepartmentService;

/**
 * Controller responsável por disponibilizar os endpoints relacionados aos departamentos.
 */
@RestController
@RequestMapping(value = "/departments")
public class DepartmentController {

	@Autowired
	private DepartmentService service;
	
	/**
	 * Retorna a lista de todos os departamentos cadastrados.
	 *
	 * * Endpoint: GET /departments
	 *
	 * * Permissão: usuários com perfil ADMIN ou OPERATOR.
	 *
	 * @return ResponseEntity contendo a lista de departamentos e o status HTTP 200 (OK).
	 */
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_OPERATOR')")
	@GetMapping
	public ResponseEntity<List<DepartmentDTO>> findAll() {
		List<DepartmentDTO> list = service.findAll();		
		return ResponseEntity.ok().body(list);
	}
}
