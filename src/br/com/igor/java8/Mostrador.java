package br.com.igor.java8;

import java.util.function.Consumer;

public class Mostrador implements Consumer<Usuario> {

	@Override
	public void accept(Usuario u) {

		System.out.println(u.getNome());

	}

}
