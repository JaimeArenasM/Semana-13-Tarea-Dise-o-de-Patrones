/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uni.database;

import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author Alumno
 */
public class prueba {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            Connection cn = AccesoDB.getConnection();
            System.out.println("Conexion conforme....");
        } catch (SQLException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            System.out.println("error:" + e.getMessage());
        }

    }
}
