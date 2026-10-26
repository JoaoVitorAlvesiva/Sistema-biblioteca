package Classes;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import Excecoes.ValidacaoException;

public class Emprestimo {
	private int id;
	private Usuario usuario;
	private Livro livro;
	private LocalDate dataEmprestimo;
	private LocalDate dataDevolucao;
	
	private static int geradorId = 1;

	private Emprestimo(Usuario usuario, Livro livro, int diasEmprestimo) {
		this.usuario = usuario;
		this.livro = livro;
		this.dataEmprestimo = LocalDate.now();
		this.dataDevolucao = dataEmprestimo.plusDays(diasEmprestimo);
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
	public String toString() {
		DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");

		return String.format("%-13s | %-30s | %-32s | %-15s | %-15s", id, usuario.getNome(), livro.getTitulo(),
				dataEmprestimo.format(formato), dataDevolucao.format(formato));
	}

}
