package com.financeiro.api.controller;

import com.financeiro.api.model.CompraCartao;
import com.financeiro.api.repository.CompraCartaoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cartao")
@CrossOrigin(origins = "*")
public class CompraCartaoController {

    @Autowired
    private CompraCartaoRepository repository;

    @GetMapping
    public List<CompraCartao> listarTodas() {
        return repository.findAll();
    }

    // === NOVA ROTA ADICIONADA: Busca as compras filtradas pelo ID do navegador ===
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<CompraCartao>> listarPorUsuario(@PathVariable String usuarioId) {
        List<CompraCartao> compras = repository.findByUsuarioId(usuarioId);
        return ResponseEntity.ok(compras);
    }

    @PostMapping
    public ResponseEntity<CompraCartao> criar(@Valid @RequestBody CompraCartao compra) {
        CompraCartao novaCompra = repository.save(compra);
        return ResponseEntity.ok(novaCompra);
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