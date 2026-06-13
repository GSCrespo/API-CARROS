package br.edu.ifsp.dao;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifsp.model.Usuario;

public class UsuarioJSONDAO implements UsuarioDAO{

    List<String> erros = new ArrayList<>();

    if(login == null || login.trim().isEmpty()){
        erros.add("Login obrigatório");
    }

    if(senha == null || senha.trim().isEmpty()){
        erros.add("Senha obrigatória");
    }

    if(!erros.isEmpty()){
        // retorna erro
    }

    @Override
    public Usuario inserir(String login, String senha) {

        if(buscarPorLogin(login) != null){
            return null;
        }

        Usuario usuario =
                new Usuario(login, senha, getProxId());

        // salva no json

        return usuario;
    }
}
