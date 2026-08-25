package model;

public class ProdutoEletrico extends produto {
    private String garantia;

    public ProdutoEletrico(String nome, double preco, int quantidade, String garantia) {
        super(nome, preco, quantidade);
        this.garantia = garantia;
    }
    // nota: sem getter/setter para garantia no projeto original (ProjetoI)
}
