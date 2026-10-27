package com.example.demo.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dtos.EmailDTO;
import com.example.demo.services.EmailService;

@RestController
@RequestMapping(value = "/email")
public class EmailController {

    private final EmailService service;

    public EmailController(EmailService service) {
        this.service = service;
    }

    // email de texto: POST /email com {para, assunto, corpo}
    @PostMapping
    public ResponseEntity<String> enviar(@RequestBody EmailDTO dto) {
        if (dto.para() == null || dto.para().isBlank()) {
            return ResponseEntity.badRequest().body("Campo 'para' é obrigatório");
        }
        try {
            service.enviarEmailTexto(dto.para(), dto.assunto(), dto.corpo());
            return ResponseEntity.status(HttpStatus.OK).body("Email enviado");
        } catch (MailException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao enviar: " + e.getMessage());
        }
    }

    // email com template (Thymeleaf): GET /email/template?para=...
    @GetMapping("/template")
    public ResponseEntity<String> enviarComTemplate(@RequestParam("para") String para) {
        try {
            service.enviarEmailComTemplate(
                para,
                "Bem-vindo!",
                "email/template",
                Map.of(
                    "nome", "Maria",
                    "itens", List.of("Plano Pro", "Suporte 24h"),
                    "link", "https://meusite.com"
                ));
            return ResponseEntity.ok("Enviado");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao enviar: " + e.getMessage());
        }
    }
}
