package br.com.igor.java8.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class TestaJavaIo {

	public static void main(String[] args) throws IOException {

		// Ler arquivo
		InputStream is = new FileInputStream("leitura.txt");
		InputStreamReader isr = new InputStreamReader(is);
		BufferedReader br = new BufferedReader(isr);
		
		// escrever arquivo
		OutputStream os = new FileOutputStream("saida.txt");
		OutputStreamWriter osw = new OutputStreamWriter(os);
		BufferedWriter bw = new BufferedWriter(osw);
		
		// lendo a linha
		String linha = br.readLine();
		
		while (linha != null) {
			// escrevendo a linha
			bw.append(linha);
			bw.newLine();

			System.out.println(linha);
			// lendo a próxima linha
			linha = br.readLine();
			
		}
		br.close();
		bw.close();		
		
		
		
	}

}
