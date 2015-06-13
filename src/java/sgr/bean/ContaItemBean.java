/*
    SGR ALPHA - BEAN PACKAGE
    File: ORDERITEMSBEAN.JAVA | Last Major Update: 13.05.2015
    Developer: Kevin Raian
    IDINALOG REBORN © 2015
*/

package sgr.bean;

import java.util.Date;

public class ContaItemBean {
   
    private int codigo;
    private int contaCodigo;
    private int mesaNumero;
    private int itemCodigo;
    private int clienteCodigo;
    private int funcionarioCodigo;
    private String clienteCpf;
    private String funcionarioCpf;
    private int quantidade;
    private String status;
    private Date data;

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public int getContaCodigo() {
        return contaCodigo;
    }

    public void setContaCodigo(int contaCodigo) {
        this.contaCodigo = contaCodigo;
    }

    public int getMesaNumero() {
        return mesaNumero;
    }

    public void setMesaNumero(int mesaNumero) {
        this.mesaNumero = mesaNumero;
    }

    public int getItemCodigo() {
        return itemCodigo;
    }

    public void setItemCodigo(int itemCodigo) {
        this.itemCodigo = itemCodigo;
    }

    public int getClienteCodigo() {
        return clienteCodigo;
    }

    public void setClienteCodigo(int clienteCodigo) {
        this.clienteCodigo = clienteCodigo;
    }

    public int getFuncionarioCodigo() {
        return funcionarioCodigo;
    }

    public void setFuncionarioCodigo(int funcionarioCodigo) {
        this.funcionarioCodigo = funcionarioCodigo;
    }

    public String getClienteCpf() {
        return clienteCpf;
    }

    public void setClienteCpf(String clienteCpf) {
        this.clienteCpf = clienteCpf;
    }

    public String getFuncionarioCpf() {
        return funcionarioCpf;
    }

    public void setFuncionarioCpf(String funcionarioCpf) {
        this.funcionarioCpf = funcionarioCpf;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

}
