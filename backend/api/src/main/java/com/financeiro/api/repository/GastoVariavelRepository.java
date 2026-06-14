package com.financeiro.api.repository;

import com.financeiro.api.model.GastoVariavel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List; // ➔ Importação necessária

@Repository
public interface GastoVariavelRepository extends JpaRepository<GastoVariavel, Long> {
    
    // Método que filtra os gastos variáveis do usuário atual
    List<GastoVariavel> findByUsuarioId(String usuarioId);
}
