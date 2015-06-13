/*
 SGR ALPHA - DAO PACKAGE
 File: CLIENTDAO.JAVA | Last Major Update: 30.04.2015
 Developer: Kevin Raian, Washington Reis
 IDINALOG REBORN © 2015
 */
package sgr.dao;

import sgr.bean.ClientBean;
import sgr.sql.QueryBuilder;
import sgr.util.ConnectionBuilder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClientDAO {

    public List<ClientBean> loadClient(QueryBuilder query) throws SQLException, ClassNotFoundException, ExceptionDAO {

        ConnectionBuilder conexao = new ConnectionBuilder();
        Connection conn = conexao.getConnection();
        List<ClientBean> clientList = new ArrayList<ClientBean>();
        String sql = "SELECT * FROM cliente " + query.buildQuery();

        System.out.println("[CLIENT DAO] SQL being executed: '" + sql + "'.");

        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {

            ClientBean clientBean = new ClientBean();

            clientBean.setCodigo(rs.getInt("codigo"));
            clientBean.setNome(rs.getString("nome"));
            clientBean.setData_nasc(rs.getDate("DATA_NASC"));
            clientBean.setEndereco(rs.getString("endereco"));
            clientBean.setNumero(rs.getInt("numero"));
            clientBean.setComplemento(rs.getString("complemento"));
            clientBean.setCidade(rs.getString("cidade"));
            clientBean.setBairro(rs.getString("Bairro"));
            clientBean.setEstado(rs.getString("estado"));
            clientBean.setCpf(rs.getString("cpf"));
            clientBean.setRg(rs.getString("rg"));
            clientBean.setTel_res(rs.getString("tel_residencial"));
            clientBean.setTel_mov(rs.getString("tel_movel"));
            clientBean.setData_nasc(rs.getDate("DATA_NASC"));
            clientBean.setEmail(rs.getString("email"));
            clientBean.setNome_usuario(rs.getString("nome_usuario"));
            clientBean.setSenha(rs.getString("senha"));

            System.out.println("[CLIENT DAO] Data fetched from SQL result: Codigo '" + clientBean.getCodigo() + "', Nome '" + clientBean.getNome() + "', Data Nasc. '"
                    + clientBean.getData_nasc() + "', Endereço '" + clientBean.getEndereco() + "', Numero '" + clientBean.getNumero() + "', Complemento '" + clientBean.getComplemento()
                    + "', Cidade '" + clientBean.getCidade() + "', Bairro '" + clientBean.getBairro() + "', Estado '" + clientBean.getEstado() + "', CPF '" + clientBean.getCpf()
                    + "', RG '" + clientBean.getRg() + "', Telefone Residêncial '" + clientBean.getTel_res() + "', Telefone Celular '" + clientBean.getTel_mov() + "', Email '"
                    + clientBean.getEmail() + "', Nome Usuário '" + clientBean.getNome_usuario() + "', Senha '" + clientBean.getSenha() + "'.");

            clientList.add(clientBean);

        }

        rs.close();
        ps.close();
        conn.close();
        return clientList;

    }

    public void atualizarCliente(ClientBean pClient) throws SQLException {

        ConnectionBuilder conexao = new ConnectionBuilder();
        Connection conn = conexao.getConnection();

        String sql = "UPDATE cliente SET endereco=?,numero=?,complemento=?,"
                + "cidade=?,bairro=?,estado=?,tel_residencial=?,tel_movel=?,"
                + "email=?,situacao=?,senha=? where codigo=?";
        System.out.println(pClient.getEndereco());
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setString(1, pClient.getEndereco());
        ps.setInt(2, pClient.getNumero());
        ps.setString(3, pClient.getComplemento());
        ps.setString(4, pClient.getCidade());
        ps.setString(5, pClient.getBairro());
        ps.setString(6, pClient.getEstado());
        ps.setString(7, pClient.getTel_res());
        ps.setString(8, pClient.getTel_mov());
        ps.setString(9, pClient.getEmail());
        ps.setString(10, pClient.getSituacao());
        ps.setString(11, pClient.getSenha());
        ps.setInt(12, pClient.getCodigo());
        ps.execute();
        ps.close();
        conn.close();
    }

    public void inserirCliente(ClientBean pCliente) throws SQLException {
        ConnectionBuilder conexao = new ConnectionBuilder();
        Connection conn = conexao.getConnection();

        String sql = "insert into cliente (nome,data_nasc,endereco,numero,complemento,cidade,bairro,"
                + "estado,cpf,rg,tel_residencial,tel_movel,email,nome_usuario,senha,situacao) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement ps = conn.prepareStatement(sql);

        ps.setString(1, pCliente.getNome());
        ps.setDate(2, new java.sql.Date(pCliente.getData_nasc().getTime()));
        ps.setString(3, pCliente.getEndereco());
        ps.setInt(4, pCliente.getNumero());
        ps.setString(5, pCliente.getComplemento());
        ps.setString(6, pCliente.getCidade());
        ps.setString(7, pCliente.getBairro());
        ps.setString(8, pCliente.getEstado());
        ps.setString(9, pCliente.getCpf());
        ps.setString(10, pCliente.getRg());
        ps.setString(11, pCliente.getTel_res());
        ps.setString(12, pCliente.getTel_mov());
        ps.setString(13, pCliente.getEmail());
        ps.setString(14, pCliente.getNome_usuario());
        ps.setString(15, pCliente.getSenha());
        ps.setString(16, "Ativo");
        ps.execute();
        ps.close();
        conn.close();

    }

}
