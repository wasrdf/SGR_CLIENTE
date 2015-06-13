/*
 SGR ALPHA - DAO PACKAGE
 File: EMPLOYEEDAO.JAVA | Last Major Update: 22.05.2015
 Developer: Rafael Sousa
 IDINALOG REBORN © 2015
 */
package sgr.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import sgr.bean.EmployeeBean;
import sgr.sql.QueryBuilder;
import sgr.util.ConnectionBuilder;

public class EmployeeDAO {

    public List<EmployeeBean> loadClient(QueryBuilder query) throws SQLException, ClassNotFoundException, ExceptionDAO {
        ConnectionBuilder conexao = new ConnectionBuilder();
        Connection conn = conexao.getConnection();
        List<EmployeeBean> clientList = new ArrayList<EmployeeBean>();
        String sql = "SELECT * FROM funcionario " + query.buildQuery();

        System.out.println("[EMPLOYEE DAO] SQL being executed: '" + sql + "'.");

        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {

            EmployeeBean EmployeeBean = new EmployeeBean();

            EmployeeBean.setCodigo(rs.getInt("codigo"));
            EmployeeBean.setNome(rs.getString("nome"));
            EmployeeBean.setData_nasc(rs.getDate("DATA_NASC"));
            EmployeeBean.setCpf(rs.getInt("cpf"));
            EmployeeBean.setRg(rs.getInt("rg"));
            EmployeeBean.setTel_mov(rs.getInt("tel_movel"));
            EmployeeBean.setNome_usuario(rs.getString("nome_usuario"));
            EmployeeBean.setSenha(rs.getString("senha"));

            System.out.println("[EMPLOYEE DAO] Data fetched from SQL result: Codigo '" 
                    + EmployeeBean.getCodigo() + "', Nome '" + EmployeeBean.getNome() + "', Data Nasc. '"
                    + EmployeeBean.getData_nasc() + "', Endereço '" +  "', CPF '" + EmployeeBean.getCpf()
                    + "', RG '" + EmployeeBean.getRg() + "', Telefone Celular '" + EmployeeBean.getTel_mov() 
                    + "', Nome Usuário '" + EmployeeBean.getNome_usuario() + "', Senha '" + EmployeeBean.getSenha() + "'.");

            clientList.add(EmployeeBean);

        }

        rs.close();
        ps.close();
        conn.close();
        return clientList;

    }

}
