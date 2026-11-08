package Classes;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;

import Excecoes.ValidacaoException;

public class Emprestimo {
	private final int id;
	private Usuario usuario;
	private Livro livro;
	private LocalDate dataEmprestimo;
	private LocalDate dataPrevista;
	private LocalDate dataDevolucao;
	private StatusEmprestimo status;
	private double multa;

	private static final double MULTA_POR_DIA = 1.50;
	
	private static int geradorId = 1;

	private Emprestimo(Usuario usuario, Livro livro, int diasEmprestimo) {
		this.usuario = usuario;
		this.livro = livro;
		this.dataEmprestimo = LocalDate.now();
		this.dataPrevista = dataEmprestimo.plusDays(diasEmprestimo);
		this.status =StatusEmprestimo.ATIVO;
		this.multa=0;
		this.id = geradorId;
		geradorId++;
	}

	public static Emprestimo getInstance(Usuario usuario, Livro livro, int diasEmprestimo) throws ValidacaoException {
		if (usuario == null) {
			throw new ValidacaoException("Usuário inválido ou inexistente");
		}
		if (livro == null) {
			throw new ValidacaoException("Livro inválido ou inexistente");
		}
		if (diasEmprestimo <= 0) {
			throw new ValidacaoException("Dias inválidos");
		}
		return new Emprestimo(usuario, livro, diasEmprestimo);

	}

	public void devolver()throws ValidacaoException{
		if(status == StatusEmprestimo.DEVOLVIDO){
			throw new ValidacaoException("Empréstimo já devolvido");
		}
		this.dataDevolucao = LocalDate.now();
		long diasAtrasos = ChronoUnit.DAYS.between(dataPrevista,dataDevolucao);
		if(diasAtrasos > 0){
			this.multa = diasAtrasos * MULTA_POR_DIA;
		}
		this.status = StatusEmprestimo.DEVOLVIDO;
		livro.devolver();
	}

	// ============================================================
	// GET
	// ============================================================

	public Usuario getUsuario() {
		return usuario;
	}

	public Livro getLivro() {
		return livro;
	}

	public LocalDate getDataEmprestimo() {
		return dataEmprestimo;
	}

	public LocalDate getDataDevolucao() {
		return dataDevolucao;
	}
	public int getId() {
		return id;
	}

	// ============================================================
	// Set
	// ============================================================

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public void setLivro(Livro livro) {
		this.livro = livro;
	}

	public void setDataDevolucao(LocalDate dataDevolucao) {
		this.dataDevolucao = dataDevolucao;
	}

	public void setDataEmprestimo(LocalDate dataEmprestimo) {
		this.dataEmprestimo = dataEmprestimo;
	}

	@Override
	public boolean equals(Object o ){
		if(this == o){
			return true;
		}
		if(!(o instanceof Emprestimo outro)){
			return false;
		}
		return id == outro.id;
	}

	@Override
	public int hashCode(){
		return Integer.hashCode(id);
	}

	@Override
	public String toString() {
		DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");

		return String.format("%-13s | %-30s | %-32s | %-15s | %-15s", id, usuario.getNome(), livro.getTitulo(),
				dataEmprestimo.format(formato), dataDevolucao.format(formato));
	}

}
