package br.com.igor.java8;

public class InterfaceRunnableCapitulo3 {

	public static void main(String[] args) {
		// Interface Funcional, tem somente um único método abstrato
		Runnable r = new Runnable() {
			
			@Override
			public void run() {

				for (int i = 0; i <= 1000; i++) {
					System.out.println(i);
				}
			}
		};
		new Thread(r).start();
		
	}

}
