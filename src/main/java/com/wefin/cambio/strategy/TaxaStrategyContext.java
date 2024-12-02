package com.wefin.cambio.strategy;

import com.wefin.cambio.model.Produto;
import com.wefin.cambio.model.Reino;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Service
public class TaxaStrategyContext {

    private final Map<String, TaxaStrategy> strategies = new HashMap<>();

    public TaxaStrategyContext(Map<String, TaxaStrategy> taxaStrategies) {
        taxaStrategies.forEach((beanName, strategy) ->
                strategies.put(beanName.toLowerCase().replace("taxaproduto", ""), strategy)
        );
    }

    public void registrarEstrategia(String chave, TaxaStrategy strategy) {

        strategies.put(chave.toLowerCase(), strategy);
    }

    public BigDecimal calcularTaxa(String chave, BigDecimal valorBase, Reino reino, Produto produto) {
        TaxaStrategy strategy = strategies.get(chave.toLowerCase());
        if (strategy == null) {
            return new BigDecimal(0);
        }
        return strategy.calcularTaxa(valorBase, reino, produto);
    }
}