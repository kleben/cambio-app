package com.wefin.cambio.strategy;

import com.wefin.cambio.model.Produto;
import com.wefin.cambio.model.Reino;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class TaxaProdutoPele implements TaxaStrategy {
    @Override
    public BigDecimal calcularTaxa(BigDecimal valorBase, Reino reino, Produto produto) {
        if (reino.getNome().equals("Vale Verdejante")) {
            return valorBase.multiply(BigDecimal.valueOf(0.15)); // 15% de taxa
        }
        return valorBase.multiply(BigDecimal.valueOf(0.07)); // 7% de taxa
    }
}