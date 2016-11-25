import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class TelaInicial extends JPanel implements ActionListener {
	Tetris tetris;
	Image capa;
	JTextField cx;
	
	TelaInicial(Tetris tetris) {
		this.tetris = tetris;
		setLayout(null);
		JLabel imagem = new JLabel();
		JButton jogoNormal = new JButton("Modo Normal");
		JButton editar = new JButton("Editar");
		JButton score = new JButton("Ranking");
		JLabel digite = new JLabel("Digite seu nome:");
		cx = new JTextField();
		
		ImageIcon referencia = new ImageIcon("src\\Img\\capa.png");
		capa = referencia.getImage();
		
		jogoNormal.addActionListener(this);
		jogoNormal.setActionCommand("normal");
		editar.addActionListener(this);
		editar.setActionCommand("editor");
		score.addActionListener(this);
		score.setActionCommand("score");
		
		digite.setFont(new Font("Lucida Console", Font.BOLD, 20));
		digite.setForeground(new Color(0xffffff));
		digite.setBounds(280, 200, 250, 40);
		digite.setHorizontalAlignment(digite.CENTER);
		cx.setBounds(302, 250, 200, 40);
		cx.setFont(new Font("Lucida Console", Font.PLAIN, 25));
		jogoNormal.setFont(new Font("Lucida Console", Font.PLAIN, 20));
		jogoNormal.setBounds(302, 300, 200, 40);
		editar.setFont(new Font("Lucida Console", Font.PLAIN, 20));
		editar.setBounds(302, 350, 200, 40);
		score.setFont(new Font("Lucida Console", Font.PLAIN, 20));
		score.setBounds(302, 400, 200, 40);
		
		add(digite);
		add(cx);
		add(imagem);
		add(score);
		add(editar);
		add(jogoNormal);
		repaint();
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
        g.drawImage(capa, 0, 0, this);
        
    }
	@Override
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		tetris.setNome(cx.getText());
		
		if (cmd.equals("normal")) {
			tetris.jogoNormal();
		}
		if (cmd.equals("editor")) {
			tetris.editor();
		}
		if (cmd.equals("score")){
			tetris.telaScore();
		}
	}
}
