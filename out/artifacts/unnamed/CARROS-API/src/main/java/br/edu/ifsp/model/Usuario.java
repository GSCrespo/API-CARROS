package br.edu.ifsp.model;

import java.io.Serializable;

public class Usuario implements Serializable{

    private int id;
    private String userName;
    private String senha;
    private Boolean isAdmin;

    public Usuario(){
        this.isAdmin = false;
    }

    public Usuario(String userName, String senha,Boolean admin){
        this();
        this.userName = userName;
        this.senha = senha;
        isAdmin = admin;
    }

    public Usuario(int id,String userName, String senha, Boolean admin){
        this.id = id;
        this.userName = userName;
        this.senha = senha;
        this.isAdmin = admin;
    }

    //GETTERS
    public String getUserName() {
        return userName;
    }

    public Boolean getIsAdmin() {
        return isAdmin;
    }

    public String getSenha() {
        return senha;
    }
    public int getId() {
        return id;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setIsAdmin(Boolean admin) {
        this.isAdmin = admin;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    @Override
    public String toString(){
        return this.userName + ";" + this.isAdmin+";"+this.id;
    }

}
