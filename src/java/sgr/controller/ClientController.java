/*
    SGR ALPHA - CONTROLLER PACKAGE
    File: CLIENTCONTROLLER.JAVA | Last Major Update: 29.04.2015
    Developer: Kevin Raian, Washington Reis
    IDINALOG REBORN © 2015
*/

package sgr.controller;
import sgr.bean.ClientBean;
import sgr.service.ClientService;
import java.io.IOException;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import sgr.bean.MovimentoBean;
import sgr.bean.SessionBean;
import sgr.bean.TableBean;
import sgr.bean.ContaItemBean;
import sgr.dao.ExceptionDAO;
import sgr.service.MovimentoService;
import sgr.service.SessionService;
import sgr.service.TableService;

@SessionScoped
@ManagedBean(name="clientController")

public class  ClientController {
    
    /* VARIAVEIS */
    // Formulário de Login 
    private String clientUsername = "";
    private String clientPassword = "";
    private String novaSenha = "";
    Integer tela = 0;
    
    // MAC Reader
    private String currentMac = "";
    
    // Métodos Específicos
    private boolean sessionOpened;    
    String currentDate = "";
    
    // HTTP Session
    private HttpSession session;
    
    // Beans    
    ClientBean clientBean = new ClientBean();    
    ClientBean clienteAtualizado = new ClientBean();    
    TableBean tableBean = new TableBean();
    MovimentoBean movimento = new MovimentoBean();
    
    
   
    ContaItemBean orderItemsBean = new ContaItemBean();
    SessionBean sessionBean = new SessionBean();
    // Lists
    List<ClientBean> listClient = new ArrayList<ClientBean>();
    List<TableBean> listTable = new ArrayList<TableBean>();
    List<SessionBean> listSession = new ArrayList<SessionBean>();
    
    private List<ContaItemBean> orderItemList = new ArrayList<ContaItemBean>();
    List<MovimentoBean> listaMovimento = new ArrayList<MovimentoBean>();
    
    /* MÉTODOS */
    // MÉTODO 01 - doLogin()
    // Realiza login do usuário cofigurando todos os parâmetros necesários
    // para iniciar uma nova seção.
    public void doLogin() throws ExceptionDAO, UnknownHostException, SQLException, IOException {

        // Inicia Services
        ClientService clientService = new ClientService();
        TableService tableService = new TableService();
        FacesContext ctx = FacesContext.getCurrentInstance();
        MovimentoService movimentoService = new MovimentoService();
       
        
        // Inicia HTTPSession
        session = (HttpSession) ctx.getExternalContext().getSession(false);
        
        // ### 01 ###
        // Identifica Mesa        
        //currentMac = MACReader.readMAC();
        System.out.println("[CLIENT CONTROLLER][01] MAC atual para execução em doTableSearch(): '" + currentMac + "'.");
        System.out.println("MINHA MAC = :" + currentMac);
       
        
        
        currentMac = "94-DE-80-70-D9-15";
        
        listTable = tableService.doTableSearch(currentMac);
        tableBean = listTable.get(0);
        System.out.println("[CLIENT CONTROLLER][01] Mesa identificada: '" + tableBean.getNumero() + "'.");
        
           
        
        // ### 02 ###
        // Busca Cliente      
        System.out.println("[CLIENT CONTROLLER][02] Dados do cliente para execução em doLogin():  clientUsername '" + clientUsername + "' e clientPassword '" + clientPassword + "'.");
        listClient = clientService.doLogin(clientUsername, clientPassword);
        clientBean = listClient.get(0);    
        
            listaMovimento = movimentoService.listarMovimentos(listClient.get(0).getCodigo(), tableBean.getNumero());
          System.out.println("tamanho da lista: " + listaMovimento.size());
      
              
        // ### 03 ###
        // Valida dados do Cliente
        if(clientUsername.equals(clientBean.getNome_usuario()) && (clientPassword.equals(clientBean.getSenha()))) {

            System.out.println("[CLIENT CONTROLLER][03] Cliente encontrado!");
            System.out.println("[CLIENT CONTROLLER][03] Preparando seção...");

            // Inicia Session Service
            SessionService sessionService = new SessionService();            
            
            // ### 04 ###
            // Abertura de Seção
            
            // @ 04.1 
            // Caso não existam seções abertas uma nova é iniciada
            if(sessionService.doOpenedSessionSearch(clientBean.getCodigo(), 1) == false) {

                // Define dados da nova seção
                sessionBean.setStatus(true);
                sessionBean.setC_codigo(clientBean.getCodigo());
                sessionBean.setC_cpf(clientBean.getCpf());

                System.out.println("[CLIENT CONTROLLER][04.1] Dados carregados para nova seção: Código do Cliente '" + clientBean.getCodigo() + "' e CPF '" +
                        clientBean.getCpf() + "'.");
                
           
                // Cria nova seção
                sessionService.newSession(sessionBean);

                // Adquiri dados da nova seção
                listSession = sessionService.doOpenedSessionInfoSearch(clientBean.getCodigo(), 1);
                sessionBean = listSession.get(0);
                    
                System.out.println("[CLIENT CONTROLLER][04.1] Dados da seção atual: Código da Seção '" + sessionBean.getCodigo() + "', Status '" + sessionBean.isStatus()+
                        "' e Código do Cliente '" + clientBean.getCodigo() + "'."); 
                       
                  
                
                // Salva dados atuais no HTTPSession
                session.setAttribute("currentClientName", clientBean.getNome());
                session.setAttribute("currentUserCode", clientBean.getCodigo());
                session.setAttribute("currentTable", tableBean.getNumero());
                session.setAttribute("currentSessionCode", sessionBean.getCodigo());
                
            // @ 04.2
            // Caso uma seção aberta seja encontrada ela é recuperada
            } else {

                System.out.println("[CLIENT CONTROLLER][04.2] Seção aberta encontrada! Restaurando seção...");
                // Recupera dados da seção aberta
                listSession = sessionService.doOpenedSessionInfoSearch(clientBean.getCodigo(), 1);
                sessionBean = listSession.get(0);     
                System.out.println("[CLIENT CONTROLLER][04.2] Dados da seção atual: Código da Seção '" + sessionBean.getCodigo() + "', Status '" + sessionBean.isStatus() +
                        "' e Código do Cliente '" + clientBean.getCodigo() + "'.");                  
                System.out.println("[CLIENT CONTROLLER[04.2] Seção restaurada com sucesso!");
                
                // Salva dados atuais no HTTPSession
                session.setAttribute("currentClientName", clientBean.getNome());
                session.setAttribute("currentUserCode", clientBean.getCodigo());
                session.setAttribute("currentTable", tableBean.getNumero());
                session.setAttribute("currentSessionCode", sessionBean.getCodigo());
               
            }

            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect(ctx.getExternalContext().getRequestContextPath() + "/mainclient.xhtml");

                } catch (IOException ex) {
                    System.out.println("[CLIENT CONTROLLER] ERRO: Não foi possível encontrar a página especificada.");
                    System.out.println(ex.getMessage());
                }


        } else {

            System.out.println("[CLIENT CONTROLLER] User not found or invalid access data.");        
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Informações inválidas para acesso. Por favor, tente novamente!"));
            FacesContext.getCurrentInstance().getExternalContext().redirect(ctx.getExternalContext().getRequestContextPath() + "/index.xhtml");
        }

    }
 
          public void recarregarMovimentos() {
          MovimentoService movimentoService = new MovimentoService();
          listaMovimento = movimentoService.listarMovimentos(clientBean.getCodigo(), tableBean.getNumero());
          System.out.println("tamanho da lista: " + listaMovimento.size());
      
    }
    
   
    // MÉTODO 02 - doLogout()
    // Realiza logout do usuário caso não exista pedidos realizados e fecha
    // a conta e seção atual.
    public void doLogout() throws SQLException, ExceptionDAO, ClassNotFoundException {
        
        SessionService sessionService = new SessionService();
        FacesContext fc = FacesContext.getCurrentInstance();
        
        listSession = sessionService.doOpenedSessionInfoSearch(clientBean.getCodigo(), 1);
        sessionBean = listSession.get(0); 
        
        sessionService.closeSession(sessionBean);
        
        HttpSession s = (HttpSession) fc.getExternalContext().getSession(false);
        s.invalidate();
        FacesContext ctx = FacesContext.getCurrentInstance();
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect(ctx.getExternalContext().getRequestContextPath() + "/index.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(ClientController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // MÉTODO 03 - goTo()
    // Método para navegação entre páginas.
    public void goTo(String page) throws IOException {
        FacesContext ctx = FacesContext.getCurrentInstance();
        FacesContext.getCurrentInstance().getExternalContext().redirect(ctx.getExternalContext().getRequestContextPath() + page);
    }
    //método 04 - updateClient()
    public void salvarClient() {
        System.out.println("dados digitados: " + clientBean.getBairro());
        System.out.println("entro no metodo");
        ClientService clientService = new ClientService();
        System.out.println(clientBean.getCodigo());
       
        if(clientService.salvar(clientBean) != null) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Informações salvas!"));
        } else {
           FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Ocorreu um erro inesperado, por favor tente novamente."));   
        }
    }
  
    public void pedidoSelecionado(MovimentoBean pMovimento) {
        System.out.println("entrou no metodo...");
       movimento = pMovimento;
       tela = 1;
        
    }
    
    public void mudarTela(Integer pTela) {
        tela = pTela;
    }
    
    public ClientController() {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Date dataAtual = Calendar.getInstance().getTime();
        currentDate = df.format(dataAtual);
    }
    
    // <editor-fold desc="GET and SET" defaultstate="collapsed">

    public String getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
    }

    
    public String getClientUsername() {
        return clientUsername;
    }

    public void setClientUsername(String clientUsername) {
        this.clientUsername = clientUsername;
    }

    public String getClientPassword() {
        return clientPassword;
    }

    public void setClientPassword(String clientPassword) {
        this.clientPassword = clientPassword;
    }

    public ClientBean getClientBean() {
        return clientBean;
    }

    public void setClientBean(ClientBean clientBean) {
        this.clientBean = clientBean;
    }
    
    public TableBean getTableBean() {
        return tableBean;
    }
    
    public void setSessionBean(SessionBean sessionBean){
        this.sessionBean = sessionBean;
    }
    
    public SessionBean getSessionBean(){
        return sessionBean;
    }
    
    public void setTableBean(TableBean tableBean) {
        this.tableBean = tableBean;
    }
    
    public String getCurrentMac(){
        return currentMac;
    }
    
    public void setCurrentMac(String currentMac){
        this.currentMac = currentMac;
    }
    

    public HttpSession getSession() {
        return session;
    }

    public void setSession(HttpSession session) {
        this.session = session;
    }   
    

    // </editor-fold>   

    public List<ContaItemBean> getOrderItemList() {
        return orderItemList;
    }

    public void setOrderItemList(List<ContaItemBean> orderItemList) {
        this.orderItemList = orderItemList;
    }

    public ClientBean getClienteAtualizado() {
        return clienteAtualizado;
    }

    public void setClienteAtualizado(ClientBean clienteAtualizado) {
        this.clienteAtualizado = clienteAtualizado;
    }

    public String getNovaSenha() {
        return novaSenha;
    }

    public void setNovaSenha(String novaSenha) {
        this.novaSenha = novaSenha;
    }

    public List<MovimentoBean> getListaMovimento() {
        return listaMovimento;
    }

    public void setListaMovimento(List<MovimentoBean> listaMovimento) {
        this.listaMovimento = listaMovimento;
    }

    public MovimentoBean getMovimento() {
        return movimento;
    }

    public void setMovimento(MovimentoBean movimento) {
        this.movimento = movimento;
    }

    public Integer getTela() {
        return tela;
    }

    public void setTela(Integer tela) {
        this.tela = tela;
    }

    
    
}
