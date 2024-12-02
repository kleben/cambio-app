package com.wefin.cambio.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransacaoDetalhadaDTO {

    private Long transacaoId;
    private String produto;
    private BigDecimal quantidade;
    private BigDecimal precoUnitario;
    private BigDecimal valorTotal;
    private String moedaOrigem;
    private String moedaDestino;
    private String reino;
    private String tipoTransacao;
    private BigDecimal taxaCambioOrigem;
    private BigDecimal taxaCambioDestino;
    private BigDecimal taxaAdicional;
    private LocalDateTime dataHoraTransacao;
}