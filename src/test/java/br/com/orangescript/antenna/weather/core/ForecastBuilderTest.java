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
    }
}