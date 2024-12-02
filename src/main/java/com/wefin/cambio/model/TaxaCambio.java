package com.wefin.cambio.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "taxa_cambio")
public class TaxaCambio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "moeda_id", nullable = false)
    private Moeda moeda;

    @Column(name = "taxa", nullable = false)
    private BigDecimal taxa; // Fator de conversão com relação ao Ouro Real

    @Column(name = "data_hora", nullable = false)
    private LocalDateTime dataHora;
}