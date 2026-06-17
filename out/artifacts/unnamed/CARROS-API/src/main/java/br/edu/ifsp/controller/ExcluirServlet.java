package br.edu.ifsp.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import br.edu.ifsp.dao.CarroDAO;

@WebServlet("/excluir")
public class ExcluirServlet extends HttpServlet {

    private final Gson gson = new Gson();

    @Override
    protected void doOptions(HttpServletRequest req,
                             HttpServletResponse response)
            throws IOException {

        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");
        response.setHeader("Access-Control-Allow-Methods",
                "POST, GET, OPTIONS, DELETE");

        response.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response)
            throws IOException {

        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");
        response.setHeader("Access-Control-Allow-Methods",
                "POST, GET, OPTIONS, DELETE");

        StringBuilder sb =
                new StringBuilder();

        BufferedReader br =
                request.getReader();

        String linha;

        while((linha = br.readLine()) != null){
            sb.append(linha);
        }

        Map<?,?> dados =
                gson.fromJson(
                        sb.toString(),
                        Map.class
                );

        int id =
                ((Double)dados.get("id"))
                        .intValue();

        CarroDAO dao =
                (CarroDAO)getServletContext()
                        .getAttribute("dao");

        boolean removido =
                dao.remover(id);

        Map<String,Object> mensagem =
                new HashMap<>();

        if(removido){

            response.setStatus(
                    HttpServletResponse.SC_OK
            );

            mensagem.put(
                    "mensagem",
                    "Carro removido com sucesso"
            );

        }else{

            response.setStatus(
                    HttpServletResponse.SC_NOT_FOUND
            );

            mensagem.put(
                    "mensagem",
                    "Carro não encontrado"
            );
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