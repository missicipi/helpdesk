package com.ulisses.helpdesk.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

import com.ulisses.helpdesk.domain.Cliente;
import com.ulisses.helpdesk.domain.Dto.ClienteDto;
import com.ulisses.helpdesk.services.ClienteService;

@RestController
@RequestMapping(value = "/clientes")
public class ClienteResource {

	@Autowired
	private ClienteService service;
	
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<ClienteDto> findById(@PathVariable Integer id){
		Cliente obj = service.findById(id);
		return ResponseEntity.ok().body(new ClienteDto(obj));
	}
	
	@GetMapping
	public ResponseEntity<List<ClienteDto>> findAll(){
		
		List<Cliente> list = service.findAll();
		List<ClienteDto> listDTO = list.stream().map(
				obj -> new ClienteDto(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDTO);
		}
	
	@PostMapping
	public ResponseEntity<ClienteDto> create(@Valid @RequestBody ClienteDto objDTO){
		Cliente newObj = service.create(objDTO);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newObj.getId()).toUri();
		return ResponseEntity.created(uri).build();
		
	}
	

	@PutMapping(value = "/{id}")
	public ResponseEntity<ClienteDto> update(@PathVariable Integer id,@Valid @RequestBody ClienteDto objDTO){
		Cliente obj = service.update(id, objDTO);
		return ResponseEntity.ok().body(new ClienteDto(obj));
		
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<ClienteDto> delete(@PathVariable Integer id){
		service.delete(id);
		return ResponseEntity.noContent().build();
		
	}
	
}
