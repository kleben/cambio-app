package com.wefin.cambio.repository;

import com.wefin.cambio.dto.TransacaoDetalhadaDTO;
import jakarta.persistence.Query;
import lombok.RequiredArgsConstructor;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
public class TransacaoRepositoryCustomImpl implements TransacaoRepositoryCustom {

    private final EntityManager entityManager;

    @Override
    public List<TransacaoDetalhadaDTO> findTransacoesDetalhadas(String moeda, String reino, LocalDate dataTaxa, String tipoTransacao) {
        String sql = """
                SELECT
                    t.id AS transacaoId,
                    p.nome AS produto,
                    t.qtd_produtos AS quantidade,
                    p.valor AS precoUnitario,
                    t.valor_transacao AS valorTotal,
                    m_origem.nome AS moedaOrigem,
                    m_destino.nome AS moedaDestino,
                    r.nome AS reino,
                    t.tipo_transacao AS tipoTransacao,
                    tc_origem.taxa AS taxaCambioOrigem,
                    tc_destino.taxa AS taxaCambioDestino,
                    t.taxa_adicional AS taxaAdicional,
                    t.data_hora AS dataHoraTransacao
                FROM
                    transacao t
                        LEFT JOIN produto p ON t.produto_id = p.id
                        LEFT JOIN moeda m_origem ON t.moeda_origem_id = m_origem.id
                        LEFT JOIN moeda m_destino ON t.moeda_destino_id = m_destino.id
                        LEFT JOIN reino r ON t.reino_id = r.id
                        LEFT JOIN LATERAL (
                            SELECT tc.taxa
                            FROM taxa_cambio tc
                            WHERE tc.moeda_id = t.moeda_origem_id
                              AND tc.data_hora <= t.data_hora
                            ORDER BY tc.data_hora DESC
                            LIMIT 1
                        ) tc_origem ON true
                        LEFT JOIN LATERAL (
                            SELECT tc.taxa
                            FROM taxa_cambio tc
                            WHERE tc.moeda_id = t.moeda_destino_id
                              AND tc.data_hora <= t.data_hora
                            ORDER BY tc.data_hora DESC
                            LIMIT 1
                        ) tc_destino ON true
                WHERE 1=1
                """;

        if (moeda != null && !moeda.isEmpty()) {
            sql += " AND (m_origem.nome = :moeda OR m_destino.nome = :moeda) ";
        }
        if (reino != null && !reino.isEmpty()) {
            sql += " AND r.nome = :reino ";
        }
        if (dataTaxa != null) {
            sql += " AND t.data_hora >= :dataInicio AND t.data_hora < :dataFim ";
        }
        if (tipoTransacao != null && !tipoTransacao.isEmpty()) {
            sql += " AND t.tipo_transacao = :tipoTransacao ";
        }

        sql += " ORDER BY t.data_hora DESC ";

        Query query = entityManager.createNativeQuery(sql, "TransacaoDetalhadaDTO");

        if (moeda != null && !moeda.isEmpty()) {
            query.setParameter("moeda", moeda);
        }
        if (reino != null && !reino.isEmpty()) {
            query.setParameter("reino", reino);
        }
        if (dataTaxa != null) {
            LocalDateTime dataInicio = dataTaxa.atStartOfDay();
            LocalDateTime dataFim = dataInicio.plusDays(1);
            query.setParameter("dataInicio", dataInicio);
            query.setParameter("dataFim", dataFim);
        }
        if (tipoTransacao != null && !tipoTransacao.isEmpty()) {
            query.setParameter("tipoTransacao", tipoTransacao);
        }

        return query.getResultList();
    }
}
