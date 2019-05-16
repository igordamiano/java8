package br.com.igor.java8.alura.data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.Period;
import java.time.format.DateTimeFormatter;

public class Datas {

	public static void main(String[] args) {

		LocalDate hoje = LocalDate.now();
		System.out.println(hoje);
		
		LocalDate dataCopaAmerica = LocalDate.of(2019, Month.JULY, 16);
		System.out.println(dataCopaAmerica);
		
		Period periodo = Period.between(hoje, dataCopaAmerica);
		System.out.println(periodo);
		
		DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		
		String dataCopaFormatado = dataCopaAmerica.format(formato);
		System.out.println(dataCopaFormatado);
		//
		// Data e hora
		
		DateTimeFormatter formatoComHora = DateTimeFormatter.ofPattern("dd/MM/yyyy dd:mm");
		LocalDateTime agora = LocalDateTime.now();
		System.out.println(agora.format(formatoComHora));
		
		
	}

}
