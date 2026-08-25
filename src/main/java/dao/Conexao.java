package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
    public String driver = "com.mysql.cj.jdbc.Driver";
    public String url = "jdbc:mysql://localhost:3306/cantina";
    public String usuario = "root";
    public String senha = "";

    public Connection conectar() {
        try {
            Class.forName(driver);
            return DriverManager.getConnection(url, usuario, senha);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
