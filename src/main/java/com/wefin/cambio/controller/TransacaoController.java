package com.wefin.cambio.controller;

import com.wefin.cambio.dto.TransacaoDTO;
import com.wefin.cambio.dto.TransacaoDetalhadaDTO;
import com.wefin.cambio.model.TipoTransacaoEnum;
import com.wefin.cambio.model.Transacao;
import com.wefin.cambio.service.TransacaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/transacoes")
@RequiredArgsConstructor
public class TransacaoController {

    private final TransacaoService transacaoService;

    @Operation(summary = "Troca de moeda", description = "Cria uma nova transação de troca de moeda.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Transação criada com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Transacao.class))),
            @ApiResponse(responseCode = "404", description = "Objeto relacionado não encontrado",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Erro de validação",
                    content = @Content)
    })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Objeto de entrada para trocar moeda",
            required = true,
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = TransacaoDTO.class),
                    examples = @ExampleObject(
                            name = "Exemplo de entrada",
                            value = "{ \"moedaOrigem\": \"Ouro Real\", \"moedaDestino\": \"Tibar\", \"reino\": \"Wefin\", \"valor\": 10 }"
                    )
            )
    )
    @PostMapping("/trocar-moeda")
    public ResponseEntity<Transacao> trocarMoeda(@RequestBody TransacaoDTO transacaoDto) {
        Transacao transacao = transacaoService.trocarMoeda(transacaoDto);
        return ResponseEntity.status(201).body(transacao);
    }

    @Operation(summary = "Comprar / Vender produto", description = "Cria uma nova transação de comrpra ou venda de produto." +
            "<br/> Tipos de transações permitidos: <b>COMPRA, VENDA</b>" +
            "<br/> Tipos de produtos: <b>Madeira, Pele, Hidromel</b>")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Transação criada com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Transacao.class))),
            @ApiResponse(responseCode = "404", description = "Objeto relacionado não encontrado",
                    content = @Content),
            @ApiResponse(responseCode = "400", description = "Erro de validação",
                    content = @Content)
    })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Objeto de entrada para comprar ou vender produto",
            required = true,
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = TransacaoDTO.class),
                    examples = @ExampleObject(
                            name = "Exemplo de entrada",
                            value = "{ \"tipoTransacao\": \"COMPRA\", \"produto\": \"Madeira\", \"quantidade\": 1, \"moedaOrigem\": \"Ouro Real\", \"reino\": \"Wefin\" }"
                    )
            )
    )
    @PostMapping("/negociar-produto")
    public ResponseEntity<Transacao> compraVendaProduto(@RequestBody TransacaoDTO transacaoDto) {
        Transacao transacao = transacaoService.comprarVenderProduto(transacaoDto);
        return ResponseEntity.status(201).body(transacao);
    }

    @Operation(summary = "Consultar transações detalhadas", description = "Consulta detalhada de transações com filtros opcionais.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Consulta realizada com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = TransacaoDetalhadaDTO.class))),
            @ApiResponse(responseCode = "400", description = "Erro de validação nos dados enviados",
                    content = @Content)
    })
    @GetMapping("/transacoes-detalhadas")
    public ResponseEntity<List<TransacaoDetalhadaDTO>> consultarTransacoesDetalhadas(
            @RequestParam(required = false) String moeda,
            @RequestParam(required = false) String reino,
            @RequestParam(required = false) LocalDate dataTaxa,
            @RequestParam(required = false) String tipoTransacao) {
        List<TransacaoDetalhadaDTO> transacoes = transacaoService.consultarTransacoesDetalhadas(moeda, reino, dataTaxa, tipoTransacao);
        return ResponseEntity.ok(transacoes);
    }
}