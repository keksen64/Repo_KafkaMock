package webserver;

import com.sun.net.httpserver.HttpExchange;
import prepare.ConfigReader;

import java.io.BufferedWriter;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExtractorRequest {

    //извлекает тело входящего запроса. Не используется
    public static String extractBody(HttpExchange httpExchange){
        Scanner s = new Scanner(httpExchange.getRequestBody()).useDelimiter("\\A");
        String result = s.hasNext() ? s.next() : "";
        return result;
    }

    //извлекает оргИД из УРЛа входящего запроса
    public static String extractOrgID(HttpExchange httpExchange){
        try {
            String url = httpExchange.getRequestURI().toString();
            Pattern pattern7 = Pattern.compile(ConfigReader.mockEndpoint + "(.*?)/statement");
            Matcher matcher7 = pattern7.matcher(url);
            matcher7.find();
            int start7=matcher7.start()+ConfigReader.mockEndpoint.length();
            int end7=matcher7.end()-10;
            String result = url.substring(start7,end7).toLowerCase();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("ERROR extract orgId");
            return "orgErr";
        }//
    }
}
