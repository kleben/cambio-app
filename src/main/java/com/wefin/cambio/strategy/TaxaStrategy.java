package com.wefin.cambio.strategy;

import com.wefin.cambio.model.Produto;
import com.wefin.cambio.model.Reino;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

// Strategy interface
public interface TaxaStrategy {
    BigDecimal calcularTaxa(BigDecimal valorBase, Reino reino, Produto produto);
}