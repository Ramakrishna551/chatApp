import java.io.*;
import java.net.*;
public class Client{
    Socket socket ;
    BufferedReader br;
    PrintWriter out;
    public Client(){
        try{
            System.out.println("sending request to server..");
            socket = new Socket("192.168.0.108",7777);
            System.out.println("connection done");
             br= new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out= new PrintWriter(socket.getOutputStream());
            startRedaing();
            startWriting();
        }
        catch(Exception e){
            
        }
    }
    public void startRedaing(){
        Runnable r1 = () ->{
          System.out.println("reader started");  
          try{
              while(true){
                  String msg = br.readLine();
                  if(msg.equals("exit")){
                      System.out.println("server terminated chat");
                      socket.close();
                      break;
                  }
                  System.out.println("server :"+msg);
              }
          }
          catch(Exception e){
              System.out.println("connection closed");
          }
        };
        new Thread(r1).start();
    }
    public void startWriting(){
        Runnable r2 = () -> {
          System.out.println("writer started ");
          try{
              while(!socket.isClosed()){
                  BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in));
                    String content = br1.readLine();
                    out.println(content);
                    out.flush();
                    if(content.equals("exit")){
                        socket.close();
                        break;
                    }
              }
          }
          catch(Exception e){
              System.out.println("connection closed");
          }
        };
        new Thread(r2).start();
    }
    public static void main(String[] args){
    	System.out.println("this is client..");
        new Client();
    }
    
}