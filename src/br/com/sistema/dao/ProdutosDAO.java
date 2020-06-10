
package br.com.sistema.dao;

import br.com.sistema.jdbc.ConnectionFactory;
import br.com.sistema.model.Clientes;
import br.com.sistema.model.Fornecedores;
import br.com.sistema.model.Produtos;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
    
    
    //metodo para alterar os produtos
     public void alterar(Produtos obj) {
    try {

            String sql = "update tb_produtos set descricao=?,preco=?,qtd_estoque=?,for_id=? where id=?";
                    
            //dados do update
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, obj.getDescricao());
            stmt.setDouble(2, obj.getPreco());
            stmt.setInt(3, obj.getQtd_estoque());
            
            stmt.setInt(4, obj.getFornecedor().getId());
            
            
            stmt.setInt(5,obj.getId()); //o id que será utilizado para trocar dados do produto(where)
            
            

            stmt.execute();
            stmt.close();

            JOptionPane.showMessageDialog(null, "Produto alterado com sucesso");

        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro" + erro);
        }
    }
     
      public void excluir(Produtos obj) {
       try {

            String sql = "delete from tb_produtos where id = ?";

            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, obj.getId());
            

            stmt.execute();
            stmt.close();

            JOptionPane.showMessageDialog(null, "Produto excluido com sucesso");

        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro" + erro);
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
    
    
    //procurar produto pelo nome
    public List<Produtos> listarProdutosPorNome(String nome){
        try {
            
            //cria a lista
            List<Produtos> lista = new ArrayList<>();
            
           
            
            String sql = "select p.id,p.descricao,p.preco,p.qtd_estoque,f.nome from tb_produtos as p "
                    + "inner join tb_fornecedores as f on (p.for_id = f.id) where p.descricao like ?";
            
            
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, nome);
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
    
    public Produtos consultaPorNome(String nome){
        try {
            
            //cria a lista
           
           
            
            String sql = "select p.id,p.descricao,p.preco,p.qtd_estoque,f.nome from tb_produtos as p "
                    + "inner join tb_fornecedores as f on (p.for_id = f.id) where p.descricao = ?";
            
            
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, nome);
            
            ResultSet rs = stmt.executeQuery();
            Produtos obj = new Produtos();
            Fornecedores f = new Fornecedores();
            
            
            if (rs.next()) {
                
                
                obj.setId(rs.getInt("p.id"));
                obj.setDescricao(rs.getString("p.descricao"));
                obj.setPreco(rs.getDouble("p.preco"));
                obj.setQtd_estoque(rs.getInt("p.qtd_estoque"));
                
                f.setNome(rs.getString(("f.nome")));
                
                obj.setFornecedor(f);
                

                
                
            }
            return obj;
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Produto não encotrado");
            return null;
            
        }
    
    }
    
    
    
    
    
    
   
    
}
