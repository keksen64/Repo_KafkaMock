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


public class UpdateConfigHandler implements HttpHandler {
    //обработчик для апдейта параметров заглушки без перезагрузки приложения. В процессе разработки
    public void handle(HttpExchange httpExchange) throws IOException {
        try {
            System.out.println("in request");
            String response = "config has been updated";
            OutputStream outputStream = httpExchange.getResponseBody();
            byte[] responseb = response.getBytes(StandardCharsets.UTF_8);
            httpExchange.sendResponseHeaders(200, responseb.length);
            outputStream.write(responseb);
            outputStream.flush();
            outputStream.close();
            httpExchange.getRequestBody().close();
            ConfigReader.parse(ConfigReader.read());
        }catch (Exception e){
            String responseErr = "error update config \n" + e.toString();
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
