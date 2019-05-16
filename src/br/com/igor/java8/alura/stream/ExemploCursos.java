package br.com.igor.java8.alura.stream;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class ExemploCursos {

	public static void main(String[] args) {

		List<Curso> cursos = new ArrayList<Curso>();
		cursos.add(new Curso("Python", 45));
		cursos.add(new Curso("JavaScript", 150));
		cursos.add(new Curso("Java 8", 113));
		cursos.add(new Curso("C", 55));
		
		// ordenando qtde de alunos do menor para o maior
		cursos.sort(Comparator.comparingInt(Curso::getQtdeAlunos));
		cursos.forEach(c -> System.out.println(c.getNome()));
		
		System.out.println("--------------------------------------");
		
		// filtrar os cursos com mais de 100 alunos
		// Strem() é imutável, não altera sua lista original
		cursos.stream()
			.filter(c -> c.getQtdeAlunos() >= 100)
			.forEach(c -> System.out.println(c.getNome()));
		
		System.out.println("--------------------------------------");
		// Traz o numero de alunos uando map()
		cursos.stream()
			.filter(c -> c.getQtdeAlunos() >= 100)
			.mapToInt(Curso::getQtdeAlunos) // map() devolve um Integer
			.forEach(System.out::println);
		
		System.out.println("--------------------------------------");
		// soma
		int sum = cursos.stream()
			.filter(c -> c.getQtdeAlunos() >= 100)
			.mapToInt(Curso::getQtdeAlunos)
			.sum();
		System.out.println(sum);
		
		System.out.println("-----------------Optional---------------------");
		// Optional, trabalha com null
		cursos.stream()
			.filter(c -> c.getQtdeAlunos() >= 100)
			.findAny() // pega qualquer um
			.ifPresent(c -> System.out.println(c.getNome()));
		
		System.out.println("-----------------Quero uma Lista---------------------");
		// Retornar uma lista/set/map
		// List
		List<Curso> result = cursos.stream()
		.filter(c -> c.getQtdeAlunos() >= 100)
		.collect(Collectors.toList());
		result.forEach(r -> System.out.println(r.getNome()));
		
		// Map
		System.out.println("-----------------Quero um Map---------------------");
		cursos.stream()
			.filter(c -> c.getQtdeAlunos() >= 100)
			.collect(Collectors.toMap( // chave , valor
					c -> c.getNome(), // chave
					c -> c.getQtdeAlunos())) // valor
			.forEach((nome, qtde) -> System.out.println("Curso " + nome + " tem " + qtde + " alunos"));
		
		// Stream paralelo
		// Rodou em paralelo, em threads, de acordo com qtde de processadores
		System.out.println("-----------------Stream Paralelo---------------------");
			cursos.parallelStream()
			.filter(c -> c.getQtdeAlunos() >= 100)
			.collect(Collectors.toMap( // chave , valor
					c -> c.getNome(), // chave
					c -> c.getQtdeAlunos())) // valor
			.forEach((nome, qtde) -> System.out.println("Curso " + nome + " tem " + qtde + " alunos"));

		
		
	}

}
