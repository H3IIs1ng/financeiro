package com.financeiro.api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "tb_compras_cartao")
public class CompraCartao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O estabelecimento/descrição é obrigatório")
    private String nome;

    @NotNull(message = "O valor é obrigatório")
    @Column(name = "valor_parcela")
    private BigDecimal valorParcela;

    @NotNull(message = "A quantidade de parcelas é obrigatória")
    @Column(name = "total_parcelas")
    private Integer totalParcelas;

    @NotNull(message = "A data da compra é obrigatória")
    @Column(name = "data_compra")
    private LocalDate dataCompra;

    // === NOVO ATRIBUTO ADICIONADO ===
    @Column(name = "usuario_id")
    private String usuarioId;

    // Construtores
    public CompraCartao() {}

    // Construtor cheio atualizado para incluir o usuarioId
    public CompraCartao(String nome, BigDecimal valorParcela, Integer totalParcelas, LocalDate dataCompra, String usuarioId) {
        this.nome = nome;
        this.valorParcela = valorParcela;
        this.totalParcelas = totalParcelas;
        this.dataCompra = dataCompra;
        this.usuarioId = usuarioId;
    }

    // Getters e Setters antigos
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    
    public BigDecimal getValorParcela() { return valorParcela; }
    public void setValorParcela(BigDecimal valorParcela) { this.valorParcela = valorParcela; }
    
    public Integer getTotalParcelas() { return totalParcelas; }
    public void setTotalParcelas(Integer totalParcelas) { this.totalParcelas = totalParcelas; }
    
    public LocalDate getDataCompra() { return dataCompra; }
    public void setDataCompra(LocalDate dataCompra) { this.dataCompra = dataCompra; }

    // === NOVOS GETTER E SETTER ADICIONADOS ===
    public String getUsuarioId() { return usuarioId; }
    public void setUsuarioId(String usuarioId) { this.usuarioId = usuarioId; }
}
