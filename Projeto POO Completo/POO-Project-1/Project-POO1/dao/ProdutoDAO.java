package dao;

import entidades.Produto;
import entidades.enums.CategoriaProduto;
import util.ConexaoBD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDAO {

    public void inserir(Produto produto) throws SQLException {
        if (!produto.validar()) {
            throw new IllegalArgumentException("Dados do produto inválidos: nome, preço e categoria são obrigatórios.");
        }
        String sql = "INSERT INTO Produto (nomeProduto, precoProduto, nomeCategoria) VALUES (?, ?, ?)";
        try (Connection con = ConexaoBD.obterConexao();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, produto.getNomeProduto());
            ps.setDouble(2, produto.getPrecoProduto());
            ps.setString(3, produto.getCategoria().toDB());
            ps.executeUpdate();
        }
    }

    public List<Produto> listarTodos() throws SQLException {
        List<Produto> lista = new ArrayList<>();
        String sql = "SELECT * FROM Produto";
        try (Connection con = ConexaoBD.obterConexao();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                lista.add(new Produto(
                    rs.getInt("idProduto"),
                    rs.getDouble("precoProduto"),
                    rs.getString("nomeProduto"),
                    CategoriaProduto.fromDB(rs.getString("nomeCategoria"))
                ));
            }
        }
        return lista;
    }

    public void atualizar(Produto produto) throws SQLException {
        if (!produto.validar()) {
            throw new IllegalArgumentException("Dados do produto inválidos: nome, preço e categoria são obrigatórios.");
        }
        String sql = "UPDATE Produto SET nomeProduto=?, precoProduto=?, nomeCategoria=? WHERE idProduto=?";
        try (Connection con = ConexaoBD.obterConexao();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, produto.getNomeProduto());
            ps.setDouble(2, produto.getPrecoProduto());
            ps.setString(3, produto.getCategoria().toDB());
            ps.setInt(4, produto.getIdProduto());
            ps.executeUpdate();
        }
    }

    public void deletar(int idProduto) throws SQLException {
        String sql = "DELETE FROM Produto WHERE idProduto=?";
        try (Connection con = ConexaoBD.obterConexao();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idProduto);
            ps.executeUpdate();
        }
    }
}
