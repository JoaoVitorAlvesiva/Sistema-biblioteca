package model;
import exception.ValidacaoException;

public class Usuario {
	private final int id;
	private static int gerarId = 1;
	private String nome;
	private String email;

	private Usuario(String nome, String email) {
		this.nome = nome;
		this.email = email;
		this.id = gerarId;
		gerarId++;
	}

	public static Usuario getInstance(String nome, String email) throws ValidacaoException {
		if (nome == null || nome.trim().length() < 3) {
			throw new ValidacaoException("Nome do usuário deve ter ao menos 3 caracteres");
		}
		if (email == null || !verificarEmail(email)) {
			throw new ValidacaoException("Email inválido: " + email);
		}
		return new Usuario(nome, email);
	}
	
	
	// ============================================================
	// GET
	// ============================================================

	public String getNome() {
		return nome;
	}
	public int getId() {
		return id;
	}
	public String getEmail() {
		return email;
	}
	// ============================================================
	// SET
	// ============================================================
	public void setNome(String nome) {
		this.nome = nome;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	


	public static boolean verificarEmail(String email) {
		char[] vetorEmail = email.toCharArray();
		for (int i = 0; i < vetorEmail.length; i++) {
			if (vetorEmail[i] == '@')
				return true;
		}
		return false;
	}

	@Override
	public boolean equals(Object o ){
		if(this == o){
			return true;
		}
		if(!(o instanceof Usuario outro)){
			return false;
		}
		return id == outro.id;
	}

	@Override
	public int hashCode(){
		return Integer.hashCode(id);
	}
	
	public String toString() {
		return String.format("%-5d | %-20s | %-20s |",
				id, nome, email);
	}

}
