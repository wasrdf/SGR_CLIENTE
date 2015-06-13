/*
    SGR ALPHA - BEAN PACKAGE
    File: EMPLOYEEBEAN.JAVA | Last Major Update: 22.05.2015
    Developer: Rafael Sousa
    IDINALOG REBORN © 2015
*/

package sgr.bean;

import java.util.Date;

public class EmployeeBean {
    
    private int codigo;
    private String nome;
    private Date data_nasc;
    private long tel_mov;
    private String funcao;
    private long cpf;
    private long rg;
    private String nome_usuario;
    private String senha;

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

    public Date getData_nasc() {
        return data_nasc;
    }

    public void setData_nasc(Date data_nasc) {
        this.data_nasc = data_nasc;
    }

    public long getTel_mov() {
        return tel_mov;
    }

    public void setTel_mov(int tel_mov) {
        this.tel_mov = tel_mov;
    }

    public String getFuncao() {
        return funcao;
    }

    public void setFuncao(String funcao) {
        this.funcao = funcao;
    }

    public long getCpf() {
        return cpf;
    }

    public void setCpf(int cpf) {
        this.cpf = cpf;
    }

    public long getRg() {
        return rg;
    }

    public void setRg(int rg) {
        this.rg = rg;
    }

    public String getNome_usuario() {
        return nome_usuario;
    }

    public void setNome_usuario(String nome_usuario) {
        this.nome_usuario = nome_usuario;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
    
          // </editor-fold>   
}
