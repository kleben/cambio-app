package com.wefin.cambio.model;

import java.util.Arrays;

public enum TipoTransacaoEnum {
    COMPRA,
    VENDA,
    TROCA_MOEDA;

    public static TipoTransacaoEnum fromString(String value) {
        return Arrays.stream(TipoTransacaoEnum.values())
                .filter(tipo -> tipo.name().equalsIgnoreCase(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Tipo de transação inválido: " + value));
    }
}