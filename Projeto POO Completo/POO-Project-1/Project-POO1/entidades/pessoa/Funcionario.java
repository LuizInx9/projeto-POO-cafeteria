package entidades.pessoa;

import entidades.Cadastravel;

public class Funcionario extends Pessoa implements Cadastravel {
    private String cargo;
    private int matricula;
    private String telefone;

    public Funcionario(String nome, String cpf, String cargo, int matricula, String telefone) {
        super(nome, cpf);
        this.cargo = cargo;
        this.matricula = matricula;
        this.telefone = telefone;
    }

    @Override
    public boolean validar() {
        return getNome() != null && !getNome().isBlank()
            && cargo != null && !cargo.isBlank();
    }

    @Override
    public String resumo() {
        return "Funcionário: " + getNome() + " | Cargo: " + cargo;
    }

    @Override
    public String toString() {
        return super.toString() + " | Telefone: " + telefone
            + " | Cargo: " + cargo + " | Matrícula: " + matricula;
    }

    public String getCargo() { return cargo; }
    public void setCargo(String cargo) { this.cargo = cargo; }
    public int getMatricula() { return matricula; }
    public void setMatricula(int matricula) { this.matricula = matricula; }
    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }
}
