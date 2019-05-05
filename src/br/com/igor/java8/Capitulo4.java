package br.com.igor.java8;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class Capitulo4 {

	public static void main(String[] args) {

		Usuario user1 = new Usuario("Paulo Silveira", 150);
		Usuario user2 = new Usuario("Rodrigo Turini", 120);
		Usuario user3 = new Usuario("Guilherme Silveira", 190);

		List<Usuario> usuarios = Arrays.asList(user1, user2, user3);

		Consumer<Usuario> mostraMensagem = u ->
			System.out.println("antes de imprimir os nomes");
			
		Consumer<Usuario> imprimeNome = u ->
			System.out.println(u.getNome());
			
		usuarios.forEach(mostraMensagem.andThen(imprimeNome));
		
		System.out.println("***************** removeIf ");
		// removeIf
		Predicate<Usuario> predicato = new Predicate<Usuario>() {
			
			@Override
			public boolean test(Usuario u) {
				return u.getPontos() > 160;
			}
		};
		
		List<Usuario> usuarios2 = new ArrayList<>();
		usuarios2.add(user1);
		usuarios2.add(user2);
		usuarios2.add(user3);
		
		//usuarios2.removeIf(predicato);
		//usuarios2.forEach(u -> System.out.println(u.getNome()));
		
		// usando exmpressão lambda
		usuarios2.removeIf(u -> u.getPontos() > 160);
		usuarios2.forEach(u -> System.out.println(u.getNome()));	
	}

}
