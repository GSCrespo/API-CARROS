package br.edu.ifsp.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import br.edu.ifsp.dao.CarroDAO;
import br.edu.ifsp.model.Carro;

@WebServlet("/detalhes")
public class DetalhesServlet extends HttpServlet {

    private final Gson gson = new Gson();

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        response.addHeader("Access-Control-Allow-Origin", "*");

        String idParam =
                request.getParameter("id");

        if(idParam == null){

            response.setStatus(
                    HttpServletResponse.SC_BAD_REQUEST
            );

            return;
        }

        int id =
                Integer.parseInt(idParam);

        CarroDAO dao =
                (CarroDAO)
                getServletContext()
                        .getAttribute("dao");

        Carro carro =
                dao.buscarPorId(id);

        if(carro == null){

            response.setStatus(
                    HttpServletResponse.SC_NOT_FOUND
            );

            return;
        }

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        PrintWriter pw =
                response.getWriter();

        pw.print(
                gson.toJson(carro)
        );
    }
}