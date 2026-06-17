package br.edu.ifsp.dao;

import java.util.List;

import br.edu.ifsp.model.Usuario;

public interface UsuarioDAO {
    Usuario inserir(String login, String senha, Boolean admin);
    Usuario buscarPorLogin(String login);
    List<Usuario> listar();
}
