package br.com.igor.java8.loja;

import java.math.BigDecimal;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Teste {

	public static void main(String[] args) {

		Customer paulo = new Customer("Paulo Silveira");
		Customer rodrigo = new Customer("Rodrigo Silva");
		Customer guilherme = new Customer("Guilherme Dias");
		Customer adriano = new Customer("Adriano Mota");

		Product bach = new Product("Bach Completo", Paths.get("/music/bach.mp3"), new BigDecimal(100));
		Product poderosas = new Product("Poderosas Anita", Paths.get("/music/poderosas.mp3"), new BigDecimal(90));
		Product bandeira = new Product("Bandeira Brasil", Paths.get("/images/brasil.jpg"), new BigDecimal(50));
		Product beauty = new Product("Beleza Americana", Paths.get("beauty.mov"), new BigDecimal(150));
		Product vingadores = new Product("Os Vingadores", Paths.get("/movies/vingadores.mov"), new BigDecimal(200));
		Product amelie = new Product("Amelie Poulain", Paths.get("/movies/amelie.mov"), new BigDecimal(100));

		LocalDateTime today = LocalDateTime.now();
		LocalDateTime yesterday = LocalDateTime.now().minusDays(1);
		LocalDateTime lastMonth = LocalDateTime.now().minusMonths(1);

		Payment payment1 = new Payment(Arrays.asList(bach, poderosas), today, paulo);
		Payment payment2 = new Payment(Arrays.asList(bach, bandeira, amelie), yesterday, rodrigo);
		Payment payment3 = new Payment(Arrays.asList(beauty, vingadores, bach), today, adriano);
		Payment payment4 = new Payment(Arrays.asList(bach, poderosas, amelie), lastMonth, guilherme);
		Payment payment5 = new Payment(Arrays.asList(beauty, amelie), yesterday, paulo);

		List<Payment> payments = Arrays.asList(payment1, payment2, payment3, payment4, payment5);
		
		// Ordenando nossos pagamentos
		payments.stream()
			.sorted(Comparator.comparing(Payment::getDate))
			.forEach(System.out::println);
		
		// Reduzindo BigDecimal em somas
		//Vamos calcular o valor total do pagamento payment1 utilizando a API de Stream e lambdas. H� um problema. 
		//Se pre�o fosse um int, poder�amos usar o mapToDouble e invocar o sum do DoubleStream resultante. 
		//N�o � o caso. Teremos um Stream<BigDecimal> e ele n�o possui um sum.
		
		//Nesse caso precisaremos fazer a redu��o na m�o, realizando a soma de BigDecimal. Podemos usar 
		// o (total, price) -> total.add(price), mas fica ainda mais f�cil usando um method reference:
		System.out.println("valor total do pagamento payment1");
		payment1.getProducts().stream()
			.map(Product::getPrice)
			.reduce(BigDecimal::add)
			.ifPresent(System.out::println);
		
		BigDecimal total =
				payment1.getProducts().stream()
				.map(Product::getPrice)
				.reduce(BigDecimal.ZERO, BigDecimal::add);
		System.out.println(total);
		
		// todos os pagamentos da lista payments
		Stream<BigDecimal> priceStream =
				payments.stream()
				.map(p -> p.getProducts().stream()
						.map(Product::getPrice)
						.reduce(BigDecimal.ZERO, BigDecimal::add));
		System.out.println("todos os pagamentos da lista payments");
		priceStream.forEach(System.out::println);
		
		// vamos somar cada um desses subtotais
		BigDecimal tot =
				payments.stream()
				.map(p -> p.getProducts().stream()
						.map(Product::getPrice)
						.reduce(BigDecimal.ZERO, BigDecimal::add))
				.reduce(BigDecimal.ZERO, BigDecimal::add);
		System.out.println("Total " + tot);
		
		// O c�digo est�umpouco repetitivo
		// criar um �nico Stream<BigDecimal> com os valores de todos os produtos de todos pagamentos. 
		// Conseguimos esse Stream usando o flatMap:
		Stream<BigDecimal> priceOfEachProduct = 
				payments.stream()
				.flatMap(p -> p.getProducts().stream().map(Product::getPrice));
		System.out.println("---------------priceOfEachProduct----------------");
		priceOfEachProduct.forEach(System.out::println);
		
		//Se est� dif�cil ler este c�digo, leia-o passo a passo. O importante � enxergar essa fun��o:
		Function<Payment, Stream<BigDecimal>> mapper = 
				p -> p.getProducts().stream().map(Product::getPrice);
		
		System.out.println("----------------totalFlat--------------");		
		BigDecimal totalFlat =
				payments.stream()
				.flatMap(p -> p.getProducts().stream().map(Product::getPrice))
				.reduce(BigDecimal.ZERO, BigDecimal::add);
		System.out.println(totalFlat);
		
		// Produtos mais vendidos
		// Queremos saber nossos campe�es de vendas
		
		Stream<Product> produtos =
				payments.stream()
				.map(Payment::getProducts)
				.flatMap(p -> p.stream());
		System.out.println("Todos os produtos");
		produtos.forEach(System.out::println);
		
		// Em vez de p -> p.stream(), h� a possibilidade de passar o lambda como
		// method reference: List::stream
		Stream<Product> prod = payments.stream()
				.map(Payment::getProducts)
				.flatMap(List::stream);
		
		// Sempre podemos juntar dois maps (independente de um deles ser flat) em um �nico map:
		Stream<Product> prods = payments.stream().flatMap(p -> p.getProducts().stream());
		
		// Precisamos gerar um Map de Product para Long. Esse Long indica quantas
		// vezes o produto foi vendido. Usaremos o groupingBy, agrupando todos esses
		// produtos pelo pr�prio produto, mapeando-o pela sua contagem:
		
		Map<Product, Long> topProdutos = payments.stream()
				.flatMap(p -> p.getProducts().stream())
				.collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
				
		System.out.println("----------topProdutos--------------");
		System.out.println(topProdutos);
		topProdutos.entrySet().stream().forEach(System.out::println);
		
		System.out.println("----------topProdutos mais vendido--------------");
		topProdutos.entrySet().stream()
			.max(Comparator.comparing(Map.Entry::getValue))
			.ifPresent(System.out::println);
		
		// Valores gerados por produto
		// Para realizar a soma em BigDecimal teremos de deixar o reduce expl�cito:
		
		Map<Product, BigDecimal> totalValuePerProduct = payments.stream()
				.flatMap(p -> p.getProducts().stream())
				.collect(Collectors.groupingBy(Function.identity(),
						Collectors.reducing(BigDecimal.ZERO, Product::getPrice, BigDecimal::add)));
		System.out.println("-----------------------------");
		//Podemos usar amesma estrat�gia do stream().forEach(System.out::println)
		//para mostrar o resultado, mas vamos aproveitar e ordenar a sa�da por valor:
		totalValuePerProduct.entrySet().stream()
			.sorted(Comparator.comparing(Map.Entry::getValue))
			.forEach(System.out::println);
		
		//Quais s�o os produtos de cada cliente
		// bastando agrupar os payments
		System.out.println("--------------------------------------------");
		Map<Customer, List<Payment>> customerToPayments =
				payments.stream()
				.collect(Collectors.groupingBy(Payment::getCustomer));
		
		Map<Customer, List<List<Product>>> customerToProductList =
				payments.stream()
				.collect(Collectors.groupingBy(Payment::getCustomer,
						Collectors.mapping(Payment::getProducts, Collectors.toList())));
		
		customerToProductList.entrySet().stream()
			.sorted(Comparator.comparing(e -> e.getKey().getName()))
			.forEach(System.out::println);
		
		// Queremos esse mesmo resultado, por�mcom as listas achatadas em uma s�
		Map<Customer, List<Product>> customerToProducts2Steps =
				customerToProductList.entrySet().stream()
				.collect(Collectors.toMap(Map.Entry::getKey, 
						e -> e.getValue().stream()
						.flatMap(List::stream).collect(Collectors.toList())));
		System.out.println("--------------------------------------------------------");
		customerToProducts2Steps.entrySet().stream()
			.sorted(Comparator.comparing(e -> e.getKey().getName()))
			.forEach(System.out::println);
		
		// Qual � nosso cliente mais especial
		Map<Customer, BigDecimal> totalValuePerCustomer = payments.stream()
				.collect(Collectors.groupingBy(Payment::getCustomer,
						Collectors.reducing(BigDecimal.ZERO, p -> p.getProducts().stream().map(Product::getPrice)
								.reduce(BigDecimal.ZERO, BigDecimal::add),
								BigDecimal::add)));
		
		//Cremos j� termos passado do limite da legibilidade.Va mos quebrar essa redu��o, criando 
		// uma vari�vel tempor�ria
		Function<Payment, BigDecimal> paymentToTotal = 
				p -> p.getProducts().stream()
				.map(Product::getPrice)
				.reduce(BigDecimal.ZERO, BigDecimal::add);
				
		//Comisso, podemos utilizar essa Function no reducing
		Map<Customer, BigDecimal> totalValuePerCustomer1 = payments.stream()
				.collect(Collectors.groupingBy(Payment::getCustomer,
						Collectors.reducing(BigDecimal.ZERO,
								paymentToTotal,
								BigDecimal::add)));
		System.out.println("----------------------------------------------");
		totalValuePerCustomer1.entrySet().stream()
			.sorted(Comparator.comparing(Map.Entry::getValue))
			.forEach(System.out::println);
		
		// Relat�rios com datas
		System.out.println("----------------------------------------------");
		Map<YearMonth, List<Payment>> paymentsPerMonth = payments.stream()
				.collect(Collectors.groupingBy(p -> YearMonth.from(p.getDate())));
		paymentsPerMonth.entrySet().stream().forEach(System.out::println);
		
		// E se quisermos saber, tamb�m por m�s, quanto foi faturado na loja? Basta agrupar
		// com o mesmo crit�rio e usar a redu��o que conhecemos: somando todos os
		// pre�os de todos os produtos de todos pagamentos.
		Map<YearMonth, BigDecimal> paymentsValuePerMonth = payments.stream()
				.collect(Collectors.groupingBy(p -> YearMonth.from(p.getDate()),
						Collectors.reducing(BigDecimal.ZERO,
								p -> p.getProducts().stream()
								.map(Product::getPrice)
								.reduce(BigDecimal.ZERO,
										BigDecimal::add),
								BigDecimal::add)));
		
		// Sistema de assinaturas
		System.out.println("---------Sistema de assinaturas---------");
		BigDecimal monthlyFee = new BigDecimal("99.90");
		
		Subscription s1 = new Subscription(monthlyFee, yesterday.minusMonths(5), paulo);
		Subscription s2 = new Subscription(monthlyFee, yesterday.minusMonths(8), rodrigo);
		Subscription s3 = new Subscription(monthlyFee, yesterday.minusMonths(5), adriano);
		
		List<Subscription> subscriptions = Arrays.asList(s1, s2, s3);
		
		// calcular quantos meses foram pagos atrav�s daquela assinatura
		long meses = ChronoUnit.MONTHS
				.between(s1.getBegin(), LocalDateTime.now());
		System.out.println(meses);
		
		// E se a assinatura terminou? Em vez de enchermos nosso c�digo com ifs, tiramos
		// proveito do Optional
		long mesesAssinatura = ChronoUnit.MONTHS
				.between(s1.getBegin(), s1.getEnd().orElse(LocalDateTime.now()));
		
		// Para calcular o valor gerado por aquela assinatura, bastamultiplicar esse n�mero
		// de meses pelo custo mensal
		BigDecimal totalAssinatura = s1.getMonthlyFee()
				.multiply(new BigDecimal(ChronoUnit.MONTHS
						.between(s1.getBegin(), 
								 s1.getEnd().orElse(LocalDateTime.now()))));
		
		// Dada uma lista de subscriptions, fica f�cil somar todo o total pago
		BigDecimal totalPaid = subscriptions.stream()
				.map(Subscription::getTotalPaid)
				.reduce(BigDecimal.ZERO, BigDecimal::add);
		
		
	}

}
