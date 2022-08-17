package com.ulisses.helpdesk.services;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ulisses.helpdesk.domain.Chamado;
import com.ulisses.helpdesk.domain.Cliente;
import com.ulisses.helpdesk.domain.Tecnico;
import com.ulisses.helpdesk.domain.enums.Perfil;
import com.ulisses.helpdesk.domain.enums.Prioridade;
import com.ulisses.helpdesk.domain.enums.Status;
import com.ulisses.helpdesk.repository.ChamadoRepository;
import com.ulisses.helpdesk.repository.ClienteRepository;
import com.ulisses.helpdesk.repository.TecnicoRepository;

@Service
public class DBService {
	
	@Autowired
	private TecnicoRepository tecnicoRepository;
	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private ChamadoRepository chamadoRepository;
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	
	
	public void instanciaDB(){
	
		
		Tecnico t1 = new Tecnico(null, "Ulisses", "08908935002","uli@email.com", encoder.encode("123"));
		t1.addPerfil(Perfil.ADMIN);
		Tecnico t2 = new Tecnico(null, "Chupisco", "63765472069","chup@email.com", encoder.encode("123"));
		t2.addPerfil(Perfil.ADMIN);
		Tecnico t3 = new Tecnico(null, "Omarmelado", "97570914058","omar@email.com", encoder.encode("123"));
		t3.addPerfil(Perfil.ADMIN);
		Tecnico t4 = new Tecnico(null, "CHunLi", "62504631006","Chunl@email.com", encoder.encode("123"));
		t4.addPerfil(Perfil.ADMIN);
		
		Cliente cli1= new Cliente(null, "Linus Torvalds", "17916312077", "torvalds@mail.com", encoder.encode("123"));
		Cliente cli2= new Cliente(null, "XuxuBeleza", "82203363070", "xuxu@mail.com", encoder.encode("123"));
		
		Chamado c1 = new Chamado(null, Prioridade.MEDIA, Status.ANDAMENTO, "Chamado1", "First Chamado", t1, cli1);
		Chamado c2 = new Chamado(null, Prioridade.MEDIA, Status.ANDAMENTO, "Chamado2", "Second Chamado", t3, cli1);
		
		tecnicoRepository.saveAll(Arrays.asList(t1,t2,t3,t4));
		clienteRepository.saveAll(Arrays.asList(cli1,cli2));
		chamadoRepository.saveAll(Arrays.asList(c1,c2));
		
	}
}
