package Principal;

import java.util.ArrayList;

import Classes.Emprestimo;
import Classes.Livro;
import Classes.Usuario;
import Excecoes.BuscaException;
import Excecoes.ValidacaoException;

public class SistemaBiblioteca {
	private ArrayList<Livro> livros = new ArrayList<>();
	private ArrayList<Usuario> usuarios = new ArrayList<>();
	private ArrayList<Emprestimo> emprestimos = new ArrayList<>();
	private static boolean existe = false;

	private SistemaBiblioteca(ArrayList<Livro> livros, ArrayList<Usuario> usuarios, ArrayList<Emprestimo> empresstimos)
			throws ValidacaoException {
		this.livros = livros;
		this.usuarios = usuarios;
		this.emprestimos = empresstimos;

		inicializarUsuarios();
		inicializarLivros();
		inicializarEmprestimos();

		existe = true;
	}

	public static SistemaBiblioteca getInstance(ArrayList<Livro> livros, ArrayList<Usuario> usuarios,
			ArrayList<Emprestimo> emprestimos) throws ValidacaoException {
		if (existe == false && livros != null && usuarios != null && emprestimos != null) {
			return new SistemaBiblioteca(livros, usuarios, emprestimos);
		}
		return null;
	}

	// ============================================================
	// USUÁRIO - CRUDL
	// ============================================================

	public void cadastrarUsuario(String nome, String email) throws ValidacaoException {
		Usuario novoUsuario = Usuario.getInstance(nome, email);
		usuarios.add(novoUsuario);
	}

	public ArrayList<Usuario> listarUsuarios() {
		return new ArrayList<>(usuarios);
	}

	public Usuario buscaUsuario(String nome) throws BuscaException, ValidacaoException {
		if (nome != null && nome.trim().length() >= 3) {
			for (Usuario usuario : usuarios) {
				if (usuario.getNome().equals(nome)) {
					return usuario;
				}
			}
			throw new BuscaException("Usuário não encontrado");
		} else
			throw new ValidacaoException("Nome inválido");
	}

	public void alterarIdUsuario(int id, int novoId) throws ValidacaoException {

		if (usuarios.size() == 0) {
			throw new ValidacaoException("Não existe nenhum usuário cadastrado");
		}
		if (!verificarIdExistenteUsuarios(id)) {
			throw new ValidacaoException("Não existe nenhum usuário com esse Id");
		}
		if (novoId <= 0) {
			throw new ValidacaoException("Novo Id inválido");
		}
		if (id == novoId) {
			throw new ValidacaoException("O ID tem que ser diferente do atual");
		}
		if (!verificarNovoIdUsuarios(novoId)) {
			throw new ValidacaoException("Já existe um usuário com esse ID");
		}
		for (Usuario usuario : usuarios) {
			if (usuario.getId() == id) {
				usuario.setId(novoId);
				break;
			}
		}

	}

	public void alterarNomeUsuario(int id, String novoNome) throws ValidacaoException {
		if (usuarios.size() == 0) {
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
		novoNome = novoNome.trim();
		for (Usuario usuario : usuarios) {
			if (usuario.getId() == id) {
				usuario.setNome(novoNome);
				break;
			}
		}
	}

	public void alterarEmailUsuario(int id, String novoEmail) throws ValidacaoException {
		if (usuarios.size() == 0) {
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
		novoEmail = novoEmail.trim();
		for (Usuario usuario : usuarios) {
			if (usuario.getId() == id) {
				usuario.setEmail(novoEmail);
				break;
			}
		}

	}

	public void excluirUsuario(int id) throws ValidacaoException {
		if (usuarios.size() == 0) {
			throw new ValidacaoException("Não existe nenhum usuário cadastrado");
		}
		if (id <= 0) {
			throw new ValidacaoException("Id inválido");
		}
		if (!verificarIdExistenteUsuarios(id)) {
			throw new ValidacaoException("Não existe nenhum usuário com esse Id");
		}
		if(verificarExistenciaEmprestimoUsuario(id)) {
			throw new ValidacaoException("Usuário possui um empréstimo");
		}
		for (int i = 0; i < usuarios.size(); i++) {
			if (usuarios.get(i).getId() == id) {
				usuarios.remove(i);
				break;
			}
		}
	}

	public boolean verificarNomeDuplicado(String novoNome, int idIgnorado) {
		for (Usuario usuario : usuarios) {
			if (usuario.getId() != idIgnorado && usuario.getNome().equalsIgnoreCase(novoNome.trim())) {
				return true;
			}
		}
		return false;
	}

	public boolean verificarNomeDuplicado(int id, String novoNome) {
		for (Usuario usuario : usuarios) {
			if (usuario.getId() == id) {
				if (usuario.getNome().equalsIgnoreCase(novoNome.trim())) {
					return true;
				}
			}
		}
		return false;
	}

	private boolean verificarNovoIdUsuarios(int id) {
		for (Usuario usuario : usuarios) {
			if (usuario.getId() == id)
				return false;
		}
		return true;
	}

	private boolean verificarIdExistenteUsuarios(int id) {
		for (Usuario usuario : usuarios) {
			if (usuario.getId() == id)
				return true;
		}
		return false;
	}

	private boolean verificarEmailDuplicado(String email, int idIgnorado) {
		if (email == null) {
	        return false;
	    }
		for (Usuario usuario : usuarios) {
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
		for (Usuario usuario : usuarios) {
			if (usuario.getId() == id) {
				if (usuario.getEmail().equalsIgnoreCase(novoEmail.trim())) {
					return true;
				}
			}
		}
		return false;
	}
	
	private boolean verificarExistenciaEmprestimoUsuario(int id){
		for(Emprestimo emprestimo : emprestimos) {
			if(emprestimo.getUsuario().getId() == id) {
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
		livros.add(novoLivro);
	}

	public ArrayList<Livro> listarLivros() {
		return new ArrayList<>(livros);
	}

	public Livro buscaLivro(String titulo) throws ValidacaoException, BuscaException {
		if (titulo != null && titulo.trim().length() >= 3) {
			for (Livro livro : livros) {
				if (livro.getTitulo().trim().equalsIgnoreCase(titulo.trim())) {
					return livro;
				}
			}
			throw new BuscaException("Livro não encontrado");
		} else
			throw new ValidacaoException("Título inválido");
	}

	public void alterarIdLivro(int id, int novoId) throws ValidacaoException {

		if (livros.size() == 0) {
			throw new ValidacaoException("Não existe nenhum livro cadastrado");
		}
		if (!verificarIdExistenteLivros(id)) {
			throw new ValidacaoException("Não existe nenhum livro com esse ID");
		}
		if (novoId <= 0) {
			throw new ValidacaoException("Novo ID inválido");
		}
		if (id == novoId) {
			throw new ValidacaoException("O ID tem que ser diferente do atual");
		}

		if (!verificarNovoIdLivros(novoId)) {
			throw new ValidacaoException("Já existe um livro com esse ID");
		}
		for (Livro livro : livros) {
			if (livro.getId() == id) {
				livro.setId(novoId);
				break;
			}
		}

	}

	public void alterarTituloLivro(int id, String novoTitulo) throws ValidacaoException {
		if (livros.size() == 0) {
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

		novoTitulo = novoTitulo.trim();
		for (Livro livro : livros) {
			if (livro.getId() == id) {
				livro.setTitulo(novoTitulo);
				break;
			}
		}
	}

	public void alterarAutorLivro(int id, String novoAutor) throws ValidacaoException {
		if (livros.size() == 0) {
			throw new ValidacaoException("Não existe nenhum livro cadastrado");
		}
		if (!verificarIdExistenteLivros(id)) {
			throw new ValidacaoException("Não existe nenhum livro com esse ID cadastrado");
		}
		if (novoAutor == null || novoAutor.trim().length() < 3) {
			throw new ValidacaoException("nome do novo autor deve ter ao menos 3 caracteres");
		}
		if (verificarAutorDuplicado(id, novoAutor)) {
			throw new ValidacaoException("Nome do novo autor do livro tem que ser diferente do atual");
		}

		novoAutor = novoAutor.trim();

		for (Livro livro : livros) {
			if (livro.getId() == id) {
				livro.setAutor(novoAutor);
				break;
			}
		}

	}

	public void alterarAnoLivro(int id, int novoAno) throws ValidacaoException {
		if (livros.size() == 0) {
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

		for (Livro livro : livros) {
			if (livro.getId() == id) {
				livro.setAno(novoAno);
				break;
			}
		}
	}

	public void excluirlivro(int id) throws ValidacaoException {
		if (livros.size() == 0) {
			throw new ValidacaoException("Não existe nenhum livro cadastrado");
		}
		if (id <= 0) {
			throw new ValidacaoException("Id inválido");
		}
		if (!verificarIdExistenteLivros(id)) {
			throw new ValidacaoException("Não existe nenhum livro com esse Id");
		}
		if(verificarExistenciaEmprestimoLivro(id)) {
			throw new ValidacaoException("Livro possui um empréstimo");
		}
		for (int i = 0; i < livros.size(); i++) {
			if (livros.get(i).getId() == id) {
				livros.remove(i);
				break;
			}
		}
	}

	public boolean verificarAnoDuplicado(int id, int novoAno) {
		for (Livro livro : livros) {
			if (livro.getId() == id) {
				if (livro.getAno() == novoAno) {
					return true;
				}
			}
		}
		return false;
	}

	public boolean verificarAutorDuplicado(int id, String novoAutor) {
		for (Livro livro : livros) {
			if (livro.getId() == id) {
				if (livro.getAutor().equalsIgnoreCase(novoAutor.trim())) {
					return true;
				}
			}
		}
		return false;
	}

	public boolean verificarTituloDuplicado(int id, String novoTitulo) {
		for (Livro livro : livros) {
			if (livro.getId() == id) {
				if (livro.getTitulo().equalsIgnoreCase(novoTitulo.trim())) {
					return true;
				}
			}
		}
		return false;
	}

	private boolean verificarNovoIdLivros(int id) {
		for (Livro livro : livros) {
			if (livro.getId() == id)
				return false;
		}
		return true;
	}
	
	private boolean verificarExistenciaEmprestimoLivro(int id) throws ValidacaoException {
		for(Emprestimo emprestimo : emprestimos) {
			if(emprestimo.getLivro().getId() == id) {
				return true;
			}
		}
		return false;
	}

	public boolean verificarIdExistenteLivros(int id) {
		for (Livro livro : livros) {
			if (livro.getId() == id)
				return true;
		}
		return false;
	}

	// ============================================================
	// EMPRÉSTIMOS
	// ============================================================

	public void fazerEmprestimo(int idLivro, int idUsuario, int diasEmprestimo)
			throws ValidacaoException, BuscaException {
		if (!verificarIdExistenteLivros(idLivro)) {
			throw new BuscaException("Não existe nenhum livro com esse ID");
		}
		if (!verificarIdExistenteUsuarios(idUsuario)) {
			throw new BuscaException("Não existe nenhum usuário com esse ID");
		}

		if (!livroDisponivel(idLivro)) {
			throw new ValidacaoException("Livro indisponível");
		}

		Usuario usuario = adicionarUsuarioEmprestimo(idUsuario);
		Livro livro = adicionarLivroEmprestimo(idLivro);

		Emprestimo emprestimo = Emprestimo.getInstance(usuario, livro, diasEmprestimo);
		emprestimos.add(emprestimo);
		livro.setDisponivel(false);

	}

	public ArrayList<Emprestimo> listarEmprestimo() {
		return new ArrayList<>(emprestimos);
	}

	private Livro adicionarLivroEmprestimo(int idLivro) throws ValidacaoException {
		for (int i = 0; i < livros.size(); i++) {
			if (livros.get(i).getId() == idLivro) {
				return livros.get(i);
			}
		}
		throw new ValidacaoException("Não existe nenhum livro com esse ID");
	}

	private Usuario adicionarUsuarioEmprestimo(int idUsuario) throws ValidacaoException {
		for (int i = 0; i < usuarios.size(); i++) {
			if (usuarios.get(i).getId() == idUsuario) {
				return usuarios.get(i);
			}
		}
		throw new ValidacaoException("Não existe nenhum usuário com esse ID");
	}

	private boolean livroDisponivel(int id) {
		for (int i = 0; i < livros.size(); i++) {
			if (livros.get(i).getId() == id && livros.get(i).getDisponivel()) {
				return true;
			}
		}
		return false;
	}


	public void excluirEmprestimo(int idEmprestimo) throws ValidacaoException {
		if (idEmprestimo <= 0) {
		    throw new ValidacaoException("Id inválido");
		}
		if (emprestimos.size() == 0) {
			throw new ValidacaoException("Não existe nenhum empréstimo cadastrado");
		}
		if (!verificarIdExistenteEmprestimo(idEmprestimo)) {
			throw new ValidacaoException("Não existe nenhum empréstimo cadastrado");
		}
		for (int i = 0; i < emprestimos.size(); i++) {
		    if (emprestimos.get(i).getId() == idEmprestimo) {
		        emprestimos.get(i).getLivro().setDisponivel(true);
		        emprestimos.remove(i);
		        break;
		    }
		}
	}

	private boolean verificarIdExistenteEmprestimo(int id) {
		for (Emprestimo emprestimo : emprestimos) {
			if (emprestimo.getId() == id)
				return true;
		}
		return false;
	}

	public  ArrayList<Emprestimo> listarEmprestimoUsuario(int idUsuario) throws ValidacaoException{
		if (usuarios.size() == 0) {
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
		usuarios.add(Usuario.getInstance("João", "joao@gmail.com"));
		usuarios.add(Usuario.getInstance("Maria", "maria@gmail.com"));
		usuarios.add(Usuario.getInstance("Carlos", "carlos@gmail.com"));
		usuarios.add(Usuario.getInstance("Ana", "ana@gmail.com"));
	}

	private void inicializarLivros() throws ValidacaoException {
		livros.add(Livro.getInstance("Dom Casmurro", "Machado de Assis", 1899));
		livros.add(Livro.getInstance("O Cortiço", "Aluísio Azevedo", 1890));
		livros.add(Livro.getInstance("Memórias Póstumas de Brás Cubas", "Machado de Assis", 1881));
		livros.add(Livro.getInstance("Vidas Secas", "Graciliano Ramos", 1938));
		livros.add(Livro.getInstance("O Hobbit", "J. R. R. Tolkien", 1937));
		livros.add(Livro.getInstance("1984", "George Orwell", 1949));
		livros.add(Livro.getInstance("Harry Potter e a Pedra Filosofal", "J. K. Rowling", 1997));
		livros.add(Livro.getInstance("O Senhor dos Anéis", "J. R. R. Tolkien", 1954));
	}

	private void inicializarEmprestimos() throws ValidacaoException {
		emprestimos.add(Emprestimo.getInstance(usuarios.get(0), livros.get(0), 7));

		emprestimos.add(Emprestimo.getInstance(usuarios.get(0), livros.get(1), 14));

		emprestimos.add(Emprestimo.getInstance(usuarios.get(2), livros.get(2), 10));

		emprestimos.add(Emprestimo.getInstance(usuarios.get(3), livros.get(3), 7));
		
		livros.get(0).setDisponivel(false);
		livros.get(1).setDisponivel(false);
		livros.get(2).setDisponivel(false);
		livros.get(3).setDisponivel(false);
	}

}
