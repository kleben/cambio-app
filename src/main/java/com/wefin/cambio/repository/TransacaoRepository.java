package com.wefin.cambio.repository;

import com.wefin.cambio.model.Transacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TransacaoRepository extends JpaRepository<Transacao, Long>, TransacaoRepositoryCustom {

}