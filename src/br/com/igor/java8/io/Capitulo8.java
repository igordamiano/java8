package br.com.igor.java8.io;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Capitulo8 {

	public static void main(String[] args) throws Exception {

		
		System.out.println("-----------------------Todos arquivos no diretório--------------------------------");
		// Praticando o que aprendemos com java.nio.file.Files
		// Se quisermos listar todos os arquivos de um diretório, basta pegar o
		// Stream<Path> e depois um forEach
		
		Files.list(Paths.get("C:\\Curso\\Java_8 e 9\\workspace_java_8\\java8"))
			.forEach(System.out::println);
		
		
		System.out.println("----------------------Apenas os .TXT---------------------------------");
		// apenas os arquivos .txt
		Files.list(Paths.get("C:\\Curso\\Java_8 e 9\\workspace_java_8\\java8"))
		.filter(p -> p.toString().endsWith(".txt"))
		.forEach(System.out::println);

		// todo o conteúdo dos arquivos
		System.out.println("-----------------------Todo o conteúdo dos arquivos--------------------------------");
		Files.list(Paths.get("C:\\\\Curso\\\\Java_8 e 9\\\\workspace_java_8\\\\java8"))
		.filter(p -> p.toString().endsWith(".txt"))
		.map(p -> {
			try {
				return Files.lines(p);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		})
		.forEach(System.out::println);
		
		System.out.println("------------------------Todo o conteúdo dos arquivos-------------------------------");
		// Em vez de invocarmos map(p -> Files.lines(p)), invocamos o nosso
		// próprio lines, que não lança checked exception
		Files.list(Paths.get("C:\\Curso\\Java_8 e 9\\workspace_java_8\\java8"))
		.filter(p -> p.toString().endsWith(".txt")).map(p -> lines(p))
		.forEach(System.out::println);

		//O problema é que, com esse map, teremos um Stream<Stream<String>>, pois a invocação de lines(p) 
		//devolve um Stream<String> para cada Path do nosso Stream<Path> original!
		Stream<Stream<String>> strings = 
				Files.list(Paths.get("C:\\Curso\\Java_8 e 9\\workspace_java_8\\java8"))
				.filter(p -> p.toString().endsWith(".txt")).map(p -> lines(p));
				
		//FlatMap
		//Podemos achatar um Stream de Streams com o flatMap. Basta trocar a invocação,
		// que teremos no final um Stream<String>:
		System.out.println("----------------------Lendo as linhas dos arquivos---------------------------------");
		Stream<String> strings1 = 
				Files.list(Paths.get("C:\\Curso\\Java_8 e 9\\workspace_java_8\\java8"))
				.filter(p -> p.toString().endsWith(".txt"))
				.flatMap(p -> lines(p));
		strings1.forEach(System.out::println);
		
		//flatMaptoInt
		IntStream chars = 
				Files.list(Paths.get("C:\\Curso\\Java_8 e 9\\workspace_java_8\\java8"))
				.filter(p -> p.toString().endsWith(".txt"))
				.flatMap(p -> lines(p)).flatMapToInt((String s) -> s.chars());
		System.out.println("chars: " + chars);
	}
	
	static Stream<String> lines(Path p) {
		try {
			return Files.lines(p);
		} catch(IOException e) {
			throw new UncheckedIOException(e);
		}
	}

}
