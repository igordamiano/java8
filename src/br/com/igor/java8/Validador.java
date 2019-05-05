package br.com.igor.java8;

@FunctionalInterface
public interface Validador <T> {
	
	// Nossa interface funcional
	
	boolean valida(T t);
}
