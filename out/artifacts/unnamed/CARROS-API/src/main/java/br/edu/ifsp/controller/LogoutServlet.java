package br.edu.ifsp.controller;

import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/Sair")
public class LogoutServlet extends HttpServlet {

     @Override
    protected void doPost(HttpServletRequest request,HttpServletResponse response)
            throws IOException {

        HttpSession session =
                request.getSession(false);

        if(session != null){
            session.invalidate();
        }

        response.setContentType(
                "application/json"
        );

        response.setCharacterEncoding(
                "UTF-8"
        );

        response.getWriter().write(
                "{\"sucesso\":true}"
        );
    }
}