package com.ulisses.helpdesk.services;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ulisses.helpdesk.domain.Pessoa;
import com.ulisses.helpdesk.domain.Tecnico;
import com.ulisses.helpdesk.domain.Dto.TecnicoDto;
import com.ulisses.helpdesk.repository.PessoaRepository;
import com.ulisses.helpdesk.repository.TecnicoRepository;
import com.ulisses.helpdesk.services.exceptions.DataIntegrityViolationException;
import com.ulisses.helpdesk.services.exceptions.ObjectnotFoundException;


@Service
public class TecnicoService {

	@Autowired
	private TecnicoRepository repository;
	@Autowired
	private PessoaRepository pessoaRepository;
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	
	public Tecnico findById(Integer id) {
		Optional<Tecnico> obj = repository.findById(id);
		
	//	return obj.orElse(null); melhorando isso abaixo
		return obj.orElseThrow(() -> new ObjectnotFoundException("Objeto não encontrado! Id:  " + id));
		
	}
	
	public List<Tecnico> findAll(){
		return repository.findAll();
	}

	public Tecnico create(TecnicoDto objDTO) {

		objDTO.setId(null);
		objDTO.setSenha(encoder.encode(objDTO.getSenha()));
		validaCpfporEemail(objDTO);
		Tecnico newObj = new Tecnico(objDTO);
		
		return repository.save(newObj);
	}
	
	public Tecnico update(Integer id, @Valid TecnicoDto objDTO) {
		objDTO.setId(id);
		Tecnico oldObj = findById(id);
		validaCpfporEemail(objDTO);
		
		oldObj = new Tecnico(objDTO);
		return repository.save(oldObj);
	
	}
	
	public void  delete(Integer id) {
		
		Tecnico obj = findById(id);
		if(obj.getChamado().size() > 0) {
			throw new DataIntegrityViolationException("Esse técnico tem ordem de serviço e não pode ser deletado");
		}
		repository.deleteById(id);
		
	}

	
	private void validaCpfporEemail(TecnicoDto objDTO) {
		Optional<Pessoa> obj = pessoaRepository.findByCpf(objDTO.getCpf());
		if(obj.isPresent() && obj.get().getId() != objDTO.getId()) {
			throw new DataIntegrityViolationException("CPF já cadastrado no sistema!");
		}
		
		obj = pessoaRepository.findByEmail(objDTO.getEmail());
		if(obj.isPresent() && obj.get().getId() != objDTO.getId()) {
			throw new DataIntegrityViolationException("E-mail já cadastrado no sistema!");
		}
		
		
	}

	


	
}
