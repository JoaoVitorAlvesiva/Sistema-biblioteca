package Principal;

import java.util.ArrayList;
import java.util.Scanner;

import Classes.Emprestimo;
import Classes.Livro;
import Classes.Usuario;
import Excecoes.BuscaException;
import Excecoes.ValidacaoException;

public class Main {

	static Scanner sc = new Scanner(System.in);

	public static void main(String[] args) throws ValidacaoException {
		SistemaBiblioteca sis = SistemaBiblioteca.getInstance(new ArrayList<>(), new ArrayList<>(), new ArrayList<>());

		System.out.println("----------Bem Vindo----------");
		menuPrincipal(sis);

	}

	static void menuPrincipal(SistemaBiblioteca sis) throws ValidacaoException {
		int escolha;
		do {
			System.out.println("1-Usuários");
			System.out.println("2-Livros");
			System.out.println("3-Empréstimos");
			System.out.println("4-Encerrar programa:");
			escolha = sc.nextInt();
			sc.nextLine();
			System.out.println("-----------------------------");

			switch (escolha) {
			case 1: {
				menuUsuario(sis);
				break;
			}
			case 2: {
				menuLivros(sis);
				break;
			}
			case 3: {
				menuEmprestimos(sis);
				break;
			}
			case 4: {
				System.out.println("Programa encerrado!");
				break;
			}

			default:
				System.out.println("Opção inválida");
			}
		} while (escolha != 4);
	}

	static void menuUsuario(SistemaBiblioteca sis) throws ValidacaoException {
		int escolha;
		do {
			System.out.println("----------Menu Usuário----------");
			System.out.println("1-Cadastrar");
			System.out.println("2-Listar");
			System.out.println("3-Buscar");
			System.out.println("4-Atualizar");
			System.out.println("5-Excluir");
			System.out.println("6-Voltar");
			escolha = sc.nextInt();
			sc.nextLine();

			switch (escolha) {
			case 1: {
				String nome;
				String email;
				System.out.print("Digite o nome do usuário: ");
				nome = sc.nextLine();
				System.out.print("Digite o email do usuário: ");
				email = sc.next();
				try {
					sis.cadastrarUsuario(nome, email);
					System.out.println("Usuário cadastrado com sucesso!");

				} catch (ValidacaoException e) {
					System.err.println(e.getMessage());
				}
				break;
			}
			case 2: {
				System.out.println(String.format("%-5s | %-20s | %-20s |", "ID", "Nome", "Email"));
				for (Usuario usuarios : sis.listarUsuarios()) {
					System.out.println(usuarios);
				}
				break;
			}
			case 3: {
				String nome;
				System.out.print("Digite o nome do usuário: ");
				nome = sc.nextLine();
				try {
					Usuario usuario = sis.buscaUsuario(nome);
					System.out.println("Usuário encontrado: ");
					System.out.println(String.format("%-5s | %-20s | %-20s |", "ID", "Nome", "Email"));
					System.out.println(usuario);
				} catch (BuscaException e) {
					System.err.println(e.getMessage());
				} catch (ValidacaoException e) {
					System.err.println(e.getMessage());
				}
				break;
			}
			case 4: {
				int id;
				System.out.println(String.format("%-5s | %-20s | %-20s |", "ID", "Nome", "Email"));
				for (Usuario usuarios : sis.listarUsuarios()) {
					System.out.println(usuarios);
				}
				System.out.println("digite o Id do usuário que você deseja atualizar: ");
				id = sc.nextInt();
				sc.nextLine();
				System.out.println("O que você deseja alterar: ");
				System.out.println("1-Id");
				System.out.println("2-Nome ");
				System.out.println("3-Email ");
				escolha = sc.nextInt();
				sc.nextLine();
				switch (escolha) {
				case 1: {
					int novoId;
					System.out.print("Novo ID: ");
					novoId = sc.nextInt();
					sc.nextLine();
					try {
						sis.alterarIdUsuario(id, novoId);
						System.out.println("ID alterado com sucesso");
					} catch (ValidacaoException e) {
						System.err.println(e.getMessage());
					}
					break;
				}
				case 2: {
					String novoNome;
					System.out.print("Novo nome: ");
					novoNome = sc.nextLine();
					try {
						sis.alterarNomeUsuario(id, novoNome);
						System.out.println("Nome alterado com sucesso");
					} catch (ValidacaoException e) {
						System.err.println(e.getMessage());
					}
					break;
				}
				case 3: {
					String novoEmail;
					System.out.println("Novo email: ");
					novoEmail = sc.nextLine();
					try {
						sis.alterarEmailUsuario(id, novoEmail);
						System.out.println("Email alterado com sucesso");

					} catch (ValidacaoException e) {
						System.err.println(e.getMessage());
					}
					break;
				}
				default:
					System.err.println("Opção inválida");
				} // switch escolha

				break;
			}
			case 5: {
				System.out.println(String.format("%-5s | %-20s | %-20s |", "ID", "Nome", "Email"));
				for (Usuario usuarios : sis.listarUsuarios()) {
					System.out.println(usuarios);
				}
				System.out.println("Digite o id que da pessoa que você deseja apagar: ");
				int id = sc.nextInt();
				sc.nextLine();

				try {
					sis.excluirUsuario(id);
					System.out.println("Usuário removido com sucesso!");
				} catch (ValidacaoException e) {
					System.err.println(e.getMessage());
				}
				break;
			}
			case 6: {
				break;
			}
			default:
				System.err.println("Opção inválida");
			}
		} while (escolha != 6);
	}

	static void menuLivros(SistemaBiblioteca sis) {
		int escolha;
		do {
			System.out.println("----------Menu Livro----------");
			System.out.println("1-Cadastrar");
			System.out.println("2-Listar");
			System.out.println("3-Buscar");
			System.out.println("4-Atualizar");
			System.out.println("5-Excluir");
			System.out.println("6-Voltar");
			escolha = sc.nextInt();

			switch (escolha) {
			case 1: {
				String nome;
				String autor;
				int ano;
				sc.nextLine();
				System.out.println("Digite o nome do livro: ");
				nome = sc.nextLine();
				System.out.println("Digite o nome do autor do livro: ");
				autor = sc.nextLine();
				System.out.println("Digite o ano do livro: ");
				ano = sc.nextInt();
				sc.nextLine();
				try {
					sis.cadastrarLivro(nome, autor, ano);
					System.out.println("Livro cadastrado com sucesso!");
				} catch (ValidacaoException e) {
					System.out.println(e.getMessage());
				}
				break;
			}
			case 2: {
				System.out.println(String.format("%-5s | %-60s | %-20s | %-20s | %-20s", "ID", "Titulo", "Autor", "Ano",
						"Disponivel"));
				for (Livro livro : sis.listarLivros()) {
					System.out.println(livro);
				}
				break;
			}
			case 3: {
				String titulo;
				System.out.println("Digite o título do livro: ");
				titulo = sc.nextLine().trim();
				try {
					sis.buscaLivro(titulo);
					System.out.println("Livro encontrado : ");
					System.out.println(String.format("%-5s | %-60s | %-20s | %-20s | %-20s", "ID", "Titulo", "Autor",
							"Ano", "Disponivel"));
					System.out.println(sis.buscaLivro(titulo));
				} catch (BuscaException e) {
					System.err.println(e.getMessage());
				} catch (ValidacaoException e) {
					System.err.println(e.getMessage());
				}
				break;
			}
			case 4: {
				int novaEscolha;
				int id;
				System.out.println(String.format("%-5s | %-60s | %-20s | %-20s | %-20s", "ID", "Titulo", "Autor", "Ano",
						"Disponivel"));
				for (Livro livros : sis.listarLivros()) {
					System.out.println(livros);
				}
				
				boolean idExiste;

				do {
				    System.out.println("Digite o ID do livro:");
				    id = sc.nextInt();
				    sc.nextLine();

				    try {
				        idExiste = sis.verificarIdExistenteLivros(id);

				        if (idExiste) {
				            System.out.println("Alterar o livro com o ID: " + id);
				        } else {
				            System.out.println("ID inválido! Tente novamente.");
				        }

				    } catch (Exception e) {
				        System.err.println(e.getMessage());
				        idExiste = false;
				    }

				} while (!idExiste);

				do {
					System.out.println("1-Id");
					System.out.println("2-Título ");
					System.out.println("3-Autor ");
					System.out.println("4-Ano ");
					System.out.println("5-Voltar");
					novaEscolha = sc.nextInt();
					sc.nextLine();

					switch (novaEscolha) {
					case 1: {
						int novoId;
						System.out.print("Novo ID: ");
						novoId = sc.nextInt();
						sc.nextLine();
						try {
							sis.alterarIdLivro(id, novoId);
							System.out.println("ID alterado com sucesso");
							id = novoId;
						} catch (ValidacaoException e) {
							System.err.println(e.getMessage());
						}
						break;
					}
					case 2: {
						String novoTitulo;
						System.out.print("Novo Título: ");
						novoTitulo = sc.nextLine();
						try {
							sis.alterarTituloLivro(id, novoTitulo);
							System.out.println("Título alterado com sucesso");
						} catch (ValidacaoException e) {
							System.err.println(e.getMessage());
						}
						break;
					}
					case 3: {
						String novoAutor;
						System.out.println("Novo autor: ");
						novoAutor = sc.nextLine();
						try {
							sis.alterarAutorLivro(id, novoAutor);
							System.out.println("Autor alterado com sucesso");

						} catch (ValidacaoException e) {
							System.err.println(e.getMessage());
						}
						break;
					}
					case 4: {
						int novoAno;
						System.out.println("Novo ano: ");
						novoAno = sc.nextInt();
						sc.nextLine();
						try {
							sis.alterarAnoLivro(id, novoAno);
						} catch (ValidacaoException e) {
							System.out.println(e.getMessage());
						}
						break;
					}
					case 5 :{
						break;
					}
						
					default:
						System.err.println("Opção inválida");
					} // switch escolha

				} while (novaEscolha != 5);
				break;
			}
			case 5: {
				System.out.println(String.format("%-5s | %-20s | %-20s |", "ID", "Nome", "Email"));
				for (Livro livros : sis.listarLivros()) {
					System.out.println(livros);
				}
				System.out.println("Digite o id do livro que você deseja apagar: ");
				int id = sc.nextInt();
				sc.nextLine();
				try {
					sis.excluirlivro(id);
					System.out.println("Livro excluído com sucesso!");
				} catch (ValidacaoException e) {
					System.err.println(e.getMessage());
				}
			}
				break;
			case 6: {
				break;
			}
			default:
				System.err.println("Opção inváldia");
			}

		} while (escolha != 6);
	}

	static void menuEmprestimos(SistemaBiblioteca sis) {
		int escolha;
		do {
			System.out.println("----------Menu Empréstimos----------");
			System.out.println("1-Realizar empréstimo");
			System.out.println("2-Listar empréstimos");
			System.out.println("3-Buscar empréstimos do Usuário");
			System.out.println("4-Devolver livro");
			System.out.println("5-Voltar");
			escolha = sc.nextInt();
			sc.nextLine();
			switch (escolha) {
			case 1: {
				int idLivro;
				int idUsuario;

				System.out.println(String.format("%-5s | %-20s | %-20s |", "ID", "Nome", "Email"));
				for (Usuario usuarios : sis.listarUsuarios()) {
					System.out.println(usuarios);
				}
				System.out.println("Digite o id do usuário que deseja fazer o empréstimo: ");
				idUsuario = sc.nextInt();
				sc.nextLine();

				System.out.println(String.format("%-5s | %-60s | %-20s | %-20s | %-20s", "ID", "Titulo", "Autor", "Ano",
						"Disponivel"));
				for (Livro livro : sis.listarLivros()) {
					System.out.println(livro);
				}
				System.out.println("Digite o id do Livro que deseja fazer o empréstimo: ");
				idLivro = sc.nextInt();
				System.out.println("Digite o número de dias que ele vai poder ficar com o livro: ");
				int diasEmprestimo = sc.nextInt();
				sc.nextLine();

				try {
					sis.fazerEmprestimo(idLivro, idUsuario, diasEmprestimo);
					System.out.println("Empréstimo realizado com sucesso");
				} catch (ValidacaoException e) {
					System.err.println(e.getMessage());
				} catch (BuscaException e) {
					System.err.println(e.getMessage());
				}
				break;
			}
			case 2: {
				System.out.println(String.format("%-10s | %-30s | %-32s | %-15s | %-15s", "ID-Empréstimo", "Usuario",
						"Livro", "Data Emprestimo", "Data Devolucao"));
				for (Emprestimo emprestimos : sis.listarEmprestimo()) {
					System.out.println(emprestimos);
				}
				break;
			}
			case 3: {
				int id;
				System.out.println("Digite o id do usuario: ");
				id = sc.nextInt();
				sc.nextLine();

				try {
					ArrayList<Emprestimo> emprestimosUsuario = sis.listarEmprestimoUsuario(id);
					System.out.println(String.format("%-10s | %-30s | %-30s | %-15s | %-15s", "ID-Empréstimo",
							"Usuario", "Livro", "Data Emprestimo", "Data Devolucao"));
					for(Emprestimo emprestimo : emprestimosUsuario) {
						System.out.println(emprestimo);
					}

				} catch (ValidacaoException e) {
					System.out.println(e.getMessage());
				}
				break;
			}
			case 4: {
				int idEmprestimo;
				int idUsuario;
				System.out.println(String.format("%-5s | %-20s | %-20s |", "ID", "Nome", "Email"));
				for (Usuario usuarios : sis.listarUsuarios()) {
					System.out.println(usuarios);
				}
				System.out.println("\n\n");
				System.out.println("Digite o ID do usuário: ");
				idUsuario = sc.nextInt();
				System.out.println(String.format("%-10s | %-30s | %-32s | %-15s | %-15s", "ID-Empréstimo", "Usuario",
						"Livro", "Data Emprestimo", "Data Devolucao"));
				try {
					for (Emprestimo emprestimos : sis.listarEmprestimoUsuario(idUsuario)) {
						System.out.println(emprestimos);
					}
				} catch (ValidacaoException e) {
					System.err.println(e.getMessage());
					
				}
				System.out.println("Digite o ID do empréstimo: ");
				idEmprestimo = sc.nextInt();

				try {
					sis.excluirEmprestimo(idEmprestimo);
					System.out.println("Livro devolvido!");
				} catch (ValidacaoException e) {
					System.out.println(e.getMessage());
				}
				break;

			}
			case 5: {
				break;
			}
			default:
				System.err.println("Opção inválida");
			}

		} while (escolha != 5);
	}
}
