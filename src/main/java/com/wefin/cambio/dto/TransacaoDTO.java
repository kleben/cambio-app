package com.wefin.cambio.dto;

import com.wefin.cambio.model.TipoTransacaoEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransacaoDTO {

    private String produto;

    private String moedaOrigem;

    private String moedaDestino;

    private BigDecimal quantidade;

    @NotBlank(message = "O reino é obrigatório.")
    private String reino;

    private String tipoTransacao;

    private BigDecimal valor;
}
