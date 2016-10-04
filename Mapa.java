import java.util.Timer;
import java.util.TimerTask;

public class Mapa {
	public boolean[][] mapa = new boolean[20][10];
	private long periodo = 500;
	private Peca pecaAtual, pecaProxima1, pecaProxima2, pecaProxima3, pecaSegurada;
	private boolean inicializarPecas = true;
	private boolean podePegarProximaPeca = true;
	private boolean seguraPeca = true;
	private boolean alternadorDoTimer = true;
	Timer t;

	int contador = 0;

	Mapa() {
		t = new Timer();
		TimerTask cicloDoJogo = new TimerTask() {
			@Override
			public void run() {
				// tudo que estiver aqui vai ser repetido em 1 segundo
				/*if (contador % 10 == 0) {
					derrubaPeca();
				}*/				
				
				//System.out.println(contador);
				/*if (contador % 4 == 0){
					seguraPeca();
				}*/

				if (alternadorDoTimer) {
					deslocarPecaNaMatrizPrincipalVertical();
					pegarProximaPeca();
					inicializarPecaNaMatrizPrincipal();
					imprimirNoConsole();
					alternadorDoTimer = false;
				} else {
					verificaLinhasCorretas();
					alternadorDoTimer = true;
					if(contador%5 == 0){

					girarPeca();
					}
						contador ++;
				}
			}

		};
		t.scheduleAtFixedRate(cicloDoJogo, 0, periodo);
	}

	public void pegarProximaPeca() {
		if (inicializarPecas) {
			pecaAtual = new Peca();
			pecaProxima1 = new Peca();
			pecaProxima2 = new Peca();
			pecaProxima3 = new Peca();
			inicializarPecas = false;
		} else if (podePegarProximaPeca) {
			pecaAtual = pecaProxima1;
			pecaProxima1 = pecaProxima2;
			pecaProxima2 = pecaProxima3;
			pecaProxima3 = new Peca();
		}
	}

	public void inicializarPecaNaMatrizPrincipal() {
		if (podePegarProximaPeca) {
			if (!acabouOJogo()) {
				for (int i = 0; i < pecaAtual.getOrdem(); i++) {
					for (int j = 0; j < pecaAtual.getOrdem(); j++) {
						if (pecaAtual.getMatriz()[i][j]) {
							atualizarOMapa(i, pecaAtual.getCoordenadaX() + j, pecaAtual.getMatriz()[i][j]);
						}
					}
				}
				podePegarProximaPeca = false;
			} else {
				t.cancel();
			}
		}
	}

	public boolean verificarSePecaPodeAndarVertical() {
		boolean pode = true;
		for (int j = 0; j < pecaAtual.getOrdem(); j++) {
			boolean achoutrue = false;
			for (int i = pecaAtual.getOrdem() - 1; i >= 0; i--) {
				if (pecaAtual.getMatriz()[i][j]) {
					achoutrue = true;
				}
				if (achoutrue) {
					if (pecaAtual.getCoordenadaY() + i + 1 == 20) {
						pode = false;
					} else if (mapa[pecaAtual.getCoordenadaY() + i + 1][pecaAtual.getCoordenadaX() + j]) {
						pode = false;
					}
					break;
				}
			}
		}
		return pode;
	}

	public void deslocarPecaNaMatrizPrincipalVertical() {
		if (!inicializarPecas) {
			if (verificarSePecaPodeAndarVertical()) {
				for (int j = pecaAtual.getCoordenadaX(); j < pecaAtual.getCoordenadaX() + pecaAtual.getOrdem(); j++) {
					// apaga a primeira linha da peca
					atualizarOMapa(pecaAtual.getCoordenadaY(), j, false);
				}
				pecaAtual.descerPeca();
				for (int j = 0; j < pecaAtual.getOrdem(); j++) {
					boolean achou = false;
					for (int i = pecaAtual.getOrdem() - 1; i >= 0; i--) {
						if (pecaAtual.getMatriz()[i][j]) {
							achou = true;
						}
						if (achou) {
							atualizarOMapa(i + pecaAtual.getCoordenadaY(), pecaAtual.getCoordenadaX() + j,
									pecaAtual.getMatriz()[i][j]);
						}
					}
				}

			} else {
				podePegarProximaPeca = true;
				seguraPeca = true;
			}
		}
	}

	public void atualizarOMapa(int x, int y, boolean valor) {
		mapa[x][y] = valor;
	}

	public boolean acabouOJogo() {
		boolean acabou = false;
		for (int i = 0; i < pecaAtual.getOrdem(); i++) {
			for (int j = 0; j < pecaAtual.getOrdem(); j++) {
				if (pecaAtual.getMatriz()[i][j] && mapa[i][j + pecaAtual.getCoordenadaX()]) {
					acabou = true;
				}
			}
		}
		return acabou;
	}

	public void seguraPeca() {
		if (seguraPeca) {
			for (int i = 0; i < pecaAtual.getOrdem(); i++) {
				for (int j = 0; j < pecaAtual.getOrdem(); j++) {
					if (pecaAtual.getMatriz()[i][j]) {
						atualizarOMapa(pecaAtual.getCoordenadaY() + i, pecaAtual.getCoordenadaX() + j, false); // limpa antigo local da peca no mapa
					}
				}
			}
			pecaAtual.resetarCoordenadas();
			if (pecaSegurada == null) { // checa se ja tem peca segurada
				pecaSegurada = pecaAtual;
				podePegarProximaPeca = true;
				pegarProximaPeca();
				inicializarPecaNaMatrizPrincipal();
			} else {
				Peca t;
				t = pecaAtual;
				pecaAtual = pecaSegurada;
				pecaSegurada = t;
			}
			seguraPeca = false;
		}
	}

	public void imprimirNoConsole() {
		char[][] a = new char[20][10];
		for (int i = 0; i < mapa.length; i++) {
			for (int j = 0; j < mapa[i].length; j++) {
				if (mapa[i][j]) {
					a[i][j] = 'X';
				} else {
					a[i][j] = '.';
				}
			}

		}
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < a[i].length; j++) {
				System.out.print(a[i][j] + "  ");
			}
			System.out.println("");

		}
		System.out.println("");
		System.out.println("");
	}

	public void verificaLinhasCorretas() {
		for (int i = 0; i < mapa.length; i++) {
			boolean achou = true;
			for (int j = 0; j < mapa[i].length; j++) {
				if (!mapa[i][j]) {
					achou = false;
				}
			}
			if (achou) {
				deslocarUmaLinhaTudo(i);
			}
		}
	}

	public void excluirLinha(int i) {
		for (int j = 0; j < mapa[i].length; j++) {
			atualizarOMapa(i, j, false);
		}
	}

	public void deslocarUmaLinhaTudo(int valor) {
		for (int i = valor; i > 0; i--) {
			for (int j = 0; j < mapa[i].length; j++) {
				atualizarOMapa(i, j, mapa[i - 1][j]);
			}
		}
		excluirLinha(0);
	}

	public void derrubaPeca() {
		 for (int i = 0; i < 20; i++) {
		 	deslocarPecaNaMatrizPrincipalVertical();
		 }
	}
	public void girarPeca() {
		if(verificaGiro()) {	
			for (int i = 0; i < pecaAtual.getOrdem(); i++){
				for(int j = 0; j < pecaAtual.getOrdem(); j++){
					if (pecaAtual.getMatriz()[i][j]) {
						atualizarOMapa(pecaAtual.getCoordenadaY() + i, pecaAtual.getCoordenadaX() + j, false);
					}
				}
			}
			pecaAtual.girar();
		}	
	}
	public boolean verificaGiro(){ // verifica se a peca pode girar
		boolean resposta = true;
		boolean[][] teste = pecaAtual.getMatriz();

		int ordem = teste.length;//gira a peca p direita
		boolean[][] b = new boolean[ordem][ordem];
		for(int i = 0; i < teste.length; i++){
			for(int j = 0; j < teste.length; j++){
				b[j][ordem-1] = teste[i][j];
			}
			ordem--;
		}
		teste = b;

		for (int i = 0; i < pecaAtual.getOrdem(); i ++){
			for (int j = 0; j < pecaAtual.getOrdem(); j++) {
				if(!(teste[i][j] && pecaAtual.getMatriz()[i][j])){
					if(teste[i][j] && mapa[pecaAtual.getCoordenadaY()+i][pecaAtual.getCoordenadaX()+j]){
						resposta = false;
					}
				}
			}
		}
		return resposta;
	}
}
