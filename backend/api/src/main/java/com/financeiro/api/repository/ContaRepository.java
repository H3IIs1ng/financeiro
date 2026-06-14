package com.financeiro.api.repository;

import com.financeiro.api.model.Conta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List; // ➔ Importação necessária

@Repository
public interface ContaRepository extends JpaRepository<Conta, Long> {
    
    // Método que filtra as contas associadas ao ID do navegador do usuário
    List<Conta> findByUsuarioId(String usuarioId);
}