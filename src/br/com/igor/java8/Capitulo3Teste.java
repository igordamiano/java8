package br.com.igor.java8;

public class Capitulo3Teste {

	public static void main(String[] args) {

		// Teste
		Capitulo3 cap3 = new Capitulo3();
		System.out.println(cap3.validadorCEP.valida("05367-080"));
		
		//
		System.out.println(cap3.validCEP.valida("05367-080"));

		//
		System.out.println(cap3.validResumidaCEP.valida("05367-0801"));
		
		Runnable o = () -> {
			System.out.println("O que eu sou ? Que lambda ?");
		};
		o.run();
		System.out.println(o);
		System.out.println(o.getClass());
	}

}
