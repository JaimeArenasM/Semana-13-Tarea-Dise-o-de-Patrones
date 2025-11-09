package uni.dao;

import java.sql.*;
import java.util.*;
import uni.database.AccesoDB;
import uni.entity.EmpleadoTo;
import uni.service.ICrudDao;

public class EmpleadoDao implements ICrudDao<EmpleadoTo> {

    // Variables de conexión y sentencias SQL
    private Connection cn = null;
    private CallableStatement cs = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;
    private String sp = ""; // guarda el nombre del procedimiento almacenado o consulta

    @Override
    public void create(EmpleadoTo o) throws Exception {
        // Crea un nuevo registro de empleado en la base de datos
        try {
            cn = AccesoDB.getConnection();
            cn.setAutoCommit(false); // desactiva el autocommit para control manual de transacciones

            // Genera un nuevo código único para el empleado
            String cod = generaCodigo();
            o.setIdempleado(cod);

            // Llama al procedimiento almacenado para insertar un empleado
            sp = "{call sp_insertar_empleado(?,?,?,?,?,?)}";
            cs = cn.prepareCall(sp);
            cs.setString(1, o.getIdempleado());
            cs.setString(2, o.getNombre());
            cs.setString(3, o.getApellidos());
            cs.setString(4, o.getEmail());
            cs.setString(5, o.getUsuario());
            cs.setString(6, o.getClave());
            cs.executeUpdate();

            cn.commit(); // confirma la transacción
        } catch (Exception e) {
            if (cn != null) cn.rollback(); // revierte cambios si hay error
            throw e;
        } finally {
            cerrarRecursos(); // libera recursos
        }
    }

    @Override
    public void update(EmpleadoTo o) throws Exception {
        // Actualiza los datos de un empleado existente
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
        // Elimina un empleado de la base de datos usando su ID
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
        // Busca un empleado según su ID y devuelve su información
        EmpleadoTo emp = null;
        try {
            cn = AccesoDB.getConnection();
            sp = "{call sp_buscar_empleado(?)}";
            cs = cn.prepareCall(sp);
            cs.setString(1, o.toString());
            rs = cs.executeQuery();

            // Si el registro existe, llena el objeto EmpleadoTo con los datos obtenidos
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
        // Obtiene la lista completa de empleados desde la base de datos
        List<EmpleadoTo> lista = new ArrayList<>();
        try {
            cn = AccesoDB.getConnection();
            sp = "{call sp_listar_empleados}";
            cs = cn.prepareCall(sp);
            rs = cs.executeQuery();

            // Recorre los resultados y crea una lista de objetos EmpleadoTo
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
        // Genera un nuevo código incremental para el empleado (E0001, E0002, etc.)
        String sql = "select valor from control where parametro='Empleados'";
        ps = cn.prepareStatement(sql);
        rs = ps.executeQuery();
        rs.next();
        int cont = rs.getInt(1);

        // Actualiza el contador en la tabla 'control'
        rs.close();
        sql = "update control set valor=valor+1 where parametro='Empleados'";
        ps = cn.prepareStatement(sql);
        ps.executeUpdate();
        ps.close();

        // Formatea el código con ceros a la izquierda
        String cod;
        if (cont < 10) {
            cod = "E000" + cont;
        } else {
            cod = "E00" + cont;
        }
        return cod;
    }

    private void cerrarRecursos() throws SQLException {
        // Cierra todos los recursos abiertos para evitar fugas de memoria
        if (rs != null) rs.close();
        if (cs != null) cs.close();
        if (ps != null) ps.close();
        if (cn != null) cn.close();
    }

    public String readAll1(String nombre) throws Exception {
        // Obtiene el ID del empleado según su apellido
        String codigo;
        try {
            cn = AccesoDB.getConnection();
            sp = "select idempleado from empleados where apellidos=?";
            ps = cn.prepareStatement(sp);
            ps.setString(1, nombre);
            rs = ps.executeQuery();
            rs.next();
            codigo = rs.getString(1);
        } catch (Exception e) {
            throw e;
        } finally {
            cerrarRecursos();
        }
        return codigo;
    }

    public boolean valida(String usu, String pas) throws Exception {
        // Verifica si existen credenciales válidas en la base de datos
        boolean sw = false;
        try {
            cn = AccesoDB.getConnection();
            sp = "select * from empleados where usuario=? and clave=?";
            ps = cn.prepareStatement(sp);
            ps.setString(1, usu);
            ps.setString(2, pas);
            rs = ps.executeQuery();
            sw = rs.next(); // true si hay coincidencia
        } catch (Exception e) {
            throw e;
        } finally {
            cerrarRecursos();
        }
        return sw;
    }
}
