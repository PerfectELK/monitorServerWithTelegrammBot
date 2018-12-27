import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.*;
import java.util.logging.Logger;

public class Telegram {

  private static String apiUrl = "https://api.telegram.org/bot655625711:AAHJEl5qZn0mPSImLEmO9iaa4eM0i1s72QU/";
  private static String apiMethod = "sendMessage?chat_id=";

  private String message;
  private String chatId;

  Telegram(String chatId, String message){
      this.chatId = chatId;
      this.message = message;
  }

  public void SendTelegramRequest() throws IOException {
      String url = apiUrl + apiMethod + this.chatId + "&parse_mode=markdown&text=" + this.message;

      URL u = new URL(url);
      HttpURLConnection conn = (HttpURLConnection) u.openConnection();
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
  }

  public void SendTelegramRequest(Proxy p) throws IOException {
      String url = apiUrl + apiMethod + this.chatId + "&parse_mode=markdown&text=" + this.message;

      URL u = new URL(url);
      HttpURLConnection conn = (HttpURLConnection) u.openConnection(p);
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
  }

}
