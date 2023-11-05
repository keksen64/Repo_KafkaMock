package kafka;

import influx.InfluxAsyncSender;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import prepare.ConfigReader;
import prepare.TransactionPullReader;

import java.util.Properties;

public class KafkaSender
{
    public static Properties props;
    public static Producer<String, String> producer;

    //метод выполняется один раз при старте заглушки
    public static void kafkaPrepare(){
        KafkaSender.props = new Properties();
        String bss = ConfigReader.kafkabss;
        props.put("bootstrap.servers", bss);
        props.put("key.serializer","org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        KafkaSender.producer = new KafkaProducer<String,String>(KafkaSender.props);
        System.out.println("||||||||||||||Kafka||||||||||||||||\n" +
                "bootstrap.servers - " + bss +
                "\nkey.serializer - org.apache.kafka.common.serialization.StringSerializer" +
                "\nvalue.serializer - org.apache.kafka.common.serialization.StringSerializer" +
                "\ntopic - " + ConfigReader.kafkaTopic +
                "\nkafkaDelayMS - " + ConfigReader.kafkaDelayMS);
    }

    //метод отправки в кафку. В данный момент консюмер постоянно открыт. Если требуется закрытие и пересоздание при отправке - раскомментировать строки
    public static void send(String uuid, String body) throws Throwable {
        // Producer<String, String> producer = new KafkaProducer<String,String>(KafkaSender.props);
        try {
            final ProducerRecord<String, String> record = new ProducerRecord<>(ConfigReader.kafkaTopic , body);
            KafkaSender.producer.send(record);
            //KafkaSender.producer.close();
            if(ConfigReader.isSendMetric){
                InfluxAsyncSender ias = new InfluxAsyncSender("sendToKafka", TransactionPullReader.txCount, 0);
                ias.start();
            }
        }catch (Exception e){
            System.err.println(e);
            System.err.println("ERROR KAFKA SEND: message id - "+uuid);
            //KafkaSender.producer.close();
            //отстрел события в инфлюкс в отдельном потоке
            if(ConfigReader.isSendMetric){
                InfluxAsyncSender ias = new InfluxAsyncSender("sendToKafka", TransactionPullReader.txCount, 1);
                ias.start();
            }
        }
    }
}

