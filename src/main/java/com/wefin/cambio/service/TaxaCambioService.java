package com.wefin.cambio.service;

import com.wefin.cambio.controller.TaxaCambioResponse;
import com.wefin.cambio.exception.NotFoundException;
import com.wefin.cambio.exception.DataValidationException;
import com.wefin.cambio.model.Moeda;
import com.wefin.cambio.model.TaxaCambio;
import com.wefin.cambio.repository.MoedaRepository;
import com.wefin.cambio.repository.TaxaCambioRepository;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaxaCambioService {

    private final TaxaCambioRepository taxaCambioRepository;
    private final MoedaRepository moedaRepository;

    public TaxaCambioResponse consultarTaxaCambio(String moedaNome, String dataTaxa) {
        // Buscar moeda pelo nome
        Moeda moeda = moedaRepository.findByNomeIgnoreCase(moedaNome)
                .orElseThrow(() -> new NotFoundException("Moeda não encontrada: " + moedaNome));

        Optional<TaxaCambio> taxaCambioOptional;

        // Processar data da taxa, se fornecida
        if (Strings.isNotBlank(dataTaxa)) {
            try {
                LocalDate parsedDate = LocalDate.parse(dataTaxa, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                LocalDateTime dataConvertida = parsedDate.atStartOfDay();

                taxaCambioOptional = taxaCambioRepository.findFirstByMoedaAndDataHora(moeda, dataConvertida);
            } catch (DateTimeParseException e) {
                throw new DataValidationException("Data no formato inválido. Use dd/MM/yyyy.");
            }
        } else {
            taxaCambioOptional = taxaCambioRepository.findFirstByMoedaOrderByDataHoraDesc(moeda);
        }

        TaxaCambio taxaCambio = taxaCambioOptional
                .orElseThrow(() -> new NotFoundException("Taxa de câmbio não encontrada para a moeda: " + moedaNome));

        return new TaxaCambioResponse(
                moeda.getNome(),
                taxaCambio.getTaxa(),
                taxaCambio.getDataHora()
        );
    }

    public TaxaCambio consultarTaxaCambio(Moeda moeda) {
        return taxaCambioRepository.findFirstByMoedaOrderByDataHoraDesc(moeda)
                .orElseThrow(() -> new NotFoundException("Taxa de câmbio não encontrada para a moeda: " + moeda.getNome()));
    }

    public BigDecimal converterMoedas(Moeda moedaOrigem, Moeda moedaDestino, BigDecimal valor) {
        if (moedaOrigem.getNome().equalsIgnoreCase(moedaDestino.getNome())) {
            return valor;
        }

        TaxaCambio taxaCambioOrigem = consultarTaxaCambio(moedaOrigem);
        TaxaCambio taxaCambioDestino = consultarTaxaCambio(moedaDestino);

        BigDecimal valorConvertido;

        if ("Ouro Real".equalsIgnoreCase(moedaOrigem.getNome())) {
            valorConvertido = valor.multiply(taxaCambioDestino.getTaxa());
        } else if ("Ouro Real".equalsIgnoreCase(moedaDestino.getNome())) {
            valorConvertido = valor.divide(taxaCambioOrigem.getTaxa(), 2, RoundingMode.HALF_EVEN);
        } else {
            BigDecimal valorEmOuroReal = valor.divide(taxaCambioOrigem.getTaxa(), 2, RoundingMode.HALF_EVEN);
            valorConvertido = valorEmOuroReal.multiply(taxaCambioDestino.getTaxa());
        }

        return valorConvertido;
    }

    public BigDecimal calcularTaxaAplicada(Moeda moedaOrigem, Moeda moedaDestino) {
        if ("Ouro Real".equalsIgnoreCase(moedaDestino.getNome())) {
            return consultarTaxaCambio(moedaOrigem).getTaxa();
        }

        BigDecimal taxaOrigem = consultarTaxaCambio(moedaOrigem).getTaxa();
        BigDecimal taxaDestino = consultarTaxaCambio(moedaDestino).getTaxa();

        return taxaOrigem.divide(taxaDestino, 2, RoundingMode.HALF_EVEN);
    }

    @Transactional
    public TaxaCambio atualizarTaxaCambio(String nomeMoeda, BigDecimal novaTaxa) {
        if (novaTaxa.compareTo(BigDecimal.ONE) <= 0) {
            throw new IllegalArgumentException("Por determinação do rei, nenhuma moeda pode ter valor maior que o Ouro Real.");
        }

        Moeda moeda = moedaRepository.findByNomeIgnoreCase(nomeMoeda)
                .orElseThrow(() -> new IllegalArgumentException("Moeda não encontrada: " + nomeMoeda));

        TaxaCambio taxaCambio = new TaxaCambio();
        taxaCambio.setMoeda(moeda);
        taxaCambio.setTaxa(novaTaxa);
        taxaCambio.setDataHora(LocalDateTime.now());

        return taxaCambioRepository.save(taxaCambio);
    }

//    public BigDecimal converterParaOuroReal(Long idMoeda, BigDecimal valor) {
//        Moeda moeda = moedaRepository.findById(idMoeda)
//                .orElseThrow(() -> new NotFoundException("Moeda não encontrada com o ID: " + idMoeda));
//
//        if ("Ouro Real".equalsIgnoreCase(moeda.getNome()))
//            return valor;
//
//        TaxaCambio taxaCambio = taxaCambioRepository.findFirstByMoedaOrderByDataHoraDesc(moeda)
//                .orElseThrow(() -> new NotFoundException("Taxa de câmbio atual não encontrada para a moeda: " + moeda.getNome()));
//
//        return valor.multiply(taxaCambio.getTaxa());
//    }


//    public BigDecimal converterMoedas(Long idMoedaOrigem, Long idMoedaDestino, BigDecimal valor) {
//        Moeda moedaOrigem = moedaRepository.findById(idMoedaOrigem)
//                .orElseThrow(() -> new NotFoundException("Moeda de origem não encontrada com o ID: " + idMoedaOrigem));
//
//        Moeda moedaDestino = moedaRepository.findById(idMoedaDestino)
//                .orElseThrow(() -> new NotFoundException("Moeda de destino não encontrada com o ID: " + idMoedaDestino));
//
//        if (moedaOrigem.getNome().equalsIgnoreCase(moedaDestino.getNome())) {
//            return valor;
//        }
//
//        TaxaCambio taxaCambioOrigem = taxaCambioRepository.findFirstByMoedaOrderByDataHoraDesc(moedaOrigem)
//                .orElseThrow(() -> new NotFoundException("Taxa de câmbio atual não encontrada para a moeda de origem: " + moedaOrigem.getNome()));
//
//        BigDecimal valorEmOuroReal = valor.multiply(taxaCambioOrigem.getTaxa());
//
//        if ("Ouro Real".equalsIgnoreCase(moedaDestino.getNome())) {
//            return valorEmOuroReal;
//        }
//
//        TaxaCambio taxaCambioDestino = taxaCambioRepository.findFirstByMoedaOrderByDataHoraDesc(moedaDestino)
//                .orElseThrow(() -> new NotFoundException("Taxa de câmbio atual não encontrada para a moeda de destino: " + moedaDestino.getNome()));
//
//        return valorEmOuroReal.divide(taxaCambioDestino.getTaxa(), BigDecimal.ROUND_HALF_UP);
//    }

}
