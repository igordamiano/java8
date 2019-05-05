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
		
		// incrementando um m�s com Calendar
		
		Calendar mesQueVem = Calendar.getInstance();
		mesQueVem.add(Calendar.MONTH, 1);
		
		// Com a nova API de datas podemos fazer essa mesma opera��o de uma forma mais moderna, 
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
		
		// Com hor�rio espec�fico � utilizando o m�todo atTime da classe LocalDate
		LocalDateTime hojeAoMeioDia = LocalDate.now().atTime(12,0);
		System.out.println("Horario especifico: " + hojeAoMeioDia);
		
		// construindo um LocalDateTime pela jun��o de um LocalDate com LocalTime
		LocalTime agora = LocalTime.now();
		LocalDate hoje = LocalDate.now();
		LocalDateTime dataEhora = hoje.atTime(agora);
		System.out.println("data e hora: " + dataEhora);
		
		// modelo utilizado para representar uma data com hora e timezone.
		ZonedDateTime dataComHoraETimeZone = dataEhora.atZone(ZoneId.of("America/Sao_Paulo"));
		System.out.println(dataComHoraETimeZone);
		
		//Em alguns momentos � importante trabalhar com o timezone, mas o ideal � utiliz�-lo apenas quando 
		//realmente for necess�rio. A pr�pria documenta��o pede cuidado com o uso dessa informa��o, pois muitas 
		//vezes n�o ser� necess�rio e us�-la pode causar problemas, como para guardar o anivers�rio de um usu�rio.

		// de ZonedDateTime para LocalDateTime
		// O mesmo pode ser feito com o m�todo toLocalDate da classe LocalDateTime, 
		// entre diversos outros m�todos para convers�o
		LocalDateTime semTimeZone = dataComHoraETimeZone.toLocalDateTime();
		
		// criando a partir do factory method *of*
		LocalDate date = LocalDate.of(2014, 12, 25);
		LocalDateTime dateTime = LocalDateTime.of(2014, 12, 25, 10, 30);
		
		// Para modificar o ano de um LocalDate
		LocalDate dataDoPassado = LocalDate.now().withYear(1988);
		
		// getYear para saber o ano, ou getMonth para o m�s, assim por diante
		System.out.println(dataDoPassado.getYear());
		
		// alguma medida de tempo acontece antes, depois ou ao mesmo tempo que outra
		LocalDate hoje1 = LocalDate.now();
		LocalDate amanha = LocalDate.now().plusDays(1);
		
		System.out.println("Hoje � antes de amanh�: " + hoje1.isBefore(amanha));
		System.out.println("Hoje � depois de amanh�: " + hoje1.isAfter(amanha));
		System.out.println("Hoje � igual amanh�: " + hoje1.isEqual(amanha));
		
		// Para estes casos podemos e devemos utilizar o m�todo isEqual exatamente como fizemos com o LocalDate
		// Comparar time zones
		ZonedDateTime tokyo = ZonedDateTime.of(2011, 5, 2, 10, 30, 0, 0, ZoneId.of("Asia/Tokyo"));
		ZonedDateTime sp = ZonedDateTime.of(2011, 5, 2, 10, 30, 0, 0, ZoneId.of("America/Sao_Paulo"));
		System.out.println(tokyo.isEqual(sp));
		
		// adicionar as 12 horas
		ZonedDateTime tokyo1 = ZonedDateTime.of(2011, 5, 2, 10, 30, 0, 0, ZoneId.of("Asia/Tokyo"));
		ZonedDateTime sp1 = ZonedDateTime.of(2011, 5, 2, 10, 30, 0, 0, ZoneId.of("America/Sao_Paulo"));
		tokyo1 = tokyo1.plusHours(12);
		System.out.println(tokyo1.isEqual(sp1));
		
		System.out.println("Hoje � o dia: " + MonthDay.now().getDayOfMonth());
		
		//Voc� pode pegar o YearMonth de uma determinada data
		YearMonth ym = YearMonth.from(data);
		System.out.println(ym.getMonth() + " " + ym.getYear());
		
		// Enums no lugar de constantes
		System.out.println(LocalDate.of(2014, 12, 25));
		System.out.println(LocalDate.of(2014, Month.DECEMBER, 25));
		
		// consultar o primeiro dia do trimestre de determinado m�s, ou ent�o incrementar/decrementar meses
		System.out.println(Month.DECEMBER.firstMonthOfQuarter());
		System.out.println(Month.DECEMBER.plus(2));
		System.out.println(Month.DECEMBER.minus(1));
		
		//Para imprimir o nome de um m�s formatado, podemos utilizar o m�todo getDisplayName fornecendo 
		// o estilo de formata��o (completo, resumido, entre outros) e tamb�m o Locale:
		Locale pt = new Locale("pt");
		System.out.println(Month.DECEMBER.getDisplayName(TextStyle.FULL, pt));
		System.out.println(Month.DECEMBER.getDisplayName(TextStyle.SHORT, pt));
		
		System.out.println(DayOfWeek.FRIDAY.getDisplayName(TextStyle.FULL, pt));
		
		// Formatando com a nova API de datas
		
		LocalDateTime agora1 = LocalDateTime.now();
		String resultado = agora1.format(DateTimeFormatter.ISO_LOCAL_TIME);
		//o pattern � hh:mm:ss
		System.out.println(resultado);
		
		//criar um DateTimeFormatter com um novo padr�o
		LocalDateTime agora2 = LocalDateTime.now();
		String res = agora2.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		System.out.println(res);
		
		LocalDateTime agora3 = LocalDateTime.now();
		DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		String result = agora3.format(formatador);
		LocalDate agoraEmData = LocalDate.parse(result, formatador);
		System.out.println(agoraEmData);
		
		// Muito diferente desse comportamento, a nova API de datas vai lan�ar uma
		// DateTimeException em casos como esse *** N�o tem 30 de fevereiro
		//LocalDate.of(2014, Month.FEBRUARY, 30);
		System.out.println("-----------------------------------------------------------------------");
		
		//O mesmo acontecer� se eu tentar criar um LocalDateTime com um hor�rio inv�lido
		//LocalDateTime horaInvalida = LocalDate.now().atTime(25,0);
		
		// Dura��o e Per�odo
		// calcular a diferen�a de dias. enum ChronoUnit da nova api
		LocalDate agora4 = LocalDate.now();
		LocalDate outraData = LocalDate.of(1989, Month.JANUARY, 25);
		long dias = ChronoUnit.DAYS.between(outraData, agora4);
		System.out.println("Diferen�a de dias: " + dias);
		
		//diferen�a de anos e meses entre essas duas datas? Poder�amos utilizar o 
		// ChronoUnit.YEARS e ChronoUnit.MONTHS
		long meses = ChronoUnit.MONTHS.between(outraData, agora4);
		long anos = ChronoUnit.YEARS.between(outraData, agora4);
		System.out.printf("%s dias, %s meses, %s anos \n", dias, meses, anos);
		
		System.out.println("-----------------------------------------------------------------------");
		
		Period periodo = Period.between(outraData, agora4);
		System.out.printf("%s dias, %s meses, %s anos \n", periodo.getDays(), periodo.getMonths()
				, periodo.getYears());
	
		// calcular uma diferen�a entre datas, � comum a necessidade de lidarmos com valores negativos
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

		// utilizando o m�todo of(years, months, days) de forma fluente
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
