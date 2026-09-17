package entidades;

import java.time.LocalDate;
import java.time.LocalTime;

public class Reserva {

    // ATRIBUTOS
    private LocalDate dataReserva;
    private LocalTime horaReserva;
    private int numReserva;
    private String cpfCliente;
    private int numMesa;

    // CONSTRUTOR
    public Reserva(LocalDate dataReserva,
                   LocalTime horaReserva,
                   int numReserva,
                   String cpfCliente,
                   int numMesa) {

        this.dataReserva = dataReserva;
        this.horaReserva = horaReserva;
        this.numReserva  = numReserva;
        this.cpfCliente  = cpfCliente;
        this.numMesa     = numMesa;
    }

    // GETTERS
    public LocalDate getDataReserva() {
        return dataReserva;
    }

    public LocalTime getHoraReserva() {
        return horaReserva;
    }

    public int getNumReserva() {
        return numReserva;
    }

    public String getCpfCliente() {
        return cpfCliente;
    }

    public int getNumMesa() {
        return numMesa;
    }

    // SETTERS
    public void setDataReserva(LocalDate dataReserva) {
        this.dataReserva = dataReserva;
    }

    public void setHoraReserva(LocalTime horaReserva) {
        this.horaReserva = horaReserva;
    }

    public void setNumReserva(int numReserva) {
        this.numReserva = numReserva;
    }

    public void setCpfCliente(String cpfCliente) {
        this.cpfCliente = cpfCliente;
    }

    public void setNumMesa(int numMesa) {
        this.numMesa = numMesa;
    }

    // TOSTRING
    @Override
    public String toString() {
        return "Reserva Nº " + numReserva +
                " | CPF Cliente: " + cpfCliente +
                " | Mesa: " + numMesa +
                " | Data: " + dataReserva +
                " | Hora: " + horaReserva;
    }
}
