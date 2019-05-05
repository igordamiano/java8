package br.com.igor.java8;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class Capitulo2 {

	public static void main(String[] args) {

		Usuario user1 = new Usuario("Paulo Silveira", 150);
		Usuario user2 = new Usuario("Rodrigo Turini", 120);
		Usuario user3 = new Usuario("Guilherme Silveira", 190);

		List<Usuario> usuarios = Arrays.asList(user1, user2, user3);

		for (Usuario u : usuarios) {
			System.out.println(u.getNome());
		}
		System.out.println("******************************");
		
		// Um novo método em todas as coleções: forEach
		Mostrador mostrador = new Mostrador();
		usuarios.forEach(mostrador);
		
		System.out.println("-------------------------------");
		// Podemos reduzir um pouco mais esse código, evitando a criação da variável
		// local mostrador:
		
		usuarios.forEach(new Consumer<Usuario>() {

			@Override
			public void accept(Usuario t) {
				System.out.println(t.getNome());
			}
			
		});
		
		System.out.println("********************************");
		// Usando lambda
		usuarios.forEach(u -> System.out.println("lambda: " + u.getNome()));
	
		System.out.println("");
		System.out.println("Tornando todos moderadores");
		
		usuarios.forEach(u -> u.tornaModerador());
		
		
	}

}
