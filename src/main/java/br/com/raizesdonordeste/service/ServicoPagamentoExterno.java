package br.com.raizesdonordeste.service;

import org.springframework.stereotype.Service;

@Service
public class ServicoPagamentoExterno {

    public String processar() {

        // Simulação da resposta de uma instituição externa
        return "APROVADO";
    }
}