package br.edu.ifsp.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import br.edu.ifsp.dao.CarroDAO;
import br.edu.ifsp.dao.CarroJSONDAO;
import br.edu.ifsp.model.Carro;


@WebServlet("/listar")
public class ListarServlet extends HttpServlet {

        private final Gson gson = new Gson();

        @Override
        public void init() throws ServletException {
            super.init();

            CarroDAO dao =
                new CarroJSONDAO(
                    getServletContext()
                    .getRealPath("/")
                );

            getServletContext()
                .setAttribute("dao",dao);
        }

        @Override
        protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

            CarroDAO dao = (CarroDAO)getServletContext().getAttribute("dao");

            List<Carro> lista = dao.listar();

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.addHeader("Access-Control-Allow-Origin","*");

            PrintWriter pw =response.getWriter();

            pw.print(gson.toJson(lista));
        }
}


