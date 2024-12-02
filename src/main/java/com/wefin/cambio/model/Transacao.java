package com.wefin.cambio.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@SqlResultSetMapping(
        name = "TransacaoDetalhadaDTO",
        classes = @ConstructorResult(
                targetClass = com.wefin.cambio.dto.TransacaoDetalhadaDTO.class,
                columns = {
                        @ColumnResult(name = "transacaoId", type = Long.class),
                        @ColumnResult(name = "produto", type = String.class),
                        @ColumnResult(name = "quantidade", type = BigDecimal.class),
                        @ColumnResult(name = "precoUnitario", type = BigDecimal.class),
                        @ColumnResult(name = "valorTotal", type = BigDecimal.class),
                        @ColumnResult(name = "moedaOrigem", type = String.class),
                        @ColumnResult(name = "moedaDestino", type = String.class),
                        @ColumnResult(name = "reino", type = String.class),
                        @ColumnResult(name = "tipoTransacao", type = String.class),
                        @ColumnResult(name = "taxaCambioOrigem", type = BigDecimal.class),
                        @ColumnResult(name = "taxaCambioDestino", type = BigDecimal.class),
                        @ColumnResult(name = "taxaAdicional", type = BigDecimal.class),
                        @ColumnResult(name = "dataHoraTransacao", type = LocalDateTime.class)
                }
        )
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "transacao")
public class Transacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = true)
    @JoinColumn(name = "produto_id", nullable = true)
    private Produto produto;

    @Column(name = "qtd_produtos", nullable = true)
    private BigDecimal qtdProdutos;

    @ManyToOne(optional = false)
    @JoinColumn(name = "moeda_origem_id", nullable = false)
    private Moeda moedaOrigem;

    @ManyToOne(optional = false)
    @JoinColumn(name = "moeda_destino_id", nullable = false)
    private Moeda moedaDestino;

    @ManyToOne(optional = false)
    @JoinColumn(name = "reino_id", nullable = false)
    private Reino reino;

    @Column(name = "taxa_cambio", nullable = true)
    private BigDecimal taxaCambio;

    @Column(name = "taxa_adicional", nullable = true)
    private BigDecimal taxaAdicional;

    @Column(name = "valor_transacao", nullable = true)
    private BigDecimal valorTransacao;

    @Column(name = "data_hora", nullable = false)
    private LocalDateTime dataHora;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_transacao", nullable = false)
    private TipoTransacaoEnum tipoTransacaoEnum;

}