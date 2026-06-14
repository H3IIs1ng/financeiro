package com.financeiro.api.controller;

import com.financeiro.api.model.GastoVariavel;
import com.financeiro.api.repository.GastoVariavelRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/variaveis")
@CrossOrigin(origins = "*")
public class GastoVariavelController {

    @Autowired
    private GastoVariavelRepository repository;

    @GetMapping
    public List<GastoVariavel> listarTodos() {
        return repository.findAll();
    }

    // === NOVA ROTA ADICIONADA: Busca os gastos variáveis filtrados pelo ID do usuário ===
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<GastoVariavel>> listarPorUsuario(@PathVariable String usuarioId) {
        List<GastoVariavel> gastos = repository.findByUsuarioId(usuarioId);
        return ResponseEntity.ok(gastos);
    }

    @PostMapping
    public ResponseEntity<GastoVariavel> criar(@Valid @RequestBody GastoVariavel gasto) {
        GastoVariavel novoGasto = repository.save(gasto);
        return ResponseEntity.ok(novoGasto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (!repository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}