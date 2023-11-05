package prepare;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConfigReader {
    final static String fileName = "./config.json";
    public static String kafkabss;
//    public static String kafkaPort;
    public static String kafkaTopic;
    public static String influxHost;
    public static String influxPort;
    public static String influxDB;
    public static String mockEndpoint;
    public static String authEndpoint;
    public static String influxBoolean;
    public static int webserverThreadsCount;
    public static int mockPort;
    public static int httpDelayMS;
    public static int kafkaDelayMS;
    public static int requestCount = 0;
    public static boolean isSendMetric = false;

    //читает конфиг
    public static String read(){
        String filer;
        try{
            filer = new String(Files.readAllBytes(Paths.get(ConfigReader.fileName)));
        }catch (Exception e){
            System.out.println("error config read. \n config must be in this directory \n config should be called config.json");
            return "1";
        }
        return filer;
    }

    //парсит конфиг и устанавливает статичные переменные с начтройками
    public static void parse(String str){
        try {
            Pattern pattern0 = Pattern.compile("kafkabss\": \"(.*?)\"");
            Matcher matcher0 = pattern0.matcher(str);
            matcher0.find();
            int start0=matcher0.start()+12;
            int end0=matcher0.end()-1;
            kafkabss = str.substring(start0,end0);

//            Pattern pattern1 = Pattern.compile("kafkaPort\": \"(.*?)\"");
//            Matcher matcher1 = pattern1.matcher(str);
//            matcher1.find();
//            int start1=matcher1.start()+13;
//            int end1=matcher1.end()-1;
//            kafkaPort = str.substring(start1,end1);

            Pattern pattern2 = Pattern.compile("kafkaTopic\": \"(.*?)\"");
            Matcher matcher2 = pattern2.matcher(str);
            matcher2.find();
            int start2=matcher2.start()+14;
            int end2=matcher2.end()-1;
            kafkaTopic = str.substring(start2,end2);

            Pattern pattern3 = Pattern.compile("influxHost\": \"(.*?)\"");
            Matcher matcher3 = pattern3.matcher(str);
            matcher3.find();
            int start3=matcher3.start()+14;
            int end3=matcher3.end()-1;
            influxHost = str.substring(start3,end3);

            Pattern pattern4 = Pattern.compile("influxPort\": \"(.*?)\"");
            Matcher matcher4 = pattern4.matcher(str);
            matcher4.find();
            int start4=matcher4.start()+14;
            int end4=matcher4.end()-1;
            influxPort = str.substring(start4,end4);

            Pattern pattern5 = Pattern.compile("influxDB\": \"(.*?)\"");
            Matcher matcher5 = pattern5.matcher(str);
            matcher5.find();
            int start5=matcher5.start()+12;
            int end5=matcher5.end()-1;
            influxDB = str.substring(start5,end5);

            Pattern pattern6 = Pattern.compile("mockEndpoint\": \"(.*?)\"");
            Matcher matcher6 = pattern6.matcher(str);
            matcher6.find();
            int start6=matcher6.start()+16;
            int end6=matcher6.end()-1;
            mockEndpoint = str.substring(start6,end6);

//            Pattern pattern7 = Pattern.compile("influxBoolean\": \"(.*?)\"");
//            Matcher matcher7 = pattern7.matcher(str);
//            matcher7.find();
//            int start7=matcher7.start()+17;
//            int end7=matcher7.end()-1;
//            influxBoolean = str.substring(start7,end7);

            Pattern pattern8 = Pattern.compile("webserverThreadsCount\": \"(.*?)\"");
            Matcher matcher8 = pattern8.matcher(str);
            matcher8.find();
            int start8=matcher8.start()+25;
            int end8=matcher8.end()-1;
            try{
                webserverThreadsCount = Integer.parseInt(str.substring(start8,end8));
            }catch (Exception e){
                System.out.println("webserverThreadsCount should be integer");
            }

            Pattern pattern9 = Pattern.compile("mockPort\": \"(.*?)\"");
            Matcher matcher9 = pattern9.matcher(str);
            matcher9.find();
            int start9=matcher9.start()+12;
            int end9=matcher9.end()-1;
            try{
                mockPort = Integer.parseInt(str.substring(start9,end9));
            }catch (Exception e){
                System.out.println("mockPort should be integer");
            }

            Pattern pattern10 = Pattern.compile("httpDelayMS\": \"(.*?)\"");
            Matcher matcher10 = pattern10.matcher(str);
            matcher10.find();
            int start10=matcher10.start()+15;
            int end10=matcher10.end()-1;
            try{
                httpDelayMS = Integer.parseInt(str.substring(start10,end10));
            }catch (Exception e){
                System.out.println("httpDelayMS should be integer");
            }

            Pattern pattern11 = Pattern.compile("kafkaDelayMS\": \"(.*?)\"");
            Matcher matcher11 = pattern11.matcher(str);
            matcher11.find();
            int start11=matcher11.start()+16;
            int end11=matcher11.end()-1;
            try{
                kafkaDelayMS = Integer.parseInt(str.substring(start11,end11));
            }catch (Exception e){
                System.out.println("kafkaDelayMS should be integer");
            }

            Pattern pattern12 = Pattern.compile("sendMetric\": \"(.*?)\"");
            Matcher matcher12 = pattern12.matcher(str);
            matcher12.find();
            int start12=matcher12.start()+14;
            int end12=matcher12.end()-1;
            influxBoolean = str.substring(start12,end12);
            if(influxBoolean.equals("enable")){
                isSendMetric = true;
                System.out.println("||||||||||||||Influx||||||||||||||||\n" +
                        "Influx - " + influxBoolean +
                        "\nInflux - " + ConfigReader.influxHost +
                        "\nInfluxPort - " + ConfigReader.influxPort+
                        "\nInfluxDatabase - " + ConfigReader.influxDB);
            }

            Pattern pattern13 = Pattern.compile("authEndpoint\": \"(.*?)\"");
            Matcher matcher13 = pattern13.matcher(str);
            matcher13.find();
            int start13=matcher13.start()+16;
            int end13=matcher13.end()-1;
            authEndpoint = str.substring(start13,end13);

        } catch (Exception e){
            System.out.println("error config read. \n config must be in this directory \n config should be called config.json");
        }
    }
}
