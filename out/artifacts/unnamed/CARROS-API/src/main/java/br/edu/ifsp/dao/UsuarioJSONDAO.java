package br.edu.ifsp.dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

import br.edu.ifsp.model.Usuario;

public class UsuarioJSONDAO implements UsuarioDAO{

    private String path = null;
    private int proxId;


    public UsuarioJSONDAO(String f){
        this.path = f + "usuario.json";
        this.proxId = this.ultimoId();
    }
    
    
    @Override
    public Usuario inserir(String login, String senha, Boolean admin) {

        if(buscarPorLogin(login) != null){
            return null;
        }

        Usuario u = null;
        try {
            checkFile(path);
            FileWriter fw = new FileWriter(path,true);
            PrintWriter pw = new PrintWriter(fw);
            u = new Usuario(this.getProxId(),login,senha,admin);
            Gson gson = new Gson();
            System.out.println(path);
            System.out.println(gson.toJson(u));
            pw.println(gson.toJson(u));
            pw.close();
            fw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return u;
    }


    @Override
    public List<Usuario> listar() {

        List<Usuario> lista = new ArrayList<>();

        try {

            checkFile(path);

            FileReader fr = new FileReader(path);
            BufferedReader reader = new BufferedReader(fr);

            String linha;

            Gson gson = new Gson();

            while((linha = reader.readLine()) != null){

                Usuario u =
                        gson.fromJson(linha, Usuario.class);

                lista.add(u);
            }

            reader.close();
            fr.close();

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return lista;
    }

        @Override
    public Usuario buscarPorLogin(String login) {

        List<Usuario> usuarios = listar();

        for(Usuario u : usuarios){

            if(u.getUserName().equals(login)){
                return u;
            }
        }

        return null;
    }
    
    private int ultimoId(){
        List<Usuario> lista = this.listar();
        return !lista.isEmpty() ? lista.get(lista.size()-1).getId() : 0;
    }

    private int getProxId(){
        return ++this.proxId;
    }

    private void checkFile(String f){
        File file = new File(path);
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
