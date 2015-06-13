/*
 SGR ALPHA - CONTROLLER PACKAGE
 File: MENUCONTROLLER.JAVA | Last Major Update: 25.05.2015
 Developer: Kevin Raian e Rafael Sousa
 IDINALOG REBORN © 2015
 */
package sgr.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import sgr.bean.ItemBean;
import sgr.bean.MovimentoBean;

import sgr.bean.ContaItemBean;
import sgr.bean.TableBean;
import sgr.dao.ExceptionDAO;
import sgr.dao.TableDAO;
import sgr.service.ItemService;
import sgr.service.MovimentoService;
import sgr.service.ContaItemService;

@SessionScoped
@ManagedBean(name = "menuController")

public class MenuController {

    @ManagedProperty(value = "#{clientController}")
    protected ClientController clientLogado;
    /* VARIAVEIS */
    private String selectedItemType = "Todos";
    private String itemName = "";
    private String itemType = "";
    private float itemPrice = 0;
    private float orderBuilderPrice = 0;
    boolean itemExists = false;
    // Beans
    private ItemBean orderBuilderItem = new ItemBean();
    private TableBean tableBean = new TableBean();

    private ContaItemBean contaItemBean = new ContaItemBean();
    // Lists
    private List<ItemBean> itemTypes = new ArrayList<ItemBean>();
    private List<ItemBean> itemList = new ArrayList<ItemBean>();
    // private List<OrderBuilderBean> orderBuilderList = new ArrayList<OrderBuilderBean>();
    private List<ItemBean> orderBuilderList = new ArrayList<ItemBean>();

    private List<MovimentoBean> listaMovimento = new ArrayList<MovimentoBean>();

    // Inicia Services
    private ItemService itemService = new ItemService();

    public MenuController() throws ExceptionDAO {

        itemTypes = itemService.searchItemTypes();
        setItemList(itemService.listItems(selectedItemType));

    }

    public void showSelectedTypeItems() throws ExceptionDAO {

        setItemList(itemService.listItems(selectedItemType));

    }

    public void clearOrderBuilder() {
        orderBuilderList.clear();
        System.out.println("Pedido Limpado.");
    }

    public void addOrderItem(ItemBean pItem) throws ExceptionDAO {
        orderBuilderItem = pItem;
        System.out.println("ITEM SELECIONADO: " + orderBuilderItem.getNome());
        orderBuilderList.add(orderBuilderItem);
    }

    // <editor-fold desc="GET and SET">
    public void deletar(ItemBean pItem) {
        // orderBuilderItem.setQuantidade(0);
        orderBuilderItem = pItem;
        orderBuilderList.remove(orderBuilderItem);

    }

    public void buildOrder() {

        ContaItemService orderItemService = new ContaItemService();
        System.out.println("***************************");
        System.out.println("NUMERO DA MESA:" + clientLogado.getTableBean().getNumero());
        System.out.println("NUMERO DA CONTA:" + clientLogado.getSessionBean().getCodigo());
        System.out.println("NUMERO CLIENTE CONTA" + clientLogado.getSessionBean().getC_codigo());
        System.out.println("NUMERO CPF CLIENTE CPF" + clientLogado.getClientBean().getCpf());
       
  
        contaItemBean.setContaCodigo(clientLogado.getSessionBean().getCodigo());
        contaItemBean.setClienteCodigo(clientLogado.getClientBean().getCodigo());
        contaItemBean.setClienteCpf(clientLogado.getClientBean().getCpf());
        contaItemBean.setMesaNumero(clientLogado.getTableBean().getNumero());
        tableBean.setNumero(clientLogado.getTableBean().getNumero());
        System.out.println("mesa11111111:" + tableBean.getNumero());
        tableBean.setStatus(true);
        TableDAO tableDAO = new TableDAO();
        tableDAO.ocuparMesa(tableBean);
 
      
        
        System.out.println("MESA FUNCIONARIO_CODIGO:" + clientLogado.getTableBean().getFuncionarioCodigo());
        System.out.println("MESA FUNCIONARIO_CPF:" + clientLogado.getTableBean().getFuncionarioCpf());
       
        
        contaItemBean.setFuncionarioCodigo(clientLogado.getTableBean().getFuncionarioCodigo());
        contaItemBean.setFuncionarioCpf(clientLogado.getTableBean().getFuncionarioCpf());
        
        contaItemBean.setData(new Date());
        
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
        System.out.println("Iniciando Pedido Item");
        System.out.println("PEDIDO NUMERO MESA: " + contaItemBean.getMesaNumero());
        System.out.println("PEDIDO CONTA_CODIGO: " + +contaItemBean.getContaCodigo());
        System.out.println("PEDIDO CLIENTE: " + contaItemBean.getClienteCodigo());
        System.out.println("PEDIDO CLIENTE CPF: " + contaItemBean.getClienteCpf());
        System.out.println("PEDIDO FUNCIONARIO: " + contaItemBean.getFuncionarioCodigo());
        System.out.println("PEDIDO FUNCIONARIO CPF: " + contaItemBean.getFuncionarioCpf());
        System.out.println("PEDIDO ITEM COD: " + contaItemBean.getItemCodigo());
        System.out.println("PEDIDO QTD: " + contaItemBean.getQuantidade());
        System.out.println("PEDIDO DATA: " + contaItemBean.getData());

        for (int i = 0; i < orderBuilderList.size(); i++) {
            contaItemBean.setItemCodigo(orderBuilderList.get(i).getCodigo());
            contaItemBean.setQuantidade(orderBuilderList.get(i).getQuantidade());
            contaItemBean.setStatus("Solicitado");
            orderItemService.salvarPedidoItem(contaItemBean);
        }
        
    
        MovimentoService movimentoService = new MovimentoService();
                //aqui eu recarrego a lista da pagina principal a lista Meus Pedidos
         clientLogado.listaMovimento = movimentoService.listarMovimentos(clientLogado.getClientBean().getCodigo(),clientLogado.getTableBean().getNumero());

        try {
            clientLogado.goTo("/mainclient.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void refleshPedidoGrid() {
        MovimentoService movimentoService = new MovimentoService();
        //aqui eu recarrego a lista da pagina principal a lista Meus Pedidos
        //clientLogado.listaMovimento = movimentoService.listarMovimentos(clientLogado.getClientBean().getCodigo(), orderBean.getMesaNumero());
    }

    public List<ItemBean> getItemTypes() {
        return itemTypes;
    }

    public void setItemTypes(List<ItemBean> itemTypes) {
        this.itemTypes = itemTypes;
    }

    public ItemService getItemService() {
        return itemService;
    }

    public void setItemService(ItemService itemService) {
        this.itemService = itemService;
    }

    public String getSelectedItemType() {
        return selectedItemType;
    }

    public void setSelectedItemType(String selectedItemType) {
        this.selectedItemType = selectedItemType;
    }
    // </editor-fold>

    public List<ItemBean> getItemList() {
        return itemList;
    }

    public void setItemList(List<ItemBean> itemList) {
        this.itemList = itemList;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public float getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(float itemPrice) {
        this.itemPrice = itemPrice;
    }

    public ItemBean getOrderBuilderItem() {
        return orderBuilderItem;
    }

    public void setOrderBuilderItem(ItemBean orderBuilderItem) {
        this.orderBuilderItem = orderBuilderItem;
    }

    public List<ItemBean> getOrderBuilderList() {
        return orderBuilderList;
    }

    public void setOrderBuilderList(List<ItemBean> orderBuilderList) {
        this.orderBuilderList = orderBuilderList;
    }

    public ClientController getClientLogado() {
        return clientLogado;
    }

    public void setClientLogado(ClientController clientLogado) {
        this.clientLogado = clientLogado;
    }

    public ContaItemBean getContaItemBean() {
        return contaItemBean;
    }

    public void setContaItemBean(ContaItemBean contaItemBean) {
        this.contaItemBean = contaItemBean;
    }

    public float getOrderBuilderPrice() {
        return orderBuilderPrice;
    }

    public void setOrderBuilderPrice(float orderBuilderPrice) {
        this.orderBuilderPrice = orderBuilderPrice;
    }

    public List<MovimentoBean> getListaMovimento() {
        return listaMovimento;
    }

    public void setListaMovimento(List<MovimentoBean> listaMovimento) {
        this.listaMovimento = listaMovimento;
    }

}
