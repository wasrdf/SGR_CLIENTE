/*
    SGR ALPHA - DAO PACKAGE
    File: ITEMDAO.JAVA | Last Major Update: 12.05.2015
    Developer: Kevin Raian, Washington Reis
    IDINALOG REBORN © 2015
*/

package sgr.dao;

import sgr.bean.ItemBean;
import sgr.util.ConnectionBuilder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import sgr.sql.QueryBuilder;

public class ItemDAO {
    
    /* MÉTODOS */
    // MÉTODO 01 - listItems
    // Lista todos os items conforme condição informada
    public List<ItemBean> listItems(QueryBuilder query) throws SQLException, ExceptionDAO {
        
        ConnectionBuilder connection = new ConnectionBuilder();
        Connection conn = connection.getConnection();
        List<ItemBean> listItem = new ArrayList<ItemBean>();
        
        String sql = "SELECT * FROM item" + query.buildQuery();
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        
        while(rs.next()) {
            ItemBean item = new ItemBean();    
            item.setCodigo(rs.getInt("codigo"));
            item.setNome(rs.getString("Nome"));
            item.setComposicao(rs.getString("Composicao"));            
            item.setPreco(rs.getFloat("Preco"));
            listItem.add(item);    
            
            
        }
        
        rs.close();
        ps.close();
        conn.close();
        return listItem;
    }
    
    public List<ItemBean> listAllItems() throws SQLException, ExceptionDAO {
        
        ConnectionBuilder connection = new ConnectionBuilder();
        Connection conn = connection.getConnection();
        List<ItemBean> listItem = new ArrayList<ItemBean>();
        
        String sql = "SELECT NOME, COMPOSICAO,codigo, PRECO FROM item";
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        
        while(rs.next()) {
            ItemBean item = new ItemBean();            
            item.setNome(rs.getString("Nome"));
            item.setComposicao(rs.getString("Composicao"));            
            item.setPreco(rs.getFloat("Preco"));
            item.setCodigo(rs.getInt("codigo"));
            listItem.add(item);    
            
            System.out.println("[ITEM DAO][01] Item encontrado: Nome '" + rs.getString("Nome")
                    + "', Composição '" + rs.getString("Composicao") + "' e Preço '" + rs.getFloat("Preco") + "'.");
        }
        
        rs.close();
        ps.close();
        conn.close();
        return listItem;
    }
    
    // MÉTODO 02 - searchTypesList()
    // Retorna uma lista de tipos de itens encontrados no banco de dados
    public List<ItemBean> searchItemTypes() throws SQLException, ExceptionDAO {
        
        ConnectionBuilder connection = new ConnectionBuilder();
        Connection conn = connection.getConnection();
        List<ItemBean> itemTypesList = new ArrayList<ItemBean>();
        
        System.out.println("[ITEM DAO][02] Iniciando busca por tipos de itens existentes...");
        
        String sql = "SELECT DISTINCT Tipo FROM item";
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        
        while(rs.next()) {
            ItemBean item = new ItemBean();
            item.setTipo(rs.getString("Tipo"));
            itemTypesList.add(item);            
            
            System.out.println("[ITEM DAO][02] Tipo encontrado: '" + rs.getString("Tipo") +"'.");
        }
        
        rs.close();
        ps.close();
        conn.close();
        
        return itemTypesList;
        
    }
    
}
