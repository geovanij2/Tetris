import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JPanel;

public class Mapa {

	private int[][] mapa;
	private long periodo = 10;
	private Peca pecaAtual = null;
	private Peca pecaProxima1 = null;
	private Peca pecaProxima2 = null;
	private Peca pecaProxima3 = null;
	private Peca pecaSegurada = null;
	private boolean inicializarPecas = true;
	private boolean podePegarProximaPeca = true;
	private boolean seguraPeca = true;
	private Timer t;
	private TimerTask cicloDoJogo;
	private int contador = 1, score = 0, nivel = 0, numerolinhasquebradas = 0, line = 0;
	private boolean jogoPausado = false, modopersonalizado = false, win = false;
	private PainelPrincipal quadro;
	public boolean deslocarParaE = false, deslocarParaD = false, derruba = false, gire = false, segure = false, deslocarParaB = false ;
	
	Mapa(PainelPrincipal quadro) {
		this.quadro = quadro;
		if(quadro.getMatriz() == null){
			mapa = new int[20][10];
		}else{
			mapa = quadro.getMatriz();
			modopersonalizado = true;
		}
		t = new Timer();
		pegarProximaPeca();
		inicializarPecaNaMatrizPrincipal();
		cicloDoJogo = new TimerTask() {
			@Override
			public void run() {
				if (!jogoPausado) {
					if (numerolinhasquebradas >= 10 && nivel < 9) {
						nivel++;
						numerolinhasquebradas -= 10;
					}
					if (contador % (100 - nivel*10) == 0) {
						if (verificarSePecaPodeAndarVertical()) {
							deslocarPecaNaMatrizPrincipalVertical();
						} else {
							verificaLinhasCorretas();
						}
						inicializarPecaNaMatrizPrincipal();
					}else{
						if(deslocarParaB){
							if (verificarSePecaPodeAndarVertical()) {
								deslocarPecaNaMatrizPrincipalVertical();
							} else {
								//verificaLinhasCorretas();
								
							}
							deslocarParaB = false;
						}
						if(deslocarParaE){
							andarEsquerda();
							deslocarParaE = false;
						}
						if(deslocarParaD){
							andarDireita();
							deslocarParaD = false;
						}
						if(gire){
							girarPeca();
							gire = false;
						}
						if(derruba){
							derrubaPeca();
							derruba = false;
						}
						if(segure){
							seguraPeca();
							segure = false;
						}
						
					}
					contador++;
				}
			}
		};
		t.scheduleAtFixedRate(cicloDoJogo, 300, periodo);
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
							atualizarOMapa(i, pecaAtual.getCoordenadaX() + j, pecaAtual.getCodigo());
						}
					}
				}
				quadro.repaint();
				podePegarProximaPeca = false;

			} else {
				t.cancel();
				quadro.setFocusable(false);
				Salvador a = new Salvador();
				a.gravarRanking(score, quadro.tetris.getNome());
				if(win){
					quadro.tetris.telaInicial();
				}else{
					quadro.tetris.telaInicial();
				}
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
					} else if (mapa[pecaAtual.getCoordenadaY() + i + 1][pecaAtual.getCoordenadaX() + j] != 0) {
						pode = false;
					}
					break;
				}
			}
		}
		return pode;
	}

	public void tirarPecaAtualDoMapa() {
		for (int i = 0; i < pecaAtual.getOrdem(); i++) {
			for (int j = 0; j < pecaAtual.getOrdem(); j++) {
				if (pecaAtual.getMatriz()[i][j]) {
					atualizarOMapa(i + pecaAtual.getCoordenadaY(), j + pecaAtual.getCoordenadaX(), 0);
				}
			}
		}
		quadro.repaint();

	}

	public void deslocarPecaNaMatrizPrincipalVertical() {
		if (!inicializarPecas && !jogoPausado) {
			tirarPecaAtualDoMapa();
			pecaAtual.descerPeca();
			for (int i = 0; i < pecaAtual.getOrdem(); i++) {
				for (int j = 0; j < pecaAtual.getOrdem(); j++) {
					if (pecaAtual.getMatriz()[i][j]) {
						atualizarOMapa(i + pecaAtual.getCoordenadaY(), j + pecaAtual.getCoordenadaX(),
								pecaAtual.getCodigo());
					}
				}
			}
			quadro.repaint();
		}

	}

	public void atualizarOMapa(int x, int y, int valor) {
		mapa[x][y] = valor;
	}

	public boolean acabouOJogo() {
		boolean acabou = false;
		for (int i = 0; i < pecaAtual.getOrdem(); i++) {
			for (int j = 0; j < pecaAtual.getOrdem(); j++) {
				if (pecaAtual.getMatriz()[i][j] && mapa[i][j + pecaAtual.getCoordenadaX()] != 0) {
					acabou = true; // e ele perdeu
				}
			}
		}
		if(modopersonalizado){ //se estiver no modo personalizado ganha se eliminar todas as pecas
			int contador = 0;
			for(int i = 0; i <= 19; i++){
				for(int j = 0; j <= 9; j++){
					contador += mapa[i][j];
				}
			}
			if(contador == 0){
				acabou = true; // e ele ganhou
				win = true;
			}
		}
		return acabou;
	}

	public void seguraPeca() {
		if (seguraPeca && !jogoPausado) {
			for (int i = 0; i < pecaAtual.getOrdem(); i++) {
				for (int j = 0; j < pecaAtual.getOrdem(); j++) {
					if (pecaAtual.getMatriz()[i][j]) {
						atualizarOMapa(pecaAtual.getCoordenadaY() + i, pecaAtual.getCoordenadaX() + j, 0);
						// limpa antigo local da peca no mapa
					}
				}
			}
			quadro.repaint();
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
				for (int i = 0; i < pecaAtual.getOrdem(); i++) {
					for (int j = 0; j < pecaAtual.getOrdem(); j++) {
						if (pecaAtual.getMatriz()[i][j]) {
							atualizarOMapa(pecaAtual.getCoordenadaY() + i, pecaAtual.getCoordenadaX() + j,
									pecaAtual.getCodigo());
						}
					}
				}
				quadro.repaint();
			}
			seguraPeca = false;

		}
	}

	public void verificaLinhasCorretas() {
		if (!jogoPausado) {
			int contadordelinhas = 0;
			for (int i = 0; i < mapa.length; i++) {
				boolean achou = true;
				for (int j = 0; j < mapa[i].length; j++) {
					if (mapa[i][j] == 0) {
						achou = false;
					}
				}
				if (achou) {
					contadordelinhas++;
					deslocarUmaLinhaTudo(i);
				}
			}
			recalcularScore(contadordelinhas);
			numerolinhasquebradas += contadordelinhas;
			line += contadordelinhas;
			podePegarProximaPeca = true;
			pegarProximaPeca();
			seguraPeca = true;
		}
	}

	private void recalcularScore(int contadordelinhas) {
		if (!jogoPausado) {
			if (contadordelinhas == 1) {
				score += 40 * (nivel+1);
			} else if (contadordelinhas == 2) {
				score += 100 * (nivel+1);
			} else if (contadordelinhas == 3) {
				score += 300 * (nivel+1);
			} else if (contadordelinhas == 4) {
				score += 1200 * (nivel+1);
			}
		}
	}

	public void excluirLinha(int i) {
		if (!jogoPausado) {
			for (int j = 0; j < mapa[i].length; j++) {
				atualizarOMapa(i, j, 0);
			}
			quadro.repaint();
		}
	}

	public void deslocarUmaLinhaTudo(int valor) {
		if (!jogoPausado) {
			for (int i = valor; i > 0; i--) {
				for (int j = 0; j < mapa[i].length; j++) {
					atualizarOMapa(i, j, mapa[i - 1][j]);
				}

			}
			quadro.repaint();
			excluirLinha(0);
		}
	}

	public void derrubaPeca() {
		if (!jogoPausado) {
			for (int i = pecaAtual.getCoordenadaY(); i < 20; i++) {
				if (verificarSePecaPodeAndarVertical()) {
					deslocarPecaNaMatrizPrincipalVertical();
				} else {
					verificaLinhasCorretas();
					break;
				}
			}
			inicializarPecaNaMatrizPrincipal();
		}
	}

	public void girarPeca() {
		if (verificaGiro() && !jogoPausado) {
			tirarPecaAtualDoMapa();
			quadro.repaint();
			pecaAtual.girar();
			for (int i = 0; i < pecaAtual.getOrdem(); i++) {
				for (int j = 0; j < pecaAtual.getOrdem(); j++) {
					if (pecaAtual.getMatriz()[i][j]) {
						atualizarOMapa(pecaAtual.getCoordenadaY() + i, pecaAtual.getCoordenadaX() + j,
								pecaAtual.getCodigo());
					}
				}
			}
			quadro.repaint();
		}
		if (verificarSePecaPodeAndarVertical()) {
			podePegarProximaPeca = false;
		}
	}

	public boolean verificaGiro() { // verifica se a peca pode girar
		boolean resposta = true;
		boolean[][] teste = pecaAtual.getMatriz();

		int ordem = teste.length;// gira a peca p direita
		boolean[][] b = new boolean[ordem][ordem];
		for (int i = 0; i < teste.length; i++) {
			for (int j = 0; j < teste.length; j++) {
				b[j][ordem - 1] = teste[i][j];
			}
			ordem--;
		}
		teste = b;

		for (int i = 0; i < pecaAtual.getOrdem(); i++) {
			for (int j = 0; j < pecaAtual.getOrdem(); j++) {
				if (teste[i][j]) { // se a peca girada tiver um true e ele
									// estiver fora dos limites
					if (pecaAtual.getCoordenadaY() + i >= 20) {
						// vai ultrapassar o mapa em baixo
						resposta = false;
					}
					if (pecaAtual.getCoordenadaX() + j >= 10 || pecaAtual.getCoordenadaX() + j < 0) {
						resposta = false;
					}
				}
				if (!(teste[i][j] && pecaAtual.getMatriz()[i][j]) && resposta) {
					// se a peca girada continuar ocupando o espaco com true ele
					// n faz
					if (teste[i][j] && mapa[pecaAtual.getCoordenadaY() + i][pecaAtual.getCoordenadaX() + j] != 0) {
						// se no mapa ta true e a peca girada deu true eh pq
						// sobrepos e nao pode girar
						resposta = false;
					}
				}
			}
		}
		return resposta;
	}

	public void andarEsquerda() {
		if (!jogoPausado) {
			boolean pode = true;
			for (int i = 0; i < pecaAtual.getOrdem(); i++) {
				boolean achoutrue = false;
				for (int j = 0; j < pecaAtual.getOrdem(); j++) {
					if (pecaAtual.getMatriz()[i][j]) {
						achoutrue = true;
					}
					if (achoutrue) {
						if (pecaAtual.getCoordenadaX() + j == 0) {
							pode = false;
						} else if (mapa[pecaAtual.getCoordenadaY() + i][pecaAtual.getCoordenadaX() + j - 1] != 0) {
							pode = false;
						}
						break;
					}
				}
			}
			if (pode) {
				tirarPecaAtualDoMapa();
				pecaAtual.deslocarEsquerda();
				for (int i = 0; i < pecaAtual.getOrdem(); i++) {
					for (int j = 0; j < pecaAtual.getOrdem(); j++) {
						if (pecaAtual.getMatriz()[i][j]) {
							atualizarOMapa(pecaAtual.getCoordenadaY() + i, pecaAtual.getCoordenadaX() + j,
									pecaAtual.getCodigo());
						}
					}
				}
				quadro.repaint();
			}

			if (verificarSePecaPodeAndarVertical()) {
				podePegarProximaPeca = false;
			}
		}
	}

	public void andarDireita() {
		if (!jogoPausado) {
			boolean pode = true;
			for (int i = 0; i < pecaAtual.getOrdem(); i++) {
				boolean achoutrue = false;
				for (int j = pecaAtual.getOrdem() - 1; j >= 0; j--) {
					if (pecaAtual.getMatriz()[i][j]) {
						achoutrue = true;
					}
					if (achoutrue) {
						if (pecaAtual.getCoordenadaX() + j == 9) {
							pode = false;
						} else if (mapa[pecaAtual.getCoordenadaY() + i][pecaAtual.getCoordenadaX() + j + 1] != 0) {
							pode = false;
						}
						break;
					}
				}
			}

			if (pode) {
				tirarPecaAtualDoMapa();
				pecaAtual.deslocarDireita();
				for (int i = 0; i < pecaAtual.getOrdem(); i++) {
					for (int j = 0; j < pecaAtual.getOrdem(); j++) {
						if (pecaAtual.getMatriz()[i][j]) {
							atualizarOMapa(pecaAtual.getCoordenadaY() + i, pecaAtual.getCoordenadaX() + j,
									pecaAtual.getCodigo());
						}
					}
				}
				quadro.repaint();
			}

			if (verificarSePecaPodeAndarVertical()) {
				podePegarProximaPeca = false;
			}
		}
	}

	public void pausaJogo() {
		if (!jogoPausado) {
			jogoPausado = true;
		} else {
			jogoPausado = false;
		}
	}

	public int[][] pegarMapa() {
		return mapa;
	}

	public int[] pegarDados() {
		return new int[] { score, nivel, line };
	}

	public Peca pegarPecaProxima1() {
		return pecaProxima1;
	}

	public Peca pegarPecaProxima2() {
		return pecaProxima2;
	}

	public Peca pegarPecaProxima3() {
		return pecaProxima3;
	}

	public Peca pegarPecaSegurada() {
		return pecaSegurada;
	}

}