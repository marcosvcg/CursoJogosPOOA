package br.com.mariojp.state;

import java.awt.Graphics;

import br.com.mariojp.game.Jogo;

public interface JogoState {

	void inicializar(Jogo jogo);
    void atualizar();
    void desenhar(Graphics g);
    void passarProximaFase();
}
