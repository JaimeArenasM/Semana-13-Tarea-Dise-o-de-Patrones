package uni.controller;

import java.util.List;
import uni.dao.EmpleadoDao;
import uni.entity.EmpleadoTo;

public class EmpleadoController {
    //variable

    EmpleadoDao dao;
    //constructor

    public EmpleadoController() {
        dao = new EmpleadoDao();
    }

    // metodos de negocio
    public void EmpleadoAdicionar(EmpleadoTo x) throws Exception {
        dao.create(x);
    }

    public void EmpleadoActualizar(EmpleadoTo x) throws Exception {
        dao.update(x);
    }

    public void EmpleadoEliminar(EmpleadoTo x) throws Exception {
        dao.delete(x);
    }

    public List<EmpleadoTo> EmpleadoListar() throws Exception {
        return dao.readAll();
    }

    public EmpleadoTo EmpleadoBuscar(Object x) throws Exception {
        return dao.find(x);
    }
    
    public String CodigodeEmpleado(String nombre) throws Exception{
        return dao.readAll1(nombre);
    }
    
    public boolean EmpleadoValidar(String u, String p) throws Exception {
        return dao.valida(u, p);
    }
}
