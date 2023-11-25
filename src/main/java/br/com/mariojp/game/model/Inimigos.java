package br.com.mariojp.game.model;

public enum Inimigos {

    INIMIGO1("imagens/alien.png"),
    INIMIGO2("imagens/enemy.png");

    private final String valor;

    Inimigos(String valor) {
        this.valor = valor;
    }

    public String getValor() {
        return this.valor;
    }


}
