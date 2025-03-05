package com.primeira.appSpring.service;

import com.primeira.appSpring.model.M_API;
import com.primeira.appSpring.repository.R_Produto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class S_API {
    @Autowired
    private R_Produto r_produto;

    public List<M_API> getSaldoProdutos(String dataParam){
        LocalDate data = LocalDate.parse(dataParam);

        return r_produto.getSaldoProduto(data);
    }
}
