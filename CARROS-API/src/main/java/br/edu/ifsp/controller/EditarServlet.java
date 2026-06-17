package br.edu.ifsp.controller;

import br.edu.ifsp.dao.CarroDAO;
import br.edu.ifsp.model.Carro;
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

@WebServlet("/editar")
public class EditarServlet extends HttpServlet {

    private final Gson gson = new Gson();

    @Override
    protected void doOptions(HttpServletRequest req,
                             HttpServletResponse response)
            throws ServletException, IOException {

        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");
        response.setHeader("Access-Control-Allow-Methods",
                "POST, GET, OPTIONS, DELETE");

        response.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods",
                "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Allow-Headers",
                "Content-Type");

        StringBuilder sb = new StringBuilder();

        BufferedReader br = request.getReader();

        String linha;

        while ((linha = br.readLine()) != null) {
            sb.append(linha);
        }

        Carro carro = gson.fromJson(
                sb.toString(),
                Carro.class
        );

        List<String> erros = new ArrayList<>();

        if (carro.getId() <= 0) {
            erros.add("Id inválido");
        }

        if (carro.getMarca() == null ||
                carro.getMarca().isEmpty()) {
            erros.add("Marca obrigatória");
        }

        if (carro.getModelo() == null ||
                carro.getModelo().isEmpty()) {
            erros.add("Modelo obrigatório");
        }

        if (carro.getAno() <= 0) {
            erros.add("Ano inválido");
        }

        if (carro.getDescricao() == null ||
                carro.getDescricao().isEmpty()) {
            erros.add("Descrição obrigatória");
        }

        Map<String, Object> mensagem =
                new HashMap<>();

        if (!erros.isEmpty()) {

            response.setStatus(
                    HttpServletResponse.SC_BAD_REQUEST);

            mensagem.put(
                    "mensagem",
                    "Houve um problema"
            );

            mensagem.put(
                    "problemas",
                    erros
            );

        } else {

            CarroDAO dao =
                    (CarroDAO) getServletContext()
                            .getAttribute("dao");

            Carro atualizado =
                    dao.atualizar(carro);

            if (atualizado == null) {

                response.setStatus(
                        HttpServletResponse.SC_NOT_FOUND
                );

                mensagem.put(
                        "mensagem",
                        "Carro não encontrado"
                );

            } else {

                response.setStatus(
                        HttpServletResponse.SC_OK
                );

                mensagem.put(
                        "mensagem",
                        "Carro atualizado com sucesso"
                );
            }
        }

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        PrintWriter pw =
                response.getWriter();

        pw.print(
                gson.toJson(mensagem)
        );
    }
}