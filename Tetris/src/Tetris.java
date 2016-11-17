import javax.swing.JFrame;

public class Tetris extends JFrame  {
	
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
		
	   
		PainelPrincipal quadro = new PainelPrincipal();
	    setContentPane(quadro);
	    quadro.setFocusable(true);
	   
	}


}
