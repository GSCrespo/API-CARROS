package br.edu.ifsp.dao;

import java.util.List;

import br.edu.ifsp.model.Carro;

public interface CarroDAO {

    Carro inserir(Carro carro);
    List<Carro> listar();
    Carro buscarPorId(int id);
    Carro atualizar(Carro carro);
    boolean remover(int id);
}
