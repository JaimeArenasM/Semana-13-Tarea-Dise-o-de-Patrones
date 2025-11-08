package uni.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import uni.database.AccesoDB;
import uni.entity.LineaTo;
import uni.service.ICrudDao;

public class LineaDao implements ICrudDao<LineaTo> {

    // variables
    Connection cn = null;
    Statement stm = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    String sql = "";

    @Override
    public void create(LineaTo o) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(LineaTo o) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(LineaTo o) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public LineaTo find(Object o) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<LineaTo> readAll() throws Exception {
        List<LineaTo> lista = new ArrayList<>();
        try {
            cn = AccesoDB.getConnection();
            sql = "select * from lineas order by idlinea";
            ps = cn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                LineaTo li = new LineaTo();
                li.setIdlinea(rs.getInt(1));
                li.setNombre(rs.getString(2));
                lista.add(li);
            }
            rs.close();
            ps.close();
        } catch (SQLException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            throw e;
        } finally {
            cn.close();
        }
        return lista;
    }

}
