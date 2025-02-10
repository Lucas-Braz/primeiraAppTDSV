package com.primeira.appSpring.service;

import com.primeira.appSpring.model.M_ViewLocacao;
import com.primeira.appSpring.repository.R_Locacao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class S_Home {
    @Autowired
    private R_Locacao r_locacao;

    public List<M_ViewLocacao> getLocacoesEmCurso(Long id_usuario){
        return r_locacao.getLocacoesEmCurso(id_usuario);
    }

    public List<M_ViewLocacao> getLocacoesFuturas(Long id_usuario){
        return r_locacao.getLocacoesFuturas(id_usuario);
    }

    public List<M_ViewLocacao> getLocacoesRealizas(Long id_usuario){
        return r_locacao.getLocacoesRealizadas(id_usuario);
    }
}
