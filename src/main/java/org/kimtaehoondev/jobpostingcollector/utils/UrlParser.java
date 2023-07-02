package org.kimtaehoondev.jobpostingcollector.utils;

import java.net.MalformedURLException;
import java.net.URL;

public class UrlParser {
    public static URL parse(String url) {
        try {
            return new URL(url);
        } catch (MalformedURLException e) {
            throw new RuntimeException("URL 양식이 정확하지 않습니다");
        }

    }
}
