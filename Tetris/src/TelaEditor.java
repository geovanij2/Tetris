import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public class TelaEditor extends JPanel implements ActionListener {
	private int[][] matriz = new int[20][10];
	private Image[] imagens = new Image[8];
	public int codigo = 1;
	Tetris tetris;
	TelaEditor(Tetris tetris) {
		this.tetris = tetris;
		setSize(804, 625);
		setLayout(null);
		JButton salvar = new JButton("Salvar");
		JButton recuperar = new JButton("Recuperar");
		JButton limpar = new JButton("Limpar");
		JButton jogar = new JButton("Jogar");
		JButton voltar = new JButton("Voltar");
		
		salvar.addActionListener(this);
		salvar.setActionCommand("salvar");
		limpar.addActionListener(this);
		limpar.setActionCommand("limpar");
		recuperar.addActionListener(this);
		recuperar.setActionCommand("recuperar");
		jogar.addActionListener(this);
		jogar.setActionCommand("jogar");
		voltar.addActionListener(this);
		voltar.setActionCommand("voltar");
		
		add(salvar);
		add(limpar);
		add(recuperar);
		add(jogar);
		add(voltar);
		
		jogar.setFont(new Font("Lucida Console", Font.PLAIN, 20));
		jogar.setBounds(580, 355, 160, 40);
		limpar.setFont(new Font("Lucida Console", Font.PLAIN, 20));
		limpar.setBounds(580, 400, 160, 40);
		recuperar.setFont(new Font("Lucida Console", Font.PLAIN, 20));
		recuperar.setBounds(580, 445, 160, 40);
		salvar.setFont(new Font("Lucida Console", Font.PLAIN, 20));
		salvar.setBounds(580, 490, 160, 40);
		voltar.setFont(new Font("Lucida Console", Font.PLAIN, 20));
		voltar.setBounds(580, 535, 160, 40);
		addMouseListener(new OuveCliques(this));

		ImageIcon referencia = new ImageIcon("src\\Img\\fundoeditar.png");
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
	}

	public void atualiza(int resultadox, int resultadoy, int resulx, int resuly) throws IOException {
		if (resultadox >= 0 && resultadox <= 9 && resultadoy >= 0 && resultadoy <= 19 && resulx >= 0 && resulx <= 9
				&& resuly >= 0 && resuly <= 19) {
			int x0, y0, lar, alt;
			lar = Math.abs(resultadox - resulx);
			alt = Math.abs(resultadoy - resuly);
			x0 = Math.min(resultadox, resulx);
			y0 = Math.min(resultadoy, resuly);
		
			for (int i = x0; i <= lar + x0; i++) {
				for (int j = y0; j <= alt + y0; j++) {
					if (matriz[j][i] == 0) {
						matriz[j][i] = codigo;
					} else {
						matriz[j][i] = 0;
					}
				}
			}

		}
		repaint();
	}
	public void limpaEditor(){
		matriz = new int[20][10];
		repaint();
	}
	public void jogar(){
		tetris.jogoNormal(matriz);
	}
	public void salvar(){
		Salvador a = new Salvador();
		a.gravarMatriz(matriz);
	}
	public void recuperar(){
		Salvador a = new Salvador();
		this.matriz = a.lerMatriz();
		repaint();
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(imagens[0], 0, 0, this);

		for (int i = 0; i < 20; i++) {
			for (int j = 0; j < 10; j++) {
				if (matriz[i][j] != 0) {
					g.drawImage(imagens[matriz[i][j]], j * 26 + 270, i * 26 + 40, this);
				}
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		
		if (cmd.equals("salvar")) {
			salvar();
		}
		if (cmd.equals("recuperar")) {
			recuperar();
		}
		if (cmd.equals("limpar")){
			limpaEditor();
		}
		if (cmd.equals("voltar")){
			tetris.telaInicial();
		}
		if (cmd.equals("jogar")){
			jogar();
		}
	}
}
