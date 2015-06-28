/*
    SGR ALPHA - BEAN PACKAGE
    File: SESSIONBEAN.JAVA | Last Major Update: 05.05.2015
    Developer: Kevin Raian, Rafael Sousa
    IDINALOG REBORN © 2015
*/

package sgr.bean;

public class SessionBean {
    
    private int codigo;
    private boolean status;
    private int c_codigo;
    private String c_cpf;
    private double total;

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
    
    

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

 
    public int getC_codigo() {
        return c_codigo;
    }

    public void setC_codigo(int c_codigo) {
        this.c_codigo = c_codigo;
    }

    public String getC_cpf() {
        return c_cpf;
    }

    public void setC_cpf(String c_cpf) {
        this.c_cpf = c_cpf;
    }
    
    
}
