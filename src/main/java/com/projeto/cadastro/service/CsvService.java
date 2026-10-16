package com.projeto.cadastro.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.projeto.cadastro.entities.Usuario;

/**
 * 
 * CLASSE: CsvSerice
 * 
 * CAMADA: Service
 * 
 * RESPONSABILIDADE:
 * Gerar o conteúdo de um arquivo CSV
 * utilizando do usuários cadastrados.
 * 
 * FUNCIONALIDADES:
 * 1.Consultar os usuários existentes.
 * 2.Criar o conteúdo do arquivo CSV.
 * 3.Adicionar o cabaçalho.
 * 4.Percorrer os campos corretamente.
 * 5.Formatar os campos corretamente.
 * 6.Tratar valores nulos e aspas.
 * 7.Reduzir riscos de execuções de formulários
 * ao abrir os dados em planilhas.
 * 
 * 
 * IMPORTANTE:
 * Esta classe gera o conteúdo CSV.
 * O download é preparado e enviado 
 * pelo UsuarioWebController.
 */
@Service
public class CsvService {
	/**
	 * DEPENDÊNCIA: UsuarioService
	 * 
	 * Permitir consultar os usuários
	 * cadastrados no sistema.
	 * 
	 * */
    private final UsuarioService usuarioService;
    /**
     * CONSTRUTOR:
     * 
     * O Spring injeta automaticamente
     * o UsuarioService.
     * 
     * Dessa forma, não precisamos criar
     * manualmente uma instãncia do Service.
     * 
     * */
    public CsvService(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }
    
    /**
     * MÉTODO: gerarCSV
     * 
     * RESPONSABILIDADE:
     * Gerar um texto no formato CSV contendo 
     * nome, e-mail e telefone dos usuários.
     * 
     * ETAPAS:
     * 1.Consultar os usuários.
     * 2.Criar o StringBuilder.
     * 3.Adicionar o cabelho.
     * 4.Percorrer a lista.
     * 5.Formatar cada campo.
     * 6.Retornar o  CSV completo.
     * 
     * 
     * @retur String contendo os dados
     * 			formatados para exportação.
     */ 
    public String gerarCsv() {
    	
    	// Busca todos dos usuários cadastrados.
        List<Usuario> usuarios = usuarioService.listarTodos();

        // Cria um objeto para montar o CSV
        // de forma eficiente.
        StringBuilder csv = new StringBuilder();

        // Cabeçalho original do arquivo.
        // OBSERVAÇÃO:
        // Este cabeçalho utiliza tabulações.
        // enquanto os dados usam ponto e vírgula.
        csv.append("NOME :\t : EMAIL : \t TELEFONE :\n");

        // Percorre os usuários cadastrados.
        for (Usuario usuario : usuarios) {
        	// Adiciona o nome formatado.
            csv.append(formatarCampo(usuario.getNome())).append(";")
            // Adiciona o e-mail formatado.  
            .append(formatarCampo(usuario.getEmail())).append(";")
            // Adiciona o telefone formatado.   
            .append(formatarCampo(usuario.getTelefone())).append("\n");
        }
        // Converte o StringBuilder em String
        return csv.toString();
    }

    /**
     * MÉTODO: formatarCompo
     * 
     * MODIFICADOR: private
     * 
     * RESPONSABILIDADE:
     * Preparar um valor para ser inserido
     * em um coluna do arquivo CSV.
     * 
     * TRATAMENTO:
     * -Valores nulos.
     * -Caracteres iniciais associados
     * à interpretação de fórmulas.
     * -Aspas duplas presentes nos dados.
     * 
     * @param valor texto que será formatado.
     * 
     * @return campo formatado entre aspas.
     */
   
    private String formatarCampo(String valor) {
    	
    	
    	// Se o valor for nulo, retorna 
    	// um campo vazio entre aspas.
        if (valor == null) {
            return "\"\"";
        }

        // Verifica se o premeiro caractere
        // pode representar um fórmula
        // em programas de planilhas.
        if (!valor.isEmpty()
                && "=+-@\t\r\n".indexOf(valor.charAt(0)) >= 0) {
            
        	// Adicionar apóstrofo ao iniicio
        	// para reduzir o risco de o valor
        	// ser interpretado como fórmula.
        	valor = "'" + valor;
        }

        // Substitui cada aspa dupla interna
        // por duas aspas duplas.
        //
        // Depois envolve o campo entre aspas,
        // preservando o formato CSV.
        return "\"" + valor.replace("\"", "\"\"") + "\"";
    }
}