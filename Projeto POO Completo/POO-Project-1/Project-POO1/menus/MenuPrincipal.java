package menus;

import util.Escritor;
import util.Leitor;

public class MenuPrincipal {

    public static void exibirMenu() {
        Leitor leitor = new Leitor();
        Escritor escritor = new Escritor();

        boolean executando = true;
        while (executando) {

            escritor.exibirTitulo("--- MENU PRINCIPAL --- ");
            escritor.escreverLinha("1. Cliente");
            escritor.escreverLinha("2. Funcionário");
            escritor.escreverLinha("3. Pedido");
            escritor.escreverLinha("4. Reserva");
            escritor.escreverLinha("5. Mesa");
            escritor.escreverLinha("6. Produto");
            escritor.escreverLinha("7. Sair");

            escritor.escrever("\nEscolha uma opção: ");

            int opcao = leitor.lerInteiro();

            switch (opcao) {
                case 1:
                    MenuCliente.menuCliente(leitor, escritor);
                    break;

                case 2:
                    MenuFuncionario.menuFuncionario(leitor, escritor);
                    break;

                case 3:
                    MenuPedido.menuPedido(leitor, escritor);
                    break;

                case 4:
                    MenuReserva.menuReserva(leitor, escritor);
                    break;

                case 5:
                    MenuMesa.menuMesa(leitor, escritor);
                    break;

                case 6:
                    MenuProduto.menuProduto(leitor, escritor);
                    break;

                case 7:
                    escritor.exibirAviso("Encerrando programa...");
                    executando = false;
                    break;

                default:
                    escritor.exibirErro("Opção inválida!");
            }
        }
    }
}
