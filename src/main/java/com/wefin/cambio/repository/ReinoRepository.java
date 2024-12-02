package com.wefin.cambio.repository;

import com.wefin.cambio.model.Reino;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReinoRepository extends JpaRepository<Reino, Long> {
    Optional<Reino> findByNomeIgnoreCase(String reino);
}