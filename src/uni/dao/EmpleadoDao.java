package uni.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import uni.database.AccesoDB;
import uni.entity.EmpleadoTo;
import uni.service.ICrudDao;

public class EmpleadoDao implements ICrudDao<EmpleadoTo> {

    //variables
    Connection cn = null;
    CallableStatement cs = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    String sp = "";

    @Override
    public void create(EmpleadoTo o) throws Exception {
        try {
            cn = AccesoDB.getConnection();
            //activar el inicio de la transaccion
            cn.setAutoCommit(false);
            String cod = generaCodigo();
            o.setIdempleado(cod);
            sp = "{call sp_Empleado_Adicionar(?,?,?,?,?,?)}";
            cs = cn.prepareCall(sp);
            cs.setString(1, o.getIdempleado());
            cs.setString(2, o.getNombre());
            cs.setString(3, o.getApellidos());
            cs.setString(4, o.getEmail());
            cs.setString(5, o.getUsuario());
            cs.setString(6, o.getClave());
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
    public void update(EmpleadoTo o) throws Exception {
        try {
            cn = AccesoDB.getConnection();
            //activar el inicio de la transaccion
            cn.setAutoCommit(false);
            sp = "{call sp_Empleado_Actualizar(?,?,?,?,?,?)}";
            cs = cn.prepareCall(sp);
            cs.setString(1, o.getIdempleado());
            cs.setString(2, o.getNombre());
            cs.setString(3, o.getApellidos());
            cs.setString(4, o.getEmail());
            cs.setString(5, o.getUsuario());
            cs.setString(6, o.getClave());
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
    public void delete(EmpleadoTo o) throws Exception {
        try {
            cn = AccesoDB.getConnection();
            //activar el inicio de la transaccion
            cn.setAutoCommit(false);
            sp = "{call sp_Empleado_Eliminar(?)}";
            cs = cn.prepareCall(sp);
            cs.setString(1, o.getIdempleado());
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
    public EmpleadoTo find(Object o) throws Exception {
        return null;
    }

    @Override
    public List<EmpleadoTo> readAll() throws Exception {
        List<EmpleadoTo> lista = new ArrayList<>();
        try {
            cn = AccesoDB.getConnection();
            String sql = "select * from empleados";
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
        String sql = "select valor from control where parametro='Empleados'";
        ps = cn.prepareStatement(sql);
        rs = ps.executeQuery();
        rs.next();
        int cont = rs.getInt(1);
        rs.close();
        sql = "update control set valor=valor+1 where parametro='Empleados'";
        ps = cn.prepareStatement(sql);
        ps.executeUpdate();
        ps.close();
        String cod = "";
        if (cont < 10) {
            cod = "E000" + cont;
        } else {
            cod = "E00" + cont;
        }
        return cod;
    }

    private List<EmpleadoTo> cargaLista(ResultSet rs) throws SQLException {
        List<EmpleadoTo> aux = new ArrayList<>();
        while (rs.next()) {
            EmpleadoTo em = new EmpleadoTo();
            em.setIdempleado(rs.getString(1));
            em.setNombre(rs.getString(2));
            em.setApellidos(rs.getString(3));
            em.setEmail(rs.getString(4));
            aux.add(em);
        }
        rs.close();
        return aux;
    }
    
    public String readAll1(String nombre) throws Exception {
        String codigo;
        try {
            cn = AccesoDB.getConnection();
            sp = "select idempleado from empleados where apellidos=?";
            ps = cn.prepareStatement(sp);
            ps.setString(1, nombre);
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
