package entidades;

import entidades.enums.CategoriaProduto;

public class Produto implements Cadastravel {
    private int idProduto;
    private double precoProduto;
    private String nomeProduto;
    private CategoriaProduto categoria;
    private int estoque;

    // CONSTRUTOR COMPLETO
    public Produto(int idProduto, double precoProduto, String nomeProduto,
                   CategoriaProduto categoria, int estoque) {
        this.idProduto = idProduto;
        this.precoProduto = precoProduto;
        this.nomeProduto = nomeProduto;
        this.categoria = categoria;
        this.estoque = estoque;
    }

    // CONSTRUTOR SOBRECARREGADO — sem estoque (usado na leitura do banco)
    public Produto(int idProduto, double precoProduto, String nomeProduto,
                   CategoriaProduto categoria) {
        this(idProduto, precoProduto, nomeProduto, categoria, 0);
    }

    @Override
    public boolean validar() {
        return nomeProduto != null && !nomeProduto.isBlank()
            && precoProduto > 0
            && categoria != null;
    }

    @Override
    public String resumo() {
        return "Produto: " + nomeProduto + " | R$ " + precoProduto;
    }

    @Override
    public String toString() {
        return "ID: " + idProduto
            + " | Produto: " + nomeProduto
            + " | Categoria: " + categoria
            + " | Preço: R$ " + precoProduto
            + " | Estoque: " + estoque;
    }

    public String getCategoriaNome() { return this.categoria.toDB(); }

    public int getIdProduto() { return idProduto; }
    public double getPrecoProduto() { return precoProduto; }
    public String getNomeProduto() { return nomeProduto; }
    public CategoriaProduto getCategoria() { return categoria; }
    public int getEstoque() { return estoque; }
    public void setIdProduto(int idProduto) { this.idProduto = idProduto; }
    public void setPrecoProduto(double precoProduto) { this.precoProduto = precoProduto; }
    public void setNomeProduto(String nomeProduto) { this.nomeProduto = nomeProduto; }
    public void setCategoria(CategoriaProduto categoria) { this.categoria = categoria; }
    public void setEstoque(int estoque) { this.estoque = estoque; }
    public void adicionarEstoque(int quantidade) { estoque += quantidade; }
    public void removerEstoque(int quantidade) {
        if (quantidade <= estoque) estoque -= quantidade;
    }
}
