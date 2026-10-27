package com.example.demo.controllers;

import java.net.URI;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.demo.dtos.SaberMaisDTO;
import com.example.demo.models.ImovelModel;
import com.example.demo.services.EmailService;
import com.example.demo.services.ImovelService;

@RestController
@RequestMapping(value = "/imoveis")
public class ImovelController {

    private final ImovelService service;
    private final EmailService emailService;

    public ImovelController(ImovelService service, EmailService emailService) {
        this.service = service;
        this.emailService = emailService;
    }

    @GetMapping
    public ResponseEntity<List<ImovelModel>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ImovelModel> find(@PathVariable Integer id) {
        ImovelModel model = service.find(id);
        if (model == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(model);
    }

    @PostMapping
    public ResponseEntity<Void> insert(@RequestBody ImovelModel model) {
        model = service.insert(model);
        URI uri = ServletUriComponentsBuilder
            .fromCurrentRequest().path("/{id}")
            .buildAndExpand(model.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Void> update(@RequestBody ImovelModel model, @PathVariable Integer id) {
        model.setId(id);
        if (service.update(model) == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

    // qualquer usuário autenticado pode alterar: body {"status": "VENDIDO"}
    @PatchMapping(value = "/{id}/status")
    public ResponseEntity<Void> updateStatus(@PathVariable Integer id, @RequestBody Map<String, String> body) {
        if (service.updateStatus(id, body.get("status")) == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    // cliente quer saber mais sobre o imóvel -> envia email com template
    @PostMapping(value = "/sabermais/{id}")
    public ResponseEntity<String> saberMais(@PathVariable Integer id, @RequestBody SaberMaisDTO dto) {
        ImovelModel imovel = service.find(id);
        if (imovel == null) {
            return ResponseEntity.notFound().build();
        }

        try {
            emailService.enviarEmailComTemplate(
                dto.getEmail(),
                "Informações sobre o imóvel",
                "email/sabermais",
                Map.of(
                    "cliente", dto,
                    "imovel", imovel,
                    "mensagem", "Obrigado pelo interesse! Em breve um corretor vai entrar em contato."
                ));

            return ResponseEntity.ok("Enviado");
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
