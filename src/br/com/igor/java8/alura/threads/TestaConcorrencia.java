package br.com.igor.java8.alura.threads;

public class TestaConcorrencia {

	public static void main(String[] args) {

		BarraDeProgresso barra = new BarraDeProgresso();
		Thread t1 = new Thread(barra);
		t1.start();
		
		
		CopiadorDeArquivos copiador = new CopiadorDeArquivos();
		Thread t2 = new Thread(copiador);
		t2.start();
		
		
		
	}

}
