
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import static java.lang.Integer.parseInt;

public class ParsePelkProxy {

    public static ArrayList parse(){
        try {
            URL url = new URL("http://api.foxtools.ru/v2/Proxy");

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();
            InputStream is = conn.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            String line = "";
            String result = "";
            try {
                while ((line = rd.readLine()) != null) {
                    result += line;
                }
            } catch (IOException ex) {

            }

            Object obj = new JSONParser().parse(result);
            JSONObject jo = (JSONObject) obj;
            JSONObject resp = (JSONObject) jo.get("response");
            JSONArray items = (JSONArray) resp.get("items");
            Object[] arr =  items.toArray();
            ArrayList<ProxyPelk> PPArray = new ArrayList<ProxyPelk>();

            for(Object a : arr){
                JSONObject item = (JSONObject) a;
                int Port = parseInt((String) item.get("port").toString());
                String Host = (String) item.get("ip").toString();
                ProxyPelk PP = new ProxyPelk(Host,Port);
                PPArray.add(PP);
            }
            System.out.println("Proxy get success");
            return PPArray;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        ArrayList<ProxyPelk> PPArray = new ArrayList<ProxyPelk>();
        return PPArray;
    }
}
