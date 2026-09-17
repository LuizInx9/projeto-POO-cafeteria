package dao;

import entidades.pessoa.Cliente;
import util.ConexaoBD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {

    public void inserir(Cliente cliente) throws SQLException {
        if (!cliente.validar()) {
            throw new IllegalArgumentException("Dados do cliente inválidos: nome e CPF são obrigatórios.");
        }
        String sql = "INSERT INTO Cliente (cpf, nomeCliente, telefone) VALUES (?, ?, ?)";
        try (Connection con = ConexaoBD.obterConexao();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, cliente.getCpf());
            ps.setString(2, cliente.getNome());
            ps.setString(3, cliente.getTelefone());
            ps.executeUpdate();
        }
    }

    public List<Cliente> listarTodos() throws SQLException {
        List<Cliente> lista = new ArrayList<>();
        String sql = "SELECT * FROM Cliente";
        try (Connection con = ConexaoBD.obterConexao();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(new Cliente(
                    rs.getString("nomeCliente"),
                    rs.getString("cpf"),
                    rs.getString("telefone")
                ));
            }
        }
        return lista;
    }

    public void atualizar(Cliente cliente) throws SQLException {
        if (!cliente.validar()) {
            throw new IllegalArgumentException("Dados do cliente inválidos: nome e CPF são obrigatórios.");
        }
        String sql = "UPDATE Cliente SET nomeCliente=?, telefone=? WHERE cpf=?";
        try (Connection con = ConexaoBD.obterConexao();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, cliente.getNome());
            ps.setString(2, cliente.getTelefone());
            ps.setString(3, cliente.getCpf());
            ps.executeUpdate();
        }
    }

    public void deletar(String cpf) throws SQLException {
        String sql = "DELETE FROM Cliente WHERE cpf=?";
        try (Connection con = ConexaoBD.obterConexao();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, cpf);
            ps.executeUpdate();
        }
    }
}
