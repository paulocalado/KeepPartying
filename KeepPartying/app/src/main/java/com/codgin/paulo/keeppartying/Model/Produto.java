package com.codgin.paulo.keeppartying.Model;

/**
 * Created by Paulo on 26/07/2017.
 */

public class Produto {
    String nome, status;
    double preco;
    String imagem;
    long tipo;
    int quantidade;
    double totalPorProduto;
    double totalPedido;

    public Produto(String nome, double preco) {
        this.nome = nome;
        this.preco = preco;
    }
    public Produto(){}
    public Produto(String nome, double preco, String imagem) {
        this.nome = nome;
        this.preco = preco;
        this.imagem = imagem;
    }

    public Produto(String nome, String status, double preco, String imagem, long tipo, int quantidade, double totalPorProduto, double totalPedido) {
        this.nome = nome;
        this.status = status;
        this.preco = preco;
        this.imagem = imagem;
        this.tipo = tipo;
        this.quantidade = quantidade;
        this.totalPorProduto = totalPorProduto;
        this.totalPedido = totalPedido;
    }

    public double getTotalPorProduto() {
        return totalPorProduto;
    }

    public void setTotalPorProduto(double totalPorProduto) {
        this.totalPorProduto = totalPorProduto;
    }

    public long getTipo() {
        return tipo;
    }

    public void setTipo(long tipo) {
        this.tipo = tipo;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
