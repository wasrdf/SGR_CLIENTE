/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sgr.service;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import sgr.bean.ContaItemBean;
import sgr.dao.ExceptionDAO;

/**
 *
 * @author WASHINGTON
 */
public class ContaItemService {
 
    public void salvarPedidoItem(ContaItemBean pOrderItem) {
        sgr.dao.ContaItemDAO pDAO = new sgr.dao.ContaItemDAO();
        
        try {
            System.out.println("PEDIDO ITEM ADICIONADO");
            pDAO.novoContaItem(pOrderItem);
        } catch (SQLException ex) {
            System.out.println("PEDIDO_ITEM ERRO MOTIVO: " + ex.getSQLState());
            Logger.getLogger(ContaItemService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ExceptionDAO ex) {
            System.out.println("PEDIDO ITEM ADICIONADO MOTIVO: " + ex.getMessage());
            Logger.getLogger(ContaItemService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
