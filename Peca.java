import java.util.Random;
public class Peca implements InterfaceDaPeca{
	private boolean[][] matriz;
	int codigo;
	private int coordenadax = 3, coordenaday = 0;
	private int ordem;
	Peca(){
		Random rand = new Random();
		codigo = rand.nextInt(7);
		
		switch(codigo) {
			case 0:
				ordem = 3;
				matriz = new boolean[3][3];
				matriz[0][0] = false;     //0	1	0
				matriz[0][1] = true;	  //1   1   1
				matriz[0][2] = false;	  //0	0	0
				matriz[1][0] = true; 		
				matriz[1][1] = true;
				matriz[1][2] = true;
				matriz[2][0] = false;
				matriz[2][1] = false;
				matriz[2][2] = false;
			break;
			
			case 1:
				coordenadax = 4;
				ordem = 2;
				matriz = new boolean[2][2];  //1	1
				matriz[0][0]=true;			 //1	1
				matriz[0][1]=true;
				matriz[1][0]=true;
				matriz[1][1]=true;
			break;
			
			case 2:
				ordem = 4;
				matriz = new boolean[4][4];   
				matriz[0][0] = false;     //0	0 	0	0
				matriz[0][1] = false;	  //1	1	1	1 
				matriz[0][2] = false;	  //0	0	0	0
				matriz[0][3] = false;	  //0	0	0	0
				matriz[1][0] = true; 		
				matriz[1][1] = true;
				matriz[1][2] = true;
				matriz[1][3] = true;
				matriz[2][0] = false;
				matriz[2][1] = false;
				matriz[2][2] = false;
				matriz[2][3] = false;
				matriz[3][0] = false;
				matriz[3][1] = false;
				matriz[3][2] = false;
				matriz[3][3] = false;
			break;
			
			case 3:
				ordem = 3;
				matriz = new boolean[3][3];
				matriz[0][0] = false;     //0	1	1
				matriz[0][1] = true;	  //1   1   0
				matriz[0][2] = true;	  //0	0	0
				matriz[1][0] = true; 		
				matriz[1][1] = true;
				matriz[1][2] = false;
				matriz[2][0] = false;
				matriz[2][1] = false;
				matriz[2][2] = false;
			break;
			
			case 4:
				ordem = 3;
				matriz = new boolean[3][3];
				matriz[0][0] = true;      //1	1	0
				matriz[0][1] = true;	  //0   1   1
				matriz[0][2] = false;	  //0	0	0
				matriz[1][0] = false; 		
				matriz[1][1] = true;
				matriz[1][2] = true;
				matriz[2][0] = false;
				matriz[2][1] = false;
				matriz[2][2] = false;
			break;
			
			case 5:
				ordem = 3;
				matriz = new boolean[3][3];
				matriz[0][0] = false;     //0	0	1
				matriz[0][1] = false;	  //1   1   1
				matriz[0][2] = true;	  //0	0	0
				matriz[1][0] = true; 		
				matriz[1][1] = true;
				matriz[1][2] = true;
				matriz[2][0] = false;
				matriz[2][1] = false;
				matriz[2][2] = false;
			break;
			
			case 6:
				ordem = 3;
				matriz = new boolean[3][3];
				matriz[0][0] = true;      //1	0	0
				matriz[0][1] = false;	  //1   1   1
				matriz[0][2] = false;	  //0	0	0
				matriz[1][0] = true; 		
				matriz[1][1] = true;
				matriz[1][2] = true;
				matriz[2][0] = false;
				matriz[2][1] = false;
				matriz[2][2] = false;
			break;
		}
		
	}
	@Override
	public boolean[][] getMatriz() {
		return matriz.clone();
	}
	public int getCoordenadaX(){
		return coordenadax;
	}
	public int getCoordenadaY(){
		return coordenaday;
	}
	public void descerPeca(){
		coordenaday++;
	}
	public int getOrdem(){
		return ordem;
	}
	public void resetarCoordenadas(){
		if(codigo == 1){
			coordenadax = 4;
		}else{
			coordenadax = 3;
		}
		coordenaday = 0;
	}
	public int nLinhasNulasAbaixo(){
		int contador = 0;
		
		for(int i = matriz.length -1; i > 0; i--){
			boolean a = true;
			for(int j = 0; j < matriz.length; j++){
				if(matriz[i][j]){
					a = false;
				}
			}
			if(a){
				contador++;
			}
		}
		return contador;
		
	}
	@Override
	public void girar() {
		int ordem = matriz.length;
		boolean[][] b = new boolean[ordem][ordem];
		for(int i = 0; i < matriz.length; i++){
			for(int j = 0; j < matriz.length; j++){
				b[j][ordem-1] = matriz[i][j];
			}
			ordem--;
		}
		matriz = b;
	}
	public void deslocarEsquerda(){
		coordenadax--;
	}
	public void deslocarDireita(){
		coordenadax++;
	}
}
