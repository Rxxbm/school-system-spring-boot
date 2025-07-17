package com.rubem.service;

import com.rubem.model.Gasto;
import com.rubem.repository.GastoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GastoService {

    @Autowired
    private GastoRepository gastoRepository;

    public List<Gasto> listarTodos() {
        return gastoRepository.findAll();
    }

    public Gasto buscarPorId(Long id) {
        return gastoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Gasto não encontrado com id: " + id));
    }

    public Gasto salvar(Gasto gasto) {
        return gastoRepository.save(gasto);
    }

    public Gasto atualizar(Long id, Gasto novoGasto) {
        Gasto gasto = buscarPorId(id);
        gasto.setDescricao(novoGasto.getDescricao());
        gasto.setValor(novoGasto.getValor());
        gasto.setData(novoGasto.getData());
        return gastoRepository.save(gasto);
    }

    public void deletar(Long id) {
        gastoRepository.deleteById(id);
    }
}
