package br.com.orangescript.antenna.weather.core;

import static org.junit.Assert.*;

import org.apache.commons.digester.rss.Item;
import org.junit.Test;

import br.com.orangescript.antenna.weather.beans.Forecast;

public class ForecastBuilderTest {

    @Test
    public void parseWithImageTest() {
        Item rssItem = new Item();
        rssItem.setDescription("<img src=\"http://img0.cptec.inpe.br/~rgrafico/icones_principais/tempo/maior/ppt.gif\"/>Qua, 31/12 - Min: 20ºC Máx: 31ºC Previsão para os próximos dias:");

        Forecast forecast = ForecastBuilder.build(rssItem);

        assertTrue(forecast.getMinimum() == 20);
        assertTrue(forecast.getMaximum() == 31);
        assertEquals("Qua, 31/12", forecast.getDate());
        assertEquals("http://img0.cptec.inpe.br/~rgrafico/icones_principais/tempo/maior/ppt.gif", forecast.getWeatherImage());
        assertEquals(null, forecast.getWeatherCondition());
    }

    @Test
    public void parseWithoutImageTest() {
        Item rssItem = new Item();
        rssItem.setDescription("Qua, 31/12 - Min: 20ºC Máx: 31ºC Condição: Parcialmente Nublado");

        Forecast forecast = ForecastBuilder.build(rssItem);

        assertTrue(forecast.getMinimum() == 20);
        assertTrue(forecast.getMaximum() == 31);
        assertEquals("Qua, 31/12", forecast.getDate());
        assertEquals(null, forecast.getWeatherImage());
        assertEquals("Parcialmente Nublado", forecast.getWeatherCondition());
    }

    @Test
    public void parseSaturdayForecastTest() {
        Item rssItem = new Item();
        rssItem.setDescription("Sáb, 10/01 -  Min: 20ºC Máx: 33ºC Condição: Parcialmente Nublado");

        Forecast forecast = ForecastBuilder.build(rssItem);

        assertEquals("Sáb, 10/01", forecast.getDate());
    }

    @Test
    public void parseEmptyItemDescriptionTest() {
        Item rssItem = new Item();
        rssItem.setDescription("");

        ForecastBuilder.build(rssItem);
    }

    @Test
    public void parseNullItemDescriptionTest() {
        Item rssItem = new Item();

        ForecastBuilder.build(rssItem);
    }

    @Test
    public void parseItemsWithoutForecastInformationTest() {
        Item rssItem = new Item();

        rssItem.setDescription("Atualizado em:06/01/2015");
        ForecastBuilder.build(rssItem);

        rssItem.setDescription("null");
        ForecastBuilder.build(rssItem);
    }
}