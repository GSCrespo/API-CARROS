package br.edu.ifsp.dao;

import br.edu.ifsp.model.Carro;
import com.google.gson.Gson;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CarroJSONDAO implements CarroDAO {

    private String path;
    private int proxId;

    public CarroJSONDAO(String f){
        this.path = f + "carro.json";
        this.proxId = ultimoId();
    }

    @Override
    public Carro inserir(Carro carro) {

        try{

            checkFile(path);

            carro.setId(getProxId());

            FileWriter fw = new FileWriter(path,true);

            PrintWriter pw = new PrintWriter(fw);

            Gson gson = new Gson();

            pw.println(gson.toJson(carro));

            pw.close();
            fw.close();

            return carro;

        }catch(IOException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Carro> listar() {

        List<Carro> lista =
                new ArrayList<>();

        try{

            checkFile(path);

            FileReader fr =
                    new FileReader(path);

            BufferedReader reader =
                    new BufferedReader(fr);

            String linha;

            Gson gson =
                    new Gson();

            while((linha = reader.readLine()) != null){

                Carro carro = gson.fromJson(linha,Carro.class);

                lista.add(carro);
            }

            reader.close();
            fr.close();

        }catch(IOException e){
            throw new RuntimeException(e);
        }

        return lista;
    }

    @Override
    public Carro buscarPorId(int id) {

        List<Carro> lista =
                listar();

        for(Carro c : lista){

            if(c.getId() == id){
                return c;
            }
        }

        return null;
    }

    @Override
    public Carro atualizar(Carro carro) {

        List<Carro> lista =
                listar();

        for(int i=0; i<lista.size(); i++){

            if(lista.get(i).getId()
                    == carro.getId()){

                lista.set(i, carro);

                salvarLista(lista);

                return carro;
            }
        }

        return null;
    }

    @Override
    public boolean remover(int id) {

        List<Carro> lista = listar();
        Carro carroRemover = null;

        for(Carro c : lista){

            if(c.getId() == id){
                carroRemover = c;
                break;
            }
        }

        if(carroRemover != null){
            lista.remove(carroRemover);
            salvarLista(lista);

            return true;
        }

        return false;
    }

    private void salvarLista(
            List<Carro> lista){

        try{

            FileWriter fw = new FileWriter(path,false);
            PrintWriter pw = new PrintWriter(fw);
            Gson gson = new Gson();

            for(Carro carro : lista){

                pw.println(
                        gson.toJson(carro)
                );
            }

            pw.close();
            fw.close();

        }catch(IOException e){
            throw new RuntimeException(e);
        }
    }

    private int ultimoId(){

        List<Carro> lista = listar();

        return !lista.isEmpty()? lista.get(lista.size()-1).getId() : 0;
    }

    private int getProxId(){
        return ++this.proxId;
    }

    private void checkFile(String f){

        File file =
                new File(f);

        if(!file.exists()){

            try{
                file.createNewFile();
            }catch(IOException e){
                throw new RuntimeException(e);
            }
        }
    }
}