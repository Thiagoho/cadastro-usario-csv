
package com.projeto.cadastro.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.projeto.cadastro.entities.Usuario;
import com.projeto.cadastro.repository.UsuarioRepository;

/**
 * CLASSE: UsuarioService
 *
 * CAMADA: Service (Regras de Negócio)
 *
 * RESPONSABILIDADE:
 * Gerenciar as operações relacionadas
 * aos usuários cadastrados no sistema.
 *
 * FUNCIONALIDADES:
 * 1. Cadastrar usuários.
 * 2. Impedir e-mails duplicados.
 * 3. Listar usuários.
 * 4. Buscar usuários pelo ID.
 * 5. Atualizar informações.
 * 6. Excluir usuários.
 * 7. Contar usuários cadastrados.
 * 8. Verificar a existência de e-mails.
 *
 * Esta classe faz a comunicação entre
 * o Controller e o Repository.
 *
 * IMPORTANTE:
 * As regras de negócio são executadas
 * antes de solicitar alterações no banco.
 */

// Identifica esta classe como um componente
// de serviço gerenciado pelo Spring.
@Service
public class UsuarioService {

    /**
     * DEPENDÊNCIA: UsuarioRepository
     *
     * Responsável pelas operações
     * de persistência no banco MySQL.
     *
     * final:
     * Impede a reatribuição da referência
     * após a inicialização.
     */
    private final UsuarioRepository repository;

    /**
     * CONSTRUTOR
     *
     * O Spring injeta automaticamente
     * uma implementação de UsuarioRepository.
     *
     * Essa abordagem é conhecida como
     * injeção de dependência por construtor.
     */
    public UsuarioService(UsuarioRepository repository) {
        this.repository = repository;
    }

    /**
     * MÉTODO: cadastrar
     *
     * RESPONSABILIDADE:
     * Cadastrar um novo usuário no sistema.
     *
     * REGRA DE NEGÓCIO:
     * Não permitir o cadastro quando o
     * e-mail informado já estiver registrado.
     *
     * @param usuario Objeto com os dados
     *                recebidos do formulário.
     *
     * @return Usuario salvo no banco.
     *
     * @throws IllegalArgumentException
     *         quando o e-mail já existe.
     */
    public Usuario cadastrar(Usuario usuario) {

        // Consulta se o e-mail já existe.
        if (repository.existsByEmail(usuario.getEmail())) {

            // Interrompe o cadastro caso
            // o e-mail esteja registrado.
            throw new IllegalArgumentException(
                "E-mail já cadastrado!"
            );
        }

        // Salva o usuário no banco de dados.
        return repository.save(usuario);
    }

    /**
     * MÉTODO: listarTodos
     *
     * RESPONSABILIDADE:
     * Consultar todos os usuários cadastrados.
     *
     * @return Lista de objetos Usuario.
     *
     * Se não existirem registros, retorna
     * uma lista vazia.
     */
    public List<Usuario> listarTodos() {

        // Executa a consulta através do JPA.
        return repository.findAll();
    }

    /**
     * MÉTODO: buscarPorId
     *
     * RESPONSABILIDADE:
     * Localizar um usuário pelo identificador.
     *
     * REGRA DE NEGÓCIO:
     * Se o usuário não existir, lançar
     * uma exceção com mensagem explicativa.
     *
     * @param id Identificador do usuário.
     *
     * @return Usuário encontrado.
     *
     * @throws IllegalArgumentException
     *         quando o ID não é encontrado.
     */
    public Usuario buscarPorId(Long id) {

        // findById retorna um Optional<Usuario>.
        return repository.findById(id)

            // Se estiver vazio, lança exceção.
            .orElseThrow(() ->
                new IllegalArgumentException(
                    "Usuário não encontrado!"
                )
            );
    }

    /**
     * MÉTODO: atualizar
     *
     * RESPONSABILIDADE:
     * Atualizar os dados de um usuário.
     *
     * REGRAS DE NEGÓCIO:
     *
     * 1. O usuário precisa existir.
     *
     * 2. O usuário pode manter seu
     *    próprio e-mail.
     *
     * 3. Um novo e-mail não pode
     *    pertencer a outro cadastro.
     *
     * 4. Após as verificações, os dados
     *    são atualizados e salvos.
     *
     * @param id Identificador do usuário.
     * @param dados Novas informações.
     *
     * @return Usuário atualizado.
     */
    public Usuario atualizar(Long id, Usuario dados) {

        // Busca o usuário existente.
        // Se não existir, lança exceção.
        Usuario usuario = buscarPorId(id);

        // Verifica duas condições:
        //
        // 1. O e-mail foi alterado?
        //
        // 2. O novo e-mail já existe no banco?
        //
        // A exceção ocorre somente quando
        // as duas condições são verdadeiras.
        if (!usuario.getEmail().equals(dados.getEmail())
                && repository.existsByEmail(dados.getEmail())) {

            throw new IllegalArgumentException(
                "E-mail já cadastrado!"
            );
        }

        // Atualiza os atributos do objeto.
        usuario.setNome(dados.getNome());
        usuario.setEmail(dados.getEmail());
        usuario.setTelefone(dados.getTelefone());

        // Persiste as alterações no MySQL.
        return repository.save(usuario);
    }

    /**
     * MÉTODO: excluir
     *
     * RESPONSABILIDADE:
     * Excluir um usuário cadastrado.
     *
     * REGRA DE NEGÓCIO:
     * Verificar se o usuário existe
     * antes de solicitar a exclusão.
     *
     * @param id Identificador do usuário.
     */
    public void excluir(Long id) {

        // Localiza o usuário.
        // Se não existir, lança exceção.
        Usuario usuario = buscarPorId(id);

        // Exclui o registro encontrado.
        repository.delete(usuario);
    }

    /**
     * MÉTODO: contarUsuarios
     *
     * RESPONSABILIDADE:
     * Consultar a quantidade total
     * de usuários cadastrados.
     *
     * @return Quantidade de registros.
     */
    public long contarUsuarios() {

        return repository.count();
    }

    /**
     * MÉTODO: emailExiste
     *
     * RESPONSABILIDADE:
     * Consultar a existência de um e-mail.
     *
     * @param email E-mail pesquisado.
     *
     * @return true quando encontrado.
     * @return false quando não encontrado.
     *
     * OBSERVAÇÃO:
     * Este método apenas consulta o banco.
     * Ele não cadastra ou altera usuários.
     */
    public boolean emailExiste(String email) {

        return repository.existsByEmail(email);
    }
}
