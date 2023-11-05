package workers;

import prepare.TransactionPullReader;

import java.io.File;
import java.io.FileWriter;

public class TransactionPreparer {
    //параметризируем пул транзакций перед отправкой в кафку.
    // в пуле транзакций соответствующие поля должны быть равны аргументам таргет
    public static String innOrgIDReplace(String inn, String orgID, String uuid){
        String change1 = TransactionPullReader.transaction.replace("0439335187", inn);
        String change2 = change1.replace("UB8LFB", orgID);
        String change3 = change2.replace("1d08007f-6737-4e0c-ba7f-a9a01c080a54", uuid);
        return change3;
    }
}
