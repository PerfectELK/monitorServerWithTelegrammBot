import java.sql.*;
import java.sql.*;
import java.util.*;

import static java.lang.Integer.parseInt;


public class PelkSqLite {

        public static Connection conn;
        public static Statement statmt;
        public static ResultSet resSet;

        // --------ПОДКЛЮЧЕНИЕ К БАЗЕ ДАННЫХ--------
        public static void Conn() throws SQLException
        {
            conn = null;
            conn = DriverManager.getConnection("jdbc:sqlite:proxies.db");
            System.out.println("Connection with DB was setup");
        }

        // --------Создание таблицы--------
        public static void CreateDB() throws ClassNotFoundException, SQLException
        {
            statmt = conn.createStatement();
            statmt.execute("CREATE TABLE if not exists 'proxies' ('id' INTEGER PRIMARY KEY AUTOINCREMENT, 'host' text, 'port' INT);");
            System.out.println("DB was created or already exist");
        }

        // --------Заполнение таблицы--------
        public static void WriteDB(String Host, int Port) throws SQLException
        {
            PreparedStatement prepare = conn.prepareStatement("INSERT INTO 'proxies' (`host`,`port`) VALUES (?,?)");
            prepare.setObject(1,Host);
            prepare.setObject(2,Port);
            prepare.execute();
            System.out.println("Writed :" + Host +":" + Port);
        }

        // -------- Вывод таблицы--------
        public static ArrayList ReadDB() throws ClassNotFoundException, SQLException
        {
            resSet = statmt.executeQuery("SELECT * FROM 'proxies'");

            ArrayList<ProxyPelk> al = new ArrayList<ProxyPelk>();
            while(resSet.next())
            {
                int id = resSet.getInt("id");
                String  host = resSet.getString("name");
                int  port = parseInt(resSet.getString("phone"));
                ProxyPelk pp = new ProxyPelk(host,port);
                al.add(pp);
            }
            return al;

        }
        public static  ResultSet getAllDB() throws SQLException {
            conn = DriverManager.getConnection("jdbc:sqlite:proxies.db");
            statmt = conn.createStatement();
            ResultSet result =  statmt.executeQuery("SELECT * FROM 'proxies'");
            return result;
        }

        public static boolean SearchHostAndPort(String Host, int Port) throws ClassNotFoundException, SQLException{
            PreparedStatement prepare = conn.prepareStatement("SELECT * FROM 'proxies' WHERE `host` = ? AND `port` = ?");
            prepare.setObject(1,Host);
            prepare.setObject(2,Port);
            ResultSet execQuery = prepare.executeQuery();
            while(execQuery.next()){
                 System.out.println("Founded same result: " + execQuery.getString("host"));
                 return true;
            }
            return false;

        }

        // --------Закрытие--------
        public static void CloseDB() throws ClassNotFoundException, SQLException
        {
            conn.close();
            System.out.println("Connection was closed");
        }

}
