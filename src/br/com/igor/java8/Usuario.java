package br.com.igor.java8;

public class Usuario {
	
	private String nome;
	private int pontos;
	private boolean moderador;
	
	public Usuario(String nome, int pontos) {
		this.nome = nome;
		this.pontos = pontos;
		this.moderador = false;
	}

	public Usuario(String nome, int pontos, boolean moderador) {
		this.nome = nome;
		this.pontos = pontos;
		this.moderador = moderador;
	}
	

	public Usuario(String nome) {
		this.nome = nome;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getPontos() {
		return pontos;
	}

	public void setPontos(int pontos) {
		this.pontos = pontos;
	}

	public void tornaModerador() {
		this.moderador = true;
	}
	
	public String toString() {
		return "Usuario: " + nome;
	}

	public boolean isModerador() {
		return moderador;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		return true;
	}
	
}
