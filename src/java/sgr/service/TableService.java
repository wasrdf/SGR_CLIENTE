/*
    SGR ALPHA - SERVICE PACKAGE
    File: TABLESERVICE.JAVA | Last Major Update: 01.05.2015
    Developer: Kevin Raian
    IDINALOG REBORN © 2015
*/

package sgr.service;

import sgr.bean.TableBean;
import sgr.sql.QueryBuilder;
import sgr.sql.QueryGender;
import sgr.sql.QueryOperation;
import sgr.sql.QueryType;
import sgr.dao.TableDAO;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import sgr.dao.ExceptionDAO;

public class TableService {
    
    // Login
    public List<TableBean> doTableSearch(String currentMac) throws ExceptionDAO {
        
        List<TableBean> currentTable = new ArrayList<TableBean>();
        QueryBuilder query = new QueryBuilder();
        TableDAO tableDAO = new TableDAO ();
        
        query.addQuery(QueryOperation.empty, "mesa.identificador", QueryGender.equal, currentMac, QueryType.text);
        
        try {
            try {
                System.out.println("[CLIENT SERVICE] Loading currentClient with TableDAO.loadTable...");              
                currentTable = tableDAO.loadTable(query);
                System.out.println("[CLIENT SERVICE] Query loaded to TableDAO: '" + query + "'.");
            } catch (ClassNotFoundException ex) {
                System.out.println("[CLIENT SERVICE] ERROR: Failed to load Query TableDAO.");
                Logger.getLogger(ClientService.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        } catch (SQLException e) {
            System.out.println("[CLIENT SERVICE] ERROR: Failed to execute currentTable query.'" + query + "'.");
            System.out.println(e.getMessage());

        }
                
        System.out.println("[CLIENT SERVICE] currentTable value: '" + currentTable + "'.");
        return currentTable;
        
    }           
    
}
