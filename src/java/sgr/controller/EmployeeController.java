/*
 SGR ALPHA - CONTROLLER PACKAGE
 File: EMPLOYEECONTROLLER.JAVA | Last Major Update: 22.05.2015
 Developer: Rafael Sousa
 IDINALOG REBORN © 2015
 */
package sgr.controller;

import java.io.IOException;
import java.net.UnknownHostException;
import java.sql.SQLException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.servlet.http.HttpSession;
import sgr.bean.EmployeeBean;
import sgr.dao.ExceptionDAO;

@SessionScoped
@ManagedBean(name = "employeeController")

public class EmployeeController {

    /* VARIAVEIS */
    // Formulário de Login 
    private String employeeUsername = "";
    private String employeePassword = "";

    // MAC Reader
    private String currentMac = "";

    // HTTP Session
    private HttpSession session;

    // Beans    
    EmployeeBean employeeBean = new EmployeeBean();

    /* MÉTODOS */
    // MÉTODO 01 - doLogin()
    // Realiza login do usuário cofigurando todos os parâmetros necesários
    // para iniciar uma nova seção.
    public void doLogin() throws ExceptionDAO, UnknownHostException, SQLException, IOException {

    }

}
