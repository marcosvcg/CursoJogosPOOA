package br.com.mariojp.game.main;

import br.com.mariojp.game.factory.SpriteFactory;
import br.com.mariojp.game.model.Inimigo;
import br.com.mariojp.game.model.Missil;
import br.com.mariojp.game.model.Nave;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

public class Jogo extends JPanel implements ActionListener {

    private static final long serialVersionUID = 1L;

    private Timer timer;
    private Nave nave;



    private int score = 0;

    private ArrayList<Inimigo> inimigos = new ArrayList<Inimigo>();

    private Random random = new Random();

    private boolean endgame = false;

    private final int DELAY = 5;

    private final int B_WIDTH = 800;
    private final int B_HEIGHT = 600;

    public Jogo() {
        initJogo();
    }

    private void initJogo() {
        // adiciona um ouvinte de eventos de teclado ao painel. TAdapter é uma classe interna que estende KeyAdapter e manipula eventos de teclado.
        addKeyListener(new TAdapter());

       // torna o painel capaz de ganhar foco. Isso é necessário para que o painel possa receber eventos de teclado.
        setFocusable(true);

		// define a cor de fundo do painel como preto.
        setBackground(Color.BLACK);

		//define o tamanho preferido do painel. B_WIDTH e B_HEIGHT são constantes que representam a largura e a altura do painel, respectivamente.
        setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));

		// ativa o double buffering, que é uma técnica usada para reduzir o flickering na animação.
        setDoubleBuffered(true);

		//cria a nave do jogador.
		nave = (Nave) SpriteFactory.createSprite("Nave", 40, 60, B_WIDTH, "/imagens/nave.png");


//		inimigos.add() = (Inimigo) SpriteFactory.createSprite("Inimigo", 40, 60, B_WIDTH, "/imagens/alien.png");
//		Inimigo inimigo = SpriteFactory.createSprite("InimigoTipo1", x, y, largura, imagem);


		//cria um timer que chama o método actionPerformed(ActionEvent e) a cada intervalo de tempo definido por DELAY.
        timer = new Timer(DELAY, this);

		//inicia o timer.
		timer.start();
    }

    private void drawGameOver(Graphics g) {
        String msg = "Game Over";
        Font small = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics fm = getFontMetrics(small);
        g.setColor(Color.white);
        g.setFont(small);
        g.drawString(msg, (B_WIDTH - fm.stringWidth(msg)) / 2, B_HEIGHT / 2);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (!endgame) {
            desenhar(g);
        } else {
            drawGameOver(g);
        }
        Toolkit.getDefaultToolkit().sync();
    }

	private void gerarInimigos() {
		int numeroDeInimigos = 10; // ou qualquer número que você quiser
		for (int i = 0; i < numeroDeInimigos; i++) {
			String img = random.nextBoolean() ? "/imagens/alien.png" : "/imagens/enemy.png"; // escolhe um tipo aleatório
			Inimigo inimigo = (Inimigo) SpriteFactory.createSprite("Inimigo", 40, 60, B_WIDTH, img);
			if (inimigo != null) {
				inimigos.add(inimigo); // adiciona o inimigo à lista
			}
		}
	}



    private void desenhar(Graphics g) {
        g.drawImage(nave.getImage(), nave.getX(), nave.getY(), this);

        for (Missil m : nave.getMissiles()) {
            if (m.isVisible()) {
                g.drawImage(m.getImage(), m.getX(), m.getY(), this);
            }
        }

        for (Inimigo i : inimigos) {
            if (i.isVisible()) {
                g.drawImage(i.getImage(), i.getX(), i.getY(), this);
            }
        }
        g.setColor(Color.WHITE);
        g.drawString("PONTOS : " + score, 5, 15);

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
        checkCollisions();
        repaint();
    }

    public void checkCollisions() {
        Rectangle r3 = nave.getBounds();
        for (Inimigo alien : inimigos) {
            Rectangle r2 = alien.getBounds();
            if (r3.intersects(r2)) {
                nave.setVisible(false);
                alien.setVisible(false);
                endgame = true;
            }
        }
        ArrayList<Missil> ms = nave.getMissiles();
        for (Missil m : ms) {
            Rectangle r1 = m.getBounds();
            for (Inimigo alien : inimigos) {
                Rectangle r2 = alien.getBounds();
                if (r1.intersects(r2)) {
                    m.setVisible(false);
                    alien.setVisible(false);
                    score++;
					if(score > 10) {
						endgame = true;
					}
                }
            }
        }
    }

    private void updateInimigo() {

        while (inimigos.size() < 5) {
            String img = random.nextBoolean() ? "/imagens/alien.png" : "/imagens/enemy.png"; // escolhe um tipo aleatório
            inimigos.add(new Inimigo(B_WIDTH, random.nextInt(B_HEIGHT - 20) + 10), img);
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
}