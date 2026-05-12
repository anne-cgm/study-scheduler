package studyscheduler.service;

import studyscheduler.model.Weather;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class WeatherService {

    private static final String API_KEY = "ae1622b04ddb72ebe4578ed1d5ff99a1";

    public Weather buscarClima() {

        String url =
                "https://api.openweathermap.org/data/2.5/weather?q=Brasilia&appid="
                        + API_KEY
                        + "&units=metric&lang=pt_br";

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        try {

            HttpResponse<String> response =
                    client.send(request, HttpResponse.BodyHandlers.ofString());

            JsonObject json =
                    JsonParser.parseString(response.body()).getAsJsonObject();

            String descricao =
                    json.getAsJsonArray("weather")
                            .get(0)
                            .getAsJsonObject()
                            .get("description")
                            .getAsString();

            double temperatura =
                    json.getAsJsonObject("main")
                            .get("temp")
                            .getAsDouble();

            String mensagem = gerarMensagem(descricao, temperatura);

            return new Weather(descricao, temperatura, mensagem);

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();

            return new Weather(
                    "Clima indisponível",
                    0,
                    "Não foi possível carregar o clima"
            );
        }
    }

    private String gerarMensagem(String descricao, double temperatura) {

        descricao = descricao.toLowerCase();

        if (descricao.contains("chuva")) {
            return "Clima calmo para leituras e revisões";
        }

        if (descricao.contains("nuv")) {
            return "Bom momento para revisões contínuas";
        }

        if (descricao.contains("céu limpo")) {
            return "Dia ótimo para tarefas mais exigentes";
        }

        if (temperatura >= 30) {
            return "Lembre-se de beber água e fazer pausas";
        }

        return "Organize suas tarefas e mantenha o foco";
    }
}