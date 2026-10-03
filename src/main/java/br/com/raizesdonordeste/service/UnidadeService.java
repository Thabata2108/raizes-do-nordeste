package br.com.raizesdonordeste.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.raizesdonordeste.model.Unidade;
import br.com.raizesdonordeste.repository.UnidadeRepository;

@Service
public class UnidadeService {

    private final UnidadeRepository unidadeRepository;

    public UnidadeService(UnidadeRepository unidadeRepository) {
        this.unidadeRepository = unidadeRepository;
    }

    public Unidade salvar(Unidade unidade) {
        return unidadeRepository.save(unidade);
    }

    public List<Unidade> listarTodas() {
        return unidadeRepository.findAll();
    }

    public Unidade buscarPorId(Long id) {
        return unidadeRepository.findById(id).orElse(null);
    }
}