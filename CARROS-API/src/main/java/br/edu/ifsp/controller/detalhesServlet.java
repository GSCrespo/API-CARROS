package br.edu.ifsp.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.edu.ifsp.model.Carro;


@WebServlet("/detalhes")
public class detalhesServlet extends HttpServlet {

    protected void doGet( HttpServletRequest request,
        HttpServletResponse response) throws ServletException, IOException{

        int id = Integer.parseInt(
        request.getParameter("id"));

        Carro carro = buscarPorId(id);

        request.setAttribute("carro", carro);

        request.getRequestDispatcher(
        "detalharCarro.jsp").forward(request,response);
    }
}
