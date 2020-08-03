package br.com.sistema.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;



/**
 *
 * @author perei
 */
public class ConnectionFactory {
    ///dados próprios da minha conexão local
    public Connection getConnection(){
        try {
            return DriverManager.getConnection("jdbc:mysql://127.0.0.1/bdvendas?allowPublicKeyRetrieval=true&useSSL=false","usuariocurso", "Alex4675");
                    
        } catch (Exception erro) {
           throw new RuntimeException(erro);
        }
        
    }
    
    
}
