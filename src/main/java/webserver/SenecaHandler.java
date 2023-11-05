package webserver;



import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import influx.InfluxAsyncSender;
import prepare.ConfigReader;
import prepare.TransactionPullReader;
import workers.AsyncWorker;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.UUID;


public class SenecaHandler implements HttpHandler {

    //метод в новом потоке обрабатывает входящие запросы по соответствующему эндпоинту указанному в классе Webserver
    public void handle(HttpExchange httpExchange) throws IOException {
        ConfigReader.requestCount ++;
        int random = 1+ (int) ( Math.random() * 5 );
        if(ConfigReader.requestCount>200){
            ConfigReader.requestCount=0;
            random = 34;
        }
        int txCount = random * TransactionPullReader.txCount;
        try {
            Thread.sleep(ConfigReader.httpDelayMS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            UUID messid = UUID.randomUUID();
            String response = "{\n" +
                    "  \"count\": "+txCount+",\n" +
                    "  \"requestId\": \""+ messid.toString() +"\"\n" +
                    "}";
            OutputStream outputStream = httpExchange.getResponseBody();
            byte[] responseb = response.getBytes(StandardCharsets.UTF_8);
            httpExchange.sendResponseHeaders(200, responseb.length);
            outputStream.write(responseb);
            //отправка ответного шттп
            outputStream.flush();
            outputStream.close();
            httpExchange.getRequestBody().close();
            //запуск процесса ответа в кафку в отдельном потоке
            java.time.LocalDateTime currentDateTime = java.time.LocalDateTime.now();
            System.out.println(currentDateTime + " out response: mess_ID: " + messid.toString() + " pack_number: " + random);
            //запуск процесса отстрела метрики в инфлюкс отдельном потоке
            if(ConfigReader.isSendMetric){
                InfluxAsyncSender ias = new InfluxAsyncSender("httpResponse", random, 0);
                ias.start();
            }
            //запуск процесса ответа в кафку в отдельном потоке
            AsyncWorker asw = new AsyncWorker(messid.toString(), "1111111111", ExtractorRequest.extractOrgID(httpExchange), random);
            asw.start();
        }catch (Exception e){
            System.err.println("error get seneca request");
            if(ConfigReader.isSendMetric){
                InfluxAsyncSender ias = new InfluxAsyncSender("httpResponse", random, 1);
                ias.start();
            }
            String responseErr = e.toString();
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
