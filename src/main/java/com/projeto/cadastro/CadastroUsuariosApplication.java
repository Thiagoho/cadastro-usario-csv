package com.projeto.cadastro;
//Importa a classe reponsavel por iniciar 
//uma aplicação Spring Boot.
import org.springframework.boot.SpringApplication;


//Import a anotação que identifica a 
// classe principal de aplicação.
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Classe: cadastroUsuarioApplication
 * 
 * RESPONSABILIDADE:
 * Incializar o sistema de Cadastro de Usuários.
 * 
 * Esta classe é o ponto de entrada da aplicação.
 * 
 * o spring boot realiza a configuração inicial 
 * e prepara os componentes necessários para o funcionamentos do sistema.
 * 
 * IMPORTANTE:
 * Esta classe não contém regra de negócio.
 * As regras de cadastro, atualização, eclusão
 * e consulta ficam nao usuarioService.*/
@SpringBootApplication
public class CadastroUsuariosApplication {
	/**
	 * MÉTODO:
	 * RESPONSABILIDADE:
	 * Iniciar a execução da aplicação Java.
	 * 
	 * @param args arguemts recebidos
	 * 				pela linha de comando.
	 * */

    public static void main(String[] args) {
    	// Inicializa o spring boot.
    	// 
    	// Durante a inicialização, o spring:
    	// 1. Carrega as configurações da aplicação.
    	// 2. Identifica os componentes gerenciados.
    	// 3. Prepara os Controllers e Services.
    	// 4.configura a integração como o banco
    	// conforme application.properties.
    	// 5. Incializa o servidor web embutido.
        SpringApplication.run(
            CadastroUsuariosApplication.class, args
        );
    }
}