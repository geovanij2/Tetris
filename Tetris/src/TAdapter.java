import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class TAdapter extends KeyAdapter {
	Mapa mapa;

	TAdapter(Mapa mapa) {
		this.mapa = mapa;
	}

	public void keyPressed(KeyEvent e) {

		int keycode = e.getKeyCode();

		switch (keycode) {
		case KeyEvent.VK_LEFT:
			//mapa.andarEsquerda();
			mapa.deslocarParaE = true;
			break;
		case KeyEvent.VK_RIGHT:
			//mapa.andarDireita();
			mapa.deslocarParaD = true;
			break;
		case KeyEvent.VK_DOWN:
			/*if (mapa.verificarSePecaPodeAndarVertical()) {
				mapa.deslocarPecaNaMatrizPrincipalVertical();
			} else {
				mapa.verificaLinhasCorretas();
			}*/
			mapa.deslocarParaB = true;
			break;
		case KeyEvent.VK_UP:
			//mapa.girarPeca();
			mapa.gire = true;
			break;
		case KeyEvent.VK_SPACE:
			//mapa.derrubaPeca();
			mapa.derruba = true;
			break;
		case 'c':
			//mapa.seguraPeca();
			mapa.segure = true;
			break;
		case 'C':
			//mapa.seguraPeca();
			mapa.segure = true;
			break;
		case 'p':
			mapa.pausaJogo();
			break;
		case 'P':
			mapa.pausaJogo();
			break;
		}

	}
}
