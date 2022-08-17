package com.ulisses.helpdesk.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ulisses.helpdesk.domain.Chamado;
import com.ulisses.helpdesk.domain.Cliente;
import com.ulisses.helpdesk.domain.Dto.ChamadoDto;
import com.ulisses.helpdesk.domain.Dto.ClienteDto;
import com.ulisses.helpdesk.services.ChamadoService;

@RestController
@RequestMapping(value = "/chamados")
public class ChamadoResource {
	
	@Autowired
	private ChamadoService service;
	
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<ChamadoDto> findById(@PathVariable Integer id){
		Chamado obj = service.findById(id);
		return ResponseEntity.ok().body(new ChamadoDto(obj));
		
	}

	
/** Meu metodo
 * 	@GetMapping
	public ResponseEntity<List<Chamado>> findByAll(){
		List<Chamado> obj = service.findByAll();
		return ResponseEntity.ok().body(obj);
	}
	dessa forma ve a entidade o legal é ver apenas o DTO

 */ 	
	//Dp curso
	@GetMapping
	public ResponseEntity<List<ChamadoDto>> findAll() {
		List<Chamado> list = service.findByAll();
		List<ChamadoDto> listDTO = list.stream().map(obj -> new ChamadoDto(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDTO);
		
	}
	
	
	@PostMapping
	public ResponseEntity<ChamadoDto> create(@Valid @RequestBody ChamadoDto objDTO){
		Chamado obj = service.create(objDTO);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().
				path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
		
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<ChamadoDto> update(@PathVariable Integer id,@Valid @RequestBody ChamadoDto objDTO){
		Chamado obj = service.update(id, objDTO);
		return ResponseEntity.ok().body(new ChamadoDto(obj));
		
	}

	//@DeleteMapping(value = "/{id}")
	//public ResponseEntity<ClienteDto> delete(@PathVariable Integer id){
	//	service.delete(id);
	//	return ResponseEntity.noContent().build();
		
	//}
}
