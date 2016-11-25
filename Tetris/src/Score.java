import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Score extends JPanel implements ActionListener {
	Tetris tetris;
	Image capa;

	Score(Tetris tetris) {
		this.tetris = tetris;
		setSize(804, 625);
		setLayout(null);
		ImageIcon referencia = new ImageIcon("src\\Img\\ranking.png");
		capa = referencia.getImage();

		Salvador a = new Salvador();
		String[] matrizrank = a.lerRanking();
		JButton voltar = new JButton("Voltar");
		JLabel nomes;

		voltar.setFont(new Font("Lucida Console", Font.PLAIN, 20));
		voltar.setBounds(653, 545, 120, 40);
		voltar.addActionListener(this);
		voltar.setActionCommand("voltar");
		add(voltar);
		for (int i = 0; i <= 9; i++) {
			nomes = new JLabel();
			nomes.setText(matrizrank[i].toUpperCase());
			nomes.setBounds(140, 240 + 30 * i, 500, 50);
			nomes.setForeground(new Color(0x000000));
			nomes.setFont(new Font("Lucida Console", Font.PLAIN, 30));
			add(nomes);
		}
		for (int i = 10; i <= 19; i++) {
			nomes = new JLabel();
			nomes.setText(matrizrank[i].toUpperCase());
			nomes.setBounds(490, 240 + 30 * (i - 10), 500, 50);
			nomes.setForeground(new Color(0x000000));
			nomes.setFont(new Font("Lucida Console", Font.PLAIN, 30));
			add(nomes);
		}
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(capa, 0, 0, this);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		if (cmd.equals("voltar")) {
			tetris.telaInicial();;
		}
		
	}
}
