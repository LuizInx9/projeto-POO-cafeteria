package dao;

import entidades.pessoa.Funcionario;
import util.ConexaoBD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FuncionarioDAO {

    public void inserir(Funcionario funcionario) throws SQLException {
        if (!funcionario.validar()) {
            throw new IllegalArgumentException("Dados do funcionário inválidos: nome e cargo são obrigatórios.");
        }
        String sql = "INSERT INTO Funcionario (nomeFuncionario, cargo) VALUES (?, ?)";
        try (Connection con = ConexaoBD.obterConexao();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, funcionario.getNome());
            ps.setString(2, funcionario.getCargo());
            ps.executeUpdate();
        }
    }

    public List<Funcionario> listarTodos() throws SQLException {
        List<Funcionario> lista = new ArrayList<>();
        String sql = "SELECT * FROM Funcionario";
        try (Connection con = ConexaoBD.obterConexao();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(new Funcionario(
                    rs.getString("nomeFuncionario"),
                    null,
                    rs.getString("cargo"),
                    rs.getInt("matricula"),
                    null
                ));
            }
        }
        return lista;
    }

    public void atualizar(Funcionario funcionario) throws SQLException {
        if (!funcionario.validar()) {
            throw new IllegalArgumentException("Dados do funcionário inválidos: nome e cargo são obrigatórios.");
        }
        String sql = "UPDATE Funcionario SET nomeFuncionario=?, cargo=? WHERE matricula=?";
        try (Connection con = ConexaoBD.obterConexao();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, funcionario.getNome());
            ps.setString(2, funcionario.getCargo());
            ps.setInt(3, funcionario.getMatricula());
            ps.executeUpdate();
        }
    }

    public void deletar(int matricula) throws SQLException {
        String sql = "DELETE FROM Funcionario WHERE matricula=?";
        try (Connection con = ConexaoBD.obterConexao();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, matricula);
            ps.executeUpdate();
        }
    }
}
