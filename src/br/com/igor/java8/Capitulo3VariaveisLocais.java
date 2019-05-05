package br.com.igor.java8;

public class Capitulo3VariaveisLocais {

	public static void main(String[] args) {
		// Captura de vari�veis locais
		
		final int numero = 5;
		
		new Thread(() -> {
			System.out.println(numero);
		}).start();;
		
		int num = 19;
		new Thread(() ->{
			System.out.println(num);
		}).start();

		// Por�m, ela deve ser efetivamente final. Isso �, apesar de n�o precisar declarar as
		// vari�veis locais como final, voc� n�o pode alter�-las se estiver utilizando-as dentro
		// do lambda. O seguinte c�digo n�o compila:
		
		// numero = 10; // por causa dessa linha!
		
	}

}
