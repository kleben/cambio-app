package com.wefin.cambio.repository;

import com.wefin.cambio.model.Moeda;
import com.wefin.cambio.model.TaxaCambio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface TaxaCambioRepository extends JpaRepository<TaxaCambio, Long> {
    Optional<TaxaCambio> findFirstByMoedaAndDataHora(Moeda moeda, LocalDateTime dataHora);

    Optional<TaxaCambio> findFirstByMoedaOrderByDataHoraDesc(Moeda moeda);

    Optional<TaxaCambio> findFirstByMoeda(Moeda moeda);
}