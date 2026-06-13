package br.edu.ifsp.dao;

import br.edu.ifsp.model.Carro;
import com.google.gson.Gson;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class CarroJSONDAO implements CarroDAO{

    private final String arquivo;
    private final Gson gson;

    private String path;
    private int proxId;

    public CarroJSONDAO(String path) {
        this.path = path + "carros.json";
        this.proxId = ultimoId();
    }

    @Override
    public Carro inserir(Carro carro) {

        try {

            checkFile(path);

            carro.setId(getProxId());

            FileWriter fw = new FileWriter(path, true);
            PrintWriter pw = new PrintWriter(fw);

            Gson gson = new Gson();

            pw.println(gson.toJson(carro));

            pw.close();
            fw.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return carro;
    }

    @Override
    public List<Carro> listar() {

        List<Carro> lista = new ArrayList<>();

        try {

            checkFile(path);

            FileReader fr = new FileReader(path);
            BufferedReader reader = new BufferedReader(fr);

            String linha;

            Gson gson = new Gson();

            while ((linha = reader.readLine()) != null) {

                Carro carro =
                        gson.fromJson(linha, Carro.class);

                lista.add(carro);
            }

            reader.close();
            fr.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return lista;
    }


    @Override
    public Carro buscarPorId(int id) {

        List<Carro> lista = listar();

        for (Carro c : lista) {

            if (c.getId() == id) {
                return c;
            }
        }

        return null;
    }

    private int ultimoId() {

        List<Carro> lista = listar();
    
        return !lista.isEmpty() ? lista.get(lista.size()-1).getId() : 0;
    }

    private int getProxId() {
        return ++proxId;
    }

    private void checkFile(String path) {

        File file = new File(path);
    
        if (!file.exists()) {
    
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
    
        }
    }


    @Override
    public Carro atualizar(Carro carroAtualizado) {

        List<Carro> lista = listar();

        for (Carro c : lista) {

            if (c.getId() == carroAtualizado.getId()) {

                c.setMarca(carroAtualizado.getMarca());
                c.setModelo(carroAtualizado.getModelo());
                c.setAno(carroAtualizado.getAno());
                c.setDescricao(carroAtualizado.getDescricao());
                c.setCor(carroAtualizado.getCor());
                c.setCombustivel(carroAtualizado.getCombustivel());
                c.setQuilometragem(carroAtualizado.getQuilometragem());
                c.setTransmissao(carroAtualizado.getTransmissao());
                c.setValor(carroAtualizado.getValor());

                salvarLista(lista);

                return c;
            }
        }

        return null;
    }

    @Override
    public boolean remover(int id) {

        List<Carro> lista = listar();

        Carro remover = null;

        for (Carro c : lista) {

            if (c.getId() == id) {
                remover = c;
                break;
            }
        }

        if (remover != null) {

            lista.remove(remover);

            salvarLista(lista);

            return true;
        }

        return false;
    }


    private void salvarLista(List<Carro> lista) {

        try {
    
            FileWriter fw = new FileWriter(path);
            PrintWriter pw = new PrintWriter(fw);
    
            Gson gson = new Gson();
    
            for (Carro carro : lista) {
    
                pw.println(gson.toJson(carro));
            }
    
            pw.close();
            fw.close();
    
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
