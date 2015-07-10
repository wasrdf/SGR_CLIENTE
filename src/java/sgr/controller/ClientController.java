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
import sgr.dao.SessionDAO;
import sgr.dao.TableDAO;
import sgr.service.MovimentoService;
import sgr.service.SessionService;
import sgr.service.TableService;
import sgr.util.Validacoes;

@SessionScoped
@ManagedBean(name = "clientController")
public class ClientController {

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
    SessionBean sessionBean = new SessionBean();
    ContaItemBean orderItemsBean = new ContaItemBean();
    // Lists
    List<ClientBean> listClient = new ArrayList<ClientBean>();
    List<TableBean> listTable = new ArrayList<TableBean>();
    List<SessionBean> listSession = new ArrayList<SessionBean>();
    
    private List<ContaItemBean> orderItemList = new ArrayList<ContaItemBean>();
    List<MovimentoBean> listaMovimento = new ArrayList<MovimentoBean>();
    List<MovimentoBean> listaHistorico = new ArrayList<MovimentoBean>();

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
        try {

            // ### 01 ###
            // Busca Cliente      
            System.out.println("[CLIENT CONTROLLER][02] Dados do cliente para execução em doLogin():  clientUsername '" + clientUsername + "' e clientPassword '" + clientPassword + "'.");

            listClient = clientService.doLogin(clientUsername, clientPassword);
            clientBean = listClient.get(0);

            // ### 02 ###
            // Identifica Mesa        
            //currentMac = MACReader.readMAC();
            System.out.println("[CLIENT CONTROLLER][01] MAC atual para execução em doTableSearch(): '" + currentMac + "'.");
            System.out.println("MINHA MAC = :" + currentMac);

            if (clientBean.getCodigo() == 7) {
                //mesa 12
                currentMac = "F4-6D-04-90-90-FA";
            } else {
                if (clientBean.getCodigo() == 10) {
                    //mesa 7
                    currentMac = "94-DE-80-70-D9-15";

                } else {
                    //mesa 5
                    currentMac = "68-17-29-0B-87-6D";

                }
            }
            listTable = tableService.doTableSearch(currentMac);
            tableBean = listTable.get(0);
            System.out.println("[CLIENT CONTROLLER][01] Mesa identificada: '" + tableBean.getNumero() + "'.");

            //aqui eu abro a MESA...
            tableBean.setStatus(true);
            System.out.println("MESA EM ABERTO: " + tableBean.getNumero());
            TableDAO tableDAO = new TableDAO();
            tableDAO.gerenciarMesas(tableBean);

            listaMovimento = movimentoService.listarMovimentos(listClient.get(0).getCodigo(), tableBean.getNumero());
            System.out.println("tamanho da lista: " + listaMovimento.size());

            
            // ### 03 ###
            // Valida dados do Cliente
            if (clientUsername.equals(clientBean.getNome_usuario()) && (clientPassword.equals(clientBean.getSenha()))) {

                System.out.println("[CLIENT CONTROLLER][03] Cliente encontrado!");
                System.out.println("[CLIENT CONTROLLER][03] Preparando seção...");

                // Inicia Session Service
                SessionService sessionService = new SessionService();

                // ### 04 ###
                // Abertura de Seção
                // @ 04.1 
                // Caso não existam seções abertas uma nova é iniciada
                if (sessionService.doOpenedSessionSearch(clientBean.getCodigo(), 1) == false) {

                    // Define dados da nova seção
                    sessionBean.setStatus(true);
                    sessionBean.setC_codigo(clientBean.getCodigo());
                    sessionBean.setC_cpf(clientBean.getCpf());

                    System.out.println("[CLIENT CONTROLLER][04.1] Dados carregados para nova seção: Código do Cliente '" + clientBean.getCodigo() + "' e CPF '"
                            + clientBean.getCpf() + "'.");

                    // Cria nova seção
                    sessionService.newSession(sessionBean);

                    // Adquiri dados da nova seção
                    listSession = sessionService.doOpenedSessionInfoSearch(clientBean.getCodigo(), 1);
                    sessionBean = listSession.get(0);

                    System.out.println("[CLIENT CONTROLLER][04.1] Dados da seção atual: Código da Seção '" + sessionBean.getCodigo() + "', Status '" + sessionBean.isStatus()
                            + "' e Código do Cliente '" + clientBean.getCodigo() + "'.");

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
                    System.out.println("[CLIENT CONTROLLER][04.2] Dados da seção atual: Código da Seção '" + sessionBean.getCodigo() + "', Status '" + sessionBean.isStatus()
                            + "' e Código do Cliente '" + clientBean.getCodigo() + "'.");
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
                FacesContext.getCurrentInstance().getExternalContext().redirect(ctx.getExternalContext().getRequestContextPath() + "/index.xhtml");
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Informações inválidas para acesso. Por favor, tente novamente!"));

            }

        } catch (Exception ex) {
            System.out.println("[CLIENT CONTROLLER] User not found or invalid access data.");
            //FacesContext.getCurrentInstance().getExternalContext().redirect(ctx.getExternalContext().getRequestContextPath() + "/index.xhtml");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Informações inválidas para acesso. Por favor, tente novamente!", ""));
        }

    }

    public void listarHistorico() {
        MovimentoService movimentoService = new MovimentoService();
        listaHistorico = movimentoService.listarHistorico(clientBean.getCodigo());
    }
    
    public void entrarTemporario() {

        // Inicia Services
        ClientService clientService = new ClientService();
        TableService tableService = new TableService();
        FacesContext ctx = FacesContext.getCurrentInstance();
        MovimentoService movimentoService = new MovimentoService();

        // Inicia HTTPSession
        session = (HttpSession) ctx.getExternalContext().getSession(false);
        try {
            // ### 01 ###
            // Identifica Mesa        
            //currentMac = MACReader.readMAC();
            System.out.println("[CLIENT CONTROLLER][01] MAC atual para execução em doTableSearch(): '" + currentMac + "'.");
            System.out.println("MINHA MAC = :" + currentMac);

            //mesa 7
            //currentMac = "94-DE-80-70-D9-15";
            //mesa 12
            //currentMac = "F4-6D-04-90-90-FA";
            //mesa 5
            currentMac = "68-17-29-0B-87-6D";
            listTable = tableService.doTableSearch(currentMac);
            tableBean = listTable.get(0);
            System.out.println("[CLIENT CONTROLLER][01] Mesa identificada: '" + tableBean.getNumero() + "'.");

            //aqui eu abro a MESA...
            tableBean.setStatus(true);
            System.out.println("MESA EM ABERTO: " + tableBean.getNumero());
            TableDAO tableDAO = new TableDAO();
            tableDAO.gerenciarMesas(tableBean);

            // ### 02 ###
            // Busca Cliente      
            System.out.println("[CLIENT CONTROLLER][02] Dados do cliente para execução em doLogin():  clientUsername '" + clientUsername + "' e clientPassword '" + clientPassword + "'.");

            //defino um usuário temporário
            listClient = clientService.doLogin("admin", "admin");
            clientBean = listClient.get(0);

            listaMovimento = movimentoService.listarMovimentos(listClient.get(0).getCodigo(), tableBean.getNumero());
            System.out.println("tamanho da lista: " + listaMovimento.size());

            // ### 03 ###
            // Valida dados do Cliente
            System.out.println("[CLIENT CONTROLLER][03] Cliente encontrado!");
            System.out.println("[CLIENT CONTROLLER][03] Preparando seção...");

            // Inicia Session Service
            SessionService sessionService = new SessionService();

            // ### 04 ###
            // Abertura de Seção
            // @ 04.1 
            // Caso não existam seções abertas uma nova é iniciada
            if (sessionService.doOpenedSessionSearch(clientBean.getCodigo(), 1) == false) {

                // Define dados da nova seção
                sessionBean.setStatus(true);
                sessionBean.setC_codigo(clientBean.getCodigo());
                sessionBean.setC_cpf(clientBean.getCpf());

                System.out.println("[CLIENT CONTROLLER][04.1] Dados carregados para nova seção: Código do Cliente '" + clientBean.getCodigo() + "' e CPF '"
                        + clientBean.getCpf() + "'.");

                // Cria nova seção
                sessionService.newSession(sessionBean);

                // Adquiri dados da nova seção
                listSession = sessionService.doOpenedSessionInfoSearch(clientBean.getCodigo(), 1);
                sessionBean = listSession.get(0);

                System.out.println("[CLIENT CONTROLLER][04.1] Dados da seção atual: Código da Seção '" + sessionBean.getCodigo() + "', Status '" + sessionBean.isStatus()
                        + "' e Código do Cliente '" + clientBean.getCodigo() + "'.");

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
                System.out.println("[CLIENT CONTROLLER][04.2] Dados da seção atual: Código da Seção '" + sessionBean.getCodigo() + "', Status '" + sessionBean.isStatus()
                        + "' e Código do Cliente '" + clientBean.getCodigo() + "'.");
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

        } catch (Exception ex) {
            System.out.println("[CLIENT CONTROLLER] User not found or invalid access data.");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Informações inválidas para acesso. Por favor, tente novamente!", ""));

        }

    }

    public void recarregarMovimentos() {
        SessionService sessionService = new SessionService();
        MovimentoService movimentoService = new MovimentoService();
        try {
            listaMovimento = movimentoService.listarMovimentos(clientBean.getCodigo(), tableBean.getNumero());
            listSession = sessionService.doOpenedSessionInfoSearch(clientBean.getCodigo(), 1);
            sessionBean = listSession.get(0);

        } catch (ExceptionDAO ex) {
            Logger.getLogger(ClientController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ClientController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // MÉTODO 02 - doLogout()
    // Realiza logout do usuário caso não exista pedidos realizados e fecha
    // a conta e seção atual.
    public void doLogout() throws SQLException, ExceptionDAO, ClassNotFoundException {

        TableDAO tableDAO = new TableDAO();
        SessionService sessionService = new SessionService();
        FacesContext fc = FacesContext.getCurrentInstance();
        MovimentoService movimentoService = new MovimentoService();
        listaMovimento = movimentoService.listarMovimentos(clientBean.getCodigo(), tableBean.getNumero());

        listSession = sessionService.doOpenedSessionInfoSearch(clientBean.getCodigo(), 1);
        boolean contaStatus = false;

        //verifica se a lista está vazia
        if (listaMovimento.isEmpty()) {
            HttpSession s = (HttpSession) fc.getExternalContext().getSession(false);
            s.invalidate();
            FacesContext ctx = FacesContext.getCurrentInstance();
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect(ctx.getExternalContext().getRequestContextPath() + "/index.xhtml");
                tableBean.setStatus(false);
                tableBean.setFlag("");
                tableDAO.gerenciarMesas(tableBean);
                SessionDAO sessionDAO = new SessionDAO();
                sessionBean.setStatus(false);
                sessionDAO.alterarConta(sessionBean);
                
            } catch (IOException ex) {
                Logger.getLogger(ClientController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {

            for (int i = 0; i < listaMovimento.size(); i++) {

                if ((listaMovimento.get(i).getItemStatus().equals("Entregue")) && sessionBean.isStatus() == false) {
                    contaStatus = true;
                } else {

                    contaStatus = false;
                }
            }

            if (contaStatus == false) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Você não pode sair do sistema,pois sua conta ainda não foi encerrada!", ""));
            } else {
                //sessionService.closeSession(sessionBean);

                HttpSession s = (HttpSession) fc.getExternalContext().getSession(false);
                s.invalidate();
                FacesContext ctx = FacesContext.getCurrentInstance();
                try {

                    FacesContext.getCurrentInstance().getExternalContext().redirect(ctx.getExternalContext().getRequestContextPath() + "/index.xhtml");
                } catch (IOException ex) {
                    Logger.getLogger(ClientController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
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
        Validacoes validacao = new Validacoes();

        if (Validacoes.isCPF(clientBean.getCpf().replace(".", "").replace("-", "")) == false) {
            System.out.println("cpf digitado" + clientBean.getCpf());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Número de CPF inválido"));
            return;

        } else {

            if (!Validacoes.validarEmail(clientBean.getEmail())) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Email inválido"));
                return;
            } else {

                if (clientService.salvar(clientBean) != null) {
                    
                     FacesContext ctx = FacesContext.getCurrentInstance();
                     FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Cliente foi cadastrado com sucesso! ", ""));
                   
                     clientBean = new ClientBean();
                } else {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Ocorreu um erro inesperado, por favor tente novamente."));
                }
            }
        }
    }

    public void salvar() {
        ClientService clientService = new ClientService();
        try {
            
        clientService.salvar(clientBean);
             FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Informações armazenadas com sucesso."));
        } catch (Exception e) {
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

    public void setSessionBean(SessionBean sessionBean) {
        this.sessionBean = sessionBean;
    }

    public SessionBean getSessionBean() {
        return sessionBean;
    }

    public void setTableBean(TableBean tableBean) {
        this.tableBean = tableBean;
    }

    public String getCurrentMac() {
        return currentMac;
    }

    public void setCurrentMac(String currentMac) {
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

    public boolean isSessionOpened() {
        return sessionOpened;
    }

    public void setSessionOpened(boolean sessionOpened) {
        this.sessionOpened = sessionOpened;
    }

    public ContaItemBean getOrderItemsBean() {
        return orderItemsBean;
    }

    public void setOrderItemsBean(ContaItemBean orderItemsBean) {
        this.orderItemsBean = orderItemsBean;
    }

    public List<ClientBean> getListClient() {
        return listClient;
    }

    public void setListClient(List<ClientBean> listClient) {
        this.listClient = listClient;
    }

    public List<TableBean> getListTable() {
        return listTable;
    }

    public void setListTable(List<TableBean> listTable) {
        this.listTable = listTable;
    }

    public List<SessionBean> getListSession() {
        return listSession;
    }

    public void setListSession(List<SessionBean> listSession) {
        this.listSession = listSession;
    }

    public List<MovimentoBean> getListaHistorico() {
        return listaHistorico;
    }

    public void setListaHistorico(List<MovimentoBean> listaHistorico) {
        this.listaHistorico = listaHistorico;
    }

    
}
