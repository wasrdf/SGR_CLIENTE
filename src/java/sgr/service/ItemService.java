/*
    SGR ALPHA - SERVICE PACKAGE
    File: ITEMSERVICE.JAVA | Last Major Update: 12.05.2015
    Developer: Kevin Raian, Washington Reis
    IDINALOG REBORN © 2015
*/

package sgr.service;

import sgr.bean.ItemBean;
import sgr.dao.ItemDAO;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import sgr.dao.ExceptionDAO;
import sgr.sql.QueryBuilder;
import sgr.sql.QueryGender;
import sgr.sql.QueryOperation;
import sgr.sql.QueryType;


public class ItemService {
    
    public List<ItemBean> listItems(String selectedItemType) throws ExceptionDAO {
        ItemDAO itemDAO = new ItemDAO();
        List<ItemBean> listItem = new ArrayList<ItemBean>();
        QueryBuilder query = new QueryBuilder();
        
        if ("Todos".equals(selectedItemType)){
            System.out.println("OK!");
        }
        
        if ("Todos".equals(selectedItemType)){
            System.out.println("[ITEM SERVICE][01] Valor atual de 'selectedItemType' : " + selectedItemType + ".");           

            try {
                listItem = itemDAO.listAllItems();
            } catch (SQLException ex) {
                Logger.getLogger(ItemService.class.getName()).log(Level.SEVERE, null, ex);
            }            
        } else {
            System.out.println("[ITEM SERVICE][01] Valor atual de 'selectedItemType' : " + selectedItemType + ".");

            query.addQuery(QueryOperation.empty,"item.Tipo", QueryGender.has, selectedItemType, QueryType.text);

            try {
                listItem = itemDAO.listItems(query);
            } catch (SQLException ex) {
                Logger.getLogger(ItemService.class.getName()).log(Level.SEVERE, null, ex);
            }   
        }
       
        
        
        return listItem;
    }
    
    public List<ItemBean> searchItemTypes() throws ExceptionDAO {
        
        ItemDAO itemDAO = new ItemDAO();
        List<ItemBean> itemTypesList = new ArrayList<ItemBean>();
        
        
        try {
            itemTypesList = itemDAO.searchItemTypes();
        } catch (SQLException ex) {
            Logger.getLogger(ItemService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        return itemTypesList;
        
    }
    
}
