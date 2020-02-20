package br.com.igor.java8.io;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class Arquivo {
	
	public static void main(String[] args) throws IOException {
		
		System.out.println("----------------------Lendo as linhas dos arquivos---------------------------------");
		Stream<String> strings1 = 
				Files.list(Paths.get("C:\\Curso\\Java_8 e 9\\workspace_java_8\\java8"))
				.filter(p -> p.toString().endsWith(".abc"))
				.flatMap(p -> lines(p));
		strings1.forEach(System.out::println);
		
		System.out.println("--------------------------------");
		Files.lines(Paths.get("C:\\Curso\\Java_8 e 9\\workspace_java_8\\java8\\cidades.txt"))
			.forEach(System.out::println);

		//System.out.println("--------------------escrever arquivo --------------------");
		
		// escrever arquivo
		String arq = "C:\\Curso\\Java_8 e 9\\workspace_java_8\\java8\\escrever.txt";

		//Files.write(Paths.get(arq), (Iterable<String>)strings1::iterator);
		
		System.out.println("--------------Ordenando o arquivo --------------");
		
		System.out.println("----------------------Lendo as linhas dos arquivos---------------------------------");

		String arqNomes = "C:\\Curso\\Java_8 e 9\\workspace_java_8\\java8\\nome.abc";
		Files.lines(Paths.get(arqNomes))
		//.filter(l -> "igor".equals(l))
		.filter(l -> l.matches("igor|eliana"))
		.forEach(System.out::println);
		
		
		
		
		
		
		
		
	}
	
	static Stream<String> lines(Path p) {
		try {
			return Files.lines(p);
		} catch(IOException e) {
			throw new UncheckedIOException(e);
		}
	}
	
}

  