package br.com.igor.java8;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Capitulo7 {

	public static void main(String[] args) {

		Usuario user1 = new Usuario("Paulo Silveira", 150);
		Usuario user2 = new Usuario("Rodrigo Turini", 90);
		Usuario user3 = new Usuario("Guilherme Silveira", 190);
		Usuario user4 = new Usuario("Jo�o da Silva", 290);
		Usuario user5 = new Usuario("Virgulino Lampi�o", 299);

		List<Usuario> usuarios = Arrays.asList(user1, user2, user3, user4, user5);
		
		// Tornando moderadores os 3 usu�rios com mais pontos
		usuarios.sort(Comparator.comparingInt(Usuario::getPontos).reversed());
		usuarios.forEach(u -> System.out.println(u.getPontos()));
		usuarios.subList(0, 3).forEach(Usuario::tornaModerador);
		
		// antes do Java 8
		Collections.sort(usuarios, new Comparator<Usuario>() {
			@Override
			public int compare(Usuario u1, Usuario u2) {
				return u1.getPontos() - u2.getPontos();
			}
		});
		Collections.reverse(usuarios);
		List<Usuario> top3 = usuarios.subList(0, 3);
		for (Usuario usuario : top3) {
			usuario.tornaModerador();
		}
		// fim antes do Java 8
		System.out.println("----------------------------------");
		// Stream - Filtrar os usu�rios com mais de 100 pontos
		Stream<Usuario> stream = usuarios.stream().filter(u -> u.getPontos() > 100);
		stream.forEach(System.out::println);
		System.out.println("----------------------------------");
		// simplificando
		Stream<Usuario> stream1 = usuarios.stream().filter(u -> u.getPontos() > 100); 
		stream1.forEach(u -> System.out.println(u.getNome() + " " + u.getPontos()));
		System.out.println("----------------------------------");
		
		usuarios.stream().filter(u -> u.getPontos() > 100).forEach(System.out::println);
		
		// Voltemos � quest�o original. Para filtrar esses usu�rios e torn�-losmoderadores,
		// fazemos assim:
		usuarios.stream().filter(u -> u.getPontos() > 100).forEach(Usuario::tornaModerador);
		
		// Para filtrar os usu�rios que s�o moderadores
		usuarios.stream().filter(u -> u.isModerador());
		// com method reference
		usuarios.stream().filter(Usuario::isModerador);
		
		// Como obter de volta uma Lista
		List<Usuario> maisQue100 = new ArrayList<>();
		usuarios.stream().filter(u -> u.getPontos() > 100).forEach(u -> maisQue100.add(u));
		System.out.println("--------Como obter de volta uma Lista----------");
		maisQue100.forEach(m -> System.out.println(m.getNome()));
		
		// Simplificar usando method reference
		usuarios.stream().filter(u -> u.getPontos() > 100).forEach(maisQue100::add);
		
		// Collectors
		usuarios.stream().filter(u -> u.getPontos() > 100).collect(Collectors.toList());
		
		//Fazendo um import est�tico podemos deixar o c�digo um pouco mais enxuto,
		//em um �nico statement
		usuarios.stream().filter(u -> u.getPontos() > 100).collect(toList());
		
		// Pronto! Agora conseguimos coletar o resultado das transforma��es no nosso
		// Stream<Usuario> em um List<Usuario>:
		List<Usuario> maisQue100_2 = usuarios.stream().filter(u -> u.getPontos() > 100)
				.collect(toList());
		
		//Ta mb� m podemos utilizar o m�todo toSet para coletar as informa��es desse
		//Stream em um Set<Usuario>:
		Set<Usuario> maisQue100Set = usuarios.stream().filter(u -> u.getPontos() > 100)
				.collect(Collectors.toSet());
		
		//H� ainda om�todo toCollection, que permite que voc� escolha a implementa��o
		//que ser� devolvida no final da coleta
//		Set<Usuario> set = stream.collect(Collectors.toCollection(HashSet::new));
		
		System.out.println("---------------------------------------------------");
		//Liste apenas os pontos de todos os usu�rios com o map
		List<Integer> pontos = usuarios.stream().map(u -> u.getPontos())
				.collect(Collectors.toList());
		
		pontos.forEach(System.out::println);
		
		//E utilizando o map, podemos tirar proveito da sintaxe do method reference, 
		// simplificando ainda mais nossa opera��o 
		List<Integer> pts = usuarios.stream().map(Usuario::getPontos).collect(toList());
		
		// IntStream e a fam�lia de Streams
		// Repare que, quando escrevemos o seguinte c�digo, temos como retorno um
		// Stream<Integer>:
		
		Stream<Integer> str = usuarios.stream().map(Usuario::getPontos);
		// Isso gera o boxing dos nossos inteiros. Se formos operar sobre eles, teremos
		//um overhead indesejado, que pode ser fatal para listas grandes ou caso haja muita
		//repeti��o dessa instru��o.
		// * O pacote java.util.stream possui implementa��es equivalentes ao
		//Stream para os principais tipos primitivos: IntStream, LongStream,e DoubleStream.
		
		//Podemos usar o IntStream aqui para evitar o autoboxing! Basta utilizarmos o
		// m�todo mapToInt:
		IntStream str1 = usuarios.stream().mapToInt(Usuario::getPontos);
		
		//obter a m�dia de pontos dos usu�rios
		double pontuacaoMedia = usuarios.stream()
				.mapToInt(Usuario::getPontos).average().getAsDouble();
		System.out.println("m�dia de pontos dos usu�rios: " + pontuacaoMedia);
		
		// O Optional em java.util
		OptionalDouble media = usuarios.stream().mapToInt(Usuario::getPontos).average();
		double ptMedia = media.orElse(0.0);
		System.out.println("Optional, m�dia de pontos dos usu�rios: " + ptMedia);
		// Optional em uma linha
		double ptsMedia = usuarios.stream().mapToInt(Usuario::getPontos)
				.average().orElse(0.0);
		System.out.println("Optional em uma linha, m�dia de pontos dos usu�rios: " + ptsMedia);
		
		// lan�ar uma exception
		double ptsException = usuarios.stream().mapToInt(Usuario::getPontos)
				.average().orElseThrow(IllegalStateException::new);
		
		// usu�rio com maior quantidade de pontos
		Optional<Usuario> max = usuarios.stream().max(Comparator.comparingInt(Usuario::getPontos));
		System.out.println("Max pontos: " + max.get().getNome());
		
		// trabalhando com Optional de maneira lazy
		Optional<String> maxNome = usuarios.stream().max(Comparator.comparingInt(Usuario::getPontos))
				.map(u -> u.getNome());
		
		System.out.println("trabalhando com Optional de maneira lazy: " + maxNome);
		
	}

}
