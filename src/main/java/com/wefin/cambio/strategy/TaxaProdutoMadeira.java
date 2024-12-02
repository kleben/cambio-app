package com.wefin.cambio.strategy;

import com.wefin.cambio.model.Produto;
import com.wefin.cambio.model.Reino;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class TaxaProdutoMadeira implements TaxaStrategy {
    @Override
    public BigDecimal calcularTaxa(BigDecimal valorBase, Reino reino, Produto produto) {
        if (reino.getNome().equals("Montanhas Distantes")) {
            return valorBase.multiply(BigDecimal.valueOf(0.10)); // 10% de taxa
        }
        return valorBase.multiply(BigDecimal.valueOf(0.05)); // 5% de taxa
    }
}