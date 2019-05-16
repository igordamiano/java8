package br.com.igor.java8.alura.threads;

public class BarraDeProgresso implements Runnable {

	@Override
	public void run() {

		for (int i = 0; i < 10000; i++) {
			System.out.println("Barra de progreso");
		}

	}

}
