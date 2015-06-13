/*
    SGR ALPHA - DAO PACKAGE
    File: SESSIONDAO.JAVA | Last Major Update: 05.05.2015
    Developer: Kevin Raian
    IDINALOG REBORN © 2015
*/

package sgr.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import sgr.bean.SessionBean;
import sgr.sql.QueryBuilder;
import sgr.util.ConnectionBuilder;

public class SessionDAO {
    
    /* MÉTODOS */
    // MÉTODO 01 - newSession()
    // Cria nova seção; abre uma nova conta    
    public void newSession(SessionBean session) throws SQLException, ExceptionDAO {
        
        System.out.println("[SESSION DAO] Preparing Session Query...");
        ConnectionBuilder conexao = new ConnectionBuilder();
        Connection conn = conexao.getConnection();
        String sql = "INSERT INTO conta(STATUS,CLIENTE_CODIGO,CLIENTE_CPF) VALUES (?,?,?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        
        System.out.println("[SESSION DAO] Values on current SQL are: Status '" + session.isStatus() + "', Código Cliente '" + session.getC_codigo() + 
                "' and CPF Cliente '" + session.getC_cpf() + "'.");  
        
        ps.setBoolean(1, session.isStatus());
        ps.setInt(2, session.getC_codigo());
        ps.setString(3, session.getC_cpf());
        ps.execute();
        ps.close();
        conn.close(); 
        
    }
    
    // MÉTODO 02 - sessionInfo()
    // Recupera informações da seção de acordo com a confição informada
    public List <SessionBean> sessionInfo(QueryBuilder query) throws ExceptionDAO, ClassNotFoundException, SQLException {
        
        ConnectionBuilder conexao = new ConnectionBuilder();
        Connection conn = conexao.getConnection();
        List<SessionBean> sessionList = new ArrayList<SessionBean>();
        String sql = "SELECT * FROM conta " + query.buildQuery();
        
        System.out.println("[SESSION DAO] SQL being executed: '" + sql + "'." );
        
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        
        while(rs.next()) {
            
            SessionBean sessionBean = new SessionBean();
            
            sessionBean.setCodigo(rs.getInt("codigo"));
            sessionBean.setStatus(rs.getBoolean("status"));
            sessionBean.setC_codigo(rs.getInt("cliente_codigo"));
            sessionBean.setC_cpf(rs.getString("cliente_cpf"));
            
            System.out.println("[SESSION DAO] Data fetched from SQL result: Codigo '" + sessionBean.getCodigo() + "', Status '"
            + sessionBean.isStatus() + "', Código Cliente '" + sessionBean.getC_codigo() + "', CPF Cliente '" + sessionBean.getC_cpf() + "'.");
            
            sessionList.add(sessionBean);
            
        }  
        
        rs.close();
        ps.close();
        conn.close();
        return sessionList;       
    }
    
    // MÉTODO 03 - closeSession()
    // Encerra seção atual; fecha conta atual.  
    public void closeSession(SessionBean session) throws ExceptionDAO, ClassNotFoundException, SQLException {
        System.out.println("[SESSION DAO] Preparando para encerrar seção...");
        
        ConnectionBuilder conexao = new ConnectionBuilder();
        Connection conn = conexao.getConnection();
        String sql = "UPDATE conta SET STATUS = 0 WHERE CODIGO = ?";
        PreparedStatement ps = conn.prepareStatement(sql);
        
        
        System.out.println("[SESSION DAO] Codigo do cliente atual: " + session.getC_codigo());
        ps.setLong(1, session.getCodigo());
        
        ps.execute();
        ps.close();
        conn.close();    
    }
    
    // MÉTODO 04 - doOpenedSessionSearch()
    // Verifica e retorna se há ou não uma seção aberta
    public boolean doOpenedSessionSearch(QueryBuilder query) throws SQLException, ExceptionDAO {
        
        boolean sessionStatus = false;
        long sessionCode = 0;
        
        ConnectionBuilder conexao = new ConnectionBuilder();
        Connection conn = conexao.getConnection();
        String sql = "SELECT * FROM conta " + query.buildQuery();
        
        System.out.println("[SESSION DAO][04] SQL sendo executado: '" + sql + "'." );
        
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        
        while(rs.next()) {
            
            SessionBean sessionBean = new SessionBean();
            
            sessionBean.setCodigo(rs.getInt("codigo"));
            sessionBean.setStatus(rs.getBoolean("status"));
            sessionBean.setC_codigo(rs.getInt("cliente_codigo"));
            sessionBean.setC_cpf(rs.getString("cliente_cpf"));
            
            System.out.println("[SESSION DAO][04] Dados adquiridos do SQL executado: Codigo '" + sessionBean.getCodigo() + "', Status '"
            + sessionBean.isStatus() + "', Código Cliente '" + sessionBean.getC_codigo() + "', CPF Cliente '" + sessionBean.getC_cpf() + "'.");
            
            sessionStatus = sessionBean.isStatus();   
            sessionCode = sessionBean.getCodigo();
            
        }  
        
        rs.close();
        ps.close();
        conn.close(); 
        
        if (sessionStatus == true) {
            System.out.println("[SESSION DAO][04] Seção aberta encontrada com código '" + sessionCode + "'!");
            return true;            
        } else {
            System.out.println("[SESSION DAO][04] Nenhuma seção aberta encontrada.");
            return false;
        }   
    
    } 
    
}
