/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sgr.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import sgr.bean.MovimentoBean;
import sgr.dao.ExceptionDAO;
import sgr.dao.MovimentoDAO;
import sgr.sql.QueryBuilder;
import sgr.sql.QueryGender;
import sgr.sql.QueryOperation;
import sgr.sql.QueryType;

/**
 *
 * @author WASHINGTON
 */
public class MovimentoService {
    
    //lista os itens na tela principal
    public List<MovimentoBean> listarMovimentos(int pCliente,int pMesa) {
        List<MovimentoBean> movimentos = new ArrayList<MovimentoBean>();
        QueryBuilder query = new QueryBuilder();
        
        query.addQuery(QueryOperation.empty, "vw_movimento.cliente_codigo", QueryGender.equal, String.valueOf(pCliente), QueryType.number);
        query.addQuery(QueryOperation.and, "vw_movimento.mesa_numero", QueryGender.equal, String.valueOf(pMesa), QueryType.number);
        query.addQuery(QueryOperation.and, "vw_movimento.conta_status", QueryGender.equal, String.valueOf(1), QueryType.number);
        query.addQuery(QueryOperation.and, "vw_movimento.item_status", QueryGender.different, "CANCELADO", QueryType.text);
        
        MovimentoDAO movimentoDAO = new MovimentoDAO();
        
        try {
            System.out.println("[movimentoservice] listando movimentos....");
            movimentos = movimentoDAO.listaMovimentos(query);
        } catch (ExceptionDAO ex) {
            System.out.println("erro ao tentar listar movimentos motivo: " + ex.getMessage());
            Logger.getLogger(MovimentoService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            System.out.println("erro ao tentar listar movimentos: " + ex.getSQLState());
            Logger.getLogger(MovimentoService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        return movimentos;
    }
   
    
     public List<MovimentoBean> listarHistorico(int pCliente) {
        List<MovimentoBean> movimentos = new ArrayList<MovimentoBean>();
        QueryBuilder query = new QueryBuilder();
        
        query.addQuery(QueryOperation.empty, "vw_movimento.cliente_codigo", QueryGender.equal, String.valueOf(pCliente), QueryType.number);
        query.addQuery(QueryOperation.and, "vw_movimento.item_status", QueryGender.different, "CANCELADO", QueryType.text);
        
        MovimentoDAO movimentoDAO = new MovimentoDAO();
        
        try {
            System.out.println("[movimentoservice] listando movimentos....");
            movimentos = movimentoDAO.listaMovimentos(query);
        } catch (ExceptionDAO ex) {
            System.out.println("erro ao tentar listar movimentos motivo: " + ex.getMessage());
            Logger.getLogger(MovimentoService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            System.out.println("erro ao tentar listar movimentos: " + ex.getSQLState());
            Logger.getLogger(MovimentoService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        return movimentos;
    }
   
   
}
