package prepare;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TransactionPullReader {
    final static String transactionFileName = "./transaction.json";
    public static String transaction;
    public static int txCount;

    //читает пул транзакций в статичную переменную
    public static void read(){
        String filer = null;
        try{
            filer = new String(Files.readAllBytes(Paths.get(TransactionPullReader.transactionFileName)));
            System.out.println("|||||||||||transaction|||||||||||||\n" +
                    "transaction have been read");
        }catch (Exception e){
            System.out.println("error transaction read. \n transaction file must be in this directory \n transaction file should be called transaction.json");
        }
        TransactionPullReader.transaction = filer;
    }

    //считает колво транзакций в пуле
    public static int txCount(String str){
        int count = 0;
        Pattern pattern00 = Pattern.compile("\"id\": \"........-....-....-....-............\",");
        Matcher matcher00 = pattern00.matcher(str);
        while (matcher00.find()){
            count++;
        }
        TransactionPullReader.txCount = count;
        System.out.println("transaction count = " +TransactionPullReader.txCount);
        return count;
    }
}
