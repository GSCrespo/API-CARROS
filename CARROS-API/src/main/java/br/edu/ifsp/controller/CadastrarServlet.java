package br.edu.ifsp.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import br.edu.ifsp.dao.CarroDAO;
import br.edu.ifsp.model.Carro;


@WebServlet("/cadastrar")
@MultipartConfig
public class CadastrarServlet extends HttpServlet {
    
    private final Gson gson = new Gson();


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // code
        throw new RuntimeException();
    }

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse response) throws ServletException, IOException {
        super.doOptions(req, response);
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setStatus(HttpServletResponse.SC_OK);
    }

     @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");


        String contentType = request.getContentType();
        String marca = null;
        String modelo = null;
        int ano = 0;
        String descricao = null;
        String cor = null;
        String combustivel = null;
        double quilometragem = 0;;
        String transmissao = null;
        double valor = 0;;
        int totalAvaliacao = 0;
        double somaAvaliacao = 0;
        if(contentType.contains("application/json")){
            StringBuilder sb = new StringBuilder();
            BufferedReader br = request.getReader();
            String linha;
            while( (linha = br.readLine()) != null) sb.append(linha);

            Carro c = gson.fromJson(sb.toString(), Carro.class);
            marca = c.getMarca();
            modelo = c.getModelo();
            ano = c.getAno();
            descricao = c.getDescricao();
            cor = c.getCor();
            combustivel = c.getCombustivel();
            quilometragem = c.getQuilometragem();
            transmissao = c.getTransmissao();
            valor = c.getValor();
            totalAvaliacao = c.getTotalAvaliacao();
            somaAvaliacao = c.getSomaAvaliacao();
            
        }List<String> listaMensagens = new ArrayList<>();

        if(marca == null || marca.isEmpty()){
            listaMensagens.add("Marca obrigatória");
        }
    
        if(modelo == null || modelo.isEmpty()){
            listaMensagens.add("Modelo obrigatório");
        }
    
        if(ano <= 0){
            listaMensagens.add("Ano inválido");
        }
    
        if(cor == null || cor.isEmpty()){
            listaMensagens.add("Cor obrigatória");
        }
    
        if(combustivel == null || combustivel.isEmpty()){
            listaMensagens.add("Combustível obrigatório");
        }
    
        if(transmissao == null || transmissao.isEmpty()){
            listaMensagens.add("Transmissão obrigatória");
        }
    
        if(valor <= 0){
            listaMensagens.add("Valor inválido");
        }
    
        if(descricao == null || descricao.isEmpty()){
            listaMensagens.add("Descrição obrigatória");
        }
    
        Map<String,Object> mensagem = new HashMap<>();
    
        if(!listaMensagens.isEmpty()){
    
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
    
            mensagem.put("mensagem","Houve um problema");
            mensagem.put("problemas",listaMensagens);
    
        }else{
    
            Carro carro = new Carro();
    
            carro.setMarca(marca);
            carro.setModelo(modelo);
            carro.setAno(ano);
            carro.setDescricao(descricao);
            carro.setCor(cor);
            carro.setCombustivel(combustivel);
            carro.setQuilometragem(quilometragem);
            carro.setTransmissao(transmissao);
            carro.setValor(valor);
    
            CarroDAO dao =
                    (CarroDAO) getServletContext()
                            .getAttribute("dao");
    
            dao.inserir(carro);
    
            response.setStatus(HttpServletResponse.SC_OK);
    
            mensagem.put(
                    "mensagem",
                    "Carro cadastrado com sucesso"
            );
        }
    
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
    
        PrintWriter pw = response.getWriter();
    
        pw.print(gson.toJson(mensagem));

    }

}
