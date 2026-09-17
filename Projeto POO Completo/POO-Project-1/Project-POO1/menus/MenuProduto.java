package menus;

import dao.ProdutoDAO;
import entidades.Produto;
import entidades.enums.CategoriaProduto;
import util.Escritor;
import util.Leitor;

import java.sql.SQLException;
import java.util.List;

public class MenuProduto {

    public static void menuProduto(Leitor leitor, Escritor escritor) {
        ProdutoDAO dao = new ProdutoDAO();
        boolean executandoProduto = true;
        while (executandoProduto) {

            escritor.exibirSucesso("Menu Produto Selecionado!");
            escritor.exibirTitulo("-- MENU PRODUTO --");
            escritor.escreverLinha("1. Cadastrar novo produto");
            escritor.escreverLinha("2. Atualizar produto");
            escritor.escreverLinha("3. Listar produtos");
            escritor.escreverLinha("4. Excluir produto");
            escritor.escreverLinha("5. Sair");
            escritor.escrever("Escolha uma opção: ");

            int opcaoProduto = leitor.lerInteiro();

            switch (opcaoProduto) {
                case 1:
                    escritor.exibirTitulo("-- CADASTRAR PRODUTO --");
                    escritor.escrever("Nome do produto: ");
                    String nomeProduto = leitor.lerString();
                    escritor.escrever("Preço: ");
                    double precoProduto = leitor.lerDouble();

                    CategoriaProduto[] categorias = CategoriaProduto.values();
                    escritor.escreverLinha("Categoria do produto: ");
                    for (int i = 0; i < categorias.length; i++) {
                        escritor.escreverLinha((i + 1) + " - " + categorias[i]);
                    }
                    escritor.escreverLinha("Selecione uma categoria: ");
                    int opcaoCategoria = leitor.lerInteiro() - 1;
                    if (opcaoCategoria < 0 || opcaoCategoria >= categorias.length) {
                        escritor.exibirErro("Categoria inválida!");
                        break;
                    }
                    CategoriaProduto categoria = categorias[opcaoCategoria];

                    escritor.escrever("Quantidade em estoque: ");
                    int estoque = leitor.lerInteiro();

                    try {
                        dao.inserir(new Produto(0, precoProduto, nomeProduto, categoria, estoque));
                        escritor.exibirSucesso("Produto cadastrado!");
                    } catch (SQLException e) {
                        escritor.exibirErro("Erro ao cadastrar: " + e.getMessage());
                    }
                    break;

                case 2:
                    escritor.exibirTitulo("-- ATUALIZAR PRODUTO --");
                    try {
                        List<Produto> produtos = dao.listarTodos();
                        if (produtos.isEmpty()) {
                            escritor.exibirErro("Nenhum produto cadastrado!");
                            break;
                        }
                        for (int i = 0; i < produtos.size(); i++) {
                            escritor.escreverLinha((i + 1) + " - " + produtos.get(i));
                        }
                        escritor.escrever("Escolha o produto: ");
                        int indice = leitor.lerInteiro() - 1;
                        if (indice < 0 || indice >= produtos.size()) {
                            escritor.exibirErro("Produto inválido!");
                            break;
                        }
                        Produto produtoSelecionado = produtos.get(indice);

                        escritor.escrever("Novo nome (" + produtoSelecionado.getNomeProduto() + "): ");
                        String novoNome = leitor.lerString();
                        if (!novoNome.isBlank()) produtoSelecionado.setNomeProduto(novoNome);

                        escritor.escrever("Novo preço (" + produtoSelecionado.getPrecoProduto() + "): ");
                        String novoPrecoStr = leitor.lerString();
                        if (!novoPrecoStr.isBlank()) produtoSelecionado.setPrecoProduto(Double.parseDouble(novoPrecoStr.replace(',', '.')));

                        dao.atualizar(produtoSelecionado);
                        escritor.exibirSucesso("Produto atualizado!");
                    } catch (SQLException e) {
                        escritor.exibirErro("Erro ao atualizar: " + e.getMessage());
                    }
                    break;

                case 3:
                    escritor.exibirTitulo("-- LISTA DE PRODUTOS --");
                    try {
                        List<Produto> produtos = dao.listarTodos();
                        if (produtos.isEmpty()) {
                            escritor.exibirAviso("Nenhum produto encontrado!");
                        } else {
                            for (int i = 0; i < produtos.size(); i++) {
                                escritor.escreverLinha((i + 1) + " - " + produtos.get(i));
                            }
                        }
                    } catch (SQLException e) {
                        escritor.exibirErro("Erro ao listar: " + e.getMessage());
                    }
                    break;

                case 4:
                    escritor.exibirTitulo("-- EXCLUIR PRODUTO --");
                    try {
                        List<Produto> produtos = dao.listarTodos();
                        if (produtos.isEmpty()) {
                            escritor.exibirErro("Nenhum produto cadastrado!");
                            break;
                        }
                        for (int i = 0; i < produtos.size(); i++) {
                            escritor.escreverLinha((i + 1) + " - " + produtos.get(i));
                        }
                        escritor.escrever("Escolha o produto: ");
                        int excluir = leitor.lerInteiro() - 1;
                        if (excluir < 0 || excluir >= produtos.size()) {
                            escritor.exibirErro("Produto inválido!");
                            break;
                        }
                        dao.deletar(produtos.get(excluir).getIdProduto());
                        escritor.exibirSucesso("Produto removido!");
                    } catch (SQLException e) {
                        escritor.exibirErro("Erro ao excluir: " + e.getMessage());
                    }
                    break;

                case 5:
                    executandoProduto = false;
                    escritor.exibirAviso("Retornando...");
                    break;

                default:
                    escritor.exibirErro("Opção inválida!");
            }
        }
    }
}
