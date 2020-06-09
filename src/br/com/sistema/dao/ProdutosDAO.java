
package br.com.sistema.dao;

import br.com.sistema.jdbc.ConnectionFactory;
import br.com.sistema.model.Fornecedores;
import br.com.sistema.model.Produtos;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author perei
 */
public class ProdutosDAO {
    private Connection con;

    public ProdutosDAO() {
        this.con = new ConnectionFactory().getConnection();
    }
    
    //metodo para cadastrar produtos
    
    public void cadastrar(Produtos obj){
        try {
            
            String sql ="insert into tb_produtos(descricao,preco,qtd_estoque,for_id) values(?,?,?,?)";
            
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1,obj.getDescricao());
            stmt.setDouble(2,obj.getPreco());
            stmt.setInt(3,obj.getQtd_estoque());
            
            stmt.setInt(4, obj.getFornecedor().getId());//peguei o id do objeto fornecedor 
            
            stmt.execute();
            stmt.close();
            
            JOptionPane.showMessageDialog(null, "Produto cadastrado com sucesso");
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro: " + e);
            
         
        }
    
    }
    
    
    //metodo para listar os produtos
    public List<Produtos> listarProdutos(){
        try {
            
            //cria a lista
            List<Produtos> lista = new ArrayList<>();
            
            //comando com o inner join referente ao nome d fornecedor para mostra na tabela
            
            String sql = "select p.id,p.descricao,p.preco,p.qtd_estoque,f.nome from tb_produtos as p "
                    + "inner join tb_fornecedores as f on (p.for_id = f.id)";
            
            
            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            
            
            while (rs.next()) {
                Produtos obj = new Produtos();
                Fornecedores f = new Fornecedores();
                
                obj.setId(rs.getInt("p.id"));
                obj.setDescricao(rs.getString("p.descricao"));
                obj.setPreco(rs.getDouble("p.preco"));
                obj.setQtd_estoque(rs.getInt("p.qtd_estoque"));
                
                f.setNome(rs.getString(("f.nome")));
                
                obj.setFornecedor(f);
                
                lista.add(obj);
                
                
            }
            return lista;
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro: " + e);
            return null;
            
        }
    
    }
    
    
}
