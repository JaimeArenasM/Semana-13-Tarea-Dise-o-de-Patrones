package uni.controller;

import java.util.List;
import uni.dao.ProveedorDao;
import uni.entity.ProveedorTo;

public class ProveedorController {
    //variable

    ProveedorDao dao;
    //constructor

    public ProveedorController() {
        dao = new ProveedorDao();
    }

    // metodos de negocio
    public void ProveedorAdicionar(ProveedorTo x) throws Exception {
        dao.create(x);
    }

    public void ProveedorActualizar(ProveedorTo x) throws Exception {
        dao.update(x);
    }

    public void ProveedorEliminar(ProveedorTo x) throws Exception {
        dao.delete(x);
    }

    public List<ProveedorTo> ProveedorListar() throws Exception {
        return dao.readAll();
    }

    public ProveedorTo ProveedorBuscar(Object x) throws Exception {
        return dao.find(x);
    }
    
    public String CodigodeProveedor(String razonsocial) throws Exception{
        return dao.readAll1(razonsocial);
    }
       
}
