import java.io.FileWriter;
import java.net.InetAddress;

public class Main {

    public static void main(String[] args) {

        while(true){
            try{
                InetAddress address = InetAddress.getByName(args[0]);
                boolean reachable = address.isReachable(10000);
                if(!reachable){
                    Telegram telegram = new Telegram();
                    telegram.alert("Сервер " + args[1] + " упал");
                    Thread.sleep(1000 * 60 * 60);
                }
                FileWriter fw = new FileWriter("./text.html");
                fw.write(args[0] + " " + args[1]);
                fw.close();
                Thread.sleep(1000 * 300);
            } catch (Exception e){
                e.printStackTrace();
            }
        }

    }

}
