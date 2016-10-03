import java.util.Timer;
import java.util.TimerTask;

public class Mapa{
	public boolean[][] mapa = new boolean[20][10];	
	private long periodo = 100;
	private Peca pecaAtual,pecaProxima1, pecaProxima2,pecaProxima3;
	private boolean inicializarPecas = true;
	private boolean podePegarProximaPeca = true;
	Timer t;
		Mapa(){
		t = new Timer();
		TimerTask cicloDoJogo = new TimerTask(){
			 @Override
			 public void run(){
				//tudo que estiver aqui vai ser repetido em 1 segundo
				 deslocarPecaNaMatrizPrincipalVertical();
				 pegarProximaPeca();
				 inicializarPecaNaMatrizPrincipal();
				 
				 int[][] a = new int[20][10]; 
				 for(int i = 0; i < mapa.length; i++){
						for(int j = 0; j < mapa[i].length; j++){
							if(mapa[i][j]){
								a[i][j] = 1;
							}else{
								a[i][j] = 0;
							}
						}
						
					}
				 for(int i = 0; i < a.length; i++){
					 for(int j = 0; j < a[i].length; j++){
						 System.out.print(a[i][j]+"  ");
					 }
					 System.out.println("");
					 
				 }
				 System.out.println("");
				 System.out.println("");
				 
			}
			 	
			 
		 };
		 t.scheduleAtFixedRate(cicloDoJogo, 0, periodo);
	}
	
	public void pegarProximaPeca(){
		if(inicializarPecas){
			pecaAtual = new Peca();
			pecaProxima1 = new Peca();
			pecaProxima2 = new Peca();
			pecaProxima3 = new Peca();
			inicializarPecas = false;
		}else if(podePegarProximaPeca){
			pecaAtual = pecaProxima1;
			pecaProxima1 = pecaProxima2;
			pecaProxima2 = pecaProxima3;
			pecaProxima3 = new Peca();
		}
	}
	
	public void inicializarPecaNaMatrizPrincipal(){
		if(podePegarProximaPeca){
			if(!acabouOJogo()){
				for(int i = 0; i < pecaAtual.getOrdem(); i++){
					for(int j = 0; j < pecaAtual.getOrdem(); j++){
						mapa[i][pecaAtual.getCoordenadaX()+j] = pecaAtual.getMatriz()[i][j];
					}	
				}
				podePegarProximaPeca = false;
			}else{
				t.cancel();	
			}
		}
	}
	
	public boolean verificarSePecaPodeAndarVertical(){//refazer
		boolean pode = true;
		for(int j = 0; j < pecaAtual.getOrdem();j++){
			boolean achoutrue = false;
			for(int i = pecaAtual.getOrdem()-1; i >= 0; i--){
				if(pecaAtual.getMatriz()[i][j]){
					achoutrue = true;
				}
				if(achoutrue){
					if(pecaAtual.getCoordenadaY()+i+1 == 20){
						pode = false;
					}else if(mapa[pecaAtual.getCoordenadaY()+i+1][pecaAtual.getCoordenadaX()+j]){
						pode = false;
					}
					break;
				}
			}
		}
		return pode;
	}
	
	public void deslocarPecaNaMatrizPrincipalVertical(){//refazer
		if(!inicializarPecas){
			if(verificarSePecaPodeAndarVertical()){
				for(int j = pecaAtual.getCoordenadaX(); j < pecaAtual.getCoordenadaX()+pecaAtual.getOrdem(); j++){
					mapa[pecaAtual.getCoordenadaY()][j]= false; //apaga a primeira linha da peça
				}
				pecaAtual.descerPeca();
				for(int j = 0; j < pecaAtual.getOrdem(); j++){
					boolean achou = false;
					for(int i = pecaAtual.getOrdem()-1; i >= 0; i--){
						if(pecaAtual.getMatriz()[i][j]){
							achou = true;
						}
						if(achou){
							mapa[i+pecaAtual.getCoordenadaY()][pecaAtual.getCoordenadaX()+j] = pecaAtual.getMatriz()[i][j];
						}
					}
				}
			
			}else{
				podePegarProximaPeca = true;
			}
		}
	}
	public boolean acabouOJogo(){
		boolean acabou = false;
		for(int i = 0; i < pecaAtual.getOrdem(); i++){
			for(int j = 0; j < pecaAtual.getOrdem(); j++){
				if(pecaAtual.getMatriz()[i][j] && mapa[i][j+pecaAtual.getCoordenadaX()]){
					acabou = true;
				}
			}
		}
		return acabou;
	}
}
