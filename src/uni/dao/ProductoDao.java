package uni.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import uni.database.AccesoDB;
import uni.entity.ProductoTo;
import uni.service.ICrudDao;

public class ProductoDao implements ICrudDao<ProductoTo> {
    // variables

    Connection cn = null;
    Statement stm = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    String sql = "";

    @Override
    public void create(ProductoTo o) throws Exception {
        try {
            cn = AccesoDB.getConnection();
            String cod = generaCodigo();//metodo genera codigo de producto
            o.setIdproducto(cod);
            sql = "insert into productos(idproducto,descripcion,idlinea,preciocompra,precioventa,stock)"
                    + " values(?,?,?,?,?,?)";
            ps = cn.prepareStatement(sql);
            //preparar los valores de los parametros
            ps.setString(1, o.getIdproducto());
            ps.setString(2, o.getDescripcion());
            ps.setInt(3, o.getIdlinea());
            ps.setDouble(4, o.getPreciocompra());
            ps.setDouble(5, o.getPrecioventa());
            ps.setInt(6, o.getStock());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            throw e;
        } finally {
            cn.close();
        }
    }

    @Override
    public void update(ProductoTo o) throws Exception {
        try {
            cn = AccesoDB.getConnection();
            sql = "update productos set descripcion=?,idlinea=?,preciocompra=?,precioventa=?,stock=?"
                    + " where idproducto=?";
            ps = cn.prepareStatement(sql);
            //preparar los valores de los parametros           
            ps.setString(1, o.getDescripcion());
            ps.setInt(2, o.getIdlinea());
            ps.setDouble(3, o.getPreciocompra());
            ps.setDouble(4, o.getPrecioventa());
            ps.setInt(5, o.getStock());
            ps.setString(6, o.getIdproducto());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            throw e;
        } finally {
            cn.close();
        }
    }

    @Override
    public void delete(ProductoTo o) throws Exception {
        try {
            cn = AccesoDB.getConnection();
            sql = "delete from productos where idproducto=?";
            ps = cn.prepareStatement(sql);
            //preparar el valor del parametro         
            ps.setString(1, o.getIdproducto());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            throw e;
        } finally {
            cn.close();
        }
    }

    @Override
    public ProductoTo find(Object o) throws Exception {
        ProductoTo pr = null;
        try {
            cn = AccesoDB.getConnection();
            sql = "select * from productos where idproducto='" + (String) o + "'";
            ps = cn.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()) {
                pr = new ProductoTo();
                pr.setIdproducto(rs.getString(1));
                pr.setDescripcion(rs.getString(2));
                pr.setIdlinea(rs.getInt(3));
                pr.setPreciocompra(rs.getDouble(4));
                pr.setPrecioventa(rs.getDouble(5));
                pr.setStock(rs.getInt(6));
            }
            rs.close();
            ps.close();
        } catch (SQLException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            throw e;
        } finally {
            cn.close();
        }
        return pr;
    }

    @Override
    public List<ProductoTo> readAll() throws Exception {
        List<ProductoTo> lista = new ArrayList<>();
        try {
            cn = AccesoDB.getConnection();
            sql = "select * from productos";
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

    public List<ProductoTo> readAll(String nombre) throws Exception {
        List<ProductoTo> lista = new ArrayList<>();
        try {
            cn = AccesoDB.getConnection();
            sql = "select * from productos where descripcion like ?";
            ps = cn.prepareStatement(sql);
            ps.setString(1, nombre + "%");
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

    public List<ProductoTo> readAll(int id) throws Exception {
        List<ProductoTo> lista = new ArrayList<>();
        try {
            cn = AccesoDB.getConnection();
            sql = "select * from productos where idlinea=?";
            ps = cn.prepareStatement(sql);
            ps.setInt(1, id);
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
    
    public ProductoTo find1(String o) throws Exception {
        ProductoTo pr = null;
        try {
            cn = AccesoDB.getConnection();
            sql = "select * from productos where descripcion='" + o + "'";
            ps = cn.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()) {
                pr = new ProductoTo();
                pr.setIdproducto(rs.getString(1));
                pr.setDescripcion(rs.getString(2));
                pr.setIdlinea(rs.getInt(3));
                pr.setPreciocompra(rs.getDouble(4));
                pr.setPrecioventa(rs.getDouble(5));
                pr.setStock(rs.getInt(6));
            }
            rs.close();
            ps.close();
        } catch (SQLException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            throw e;
        } finally {
            cn.close();
        }
        return pr;
    }

    private List<ProductoTo> cargaLista(ResultSet rs) throws SQLException {
        List<ProductoTo> aux = new ArrayList<>();
        while (rs.next()) {
            ProductoTo pr = new ProductoTo();
            pr.setIdproducto(rs.getString(1));
            pr.setDescripcion(rs.getString(2));
            pr.setIdlinea(rs.getInt(3));
            pr.setPreciocompra(rs.getDouble(4));
            pr.setPrecioventa(rs.getDouble(5));
            pr.setStock(rs.getInt(6));
            aux.add(pr);
        }
        rs.close();
        return aux;
    }

    private String generaCodigo() throws SQLException {
        sql = "select valor from control where parametro='Productos'";
        ps = cn.prepareStatement(sql);
        rs = ps.executeQuery();
        rs.next();
        int cont = rs.getInt(1);
        rs.close();
        sql = "update control set valor=valor+1 where parametro='Productos'";
        ps = cn.prepareStatement(sql);
        ps.executeUpdate();
        ps.close();
        String cod = "";
        if (cont < 10) {
            cod = "A000" + cont;
        } else {
            cod = "A00" + cont;
        }
        return cod;
    }

}
