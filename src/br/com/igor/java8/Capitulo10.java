package br.com.igor.java8;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.MonthDay;
import java.time.Period;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Locale;

public class Capitulo10 {

	public static void main(String[] args) {

		// Chega de Calendar! Nova API de datas
		// A java.time vem do Joda Time
		
		// incrementando um mês com Calendar
		
		Calendar mesQueVem = Calendar.getInstance();
		mesQueVem.add(Calendar.MONTH, 1);
		
		// Com a nova API de datas podemos fazer essa mesma operação de uma forma mais moderna, 
		// utilizando sua interface fluente
		LocalDate mesQueVem1 = LocalDate.now().plusMonths(1);
		System.out.println("Mes que vem: " + mesQueVem1);
		
		// Para subtrair um ano
		LocalDate anoPassado = LocalDate.now().minusYears(1);
		System.out.println("Ano passado: " + anoPassado);
		
		LocalDate data = LocalDate.now();
		System.out.println("Data: " + data);
		
		LocalDateTime dataHora = LocalDateTime.now();
		System.out.println("Data e hora: " + dataHora);
		
		LocalTime hora = LocalTime.now();
		System.out.println("Hora: " + hora);
		
		// Com horário específico é utilizando o método atTime da classe LocalDate
		LocalDateTime hojeAoMeioDia = LocalDate.now().atTime(12,0);
		System.out.println("Horario especifico: " + hojeAoMeioDia);
		
		// construindo um LocalDateTime pela junção de um LocalDate com LocalTime
		LocalTime agora = LocalTime.now();
		LocalDate hoje = LocalDate.now();
		LocalDateTime dataEhora = hoje.atTime(agora);
		System.out.println("data e hora: " + dataEhora);
		
		// modelo utilizado para representar uma data com hora e timezone.
		ZonedDateTime dataComHoraETimeZone = dataEhora.atZone(ZoneId.of("America/Sao_Paulo"));
		System.out.println(dataComHoraETimeZone);
		
		//Em alguns momentos é importante trabalhar com o timezone, mas o ideal é utilizá-lo apenas quando 
		//realmente for necessário. A própria documentação pede cuidado com o uso dessa informação, pois muitas 
		//vezes não será necessário e usá-la pode causar problemas, como para guardar o aniversário de um usuário.

		// de ZonedDateTime para LocalDateTime
		// O mesmo pode ser feito com o método toLocalDate da classe LocalDateTime, 
		// entre diversos outros métodos para conversão
		LocalDateTime semTimeZone = dataComHoraETimeZone.toLocalDateTime();
		
		// criando a partir do factory method *of*
		LocalDate date = LocalDate.of(2014, 12, 25);
		LocalDateTime dateTime = LocalDateTime.of(2014, 12, 25, 10, 30);
		
		// Para modificar o ano de um LocalDate
		LocalDate dataDoPassado = LocalDate.now().withYear(1988);
		
		// getYear para saber o ano, ou getMonth para o mês, assim por diante
		System.out.println(dataDoPassado.getYear());
		
		// alguma medida de tempo acontece antes, depois ou ao mesmo tempo que outra
		LocalDate hoje1 = LocalDate.now();
		LocalDate amanha = LocalDate.now().plusDays(1);
		
		System.out.println("Hoje é antes de amanhã: " + hoje1.isBefore(amanha));
		System.out.println("Hoje é depois de amanhã: " + hoje1.isAfter(amanha));
		System.out.println("Hoje é igual amanhã: " + hoje1.isEqual(amanha));
		
		// Para estes casos podemos e devemos utilizar o método isEqual exatamente como fizemos com o LocalDate
		// Comparar time zones
		ZonedDateTime tokyo = ZonedDateTime.of(2011, 5, 2, 10, 30, 0, 0, ZoneId.of("Asia/Tokyo"));
		ZonedDateTime sp = ZonedDateTime.of(2011, 5, 2, 10, 30, 0, 0, ZoneId.of("America/Sao_Paulo"));
		System.out.println(tokyo.isEqual(sp));
		
		// adicionar as 12 horas
		ZonedDateTime tokyo1 = ZonedDateTime.of(2011, 5, 2, 10, 30, 0, 0, ZoneId.of("Asia/Tokyo"));
		ZonedDateTime sp1 = ZonedDateTime.of(2011, 5, 2, 10, 30, 0, 0, ZoneId.of("America/Sao_Paulo"));
		tokyo1 = tokyo1.plusHours(12);
		System.out.println(tokyo1.isEqual(sp1));
		
		System.out.println("Hoje é o dia: " + MonthDay.now().getDayOfMonth());
		
		//Você pode pegar o YearMonth de uma determinada data
		YearMonth ym = YearMonth.from(data);
		System.out.println(ym.getMonth() + " " + ym.getYear());
		
		// Enums no lugar de constantes
		System.out.println(LocalDate.of(2014, 12, 25));
		System.out.println(LocalDate.of(2014, Month.DECEMBER, 25));
		
		// consultar o primeiro dia do trimestre de determinado mês, ou então incrementar/decrementar meses
		System.out.println(Month.DECEMBER.firstMonthOfQuarter());
		System.out.println(Month.DECEMBER.plus(2));
		System.out.println(Month.DECEMBER.minus(1));
		
		//Para imprimir o nome de um mês formatado, podemos utilizar o método getDisplayName fornecendo 
		// o estilo de formatação (completo, resumido, entre outros) e também o Locale:
		Locale pt = new Locale("pt");
		System.out.println(Month.DECEMBER.getDisplayName(TextStyle.FULL, pt));
		System.out.println(Month.DECEMBER.getDisplayName(TextStyle.SHORT, pt));
		
		System.out.println(DayOfWeek.FRIDAY.getDisplayName(TextStyle.FULL, pt));
		
		// Formatando com a nova API de datas
		
		LocalDateTime agora1 = LocalDateTime.now();
		String resultado = agora1.format(DateTimeFormatter.ISO_LOCAL_TIME);
		//o pattern é hh:mm:ss
		System.out.println(resultado);
		
		//criar um DateTimeFormatter com um novo padrão
		LocalDateTime agora2 = LocalDateTime.now();
		String res = agora2.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		System.out.println(res);
		
		LocalDateTime agora3 = LocalDateTime.now();
		DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		String result = agora3.format(formatador);
		LocalDate agoraEmData = LocalDate.parse(result, formatador);
		System.out.println(agoraEmData);
		
		// Muito diferente desse comportamento, a nova API de datas vai lançar uma
		// DateTimeException em casos como esse *** Não tem 30 de fevereiro
		//LocalDate.of(2014, Month.FEBRUARY, 30);
		System.out.println("-----------------------------------------------------------------------");
		
		//O mesmo acontecerá se eu tentar criar um LocalDateTime com um horário inválido
		//LocalDateTime horaInvalida = LocalDate.now().atTime(25,0);
		
		// Duração e Período
		// calcular a diferença de dias. enum ChronoUnit da nova api
		LocalDate agora4 = LocalDate.now();
		LocalDate outraData = LocalDate.of(1989, Month.JANUARY, 25);
		long dias = ChronoUnit.DAYS.between(outraData, agora4);
		System.out.println("Diferença de dias: " + dias);
		
		//diferença de anos e meses entre essas duas datas? Poderíamos utilizar o 
		// ChronoUnit.YEARS e ChronoUnit.MONTHS
		long meses = ChronoUnit.MONTHS.between(outraData, agora4);
		long anos = ChronoUnit.YEARS.between(outraData, agora4);
		System.out.printf("%s dias, %s meses, %s anos \n", dias, meses, anos);
		
		System.out.println("-----------------------------------------------------------------------");
		
		Period periodo = Period.between(outraData, agora4);
		System.out.printf("%s dias, %s meses, %s anos \n", periodo.getDays(), periodo.getMonths()
				, periodo.getYears());
	
		// calcular uma diferença entre datas, é comum a necessidade de lidarmos com valores negativos
		LocalDate agora5 = LocalDate.now();
		LocalDate outraData1 = LocalDate.of(2020, Month.JANUARY, 25);
		Period periodo1 = Period.between(outraData1, agora5);
		System.out.printf("%s dias, %s meses, %s anos \n", periodo1.getDays(), periodo1.getMonths()
				, periodo1.getYears());
		
		if(periodo1.isNegative()) {
			periodo1= periodo1.negated();
		}
		System.out.printf("%s dias, %s meses, %s anos \n", periodo1.getDays(), periodo1.getMonths()
				, periodo1.getYears());

		// utilizando o método of(years, months, days) de forma fluente
		Period periodo3 = Period.of(2, 10, 5);
		
		// Period considera as medidas de data (dias, meses e anos), 
		// a Duration considera asmedidas de tempo (horas, minutos, segundos etc.).
		LocalDateTime ag = LocalDateTime.now();
		LocalDateTime daquiUmaHora = LocalDateTime.now().plusHours(1);
		Duration duration = Duration.between(ag, daquiUmaHora);
		
		if (duration.isNegative()) {
			duration = duration.negated();
		}
		System.out.printf("%s horas, %s minutos, %s segundos \n",
				duration.toHours(), duration.toMinutes(), duration.getSeconds());
		
		
		
		
		
		
		
		
		
		
	}

}
