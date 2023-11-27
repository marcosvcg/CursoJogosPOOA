package br.com.mariojp.state;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;

import br.com.mariojp.game.Inimigo;
import br.com.mariojp.game.Jogo;
import br.com.mariojp.game.Missil;

public class Fase3 implements JogoState {

	private Jogo jogo;
	private final int numeroDaFase = 3;
	
	@Override
	public void inicializar(Jogo jogo) {
		// TODO Auto-generated method stub
		this.jogo = jogo;
	}

	@Override
	public void atualizar() {
		// TODO Auto-generated method stub
		Rectangle r3 = jogo.nave.getBounds();
		for (Inimigo alien : jogo.inimigos) {
			Rectangle r2 = alien.getBounds();
			if (r3.intersects(r2)) {
				jogo.nave.setVisible(false);
				alien.setVisible(false);
				jogo.endgame = true;
			}
		}
		ArrayList<Missil> ms = jogo.nave.getMissiles();
		for (Missil m : ms) {
			Rectangle r1 = m.getBounds();
			for (Inimigo alien : jogo.inimigos) {
				Rectangle r2 = alien.getBounds();
				if (r1.intersects(r2)) {
					m.setVisible(false);
					alien.setVisible(false);
					jogo.score++;
					if(jogo.score > numeroDaFase*5) {
						jogo.score = 0;
						passarProximaFase();	
					}
				}
			}
		}
	}

	@Override
	public void desenhar(Graphics g) {
		// TODO Auto-generated method stub
		g.drawImage(jogo.nave.getImage(), jogo.nave.getX(), jogo.nave.getY(), this.jogo);

		for (Missil m : jogo.nave.getMissiles()) {
			if (m.isVisible()) {
				g.drawImage(m.getImage(), m.getX(), m.getY(), this.jogo);
			}
		}
		for (Inimigo i : jogo.inimigos) {
			if (i.isVisible()) {
				g.drawImage(i.getImage(), i.getX(), i.getY(), this.jogo);
			}
		}
		g.setColor(Color.WHITE);
		g.drawString("PONTOS : " + jogo.score, 5, 15);
		g.drawString("|", 80, 15);
		g.drawString("FASE : " + numeroDaFase, 90, 15);
	}

	public void passarProximaFase() {
		jogo.nextStage = true;
		jogo.nextStageScreenTimer = 60;
		jogo.setState(new Fase4());
	}
}
