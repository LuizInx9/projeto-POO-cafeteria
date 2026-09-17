package menus;

import dao.FuncionarioDAO;
import entidades.pessoa.Funcionario;
import util.Escritor;
import util.Leitor;

import java.sql.SQLException;
import java.util.List;

public class MenuFuncionario {

    public static void menuFuncionario(Leitor leitor, Escritor escritor) {
        FuncionarioDAO dao = new FuncionarioDAO();
        boolean executandoFuncionario = true;
        while (executandoFuncionario) {

            escritor.exibirSucesso("Menu Funcionário Selecionado! ");
            escritor.exibirTitulo("-- MENU FUNCIONÁRIO --");
            escritor.escreverLinha("1. Cadastrar novo funcionário");
            escritor.escreverLinha("2. Atualizar cargo do funcionário");
            escritor.escreverLinha("3. Excluir funcionário");
            escritor.escreverLinha("4. Exibir funcionários cadastrados");
            escritor.escreverLinha("5. Sair");
            escritor.escrever("Escolha uma opção: ");

            int opcaoFuncionario = leitor.lerInteiro();

            switch (opcaoFuncionario) {
                case 1: {
                    escritor.exibirTitulo("-- CADASTRAR NOVO FUNCIONÁRIO --");
                    escritor.escrever("Nome:");
                    String nome = leitor.lerString();
                    escritor.escrever("CPF:");
                    String cpf = leitor.lerString();
                    escritor.escrever("Cargo: ");
                    String cargo = leitor.lerString();
                    escritor.escrever("Telefone:");
                    String telefone = leitor.lerString();
                    try {
                        dao.inserir(new Funcionario(nome, cpf, cargo, 0, telefone));
                        escritor.exibirSucesso("Funcionário cadastrado com sucesso!");
                    } catch (SQLException e) {
                        escritor.exibirErro("Erro ao cadastrar: " + e.getMessage());
                    }
                    break;
                }

                case 2: {
                    escritor.exibirTitulo("-- ATUALIZAR DADOS DE FUNCIONÁRIO --");
                    try {
                        List<Funcionario> funcionarios = dao.listarTodos();
                        if (funcionarios.isEmpty()) {
                            escritor.exibirAviso("Nenhum funcionário cadastrado");
                            break;
                        }
                        for (int i = 0; i < funcionarios.size(); i++) {
                            escritor.escreverLinha((i + 1) + " - " + funcionarios.get(i));
                        }
                        escritor.escrever("\nDigite o número do funcionário: ");
                        int indice = leitor.lerInteiro() - 1;
                        if (indice < 0 || indice >= funcionarios.size()) {
                            escritor.exibirErro("Funcionário inválido!");
                            break;
                        }
                        Funcionario funcionarioSelecionado = funcionarios.get(indice);
                        escritor.exibirTitulo("-- NOVOS DADOS --");

                        escritor.escrever("Novo nome (" + funcionarioSelecionado.getNome() + "): ");
                        String novoNome = leitor.lerString();
                        if (!novoNome.isBlank()) funcionarioSelecionado.setNome(novoNome);

                        escritor.escrever("Novo CPF (" + funcionarioSelecionado.getCpf() + "): ");
                        String novoCpf = leitor.lerString();
                        if (!novoCpf.isBlank()) funcionarioSelecionado.setCpf(novoCpf);

                        escritor.escrever("Novo cargo (" + funcionarioSelecionado.getCargo() + "): ");
                        String novoCargo = leitor.lerString();
                        if (!novoCargo.isBlank()) funcionarioSelecionado.setCargo(novoCargo);

                        escritor.escrever("Novo telefone (" + funcionarioSelecionado.getTelefone() + "): ");
                        String novoTelefone = leitor.lerString();
                        if (!novoTelefone.isBlank()) funcionarioSelecionado.setTelefone(novoTelefone);

                        dao.atualizar(funcionarioSelecionado);
                        escritor.exibirSucesso("Funcionário atualizado!");
                    } catch (SQLException e) {
                        escritor.exibirErro("Erro ao atualizar: " + e.getMessage());
                    }
                    break;
                }

                case 3: {
                    escritor.exibirTitulo("-- EXCLUIR FUNCIONÁRIO --");
                    try {
                        List<Funcionario> funcionarios = dao.listarTodos();
                        if (funcionarios.isEmpty()) {
                            escritor.exibirAviso("Nenhum funcionário cadastrado");
                            break;
                        }
                        for (int i = 0; i < funcionarios.size(); i++) {
                            escritor.escreverLinha((i + 1) + " - " + funcionarios.get(i));
                        }
                        escritor.escrever("\nDigite o número do funcionário: ");
                        int indice = leitor.lerInteiro() - 1;
                        if (indice < 0 || indice >= funcionarios.size()) {
                            escritor.exibirErro("Funcionário inválido!");
                            break;
                        }
                        Funcionario funcionarioSelecionado = funcionarios.get(indice);
                        escritor.escreverLinha("\nFuncionário selecionado: ");
                        escritor.escreverLinha(funcionarioSelecionado.toString());
                        escritor.escrever("\nTem certeza que deseja remover? (s/n): ");
                        boolean confirmar = leitor.lerBooleano();
                        if (confirmar) {
                            dao.deletar(funcionarioSelecionado.getMatricula());
                            escritor.exibirSucesso("Funcionário removido com sucesso!");
                        } else {
                            escritor.exibirAviso("Operação cancelada.");
                        }
                    } catch (SQLException e) {
                        escritor.exibirErro("Erro ao excluir: " + e.getMessage());
                    }
                    break;
                }

                case 4: {
                    escritor.exibirTitulo("-- FUNCIONÁRIOS CADASTRADOS --");
                    try {
                        List<Funcionario> funcionarios = dao.listarTodos();
                        if (funcionarios.isEmpty()) {
                            escritor.exibirAviso("Nenhum funcionário encontrado!");
                        } else {
                            for (int i = 0; i < funcionarios.size(); i++) {
                                escritor.escreverLinha((i + 1) + ". " + funcionarios.get(i));
                            }
                        }
                    } catch (SQLException e) {
                        escritor.exibirErro("Erro ao listar: " + e.getMessage());
                    }
                    break;
                }

                case 5:
                    escritor.exibirAviso("Retornando ao menu inicial...");
                    executandoFuncionario = false;
                    break;

                default:
                    System.err.println("Opção inválida! Tente novamente.");
            }
        }
    }
}
