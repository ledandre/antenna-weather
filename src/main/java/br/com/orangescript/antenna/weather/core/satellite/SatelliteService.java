package br.com.orangescript.antenna.weather.core.satellite;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.digester.rss.Channel;
import org.apache.commons.digester.rss.RSSDigester;
import org.apache.http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import br.com.orangescript.antenna.weather.beans.Forecast;
import br.com.orangescript.antenna.weather.core.Cities;
import br.com.orangescript.antenna.weather.core.ForecastBuilder;
import br.com.orangescript.restcube.core.RestCube;
import br.com.orangescript.restcube.exceptions.BadRequestException;
import br.com.orangescript.restcube.request.ContentType;

public class SatelliteService implements Satellite {
    private static final Logger LOGGER = LoggerFactory.getLogger(SatelliteService.class);

    @Override
    public List<Forecast> getWeatherForecastFor(Cities city) 
            throws IllegalStateException, IOException, BadRequestException, SAXException {

        LOGGER.info("Getting weather forecast from {}", city.getForecastUrl());
        HttpResponse weatherForecastXML = 
                new RestCube()
                    .to(city.getForecastUrl())
                    .as(ContentType.CONTENT_TYPE_FORM_URLENCODED)
                    .get();

        return parseXML(weatherForecastXML.getEntity().getContent());
    }

    private List<Forecast> parseXML(InputStream weatherForecastXML) {
        List<Forecast> forecastList = new ArrayList<Forecast>();

        RSSDigester rssDigester = new RSSDigester();

        try {
            Channel rssChannel = (Channel) rssDigester.parse(weatherForecastXML);
            return ForecastBuilder.build(rssChannel);
        } catch (IOException | SAXException e) {
            LOGGER.error(e.getLocalizedMessage(), e);
        }

        return forecastList;
    }
}