package br.com.igor.java8;

public class Capitulo3VariaveisLocais {

	public static void main(String[] args) {
		// Captura de variáveis locais
		
		final int numero = 5;
		
		new Thread(() -> {
			System.out.println(numero);
		}).start();;
		
		int num = 19;
		new Thread(() ->{
			System.out.println(num);
		}).start();

		// Porém, ela deve ser efetivamente final. Isso é, apesar de não precisar declarar as
		// variáveis locais como final, você não pode alterá-las se estiver utilizando-as dentro
		// do lambda. O seguinte código não compila:
		
		// numero = 10; // por causa dessa linha!
		
	}

}
