/*
    SGR ALPHA - UTILITY PACKAGE
    File: MACREADER.JAVA | Last Major Update: 11.05.2015 
    Developer: Kevin Raian e Washington Reis
    IDINALOG REBORN © 2015
*/

package sgr.util;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;

public class MACReader {
    
    /* MÉTODOS */
    // MÉTODO 01 - readMAC()
    // Retorna endereço IP e MAC do hardware de conexão do dispositivo.
    public static String readMAC() throws java.net.UnknownHostException {
        
        // Variaveis
        InetAddress IP;
        String CurrentMAC = null;
        
        try {
        
            // Verifica o IP atual
            IP = InetAddress.getLocalHost();
            System.out.println("[MAC READER] IP atual: " + IP.getHostAddress());

            // Utiliza o IP atual para chegar ao NetworkInterface
            NetworkInterface network = NetworkInterface.getByInetAddress(IP);

            // Verifica MAC atual
            byte[] MAC = network.getHardwareAddress();      

            // Converte o MAC em String
            StringBuilder sbMAC = new StringBuilder();

            for (int i = 0; i < MAC.length; i++) {

                sbMAC.append(String.format("%02X%s", MAC[i], (i < MAC.length - 1) ? "-" : ""));  

            }

            System.out.println("[MAC READER] MAC atual: " +sbMAC.toString());
            CurrentMAC = sbMAC.toString();
        
        } catch (SocketException e){

        e.printStackTrace();
        System.out.println("[MAC READER] ERRO: Não foi possível verificar IP do dispositivo.");

        }

        // Retorna MAC
        return CurrentMAC;
        
    }
    
    
    public static void main(String[] args) throws UnknownHostException {
        MACReader m = new MACReader();
        MACReader.readMAC();
    }
}
    

