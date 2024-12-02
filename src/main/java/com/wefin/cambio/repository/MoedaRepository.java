package com.wefin.cambio.repository;

import com.wefin.cambio.model.Moeda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MoedaRepository extends JpaRepository<Moeda, Long> {
    Optional<Moeda> findByNomeIgnoreCase(String nome);
}