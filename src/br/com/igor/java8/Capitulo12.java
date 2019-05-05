package br.com.igor.java8;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Capitulo12 {

	public static void main(String[] args) {

		Usuario u1 = new Usuario("Paulo Silveira", 150);
		Usuario u2 = new Usuario("Rodrigo Turini", 120);
		Usuario u3 = new Usuario("Guilherme Silveira", 190);

		List<Usuario> usuarios = Arrays.asList(u1, u2, u3);

		// diamond operator
		// antes do java 8
		List<Usuario> l1 = new ArrayList<Usuario>();
		
		// a partir do java 8
		List<Usuario> l2 = new ArrayList<>();
		
		// Método falha:
		// repositorio.adiciona(new ArrayList<>());
		
		// para compilar no java7
		// repositorio.adiciona(new ArrayList<Usuario>());
		
		//repositorio.adiciona(Collections.emptyList());
		// Porém o seguinte erro será exibido ao tentar compilar
		// is not applicable for the arguments (List<Object>)
		
		
		
		
		
		
		
		
		
		
		
	}

}
