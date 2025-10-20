package com.akhiastudios.todolist.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // controlador em rest (API)
@RequestMapping("/primeiraRota") // http://localhost:8080/[ROTA]
public class MinhaPrimeiraController {

    @GetMapping("/mensagem") // http://localhost:8080/primeiraRota/mensagem
    public String primeiraMensagem () { // metodo
        return "Funcionou";
    }
}
