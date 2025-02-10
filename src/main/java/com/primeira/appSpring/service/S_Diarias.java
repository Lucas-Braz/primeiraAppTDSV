package com.primeira.appSpring.service;

import com.primeira.appSpring.model.M_Consumo;
import com.primeira.appSpring.model.M_Locacao;
import com.primeira.appSpring.model.M_Produto;
import com.primeira.appSpring.repository.R_Consumo;
import com.primeira.appSpring.repository.R_Locacao;
import com.primeira.appSpring.repository.R_Produto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class S_Diarias {
    @Autowired
    private R_Locacao r_locacao;
    @Autowired
    private R_Produto r_produto;
    @Autowired
    private R_Consumo r_consumo;

    @Scheduled(cron = "0 0 10 * * ?")
    public void gerarDiarias() {
        List<M_Locacao> locacoes = r_locacao.getLocacoesGerarDiarias();
        M_Produto diaria = new M_Produto();
        diaria.setId(Long.parseLong("8"));

        for(M_Locacao locacao : locacoes){
            M_Consumo m_consumo = new M_Consumo();
            m_consumo.setQuantidade(1);
            m_consumo.setProduto(diaria);
            m_consumo.setPreco(locacao.getPreco());
            m_consumo.setLocacao(locacao);
            m_consumo.setData(LocalDateTime.now());
            r_consumo.save(m_consumo);
        }

    }
}
