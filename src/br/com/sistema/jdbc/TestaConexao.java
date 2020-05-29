
package br.com.sistema.jdbc;

import javax.swing.JOptionPane;

/**
 *
 * @author perei
 */
public class TestaConexao {
    
    public static void main(String[] args) {
        
        try {
            new ConnectionFactory().getConnection();
            JOptionPane.showMessageDialog(null, "Conectado com sucesso");
        } catch (Exception erro) {
            JOptionPane.showMessageDialog(null,"Ops, aconteceu um erro de conexao"+ erro);
        }
        
    }
    
}
