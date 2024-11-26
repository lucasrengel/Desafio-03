package com.lucas.pedido.model;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Embeddable
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Produto {

    private String nome;
    private Long produtoId;
    private Integer quantidade;
    private Double preco;

    public Produto(String nome, Integer quantidade, Double preco) {
    }
}