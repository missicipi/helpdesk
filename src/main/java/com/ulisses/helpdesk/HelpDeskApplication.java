package com.ulisses.helpdesk;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ulisses.helpdesk.domain.Chamado;
import com.ulisses.helpdesk.domain.Cliente;
import com.ulisses.helpdesk.domain.Tecnico;
import com.ulisses.helpdesk.domain.enums.Perfil;
import com.ulisses.helpdesk.domain.enums.Prioridade;
import com.ulisses.helpdesk.domain.enums.Status;
import com.ulisses.helpdesk.repository.ChamadoRepository;
import com.ulisses.helpdesk.repository.ClienteRepository;
import com.ulisses.helpdesk.repository.TecnicoRepository;

//@RestController
@SpringBootApplication
public class HelpDeskApplication /*implements CommandLineRunner*/ {
/*
	@Autowired
	private TecnicoRepository tecnicoRepository;
	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private ChamadoRepository chamadoRepository;
	
	*/
	public static void main(String[] args) {
		SpringApplication.run(HelpDeskApplication.class, args);
	}

/*	@GetMapping
	public String hell() {
		return "Olá Mundo!, chuchu";
	}

	@Override
	public void run(String... args) throws Exception {
	Tecnico t1 = new Tecnico(null, "Ulisses", "07172580095","uli@email.com","123");
	t1.addPerfil(Perfil.ADMIN);
		
	Cliente cli1= new Cliente(null, "Linus Torvalds", "11269657070", "torvalds@mail.com", "123");
	
	Chamado c1 = new Chamado(null, Prioridade.MEDIA, Status.ANDAMENTO, "Chamado1", "First Chamado", t1, cli1);
	
	tecnicoRepository.saveAll(Arrays.asList(t1));
	clienteRepository.saveAll(Arrays.asList(cli1));
	chamadoRepository.saveAll(Arrays.asList(c1));
	
	
	}
	*/
}
