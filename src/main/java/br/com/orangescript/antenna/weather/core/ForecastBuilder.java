package br.com.orangescript.antenna.weather.core;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.digester.rss.Channel;
import org.apache.commons.digester.rss.Item;

import br.com.orangescript.antenna.weather.beans.Forecast;

public class ForecastBuilder {
    private static final Pattern DEGREE_PATTERN = Pattern.compile("[0-9]{1,2}[º](C)");
    private static final Pattern DATE_PATTERN = Pattern.compile("([a-zA-Z]){3}(,)\\s([0-9]){1,2}/([0-9]){1,2}");
    private static final Pattern IMAGE_LINK_PATTERN = Pattern.compile("(http://)(.)+(.gif|.jpeg|.jpg|.png)");

    private static final String DEGREE_SIMBOL = "ºC";

    public static List<Forecast> build(Channel rssChannel) {
        List<Forecast> forecasts = new ArrayList<Forecast>();

        for (Item rssItem : rssChannel.getItems()) {
            forecasts.add(build(rssItem));
        }

        return forecasts;
    }

    public static Forecast build(Item rssItem) {
        Forecast forecast = new Forecast();
        String itemDescription = rssItem.getDescription();

        buildDegrees(itemDescription, forecast);
        buildDate(itemDescription, forecast);
        buildImageLink(itemDescription, forecast);

        return forecast;
    }

    private static void buildImageLink(String itemDescription, Forecast forecast) {
        Matcher matcher = IMAGE_LINK_PATTERN.matcher(itemDescription);
        if (matcher.find())
            forecast.setWeatherImage(matcher.group());
    }

    private static void buildDate(String rssItemDescription, Forecast forecast) {
        Matcher matcher = DATE_PATTERN.matcher(rssItemDescription);
        if (matcher.find())
            forecast.setDate(matcher.group());
    }

    private static void buildDegrees(String rssItemDescription, Forecast forecast) {
        Matcher matcher = DEGREE_PATTERN.matcher(rssItemDescription);
        if (matcher.find()) {
            String min = matcher.group().replaceAll(DEGREE_SIMBOL, "");

            forecast.setMinimum(Integer.parseInt(min));

            matcher.find();
            String max = matcher.group().replaceAll(DEGREE_SIMBOL, "");
            forecast.setMaximum(Integer.parseInt(max));
        }
    }
}
