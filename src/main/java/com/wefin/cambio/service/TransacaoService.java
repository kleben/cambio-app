package com.wefin.cambio.service;

import com.wefin.cambio.dto.TransacaoDTO;
import com.wefin.cambio.dto.TransacaoDetalhadaDTO;
import com.wefin.cambio.exception.DataValidationException;
import com.wefin.cambio.exception.NotFoundException;
import com.wefin.cambio.model.*;
import com.wefin.cambio.repository.MoedaRepository;
import com.wefin.cambio.repository.ProdutoRepository;
import com.wefin.cambio.repository.ReinoRepository;
import com.wefin.cambio.repository.TransacaoRepository;
import com.wefin.cambio.strategy.TaxaStrategyContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransacaoService {

    private final TransacaoRepository transacaoRepository;
    private final ProdutoRepository produtoRepository;
    private final MoedaRepository moedaRepository;
    private final ReinoRepository reinoRepository;
    private final TaxaCambioService taxaCambioService;
    private final TaxaStrategyContext taxaStrategyContext;

//    public Transacao processarTransacao(TransacaoDTO transacaoDto) {
//        switch (transacaoDto.getTipoTransacao()) {
//            case TROCA_MOEDA:
//                return trocarMoeda(transacaoDto);
//            case COMPRA_PRODUTO:
//                return comprarVenderProduto(transacaoDto);
//            case VENDA_PRODUTO:
//                return comprarVenderProduto(transacaoDto);
//            default:
//                throw new DataValidationException("O mercado não aceita nenhum tipo de comércio de objetos/pessoas de forma a ferir valores morais.");
//        }
//    }

    @Transactional(rollbackFor = Exception.class)
    public Transacao trocarMoeda(TransacaoDTO transacaoDto) {

        Moeda moedaOrigem = moedaRepository.findByNomeIgnoreCase(transacaoDto.getMoedaOrigem())
                .orElseThrow(() -> new NotFoundException("Moeda origem não encontrada: " + transacaoDto.getMoedaOrigem()));

        Moeda moedaDestino = moedaRepository.findByNomeIgnoreCase(transacaoDto.getMoedaDestino())
                .orElseThrow(() -> new NotFoundException("Moeda destino não encontrada: " + transacaoDto.getMoedaDestino()));

        Reino reino = reinoRepository.findByNomeIgnoreCase(transacaoDto.getReino())
                .orElseThrow(() -> new NotFoundException("Reino não encontrado: " + transacaoDto.getReino()));

        BigDecimal taxaAplicada = taxaCambioService.calcularTaxaAplicada(moedaOrigem, moedaDestino);
        BigDecimal valorConvertido = taxaCambioService.converterMoedas(moedaOrigem, moedaDestino, transacaoDto.getValor());

        Transacao transacao = new Transacao();
        transacao.setMoedaOrigem(moedaOrigem);
        transacao.setMoedaDestino(moedaDestino);
        transacao.setReino(reino);
        transacao.setTipoTransacaoEnum(TipoTransacaoEnum.TROCA_MOEDA);
        transacao.setTaxaCambio(taxaAplicada);
        transacao.setValorTransacao(valorConvertido);
        transacao.setDataHora(LocalDateTime.now());

        return transacaoRepository.save(transacao);
    }

    @Transactional(rollbackFor = Exception.class)
    public Transacao comprarVenderProduto(TransacaoDTO transacaoDto) {
        Produto produto = produtoRepository.findByNomeIgnoreCase(transacaoDto.getProduto())
                .orElseThrow(() -> new NotFoundException("Produto não encontrado: " + transacaoDto.getProduto()));

        Moeda moedaOuroReal = moedaRepository.findByNomeIgnoreCase("Ouro Real")
                .orElseThrow(() -> new NotFoundException("Moeda origem não encontrada: " + transacaoDto.getMoedaOrigem()));

        Moeda moedaOrigem = moedaRepository.findByNomeIgnoreCase(transacaoDto.getMoedaOrigem())
                .orElseThrow(() -> new NotFoundException("Moeda origem não encontrada: " + transacaoDto.getMoedaOrigem()));

        Reino reino = reinoRepository.findByNomeIgnoreCase(transacaoDto.getReino())
                .orElseThrow(() -> new NotFoundException("Reino não encontrado: " + transacaoDto.getReino()));

        if (!(transacaoDto.getTipoTransacao().trim().toUpperCase().equals(TipoTransacaoEnum.COMPRA.toString())
                || transacaoDto.getTipoTransacao().trim().toUpperCase().equals(TipoTransacaoEnum.VENDA.toString()))) {
            throw new DataValidationException("Tipo de transação inválido.");
        }

        // Calcular valor base
        BigDecimal valorBase = produto.getValor().multiply(transacaoDto.getQuantidade());

        // Aplicar taxa adicional
        String chaveStrategy = produto.getNome().toLowerCase();
        BigDecimal taxaAdicional = taxaStrategyContext.calcularTaxa(chaveStrategy, valorBase, reino, produto);
        BigDecimal valorComTaxa = valorBase.add(taxaAdicional);

        // Converter para a moeda de origem, se necessário
        BigDecimal valorFinal;
        if (!"Ouro Real".equalsIgnoreCase(moedaOrigem.getNome())) {
            valorFinal = taxaCambioService.converterMoedas(moedaOuroReal, moedaOrigem, valorComTaxa);
        } else {
            valorFinal = valorComTaxa;
        }

        Transacao transacao = new Transacao();
        transacao.setProduto(produto);
        transacao.setQtdProdutos(transacaoDto.getQuantidade());
        transacao.setMoedaOrigem(moedaOrigem);
        transacao.setMoedaDestino(moedaOuroReal);
        transacao.setReino(reino);
        transacao.setTipoTransacaoEnum(TipoTransacaoEnum.fromString(transacaoDto.getTipoTransacao().trim().toUpperCase()));
        transacao.setTaxaAdicional(taxaAdicional);
        transacao.setValorTransacao(valorFinal);
        transacao.setDataHora(LocalDateTime.now());

        return transacaoRepository.save(transacao);
    }

    public List<TransacaoDetalhadaDTO> consultarTransacoesDetalhadas(String moeda, String reino, LocalDate dataTaxa, String tipoTransacao) {
        return transacaoRepository.findTransacoesDetalhadas(moeda, reino, dataTaxa, tipoTransacao);
    }

}