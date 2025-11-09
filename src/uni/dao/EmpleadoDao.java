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

    private Connection cn = null;
    private CallableStatement cs = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;
    private String sp = "";

    @Override
    public void create(EmpleadoTo o) throws Exception {
        try {
            cn = AccesoDB.getConnection();
            cn.setAutoCommit(false);
            String cod = generaCodigo();
            o.setIdempleado(cod);
            sp = "{call sp_insertar_empleado(?,?,?,?,?,?)}";
            cs = cn.prepareCall(sp);
            cs.setString(1, o.getIdempleado());
            cs.setString(2, o.getNombre());
            cs.setString(3, o.getApellidos());
            cs.setString(4, o.getEmail());
            cs.setString(5, o.getUsuario());
            cs.setString(6, o.getClave());
            cs.executeUpdate();
            cn.commit();
        } catch (Exception e) {
            if (cn != null) cn.rollback();
            throw e;
        } finally {
            cerrarRecursos();
        }
    }

    @Override
    public void update(EmpleadoTo o) throws Exception {
        try {
            cn = AccesoDB.getConnection();
            cn.setAutoCommit(false);
            sp = "{call sp_actualizar_empleado(?,?,?,?,?,?)}";
            cs = cn.prepareCall(sp);
            cs.setString(1, o.getIdempleado());
            cs.setString(2, o.getNombre());
            cs.setString(3, o.getApellidos());
            cs.setString(4, o.getEmail());
            cs.setString(5, o.getUsuario());
            cs.setString(6, o.getClave());
            cs.executeUpdate();
            cn.commit();
        } catch (Exception e) {
            if (cn != null) cn.rollback();
            throw e;
        } finally {
            cerrarRecursos();
        }
    }

    @Override
    public void delete(EmpleadoTo o) throws Exception {
        try {
            cn = AccesoDB.getConnection();
            cn.setAutoCommit(false);
            sp = "{call sp_eliminar_empleado(?)}";
            cs = cn.prepareCall(sp);
            cs.setString(1, o.getIdempleado());
            cs.executeUpdate();
            cn.commit();
        } catch (Exception e) {
            if (cn != null) cn.rollback();
            throw e;
        } finally {
            cerrarRecursos();
        }
    }

    @Override
    public EmpleadoTo find(Object o) throws Exception {
        EmpleadoTo emp = null;
        try {
            cn = AccesoDB.getConnection();
            sp = "{call sp_buscar_empleado(?)}";
            cs = cn.prepareCall(sp);
            cs.setString(1, o.toString());
            rs = cs.executeQuery();
            if (rs.next()) {
                emp = new EmpleadoTo();
                emp.setIdempleado(rs.getString("idempleado"));
                emp.setNombre(rs.getString("nombre"));
                emp.setApellidos(rs.getString("apellidos"));
                emp.setEmail(rs.getString("email"));
                emp.setUsuario(rs.getString("usuario"));
                emp.setClave(rs.getString("clave"));
            }
        } catch (Exception e) {
            throw e;
        } finally {
            cerrarRecursos();
        }
        return emp;
    }

    @Override
    public List<EmpleadoTo> readAll() throws Exception {
        List<EmpleadoTo> lista = new ArrayList<>();
        try {
            cn = AccesoDB.getConnection();
            sp = "{call sp_listar_empleados}";
            cs = cn.prepareCall(sp);
            rs = cs.executeQuery();
            while (rs.next()) {
                EmpleadoTo e = new EmpleadoTo();
                e.setIdempleado(rs.getString("idempleado"));
                e.setNombre(rs.getString("nombre"));
                e.setApellidos(rs.getString("apellidos"));
                e.setEmail(rs.getString("email"));
                e.setUsuario(rs.getString("usuario"));
                e.setClave(rs.getString("clave"));
                lista.add(e);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            cerrarRecursos();
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

    private void cerrarRecursos() throws SQLException {
        if (rs != null) rs.close();
        if (cs != null) cs.close();
        if (ps != null) ps.close();
        if (cn != null) cn.close();
    }
}
