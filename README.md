# Sistema de Biblioteca (Console)

Sistema de gerenciamento de biblioteca em Java puro, executado no terminal. Permite cadastrar usuários e livros, realizar empréstimos, registrar devoluções com cálculo de multa por atraso e consultar o histórico de empréstimos.

Projeto de estudo: versão do **Capítulo 1 (Java: revisão direcionada)** da apostila de Back-end Java Júnior. Nas próximas etapas ele evolui para Streams/Optional/Records, Maven, API REST com Spring Boot e PostgreSQL.

## Tecnologias

- Java 16 ou superior (recomendado JDK 21 LTS)
- `java.time` (`LocalDate`, `ChronoUnit`) para datas e prazos
- Collections (`ArrayList`)
- Git e GitHub

## Funcionalidades

- **Usuários:** cadastrar, listar, buscar por nome, atualizar (nome e e-mail) e excluir.
- **Livros:** cadastrar, listar, buscar por título, atualizar (título, autor e ano) e excluir.
- **Empréstimos:** realizar empréstimo, listar todos, listar por usuário e devolver livro.
- Dados de demonstração carregados na inicialização (4 usuários, 8 livros e 4 empréstimos).

## Regras de negócio

| Regra | Detalhe |
|---|---|
| IDs | Gerados automaticamente e nunca alterados depois de criados |
| Nome, título e autor | Mínimo de 3 caracteres (espaços nas pontas são ignorados) |
| E-mail | Precisa conter `@` |
| Disponibilidade | Livro emprestado fica indisponível; ao devolver, volta a ficar disponível |
| Limite de empréstimos | No máximo **3 empréstimos ativos** por usuário |
| Prazo | Informado em dias no momento do empréstimo (data prevista = hoje + dias) |
| Multa | **R$ 1,50 por dia de atraso**, calculada na devolução |
| Histórico | Devolver não apaga o empréstimo: ele passa para o status `DEVOLVIDO` |
| Exclusão | Não é possível excluir usuário ou livro com empréstimo **ativo** |
| Devolução repetida | Devolver um empréstimo já devolvido é recusado |

## Como executar

Pré-requisito: JDK 16 ou superior instalado (`java -version`).

Na pasta que contém os pacotes `Classes`, `Excecoes` e `Principal`:

```bash
# compilar
javac -d bin Classes/*.java Excecoes/*.java Principal/*.java

# executar
java -cp bin Principal.Main
```

No IntelliJ ou Eclipse, basta executar a classe `Principal.Main`.

## Como usar o menu

```
1-Usuários      -> cadastrar, listar, buscar, atualizar, excluir
2-Livros        -> cadastrar, listar, buscar, atualizar, excluir
3-Empréstimos   -> realizar, listar, buscar por usuário, devolver
4-Encerrar programa
```

Exemplo de fluxo: listar empréstimos (opção 3 > 2), devolver o empréstimo 1 (3 > 4) e listar novamente para ver o status `Devolvido` e a multa, se houver atraso.

## Estrutura do projeto

```
Classes/
  Usuario.java, Livro.java, Emprestimo.java, StatusEmprestimo.java
Excecoes/
  ValidacaoException.java, BuscaException.java, LimiteEmprestimosException.java
Principal/
  Main.java                 -> menus e entrada de dados
  SistemaBiblioteca.java    -> regras de negócio (Singleton)
```

## Decisões técnicas

- **Encapsulamento:** atributos `private`, `id` como `final` e métodos que expressam intenção (`Livro.emprestar()`, `Livro.devolver()`, `Emprestimo.devolver()`) no lugar de setters genéricos.
- **Construtores privados com `getInstance`:** validam os dados antes de criar o objeto, garantindo estado sempre consistente.
- **`equals` e `hashCode` por `id`** nas entidades.
- **Singleton** em `SistemaBiblioteca`, que concentra as regras; o `Main` cuida apenas de menu e entrada.
- **Exceções próprias (checked):** `ValidacaoException`, `BuscaException` e `LimiteEmprestimosException`, tratadas no `Main`.
- **Histórico preservado:** o empréstimo tem `StatusEmprestimo` (`ATIVO`, `DEVOLVIDO`, `ATRASADO`), data prevista e data real de devolução.
- **Listas protegidas:** `listarX()` devolve uma cópia, sem expor as coleções internas.

## Próximos passos

- Trocar `double` por `BigDecimal` no cálculo da multa.
- Usar `Map` para busca por ID e reduzir repetição de código.
- Atualizar o status para `ATRASADO` automaticamente.
- Renomear pacotes para `model`, `service`, `exception` e `ui`.
- Cap. 2: Streams, `Optional`, records e menu de relatórios.
- Migrar para Maven, testes com JUnit e depois API REST com Spring Boot.

## Autor

João Vitor Alves Silva — [LinkedIn](https://www.linkedin.com/) · [GitHub](https://github.com/)
