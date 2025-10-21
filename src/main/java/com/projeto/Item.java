package com.projeto;

public class Item {
    private int id,qtd;
    private String nome, descricao;
    private boolean stats;

    
    public Item(int id, String nome, String descricao, boolean stats, int qtd) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.stats = stats;
        this.qtd = qtd;
    }
    
    public int getQtd(){
        return qtd;
    }
    public void setQtd(int qtd){
        this.qtd = qtd;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getDescricao() {
        return descricao;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    public boolean isStats() {
        return stats;
    }
    public void setStats(boolean stats) {
        this.stats = stats;
    }


    
}
