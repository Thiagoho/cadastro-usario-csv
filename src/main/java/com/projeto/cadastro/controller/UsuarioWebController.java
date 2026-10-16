package com.projeto.cadastro.controller;

import java.nio.charset.StandardCharsets;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.projeto.cadastro.entities.Usuario;
import com.projeto.cadastro.service.CsvService;
import com.projeto.cadastro.service.UsuarioService;

/**
 * CLASSE: UsuarioWebController
 * 
 * CAMADA: controller (apresentação)
 * 
 * RESPONSIBILIDADE:
 * Receber as requisições HTTP enviadas
 * pelo navegador e encaminhá-las para 
 * os serviços responsáveis.
 * 
 * FUNCIONALIDADES:
 * Exibir o formúlario de cadastro.
 * -Listar usuários cadastrados.
 * -Cadastrar novos usuários.
 * -Atualizar usuários existentes.
 * -Excluir usuários.
 * -Exportar usuários para csv.
 * 
 * IMPORTENTE:
 * O controller não deve concentrar
 * as regras de negócios.
 * 
 * Essas regras pertencem ao usuarioService.
 */


// Identificar a classe como Controller MVC.
// Permitir retornar páginas HTML do Thymeleaf.
@Controller

// Define o caminho principal das requisições.
// Todos os métodos desta classe começam.
// com o endereço /usuarios.
@RequestMapping("/usuarios")
public class UsuarioWebController {
/**
 * DEPENDÊNIAS
 * 
 * UsuarioService:
 * Executa as operações de cadastro,
 * consulta, atualização e exclusão.
 * 
 * CsvService;
 * Gera o conteúdo do arquivo CSV.
 *
 * */
	private final UsuarioService service;
	private final CsvService csvService;
/**
 * CONSTRUTOR
 * 
 * O Spring realize a injeção de
 * dependências automaticamente.
 * 
 * Isso permite utilizar os serviços 
 * sem criar objetos manualmente com new.
 */
	public UsuarioWebController(UsuarioService service, CsvService csvService) {

		this.service = service;
		this.csvService = csvService;
	}

	/**
	 * MÉTODO: listar
	 * 
	 * ROTA: GET /usuarios
	 * 
	 * RESPONSABILIDADE:
	 * Exibir a página principal do sistema.
	 * do Controller para o Thymeleaf.
	 */
	@GetMapping
	public String listar(Model model) {
		// Consulta os usuários no banco.
		model.addAttribute("usuarios", service.listarTodos());
		
		// Consulta a quantidade de usuários.
		model.addAttribute("total", service.contarUsuarios());
	
		// Cria um objeto vazio para receber
		// os dados dos formulário de cadastro.
		model.addAttribute("usuario", new Usuario());

		// Retorno o arquivo usuarios.html
		// Localizado na pasta templates.
		return "usuarios";
	}


	/**
	 * MÉTODO: cadastrar
	 * 
	 * ROTA: POST /usuarios/salvar
	 * 
	 * RESPONSABILIDADE:
	 * Receber os dados do formulário
	 * e solicitar o cadastro.
	 * 
	 * @ModelAttribute:
	 * Converte os campos envidos pelo
	 * formulário em um objeto Usuario.
	 * 
	 * REGRA DE NEGÓCIO:
	 * O UsuárioService verifica se o 
	 * e-mail já está cadastrado.
	 */
	@PostMapping("/salvar")
	public String cadastrar(@ModelAttribute Usuario usuario) {
		
		// Encaminha os dados para o Service.
		service.cadastrar(usuario);
		
		// Redireciona para a página principal.
		return "redirect:/usuarios";
	}

	
	
	/**
	 * 	MÉTODO: exportar-csv
	 * 
	 * ROTA: GET /usuarios/exportar-csv
	 * 
	 * O conteúdo do arquivo é produzido
	 * pela classe CsvService.
	 */
	@GetMapping("/exportar-csv")
	public ResponseEntity<byte[]> exportarCsv() {
		// Solicita a geração do CSV.
	    String csv = csvService.gerarCsv();

	    // Converte o conteúdo para bytes UTF-8.
	    // O BOM auxiliar alguns programas, 
	    // como o Excel, a reconhecer acentes.
	    byte[] arquivo = ("\uFEFF" + csv)
	            .getBytes(StandardCharsets.UTF_8);

	    // Monta a resposta HTTP para download.
	    return ResponseEntity.ok()
	    		// Define o nome do arquivo.
	            .header(
	                HttpHeaders.CONTENT_DISPOSITION,
	                "attachment; filename=\"usuarios.csv\""
	            )
	            // Define o formato da resposta.
	            .contentType(
	                MediaType.parseMediaType("text/csv;charset=UTF-8")
	            )
	            // Envia os bytes do CSV.
	            .body(arquivo);
	}

	/**
	 * MÉTODO: atualizar
	 * 
	 * ROTA: POST /usuarios/atulizar/{id}
	 * 
	 * RESPONSABILIDADE:
	 * Receber os novos dados de uma usuário.
	 * 
	 * @PathVariable:
	 * Obtém o ID informado na URL.
	 * 
	 * @ModelAttribute:
	 * Recebe os dados do formulário.
	 * 
	 * REGRA DE NEGÓCIO:
	 * O UsuarioService verifica se o 
	 * usuário existe e se o novo e-mail
	 * já pertence a outro cadastro.
	 */
	@PostMapping("/atualizar/{id}")
	public String atualizar(@PathVariable Long id, @ModelAttribute Usuario usuario) {

		// Socilita a atualização do usuário.
		service.atualizar(id, usuario);

		// Retorna à página de listagem.
		return "redirect:/usuarios";
	}

	/**
	 * MÉTODO: excluir
	 * 
	 * ROTA: POST /usuarios/excluir/{id}
	 * 
	 * RESPONSABILIDADE: 
	 * Solicitar a exclusão de um usuário.
	 * 
	 * O ID identifica qual registro
	 * deverá ser removido.
	 * 
	 * REGRA DE NEGÓCIO:
	 * O Service consulta o usuario
	 * antes de realizar a exclusão.
	 */
	@PostMapping("/excluir/{id}")
	public String excluir(@PathVariable Long id) {

		// Solicita a exclusão ao Service.
		service.excluir(id);
		
		// Atualiza a página por redirecionamento.
		return "redirect:/usuarios";
	}
}