import java.io.FileWriter;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws IOException {

        ArrayList<ProxyPelk> proxy = new ArrayList<ProxyPelk>();
        proxy.add(new ProxyPelk("159.192.240.143",8080));
        Proxy p = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxy.get(0).host,proxy.get(0).port));

//        try {
//            Telegram telegram = new Telegram("@yarus57","pizdec");
//            telegram.SendTelegramRequest();
//        } catch (IOException e) {
//
//            Telegram telegram = new Telegram("@yarus57","pizdec");
//            telegram.SendTelegramRequest(p);
//        }

        if("Alert".equals(args[0])){
            try {
                Telegram telegram = new Telegram(args[1],args[2]);
                telegram.SendTelegramRequest();
            } catch (IOException e) {
                e.printStackTrace();

                Telegram telegram = new Telegram(args[1],args[2]);
                telegram.SendTelegramRequest(p);
            }
        }else if("Monitor".equals(args[0])){
            while (true) {
                try {
                    InetAddress address = InetAddress.getByName(args[4]);
                    boolean reachable = address.isReachable(10000);
                    if (!reachable) {
                        Telegram telegram = new Telegram(args[1],args[2]);
                        telegram.SendTelegramRequest();
                        Thread.sleep(1000 * 60 * 60);
                    }
                    Thread.sleep(1000 * 300);
                } catch (Exception e) {
                    Telegram telegram = new Telegram(args[1],args[2]);
                    telegram.SendTelegramRequest(p);
                }
            }
        }


    }

}
