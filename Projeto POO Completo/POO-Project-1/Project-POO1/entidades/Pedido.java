package entidades;


import entidades.pessoa.Cliente;
import entidades.pessoa.Funcionario;
import entidades.enums.StatusPedido;
import java.time.LocalDate;

public class Pedido {

    private int numPedido;
    private Cliente cliente;
    private double precoPedido;
    private LocalDate dataPedido;
    private StatusPedido statusPedido;
    private String formaPagamento;
    private Funcionario funcionario;

    public Pedido(int numPedido, Cliente cliente, double precoPedido, LocalDate dataPedido,
              StatusPedido statusPedido, String formaPagamento, Funcionario funcionario) {
    this.numPedido = numPedido;
    this.cliente = cliente;
    this.dataPedido = dataPedido;
    this.precoPedido = precoPedido;
    this.statusPedido = statusPedido;
    this.formaPagamento = formaPagamento;
    this.funcionario = funcionario;
}
    public int getNumPedido() {
        return numPedido;
    }

    public void setNumPedido(int numPedido) {
        this.numPedido = numPedido;
    }

    public Cliente getCliente() {
        return this.cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public double getPrecoPedido() {
        return precoPedido;
    }

    public void setPrecoPedido(double precoPedido) {
        this.precoPedido = precoPedido;
    }

    public LocalDate getDataPedido() {
        return dataPedido;
    }

    public void setDataPedido(LocalDate dataPedido) {
        this.dataPedido = dataPedido;
    }

    public StatusPedido getStatusPedido() {
        return statusPedido;
    }

    public void setStatusPedido(StatusPedido statusPedido) {
        this.statusPedido = statusPedido;
    }

    public String getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(String formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    @Override
    public String toString() {
        return "Pedido #" + numPedido +
                " | Cliente: " + cliente.getNome() +
                " | Funcionário: " + funcionario.getNome() +
                " | Status: " + statusPedido +
                " | Total: R$" + precoPedido;
    }
    public static void menuPedido() {


    }
}
