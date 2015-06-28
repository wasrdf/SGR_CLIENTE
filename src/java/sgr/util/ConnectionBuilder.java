/*
    SGR ALPHA - UTILITY PACKAGE
    File: CONNECTIONBUILDER.JAVA | Last Major Update: 23.06.2015 | Version 2.1 Final
    Developer: Kevin Raian
    IDINALOG REBORN © 2015
*/

package sgr.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

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
            
            /* 
               CLOUD DATABASE PARAMETERS
               String IP = "192.185.221.164:3306";
               String databaseUser = "idina743_admin";
               String databaseUserPassword = "478403";
            */
            
            // Parâmetros de Database Local
            String IP = "localhost:3306";    
            String databaseName ="idina743_sgr";        
            String url = "jdbc:mysql://" + IP + "/" + databaseName;
            String databaseUser = "root";             
            String databaseUserPassword = "26583205";     
            
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