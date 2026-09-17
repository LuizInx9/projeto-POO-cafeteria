package dao;

import entidades.Pedido;
import entidades.enums.StatusPedido;
import entidades.pessoa.Cliente;
import entidades.pessoa.Funcionario;
import util.ConexaoBD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PedidoDAO {

    public void inserir(Pedido pedido) throws SQLException {
        String sql = "INSERT INTO Pedido (cpfCliente, matriculaFuncionario, precoPedido, dataPedido, statusPedido, formaPagamento) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection con = ConexaoBD.obterConexao();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, pedido.getCliente().getCpf());
            ps.setInt(2, pedido.getFuncionario().getMatricula());
            ps.setDouble(3, pedido.getPrecoPedido());
            ps.setDate(4, Date.valueOf(pedido.getDataPedido()));
            ps.setString(5, pedido.getStatusPedido().name());
            ps.setString(6, pedido.getFormaPagamento());
            ps.executeUpdate();
        }
    }

    public List<Pedido> listarTodos() throws SQLException {
        List<Pedido> lista = new ArrayList<>();
        String sql = "SELECT * FROM Pedido";
        try (Connection con = ConexaoBD.obterConexao();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Cliente c = new Cliente(null, rs.getString("cpfCliente"), null);
                Funcionario f = new Funcionario(null, null, null, rs.getInt("matriculaFuncionario"), null);
                Pedido p = new Pedido(
                        rs.getInt("numPedido"),
                        c,
                        rs.getDouble("precoPedido"),
                        rs.getDate("dataPedido").toLocalDate(),
                        StatusPedido.valueOf(rs.getString("statusPedido")),
                        rs.getString("formaPagamento"),
                        f
                );
                lista.add(p);
            }
        }
        return lista;
    }

    public void atualizar(Pedido pedido) throws SQLException {
        String sql = "UPDATE Pedido SET statusPedido=?, formaPagamento=?, precoPedido=? WHERE numPedido=?";
        try (Connection con = ConexaoBD.obterConexao();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, pedido.getStatusPedido().name());
            ps.setString(2, pedido.getFormaPagamento());
            ps.setDouble(3, pedido.getPrecoPedido());
            ps.setInt(4, pedido.getNumPedido());
            ps.executeUpdate();
        }
    }

    public void deletar(int numPedido) throws SQLException {
        String sql = "DELETE FROM Pedido WHERE numPedido=?";
        try (Connection con = ConexaoBD.obterConexao();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, numPedido);
            ps.executeUpdate();
        }
    }
}
