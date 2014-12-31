import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.digester.rss.Channel;
import org.apache.commons.digester.rss.Item;
import org.apache.commons.digester.rss.RSSDigester;
import org.xml.sax.SAXException;

import br.com.orangescript.antenna.weather.core.Cities;
import br.com.orangescript.restcube.core.RestCube;
import br.com.orangescript.restcube.exceptions.BadRequestException;
import br.com.orangescript.restcube.request.ContentType;

public class ForecastTest {

    public static void main(String args[]) throws IllegalStateException, IOException, BadRequestException, SAXException {
        RSSDigester digester =  new RSSDigester();
        InputStream xmlStream = new RestCube().to(Cities.OSASCO.getForecastUrl()).as(ContentType.CONTENT_TYPE_FORM_URLENCODED).get().getEntity().getContent();
        Channel channel = (Channel) digester.parse(xmlStream);

        if (channel != null) {
            System.out.println(channel.getDescription());
            for (Item item : channel.getItems()) {
                System.out.println(item.getDescription());
            }
        }
    }
}