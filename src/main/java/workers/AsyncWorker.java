package workers;

import kafka.KafkaSender;
import prepare.ConfigReader;

//асинхронный обработчик для подготовки и отправки сообщений в кафку
public class AsyncWorker extends Thread{

    private String uuid;
    private String inn;
    private String orgID;
    private int count;

    public AsyncWorker(String uuid, String inn, String orgID, int count) {
        this.uuid = uuid;
        this.inn = inn;
        this.orgID = orgID;
        this.count = count;
    }

    public void run(){

        try {
            Thread.sleep(ConfigReader.kafkaDelayMS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String replacedTX = TransactionPreparer.innOrgIDReplace(inn, orgID, uuid);
        for(int i = 0; i<count; i++){
            try {
                KafkaSender.send(uuid,replacedTX);
                java.time.LocalDateTime currentDateTime1 = java.time.LocalDateTime.now();
                System.out.println(currentDateTime1 + " kafka sended: mess_ID: " + uuid + " pack_order_number: " + i);
            } catch (Throwable e) {
                e.printStackTrace();
                System.err.println("ERROR KAFKA SEND: uuid - " + uuid + " inn - " + inn + " orgID - " + orgID + e);
            }
        }
    }


}
