package br.com.orangescript.antenna.weather.core;

public enum Cities {
    OSASCO("http://servicos.cptec.inpe.br/RSS/cidade/3656/previsao.xml");

    private String forecastUrl;

    private Cities(String forecastUrl) {
        this.forecastUrl = forecastUrl;
    }

    public String getForecastUrl() {
        return this.forecastUrl;
    }
}