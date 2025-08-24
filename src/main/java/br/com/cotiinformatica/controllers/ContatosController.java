package br.com.cotiinformatica.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.cotiinformatica.entities.Contato;
import br.com.cotiinformatica.repositories.ContatoRepository;

@RestController
@RequestMapping("api/v1/contato")
public class ContatosController {

    private ContatoRepository contatoRepository = new ContatoRepository();

    // Listar todos contatos
    @GetMapping
    public ResponseEntity<List<Contato>> getAll() {
        try {
            List<Contato> contatos = contatoRepository.findAll();
            return ResponseEntity.ok(contatos);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    // Buscar contato por id
    @GetMapping("/{idContato}")
    public ResponseEntity<Contato> getById(@PathVariable UUID idContato) {
        try {
            Contato contato = contatoRepository.findById(idContato);
            if (contato != null) {
                return ResponseEntity.ok(contato);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    // Cadastrar novo contato
    @PostMapping
    public ResponseEntity<Contato> post(@RequestBody Contato novoContato) {
        try {
            contatoRepository.insert(novoContato);
            return ResponseEntity.ok(novoContato);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    // Atualizar contato
    @PutMapping
    public ResponseEntity<Void> put(@RequestBody Contato contatoAtualizado) {
        try {
            boolean atualizado = contatoRepository.update(contatoAtualizado);
            if (atualizado) {
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    // Deletar contato
    @DeleteMapping("/{idContato}")
    public ResponseEntity<Void> delete(@PathVariable UUID idContato) {
        try {
            boolean deletado = contatoRepository.delete(idContato);
            if (deletado) {
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}

