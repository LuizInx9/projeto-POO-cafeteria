package dao;

import entidades.Reserva;
import util.ConexaoBD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReservaDAO {

    public void inserir(Reserva reserva) throws SQLException {
        String sql = "INSERT INTO Reserva (cpfCliente, numMesa, datareserva, horaReserva) VALUES (?, ?, ?, ?)";
        try (Connection con = ConexaoBD.obterConexao();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, reserva.getCpfCliente());
            ps.setInt(2, reserva.getNumMesa());
            ps.setDate(3, Date.valueOf(reserva.getDataReserva()));
            ps.setTime(4, Time.valueOf(reserva.getHoraReserva()));
            ps.executeUpdate();
        }
    }

    public List<Reserva> listarTodos() throws SQLException {
        List<Reserva> lista = new ArrayList<>();
        String sql = "SELECT * FROM Reserva";
        try (Connection con = ConexaoBD.obterConexao();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Reserva r = new Reserva(
                        rs.getDate("datareserva").toLocalDate(),
                        rs.getTime("horaReserva").toLocalTime(),
                        rs.getInt("numReserva"),
                        rs.getString("cpfCliente"),
                        rs.getInt("numMesa")
                );
                lista.add(r);
            }
        }
        return lista;
    }

    public void atualizar(Reserva reserva) throws SQLException {
        String sql = "UPDATE Reserva SET datareserva=?, horaReserva=? WHERE numReserva=?";
        try (Connection con = ConexaoBD.obterConexao();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setDate(1, Date.valueOf(reserva.getDataReserva()));
            ps.setTime(2, Time.valueOf(reserva.getHoraReserva()));
            ps.setInt(3, reserva.getNumReserva());
            ps.executeUpdate();
        }
    }

    public void deletar(int numReserva) throws SQLException {
        String sql = "DELETE FROM Reserva WHERE numReserva=?";
        try (Connection con = ConexaoBD.obterConexao();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, numReserva);
            ps.executeUpdate();
        }
    }
}
