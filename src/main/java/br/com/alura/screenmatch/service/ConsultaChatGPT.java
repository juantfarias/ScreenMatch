package br.com.alura.screenmatch.service;


import com.theokanning.openai.OpenAiHttpException;
import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.service.OpenAiService;

public class ConsultaChatGPT {
    public static String obterTraducao(String texto) {
        try {
            OpenAiService service = new OpenAiService("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");

            CompletionRequest requisicao = CompletionRequest.builder()
                    .model("gpt-3.5-turbo-instruct")
                    .prompt("traduza para o português o texto: " + texto)
                    .maxTokens(500)
                    .temperature(0.7)
                    .build();

            var resposta = service.createCompletion(requisicao);
            return resposta.getChoices().get(0).getText();
        } catch (OpenAiHttpException e) {
            if (e.getMessage().contains("You exceeded your current quota")) {
                System.out.println("Limite de cota excedido. Verifique seu plano e detalhes de faturamento.");
                // Lógica adicional para tratar o erro, como notificar o usuário ou registrar o evento
                return "Erro: Limite de cota excedido.";
            } else {
                // Tratamento de outros tipos de exceções
                e.printStackTrace();
                return "Erro: Ocorreu um problema ao tentar obter a tradução.";
            }
        } catch (Exception e) {
            // Tratamento de outras exceções não esperadas
            e.printStackTrace();
            return "Erro: Ocorreu um problema inesperado.";
        }
    }
}
