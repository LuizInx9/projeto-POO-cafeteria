package menus;

import dao.MesaDAO;
import entidades.Mesa;
import entidades.enums.StatusMesa;
import util.Escritor;
import util.Leitor;

import java.sql.SQLException;
import java.util.List;

public class MenuMesa {

    public static void menuMesa(Leitor leitor, Escritor escritor) {
        MesaDAO dao = new MesaDAO();
        boolean executandoMesa = true;
        while (executandoMesa) {

            escritor.exibirSucesso("Menu Mesa Selecionado!");
            escritor.exibirTitulo("-- MENU MESA --");
            escritor.escreverLinha("1. Cadastrar Mesa");
            escritor.escreverLinha("2. Atualizar Mesa");
            escritor.escreverLinha("3. Listar Mesas");
            escritor.escreverLinha("4. Excluir Mesa");
            escritor.escreverLinha("5. Sair");
            escritor.escrever("Escolha uma opção: ");

            int opcaoMesa = leitor.lerInteiro();

            switch (opcaoMesa) {
                case 1:
                    escritor.exibirTitulo("-- CADASTRAR MESA --");
                    escritor.escrever("Capacidade da mesa: ");
                    int capacidade = leitor.lerInteiro();
                    try {
                        dao.inserir(new Mesa(0, capacidade, StatusMesa.LIVRE));
                        escritor.exibirSucesso("Mesa cadastrada!");
                    } catch (SQLException e) {
                        escritor.exibirErro("Erro ao cadastrar: " + e.getMessage());
                    }
                    break;

                case 2:
                    escritor.exibirTitulo("-- ATUALIZAR MESA --");
                    try {
                        List<Mesa> mesas = dao.listarTodos();
                        if (mesas.isEmpty()) { escritor.exibirErro("Nenhuma mesa cadastrada!"); break; }
                        for (int i = 0; i < mesas.size(); i++) escritor.escreverLinha((i + 1) + " - " + mesas.get(i));
                        escritor.escrever("Escolha a mesa: ");
                        int indice = leitor.lerInteiro() - 1;
                        if (indice < 0 || indice >= mesas.size()) { escritor.exibirErro("Mesa inválida!"); break; }
                        Mesa mesaSelecionada = mesas.get(indice);

                        escritor.escrever("Nova capacidade (" + mesaSelecionada.getCapacidadeMesa() + "): ");
                        String novaCapStr = leitor.lerString();
                        if (!novaCapStr.isBlank()) mesaSelecionada.setCapacidadeMesa(Integer.parseInt(novaCapStr));

                        escritor.escreverLinha("1 - LIVRE");
                        escritor.escreverLinha("2 - RESERVADA");
                        escritor.escrever("Novo status: ");
                        int status = leitor.lerInteiro();
                        if (status == 1) mesaSelecionada.setStatusMesa(StatusMesa.LIVRE);
                        else if (status == 2) mesaSelecionada.setStatusMesa(StatusMesa.RESERVADA);

                        dao.atualizar(mesaSelecionada);
                        escritor.exibirSucesso("Mesa atualizada!");
                    } catch (SQLException e) {
                        escritor.exibirErro("Erro ao atualizar: " + e.getMessage());
                    }
                    break;

                case 3:
                    escritor.exibirTitulo("-- LISTA DE MESAS --");
                    try {
                        List<Mesa> mesas = dao.listarTodos();
                        if (mesas.isEmpty()) escritor.exibirAviso("Nenhuma mesa cadastrada!");
                        else for (int i = 0; i < mesas.size(); i++) escritor.escreverLinha((i + 1) + " - " + mesas.get(i));
                    } catch (SQLException e) {
                        escritor.exibirErro("Erro ao listar: " + e.getMessage());
                    }
                    break;

                case 4:
                    escritor.exibirTitulo("-- EXCLUIR MESA --");
                    try {
                        List<Mesa> mesas = dao.listarTodos();
                        if (mesas.isEmpty()) { escritor.exibirErro("Nenhuma mesa cadastrada!"); break; }
                        for (int i = 0; i < mesas.size(); i++) escritor.escreverLinha((i + 1) + " - " + mesas.get(i));
                        escritor.escrever("Escolha a mesa: ");
                        int excluir = leitor.lerInteiro() - 1;
                        if (excluir < 0 || excluir >= mesas.size()) { escritor.exibirErro("Mesa inválida!"); break; }
                        dao.deletar(mesas.get(excluir).getNumMesa());
                        escritor.exibirSucesso("Mesa removida!");
                    } catch (SQLException e) {
                        escritor.exibirErro("Erro ao excluir: " + e.getMessage());
                    }
                    break;

                case 5:
                    executandoMesa = false;
                    escritor.exibirAviso("Retornando...");
                    break;

                default:
                    escritor.exibirErro("Opção inválida!");
            }
        }
    }
}
