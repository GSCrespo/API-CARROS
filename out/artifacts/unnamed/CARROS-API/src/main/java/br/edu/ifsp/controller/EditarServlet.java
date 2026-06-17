package br.edu.ifsp.controller;

import br.edu.ifsp.dao.UsuarioDAO;
import br.edu.ifsp.dao.UsuarioJSONDAO;
import br.edu.ifsp.model.Usuario;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "EditarServlet", value = "/editar")
public class EditarServlet extends HttpServlet {

    private final Gson gson = new Gson();

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");

        String username = null;
        String senha = null;
        Boolean admin = false;

        String contentType = request.getContentType();

        if(contentType != null &&
                contentType.contains("application/json")){

            StringBuilder sb = new StringBuilder();

            BufferedReader br = request.getReader();

            String linha;

            while((linha = br.readLine()) != null){
                sb.append(linha);
            }

            Usuario usuario =
                    gson.fromJson(sb.toString(),
                            Usuario.class);

            username = usuario.getUserName();
            senha = usuario.getSenha();
            admin = usuario.getIsAdmin();

        }else{

            username = request.getParameter("username");
            senha = request.getParameter("senha");

            String tipo =
                    request.getParameter("tipo");

            admin =
                    "ADMIN".equals(tipo);
        }

        List<String> listaMensagens =
                new ArrayList<>();

        if(username == null ||
                username.isEmpty()){

            listaMensagens.add(
                    "O campo username deve ser preenchido"
            );
        }

        if(senha == null ||
                senha.length() < 5){

            listaMensagens.add(
                    "A senha deve possuir pelo menos 5 caracteres"
            );
        }

        UsuarioDAO dao =
                new UsuarioJSONDAO(
                        getServletContext()
                                .getRealPath("/")
                );

        if(username != null &&
                dao.buscarPorLogin(username)
                        != null){

            listaMensagens.add(
                    "Usuário já cadastrado"
            );
        }

        Map<String,Object> mensagem =
                new HashMap<>();

        if(!listaMensagens.isEmpty()){

            response.setStatus(
                    HttpServletResponse
                            .SC_BAD_REQUEST
            );

            mensagem.put(
                    "mensagem",
                    "Houve um problema"
            );

            mensagem.put(
                    "problemas",
                    listaMensagens
            );

        }else{

            dao.inserir(
                    username,
                    senha,
                    admin
            );

            response.setStatus(
                    HttpServletResponse
                            .SC_OK
            );

            mensagem.put(
                    "mensagem",
                    "Usuário cadastrado com sucesso"
            );
        }

        response.setContentType(
                "application/json"
        );

        response.setCharacterEncoding(
                "UTF-8"
        );

        PrintWriter pw =
                response.getWriter();

        pw.print(
                gson.toJson(mensagem)
        );
    }
}