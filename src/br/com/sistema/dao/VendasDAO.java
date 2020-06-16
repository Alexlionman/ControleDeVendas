package br.com.sistema.dao;

import br.com.sistema.jdbc.ConnectionFactory;
import br.com.sistema.model.Clientes;

import br.com.sistema.model.Vendas;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author perei
 */
public class VendasDAO {

    private Connection con;

    public VendasDAO() {
        this.con = new ConnectionFactory().getConnection();
    }

    //cadastrar uma venda
    public void cadastrarVenda(Vendas obj) {
        try {

            String sql = "insert into tb_vendas(cliente_id,data_venda,total_venda,observacoes) values(?,?,?,?)";

            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setInt(1, obj.getCliente().getId());
            stmt.setString(2, obj.getData_venda());
            stmt.setDouble(3, obj.getTotal_venda());

            stmt.setString(4, obj.getObs());

            stmt.execute();
            stmt.close();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro: " + e);

        }

    }

    //retorna a ultima venda
    public int retornaUltimaVenda() {
        try {

            int idvenda = 0;

            String sql = "select max(id) from tb_vendas";//seleciona a ultima venda através do maior id
            PreparedStatement ps = con.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Vendas p = new Vendas();

                p.setId(rs.getInt("max(id)"));

                idvenda = p.getId();

            }
            return idvenda;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

//metodo que filtra vendas por data
    public List<Vendas> lstarVendasPorPeriodo(LocalDate data_inicio, LocalDate data_fim) {
        try {

            //cria a lista
            List<Vendas> lista = new ArrayList<>();

            //comando com o inner join referente ao nome d fornecedor para mostra na tabela
            String sql = "select v.id, date_format(v.data_venda,'%d/%m/%y') as data_formatada, c.nome, v.total_venda, v.observacoes from tb_vendas as v "
                    + "inner join tb_clientes as c on(v.cliente_id = c.id) where v.data_venda BETWEEN? AND?";//este comando ja formata a data

            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, data_inicio.toString());
            stmt.setString(2, data_fim.toString());
            
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Vendas obj = new Vendas();
                Clientes c = new Clientes();

                obj.setId(rs.getInt("v.id"));
                obj.setData_venda(rs.getString("data_formatada"));
                c.setNome(rs.getString("c.nome"));
                obj.setTotal_venda(rs.getDouble("v.total_venda"));
                obj.setObs(rs.getString("v.observacoes"));
              
               obj.setCliente(c);
               lista.add(obj);
           

         

                lista.add(obj);

            }
            return lista;

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro: " + e);
            return null;

        }

    }
    
    
    public double retornaTotalVendaPorData(LocalDate data_venda){
         try {
            
             double totalvenda = 0;
             
             String sql = "select sum(total_venda)as total from tb_vendas where data_venda = ?";   //soma todas as venda de determinado
             PreparedStatement ps = con.prepareStatement(sql);
             ps.setString(1, data_venda.toString());
             
             ResultSet rs = ps.executeQuery();
             
             if(rs.next()){
               totalvenda = rs.getDouble("total");
               
             }
             
             return totalvenda;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    
    }

}
