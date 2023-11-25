package br.com.mariojp.game.model;

public class sith extends Inimigo{

    private int initX;


    public sith(int x, int y) {
        super(x, y);

    }

    private void initInimigo() {
        carregarImagem("/imagens/outroTipo.png");
        getImageDimensions();
    }

    public void move() {
        if (x < 0) {
            x = initX;
        }
        x -= 2;
    }

}
