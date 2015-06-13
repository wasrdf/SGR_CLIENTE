/*
    SGR ALPHA - BEAN PACKAGE
    File: ITEMBEAN.JAVA | Last Major Update: 12.05.2015
    Developer: Rafael Sousa
    IDINALOG REBORN © 2015
*/

package sgr.bean;

public class ItemBean {

    private int codigo;
    private String nome;
    private String composicao;
    private String tipo;
    private float preco;
    private int quantidade;
    
    // <editor-fold defaultstate="collapsed" desc="GET and SET"> 

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getComposicao() {
        return composicao;
    }

    public void setComposicao(String composicao) {
        this.composicao = composicao;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public float getPreco() {
        return preco;
    }

    public void setPreco(float preco) {
        this.preco = preco;
    }
    
        // </editor-fold>   

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

}
