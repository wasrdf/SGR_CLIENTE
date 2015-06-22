/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sgr.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import sgr.bean.ContaItemBean;
import sgr.util.ConnectionBuilder;

/**
 *
 * @author WASHINGTON
 */
public class ContaItemDAO {
    
     public void novoContaItem(ContaItemBean pContaItem) throws SQLException, ExceptionDAO {
            ConnectionBuilder connection = new ConnectionBuilder();
            Connection conn = connection.getConnection();
            String sql = "INSERT INTO conta_item(conta_codigo,cliente_codigo,cliente_cpf,item_codigo,mesa_numero,"
                    + "funcionario_codigo,funcionario_cpf,quantidade,status,data)"
                    + "values (?,?,?,?,?,?,?,?,?,?)";
                  
          System.out.println(sql);
          
          PreparedStatement ps = conn.prepareStatement(sql);
          ps.setInt(1, pContaItem.getContaCodigo());
          ps.setInt(2, pContaItem.getClienteCodigo());
          ps.setString(3, pContaItem.getClienteCpf());
          ps.setInt(4, pContaItem.getItemCodigo());
          ps.setInt(5, pContaItem.getMesaNumero());
          ps.setInt(6, pContaItem.getFuncionarioCodigo());
          ps.setString(7, pContaItem.getFuncionarioCpf());
          ps.setInt(8, pContaItem.getQuantidade());
          ps.setString(9, pContaItem.getStatus());
           try {
            ps.setDate(10, new java.sql.Date(pContaItem.getData().getTime()));
        } catch (NullPointerException e) {
            ps.setDate(11, null);
        }

          ps.execute();
          ps.close();
          conn.close();
    }
 
     
     public ContaItemBean solicitarCancelamento(ContaItemBean pContaItem) throws SQLException {
          ConnectionBuilder connection = new ConnectionBuilder();
            Connection conn = connection.getConnection();
            String sql = "update conta_item set status=? where codigo=?";
            System.out.println(sql);
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, pContaItem.getStatus());
            ps.setInt(2, pContaItem.getCodigo());
            ps.execute();
            ps.close();
            conn.close();
            return pContaItem;
     }
}
