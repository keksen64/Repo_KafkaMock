package workers;

import prepare.TransactionPullReader;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

//декодер бэйс64. Не используется
public class B64 {
    public static   void decoder(){

        String filer = null;
        try{
            filer = new String(Files.readAllBytes(Paths.get("./code.txt")));
        }catch (Exception e){
            System.out.println("error transaction read. \n transaction file must be in this directory \n transaction file should be called transaction.json");
        }

//        // кодируем строку с помощью кодировщика `Base64`
//        Base64.Encoder encoder = Base64.getEncoder();
//        String encoded = encoder.encodeToString(string.getBytes());
//        System.out.println("Encoded Data: " + encoded);

        // декодируем закодированные данные
        Base64.Decoder decoder = Base64.getDecoder();
        String decoded = new String(decoder.decode(filer));
        //System.out.println("Decoded Data: " + decoded);
        try{
            File fileerr = new File("decoded.json"); //файл куда сливаются ошибочные блоки
            fileerr.createNewFile();
            FileWriter writer2 = new FileWriter(fileerr);
            writer2.write(decoded);
            System.out.println("записан файл с измененным инн");
            writer2.close();
        }catch (Exception e){
            System.out.println("ошибка записи файла");
        }
    }
}
