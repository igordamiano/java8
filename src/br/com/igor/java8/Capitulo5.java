package br.com.igor.java8;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.function.ToIntFunction;

public class Capitulo5 {

	public static void main(String[] args) {

		Usuario user1 = new Usuario("Paulo Silveira", 150);
		Usuario user2 = new Usuario("Rodrigo Turini", 120);
		Usuario user3 = new Usuario("Guilherme Silveira", 190);

		List<Usuario> usuarios = Arrays.asList(user1, user2, user3);

		
		Comparator<Usuario> comparator = new Comparator<Usuario>() {
			
			@Override
			public int compare(Usuario u1, Usuario u2) {
				return u1.getNome().compareTo(u2.getNome());
			}
		};
		
		
		Collections.sort(usuarios, comparator);
		usuarios.forEach(u -> System.out.println(u.getNome()));
		
		Comparator<Usuario> compLambda = (u1, u2) -> u1.getNome().compareTo(u2.getNome());
		Collections.sort(usuarios, compLambda);
		usuarios.forEach(u -> System.out.println(u.getNome()));
				
		// Em uma mesma linha
		Collections.sort(usuarios, (u1, u2) -> u1.getNome().compareTo(u2.getNome()));
		usuarios.forEach(u -> System.out.println(u.getNome()));
		
		//O método List.sort - Podemos ordenar uma lista de usuários de forma ainda mais sucinta:
		usuarios.sort((u1, u2) -> u1.getNome().compareTo(u2.getNome()));
		
		// 
		usuarios.sort(Comparator.comparing(u -> u.getNome()));
		usuarios.sort(Comparator.comparing(u -> u.getPontos()));
		usuarios.forEach(u -> System.out.println(u.getPontos()));
		System.out.println("-----------------------------------");
		
		// Indexando pela ordem natural
		List<String> palavras = Arrays.asList("Casa do Código", "Alura", "Caelum"); 
		//Collections.sort(palavras);
		
		palavras.sort(Comparator.naturalOrder());
		palavras.forEach(p -> System.out.println(p));
		System.out.println("-----------------------------------");
		palavras.sort(Comparator.reverseOrder());
		palavras.forEach(p -> System.out.println(p));
		
		System.out.println("--------------Ordenando por pontos---------------------");
		// Ordenando por pontos
		usuarios.sort(Comparator.comparing(u -> u.getPontos()));
		usuarios.forEach(u -> System.out.println(u.getPontos()));
		
		// Para enxergar melhor o que acontece, podemos quebrar esse código em mais
		// linhas e variáveis locais
		System.out.println("--------------Ordenando por pontos---------------------");
		
		Function<Usuario, Integer> extraiPontos = u -> u.getPontos();
		Comparator<Usuario> comparator1 = Comparator.comparing(extraiPontos);
		usuarios.sort(comparator1);
		usuarios.forEach(u -> System.out.println(u.getPontos()));
		
		System.out.println("--------------Ordenando por pontos---------------------");
		// Autoboxing nos lambdas
		// Para evitar esse autoboxing (e às vezes o unboxing) desnecessário, há interfaces 
		// equivalentes que trabalham diretamente com long, double e int.
		
		ToIntFunction<Usuario> extraiPontos2 = u -> u.getPontos();
		Comparator<Usuario> comparator2 = Comparator.comparingInt(extraiPontos2);
		usuarios.sort(comparator2);
		usuarios.forEach(u -> System.out.println(u.getPontos()));
		// Versão mais enxuta Lambda
		usuarios.sort(Comparator.comparingInt(u -> u.getPontos()));
	}

}
