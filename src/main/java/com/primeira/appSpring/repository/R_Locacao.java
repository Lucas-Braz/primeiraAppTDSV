package com.primeira.appSpring.repository;

import com.primeira.appSpring.model.M_Locacao;
import com.primeira.appSpring.model.M_ViewLocacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface R_Locacao extends JpaRepository<M_Locacao, Long> {
    @Query(value = "select * " +
            "from locacao " +
            "where locacao.id_quarto = :id_quarto " +
            "and (locacao.check_in between :check_in and :check_out " +
            "or :check_in between locacao.check_in and locacao.check_out " +
            "or :check_out between locacao.check_in and locacao.check_out) " +
            "limit 1",nativeQuery = true)
    M_Locacao quartoEstaLocado(@Param("id_quarto") Long id,
                                        @Param("check_in") LocalDateTime check_in,
                                        @Param("check_out") LocalDateTime check_out);

    @Query(value="select " +
            "locacao.id, " +
            "quarto.nome, " +
            "locacao.preco, " +
            "locacao.senha, " +
            "locacao.check_in, " +
            "locacao.check_out, " +
            "case cast(locacao.check_out as date) - cast(locacao.check_in as date) when 0 " +
            "then 1 " +
            "else cast(locacao.check_out as date) - cast(locacao.check_in as date) " +
            "end as diarias, " +
            "COALESCE(sum(consumo.preco * consumo.quantidade),0) as total_consumo " +
            "from locacao " +
            "inner join quarto " +
            "on locacao.id_quarto = quarto.id " +
            "LEFT JOIN consumo " +
            "on locacao.id = consumo.id_locacao " +
            "AND consumo.id_produto not in(8)/*Elimina Diarias*/ " +
            "where locacao.id_usuario = :id_usuario " +
            "and now() between locacao.check_in and locacao.check_out " +
            "GROUP BY 1,2,3,4,5,6,7 " +
            "ORDER BY 1", nativeQuery = true)
    List<M_ViewLocacao> getLocacoesEmCurso(@Param("id_usuario") Long id_usuario);

    @Query(value="select " +
            "locacao.id, " +
            "quarto.nome, " +
            "locacao.preco, " +
            "locacao.senha, " +
            "locacao.check_in, " +
            "locacao.check_out, " +
            "case cast(locacao.check_out as date) - cast(locacao.check_in as date) when 0 " +
            "then 1 " +
            "else cast(locacao.check_out as date) - cast(locacao.check_in as date) " +
            "end as diarias, " +
            "COALESCE(sum(consumo.preco * consumo.quantidade),0) as total_consumo " +
            "from locacao " +
            "inner join quarto " +
            "on locacao.id_quarto = quarto.id " +
            "LEFT JOIN consumo " +
            "on locacao.id = consumo.id_locacao " +
            "AND consumo.id_produto not in(8)/*Elimina Diarias*/ " +
            "where locacao.id_usuario = :id_usuario " +
            "and now() < locacao.check_in " +
            "GROUP BY 1,2,3,4,5,6,7 " +
            "ORDER BY 1", nativeQuery = true)
    List<M_ViewLocacao> getLocacoesFuturas(@Param("id_usuario") Long id_usuario);

    @Query(value="select " +
            "locacao.id, " +
            "quarto.nome, " +
            "locacao.preco, " +
            "locacao.senha, " +
            "locacao.check_in, " +
            "locacao.check_out, " +
            "case cast(locacao.check_out as date) - cast(locacao.check_in as date) when 0 " +
            "then 1 " +
            "else cast(locacao.check_out as date) - cast(locacao.check_in as date) " +
            "end as diarias, " +
            "COALESCE(sum(consumo.preco * consumo.quantidade),0) as total_consumo " +
            "from locacao " +
            "inner join quarto " +
            "on locacao.id_quarto = quarto.id " +
            "LEFT JOIN consumo " +
            "on locacao.id = consumo.id_locacao " +
            "AND consumo.id_produto not in(8)/*Elimina Diarias*/ " +
            "where locacao.id_usuario = :id_usuario " +
            "and now() > locacao.check_out " +
            "GROUP BY 1,2,3,4,5,6,7 " +
            "ORDER BY 1", nativeQuery = true)
    List<M_ViewLocacao> getLocacoesRealizadas(@Param("id_usuario") Long id_usuario);

    @Query(value="select * " +
            "from locacao " +
            "where (cast(check_out as date) > now() or cast(check_out as date) = cast(check_in as date)) " +
            "and now() between check_in and check_out",nativeQuery = true)
    List<M_Locacao> getLocacoesGerarDiarias();
}
