package br.com.igor.java8;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.IntBinaryOperator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Capitulo8 {

	public static void main(String[] args) throws Exception {

		Usuario user1 = new Usuario("Paulo Silveira", 150);
		Usuario user2 = new Usuario("Rodrigo Turini", 90);
		Usuario user3 = new Usuario("Guilherme Silveira", 190);
		Usuario user4 = new Usuario("João da Silva", 290);
		Usuario user5 = new Usuario("Virgulino Lampião", 299);

		List<Usuario> usuarios = Arrays.asList(user1, user2, user3, user4, user5);
		
		//Ordenando um Stream
		usuarios.sort(Comparator.comparing(Usuario::getNome));
		
		// filtrar os usuários commais de 100 pontos e aí ordená-los:
		usuarios.stream().filter(u -> u.getPontos() > 100).sorted(Comparator.comparing(Usuario::getNome));
		
		// A diferença entre ordenar uma lista com sort e um stream com sorted você já deve imaginar: 
		// um método invocado em Stream não altera quem o gerou.
		
		// Se quisermos o resultado em uma List, precisamos usar um coletor
		List<Usuario> filtradosOrdenados = usuarios.stream().filter(u -> u.getPontos() > 100)
				.sorted(Comparator.comparing(Usuario::getNome)).collect(Collectors.toList());
		
		filtradosOrdenados.forEach(f -> System.out.println(f.getNome() + " " + f.getPontos()));
		System.out.println("----------------------------------------------");
		
		// Muitas operações no Stream são lazy
		// Esse conjunto de operações realizado em um Stream é conhecido como pipeline.
		// Evitando executar as operações o máximo possível: grande parte
		// delas são lazy e executam realmente só quando necessário para obter o resultado final.
		
		// * Qual é a vantagemdos métodos serem lazy
		
		// Imagine que queremos encontrar um usuário com mais de 100 pontos. Basta um e
		// serve qualquer um, desde que cumpra o predicado de ter mais de 100 pontos.
		// Podemos filtrar o stream, coletá-lo em uma lista e pegar o primeiro elemento
		Usuario maisDe100 = usuarios.stream().filter(u -> u.getPontos() > 100).collect(Collectors.toList()).get(0);
		
		// O Stream possui ométodo findAny que devolve qualquer um dos elementos
		Optional<Usuario> usuarioOptional = usuarios.stream().filter(u -> u.getPontos() > 100)
				.findAny();
		
		// Enxergando a execuçãodopipeline com peek
		usuarios.stream().filter(u -> u.getPontos() > 100)
			.peek(System.out::println).findAny();
		System.out.println("----------------------------------------------");
		
		// Com o peek, podemos ver se outras operações conseguem tirar vantagem do
		// lazyness. Experimente fazer o mesmo truque com o sorted
		usuarios.stream().sorted(Comparator.comparing(Usuario::getNome)).peek(System.out::println).findAny();

		// Operações de redução
		
		// Operações que utilizam os elementos da stream para retornar um valor final são
		// frequentemente chamadas de operações de redução (reduction).
		
		double pontuacaoMedia = usuarios.stream().mapToInt(Usuario::getPontos).average().getAsDouble();
		
		Optional<Usuario> max = usuarios.stream().max(Comparator.comparing(Usuario::getPontos));
		Usuario maximaPontuacao = max.get();
		
		System.out.println("maximaPontuacao: " + maximaPontuacao.getPontos());
		
		// somar todos os pontos dos usuários
		int total = usuarios.stream().mapToInt(Usuario::getPontos).sum();
		System.out.println("somar todos os pontos dos usuários: " + total);
		
		// operação de redução
		int valorInicial = 0;
		IntBinaryOperator operacao = (a,b) -> a + b;
		
		int total1 = usuarios.stream().mapToInt(Usuario::getPontos)
				.reduce(valorInicial, operacao);
		
		// Temos um código equivalente ao sum. Poderíamos ter escrito tudo mais
		// sucintamente, sem a declaração de variáveis locais
		int total2 = usuarios.stream().mapToInt(Usuario::getPontos)
				.reduce(0, (a,b) -> a + b);
		
		// Na classe Integer, há agora o método estático Integer.sum, que soma dois inteiros. 
		// Em vez do lambda, podemos usar um method reference:
		int total3 = usuarios.stream().mapToInt(Usuario::getPontos).reduce(0, Integer::sum);
		
		// Multiplicar todos os pontos
		int mult = usuarios.stream().mapToInt(Usuario::getPontos).reduce(1, (a,b) -> a * b);
		
		// soma sem o map
		int total4 = usuarios.stream().reduce(0, (atual, u) -> atual + u.getPontos(), Integer::sum);
		System.out.println("-----------------------------------------------------");
		// Trabalhando com iterators
		// Porém, podemos percorrer os elementos de um Stream através de um Iterator
		Iterator<Usuario> i = usuarios.stream().iterator();
		usuarios.stream().iterator().forEachRemaining(System.out::println);
		// Um motivo para usar um Iterator é quando queremos modificar os objetos de um Stream.
		
		// Testando Predicates
		
		// se quisermos saber se há algum elemento daquela lista de usuários que é moderador:
		boolean hasModerador = usuarios.stream().anyMatch(Usuario::isModerador);
		
		/*
		// Streams infinitos
		// gerar uma lista “infinita” de números aleatórios
		Random random = new Random();
		Supplier<Integer> supplier = () -> random.nextInt();
		Stream<Integer> stream = Stream.generate(supplier);
		// O Stream gerado por generate é lazy. Certamente ele não vai gerar infinitos
		// números aleatórios. Eles só serão gerados à medida que forem necessários.
		
		Random r = new Random();
		IntStream intStream = IntStream.generate(() -> r.nextInt());
		
		// Agora precisamos de cuidado. Qualquer operação que necessite passar por todos
		// os elementos do Stream nunca terminará de executar. Por exemplo:
		int val = intStream.sum();

		// Operações de curto circuito
		// São operações que não precisam processar todos os elementos. Um exemplo
		// seria pegar apenas os 100 primeiros elementos com limit:
		
		Random random1 = new Random(0);
		IntStream stream1 = IntStream.generate(() -> random1.nextInt());
		List<Integer> list = stream1.limit(100).boxed().collect(Collectors.toList());
		
		// Vamos rever o mesmo código com a interface fluente
		Random random2 = new Random(0);
		List<Integer> list2 = IntStream.generate(() -> random2.nextInt())
				.limit(100).boxed().collect(Collectors.toList());
		*/
		// Vamos gerar a sequência infinita de números de Fibonacci de maneira
		// lazy e imprimir seus 10 primeiros elementos
		System.out.println("----Fibonacci de maneira lazy e imprimir seus 10 primeiros elementos----");
		IntStream.generate(new Fibonacci()).limit(10).forEach(System.out::println);
		
		// pegar o primeiro elemento maior que 100!
		int maisQue100 = IntStream.generate(new Fibonacci()).filter(f -> f > 100)
				.findFirst().getAsInt();
		
		System.out.println("pegar o primeiro elemento maior que 100: " + maisQue100);
		// O filter não é de curto-circuito: ele não produz um Stream finito dado um Stream infinito
		
		// Quando for necessário manter o estado de apenas uma variável, podemos usar
		// o iterate em vez do generate, que recebe um UnaryOperator. Para gerar os números naturais:
		
		IntStream.iterate(0, x -> x + 1).limit(10).forEach(System.out::println);
		
		System.out.println("-------------------------------------------------------");
		// Praticando o que aprendemos com java.nio.file.Files
		// Se quisermos listar todos os arquivos de um diretório, basta pegar o
		// Stream<Path> e depois um forEach
		
		Files.list(Paths.get("C:\\Curso\\Java_8 e 9\\workspace_java_8\\java8\\src\\br\\com\\igor\\java8"))
			.forEach(System.out::println);
		System.out.println("-------------------------------------------------------");
		// apenas os arquivos java
		
		Files.list(Paths.get("C:\\Curso\\Java_8 e 9\\workspace_java_8\\java8\\src\\br\\com\\igor\\java8"))
		.filter(p -> p.toString().endsWith(".java"))
		.forEach(System.out::println);

		// todo o conteúdo dos arquivos
		System.out.println("-------------------------------------------------------");
		Files.list(Paths.get("C:\\Curso\\Java_8 e 9\\workspace_java_8\\java8\\src\\br\\com\\igor\\java8"))
		.filter(p -> p.toString().endsWith(".java"))
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
		System.out.println("-------------------------------------------------------");
		
		// Em vez de invocarmos map(p -> Files.lines(p)), invocamos o nosso
		// próprio lines, que não lança checked exception
		Files.list(Paths.get("C:\\Curso\\Java_8 e 9\\workspace_java_8\\java8\\src\\br\\com\\igor\\java8"))
		.filter(p -> p.toString().endsWith(".java")).map(p -> lines(p))
		.forEach(System.out::println);

		//O problema é que, com esse map, teremos um Stream<Stream<String>>, pois a invocação de lines(p) 
		//devolve um Stream<String> para cada Path do nosso Stream<Path> original!
		Stream<Stream<String>> strings = 
				Files.list(Paths.get("C:\\Curso\\Java_8 e 9\\workspace_java_8\\java8\\src\\br\\com\\igor\\java8"))
				.filter(p -> p.toString().endsWith(".java")).map(p -> lines(p));
				
		//FlatMap
		//Podemos achatar um Stream de Streams com o flatMap. Basta trocar a invocação,
		// que teremos no final um Stream<String>:
		System.out.println("-------------------------------------------------------");
		Stream<String> strings1 = 
				Files.list(Paths.get("C:\\Curso\\Java_8 e 9\\workspace_java_8\\java8\\src\\br\\com\\igor\\java8"))
				.filter(p -> p.toString().endsWith(".java"))
				.flatMap(p -> lines(p));

		//flatMaptoInt
		IntStream chars = 
				Files.list(Paths.get("C:\\Curso\\Java_8 e 9\\workspace_java_8\\java8\\src\\br\\com\\igor\\java8"))
				.filter(p -> p.toString().endsWith(".java"))
				.flatMap(p -> lines(p)).flatMapToInt((String s) -> s.chars());
		
		Grupo ingles = new Grupo();
		ingles.add(user1);
		ingles.add(user2);
		
		Grupo espanhol = new Grupo();
		espanhol.add(user2);
		espanhol.add(user3);
		
		// Se temos esses grupos dentro de uma coleção
		List<Grupo> grupos = Arrays.asList(ingles, espanhol);
		
		// queiramos todos os usuários desses grupos
		//Temos como resultado todos os usuários de ambos os grupos, sem repetição
		grupos.stream().flatMap(g -> g.getUsuarios().stream())
		.distinct().forEach(System.out::println);
	}
	
	static Stream<String> lines(Path p) {
		try {
			return Files.lines(p);
		} catch(IOException e) {
			throw new UncheckedIOException(e);
		}
	}

}
