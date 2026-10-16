package com.projeto.cadastro.entities;

//Importações da API Jakarta Persistence (JPA).
//São utilizadas para mapear a classe Java
//para uma tabela do banco de dados.
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * CLASSE: Usuario
 * 
 * CAMADA: Entity (Entidade)
 * 
 * RESPONSABILIDADE: Representar um usuário cadastrado no sistema.
 * 
 * Esta classe define os atributos que serão armazenado na tabela "Usuarios" do
 * MYSQL.
 * 
 * 
 * Os dados são manipulados pelo usuarioService e persistidos por meio do
 * UsuarioRepository.
 * 
 * IMPORTENTE: Esta entidade não executa diretamente operações SQL ou regras de
 * cadastro.
 */
// Informa ao JPA que esta classe representa
// uma entidade persistente.

@Entity
@Table(name = "usuarios")
public class Usuario {

	/**
	 * ATRIBUTO: id
	 * 
	 * Identificador único de cada usuário.
	 * 
	 * @Id: Define a chave primária da tabela.
	 * 
	 * @GeneratedValue: Configura a geração automática do ID.
	 * 
	 *                  IDENTITY: Utiliza o mecanismo de geração de identificadores
	 *                  do banco de dados.
	 * 
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/**
	 * ATRIBUTO: nome
	 * 
	 * Armazena o nome do usuário.
	 */
	private String nome;

	/**
	 * ATRIBUTO: email
	 * 
	 * Armezana o endereço de e-mail
	 * 
	 * No projeto, o UsuarioService verifica se o e-mail já está cadastrado antes de
	 * inserir um novo usuário.
	 */
	private String email;
	/**
	 * ATRIBUTO: telefone
	 * 
	 * Armazena o telefone informado pelo usuário no formulário.
	 */
	private String telefone;

	/**
	 * CONSTRUTOR SEM PARÂMENTROS
	 * 
	 * Necessário para que o JPA consiga instanciar a entidade.
	 */
	public Usuario() {
	}

	/**
	 * 
	 * CONSTRUTOR COM PARÂMETROS
	 * 
	 * Permite criar um objeto usuario informando nome, e-mail e telefone.
	 * 
	 * O ID não é informado porque será gerado pelo banco do dados.
	 */
	public Usuario(String nome, String email, String telefone) {
		this.nome = nome;
		this.email = email;
		this.telefone = telefone;
	}

	/**
	 * Retorna o identificador do usuario.
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Retorno nome cadastrado.
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * Atualiza o nome do objeto usuario.
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}

	/**
	 * Retorno o e-mail cadastrado.
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Atualiza o e-mail do objeto Usuario.
	 * 
	 * A verificação de duplicidade é feita no UsuarioService, não neste método.
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Retorno o telefone cadastrado.
	 */
	public String getTelefone() {
		return telefone;
	}

	/**
	 * Atualiza o telefone do objeto Usuario.
	 */
	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	/**
	 * 
	 * MÉTODO: toString()
	 * 
	 * Retorna um represetação textual dos atributos do objeto Usuario.
	 * 
	 * Útil durante testes e depuração.
	 * 
	 * ATENÇÃO: Come contém e-mail e telefone, evite registrar esse retorno
	 * indiscriminadamente em logs.
	 * 
	 */
	@Override
	public String toString() {
		return "Usuario [id=" + id + ", nome=" + nome + ", email=" + email + ", telefone=" + telefone + "]";
	}
}
