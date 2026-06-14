package com.financeiro.api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "tb_gastos_variaveis")
public class GastoVariavel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "A categoria/lista é obrigatória")
    private String categoria;

    @NotNull(message = "O valor é obrigatório")
    private BigDecimal valor;

    @NotNull(message = "A data do gasto é obrigatória")
    @Column(name = "data_gasto")
    private LocalDate dataGasto;

    // === NOVO ATRIBUTO ADICIONADO ===
    @Column(name = "usuario_id")
    private String usuarioId;

    // Construtores
    public GastoVariavel() {}

    // Construtor cheio atualizado para incluir o usuarioId
    public GastoVariavel(String categoria, BigDecimal valor, LocalDate dataGasto, String usuarioId) {
        this.categoria = categoria;
        this.valor = valor;
        this.dataGasto = dataGasto;
        this.usuarioId = usuarioId;
    }

    // Getters e Setters antigos
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public BigDecimal getValor() { return valor; }
    public void setValor(BigDecimal valor) { this.valor = valor; }

    public LocalDate getDataGasto() { return dataGasto; }
    public void setDataGasto(LocalDate dataGasto) { this.dataGasto = dataGasto; }

    // === NOVOS GETTER E SETTER ADICIONADOS ===
    public String getUsuarioId() { return usuarioId; }
    public void setUsuarioId(String usuarioId) { this.usuarioId = usuarioId; }
}
