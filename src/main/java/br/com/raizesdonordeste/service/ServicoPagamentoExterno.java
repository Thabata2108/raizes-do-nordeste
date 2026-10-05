package br.com.raizesdonordeste.service;

import org.springframework.stereotype.Service;

@Service
public class ServicoPagamentoExterno {

    public String processar(String resultadoSimulado) {

        if ("NEGADO".equalsIgnoreCase(resultadoSimulado)) {
            return "NEGADO";
        }

        return "APROVADO";
    }
}