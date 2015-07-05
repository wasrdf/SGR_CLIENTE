/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sgr.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import sgr.bean.MovimentoBean;
import sgr.sql.QueryBuilder;
import sgr.util.ConnectionBuilder;

/**
 *
 * @author WASHINGTON
 */
public class MovimentoDAO {
    /**aqui eu carrego todos os pedidos em aberto na tela principal do cliente
     e também na tela do garcom onde cada garcom só consegue ver os pedidos de suas
     * mesas
    * */
    public List<MovimentoBean> listaMovimentos(QueryBuilder sClausula) throws ExceptionDAO, SQLException {
        List<MovimentoBean> movimentos = new ArrayList<MovimentoBean>();
        ConnectionBuilder conexao = new ConnectionBuilder();
        Connection conn = conexao.getConnection();
        String sql = "select * from vw_movimento" + sClausula.buildQuery();
        System.out.println("SQL:" + sql);
        PreparedStatement ps = conn.prepareStatement(sql);
        
        ResultSet rs = ps.executeQuery();
        
        while(rs.next()) {
            MovimentoBean movimento = new MovimentoBean();
            movimento.setClienteCodigo(rs.getInt("cliente_codigo"));
            movimento.setClienteNome(rs.getString("cliente"));
            movimento.setContaCodigo(rs.getInt("conta_codigo"));
            movimento.setContaStatus(rs.getBoolean("conta_status"));
            movimento.setFrag(rs.getString("flag"));
            movimento.setMesaNumero(rs.getInt("mesa_numero"));
            movimento.setItemStatus(rs.getString("item_status"));
            movimento.setItem(rs.getString("item"));
            movimento.setQuantidade(rs.getInt("quantidade"));
            movimento.setPreco(rs.getDouble("preco"));
            movimento.setItemCodigo(rs.getInt("item_codigo"));
            movimento.setContaItemCodigo(rs.getInt("conta_item_codigo"));
            movimento.setData(rs.getDate("data"));
            
            movimentos.add(movimento);
        }
        System.out.println("[MOVIMENTODAO] fim do while...");
        rs.close();
        ps.close();
        conn.close();
        return movimentos;
    }
    
   
    
}
