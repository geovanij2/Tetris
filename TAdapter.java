import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class TAdapter extends KeyAdapter {
	TAdapter() {
		
	}
    public void keyPressed(KeyEvent e, Mapa m) {
        
    	int keycode = e.getKeyCode();

        switch (keycode) {
        case KeyEvent.VK_LEFT:
        	m.andarEsquerda();
            break;
        case KeyEvent.VK_RIGHT:
            m.andarDireita();
            break;
        case KeyEvent.VK_DOWN:
        	m.deslocarPecaNaMatrizPrincipalVertical();
            break;
        case KeyEvent.VK_UP:
            m.girarPeca();
            break;
        case KeyEvent.VK_SPACE:
            m.derrubaPeca();
            break;
        case 'c':
        	m.seguraPeca();
            break;
        case 'C':
            m.seguraPeca();
            break;
        case 'p':
        	m.pausaJogo();
        	break;
        case 'P':
        	m.pausaJogo();
        	break;
        }

    }
}

