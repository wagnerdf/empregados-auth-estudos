package com.devsuperior.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.devsuperior.demo.dto.DepartmentDTO;
import com.devsuperior.demo.entities.Department;
import com.devsuperior.demo.repositories.DepartmentRepository;

/**
 * Serviço responsável pelas operações relacionadas aos departamentos.
 */
@Service
public class DepartmentService {

	@Autowired
	private DepartmentRepository repository;
	
	/**
	 * Busca todos os departamentos cadastrados, ordenando-os pelo nome.
	 *
	 * @return lista de departamentos convertidos para DepartmentDTO.
	 */
	public List<DepartmentDTO> findAll() {
		List<Department> list = repository.findAll(Sort.by("name"));
		return list.stream().map(x -> new DepartmentDTO(x)).toList();
	}
}
