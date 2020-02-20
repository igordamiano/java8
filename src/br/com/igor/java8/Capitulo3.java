package br.com.igor.java8;

public class Capitulo3 {

	public static void main(String[] args) {

		// Interface funcional tem somente um método abstrato
		// Pode ser intanciada através de uma expressão lambda
	
		Runnable r1 = () -> {

			for (int i = 0; i < 1000; i++) {
				System.out.println(i);
			}

		};
		new Thread(r1).start();

		// fazer tudo em um único statement, com talvez um pouco menos de legibilidade:
		new Thread(() -> {
			for (int i = 0; i < 1000; i++) {
				System.out.println(i);
			}
		}).start();
	}

	// Nossa interface funcional
	Validador<String> validadorCEP = new Validador<String>() {
		@Override
		public boolean valida(String valor) {
			return valor.matches("[0-9]{5}-[0-9]{3}");
		}
	};

	// expressão lambda
	Validador<String> validCEP = valor -> {
		return valor.matches("[0-9]{5}-[0-9]{3}");
	};

	// expressão lambda resumida
	Validador<String> validResumidaCEP = valor -> valor.matches("[0-9]{5}-[0-9]{3}");

}
