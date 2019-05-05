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
		Usuario user4 = new Usuario("Jo�o da Silva", 290);
		Usuario user5 = new Usuario("Virgulino Lampi�o", 299);

		List<Usuario> usuarios = Arrays.asList(user1, user2, user3, user4, user5);
		
		//Ordenando um Stream
		usuarios.sort(Comparator.comparing(Usuario::getNome));
		
		// filtrar os usu�rios commais de 100 pontos e a� orden�-los:
		usuarios.stream().filter(u -> u.getPontos() > 100).sorted(Comparator.comparing(Usuario::getNome));
		
		// A diferen�a entre ordenar uma lista com sort e um stream com sorted voc� j� deve imaginar: 
		// um m�todo invocado em Stream n�o altera quem o gerou.
		
		// Se quisermos o resultado em uma List, precisamos usar um coletor
		List<Usuario> filtradosOrdenados = usuarios.stream().filter(u -> u.getPontos() > 100)
				.sorted(Comparator.comparing(Usuario::getNome)).collect(Collectors.toList());
		
		filtradosOrdenados.forEach(f -> System.out.println(f.getNome() + " " + f.getPontos()));
		System.out.println("----------------------------------------------");
		
		// Muitas opera��es no Stream s�o lazy
		// Esse conjunto de opera��es realizado em um Stream � conhecido como pipeline.
		// Evitando executar as opera��es o m�ximo poss�vel: grande parte
		// delas s�o lazy e executam realmente s� quando necess�rio para obter o resultado final.
		
		// * Qual � a vantagemdos m�todos serem lazy
		
		// Imagine que queremos encontrar um usu�rio com mais de 100 pontos. Basta um e
		// serve qualquer um, desde que cumpra o predicado de ter mais de 100 pontos.
		// Podemos filtrar o stream, colet�-lo em uma lista e pegar o primeiro elemento
		Usuario maisDe100 = usuarios.stream().filter(u -> u.getPontos() > 100).collect(Collectors.toList()).get(0);
		
		// O Stream possui om�todo findAny que devolve qualquer um dos elementos
		Optional<Usuario> usuarioOptional = usuarios.stream().filter(u -> u.getPontos() > 100)
				.findAny();
		
		// Enxergando a execu��odopipeline com peek
		usuarios.stream().filter(u -> u.getPontos() > 100)
			.peek(System.out::println).findAny();
		System.out.println("----------------------------------------------");
		
		// Com o peek, podemos ver se outras opera��es conseguem tirar vantagem do
		// lazyness. Experimente fazer o mesmo truque com o sorted
		usuarios.stream().sorted(Comparator.comparing(Usuario::getNome)).peek(System.out::println).findAny();

		// Opera��es de redu��o
		
		// Opera��es que utilizam os elementos da stream para retornar um valor final s�o
		// frequentemente chamadas de opera��es de redu��o (reduction).
		
		double pontuacaoMedia = usuarios.stream().mapToInt(Usuario::getPontos).average().getAsDouble();
		
		Optional<Usuario> max = usuarios.stream().max(Comparator.comparing(Usuario::getPontos));
		Usuario maximaPontuacao = max.get();
		
		System.out.println("maximaPontuacao: " + maximaPontuacao.getPontos());
		
		// somar todos os pontos dos usu�rios
		int total = usuarios.stream().mapToInt(Usuario::getPontos).sum();
		System.out.println("somar todos os pontos dos usu�rios: " + total);
		
		// opera��o de redu��o
		int valorInicial = 0;
		IntBinaryOperator operacao = (a,b) -> a + b;
		
		int total1 = usuarios.stream().mapToInt(Usuario::getPontos)
				.reduce(valorInicial, operacao);
		
		// Temos um c�digo equivalente ao sum. Poder�amos ter escrito tudo mais
		// sucintamente, sem a declara��o de vari�veis locais
		int total2 = usuarios.stream().mapToInt(Usuario::getPontos)
				.reduce(0, (a,b) -> a + b);
		
		// Na classe Integer, h� agora o m�todo est�tico Integer.sum, que soma dois inteiros. 
		// Em vez do lambda, podemos usar um method reference:
		int total3 = usuarios.stream().mapToInt(Usuario::getPontos).reduce(0, Integer::sum);
		
		// Multiplicar todos os pontos
		int mult = usuarios.stream().mapToInt(Usuario::getPontos).reduce(1, (a,b) -> a * b);
		
		// soma sem o map
		int total4 = usuarios.stream().reduce(0, (atual, u) -> atual + u.getPontos(), Integer::sum);
		System.out.println("-----------------------------------------------------");
		// Trabalhando com iterators
		// Por�m, podemos percorrer os elementos de um Stream atrav�s de um Iterator
		Iterator<Usuario> i = usuarios.stream().iterator();
		usuarios.stream().iterator().forEachRemaining(System.out::println);
		// Um motivo para usar um Iterator � quando queremos modificar os objetos de um Stream.
		
		// Testando Predicates
		
		// se quisermos saber se h� algum elemento daquela lista de usu�rios que � moderador:
		boolean hasModerador = usuarios.stream().anyMatch(Usuario::isModerador);
		
		/*
		// Streams infinitos
		// gerar uma lista �infinita� de n�meros aleat�rios
		Random random = new Random();
		Supplier<Integer> supplier = () -> random.nextInt();
		Stream<Integer> stream = Stream.generate(supplier);
		// O Stream gerado por generate � lazy. Certamente ele n�o vai gerar infinitos
		// n�meros aleat�rios. Eles s� ser�o gerados � medida que forem necess�rios.
		
		Random r = new Random();
		IntStream intStream = IntStream.generate(() -> r.nextInt());
		
		// Agora precisamos de cuidado. Qualquer opera��o que necessite passar por todos
		// os elementos do Stream nunca terminar� de executar. Por exemplo:
		int val = intStream.sum();

		// Opera��es de curto circuito
		// S�o opera��es que n�o precisam processar todos os elementos. Um exemplo
		// seria pegar apenas os 100 primeiros elementos com limit:
		
		Random random1 = new Random(0);
		IntStream stream1 = IntStream.generate(() -> random1.nextInt());
		List<Integer> list = stream1.limit(100).boxed().collect(Collectors.toList());
		
		// Vamos rever o mesmo c�digo com a interface fluente
		Random random2 = new Random(0);
		List<Integer> list2 = IntStream.generate(() -> random2.nextInt())
				.limit(100).boxed().collect(Collectors.toList());
		*/
		// Vamos gerar a sequ�ncia infinita de n�meros de Fibonacci de maneira
		// lazy e imprimir seus 10 primeiros elementos
		System.out.println("----Fibonacci de maneira lazy e imprimir seus 10 primeiros elementos----");
		IntStream.generate(new Fibonacci()).limit(10).forEach(System.out::println);
		
		// pegar o primeiro elemento maior que 100!
		int maisQue100 = IntStream.generate(new Fibonacci()).filter(f -> f > 100)
				.findFirst().getAsInt();
		
		System.out.println("pegar o primeiro elemento maior que 100: " + maisQue100);
		// O filter n�o � de curto-circuito: ele n�o produz um Stream finito dado um Stream infinito
		
		// Quando for necess�rio manter o estado de apenas uma vari�vel, podemos usar
		// o iterate em vez do generate, que recebe um UnaryOperator. Para gerar os n�meros naturais:
		
		IntStream.iterate(0, x -> x + 1).limit(10).forEach(System.out::println);
		
		System.out.println("-------------------------------------------------------");
		// Praticando o que aprendemos com java.nio.file.Files
		// Se quisermos listar todos os arquivos de um diret�rio, basta pegar o
		// Stream<Path> e depois um forEach
		
		Files.list(Paths.get("C:\\Curso\\Java_8 e 9\\workspace_java_8\\java8\\src\\br\\com\\igor\\java8"))
			.forEach(System.out::println);
		System.out.println("-------------------------------------------------------");
		// apenas os arquivos java
		
		Files.list(Paths.get("C:\\Curso\\Java_8 e 9\\workspace_java_8\\java8\\src\\br\\com\\igor\\java8"))
		.filter(p -> p.toString().endsWith(".java"))
		.forEach(System.out::println);

		// todo o conte�do dos arquivos
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
		// pr�prio lines, que n�o lan�a checked exception
		Files.list(Paths.get("C:\\Curso\\Java_8 e 9\\workspace_java_8\\java8\\src\\br\\com\\igor\\java8"))
		.filter(p -> p.toString().endsWith(".java")).map(p -> lines(p))
		.forEach(System.out::println);

		//O problema � que, com esse map, teremos um Stream<Stream<String>>, pois a invoca��o de lines(p) 
		//devolve um Stream<String> para cada Path do nosso Stream<Path> original!
		Stream<Stream<String>> strings = 
				Files.list(Paths.get("C:\\Curso\\Java_8 e 9\\workspace_java_8\\java8\\src\\br\\com\\igor\\java8"))
				.filter(p -> p.toString().endsWith(".java")).map(p -> lines(p));
				
		//FlatMap
		//Podemos achatar um Stream de Streams com o flatMap. Basta trocar a invoca��o,
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
		
		// Se temos esses grupos dentro de uma cole��o
		List<Grupo> grupos = Arrays.asList(ingles, espanhol);
		
		// queiramos todos os usu�rios desses grupos
		//Temos como resultado todos os usu�rios de ambos os grupos, sem repeti��o
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
