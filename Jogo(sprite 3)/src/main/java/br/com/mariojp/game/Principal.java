package br.com.mariojp.game;

import java.awt.EventQueue;

import javax.swing.JFrame;

public class Principal extends JFrame {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Principal() {

        initUI();
    }
    
    private void initUI() {
        add(new Jogo());
        setResizable(false);
        pack();
        setTitle("Game");    
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);        
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {                
                JFrame ex = new Principal();
                ex.setVisible(true);                
            }
        });
    }
}