package br.com.sistema.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;



/**
 *
 * @author perei
 */
public class ConnectionFactory {
    
    public Connection getConnection(){
        try {
            return DriverManager.getConnection("jdbc:mysql://127.0.0.1/bdvendas?useSSL=false","usuariocurso", "Alex4675");
                    
        } catch (Exception erro) {
           throw new RuntimeException(erro);
        }
        
    }
    
    
}
