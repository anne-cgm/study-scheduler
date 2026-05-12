package studyscheduler.model;

public class Weather {
    private String descricao;
    private double temperatura;
    private String mensagem;

    public Weather(String descricao, double temperatura, String mensagem) {
        this.descricao = descricao;
        this.temperatura = temperatura;
        this.mensagem = mensagem;
    }

    public String getDescricao() {
        return descricao;
    }

    public double getTemperatura() {
        return temperatura;
    }

    public String getMensagem() {
        return mensagem;
    }
}