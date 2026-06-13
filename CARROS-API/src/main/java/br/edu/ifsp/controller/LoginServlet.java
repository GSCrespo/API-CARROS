package br.edu.ifsp.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import br.edu.ifsp.model.Usuario;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String userName = request.getParameter("username");
        String senha = request.getParameter("senha");

        for (Usuario u : UsuarioServlet.listaUsuarios) {

            if (u.getUserName().equals(userName) && u.getSenha().equals(senha)) {

                HttpSession session = request.getSession();
                session.setAttribute("usuarioLogado", u);

                response.sendRedirect(request.getContextPath() + "/carro?action=home");
                return;
            }
        }

        request.setAttribute("erro", "Usuário ou senha inválidos");
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }
}