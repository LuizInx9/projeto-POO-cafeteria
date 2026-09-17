package entidades.pessoa;

import entidades.Cadastravel;

public class Cliente extends Pessoa implements Cadastravel {
    private String telefone;

    public Cliente(String nome, String cpf, String telefone) {
        super(nome, cpf);
        this.telefone = telefone;
    }

    @Override
    public boolean validar() {
        return getNome() != null && !getNome().isBlank()
            && getCpf() != null && !getCpf().isBlank();
    }

    @Override
    public String resumo() {
        return "Cliente: " + getNome() + " (CPF: " + getCpf() + ")";
    }

    @Override
    public String toString() {
        return super.toString() + " | Telefone: " + telefone;
    }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }
}
