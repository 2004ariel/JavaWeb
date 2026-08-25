package model;

public class ProdutoAlimenticio extends produto {
    private String validade;

    public ProdutoAlimenticio() {
    }

    public ProdutoAlimenticio(String nome, double preco, int quantidade, String validade) {
        super(nome, preco, quantidade);
        this.validade = validade;
    }

    public String getValidade() { return this.validade; }
    public void setValidade(String validade) { this.validade = validade; }
}
