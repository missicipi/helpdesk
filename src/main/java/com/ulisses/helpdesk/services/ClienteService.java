package com.ulisses.helpdesk.services;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ulisses.helpdesk.domain.Pessoa;
import com.ulisses.helpdesk.domain.Cliente;
import com.ulisses.helpdesk.domain.Dto.ClienteDto;
import com.ulisses.helpdesk.repository.PessoaRepository;
import com.ulisses.helpdesk.repository.ClienteRepository;
import com.ulisses.helpdesk.services.exceptions.DataIntegrityViolationException;
import com.ulisses.helpdesk.services.exceptions.ObjectnotFoundException;


@Service
public class ClienteService {

	@Autowired
	private ClienteRepository repository;
	@Autowired
	private PessoaRepository pessoaRepository;
	@Autowired
	private BCryptPasswordEncoder encoder;

	
	
	public Cliente findById(Integer id) {
		Optional<Cliente> obj = repository.findById(id);
		
	//	return obj.orElse(null); melhorando isso abaixo
		return obj.orElseThrow(() -> new ObjectnotFoundException("Objeto não encontrado! Id:  " + id));
		
	}
	
	public List<Cliente> findAll(){
		return repository.findAll();
	}

	public Cliente create(ClienteDto objDTO) {

		objDTO.setId(null);
		objDTO.setSenha(encoder.encode(objDTO.getSenha()));
		validaCpfporEemail(objDTO);
		Cliente newObj = new Cliente(objDTO);
		
		return repository.save(newObj);
	}
	
	public Cliente update(Integer id, @Valid ClienteDto objDTO) {
		objDTO.setId(id);
		Cliente oldObj = findById(id);
		validaCpfporEemail(objDTO);
		
		oldObj = new Cliente(objDTO);
		return repository.save(oldObj);
	
	}
	
	public void  delete(Integer id) {
		
		Cliente obj = findById(id);
		if(obj.getChamado().size() > 0) {
			throw new DataIntegrityViolationException("Esse cliente tem ordem de serviço e não pode ser deletado");
		}
		repository.deleteById(id);
		
	}

	
	private void validaCpfporEemail(ClienteDto objDTO) {
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
