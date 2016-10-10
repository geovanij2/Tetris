import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Tetris extends JFrame implements ActionListener {

	public static void main(String[] args) {
		Tetris game = new Tetris();
		game.setLocationRelativeTo(null);
        game.setVisible(true);
	}

	Tetris() {
		setFocusable(true);
		setSize(800, 600);
		setTitle("Tetris");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		Mapa mapa = new Mapa();
		JPanel quadro = new JPanel();
	    setContentPane(quadro);
	    quadro.setBounds(275, 50, 250, 500);
	}

	@Override
	class TAdapter extends KeyAdapter {
        public void keyPressed(KeyEvent e) {
        	
        }
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
