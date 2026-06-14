package com.financeiro.api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "tb_contas")
public class Conta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome da conta é obrigatório")
    private String nome;

    @NotNull(message = "O valor é obrigatório")
    private BigDecimal valor;

    @NotNull(message = "A data de vencimento é obrigatório")
    @Column(name = "data_vencimento")
    private LocalDate dataVencimento;

    // === NOVO ATRIBUTO ADICIONADO ===
    @Column(name = "usuario_id")
    private String usuarioId;

    // Construtores
    public Conta() {}

    // Construtor cheio atualizado para incluir o usuarioId se precisar criar direto pelo Java
    public Conta(String nome, BigDecimal valor, LocalDate dataVencimento, String usuarioId) {
        this.nome = nome;
        this.valor = valor;
        this.dataVencimento = dataVencimento;
        this.usuarioId = usuarioId;
    }

    // Getters e Setters antigos
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    
    public BigDecimal getValor() { return valor; }
    public void setValor(BigDecimal valor) { this.valor = valor; }
    
    public LocalDate getDataVencimento() { return dataVencimento; }
    public void setDataVencimento(LocalDate dataVencimento) { this.dataVencimento = dataVencimento; }

    // === NOVOS GETTER E SETTER ADICIONADOS ===
    public String getUsuarioId() { return usuarioId; }
    public void setUsuarioId(String usuarioId) { this.usuarioId = usuarioId; }
}