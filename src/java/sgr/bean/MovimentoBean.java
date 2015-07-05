/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sgr.bean;

import java.util.Date;

/**
 *
 * @author WASHINGTON
 */
public class MovimentoBean {

    private int clienteCodigo;
    private String clienteNome;
    private int contaCodigo;
    private boolean contaStatus;
    private String frag;
    private int mesaNumero;
    private String itemStatus;
    private int quantidade;
    private String item;
    private double preco;
    private int itemCodigo;
    private int contaItemCodigo;
    private Date data;

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }
    
    
    public int getContaItemCodigo() {
        return contaItemCodigo;
    }

    public void setContaItemCodigo(int contaItemCodigo) {
        this.contaItemCodigo = contaItemCodigo;
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

    public String getClienteNome() {
        return clienteNome;
    }

    public void setClienteNome(String clienteNome) {
        this.clienteNome = clienteNome;
    }

    public int getContaCodigo() {
        return contaCodigo;
    }

    public void setContaCodigo(int contaCodigo) {
        this.contaCodigo = contaCodigo;
    }

    public boolean isContaStatus() {
        return contaStatus;
    }

    public void setContaStatus(boolean contaStatus) {
        this.contaStatus = contaStatus;
    }

    public String getFrag() {
        return frag;
    }

    public void setFrag(String frag) {
        this.frag = frag;
    }

    public int getMesaNumero() {
        return mesaNumero;
    }

    public void setMesaNumero(int mesaNumero) {
        this.mesaNumero = mesaNumero;
    }

    public String getItemStatus() {
        return itemStatus;
    }

    public void setItemStatus(String itemStatus) {
        this.itemStatus = itemStatus;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }
    
    
}
