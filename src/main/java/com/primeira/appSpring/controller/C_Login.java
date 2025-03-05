package com.primeira.appSpring.controller;

import com.primeira.appSpring.model.M_Usuario;
import com.primeira.appSpring.service.S_Home;
import com.primeira.appSpring.service.S_Login;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class C_Login {
    private final S_Home s_home;

    public C_Login(S_Home s_home) {
        this.s_home = s_home;
    }

    @GetMapping("/")
    public String getLogin(HttpSession session,
                           Model model){
        M_Usuario m_usuario = (M_Usuario) session.getAttribute("usuario");
        if(m_usuario != null){
            model.addAttribute("em_curso",s_home.getLocacoesEmCurso(m_usuario.getId()));
            model.addAttribute("futuras",s_home.getLocacoesFuturas(m_usuario.getId()));
            model.addAttribute("realizadas",s_home.getLocacoesRealizas(m_usuario.getId()));
            model.addAttribute("search",true);
            return "home/home";
        }
        return "index";
    }

    @PostMapping("/")
    public String postLogin(@RequestParam("usuario") String usuario,
                            @RequestParam("senha") String senha,
                            HttpSession session){
        M_Usuario m_usuario = S_Login.validaLogin(usuario,senha);
        session.setAttribute("usuario",m_usuario);
        return "redirect:/";
    }

    @GetMapping("/logout")
    @ResponseBody
    public boolean getLogout(HttpSession session){
        session.setAttribute("usuario", null);
        return true;
    }
}
