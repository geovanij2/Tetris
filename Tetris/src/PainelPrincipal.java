import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class PainelPrincipal extends JPanel {
	Image fundo, amarelo, laranja, roxo, azul, azulclaro, verde, vermelho;
	Image[] imagens = new Image[8];
	Mapa mapa;
	JLabel score, line, level;

	public PainelPrincipal() {
		setLayout(null);
		mapa = new Mapa(this);
		addKeyListener(new TAdapter(mapa));

		ImageIcon referencia = new ImageIcon("src\\Img\\Fundo.png");
		imagens[0] = referencia.getImage();
		referencia = new ImageIcon("src\\Img\\Amarelo25x25.png");
		imagens[2] = referencia.getImage();
		referencia = new ImageIcon("src\\Img\\Azul25x25.png");
		imagens[7] = referencia.getImage();
		referencia = new ImageIcon("src\\Img\\AzulClaro25x25.png");
		imagens[3] = referencia.getImage();
		referencia = new ImageIcon("src\\Img\\Laranja25x25.png");
		imagens[6] = referencia.getImage();
		referencia = new ImageIcon("src\\Img\\Roxo25x25.png");
		imagens[1] = referencia.getImage();
		referencia = new ImageIcon("src\\Img\\Verde25x25.png");
		imagens[4] = referencia.getImage();
		referencia = new ImageIcon("src\\Img\\Vermelho25x25.png");
		imagens[5] = referencia.getImage();

		this.score = new JLabel(String.valueOf(mapa.pegarDados()[0]), SwingConstants.CENTER);
		score.setBounds(117, 366, 50, 50);
		score.setForeground(new Color(0xffffff));
		score.setFont(new Font("Comic Sans", Font.BOLD, 18));
		this.add(score);

		this.level = new JLabel(String.valueOf(mapa.pegarDados()[1]), SwingConstants.CENTER);
		level.setBounds(117, 427, 50, 50);
		level.setForeground(new Color(0xffffff));
		level.setFont(new Font("Comic Sans", Font.BOLD, 18));
		this.add(level);

		this.line = new JLabel(String.valueOf(mapa.pegarDados()[2]), SwingConstants.CENTER);
		line.setBounds(117, 485, 50, 50);
		line.setForeground(new Color(0xffffff));
		line.setFont(new Font("Comic Sans", Font.BOLD, 18));
		this.add(line);
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(imagens[0], 0, 0, this);
		int[][] matriz = mapa.pegarMapa();

		for (int i = 0; i < 20; i++) {
			for (int j = 0; j < 10; j++) {
				if (matriz[i][j] != 0) {
					g.drawImage(imagens[matriz[i][j]], j * 26 + 268, i * 26 + 40, this);
				}
			}
		}
		score.setText(mapa.pegarDados()[0] + "");
		level.setText(mapa.pegarDados()[1] + "");
		line.setText(mapa.pegarDados()[2] + "");
		Peca PecaS = mapa.pegarPecaSegurada();
		Peca Peca1 = mapa.pegarPecaProxima1();
		Peca Peca2 = mapa.pegarPecaProxima2();
		Peca Peca3 = mapa.pegarPecaProxima3();

		if (PecaS != null) {
			PecaS.refatorar();
			for (int i = 0; i < 2; i++) {
				for (int j = 0; j < PecaS.getOrdem(); j++) {
					if (PecaS.getMatriz()[i][j]) {
						g.drawImage(imagens[PecaS.getCodigo()], j * 26 + 105 - (PecaS.getOrdem() - 3) * 12,
								i * 26 + 110, this);
					}
				}
			}
		}
		if (Peca1 != null) {
			Peca1.refatorar();
			for (int i = 0; i < 2; i++) {
				for (int j = 0; j < Peca1.getOrdem(); j++) {
					if (Peca1.getMatriz()[i][j]) {
						g.drawImage(imagens[Peca1.getCodigo()], j * 26 + 618 - (Peca1.getOrdem() - 3) * 12,
								i * 26 + 110, this);
					}
				}
			}
		}
		if (Peca2 != null) {
			Peca2.refatorar();
			for (int i = 0; i < 2; i++) {
				for (int j = 0; j < Peca2.getOrdem(); j++) {
					if (Peca2.getMatriz()[i][j]) {
						g.drawImage(imagens[Peca2.getCodigo()], j * 26 + 618 - (Peca2.getOrdem() - 3) * 12,
								i * 26 + 180, this);
					}
				}
			}
		}
		if (Peca3 != null) {
			Peca3.refatorar();
			for (int i = 0; i < 2; i++) {
				for (int j = 0; j < Peca3.getOrdem(); j++) {
					if (Peca3.getMatriz()[i][j]) {
						g.drawImage(imagens[Peca3.getCodigo()], j * 26 + 618 - (Peca3.getOrdem() - 3) * 12,
								i * 26 + 250, this);
					}
				}
			}
		}
		
	}

}
