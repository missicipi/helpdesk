package com.ulisses.helpdesk.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ulisses.helpdesk.domain.Chamado;
import com.ulisses.helpdesk.domain.Cliente;
import com.ulisses.helpdesk.domain.Chamado;
import com.ulisses.helpdesk.domain.Tecnico;
import com.ulisses.helpdesk.domain.Dto.ChamadoDto;
import com.ulisses.helpdesk.domain.Dto.ChamadoDto;
import com.ulisses.helpdesk.domain.enums.Prioridade;
import com.ulisses.helpdesk.domain.enums.Status;
import com.ulisses.helpdesk.repository.ChamadoRepository;
import com.ulisses.helpdesk.services.exceptions.DataIntegrityViolationException;
import com.ulisses.helpdesk.services.exceptions.ObjectnotFoundException;

@Service
public class ChamadoService {

	@Autowired
	private ChamadoRepository repository;
	@Autowired
	private TecnicoService tecnicoService;
	@Autowired
	private ClienteService clienteService;
	
	
	public Chamado findById(Integer id) {
		
		Optional<Chamado> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ObjectnotFoundException("Objeto não encontrado! ID: "+id));
	}
	
	
	public List<Chamado> findByAll() {
		List<Chamado> obj = repository.findAll();
		return obj;
	}


	public Chamado create(@Valid ChamadoDto objDTO) {
		
		return repository.save(newChamado(objDTO));
		
	}
	
	private Chamado newChamado(ChamadoDto obj) {
		Tecnico tecnico = tecnicoService.findById(obj.getTecnico());
		Cliente cliente = clienteService.findById(obj.getCliente());
		Chamado chamado = new Chamado();
		
		if(obj.getId() != null) {
			chamado.setId(obj.getId());
		}
		
		if(obj.getStatus().equals(2)) {
			chamado.setDataFechamento(LocalDate.now());
		}
		
		chamado.setTecnico(tecnico);
		chamado.setCliente(cliente);
		chamado.setPrioridade(Prioridade.toEnum(obj.getPrioridade()));
		chamado.setStatus(Status.toEnum(obj.getStatus()));
		chamado.setTitulo(obj.getTitulo());
		chamado.setObservacoes(obj.getObservacoes());
		return chamado;
	}
	
	
	public Chamado update(Integer id, @Valid ChamadoDto objDTO) {
		objDTO.setId(id);
		Chamado oldObj = findById(id);
	//	validaCpfporEemail(objDTO);
		
		oldObj = newChamado(objDTO);
		return repository.save(oldObj);
	
	}
	
	
	
	
	
	
	
	
	
	
	
}
