import java.io.FileWriter;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws IOException, SQLException, ClassNotFoundException {


         ArrayList<ProxyPelk> al = ParsePelkProxy.parse();
         PelkSqLite.Conn();
         PelkSqLite.CreateDB();

         for(int i = 0; i < al.size(); i++){
             ProxyPelk pp = al.get(i);
             if(!PelkSqLite.SearchHostAndPort(pp.host,pp.port)){
                 PelkSqLite.WriteDB(pp.host,pp.port);
             }
         }
         PelkSqLite.CloseDB();

        if("Alert".equals(args[0])){
            try {
                Telegram telegram = new Telegram(args[1],args[2]);
                telegram.SendTelegramRequest();
            } catch (IOException e) {
                PelkSqLite.Conn();
                PelkSqLite.CreateDB();
                ResultSet proxy = PelkSqLite.getAllDB();
                while(proxy.next()){
                    try{
                        String host =  proxy.getString("host");
                        int port = proxy.getInt("port");
                        Telegram telegram = new Telegram(args[1],args[2]);
                        Proxy p = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(host, port));
                        telegram.SendTelegramRequest(p);
                        break;
                    }catch (Exception i){
                        System.out.println("Fuck proxy");
                    }

                }

            }
        }else if("Monitor".equals(args[0])){
            while (true) {
                try {
                    InetAddress address = InetAddress.getByName(args[3]);
                    boolean reachable = address.isReachable(10000);
                    if (!reachable) {
                        try {
                            Telegram telegram = new Telegram(args[1],args[2]);
                            telegram.SendTelegramRequest();
                        } catch (IOException e) {
                            PelkSqLite.Conn();
                            PelkSqLite.CreateDB();
                            ResultSet proxy = PelkSqLite.getAllDB();
                            while(proxy.next()){
                                try{
                                    String host =  proxy.getString("host");
                                    int port = proxy.getInt("port");
                                    Telegram telegram = new Telegram(args[1],args[2]);
                                    Proxy p = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(host, port));
                                    telegram.SendTelegramRequest(p);
                                    break;
                                }catch (Exception i){
                                    System.out.println("Fuck proxy");
                                }

                            }

                        }
                        Thread.sleep(1000 * 60);
                    }else{
                        System.out.println("Server on");
                        Thread.sleep(1000 * 200);
                    }

                } catch (Exception e) {
                    System.out.println("Pizdec");
                }
            }
        }


    }

}
