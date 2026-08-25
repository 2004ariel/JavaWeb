package model;

public class produto {
    String nome;
    double preco;
    int quantidade;

    public produto() {
    }

    public produto(String nome, double preco, int quantidade) {
        this.nome = nome;
        this.preco = preco;
        this.quantidade = quantidade;
    }

    public void exibirProduto() {
        System.out.println(nome + " - R$" + preco + " - Quantidade: " + quantidade);
    }

    public String getNome() { return this.nome; }
    public void setNome(String nome) { this.nome = nome; }
    public double getPreco() { return this.preco; }
    public void setPreco(double preco) { this.preco = preco; }
    public int getQuantidade() { return this.quantidade; }
    public void setQuantidade(int quantidade) { this.quantidade = quantidade; }
}
