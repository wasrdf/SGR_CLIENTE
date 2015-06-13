/*
 SGR ALPHA - UTILITY PACKAGE
 File: CONNECTIONBUILDER.JAVA | Last Major Update: 11.05.2015 | Version 2.0
 Developer: Kevin Raian
 IDINALOG REBORN © 2015
 */

package sgr.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import sgr.dao.ExceptionDAO;


//<editor-fold desc="Version 2.0">

public class ConnectionBuilder {
    
// ## VARIÁVEIS ##
// Indicador de Status da Conexão
public static String connectionStatus = "";
    
    // ## CONSTRUTOR ##
    // Vazio
    public ConnectionBuilder() {
        
    }
    
    public static void main(String[] args) throws SQLException {
        ConnectionBuilder c = new ConnectionBuilder();
        c.getConnection();
    }
    
    // ## MÉTODOS ##
    // MÉTODO 01 - getConnection()
    // Realizar Conexão
    public static java.sql.Connection getConnection() throws SQLException {
        
        Connection conn = null; 
        
        try {
            // Carrega o driver JDBC 
            String driverName = "com.mysql.jdbc.Driver";   
            Class.forName(driverName); 
            
            // Configuração do Banco de Dados
           // String IP = "192.185.221.164:3306";    
            String IP = "localhost:3306";    
            String databaseName ="idina743_sgr";        
            String url = "jdbc:mysql://" + IP + "/" + databaseName;
            //String databaseUser = "idina743_admin";             
            String databaseUser = "root";             
            String databaseUserPassword = "26583205";     
           // String databaseUserPassword = "478403";     
            conn = DriverManager.getConnection(url, databaseUser, databaseUserPassword);         

            // Testa Conexão
            if (conn != null) {
                
           
                connectionStatus = ("[CONNECTION BUILDER] Conexão com banco de dados realizada com sucesso!");
                System.out.println(connectionStatus);
            } else {
                connectionStatus = ("[CONNECTION BUILDER] ERRO: Não foi possível conectar ao banco de dados.");
            }
            return conn; 

        } catch (ClassNotFoundException e) {  
                System.out.println("[CONNECTION BUILDER] O driver necessário para conexão não foi encontrado.");
                return null;
        } catch (SQLException e) {
                System.out.println("[CONNECTION BUILDER] ERRO: Não foi possível conectar ao banco de dados.");
                return null;
        }
    }
    
    // MÉTODO 02 - statusConnection()
    // Verifica Status da Conexão
    public static String statusConection() {
        return connectionStatus;
    }  

    // MÉTODO 03 - closeConnection()
    // Fecha Conexão
    public static boolean closeConnection() {
        try {
            ConnectionBuilder.getConnection().close();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    // MÉTODO 04 - restartConnection()
    // Reinicia Conexão
    public static java.sql.Connection restartConnection() throws SQLException {
        closeConnection();
        return ConnectionBuilder.getConnection();
    }
}




// </editor-fold>


// <editor-fold defaultstate="collapsed" desc="Version 1.0">

/*public class ConnectionBuilder  {
    public static Connection getConnection( ) throws ExceptionDAO { 
    try {      
        Class.forName ("com.mysql.jdbc.Driver") ;
        // Conecta ao Banco de Dados MySQL
        System.out.println("[DATABASE] Connecting to default Database...");
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/idina743_sgr", "root", "26583205");
    } catch (Exception e){
        System.out.println("[DATABASE] ERROR: Connection Failed!");
        throw new ExceptionDAO(e.getMessage());
    }
}
    
// Fecha a Conexão
public static void closeConnection (Connection conn, Statement stmt, ResultSet rs) throws ExceptionDAO {
    close (conn, stmt, rs);
    System.out.println("[DATABASE] Closing Connection to default Database...");
    }
public static void closeConnection(Connection conn, Statement stmt)
    throws ExceptionDAO {
    close (conn, stmt, null);
}
public static void closeConnection(Connection conn)
    throws ExceptionDAO {
    close (conn, null,null);
}
private static void close(Connection conn, 
    Statement stmt, ResultSet rs)
    throws ExceptionDAO {
    try {
        if (rs != null) rs.close();
        if (stmt != null) stmt.close();
        if (conn != null) conn.close();
    } catch (Exception e) {
        throw new ExceptionDAO (e.getMessage());
    }
}
   

} */






// </editor-fold>
