import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;


public class Mapa{
	public boolean[][] mapa = new boolean[20][10];
	private long periodo = 200;
	private Peca pecaAtual, pecaProxima1, pecaProxima2, pecaProxima3, pecaSegurada;
	private boolean inicializarPecas = true;
	private boolean podePegarProximaPeca = true;
	private boolean seguraPeca = true;
	Timer t;
	TimerTask cicloDoJogo;
	int contador = 1;
	private boolean jogoPausado = false;

	Mapa() {
		t = new Timer();
		cicloDoJogo = new TimerTask() {
			@Override
			public void run() {

				// tudo que estiver aqui vai ser repetido em 1 segundo
				pegarProximaPeca();
				inicializarPecaNaMatrizPrincipal();
				Random a = new Random();
				int sorteio = a.nextInt(10);
				if(sorteio == 0){
					andarEsquerda();
				};
				if(sorteio == 1){
					andarDireita();
				}
				if(sorteio == 2){
					girarPeca();
				}
				if(sorteio == 3){
					derrubaPeca();
				}
				if(sorteio == 4){
					seguraPeca();
				}
				if(sorteio == 5){
					pausaJogo();
					resumeJogo();
				}
				if (contador % 4 == 0) {
					if (verificarSePecaPodeAndarVertical()) {
						deslocarPecaNaMatrizPrincipalVertical();
					} else {
						verificaLinhasCorretas();
					}
				}
				imprimirNoConsole();
				contador++;
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
	
	public void tirarPecaAtualDoMapa(){
		for (int i = 0; i < pecaAtual.getOrdem(); i++) {
			for (int j = 0; j < pecaAtual.getOrdem(); j++) {
				if(pecaAtual.getMatriz()[i][j]){
					atualizarOMapa(i+pecaAtual.getCoordenadaY(), j+pecaAtual.getCoordenadaX(), false);
				}
			}
		}	
	}

	public void deslocarPecaNaMatrizPrincipalVertical() {
		if (!inicializarPecas) {
			tirarPecaAtualDoMapa();
			pecaAtual.descerPeca();
			for(int i = 0; i < pecaAtual.getOrdem(); i++){
				for (int j = 0; j < pecaAtual.getOrdem(); j++) {
					if(pecaAtual.getMatriz()[i][j]){
						atualizarOMapa(i+pecaAtual.getCoordenadaY(), j+pecaAtual.getCoordenadaX(), true);
					}
				}
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
						atualizarOMapa(pecaAtual.getCoordenadaY() + i, pecaAtual.getCoordenadaX() + j, false);
						// limpa antigo local da peca no mapa
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
		podePegarProximaPeca = true;
		seguraPeca = true;
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

	public void derrubaPeca() { // erro: quando a peça eh derrubada ela continua jogavel
		for (int i = pecaAtual.getCoordenadaY(); i < 20; i++) {
			if(verificarSePecaPodeAndarVertical()){
				deslocarPecaNaMatrizPrincipalVertical();
			}
		}
	}

	public void girarPeca() {
		if (verificaGiro()) {
			for (int i = 0; i < pecaAtual.getOrdem(); i++) {
				for (int j = 0; j < pecaAtual.getOrdem(); j++) {
					if (pecaAtual.getMatriz()[i][j]) {
						atualizarOMapa(pecaAtual.getCoordenadaY() + i, pecaAtual.getCoordenadaX() + j, false);
					}
				}
			}
			pecaAtual.girar();
			for (int i = 0; i < pecaAtual.getOrdem(); i++) {
				for (int j = 0; j < pecaAtual.getOrdem(); j++) {
					if (pecaAtual.getMatriz()[i][j]) {
						atualizarOMapa(pecaAtual.getCoordenadaY() + i, pecaAtual.getCoordenadaX() + j,
								pecaAtual.getMatriz()[i][j]);
					}
				}
			}
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
					// se a peca girada continuar ocupando o espaco com true ele n faz
					if (teste[i][j] && mapa[pecaAtual.getCoordenadaY() + i][pecaAtual.getCoordenadaX() + j]) {
						// se no mapa ta true e a peca girada deu true eh pq
						// sobrepos e nao pode girar
						resposta = false;
					}
				}
			}
		}
		return resposta;
	}

	public void andarEsquerda(){
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
					} else if (mapa[pecaAtual.getCoordenadaY() + i][pecaAtual.getCoordenadaX() + j - 1]) {
						pode = false;
					}
					break;
				}
			}
		}
		
		if(pode){
			tirarPecaAtualDoMapa();
			pecaAtual.deslocarEsquerda();
			for(int i = 0; i < pecaAtual.getOrdem(); i++){
				for(int j = 0; j < pecaAtual.getOrdem(); j++){
					if(pecaAtual.getMatriz()[i][j]){
						atualizarOMapa(pecaAtual.getCoordenadaY()+i, pecaAtual.getCoordenadaX()+j, true);
					}
				}
			}
		}
	}
	
	public void andarDireita(){
		boolean pode = true;
		for (int i = 0; i < pecaAtual.getOrdem(); i++) {
			boolean achoutrue = false;
			for (int j = pecaAtual.getOrdem()-1; j >= 0; j--) {
				if (pecaAtual.getMatriz()[i][j]) {
					achoutrue = true;
				}
				if (achoutrue) {
					if (pecaAtual.getCoordenadaX() + j == 9) {
						pode = false;
					} else if (mapa[pecaAtual.getCoordenadaY() + i][pecaAtual.getCoordenadaX() + j + 1]) {
						pode = false;
					}
					break;
				}
			}
		}
		
		if(pode){
			tirarPecaAtualDoMapa();
			pecaAtual.deslocarDireita();
			for(int i = 0; i < pecaAtual.getOrdem(); i++){
				for(int j = 0; j < pecaAtual.getOrdem(); j++){
					if(pecaAtual.getMatriz()[i][j]){
						atualizarOMapa(pecaAtual.getCoordenadaY()+i, pecaAtual.getCoordenadaX()+j, true);
					}
				}
			}
		}
	}
	public void pausaJogo(){
		if (!jogoPausado){
		t.cancel(); // cancela o timer, acho que n posso usar esse metodo
		//acrescentar uma tela de pause??
		System.out.println("pause");
		}
	}
	public void resumeJogo(){ // talvez deva construir um novo mapa com as mesmas carac do antigo
		if (jogoPausado){
		t.scheduleAtFixedRate(cicloDoJogo, 0, periodo); //inicia denovo o timer criado quando o jogo começa com os mesmos parametros
		// voltar para a tela do jogo - adicionar
		System.out.println("jogoVoltou");
		}
	}
}
