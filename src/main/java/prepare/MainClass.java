package prepare;

import kafka.KafkaSender;
import webserver.Webserver;
import workers.B64;

import static kafka.KafkaSender.kafkaPrepare;

public class MainClass {
    public static void main(String[] args) {

        ConfigReader.parse(ConfigReader.read());
        TransactionPullReader.read();
        TransactionPullReader.txCount(TransactionPullReader.transaction);
        //ConfigReader.parse(ConfigReader.read());
        kafkaPrepare();
        Webserver.startWebserver();
    }
}
