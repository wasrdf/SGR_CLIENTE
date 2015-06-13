/*
    SGR ALPHA - SERVICE PACKAGE
    File: SESSIONSERVICE.JAVA | Last Major Update: 05.05.2015
    Developer: Kevin Raian
    IDINALOG REBORN © 2015
*/

package sgr.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import sgr.bean.SessionBean;
import sgr.dao.ExceptionDAO;
import sgr.dao.SessionDAO;
import sgr.sql.QueryBuilder;
import sgr.sql.QueryGender;
import sgr.sql.QueryOperation;
import sgr.sql.QueryType;

public class SessionService {
    
    public void newSession(SessionBean session) throws SQLException, ExceptionDAO {
        
        try {
            SessionDAO sessionDAO = new SessionDAO();
            sessionDAO.newSession(session);
            System.out.println("[SESSION SERVICE] Session opened with code '" + session.getCodigo() + "'!");
        } catch (SQLException ex) {
            System.out.println("[SESSION SERVICE] ERROR: Failed to open new session!");
        }
    }
    
    public boolean doOpenedSessionSearch (int currentUserCode, int sessionStatus) throws ExceptionDAO, SQLException {
       
       boolean currentSessionStatus = false;
       QueryBuilder query = new QueryBuilder();
       SessionDAO sessionDAO = new SessionDAO();
       
       query.addQuery(QueryOperation.empty, "conta.cliente_codigo", QueryGender.equal, String.valueOf(currentUserCode), QueryType.number);
       query.addQuery(QueryOperation.and, "conta.status", QueryGender.equal, String.valueOf(sessionStatus), QueryType.number);
       
       try {        
           System.out.println("[SESSION SERVICE] Loading currentClientCode with SessionDAO.doOpenSessionSearch...");
           currentSessionStatus = sessionDAO.doOpenedSessionSearch(query);
           System.out.println("[SESSION SERVICE] Query loaded to SessionDAO: '" + query + "'.");            
        } catch (SQLException e) {            
            System.out.println("[SESSION SERVICE] ERROR: Failed to execute openedSession query.'" + query + "'.");
            System.out.println(e.getMessage());
            
        }
       
       return currentSessionStatus;
       
    }
    
    public List<SessionBean> doOpenedSessionInfoSearch (int currentUserCode, int sessionStatus) throws ExceptionDAO, SQLException {
        
        List<SessionBean> openedSession = new ArrayList<SessionBean>();
        QueryBuilder query = new QueryBuilder();
        SessionDAO sessionDAO = new SessionDAO();
        
        query.addQuery(QueryOperation.empty, "conta.cliente_codigo", QueryGender.equal, String.valueOf(currentUserCode), QueryType.number);
        query.addQuery(QueryOperation.and, "conta.status", QueryGender.equal, String.valueOf(sessionStatus), QueryType.number);
        
        try {        
            try {
                System.out.println("[SESSION SERVICE] Loading currentClientCode with SessionDAO.doOpenSessionSearch...");  
                openedSession = sessionDAO.sessionInfo(query);
                System.out.println("[SESSION SERVICE] Query loaded to SessionDAO: '" + query + "'.");    
            } catch (ClassNotFoundException ex) {
                System.out.println("[SESSION SERVICE] ERROR: Failed to load Query SessionDAO.");
                Logger.getLogger(ClientService.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        } catch (SQLException e) {
            
            System.out.println("[SESSION SERVICE] ERROR: Failed to execute openedSession query.'" + query + "'.");
            System.out.println(e.getMessage());
            
        }
        
        System.out.println("[SESSION SERVICE] openedSession value: '" + openedSession + "'.");        
        return openedSession;
        
    }
    
     public void closeSession(SessionBean session) throws SQLException, ExceptionDAO, ClassNotFoundException {
        
        try {
            SessionDAO sessionDAO = new SessionDAO();
            sessionDAO.closeSession(session);
            System.out.println("[SESSION SERVICE] Session closed '" + session.getCodigo() + "'!");
        } catch (SQLException ex) {
            System.out.println("[SESSION SERVICE] ERROR: Failed to close session!");
        }
    }
    
}
