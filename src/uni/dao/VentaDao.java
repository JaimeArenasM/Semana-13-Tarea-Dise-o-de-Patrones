
package uni.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import uni.database.AccesoDB;
import uni.entity.DetalleTo;
import uni.entity.VentaTo;
import uni.service.IProceso;

public class VentaDao implements IProceso<VentaTo>{
     //variables
    Connection cn = null;
    PreparedStatement ps = null;
    CallableStatement csv, csd, csp = null;
    ResultSet rs = null;
    String sql = "";
    
    @Override
    public void Procesar(VentaTo o) throws Exception {
       int nro;
        try {
            cn = AccesoDB.getConnection();
            // activa la transaccion
            cn.setAutoCommit(false);
            nro = numeroFactura();//genera numero de venta
            o.setIdventa(nro);
            sql = "{call sp_Registra_Venta(?,?,?,?,?,?)}";
            csv = cn.prepareCall(sql);
            //preparar los valores a pasar al sp
            csv.setInt(1, o.getIdventa());
            csv.setString(2, o.getIdcliente());
            csv.setString(3, o.getIdmpleaado());
            csv.setString(4, o.getTipodoc());
            csv.setString(5, o.getNrodoca());
            csv.setDouble(6, o.getTotal());
            //ejecutar sp_Registrar_Venta
            csv.executeUpdate();
            sql = "{call sp_Registra_Detalle(?,?,?,?,?)}";
            csd = cn.prepareCall(sql);
            sql = "{call sp_Actualiza_Stock(?,?)}";
            csp = cn.prepareCall(sql);
            for (DetalleTo d : o.getLista()) {
                //preparar los valores para sp de detalle
                csd.setInt(1, o.getIdventa());
                csd.setString(2, d.getIdproducto());
                csd.setDouble(3, d.getPrecio());
                csd.setInt(4, d.getCantidad());
                csd.setDouble(5, d.getTotal());
                csd.executeUpdate();//ejecuta sp de detalle
                //preparar los valores para sp de producto
                csp.setInt(2, d.getCantidad());
                csp.setString(1, d.getIdproducto());               
                csp.executeUpdate();//ejecuta sp para actualizar stock de producto
            }
            csv.close();
            csd.close();
            csp.close();
            cn.commit();//confirma la transaccion
        } catch (SQLException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            try {
                cn.rollback();//deshace la transaccion
            } catch (SQLException e1) {
            }
            throw e;
        } finally {
            cn.close();
        }
    }

    private int numeroFactura() throws SQLException {
        sql = "select valor from control where parametro='Ventas'";
        ps = cn.prepareStatement(sql);
        rs = ps.executeQuery();
        rs.next();
        int cont = rs.getInt(1);
        rs.close();
        sql = "update control set valor=valor+1 where parametro='Ventas'";
        ps = cn.prepareStatement(sql);
        ps.executeUpdate();
        ps.close();
        return cont;
    }
   
}
