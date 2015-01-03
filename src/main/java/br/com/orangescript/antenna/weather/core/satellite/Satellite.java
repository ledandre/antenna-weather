package br.com.orangescript.antenna.weather.core.satellite;

import java.io.IOException;
import java.util.List;

import org.xml.sax.SAXException;

import br.com.orangescript.antenna.weather.beans.Forecast;
import br.com.orangescript.antenna.weather.core.Cities;
import br.com.orangescript.restcube.exceptions.BadRequestException;

public interface Satellite {

	public List<Forecast> getWeatherForecastFor(Cities city) throws IllegalStateException, IOException, BadRequestException, SAXException;
}