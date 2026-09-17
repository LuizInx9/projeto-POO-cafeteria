package dao;

import entidades.Mesa;
import entidades.enums.StatusMesa;
import util.ConexaoBD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MesaDAO {

    public void inserir(Mesa mesa) throws SQLException {
        String sql = "INSERT INTO Mesa (capacidadeMesa, statusMesa) VALUES (?, ?)";
        try (Connection con = ConexaoBD.obterConexao();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, mesa.getCapacidadeMesa());
            ps.setString(2, mesa.getStatusMesa().name());
            ps.executeUpdate();
        }
    }

    public List<Mesa> listarTodos() throws SQLException {
        List<Mesa> lista = new ArrayList<>();
        String sql = "SELECT * FROM Mesa";
        try (Connection con = ConexaoBD.obterConexao();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Mesa m = new Mesa(
                        rs.getInt("numMesa"),
                        rs.getInt("capacidadeMesa"),
                        StatusMesa.valueOf(rs.getString("statusMesa"))
                );
                lista.add(m);
            }
        }
        return lista;
    }

    public void atualizar(Mesa mesa) throws SQLException {
        String sql = "UPDATE Mesa SET capacidadeMesa=?, statusMesa=? WHERE numMesa=?";
        try (Connection con = ConexaoBD.obterConexao();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, mesa.getCapacidadeMesa());
            ps.setString(2, mesa.getStatusMesa().name());
            ps.setInt(3, mesa.getNumMesa());
            ps.executeUpdate();
        }
    }

    public void deletar(int numMesa) throws SQLException {
        String sql = "DELETE FROM Mesa WHERE numMesa=?";
        try (Connection con = ConexaoBD.obterConexao();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, numMesa);
            ps.executeUpdate();
        }
    }
}
