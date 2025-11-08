package uni.controller;

import uni.dao.VentaDao;
import uni.entity.VentaTo;

public class VentaController {

    // variable
    VentaDao dao;
    //constructor

    public VentaController() {
        dao = new VentaDao();
    }

    public void registrarVenta(VentaTo v) throws Exception {
        dao.Procesar(v);
    }

}
