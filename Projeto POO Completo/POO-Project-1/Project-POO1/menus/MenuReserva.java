package menus;
import dao.ClienteDAO;
import dao.MesaDAO;
import dao.ReservaDAO;
import entidades.Mesa;
import entidades.Reserva;
import entidades.pessoa.Cliente;
import util.Escritor;
import util.Leitor;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class MenuReserva {
    public static void menuReserva(Leitor leitor, Escritor escritor) {
        ReservaDAO reservaDAO = new ReservaDAO();
        ClienteDAO clienteDAO = new ClienteDAO();
        MesaDAO mesaDAO = new MesaDAO();

        boolean executandoReserva = true;
        while (executandoReserva) {
            escritor.exibirSucesso("Menu Reserva Selecionado! ");
            escritor.exibirTitulo("-- MENU RESERVAS --");
            escritor.escreverLinha("1. Cadastrar nova reserva");
            escritor.escreverLinha("2. Atualizar dados da reserva");
            escritor.escreverLinha("3. Lista reservas");
            escritor.escreverLinha("4. Excluir reserva");
            escritor.escreverLinha("5. Sair");
            escritor.escrever("Escolha uma opção: ");

            int opcaoReserva = leitor.lerInteiro();

            switch (opcaoReserva) {
                case 1:
                    escritor.exibirTitulo("-- NOVA RESERVA --");
                    try {
                        List<Cliente> clientes = clienteDAO.listarTodos();
                        if (clientes.isEmpty()) { escritor.exibirErro("Nenhum cliente cadastrado!"); break; }
                        escritor.exibirTitulo("Selecione o Cliente");
                        for (int i = 0; i < clientes.size(); i++) escritor.escreverLinha((i + 1) + " - " + clientes.get(i));
                        escritor.escrever("Escolha o cliente: ");
                        int indiceCliente = leitor.lerInteiro() - 1;
                        if (indiceCliente < 0 || indiceCliente >= clientes.size()) { escritor.exibirErro("Cliente inválido!"); break; }
                        Cliente clienteSelecionado = clientes.get(indiceCliente);

                        List<Mesa> mesas = mesaDAO.listarTodos();
                        if (mesas.isEmpty()) { escritor.exibirErro("Nenhuma mesa cadastrada!"); break; }
                        escritor.exibirTitulo("Selecione a Mesa");
                        for (int i = 0; i < mesas.size(); i++) escritor.escreverLinha((i + 1) + " - " + mesas.get(i));
                        escritor.escrever("Escolha a mesa: ");
                        int indiceMesa = leitor.lerInteiro() - 1;
                        if (indiceMesa < 0 || indiceMesa >= mesas.size()) { escritor.exibirErro("Mesa inválida!"); break; }
                        Mesa mesaSelecionada = mesas.get(indiceMesa);

                        escritor.escrever("Digite a data (dd/MM/yyyy): ");
                        String textoData = leitor.lerString();
                        LocalDate dataReserva;
                        try {
                            dataReserva = LocalDate.parse(textoData, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                        } catch (Exception e) { escritor.exibirErro("Formato de data inválido!"); break; }

                        escritor.escrever("Digite a hora (HH:mm): ");
                        String textoHora = leitor.lerString();
                        LocalTime horaReserva;
                        try {
                            horaReserva = LocalTime.parse(textoHora, DateTimeFormatter.ofPattern("HH:mm"));
                        } catch (Exception e) { escritor.exibirErro("Formato de hora inválido!"); break; }

                        List<Reserva> reservas = reservaDAO.listarTodos();
                        Reserva novaReserva = new Reserva(dataReserva, horaReserva, reservas.size() + 1, clienteSelecionado.getCpf(), mesaSelecionada.getNumMesa());
                        reservaDAO.inserir(novaReserva);
                        escritor.exibirSucesso("Reserva cadastrada com sucesso!");
                    } catch (SQLException e) {
                        escritor.exibirErro("Erro ao cadastrar: " + e.getMessage());
                    }
                    break;

                case 2:
                    escritor.exibirTitulo("-- ATUALIZAR RESERVA --");
                    try {
                        List<Reserva> reservas = reservaDAO.listarTodos();
                        if (reservas.isEmpty()) { escritor.exibirErro("Nenhuma reserva cadastrada!"); break; }
                        for (int i = 0; i < reservas.size(); i++) escritor.escreverLinha((i + 1) + " - " + reservas.get(i));
                        escritor.escrever("Escolha a reserva: ");
                        int indiceReserva = leitor.lerInteiro() - 1;
                        if (indiceReserva < 0 || indiceReserva >= reservas.size()) { escritor.exibirErro("Reserva inválida!"); break; }
                        Reserva reservaSelecionada = reservas.get(indiceReserva);

                        escritor.escrever("Nova data (" + reservaSelecionada.getDataReserva() + ") dd/MM/yyyy: ");
                        String novaData = leitor.lerString();
                        if (!novaData.isBlank()) {
                            try { reservaSelecionada.setDataReserva(LocalDate.parse(novaData, DateTimeFormatter.ofPattern("dd/MM/yyyy"))); }
                            catch (Exception e) { escritor.exibirErro("Formato inválido!"); break; }
                        }

                        escritor.escrever("Nova hora (" + reservaSelecionada.getHoraReserva() + ") HH:mm: ");
                        String novaHora = leitor.lerString();
                        if (!novaHora.isBlank()) {
                            try { reservaSelecionada.setHoraReserva(LocalTime.parse(novaHora, DateTimeFormatter.ofPattern("HH:mm"))); }
                            catch (Exception e) { escritor.exibirErro("Formato inválido!"); break; }
                        }

                        reservaDAO.atualizar(reservaSelecionada);
                        escritor.exibirSucesso("Reserva atualizada!");
                    } catch (SQLException e) {
                        escritor.exibirErro("Erro ao atualizar: " + e.getMessage());
                    }
                    break;

                case 3:
                    escritor.exibirTitulo("-- LISTA DE RESERVAS --");
                    try {
                        List<Reserva> reservas = reservaDAO.listarTodos();
                        if (reservas.isEmpty()) escritor.exibirAviso("Nenhuma reserva encontrada.");
                        else for (int i = 0; i < reservas.size(); i++) escritor.escreverLinha((i + 1) + " - " + reservas.get(i));
                    } catch (SQLException e) {
                        escritor.exibirErro("Erro ao listar: " + e.getMessage());
                    }
                    break;

                case 4:
                    escritor.exibirTitulo("-- EXCLUIR RESERVA --");
                    try {
                        List<Reserva> reservas = reservaDAO.listarTodos();
                        if (reservas.isEmpty()) { escritor.exibirErro("Nenhuma reserva cadastrada!"); break; }
                        for (int i = 0; i < reservas.size(); i++) escritor.escreverLinha((i + 1) + " - " + reservas.get(i));
                        escritor.escrever("Escolha a reserva: ");
                        int excluir = leitor.lerInteiro() - 1;
                        if (excluir < 0 || excluir >= reservas.size()) { escritor.exibirErro("Reserva inválida!"); break; }
                        reservaDAO.deletar(reservas.get(excluir).getNumReserva());
                        escritor.exibirSucesso("Reserva removida!");
                    } catch (SQLException e) {
                        escritor.exibirErro("Erro ao excluir: " + e.getMessage());
                    }
                    break;

                case 5:
                    executandoReserva = false;
                    escritor.exibirAviso("Retornando...");
                    break;

                default:
                    escritor.exibirErro("Opção inválida!");
            }
        }
    }
}