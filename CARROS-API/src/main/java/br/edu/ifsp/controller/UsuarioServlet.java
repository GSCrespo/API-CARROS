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

@WebServlet(name = "UsuarioServlet", value = "/usuario")
public class UsuarioServlet extends HttpServlet {

    public static final List<Usuario> listaUsuarios = new ArrayList<>();

    // ADMIN INICIAL
    static{
        listaUsuarios.add(new Usuario("admin", "admin", "ADMIN"));
    }

    //  LISTAR USUÁRIOS
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        Usuario logado = (Usuario) session.getAttribute("usuarioLogado");

        String action = request.getParameter("action");

        if (action == null) {
            action = "listar";
        }

        switch (action) {

            case "novo":

                request.getRequestDispatcher("/WEB-INF/cadastroUsuario.jsp")
                        .forward(request, response);
                break;

            case "listar":

                request.setAttribute("usuarios", listaUsuarios);
                request.getRequestDispatcher("/WEB-INF/listaUsuarios.jsp")
                        .forward(request, response);
                break;

            default:
                response.sendRedirect("index.html");
        }
    }

    //  CADASTRAR USUÁRIO
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Usuario logado = (Usuario) session.getAttribute("usuarioLogado");

        String userName = request.getParameter("username");
        String senha = request.getParameter("senha");
        String tipo;

        if(logado == null){
            tipo = "COMUM"; //USER
        }else {
            tipo = request.getParameter("tipo"); // ADMIN
        }
        List<String> erros = new ArrayList<>();

        //  VALIDAÇÕES
        if (userName == null || userName.isEmpty()) {
            erros.add("Preencha o username");
        }

        if (senha == null || senha.length() < 5) {
            erros.add("Senha deve conter no mínimo 5 caracteres");
        }

        if (tipo == null || tipo.isEmpty()) {
            erros.add("Selecione o tipo de usuário");
        }

        //  Verificar se usuário já existe
        for (Usuario u : listaUsuarios) {
            if (u.getUserName().equals(userName)) {
                erros.add("Usuário já existe");
                break;
            }
        }

        //  Se tiver erro
        if (!erros.isEmpty()) {
            request.setAttribute("erros", erros);
            request.getRequestDispatcher("/WEB-INF/cadastroUsuario.jsp").forward(request, response);
            return;
        }

        //  Criar usuário
        Usuario novoUsuario = new Usuario(userName, senha, tipo);

        listaUsuarios.add(novoUsuario);

        if(logado != null && !"COMUM".equals(logado.getTipo())) { /*caso seja admin que cadastrou um usuario ele volta para o index,
                                                                 caso seja o proprio usuario a se cadastrar ele vai para p login direto */
            response.sendRedirect("carro?action=home");
        } else{
            response.sendRedirect("login.jsp");
        }
    }


}