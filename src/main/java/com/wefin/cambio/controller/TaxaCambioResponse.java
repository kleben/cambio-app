package com.wefin.cambio.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TaxaCambioResponse {
        private String moeda;
        private BigDecimal taxa;
        private String data;

        public TaxaCambioResponse(String moeda, BigDecimal taxa, LocalDateTime dataHora) {
            this.moeda = moeda;
            this.taxa = taxa;
            this.data = dataHora.toLocalDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));;
        }

        public String getMoeda() {
            return moeda;
        }

        public BigDecimal getTaxa() {
            return taxa;
        }

        public String getData() {
            return data;
        }
    }