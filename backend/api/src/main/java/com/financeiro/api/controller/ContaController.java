package com.financeiro.api.controller;

import com.financeiro.api.model.Conta;
import com.financeiro.api.repository.ContaRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contas")
@CrossOrigin(origins = "*") // Permite que o seu HTML acesse a API sem dar erro de CORS
public class ContaController {

    @Autowired
    private ContaRepository contaRepository;

    // Rota para listar todas as contas fixas
    @GetMapping
    public List<Conta> listarTodas() {
        return contaRepository.findAll();
    }

    // === NOVA ROTA ADICIONADA: Busca as contas fixas filtradas pelo ID do usuário ===
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Conta>> listarPorUsuario(@PathVariable String usuarioId) {
        List<Conta> contas = contaRepository.findByUsuarioId(usuarioId);
        return ResponseEntity.ok(contas);
    }

    // Rota para salvar uma nova conta fixa
    @PostMapping
    public ResponseEntity<Conta> criar(@Valid @RequestBody Conta conta) {
        Conta novaConta = contaRepository.save(conta);
        return ResponseEntity.ok(novaConta);
    }

    // Rota para deletar uma conta pelo ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (!contaRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        contaRepository.deleteById(id);
        return ResponseEntity.noContent().build(); // Retorna o status 204 (Sucesso, sem conteúdo)
    }
}