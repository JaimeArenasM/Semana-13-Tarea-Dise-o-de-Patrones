package uni.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import uni.database.AccesoDB;
import uni.entity.ClienteTo;
import uni.service.ICrudDao;

public class ClienteDao implements ICrudDao<ClienteTo> {
    // variables

    Connection cn = null;
    Statement stm = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    String sql = "";

    @Override
    public void create(ClienteTo o) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(ClienteTo o) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(ClienteTo o) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ClienteTo find(Object o) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<ClienteTo> readAll() throws Exception {
        List<ClienteTo> lista = new ArrayList<>();
        try {
            cn = AccesoDB.getConnection();
            sql = "select * from clientes order by idcliente";
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

    private List<ClienteTo> cargaLista(ResultSet rs) throws SQLException {
        List<ClienteTo> aux = new ArrayList<>();
        while (rs.next()) {
            ClienteTo cli = new ClienteTo();
            cli.setIdcliente(rs.getString(1));
            cli.setNombre(rs.getString(2));
            cli.setDireccion(rs.getString(3));
            cli.setRuc(rs.getString(4));
            cli.setTelefono(rs.getString(5));
            aux.add(cli);
        }
        rs.close();
        return aux;
    }

    public String readAll1(String nombre) throws Exception {
        String codigo;
        try {
            cn = AccesoDB.getConnection();
            sql = "select idcliente from clientes where nombre=?";
            ps = cn.prepareStatement(sql);
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

}
