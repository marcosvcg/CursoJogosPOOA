package br.com.mariojp.game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JPanel;
import javax.swing.Timer;

import br.com.mariojp.state.Fase1;
import br.com.mariojp.state.JogoState;

public class Jogo extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;

	private Timer timer;
	public Nave nave;

	public int score = 0;

	public ArrayList<Inimigo> inimigos = new ArrayList<Inimigo>();

	private Random random = new Random();

	public boolean endgame = false;

	private final int DELAY = 10;

	private final int B_WIDTH = 800;
	private final int B_HEIGHT = 600;

	private JogoState state;
	private JogoState fase1;
	public boolean nextStage = false;
	public int nextStageScreenTimer = 0;
	
	public Jogo() {
		fase1 = new Fase1();
		
		state = fase1;
		state.inicializar(this);
		
		initJogo();
	}

	private void initJogo() {
		addKeyListener(new TAdapter());
		setFocusable(true);
		setBackground(Color.BLACK);
		setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));
		setDoubleBuffered(true);
		nave = new Nave(40, 60, B_WIDTH);

		timer = new Timer(DELAY, this);
		timer.start();
	}

	private void drawMessage(Graphics g, String msg) {
		Font small = new Font("Helvetica", Font.BOLD, 18);
		FontMetrics fm = getFontMetrics(small);
		g.setColor(Color.white);
		g.setFont(small);
		g.drawString(msg, (B_WIDTH - fm.stringWidth(msg)) / 2, B_HEIGHT / 2);
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		if(nextStage) {
			drawMessage(g, "Next Stage");
			timer.setDelay(DELAY);
			nextStageScreenTimer--;
			
			if(nextStageScreenTimer <= 0) {
				nextStage = false;
			}
		}
		
		if (!endgame) {
			state.desenhar(g);
		} else {
			drawMessage(g, "Game Over");
		}
		Toolkit.getDefaultToolkit().sync();
	}


	private void updateMissiles() {
		ArrayList<?> ms = nave.getMissiles();
		for (int i = 0; i < ms.size(); i++) {
			Missil m = (Missil) ms.get(i);
			if (m.isVisible()) {
				m.move();
			} else {
				ms.remove(i);
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		stopGame();
		updateNave();
		updateMissiles();
		updateInimigo();
		state.atualizar();
		repaint();
	}


	private void updateInimigo() {
		while (inimigos.size() < 5) {
			inimigos.add(new Inimigo(B_WIDTH, random.nextInt(B_HEIGHT - 20) + 10));
		}

		for (int i = 0; i < inimigos.size(); i++) {
			Inimigo inimigo = inimigos.get(i);
			if (inimigo.isVisible()) {
				inimigo.move();
			} else {
				inimigos.remove(inimigo);
			}
		}

	}

	private void stopGame() {
		if (endgame) {
			timer.stop();
		}
	}

	private void updateNave() {
		nave.move();
	}

	private class TAdapter extends KeyAdapter {
		@Override
		public void keyReleased(KeyEvent e) {
			nave.keyReleased(e);
		}

		@Override
		public void keyPressed(KeyEvent e) {
			nave.keyPressed(e);
		}
	}
	
	public void setState(JogoState newState) {
        this.state = newState;
        state.inicializar(this);
    }
}