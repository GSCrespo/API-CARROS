package br.edu.ifsp.model;

public class Usuario{

    private String userName;
    private String tipo;
    private String senha;

    public Usuario(String userName, String senha, String tipo){
        this.userName = userName;
        this.senha = senha;
        this.tipo = tipo;
    }

    //GETTERS
    public String getUserName() {
        return userName;
    }

    public String getTipo() {
        return tipo;
    }

    public String getSenha() {
        return senha;
    }

    //SETTERS
    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

}
