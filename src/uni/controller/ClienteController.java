package uni.controller;

import java.util.List;
import uni.dao.ClienteDao;
import uni.entity.ClienteTo;

public class ClienteController {
    //variable

    ClienteDao dao;
    //constructor

    public ClienteController() {
        dao = new ClienteDao();
    }

    // metodos de negocio
    public List<ClienteTo> ClienteListar() throws Exception {
        return dao.readAll();
    }

    public String CodigodeCliente(String nombre) throws Exception{
        return dao.readAll1(nombre);
    }
}
