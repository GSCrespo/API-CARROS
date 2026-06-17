package br.edu.ifsp.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import br.edu.ifsp.model.Usuario;
import br.edu.ifsp.dao.UsuarioDAO;
import br.edu.ifsp.dao.UsuarioJSONDAO;
import com.google.gson.Gson;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private UsuarioDAO usuarioDAO;

    @Override
    public void init() {

        String path =
                getServletContext().getRealPath("/");

        usuarioDAO =
                new UsuarioJSONDAO(path);
    }

    @Override
    protected void doPost(HttpServletRequest request,HttpServletResponse response) 
    throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String userName =
                request.getParameter("username");

        String senha =
                request.getParameter("senha");

        List<String> erros =
                new ArrayList<>();

        if(userName == null ||
                userName.trim().isEmpty()){

            erros.add("Login obrigatório");
        }

        if(senha == null ||
                senha.trim().isEmpty()){

            erros.add("Senha obrigatória");
        }

        if(!erros.isEmpty()){

            response.setStatus(400);

            response.getWriter().write(
                    new Gson().toJson(erros)
            );

            return;
        }

        Usuario usuario =
                usuarioDAO.buscarPorLogin(
                        userName
                );

        if(usuario == null){

            response.setStatus(401);

            response.getWriter().write(
                    "{\"erro\":\"Usuário não encontrado\"}"
            );

            return;
        }

        if(!usuario.getSenha().equals(senha)){

            response.setStatus(401);

            response.getWriter().write(
                    "{\"erro\":\"Senha inválida\"}"
            );

            return;
        }

        HttpSession session =
                request.getSession();

        session.setAttribute(
                "usuarioLogado",
                usuario
        );

        response.getWriter().write(
                "{\"sucesso\":true}"
        );
    }
}