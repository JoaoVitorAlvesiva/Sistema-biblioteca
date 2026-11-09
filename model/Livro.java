package model;
import exception.ValidacaoException;

public class Livro {
	private final int id;
	private String titulo;
	private String autor;
	private int ano;
	private boolean disponivel;
	
	private static int gerarId = 1;
	
	private Livro(String titulo,String autor,int ano){
		this.id = gerarId;
		gerarId++;
		this.titulo= titulo;
		this.autor= autor;
		this.ano = ano;
		this.disponivel = true;
	}
	
	// ============================================================
	// GET
	// ============================================================
	
	public String getTitulo() {
		return titulo;
	}
	
	public int getId() {
		return id;
	}
	
	public int getAno() {
		return ano;
	}
	
	public String getAutor() {
		return autor;
	}

	public boolean getDisponivel() {
		return disponivel;
	}
	
	
	
	// ============================================================
	// Set
	// ============================================================
	

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	
	public void setAutor(String autor) {
		this.autor = autor;
	}
	
	public void setAno(int ano) {
		this.ano = ano;
	}

	public void emprestar() throws ValidacaoException{
		if(!disponivel){
			throw  new ValidacaoException("Livro indisponível");
		}
		disponivel = false;
	}
	public void devolver(){
		disponivel = true;
	}
	
	public static Livro getInstance(String titulo,String autor,int ano) throws ValidacaoException {
		if(titulo == null || titulo.trim().length() < 3) {
			throw new ValidacaoException("Título tem que ter pelo menos 3 caracteres");
		}
		if(autor == null || autor.trim().length() < 3) {
			throw new ValidacaoException("Autor tem que ter pelo menos 3 caracteres");
		}
		if(ano <= 0) {
			throw new ValidacaoException("Ano tem que ser maior que 0");
		}
		return new Livro(titulo, autor, ano);
	}

	@Override
	public boolean equals(Object o ){
		if(this == o){
			return true;
		}
		if(!(o instanceof Livro outro)){
			return false;
		}
		return id == outro.id;
	}

	@Override
	public int hashCode(){
		return Integer.hashCode(id);
	}
	
	
	public String toString() {
		return String.format("%-5d | %-60s | %-20s | %-20s | %-20s",
				id, titulo, autor,ano,disponivel ? "Sim" : "Não");
	}

	
}
