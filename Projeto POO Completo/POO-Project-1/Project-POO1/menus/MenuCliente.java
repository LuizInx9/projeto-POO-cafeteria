package menus;

import dao.ClienteDAO;
import entidades.pessoa.Cliente;
import util.Escritor;
import util.Leitor;

import java.sql.SQLException;
import java.util.List;

public class MenuCliente {

    public static void menuCliente(Leitor leitor, Escritor escritor) {
        ClienteDAO dao = new ClienteDAO();
        boolean executandoCliente = true;
        while (executandoCliente) {

            escritor.escreverLinha(" ");
            escritor.exibirSucesso("Menu Cliente Selecionado! ");
            escritor.exibirTitulo("-- CLIENTE --");
            escritor.escreverLinha("1. Cadastrar novo cliente");
            escritor.escreverLinha("2. Atualizar cliente existente");
            escritor.escreverLinha("3. Excluir cliente");
            escritor.escreverLinha("4. Exibir lista de clientes");
            escritor.escreverLinha("5. Sair");
            escritor.escrever("Escolha uma opção: ");

            int opcaoCliente = leitor.lerInteiro();

            switch (opcaoCliente) {
                case 1:
                    escritor.exibirTitulo("-- NOVO CLIENTE --");
                    escritor.escrever("Nome:");
                    String nome = leitor.lerString();
                    escritor.escrever("CPF:");
                    String cpf = leitor.lerString();
                    escritor.escrever("Telefone:");
                    String telefone = leitor.lerString();
                    try {
                        dao.inserir(new Cliente(nome, cpf, telefone));
                        escritor.exibirSucesso("Cliente cadastrado com sucesso!");
                    } catch (SQLException e) {
                        escritor.exibirErro("Erro ao cadastrar: " + e.getMessage());
                    }
                    break;

                case 2: {
                    escritor.exibirTitulo("-- ATUALIZAR CLIENTES --");
                    try {
                        List<Cliente> clientes = dao.listarTodos();
                        if (clientes.isEmpty()) {
                            escritor.exibirAviso("Nenhum cliente cadastrado");
                            break;
                        }
                        for (int i = 0; i < clientes.size(); i++) {
                            escritor.escreverLinha((i + 1) + " - " + clientes.get(i));
                        }
                        escritor.escrever("\nDigite o número do cliente: ");
                        int indice = leitor.lerInteiro() - 1;
                        if (indice < 0 || indice >= clientes.size()) {
                            escritor.exibirErro("Cliente inválido!");
                            break;
                        }
                        Cliente clienteSelecionado = clientes.get(indice);
                        escritor.exibirTitulo("-- NOVOS DADOS --");

                        escritor.escrever("Novo nome (" + clienteSelecionado.getNome() + "): ");
                        String novoNome = leitor.lerString();
                        if (!novoNome.isBlank()) clienteSelecionado.setNome(novoNome);

                        escritor.escrever("Novo CPF (" + clienteSelecionado.getCpf() + "): ");
                        String novoCpf = leitor.lerString();
                        if (!novoCpf.isBlank()) clienteSelecionado.setCpf(novoCpf);

                        escritor.escrever("Novo telefone (" + clienteSelecionado.getTelefone() + "): ");
                        String novoTelefone = leitor.lerString();
                        if (!novoTelefone.isBlank()) clienteSelecionado.setTelefone(novoTelefone);

                        dao.atualizar(clienteSelecionado);
                        escritor.exibirSucesso("Cliente atualizado!");
                    } catch (SQLException e) {
                        escritor.exibirErro("Erro ao atualizar: " + e.getMessage());
                    }
                    break;
                }

                case 3: {
                    escritor.exibirTitulo("-- EXCLUIR CLIENTES --");
                    try {
                        List<Cliente> clientes = dao.listarTodos();
                        if (clientes.isEmpty()) {
                            escritor.exibirAviso("Nenhum cliente cadastrado");
                            break;
                        }
                        for (int i = 0; i < clientes.size(); i++) {
                            escritor.escreverLinha((i + 1) + " - " + clientes.get(i));
                        }
                        escritor.escrever("\nDigite o número do cliente: ");
                        int indice = leitor.lerInteiro() - 1;
                        if (indice < 0 || indice >= clientes.size()) {
                            escritor.exibirErro("Cliente inválido!");
                            break;
                        }
                        Cliente clienteSelecionado = clientes.get(indice);
                        escritor.escreverLinha("\nCliente selecionado: ");
                        escritor.escreverLinha(clienteSelecionado.toString());
                        escritor.escrever("\nTem certeza que deseja remover? (s/n): ");
                        boolean confirmar = leitor.lerBooleano();
                        if (confirmar) {
                            dao.deletar(clienteSelecionado.getCpf());
                            escritor.exibirSucesso("Cliente removido com sucesso!");
                        } else {
                            escritor.exibirAviso("Operação cancelada.");
                        }
                    } catch (SQLException e) {
                        escritor.exibirErro("Erro ao excluir: " + e.getMessage());
                    }
                    break;
                }

                case 4: {
                    escritor.exibirTitulo("-- LISTA DE CLIENTES --");
                    try {
                        List<Cliente> clientes = dao.listarTodos();
                        if (clientes.isEmpty()) {
                            escritor.exibirAviso("Nenhum cliente encontrado!");
                        } else {
                            for (int i = 0; i < clientes.size(); i++) {
                                escritor.escreverLinha((i + 1) + ". " + clientes.get(i));
                            }
                        }
                    } catch (SQLException e) {
                        escritor.exibirErro("Erro ao listar: " + e.getMessage());
                    }
                    break;
                }

                case 5:
                    escritor.exibirAviso("Retornando ao menu inicial...");
                    executandoCliente = false;
                    break;

                default:
                    System.err.println("Opção inválida! Tente novamente.");
            }
        }
    }
}
