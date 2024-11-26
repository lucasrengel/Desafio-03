package com.lucas.pedido.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor

public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long clienteId;

    @ElementCollection
    private List<Produto> produtos;

    @Column(nullable = false)
    private Double total;

    public Pedido(Long clienteId, List<Produto> produtos) {
        this.clienteId = clienteId;
        this.produtos = produtos;
    }
}
