package com.projeto.cadastro.repository;

// Importa a interface JpaRepository
// fornecida pelo Spring Data JPA.

// Importa a entidade Usuario, que representa
// a tabela "Usuarios" no banco de dados.
import com.projeto.cadastro.entities.Usuario;

/**
 * INTERFACE: UsuarioRepository
 * 
 * CAMADA: Repository (Persistência)
 * 
 * RESPONSABILIDADE: 
 * Realizar o acesso aos dos dados dos usuários
 * armazenados na banco MYSQL.
 * 
 * O Spring Data JPA fornece automaticamente
 * operações básicas de persistência, como:
 * 
 * - Salvar um usuáio.
 * - Buscar um usuário pelo ID.
 * - Listar todos os usuários.
 * - Atualizar um usuário
 * - Excluir um usuário.
 * - Contar os usuários cadastrados.
 * 
 * IMPORTANTE:
 * Esta interface não implementa as regras
 * de negócio do sistema.
 * 
 * As validações e decisões relacionadas
 * ao cadastro ficam no UsuariosService.
 */

 /**
  * JpaRepository<Usuario, Long>
  * 
  * Usuario: 
  * Define e entidade gerenciada.
  * 
  * 
  * Long: Define o tipo da chave primário (ID).
  * 
  * O Spring Data JPA cria uma implementação
  * dessa interface durante a execução.
  */
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	/**
	 * MÉTODO: existsByEmail
	 * 
	 * RESPONSABILIDADE:
	 * Verificar se existe algum usuário
	 * cadastrado com o e-mail informado.
	 * 
	 * O Spring Data JPA interpreta o nome 
	 * do método e gera a consulta necessária.
	 * 
	 * @param email E-mail que será consultado
	 * 
	 * @return true;
	 * 			O e-mail já existe no banco.
	 * @return false;
	 * 			O e-mail não foi no encontrado.
	 * 
	 * REGRA DE NEGÓCIO:
	 * O UsuarioService utiliza esse método
	 * para decidir se permite ou rejeita determinado cadastro.
	 */
	boolean existsByEmail(String email);
}
