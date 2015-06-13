/*
    SGR ALPHA - DAO PACKAGE
    File: TABLEDAO.JAVA | Last Major Update: 01.05.2015
    Developer: Kevin Raian
    IDINALOG REBORN © 2015
*/

package sgr.dao;

import sgr.bean.TableBean;
import sgr.sql.QueryBuilder;
import sgr.util.ConnectionBuilder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TableDAO {
    
    public List <TableBean> loadTable(QueryBuilder query) throws SQLException, ClassNotFoundException, ExceptionDAO {
        
        ConnectionBuilder conexao = new ConnectionBuilder();
        Connection conn = conexao.getConnection();
        List<TableBean> tableList = new ArrayList<TableBean>();
        String sql = "SELECT * FROM mesa " + query.buildQuery();
        
        System.out.println("[TABLE DAO] SQL being executed: '" + sql + "'.");
        
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        
        while(rs.next()) {
            
            TableBean tableBean = new TableBean();
            
            tableBean.setNumero(rs.getInt("numero"));
            tableBean.setCapacidade(rs.getInt("capacidade"));
            tableBean.setIdentificador(rs.getString("identificador"));
            tableBean.setStatus(rs.getBoolean("status"));
            tableBean.setFuncionarioCodigo(rs.getInt("funcionario_codigo"));
            tableBean.setFuncionarioCpf(rs.getString("funcionario_cpf"));
            
            System.out.println("[TABLE DAO] Data fetched from SQL result: Numero '" + tableBean.getNumero() + "', Capacidade '"
            + tableBean.getCapacidade() + "', Identificador '" + tableBean.getIdentificador() + "', Status '" + tableBean.isStatus() + "'.");
            
            tableList.add(tableBean);
            
        }
        
        rs.close();
        ps.close();
        conn.close();
        return tableList;        
       
    }
    
     public void ocuparMesa(TableBean pTableBean)  {
        ConnectionBuilder conexao = new ConnectionBuilder();
        Connection conn = null;
        try {
            conn = conexao.getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(TableDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        String sql = "update mesa set status = ? where numero=?";
        System.out.println(sql);
        PreparedStatement ps;
        try {
            ps = conn.prepareStatement(sql);
        ps.setBoolean(1, pTableBean.isStatus());
        ps.setInt(2, pTableBean.getNumero());
        ps.execute();
        ps.close();
        conn.close();
    
         } catch (SQLException ex) {
            Logger.getLogger(TableDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
       
        }
    
}
