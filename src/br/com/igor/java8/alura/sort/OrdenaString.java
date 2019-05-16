package br.com.igor.java8.alura.sort;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class OrdenaString {

	public static void main(String[] args) {

		List<String> palavras = Arrays.asList("alura online", "editora casa do codigo", "caelum");
		System.out.println(palavras);
		
		// antigo
		Collections.sort(palavras); // ordenando por ordem alfabetica
		System.out.println(palavras);
		
		// Passando nosso comparador por tamanho
		Comparator<String> comparadorPorTamanho = new ComparadorPorTamanho();
		Collections.sort(palavras, comparadorPorTamanho);
		System.out.println(palavras);
		
		// java 8
		System.out.println("---------------------------------------------------------");
		List<String> palavrasJ8 = Arrays.asList("alura online", "editora casa do codigo", "caelum");
		System.out.println(palavrasJ8);
		palavrasJ8.sort(comparadorPorTamanho);
		System.out.println(palavrasJ8);
		
		// default method foreach
		System.out.println("---------------------------------------------------------");
		palavrasJ8.forEach(System.out::println);
		
		
		
		
		
		
	}

}
