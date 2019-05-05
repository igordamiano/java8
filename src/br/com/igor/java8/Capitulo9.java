package br.com.igor.java8;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class Capitulo9 {

	public static void main(String[] args) throws Exception {
		
		final String DIR = "C:\\Curso\\Java_8 e 9\\workspace_java_8\\java8\\src\\br\\com\\igor\\java8";

		Usuario user1 = new Usuario("Paulo Silveira", 150, true);
		Usuario user2 = new Usuario("Rodrigo Turini", 120, true);
		Usuario user3 = new Usuario("Guilherme Silveira", 90);
		Usuario user4 = new Usuario("Sergio Lopes", 120);
		Usuario user5 = new Usuario("Adriano Almeida", 100);
		List<Usuario> usuarios = Arrays.asList(user1, user2, user3, user4, user5);
		
		// Mapeando, particionando, agrupando e paralelizando
		
		// Coletores gerando mapas
		// gerar um Stream com todas as linhas dos arquivos de determinado diretório
		
		Stream<String> strings = Files.list(Paths.get(DIR))
				.filter(p -> p.toString().endsWith(".java")).flatMap(p -> lines(p));
		
		// quantidade de linhas
		LongStream linhas = Files.list(Paths.get(DIR))
				.filter(p -> p.toString().endsWith(".java"))
				.mapToLong(p -> lines(p).count());
		
		// List<Long> com os valores desse LongStream, fazemos um collect
		
		 List<Long> linhas1 = Files.list(Paths.get(DIR))
				.filter(p -> p.toString().endsWith(".java"))
				.map(p -> lines(p).count())
				.collect(Collectors.toList());

		 Map<Path, Long> linhasPorArquivo = new HashMap<>();
		Files.list(Paths.get(DIR))
			.filter(p -> p.toString().endsWith(".java"))
			.forEach(a -> linhasPorArquivo.put(a, lines(a).count()));

		System.out.println(linhasPorArquivo);
		System.out.println("------------------------------------------");
		
		Map<Path, Long> linhas2 = Files.list(Paths.get(DIR))
				.filter(p -> p.toString().endsWith(".java"))
				.collect(Collectors.toMap
						(p -> p, 
						 p -> lines(p).count()));
		System.out.println(linhas2);
		
		// gerar um mapa de cada arquivo para toda a lista de linhas contidas nos arquivos,
		Map<Path, List<String>> content =
				Files.list(Paths.get(DIR))
				.filter(p -> p.toString().endsWith(".java"))
				.collect(Collectors.toMap(
						Function.identity(),
						p -> lines(p).collect(Collectors.toList())));
		
		// Mapear todos os usuários utilizando seu nome como chave
		Map<String, Usuario> nomeUsuario = 
				usuarios.stream().collect(Collectors.toMap
						(Usuario::getNome, 
						 Function.identity()));
		
		// groupingBy e partitioningBy
		// mapa em que a chave seja a pontuação do usuário e o valor seja uma lista de usuários
		
		Map<Integer, List<Usuario>> pontuacao = new HashMap<>();
		for (Usuario u : usuarios) {
			pontuacao.computeIfAbsent(u.getPontos(), u1 -> new ArrayList<>()).add(u);
		}
		System.out.println("------------------------------------------");
		System.out.println(pontuacao);
		
		// Collectors.groupingBy
		Map<Integer, List<Usuario>> pontuacao1 = usuarios.stream()
				.collect(Collectors.groupingBy(Usuario::getPontos));
		System.out.println("------------------groupingBy------------------------");
		System.out.println(pontuacao1);
		
		// particionar todos os usuários entre moderadores e não moderadores, usando o partitionBy
		// O partitioningBy nada mais é do que uma versão mais eficiente para ser usada ao agrupar booleans
		Map<Boolean, List<Usuario>> moderadores = usuarios.stream()
				.collect(Collectors.partitioningBy(Usuario::isModerador));
		System.out.println("------------------partitionBy------------------------");
		System.out.println(moderadores);
		
		// guardar uma lista com apenas o nome de cada usuário, usando o mapping
		Map<Boolean, List<String>> nomes = usuarios.stream()
				.collect(Collectors.partitioningBy(Usuario::isModerador,
						Collectors.mapping(Usuario::getNome, Collectors.toList())));
		System.out.println("------------------mapping------------------------");
		System.out.println(nomes);
				
		// particionar por moderação, mas ter como valor não os usuários, mas sim a soma de seus pontos
		Map<Boolean, Integer> pontos = usuarios.stream()
				.collect(Collectors.partitioningBy(Usuario::isModerador,
						Collectors.summingInt(Usuario::getPontos)));
		System.out.println("------------------summingInt------------------------");
		System.out.println(pontos);
		
		// para concatenar todos os nomes dos usuários há um coletor
		String nomes1 = usuarios.stream()
				.map(Usuario::getNome)
				.collect(Collectors.joining(", "));
		System.out.println("------------------joining------------------------");
		System.out.println(nomes1);
		
		System.out.println("-------------------------------------------------");
		
		// Executando o pipeline em paralelo
		// Acontece na mesma Thread
		List<Usuario> filtradosOrdenados = usuarios.stream()
				.filter(u -> u.getPontos() > 100)
				.sorted(Comparator.comparing(Usuario::getNome))
				.collect(Collectors.toList());
		
		// parallelStream em vez de Stream
		List<Usuario> filtradosOrdenados1 = usuarios.parallelStream()
				.filter(u -> u.getPontos() > 100)
				.sorted(Comparator.comparing(Usuario::getNome))
				.collect(Collectors.toList());
		
		long sum = LongStream.range(0, 1_000_000_00)
				.parallel()
				.filter(x -> x % 2 == 0)
				.sum();
		System.out.println(sum);
		
		
		
	}
	static Stream<String> lines(Path p) {
		try {
			return Files.lines(p, Charset.forName("Cp1252"));
		} catch(IOException e) {
			throw new UncheckedIOException(e);
		}
	}
	
	 
	
	
	
	


}
