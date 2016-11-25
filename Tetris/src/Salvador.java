import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

public class Salvador {

	public Salvador() {

	}

	public int[][] lerMatriz() {
		int[][] matriz = new int[20][10];
		try {
			FileReader arq1 = new FileReader("src\\Arquivos\\matriz.txt");
			BufferedReader lerArq = new BufferedReader(arq1);
			String matrizTexto;
			matrizTexto = lerArq.readLine();
			int cont = 0;

			for (int i = 0; i <= 19; i++) {
				for (int j = 0; j <= 9; j++) {
					matriz[i][j] = matrizTexto.charAt(cont) - 48;// ASCII 0 eh
																	// 48
					cont++;
				}
			}
			arq1.close();
		} catch (IOException e) {
			System.err.printf("Erro", e.getMessage());
		}
		return matriz;
	}

	public void gravarMatriz(int[][] matriz) {
		try {
			FileWriter arq = new FileWriter("src\\Arquivos\\matriz.txt");
			PrintWriter gravarArq = new PrintWriter(arq);
			String gravar;

			for (int i = 0; i <= 19; i++) {
				gravar = "";
				for (int j = 0; j <= 9; j++) {
					gravar = gravar + matriz[i][j];
				}
				gravarArq.printf(gravar);
			}

			arq.close();
		} catch (IOException e) {
			System.err.printf("Erro", e.getMessage());
		}
	}

	public String[] lerRanking() {
		String[] matrizTexto = new String[20];
		try {
			FileReader arq1 = new FileReader("src\\Arquivos\\ranking.txt");
			BufferedReader lerArq = new BufferedReader(arq1);

			for (int i = 0; i <= 19; i++) {
				matrizTexto[i] = lerArq.readLine();
			}
			arq1.close();
		} catch (IOException e) {
			System.err.printf("Erro", e.getMessage());
		}
		return matrizTexto;
	}

	public void gravarRanking(int score, String nome){
		try{
		String[] matrizTexto = this.lerRanking();
		int i = 10;
		int temp;
		String temps;
		while(i <= 19){
			if(score > Integer.parseInt(matrizTexto[i])){
				temp = Integer.parseInt(matrizTexto[i]);
				matrizTexto[i] = String.valueOf(score);
				score = temp;
				
				temps = matrizTexto[i-10];
				matrizTexto[i-10] = nome;
				nome = temps;
			}
			i++;
		}
		
		FileWriter arq = new FileWriter("src\\Arquivos\\ranking.txt");
		PrintWriter gravarArq = new PrintWriter(arq);
		for(int j = 0; j <= 19; j++){
			gravarArq.printf(matrizTexto[j]+"%n");
		}
		arq.close();
		} catch (IOException e) {
			System.err.printf("Erro", e.getMessage());
		}
	}
}
