package com.wefin.cambio.controller;

import com.wefin.cambio.model.Moeda;
import com.wefin.cambio.model.TaxaCambio;
import com.wefin.cambio.repository.MoedaRepository;
import com.wefin.cambio.repository.TaxaCambioRepository;
import com.wefin.cambio.service.TaxaCambioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;

@RestController
@RequestMapping("/api/taxa-cambio")
@RequiredArgsConstructor
public class TaxaCambioController {

    private final TaxaCambioService taxaCambioService;

    @Operation(summary = "Consulta a taxa de câmbio para uma moeda", description = "Retorna o valor convertido da moeda em Ouro Real. Se nenhuma data for passada, busca a taxa mais recente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Taxa de câmbio encontrada",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TaxaCambioResponse.class))),
            @ApiResponse(responseCode = "404", description = "Moeda não encontrada ou taxa indisponível",
                    content = @Content)
    })
    @GetMapping
    public ResponseEntity<TaxaCambioResponse> consultarTaxaCambio(
            @Parameter(description = "Nome da moeda. Valor padrão: 'Ouro Real'", example = "Ouro Real")
            @RequestParam(defaultValue = "Ouro Real") String moedaNome,
            @Parameter(description = "Data da taxa no formato dd/MM/yyyy", schema = @Schema(type = "string", format = "date", example = "30/11/2024"))
            @RequestParam(required = false) String dataTaxa) {


        TaxaCambioResponse response = taxaCambioService.consultarTaxaCambio(moedaNome, dataTaxa);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Atualizar taxa de câmbio", description = "Atualiza o valor da taxa de câmbio para uma moeda específica.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = TaxaCambio.class),
                            examples = {
                                    @io.swagger.v3.oas.annotations.media.ExampleObject(
                                            name = "Exemplo de Atualização",
                                            value = "{\"nomeMoeda\": \"Tibar\", \"novaTaxa\": 2.5}"
                                    )
                            }
                    )
            ))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Taxa de câmbio atualizada com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = TaxaCambio.class))),
            @ApiResponse(responseCode = "400", description = "Erro de validação nos dados enviados",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Moeda não encontrada",
                    content = @Content)
    })
    @PostMapping("/atualizar")
    public ResponseEntity<TaxaCambio> atualizarTaxaCambio(@RequestParam String nomeMoeda, @RequestParam BigDecimal novaTaxa) {
        TaxaCambio taxaAtualizada = taxaCambioService.atualizarTaxaCambio(nomeMoeda, novaTaxa);
        return ResponseEntity.ok(taxaAtualizada);
    }

}