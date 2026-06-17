package br.edu.ifsp.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import br.edu.ifsp.dao.UsuarioDAO;
import br.edu.ifsp.dao.UsuarioJSONDAO;
import br.edu.ifsp.model.Usuario;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private UsuarioDAO usuarioDAO;
    private final Gson gson = new Gson();

    @Override
    public void init() {

        String path = getServletContext().getRealPath("/");

        usuarioDAO = new UsuarioJSONDAO(path);
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String username = null;
        String senha = null;

        String contentType = request.getContentType();

        // Recebendo JSON
        if (contentType != null &&
                contentType.contains("application/json")) {

            StringBuilder sb = new StringBuilder();
            BufferedReader br = request.getReader();

            String linha;

            while ((linha = br.readLine()) != null) {
                sb.append(linha);
            }

            Map<?, ?> dados =
                    gson.fromJson(sb.toString(), Map.class);

            username = (String) dados.get("username");
            senha = (String) dados.get("senha");

        } else {

            username = request.getParameter("username");
            senha = request.getParameter("senha");
        }

        List<String> erros = new ArrayList<>();

        if (username == null || username.trim().isEmpty()) {
            erros.add("Usuário obrigatório");
        }

        if (senha == null || senha.trim().isEmpty()) {
            erros.add("Senha obrigatória");
        }

        if (!erros.isEmpty()) {

            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);

            Map<String, Object> resp = new HashMap<>();
            resp.put("mensagem", "Dados inválidos");
            resp.put("problemas", erros);

            response.getWriter().print(
                    gson.toJson(resp)
            );

            return;
        }

        Usuario usuario =
                usuarioDAO.buscarPorLogin(username);

        if (usuario == null) {

            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

            response.getWriter().print(
                    "{\"mensagem\":\"Usuário não encontrado\"}"
            );

            return;
        }

        if (!usuario.getSenha().equals(senha)) {

            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

            response.getWriter().print(
                    "{\"mensagem\":\"Senha inválida\"}"
            );

            return;
        }

        HttpSession session = request.getSession();

        session.setAttribute(
                "usuarioLogado",
                usuario
        );

        Map<String, Object> resp = new HashMap<>();

        resp.put("mensagem", "Login realizado com sucesso");
        resp.put("usuario", usuario);

        response.setStatus(HttpServletResponse.SC_OK);

        response.getWriter().print(
                gson.toJson(resp)
        );
    }
}