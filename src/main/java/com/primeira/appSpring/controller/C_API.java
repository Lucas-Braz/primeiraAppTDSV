package com.primeira.appSpring.controller;

import com.primeira.appSpring.model.M_API;
import com.primeira.appSpring.service.S_API;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class C_API {
    private final S_API s_api;

    public C_API(S_API s_api) {
        this.s_api = s_api;
    }

    @GetMapping("/API/{data}")
    public List<M_API> getSaldoProdutos(@PathVariable("data") String data){
        return s_api.getSaldoProdutos(data);
    }
}
