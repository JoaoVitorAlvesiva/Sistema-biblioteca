package Classes;
import Excecoes.ValidacaoException;

public class Usuario {
	private int id;
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
			throw new ValidacaoException("Email inváilo: " + email);
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
	public void setId(int id) {
		this.id = id;
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
	
	
	public String toString() {
		return String.format("%-5d | %-20s | %-20s |",
				id, nome, email);
	}
	
	
	
}
