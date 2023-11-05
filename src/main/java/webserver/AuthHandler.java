package webserver;



import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import prepare.ConfigReader;
import workers.AsyncWorker;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.UUID;


public class AuthHandler implements HttpHandler {
    //обработчик для получения токена
    public void handle(HttpExchange httpExchange) throws IOException {
        try {
            String response = "{\"access_token\": \"EtoMoyTokenTakikhTokenovMnogoNoEtotMoyMoyTokenMoyLuchshiyDrugEtoMoyaZhiznYaDolzhenNauchitsyaVladetTokenomTakZheKakVladeyuSvoeyZhiznyuBezMenyaMoyTokenBespolezenBezMoegoTokenaBespolezenYaYaDolzhenMetkoSlatSvoyTokenYaDolzhenSlatTochneeChemLyuboyKeyKlokKotoryyPytaetsyaMenyaRazvernutYaDolzhenOtpravitYegoDoTogoKakOnProtukhnetIYaEtoSdelayuKlyanusPeredOliveromYaiMoyTokenMyZashchitnikiMoeyABS\", \"expires_in\": 1800}";
            OutputStream outputStream = httpExchange.getResponseBody();
            byte[] responseb = response.getBytes(StandardCharsets.UTF_8);
            httpExchange.sendResponseHeaders(200, responseb.length);
            outputStream.write(responseb);
            outputStream.flush();
            outputStream.close();
            httpExchange.getRequestBody().close();
            java.time.LocalDateTime currentDateTime = java.time.LocalDateTime.now();
            System.out.println(currentDateTime + " out response: successGetToken" );
        }catch (Exception e){
            System.err.println("error get access_token" + e.toString());
            String responseErr = "error get access_token \n" + e.toString();
            byte[] bytesErr = responseErr.getBytes(StandardCharsets.UTF_8);
            String utf8EncodedStringErr = new String(bytesErr, StandardCharsets.UTF_8);
            httpExchange.getResponseHeaders().set("Access-Control-Allow-Origin", " *");
            httpExchange.getResponseHeaders().set("Content-Type", "text/html; charset=UTF-8");
            httpExchange.sendResponseHeaders(400, 0);
            OutputStream os = httpExchange.getResponseBody();
            Writer ow = new OutputStreamWriter(os);
            ow.write(utf8EncodedStringErr);
            ow.close();
            os.close();
        }
    }
}
