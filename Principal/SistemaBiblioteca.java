package Principal;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import Classes.Emprestimo;
import Classes.Livro;
import Classes.Usuario;
import Excecoes.BuscaException;
import Excecoes.LimiteEmprestimosException;
import Excecoes.ValidacaoException;

public class SistemaBiblioteca {
	private static final int LIMITE_EMPRESTIMOS_ATIVOS = 3;
	private Map<Integer, Livro> livros = new LinkedHashMap<>();
	private Map<Integer, Usuario> usuarios = new LinkedHashMap<>();
	private ArrayList<Emprestimo> emprestimos = new ArrayList<>();

	private static SistemaBiblioteca instancia;

	private SistemaBiblioteca() throws ValidacaoException {
		inicializarUsuarios();
		inicializarLivros();
		inicializarEmprestimos();
	}

	public static SistemaBiblioteca getInstance() throws ValidacaoException {
		if (instancia == null) {
			instancia = new SistemaBiblioteca();
		}
		return instancia;
	}

	private int contarEmprestimoAtivos(int idUsuario){
		int total = 0;
		for (Emprestimo emprestimo : emprestimos) {
			if (emprestimo.getUsuario().getId() == idUsuario && emprestimo.estaAtivo()) {
				total++;
			}
		}
		return total;
	}

	// ============================================================
	// USUÁRIO - CRUDL
	// ============================================================

	public void cadastrarUsuario(String nome, String email) throws ValidacaoException {
		Usuario novoUsuario = Usuario.getInstance(nome, email);
		usuarios.put(novoUsuario.getId(), novoUsuario);
	}

	public ArrayList<Usuario> listarUsuarios() {
		return new ArrayList<>(usuarios.values());
	}

	public Usuario buscaUsuario(String nome) throws BuscaException, ValidacaoException {
		if (nome != null && nome.trim().length() >= 3) {
			for (Usuario usuario : usuarios.values()) {
				if (usuario.getNome().trim().equalsIgnoreCase(nome.trim())) {
					return usuario;
				}
			}
			throw new BuscaException("Usuário não encontrado");
		} else
			throw new ValidacaoException("Nome inválido");
	}


	public void alterarNomeUsuario(int id, String novoNome) throws ValidacaoException {
		if (usuarios.isEmpty()) {
			throw new ValidacaoException("Não existe nenhum usuário cadastrado");
		}
		if (!verificarIdExistenteUsuarios(id)) {
			throw new ValidacaoException("Não existe nenhum usuário com esse Id");
		}
		if (novoNome == null || novoNome.trim().length() < 3) {
			throw new ValidacaoException("Nome do usuário deve ter ao menos 3 caracteres");
		}
		if (verificarNomeDuplicado(novoNome, id)) {
			throw new ValidacaoException("Já existe um usuário com esse nome");
		}
		if (verificarNomeDuplicado(id, novoNome)) {
			throw new ValidacaoException("Nome do usuário deve ser diferente do atual");
		}
		usuarios.get(id).setNome(novoNome.trim());
	}

	public void alterarEmailUsuario(int id, String novoEmail) throws ValidacaoException {
		if (usuarios.isEmpty()) {
			throw new ValidacaoException("Não existe nenhum usuário cadastrado");
		}
		if (!verificarIdExistenteUsuarios(id)) {
			throw new ValidacaoException("Não existe nenhum usuário com esse Id");
		}
		if (!Usuario.verificarEmail(novoEmail)) {
			throw new ValidacaoException("Email inválido: " + novoEmail);
		}
		if (verificarEmailDuplicado(novoEmail, id)) {
			throw new ValidacaoException("Já existe um usuário com esse email");
		}
		if (verificarEmailDuplicado(id, novoEmail)) {
			throw new ValidacaoException("Email tem que ser diferente do atual");
		}
		usuarios.get(id).setEmail(novoEmail.trim());
	}

	public void excluirUsuario(int id) throws ValidacaoException {
		if (usuarios.isEmpty()) {
			throw new ValidacaoException("Não existe nenhum usuário cadastrado");
		}
		if (id <= 0) {
			throw new ValidacaoException("Id inválido");
		}
		if (!verificarIdExistenteUsuarios(id)) {
			throw new ValidacaoException("Não existe nenhum usuário com esse Id");
		}
		if (verificarExistenciaEmprestimoUsuario(id)) {
			throw new ValidacaoException("Usuário possui empréstimo ativo");
		}
		usuarios.remove(id);
	}

	public boolean verificarNomeDuplicado(String novoNome, int idIgnorado) {
		for (Usuario usuario : usuarios.values()) {
			if (usuario.getId() != idIgnorado && usuario.getNome().equalsIgnoreCase(novoNome.trim())) {
				return true;
			}
		}
		return false;
	}

	public boolean verificarNomeDuplicado(int id, String novoNome) {
		return usuarios.get(id).getNome().equalsIgnoreCase(novoNome.trim());
	}

	private boolean verificarIdExistenteUsuarios(int id) {
		return usuarios.containsKey(id);
	}

	private boolean verificarEmailDuplicado(String email, int idIgnorado) {
		if (email == null) {
			return false;
		}
		for (Usuario usuario : usuarios.values()) {
			if (usuario.getId() != idIgnorado && usuario.getEmail().equalsIgnoreCase(email.trim())) {
				return true;
			}
		}
		return false;
	}

	public boolean verificarEmailDuplicado(int id, String novoEmail) {
		if (novoEmail == null) {
			return false;
		}
		return usuarios.get(id).getEmail().equalsIgnoreCase(novoEmail.trim());
	}
	
	private boolean verificarExistenciaEmprestimoUsuario(int id){
		for(Emprestimo emprestimo : emprestimos) {
			if(emprestimo.getUsuario().getId() == id && emprestimo.estaAtivo()) {
				return true;
			}
		}
		return false;
	}

	// ============================================================
	// LIVRO- CRUDL
	// ============================================================

	public void cadastrarLivro(String titulo, String autor, int ano) throws ValidacaoException {
		Livro novoLivro = Livro.getInstance(titulo, autor, ano);
		livros.put(novoLivro.getId(), novoLivro);
	}

	public ArrayList<Livro> listarLivros() {
		return new ArrayList<>(livros.values());
	}

	public Livro buscaLivro(String titulo) throws ValidacaoException, BuscaException {
		if (titulo != null && titulo.trim().length() >= 3) {
			for (Livro livro : livros.values()) {
				if (livro.getTitulo().trim().equalsIgnoreCase(titulo.trim())) {
					return livro;
				}
			}
			throw new BuscaException("Livro não encontrado");
		} else
			throw new ValidacaoException("Título inválido");
	}


	public void alterarTituloLivro(int id, String novoTitulo) throws ValidacaoException {
		if (livros.isEmpty()) {
			throw new ValidacaoException("Não existe nenhum livro cadastrado");
		}
		if (!verificarIdExistenteLivros(id)) {
			throw new ValidacaoException("Não existe nenhum livro com esse ID cadastrado");
		}
		if (novoTitulo == null || novoTitulo.trim().length() < 3) {
			throw new ValidacaoException("Título deve ter ao menos 3 caracteres");
		}
		if (verificarTituloDuplicado(id, novoTitulo)) {
			throw new ValidacaoException("Novo título do livro tem que ser diferente do atual");
		}
		livros.get(id).setTitulo(novoTitulo.trim());
	}

	public void alterarAutorLivro(int id, String novoAutor) throws ValidacaoException {
		if (livros.isEmpty()) {
			throw new ValidacaoException("Não existe nenhum livro cadastrado");
		}
		if (!verificarIdExistenteLivros(id)) {
			throw new ValidacaoException("Não existe nenhum livro com esse ID cadastrado");
		}
		if (novoAutor == null || novoAutor.trim().length() < 3) {
			throw new ValidacaoException("Nome do novo autor deve ter ao menos 3 caracteres");
		}
		if (verificarAutorDuplicado(id, novoAutor)) {
			throw new ValidacaoException("Nome do novo autor do livro tem que ser diferente do atual");
		}
		livros.get(id).setAutor(novoAutor.trim());
	}

	public void alterarAnoLivro(int id, int novoAno) throws ValidacaoException {
		if (livros.isEmpty()) {
			throw new ValidacaoException("Não existe nenhum livro cadastrado");
		}
		if (!verificarIdExistenteLivros(id)) {
			throw new ValidacaoException("Não existe nenhum livro com esse ID cadastrado");
		}
		if (novoAno <= 0) {
			throw new ValidacaoException("Ano tem que ser maior que 0");
		}
		if (verificarAnoDuplicado(id, novoAno)) {
			throw new ValidacaoException("Ano tem que ser diferente do atual");
		}
		livros.get(id).setAno(novoAno);
	}

	public void excluirlivro(int id) throws ValidacaoException {
		if (livros.isEmpty()) {
			throw new ValidacaoException("Não existe nenhum livro cadastrado");
		}
		if (id <= 0) {
			throw new ValidacaoException("Id inválido");
		}
		if (!verificarIdExistenteLivros(id)) {
			throw new ValidacaoException("Não existe nenhum livro com esse Id");
		}
		if (verificarExistenciaEmprestimoLivro(id)) {
			throw new ValidacaoException("Livro possui empréstimo ativo");
		}
		livros.remove(id);
	}

	public boolean verificarAnoDuplicado(int id, int novoAno) {
		return livros.get(id).getAno() == novoAno;
	}

	public boolean verificarAutorDuplicado(int id, String novoAutor) {
		return livros.get(id).getAutor().equalsIgnoreCase(novoAutor.trim());
	}

	public boolean verificarTituloDuplicado(int id, String novoTitulo) {
		return livros.get(id).getTitulo().equalsIgnoreCase(novoTitulo.trim());
	}

	
	private boolean verificarExistenciaEmprestimoLivro(int id){
		for(Emprestimo emprestimo : emprestimos) {
			if(emprestimo.getLivro().getId() == id && emprestimo.estaAtivo()) {
				return true;
			}
		}
		return false;
	}

	public boolean verificarIdExistenteLivros(int id) {
		return livros.containsKey(id);
	}

	// ============================================================
	// EMPRÉSTIMOS
	// ============================================================

	public void fazerEmprestimo(int idLivro, int idUsuario, int diasEmprestimo)
			throws ValidacaoException, BuscaException, LimiteEmprestimosException {
		if (!verificarIdExistenteLivros(idLivro)) {
			throw new BuscaException("Não existe nenhum livro com esse ID");
		}
		if (!verificarIdExistenteUsuarios(idUsuario)) {
			throw new BuscaException("Não existe nenhum usuário com esse ID");
		}
		if (!livros.get(idLivro).getDisponivel()) {
			throw new ValidacaoException("Livro indisponível");
		}
		if (contarEmprestimoAtivos(idUsuario) >= LIMITE_EMPRESTIMOS_ATIVOS) {
			throw new LimiteEmprestimosException(
					"Usuário atingiu o limite de " + LIMITE_EMPRESTIMOS_ATIVOS + " empréstimos ativos");
		}

		Usuario usuario = usuarios.get(idUsuario);
		Livro livro = livros.get(idLivro);

		Emprestimo emprestimo = Emprestimo.getInstance(usuario, livro, diasEmprestimo);
		livro.emprestar();
		emprestimos.add(emprestimo);
	}

	public ArrayList<Emprestimo> listarEmprestimo() {
		return new ArrayList<>(emprestimos);
	}



	public double devolverLivro(int idEmprestimo) throws  ValidacaoException{
		if(idEmprestimo <= 0){
			throw new ValidacaoException("Id inválido");
		}
		for(Emprestimo emprestimo : emprestimos){
			if(emprestimo.getId() == idEmprestimo){
				emprestimo.devolver();
				return emprestimo.getMulta();
			}
		}
		throw new ValidacaoException("Não existe nenhum empréstimo com esse ID");
	}


	public  ArrayList<Emprestimo> listarEmprestimoUsuario(int idUsuario) throws ValidacaoException{
		if (usuarios.isEmpty()) {
			throw new ValidacaoException("Não existe nenhum usuário cadastrado");
		}
		if (!verificarIdExistenteUsuarios(idUsuario)) {
			throw new ValidacaoException("Não existe nenhum usuário com esse Id");
		}
		 ArrayList<Emprestimo> emprestimosUsuario = new ArrayList<>();
		 for(Emprestimo emprestimo : emprestimos) {
			 if(emprestimo.getUsuario().getId() == idUsuario) {
				 emprestimosUsuario.add(emprestimo);
			 }
		 }
		 if (emprestimosUsuario.isEmpty()) {
			    throw new ValidacaoException(
			        "Esse usuário não possui nenhum empréstimo"
			    );
			}
		 return emprestimosUsuario;
	}

	// ============================================================
	// INICIALIZAÇÃO
	// ============================================================

	private void inicializarUsuarios() throws ValidacaoException {
		cadastrarUsuario("João", "joao@gmail.com");
		cadastrarUsuario("Maria", "maria@gmail.com");
		cadastrarUsuario("Carlos", "carlos@gmail.com");
		cadastrarUsuario("Ana", "ana@gmail.com");
	}

	private void inicializarLivros() throws ValidacaoException {
		cadastrarLivro("Dom Casmurro", "Machado de Assis", 1899);
		cadastrarLivro("O Cortiço", "Aluísio Azevedo", 1890);
		cadastrarLivro("Memórias Póstumas de Brás Cubas", "Machado de Assis", 1881);
		cadastrarLivro("Vidas Secas", "Graciliano Ramos", 1938);
		cadastrarLivro("O Hobbit", "J. R. R. Tolkien", 1937);
		cadastrarLivro("1984", "George Orwell", 1949);
		cadastrarLivro("Harry Potter e a Pedra Filosofal", "J. K. Rowling", 1997);
		cadastrarLivro("O Senhor dos Anéis", "J. R. R. Tolkien", 1954);
	}

	private void inicializarEmprestimos() throws ValidacaoException {
		registrarEmprestimoInicial(1, 1, 7);
		registrarEmprestimoInicial(1, 2, 14);
		registrarEmprestimoInicial(3, 3, 10);
		registrarEmprestimoInicial(4, 4, 7);
	}

	private void registrarEmprestimoInicial(int idUsuario, int idLivro, int dias) throws ValidacaoException {
		Emprestimo emprestimo = Emprestimo.getInstance(usuarios.get(idUsuario), livros.get(idLivro), dias);
		livros.get(idLivro).emprestar();
		emprestimos.add(emprestimo);
	}

}
