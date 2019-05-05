package br.com.igor.java8;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntBinaryOperator;
import java.util.function.Supplier;
import java.util.function.ToIntBiFunction;
import java.util.function.ToIntFunction;
import static java.util.Comparator.comparing;

public class Capitulo6 {

	public static void main(String[] args) {

		Usuario user0 = new Usuario("", 290);
		Usuario user1 = new Usuario("Paulo Silveira", 150);
		Usuario user2 = new Usuario("Rodrigo Turini", 120);
		Usuario user3 = new Usuario("Guilherme Silveira", 190);
		Usuario user4 = new Usuario("", 390);

		List<Usuario> usuarios = Arrays.asList(user1, user2, user3, user0, user4);

		usuarios.forEach(u -> u.tornaModerador());
		// Podemos fazer isso usando um novo recurso da linguagem, o method reference.
		usuarios.forEach(Usuario::tornaModerador);

		Consumer<Usuario> tornaModerador = Usuario::tornaModerador;
		usuarios.forEach(tornaModerador);

		// Usando Lambda
		usuarios.sort(Comparator.comparing(u -> u.getNome()));
		// Usando referencia ao metodo
		usuarios.sort(Comparator.comparing(Usuario::getNome));

		// estatico do metodo comparing
		Function<Usuario, String> byName = Usuario::getNome;
		usuarios.sort(comparing(byName));

		// compondo comparators
		// Utilizamos o comparingInt em vez do comparing para evitar o boxing
		// desnecess�rio.
		usuarios.sort(Comparator.comparingInt(Usuario::getPontos));

		// Ordenar pelos pontos e se empatar ordenar pelo nome
		usuarios.sort(Comparator.comparingInt(Usuario::getPontos).thenComparing(Usuario::getNome));
		System.out.println("---------Ordenar pelos pontos e se empatar ordenar pelo nome----------");
		usuarios.forEach(u -> System.out.println(u.getNome() + " - " + u.getPontos()));

		// Com isso, todos os usu�rios nulos da nossa lista estar�o posicionados no fim,
		// e
		// o restante ordenado pelo nome! H� tamb�m o m�todo est�tico nullsFirst.

		System.out.println("---------Nomes nulos no fim----------");
		usuarios.sort(Comparator.nullsLast(Comparator.comparing(Usuario::getNome)));
		usuarios.forEach(u -> System.out.println(u.getNome() + " - " + u.getPontos()));

		System.out.println("---------comparator.reversed() ordem decrescente----------");
		usuarios.sort(Comparator.comparing(Usuario::getPontos).reversed());
		usuarios.forEach(u -> System.out.println(u.getNome() + " - " + u.getPontos()));

		// Referenciando metodos de instancia
		Usuario igor = new Usuario("Igor Rodrigues Damian", 50);
		// os dois blocos a seguir s�o equivalentes
		Runnable bloco = igor::tornaModerador;
		Runnable bloco1 = () -> igor.tornaModerador();
		bloco.run();

		Usuario igor1 = new Usuario("Igor Rodrigues", 51);
		Consumer<Usuario> consumer = Usuario::tornaModerador;
		consumer.accept(igor1);

		// usando method reference
		Consumer<Usuario> consumer1 = Usuario::tornaModerador;

		// usando lambda
		Consumer<Usuario> consumer2 = u -> u.tornaModerador();

		// Referenciando m�todos que recebem argumentos
		System.out.println("------------Referenciando m�todos que recebem argumentos-------------");
		usuarios.forEach(System.out::println);

		// Referenciando construtores
		Function<String, Usuario> criadorDeUsuario = Usuario::new;
		Usuario ig = criadorDeUsuario.apply("Igor");
		Usuario le = criadorDeUsuario.apply("L�");

		// Referenciando construtor de dois par�metros
		BiFunction<String, Integer, Usuario> criadorDeUsuario1 = Usuario::new;
		Usuario ig1 = criadorDeUsuario1.apply("Igor", 89);
		Usuario le1 = criadorDeUsuario1.apply("L�", 99);

		// Evitar boxing
		BiFunction<Integer, Integer, Integer> max = Math::max;
		ToIntBiFunction<Integer, Integer> max2 = Math::max;
		IntBinaryOperator max3 = Math::max;

	}

}
