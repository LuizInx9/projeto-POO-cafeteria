package menus;

import dao.ClienteDAO;
import dao.FuncionarioDAO;
import dao.PedidoDAO;
import dao.ProdutoDAO;
import entidades.Pedido;
import entidades.Produto;
import entidades.enums.StatusPedido;
import entidades.pessoa.Cliente;
import entidades.pessoa.Funcionario;
import util.Escritor;
import util.Leitor;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class MenuPedido {

    public static void menuPedido(Leitor leitor, Escritor escritor) {
        PedidoDAO pedidoDAO = new PedidoDAO();
        ClienteDAO clienteDAO = new ClienteDAO();
        FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
        ProdutoDAO produtoDAO = new ProdutoDAO();

        boolean executandoPedido = true;
        while (executandoPedido) {

            escritor.exibirSucesso("Menu Pedidos Selecionado! ");
            escritor.exibirTitulo("-- MENU PEDIDOS --");
            escritor.escreverLinha("1. Criar novo pedido");
            escritor.escreverLinha("2. Atualizar pedido existente");
            escritor.escreverLinha("3. Excluir pedido");
            escritor.escreverLinha("4. Lista de pedidos");
            escritor.escreverLinha("5. Finalizar pedido");
            escritor.escreverLinha("6. Sair");
            escritor.escrever("Escolha uma opção: ");

            int opcaoPedido = leitor.lerInteiro();

            switch (opcaoPedido) {
                case 1:
                    escritor.exibirTitulo("-- CRIAR NOVO PEDIDO --");
                    try {
                        List<Cliente> clientes = clienteDAO.listarTodos();
                        if (clientes.isEmpty()) {
                            escritor.exibirErro("Nenhum cliente cadastrado!");
                            break;
                        }
                        escritor.exibirTitulo("Selecione o Cliente");
                        for (int i = 0; i < clientes.size(); i++) escritor.escreverLinha((i + 1) + " - " + clientes.get(i));
                        escritor.escrever("Escolha o cliente: ");
                        int indiceCliente = leitor.lerInteiro() - 1;
                        if (indiceCliente < 0 || indiceCliente >= clientes.size()) {
                            escritor.exibirErro("Cliente inválido!");
                            break;
                        }
                        Cliente clienteSelecionado = clientes.get(indiceCliente);

                        List<Funcionario> funcionarios = funcionarioDAO.listarTodos();
                        if (funcionarios.isEmpty()) {
                            escritor.exibirErro("Nenhum funcionário cadastrado!");
                            break;
                        }
                        escritor.exibirTitulo("Selecione o Funcionário");
                        for (int i = 0; i < funcionarios.size(); i++) escritor.escreverLinha((i + 1) + " - " + funcionarios.get(i));
                        escritor.escrever("Escolha o funcionário: ");
                        int indiceFuncionario = leitor.lerInteiro() - 1;
                        if (indiceFuncionario < 0 || indiceFuncionario >= funcionarios.size()) {
                            escritor.exibirErro("Funcionário inválido!");
                            break;
                        }
                        Funcionario funcionarioSelecionado = funcionarios.get(indiceFuncionario);

                        List<Produto> produtos = produtoDAO.listarTodos();
                        boolean executandoLista = true;
                        while (executandoLista) {
                            escritor.exibirTitulo("Adicione um produto ao pedido");
                            for (int i = 0; i < produtos.size(); i++) escritor.escreverLinha((i + 1) + " - " + produtos.get(i));
                            escritor.escrever("Escolha o produto: ");
                            int indiceProduto = leitor.lerInteiro() - 1;
                            if (indiceProduto < 0 || indiceProduto >= produtos.size()) {
                                escritor.exibirErro("Produto inválido!");
                                break;
                            }
                            escritor.escrever("Deseja adicionar mais produtos? (s/n) ");
                            if (!leitor.lerBooleano()) break;
                        }

                        escritor.exibirTitulo("Forma de Pagamento");
                        escritor.escreverLinha("1 - Dinheiro");
                        escritor.escreverLinha("2 - Cartão");
                        escritor.escreverLinha("3 - Pix");
                        escritor.escrever("Escolha uma forma de pagamento: ");
                        int opcaoPagamento = leitor.lerInteiro();
                        String formaPagamento = switch (opcaoPagamento) {
                            case 1 -> "Dinheiro";
                            case 2 -> "Cartão";
                            case 3 -> "Pix";
                            default -> "";
                        };
                        if (formaPagamento.isEmpty()) {
                            escritor.exibirErro("Pagamento inválido!");
                            break;
                        }

                        Pedido novoPedido = new Pedido(0, clienteSelecionado, 0.0, LocalDate.now(), StatusPedido.ABERTO, formaPagamento, funcionarioSelecionado);
                        pedidoDAO.inserir(novoPedido);
                        escritor.exibirSucesso("Pedido criado com sucesso!");
                    } catch (SQLException e) {
                        escritor.exibirErro("Erro ao criar pedido: " + e.getMessage());
                    }
                    break;

                case 2: {
                    escritor.exibirTitulo("-- ATUALIZAR PEDIDO --");
                    try {
                        List<Pedido> pedidos = pedidoDAO.listarTodos();
                        if (pedidos.isEmpty()) { escritor.exibirAviso("Nenhum pedido cadastrado!"); break; }
                        for (int i = 0; i < pedidos.size(); i++) escritor.escreverLinha((i + 1) + " - " + pedidos.get(i));
                        escritor.escrever("Escolha o pedido: ");
                        int indicePedido = leitor.lerInteiro() - 1;
                        if (indicePedido < 0 || indicePedido >= pedidos.size()) { escritor.exibirErro("Pedido inválido!"); break; }
                        Pedido pedidoSelecionado = pedidos.get(indicePedido);

                        escritor.escreverLinha("1 - Dinheiro  2 - Cartão  3 - Pix");
                        escritor.escrever("Nova forma de pagamento (0 para manter): ");
                        int novoPag = leitor.lerInteiro();
                        if (novoPag == 1) pedidoSelecionado.setFormaPagamento("Dinheiro");
                        else if (novoPag == 2) pedidoSelecionado.setFormaPagamento("Cartão");
                        else if (novoPag == 3) pedidoSelecionado.setFormaPagamento("Pix");

                        pedidoDAO.atualizar(pedidoSelecionado);
                        escritor.exibirSucesso("Pedido atualizado!");
                    } catch (SQLException e) {
                        escritor.exibirErro("Erro ao atualizar: " + e.getMessage());
                    }
                    break;
                }

                case 3: {
                    escritor.exibirTitulo("-- EXCLUIR PEDIDO --");
                    try {
                        List<Pedido> pedidos = pedidoDAO.listarTodos();
                        if (pedidos.isEmpty()) { escritor.exibirAviso("Nenhum pedido cadastrado!"); break; }
                        for (int i = 0; i < pedidos.size(); i++) escritor.escreverLinha((i + 1) + " - " + pedidos.get(i));
                        escritor.escrever("\nDigite o número do pedido: ");
                        int indice = leitor.lerInteiro() - 1;
                        if (indice < 0 || indice >= pedidos.size()) { escritor.exibirErro("Pedido inválido!"); break; }
                        Pedido pedidoSelecionado = pedidos.get(indice);
                        escritor.escreverLinha("\nPedido selecionado: " + pedidoSelecionado);
                        escritor.escrever("\nTem certeza que deseja remover? (s/n): ");
                        if (leitor.lerBooleano()) {
                            pedidoDAO.deletar(pedidoSelecionado.getNumPedido());
                            escritor.exibirSucesso("Pedido removido com sucesso!");
                        } else {
                            escritor.exibirAviso("Operação cancelada.");
                        }
                    } catch (SQLException e) {
                        escritor.exibirErro("Erro ao excluir: " + e.getMessage());
                    }
                    break;
                }

                case 4:
                    escritor.exibirTitulo("-- LISTA DE PEDIDOS --");
                    try {
                        List<Pedido> pedidos = pedidoDAO.listarTodos();
                        if (pedidos.isEmpty()) {
                            escritor.exibirAviso("Nenhum pedido encontrado!");
                        } else {
                            for (int i = 0; i < pedidos.size(); i++) escritor.escreverLinha((i + 1) + ". " + pedidos.get(i));
                        }
                    } catch (SQLException e) {
                        escritor.exibirErro("Erro ao listar: " + e.getMessage());
                    }
                    break;

                case 5:
                    escritor.exibirTitulo("-- FINALIZAR PEDIDO --");
                    try {
                        List<Pedido> pedidos = pedidoDAO.listarTodos();
                        if (pedidos.isEmpty()) { escritor.exibirAviso("Nenhum pedido cadastrado!"); break; }
                        for (int i = 0; i < pedidos.size(); i++) escritor.escreverLinha((i + 1) + " - " + pedidos.get(i));
                        escritor.escrever("\nDigite o número do pedido: ");
                        int indiceStatus = leitor.lerInteiro() - 1;
                        if (indiceStatus < 0 || indiceStatus >= pedidos.size()) { escritor.exibirErro("Pedido inválido!"); break; }
                        Pedido pedidoSelecionado = pedidos.get(indiceStatus);
                        escritor.escreverLinha("\nPedido selecionado: " + pedidoSelecionado);
                        escritor.escrever("\nFinalizar pedido? (s/n): ");
                        if (leitor.lerBooleano()) {
                            pedidoSelecionado.setStatusPedido(StatusPedido.FINALIZADO);
                            pedidoDAO.atualizar(pedidoSelecionado);
                            escritor.exibirSucesso("Pedido finalizado com sucesso!");
                        } else {
                            escritor.exibirAviso("Operação cancelada.");
                        }
                    } catch (SQLException e) {
                        escritor.exibirErro("Erro ao finalizar: " + e.getMessage());
                    }
                    break;

                case 6:
                    escritor.exibirAviso("Retornando ao menu inicial...");
                    executandoPedido = false;
                    break;

                default:
                    System.err.println("Opção inválida! Tente novamente.");
            }
        }
    }
}
