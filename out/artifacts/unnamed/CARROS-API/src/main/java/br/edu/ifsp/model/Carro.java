package br.edu.ifsp.model;

import java.io.Serializable;

public class Carro implements Serializable{

    private int id;
    private String marca;
    private String modelo;
    private int ano;
    private String descricao;
    private String cor;
    private String combustivel;
    private double quilometragem;
    private String transmissao;
    private double valor;
    private int totalAvaliacao;
    private double somaAvaliacao;

    public Carro() {
        
    }

    //construtor completo
    public Carro(String marca, String modelo, int ano, String descricao, String cor,
                 String combustivel, double quilometragem, String transmissao,
                 double valor) {
        this();
        this.marca = marca;
        this.modelo = modelo;
        this.ano = ano;
        this.descricao = descricao;
        this.cor = cor;
        this.combustivel = combustivel;
        this.quilometragem = quilometragem;
        this.transmissao = transmissao;
        this.valor = valor;
    }

    public double getMediaAvaliacao() {
        if (totalAvaliacao == 0) return 0;
        return somaAvaliacao / totalAvaliacao;
    }


    public int getId() {
        return id;
    }

    public String getMarca() {
        return marca;
    }

    public String getModelo() {
        return modelo;
    }

    public int getAno() {
        return ano;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getCor() {
        return cor;
    }

    public String getCombustivel() {
        return combustivel;
    }

    public double getQuilometragem() {
        return quilometragem;
    }

    public String getTransmissao() {
        return transmissao;
    }

    public double getValor() {
        return valor;
    }

    public int getTotalAvaliacao() {
        return totalAvaliacao;
    }

    public double getSomaAvaliacao() {
        return somaAvaliacao;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public void setCombustivel(String combustivel) {
        this.combustivel = combustivel;
    }

    public void setQuilometragem(double quilometragem) {
        this.quilometragem = quilometragem;
    }

    public void setTransmissao(String transmissao) {
        this.transmissao = transmissao;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public void setTotalAvaliacao(int totalAvaliacao) {
        this.totalAvaliacao = totalAvaliacao;
    }

    public void setSomaAvaliacao(double somaAvaliacao) {
        this.somaAvaliacao = somaAvaliacao;
    }

}

