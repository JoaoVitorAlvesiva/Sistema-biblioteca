package Principal;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import Classes.Emprestimo;
import Classes.Livro;
import Classes.Usuario;
import Excecoes.BuscaException;
import Excecoes.LimiteEmprestimosException;
import Excecoes.ValidacaoException;

public class Main {

	static Scanner sc = new Scanner(System.in);

	public static void main(String[] args) throws ValidacaoException {
		SistemaBiblioteca sis = SistemaBiblioteca.getInstance();

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
			escolha = lerInt();
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
			escolha = lerInt();

			switch (escolha) {
			case 1: {
				String nome;
				String email;
				System.out.print("Digite o nome do usuário: ");
				nome = sc.nextLine();
				System.out.print("Digite o email do usuário: ");
				email = sc.nextLine();
				try {
					sis.cadastrarUsuario(nome, email);
					System.out.println("Usuário cadastrado com sucesso!");

				} catch (ValidacaoException e) {
					System.err.println(e.getMessage());
				}
				break;
			}
			case 2: {
				imprimirUsuarios(sis.listarUsuarios());
				break;
			}
			case 3: {
				String nome;
				System.out.print("Digite o nome do usuário: ");
				nome = sc.nextLine();
				try {
					Usuario usuario = sis.buscaUsuario(nome);
					System.out.println("Usuário encontrado: ");
					imprimirUsuarios(List.of(usuario));
				} catch (BuscaException e) {
					System.err.println(e.getMessage());
				} catch (ValidacaoException e) {
					System.err.println(e.getMessage());
				}
				break;
			}
			case 4: {
				int id;
				imprimirUsuarios(sis.listarUsuarios());
				System.out.println("digite o Id do usuário que você deseja atualizar: ");
				id = lerInt();
				System.out.println("O que você deseja alterar: ");
				System.out.println("1-Nome ");
				System.out.println("2-Email ");
				int novaEscolha = lerInt();
				switch (novaEscolha) {
				case 1: {
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
				case 2: {
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
				imprimirUsuarios(sis.listarUsuarios());
				System.out.println("Digite o id que da pessoa que você deseja apagar: ");
				int id = lerInt();

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
			escolha = lerInt();

			switch (escolha) {
			case 1: {
				String nome;
				String autor;
				int ano;
				System.out.println("Digite o nome do livro: ");
				nome = sc.nextLine();
				System.out.println("Digite o nome do autor do livro: ");
				autor = sc.nextLine();
				System.out.println("Digite o ano do livro: ");
				ano = lerInt();
				try {
					sis.cadastrarLivro(nome, autor, ano);
					System.out.println("Livro cadastrado com sucesso!");
				} catch (ValidacaoException e) {
					System.out.println(e.getMessage());
				}
				break;
			}
			case 2: {
				imprimirLivros(sis.listarLivros());
				break;
			}
			case 3: {
				String titulo;
				System.out.println("Digite o título do livro: ");
				titulo = sc.nextLine().trim();
				try {
					Livro livro = sis.buscaLivro(titulo);
					System.out.println("Livro encontrado : ");
					imprimirLivros(List.of(livro));
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
				imprimirLivros(sis.listarLivros());
				
				boolean idExiste;

				do {
				    System.out.println("Digite o ID do livro:");
				    id = lerInt();

				    try {
				        idExiste = sis.verificarIdExistenteLivros(id);

				        if (idExiste) {
				            System.out.println("Alterando o livro com o ID: " + id);
				        } else {
				            System.out.println("ID inválido! Tente novamente.");
				        }

				    } catch (Exception e) {
				        System.err.println(e.getMessage());
				        idExiste = false;
				    }

				} while (!idExiste);

				do {
					System.out.println("1-Título ");
					System.out.println("2-Autor ");
					System.out.println("3-Ano ");
					System.out.println("4-Voltar");
					novaEscolha = lerInt();

					switch (novaEscolha) {
					case 1: {
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
					case 2: {
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
					case 3: {
						int novoAno;
						System.out.println("Novo ano: ");
						novoAno = lerInt();
						try {
							sis.alterarAnoLivro(id, novoAno);
							System.out.println("Ano alterado com sucesso");
						} catch (ValidacaoException e) {
							System.out.println(e.getMessage());
						}
						break;
					}
					case 4 :{
						break;
					}
						
					default:
						System.err.println("Opção inválida");
					} // switch escolha

				} while (novaEscolha != 4);
				break;
			}
			case 5: {
				imprimirLivros(sis.listarLivros());
				System.out.println("Digite o id do livro que você deseja apagar: ");
				int id = lerInt();
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
				System.err.println("Opção inválida");
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
			escolha = lerInt();
			switch (escolha) {
			case 1: {
				int idLivro;
				int idUsuario;

				imprimirUsuarios(sis.listarUsuarios());
				System.out.println("Digite o id do usuário que deseja fazer o empréstimo: ");
				idUsuario = lerInt();

				imprimirLivros(sis.listarLivros());
				System.out.println("Digite o id do Livro que deseja fazer o empréstimo: ");
				idLivro = lerInt();
				System.out.println("Digite o número de dias que ele vai poder ficar com o livro: ");
				int diasEmprestimo = lerInt();

				try {
					sis.fazerEmprestimo(idLivro, idUsuario, diasEmprestimo);
					System.out.println("Empréstimo realizado com sucesso");
				} catch (ValidacaoException e) {
					System.err.println(e.getMessage());
				} catch (BuscaException e) {
					System.err.println(e.getMessage());
				} catch (LimiteEmprestimosException e) {
					System.err.println(e.getMessage());
                }
				break;
			}
			case 2: {
				imprimirEmprestimos(sis.listarEmprestimo());
				break;
			}
			case 3: {
				int id;
				System.out.println("Digite o id do usuario: ");
				id = lerInt();

				try {
					imprimirEmprestimos(sis.listarEmprestimoUsuario(id));
				} catch (ValidacaoException e) {
					System.out.println(e.getMessage());
				}
				break;
			}
			case 4: {
				int idEmprestimo;
				int idUsuario;
				imprimirUsuarios(sis.listarUsuarios());
				System.out.println("\n\n");
				System.out.println("Digite o ID do usuário: ");
				idUsuario = lerInt();
				try {
					imprimirEmprestimos(sis.listarEmprestimoUsuario(idUsuario));
				} catch (ValidacaoException e) {
					System.err.println(e.getMessage());
				}
				System.out.println("Digite o ID do empréstimo: ");
				idEmprestimo = lerInt();
				try {
					double multa = sis.devolverLivro(idEmprestimo);
					System.out.println("Livro devolvido!");
					if (multa > 0) {
						System.out.printf("Multa por atraso: R$ %.2f%n", multa);
					}
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
	static int lerInt() {
		while (true) {
			try {
				int valor = sc.nextInt();
				sc.nextLine();
				return valor;
			} catch (InputMismatchException e) {
				sc.nextLine();
				System.out.print("Digite um número válido: ");
			}
		}
	}

	static void imprimirUsuarios(List<Usuario> lista) {
		System.out.println(String.format("%-5s | %-20s | %-20s |", "ID", "Nome", "Email"));
		for (Usuario usuario : lista) {
			System.out.println(usuario);
		}
	}

	static void imprimirLivros(List<Livro> lista) {
		System.out.println(String.format("%-5s | %-60s | %-20s | %-20s | %-20s", "ID", "Titulo", "Autor", "Ano",
				"Disponivel"));
		for (Livro livro : lista) {
			System.out.println(livro);
		}
	}

	static void imprimirEmprestimos(List<Emprestimo> lista) {
		System.out.println(String.format("%-13s | %-30s | %-32s | %-15s | %-15s | %-10s | %s",
				"ID-Empréstimo", "Usuario", "Livro", "Data Emprestimo", "Data Prevista", "Status", "Multa"));
		for (Emprestimo emprestimo : lista) {
			System.out.println(emprestimo);
		}
	}
}
