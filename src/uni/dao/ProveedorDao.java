package uni.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import uni.database.AccesoDB;
import uni.entity.ProveedorTo;
import uni.service.ICrudDao;

public class ProveedorDao implements ICrudDao<ProveedorTo> {

    //variables
    Connection cn = null;
    CallableStatement cs = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    String sp = "";

    @Override
    public void create(ProveedorTo o) throws Exception {
        try {
            cn = AccesoDB.getConnection();
            //activar el inicio de la transaccion
            cn.setAutoCommit(false);
            String cod = generaCodigo();
            //o.setIdempleado(cod);
            o.setIdproveedor(cod);
            sp = "{call USP_PROVEEDOR_INSERT(?,?,?,?,?)}";
            cs = cn.prepareCall(sp);
            
            cs.setString(1, o.getIdproveedor());
            cs.setString(2, o.getRazonsocial());
            cs.setString(3, o.getDireccion());
            cs.setString(4, o.getRuc());
            cs.setString(5, o.getTelefono());
            
            cs.executeUpdate();
            cs.close();
            cn.commit();//confirma que la transaccion se realizado ok
        } catch (SQLException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            try {
                cn.rollback();
            } catch (Exception e1) {
            }
            throw e;
        } finally {
            cn.close();
        }
    }

    @Override
    public void update(ProveedorTo o) throws Exception {
        try {
            cn = AccesoDB.getConnection();
            //activar el inicio de la transaccion
            cn.setAutoCommit(false);
            sp = "{call usp_proveedor_modificar(?,?,?,?,?)}";
            cs = cn.prepareCall(sp);
            
             cs.setString(1, o.getIdproveedor());
            cs.setString(2, o.getRazonsocial());
            cs.setString(3, o.getDireccion());
            cs.setString(4, o.getRuc());
            cs.setString(5, o.getTelefono());
            
            cs.executeUpdate();
            cs.close();
            cn.commit();//confirma que la transaccion se realizado ok
        } catch (SQLException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            try {
                cn.rollback();
            } catch (Exception e1) {
            }
            throw e;
        } finally {
            cn.close();
        }
    }

    @Override
    public void delete(ProveedorTo o) throws Exception {
        try {
            cn = AccesoDB.getConnection();
            //activar el inicio de la transaccion
            cn.setAutoCommit(false);
            sp = "{call usp_proveedor_eliminar(?)}";
            cs = cn.prepareCall(sp);
            
            cs.setString(1, o.getIdproveedor());
            
            cs.executeUpdate();
            cs.close();
            cn.commit();//confirma que la transaccion se realizado ok
        } catch (SQLException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            try {
                cn.rollback();
            } catch (Exception e1) {
            }
            throw e;
        } finally {
            cn.close();
        }
    }

    @Override
    public ProveedorTo find(Object o) throws Exception {
        return null;
    }

    @Override
    public List<ProveedorTo> readAll() throws Exception {
        List<ProveedorTo> lista = new ArrayList<>();
        try {
            cn = AccesoDB.getConnection();
            String sql = "select * from proveedores";
            ps = cn.prepareStatement(sql);
            rs = ps.executeQuery();
            lista = cargaLista(rs);
            rs.close();
            ps.close();
        } catch (SQLException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            throw e;
        } finally {
            cn.close();
        }
        return lista;
    }

    private String generaCodigo() throws SQLException {
        String sql = "select valor from control where parametro='Proveedores'";
        ps = cn.prepareStatement(sql);
        rs = ps.executeQuery();
        rs.next();
        int cont = rs.getInt(1);
        rs.close();
        sql = "update control set valor=valor+1 where parametro='Proveedores'";
        ps = cn.prepareStatement(sql);
        ps.executeUpdate();
        ps.close();
        String cod = "";
        if (cont < 10) {
            cod = "P000" + cont;
        } else {
            cod = "P00" + cont;
        }
        return cod;
    }

    private List<ProveedorTo> cargaLista(ResultSet rs) throws SQLException {
        List<ProveedorTo> aux = new ArrayList<>();
        while (rs.next()) {
            ProveedorTo pr = new ProveedorTo();
            pr.setIdproveedor(rs.getString(1));
            pr.setRazonsocial(rs.getString(2));
            pr.setDireccion(rs.getString(3));
            pr.setRuc(rs.getString(4));
            pr.setTelefono(rs.getString(5));
            aux.add(pr);
        }
        rs.close();
        return aux;
    }
    
    public String readAll1(String razonsocial) throws Exception {
        String codigo;
        try {
            cn = AccesoDB.getConnection();
            sp = "select idproveedor from proveedores where razonsocial=?";
            ps = cn.prepareStatement(sp);
            ps.setString(1, razonsocial);
            rs = ps.executeQuery();
            rs.next();
            codigo = rs.getString(1);
            rs.close();
            ps.close();
        } catch (SQLException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            throw e;
        } finally {
            cn.close();
        }
        return codigo;
    }
    
     public boolean valida(String usu, String pas) throws Exception {
        boolean sw=false;
        try {
            cn = AccesoDB.getConnection();
            sp = "select * from empleados where usuario=? and clave=?";
            ps = cn.prepareStatement(sp);
            ps.setString(1, usu);
            ps.setString(2, pas);
            rs = ps.executeQuery();
            sw = rs.next();
            rs.close();
            ps.close();
        } catch (Exception e) {
            throw e;
        } finally {
            cn.close();
        }
        return sw;//que puede ser verdadero o falso
    }
   
}
