import javax.swing.JFrame;

public class Tetris extends JFrame {
	String nome;
	
	public static void main(String[] args) {
		Tetris game = new Tetris();
		game.setLocationRelativeTo(null);
		game.setVisible(true);
	}

	Tetris() {
		setSize(804, 625);
		setResizable(false);
		setTitle("Tetris");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		telaInicial();
	}
	public void telaScore(){
		Score quadro = new Score(this);
		setContentPane(quadro);
		quadro.setFocusable(true);
		quadro.grabFocus();
		revalidate();
	}
	public void telaInicial(){
		TelaInicial quadro = new TelaInicial(this);
		setContentPane(quadro);
		quadro.setFocusable(true);
		quadro.grabFocus();
		revalidate();
	}
	public void jogoNormal() {
		getContentPane().setFocusable(false);
		PainelPrincipal quadro = new PainelPrincipal(this);
		setContentPane(quadro);
		quadro.setFocusable(true);
		quadro.grabFocus();
		revalidate();
	}
	public void jogoNormal(int[][] matriz) {
		getContentPane().setFocusable(false);
		PainelPrincipal quadro = new PainelPrincipal(matriz, this);
		setContentPane(quadro);
		quadro.setFocusable(true);
		quadro.grabFocus();
		revalidate();
	}
	public void setNome(String nome){
		if(nome.equals("")){
			this.nome = "ANÔNIMO";
		}else{
			this.nome = nome;
		}
	}
	public String getNome(){
		return nome;
	}
	public void editor() {
		TelaEditor quadro = new TelaEditor(this);
		setContentPane(quadro);
		quadro.setFocusable(true);
		revalidate();
	}

}
