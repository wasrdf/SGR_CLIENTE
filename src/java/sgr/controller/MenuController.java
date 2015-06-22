/*
 SGR ALPHA - CONTROLLER PACKAGE
 File: MENUCONTROLLER.JAVA | Last Major Update: 25.05.2015
 Developer: Kevin Raian e Rafael Sousa
 IDINALOG REBORN © 2015
 */
package sgr.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import sgr.bean.ItemBean;
import sgr.bean.MovimentoBean;
import sgr.bean.ContaItemBean;
import sgr.bean.SessionBean;
import sgr.bean.TableBean;
import sgr.dao.ExceptionDAO;
import sgr.dao.TableDAO;
import sgr.service.ItemService;
import sgr.service.MovimentoService;
import sgr.service.ContaItemService;
import sgr.service.SessionService;

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
    private double subTotal;
    private double contaTotal;
    private List<SessionBean> listSession = new ArrayList<SessionBean>();

    private ContaItemBean contaItemBean = new ContaItemBean();
    // Lists
    private List<ItemBean> itemTypes = new ArrayList<ItemBean>();
    private List<ItemBean> itemList = new ArrayList<ItemBean>();
    private List<ItemBean> orderBuilderList = new ArrayList<ItemBean>();
    private List<MovimentoBean> listaMovimento = new ArrayList<MovimentoBean>();

    // Inicia Services
    private ItemService itemService = new ItemService();

    public MenuController() throws ExceptionDAO {

        itemTypes = itemService.searchItemTypes();
        setItemList(itemService.listItems(selectedItemType));

    }

    public void solicitarEncerramento() {
        TableDAO tableDAO = new TableDAO();
        boolean itemStatus = false;
        System.out.println("STATUS DA CONTA:" + clientLogado.getSessionBean().isStatus());
        for (int i = 0; i < clientLogado.listaMovimento.size(); i++) {
            if ((clientLogado.listaMovimento.get(i).getItemStatus().equals("Solicitado")) || (clientLogado.listaMovimento.get(i).getItemStatus().equals("Pronto"))) {
                itemStatus = false;
            } else {
                itemStatus = true;
            }
        }

        if (itemStatus == true) {

            tableBean.setNumero(clientLogado.tableBean.getNumero());
            tableBean.setFlag("Solicitado");
            tableDAO.gerenciarMesas(tableBean);

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Solicitação de encerramento foi encaminhada para o caixa,por favor aguarde alguns instantes.", ""));

        } else {

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Você não pode solicitar o encerramento pois existem itens que ainda não foram entregues.", ""));
        }

    }

    public void showSelectedTypeItems() throws ExceptionDAO {

        setItemList(itemService.listItems(selectedItemType));

    }

    public void solicitarCancelamento(MovimentoBean pMovimento) {
        ContaItemService contaItemService = new ContaItemService();

        if (pMovimento.getItemStatus().equals("Entregue")) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Você não pode cancelar o item pois o mesmo já foi entregue.", ""));
        } else {

            contaItemBean.setCodigo(pMovimento.getContaItemCodigo());
            System.out.println("codigo do item: " + contaItemBean.getItemCodigo());
            contaItemBean.setStatus("Cancelamento");

            contaItemService.solicitarCancelamento(contaItemBean);

            //if (contaItemService.solicitarCancelamento(contaItemBean) != null) {
            //  FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Sua solicitação de cancelamento foi enviada para o caixa favor aguardar alguns instantes.", ""));
            //} else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Ocorreu um erro inesperado ao tentar cancelar o item selecionado por favor tente novamente.", ""));
            //}

        }
    }

    public void clearOrderBuilder() {
        orderBuilderList.clear();
        System.out.println("Pedido Limpado.");
        subTotal = 0.00;
    }

    public void addOrderItem(ItemBean pItem) throws ExceptionDAO {
        boolean encontrou = false;
        //orderBuilderItem = pItem;
      
        for (int i = 0; i < orderBuilderList.size(); i++) {
            System.out.println(orderBuilderList.get(i).getQuantidade()+" - " + pItem.getQuantidade());
            if (orderBuilderList.get(i).getCodigo() == pItem.getCodigo()) {
                orderBuilderList.get(i).setQuantidade(orderBuilderList.get(i).getQuantidade() + 1);
                encontrou = true;
            }

        }
        if (encontrou == true) {
            orderBuilderList.remove(pItem);
            orderBuilderList.add(pItem);

        } else {
            pItem.setQuantidade(1);
            orderBuilderList.add(pItem);

        }

    }

    public void goTo(String page) throws IOException {
        FacesContext ctx = FacesContext.getCurrentInstance();
        FacesContext.getCurrentInstance().getExternalContext().redirect(ctx.getExternalContext().getRequestContextPath() + page);
    }

    // <editor-fold desc="GET and SET">
    public void deletar(ItemBean pItem) {
        // orderBuilderItem.setQuantidade(0);
        orderBuilderItem = pItem;

        orderBuilderList.remove(orderBuilderItem);

    }

    public void chamarGarcom() {
        TableDAO tableDAO = new TableDAO();
        tableBean.setNumero(clientLogado.tableBean.getNumero());
        tableBean.setFlagGarcom("SOLICITADO");
        tableDAO.gerenciarMesas(tableBean);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Solicitação enviada para o garçom,em breve iremos atende-lo", ""));
    }

    public void buildOrder() {
        SessionService sessionService = new SessionService();
        try {
            listSession = sessionService.doOpenedSessionInfoSearch(clientLogado.getClientBean().getCodigo(), 1);
        } catch (ExceptionDAO ex) {
            Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (!(listSession.isEmpty())) {

            System.out.println("status da conta:" + clientLogado.getSessionBean().isStatus());
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

            tableBean.setStatus(true);

            TableDAO tableDAO = new TableDAO();
            tableDAO.gerenciarMesas(tableBean);
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

            contaTotal = contaTotal + subTotal;
            MovimentoService movimentoService = new MovimentoService();
            //aqui eu recarrego a lista da pagina principal a lista Meus Pedidos
            clientLogado.listaMovimento = movimentoService.listarMovimentos(clientLogado.getClientBean().getCodigo(), clientLogado.getTableBean().getNumero());
            orderBuilderList = new ArrayList<ItemBean>();
            try {
                clientLogado.goTo("/mainclient.xhtml");
            } catch (IOException ex) {
                Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {
            try {
                clientLogado.goTo("/index.xhtml");
            } catch (IOException ex) {
                Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void atualizarValorTotal() {
        System.out.println("Ola");
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

    public double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(double subTotal) {
        this.subTotal = subTotal;
    }

    public double getContaTotal() {
        return contaTotal;
    }

    public void setContaTotal(double contaTotal) {
        this.contaTotal = contaTotal;
    }

}
