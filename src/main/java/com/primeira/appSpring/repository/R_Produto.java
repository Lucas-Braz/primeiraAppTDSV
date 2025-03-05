package com.primeira.appSpring.repository;

import com.primeira.appSpring.model.M_API;
import com.primeira.appSpring.model.M_Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface R_Produto  extends JpaRepository<M_Produto,Long> {
    @Query(value="with vendas_produto as (\n" +
            "\tselect \n" +
            "\t\tid_produto,\n" +
            "\t\tsum(quantidade) as quantidade\n" +
            "\tfrom consumo\n" +
            "\twhere cast(consumo.data as date) <= :data_base\n" +
            "\tgroup by id_produto\n" +
            "),\n" +
            "compras_produto as (\n" +
            "\tselect\n" +
            "\t\tid_produto,\n" +
            "\t\tsum(quantidade) as quantidade,\n" +
            "\t\tsum(quantidade * preco) as valor,\n" +
            "\t\tmax(data) as ultima_compra\n" +
            "\tfrom compra\n" +
            "\twhere cast(compra.data as date) <= :data_base\n" +
            "\tgroup by 1\n" +
            ")\n" +
            "select \n" +
            "\tp.cod_barras as produto,\n" +
            "\tcoalesce(cp.quantidade,0) - coalesce(vp.quantidade,0) as quantidade,\n" +
            "\tp.min,\n" +
            "\tp.max,\n" +
            "\tcoalesce(cp.valor/cp.quantidade,0) as custo_medio,\n" +
            "\tcp.ultima_compra\n" +
            "from produto p\n" +
            "left join vendas_produto vp\n" +
            "\ton p.id = vp.id_produto\n" +
            "left join compras_produto cp\n" +
            "\ton p.id = cp.id_produto\t\n",nativeQuery = true)
    List<M_API> getSaldoProduto(@Param("data_base")LocalDate data);
}
