import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class TelaInicial extends JPanel implements ActionListener {
	Tetris tetris;
	TelaInicial(Tetris tetris) {
		this.tetris = tetris;
		setLayout(null);
		JButton jogoNormal = new JButton("MODO NORMAL");
		JButton editar = new JButton("EDITAR");
		JButton score = new JButton("SCORE");
		
		jogoNormal.addActionListener(this);
	    jogoNormal.setActionCommand("normal");
	    
		jogoNormal.setBounds(302, 200, 200, 40);
		editar.setBounds(302, 250, 200, 40);
		score.setBounds(302, 300, 200, 40);

		add(score);
		add(editar);
		add(jogoNormal);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		if(cmd.equals("normal")){
			tetris.jogoNormal();
		}
	}
}
