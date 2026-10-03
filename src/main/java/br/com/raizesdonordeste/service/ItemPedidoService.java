package br.com.raizesdonordeste.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.raizesdonordeste.model.ItemPedido;
import br.com.raizesdonordeste.repository.ItemPedidoRepository;

@Service
public class ItemPedidoService {

    private final ItemPedidoRepository itemPedidoRepository;

    public ItemPedidoService(ItemPedidoRepository itemPedidoRepository) {
        this.itemPedidoRepository = itemPedidoRepository;
    }

    public ItemPedido salvar(ItemPedido itemPedido) {
        return itemPedidoRepository.save(itemPedido);
    }

    public List<ItemPedido> listarTodos() {
        return itemPedidoRepository.findAll();
    }

    public ItemPedido buscarPorId(Long id) {
        return itemPedidoRepository.findById(id).orElse(null);
    }
}