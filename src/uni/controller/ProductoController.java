package uni.controller;

import java.util.List;
import uni.dao.ProductoDao;
import uni.entity.ProductoTo;

public class ProductoController {
    //variables

    ProductoDao dao;
    // constructor

    public ProductoController() {
        dao = new ProductoDao();
    }

    // metodos
    public List<ProductoTo> ProductoListar() throws Exception {
        return dao.readAll();
    }

    public List<ProductoTo> ProductoListar(String nombre) throws Exception {
        return dao.readAll(nombre);
    }

    public List<ProductoTo> ProductoListar(int id) throws Exception {
        return dao.readAll(id);
    }

    public ProductoTo ProductoBuscar(Object o) throws Exception {
        return dao.find(o);
    }    
   
     public ProductoTo ProductoPorNombre(String o) throws Exception {
        return dao.find1(o);
    }

    public void ProductoAdicionar(ProductoTo o) throws Exception {
        dao.create(o);
    }

    public void ProductoActualizar(ProductoTo o) throws Exception {
        dao.update(o);
    }

    public void ProductoEliminar(ProductoTo o) throws Exception {
        dao.delete(o);
    }

}
