import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.commons.digester.rss.Channel;
import org.apache.commons.digester.rss.RSSDigester;
import org.xml.sax.SAXException;

import br.com.orangescript.antenna.weather.beans.Forecast;
import br.com.orangescript.antenna.weather.core.Cities;
import br.com.orangescript.antenna.weather.core.ForecastBuilder;
import br.com.orangescript.restcube.core.RestCube;
import br.com.orangescript.restcube.exceptions.BadRequestException;
import br.com.orangescript.restcube.request.ContentType;

public class ForecastTest {

    public static void main(String args[]) throws IllegalStateException, IOException, BadRequestException, SAXException {
        RSSDigester digester =  new RSSDigester();
        InputStream xmlStream = new RestCube().to(Cities.OSASCO.getForecastUrl()).as(ContentType.CONTENT_TYPE_FORM_URLENCODED).get().getEntity().getContent();
        Channel channel = (Channel) digester.parse(xmlStream);

        List<Forecast> forecasts = ForecastBuilder.build(channel);
        
        for (Forecast forecast : forecasts)
            System.out.println(forecast.getDate() + ": " + forecast.getMinimum()  + " - " + forecast.getMaximum()); 
    }
}