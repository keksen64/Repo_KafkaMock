package webserver;

import com.sun.net.httpserver.HttpServer;
import prepare.ConfigReader;
import java.net.InetSocketAddress;

//объект вебсервера
public class Webserver {
    public static void startWebserver()  {
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(ConfigReader.mockPort), 0);
            server.createContext(ConfigReader.mockEndpoint, new SenecaHandler());
            server.createContext(ConfigReader.authEndpoint, new AuthHandler());
            server.setExecutor(java.util.concurrent.Executors.newFixedThreadPool(ConfigReader.webserverThreadsCount));
            server.start();
            System.out.println("||||||||||||Webserver|||||||||||||");
            System.out.println("webserver started on:\n" +
                    "port - " + ConfigReader.mockPort +
                    "\nmockEndpoint - " + ConfigReader.mockEndpoint +
                    "\nauthEndpoint - " + ConfigReader.authEndpoint +
                    "\nthreads - " + ConfigReader.webserverThreadsCount +
                    "\nhttpDelayMS - " + ConfigReader.httpDelayMS);
        } catch (Exception e){
            System.out.println("error start webserver");
            System.out.println(e);
        }
    }
}
