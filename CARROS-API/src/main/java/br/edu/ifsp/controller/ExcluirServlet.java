package br.edu.ifsp.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ExcluirServlet {

    protected void doPost(
        HttpServletRequest request,
        HttpServletResponse response)
        throws ServletException, IOException {

        request.setAttribute(
            "listaCarros",
            listaCarros
        );

        request.getRequestDispatcher(
            "/WEB-INF/listaCarros.jsp"
        ).forward(request,response);
    }
}
