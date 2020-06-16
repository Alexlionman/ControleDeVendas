package br.com.sistema.dao;

import br.com.sistema.jdbc.ConnectionFactory;
import br.com.sistema.model.Clientes;
import br.com.sistema.model.ItemVenda;
import br.com.sistema.model.Produtos;
import br.com.sistema.model.Vendas;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
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
    public void cadastraItem(ItemVenda obj) {
        try {

            String sql = "insert into tb_itensvendas(venda_id,produto_id,qtd,subtotal) values(?,?,?,?)";

            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setInt(1, obj.getVenda().getId());
            stmt.setInt(2, obj.getProduto().getId());
            stmt.setInt(3, obj.getQtd());

            stmt.setDouble(4, obj.getSubtotal());

            stmt.execute();
            stmt.close();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro: " + e);

        }
    }

    //metodo que lista intens vendidos de uma determinada venda(id)
    public List<ItemVenda> listaItensPorVenda(int venda_id) {
        try {

            //cria a lista
            List<ItemVenda> lista = new ArrayList<>();

            //comando com o inner join referente ao nome d fornecedor para mostra na tabela
            String sql = "SELECT p.descricao, i.qtd, p.preco, i.subtotal from tb_itensvendas as i "
                    + " inner join tb_produtos as p on(i.produto_id = p.id) where i.venda_id = ?";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, venda_id);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ItemVenda item = new ItemVenda();
                Produtos prod = new Produtos();

                prod.setDescricao(rs.getString("p.descricao"));
                item.setQtd(rs.getInt("i.qtd"));
                prod.setPreco(rs.getDouble("p.preco"));
                item.setSubtotal(rs.getDouble("i.subtotal"));

                item.setProduto(prod);

                lista.add(item);

            }
            return lista;

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro: " + e);
            return null;

        }

    }
}
