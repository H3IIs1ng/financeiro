package com.financeiro.api.repository;

import com.financeiro.api.model.CompraCartao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List; // ➔ Importação necessária para a lista

@Repository
public interface CompraCartaoRepository extends JpaRepository<CompraCartao, Long> {
    
    // Método que faz o Spring gerar o SQL de filtro por usuário automaticamente
    List<CompraCartao> findByUsuarioId(String usuarioId);
}