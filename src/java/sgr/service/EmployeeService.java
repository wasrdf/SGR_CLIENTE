/*
    SGR ALPHA - SERVICE PACKAGE
    File: EMPLOYEESERVICE.JAVA | Last Major Update: 22.05.2015
    Developer: Rafael Sousa
    IDINALOG REBORN © 2015
*/

package sgr.service;



import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import sgr.bean.EmployeeBean;
import sgr.dao.EmployeeDAO;
import sgr.dao.ExceptionDAO;
import sgr.sql.QueryBuilder;
import sgr.sql.QueryGender;
import sgr.sql.QueryOperation;
import sgr.sql.QueryType;


public class EmployeeService {
    
    // Login
    public List<EmployeeBean> doLogin(String clientUsername, String clientPassword) throws ExceptionDAO {
        
        List<EmployeeBean> currentClient = new ArrayList<EmployeeBean>();
        QueryBuilder query = new QueryBuilder();
        EmployeeDAO clientDAO = new EmployeeDAO();

        query.addQuery(QueryOperation.empty, "funcionario.nome_usuario", QueryGender.equal, clientUsername, QueryType.text);
        query.addQuery(QueryOperation.and, "funcionario.senha", QueryGender.equal, clientPassword, QueryType.text);

        try {
            try {
                System.out.println("[EMPLOYEE SERVICE] Loading currentClient with clientDAO.loadClient...");                
                currentClient = clientDAO.loadClient(query);
                System.out.println("[EMPLOYEE SERVICE] Query loaded to EmployeeDAO: '" + query + "'.");
            } catch (ClassNotFoundException ex) {
                System.out.println("[EMPLOYEE SERVICE] ERROR: Failed to load Query on EmployeeDAO.");
                Logger.getLogger(ClientService.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (SQLException e) {
            System.out.println("[EMPLOYEE SERVICE] ERROR: Failed to execute currentClient query.'" + query + "'.");
            System.out.println(e.getMessage());

        }
        
        System.out.println("[EMPLOYEE SERVICE] currentCliente value: '" + currentClient + "'.");
        return currentClient;
    }
    
}
