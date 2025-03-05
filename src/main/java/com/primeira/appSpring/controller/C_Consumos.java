package com.primeira.appSpring.controller;

import com.primeira.appSpring.service.S_Consumo;
import com.primeira.appSpring.service.S_Produto;
import com.primeira.appSpring.service.S_Reserva;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class C_Consumos {
    private final S_Produto s_produto;
    private final S_Consumo s_consumo;
    private final S_Reserva s_reserva;

    public C_Consumos(S_Produto s_produto, S_Consumo s_consumo, S_Reserva s_reserva) {
        this.s_produto = s_produto;
        this.s_consumo = s_consumo;
        this.s_reserva = s_reserva;
    }

    @GetMapping("/consumo/{id}")
    public String getConsumos(@PathVariable("id") Long id,
                              Model model,
                              HttpSession session){
        if(session.getAttribute("usuario") != null) {
            model.addAttribute("locacao", s_reserva.getLocacaoById(id));
            model.addAttribute("produtos", s_produto.getProdutos());
            model.addAttribute("consumos", s_consumo.getConsumos(id));
            model.addAttribute("search",true);
            return "consumos/consumo";
        }
        return "redirect:/";
    }

    @PostMapping("/consumo")
    public String getItemConsumo(@RequestParam("locacao") Long idLocacao,
                                 @RequestParam("produto") Long idProduto,
                                 @RequestParam("quantidade") Integer quantidade,
                                 Model model,
                                 HttpSession session){
        if(session.getAttribute("usuario") != null) {
            model.addAttribute("consumo", s_consumo.salvarConsumo(idProduto, idLocacao, quantidade));
            return "consumos/item";
        }
        return "redirect:/";
    }
}