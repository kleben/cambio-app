package com.wefin.cambio.repository;

import com.wefin.cambio.dto.TransacaoDetalhadaDTO;

import java.time.LocalDate;
import java.util.List;

public interface TransacaoRepositoryCustom {
    List<TransacaoDetalhadaDTO> findTransacoesDetalhadas(String moeda, String reino, LocalDate dataTaxa, String tipoTransacao);
}
