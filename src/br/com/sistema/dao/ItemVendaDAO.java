
package br.com.sistema.dao;

import br.com.sistema.jdbc.ConnectionFactory;
import br.com.sistema.model.ItemVenda;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.swing.JOptionPane;

/**
 *
 * @author perei
 */
public class ItemVendaDAO {
     private Connection con;

    public ItemVendaDAO() {
        this.con = new ConnectionFactory().getConnection();
    }
    
    
    //Metodo que cadastra os Itens
    public void cadastraItem(ItemVenda obj){
     try {
            
            String sql ="insert into tb_itensvendas(venda_id,produto_id,qtd,subtotal) values(?,?,?,?)";
            
            PreparedStatement stmt = con.prepareStatement(sql);
            
            stmt.setInt(1,obj.getVenda().getId());
            stmt.setInt(2,obj.getProduto().getId());
            stmt.setInt(3,obj.getQtd());
            
            stmt.setDouble(4, obj.getSubtotal());
            
            stmt.execute();
            stmt.close();
            
     
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro: " + e);
            
         
        }
    }
}
