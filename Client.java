import java.util.*;
import java.net.*;
import java.io.*;

class Client {

  public static boolean print;
  public static boolean flag = true;

  public static String recv;
  public static String sendTo;
  public static Socket socket;
  public static String prompt;
  public static Scanner user_input;
  public static BufferedReader bytesRead;
  public static PrintWriter bytesWritten;

  final static String quit = "quit";

  public static void main(String[] args) {

    try {
      
	    socket = new Socket("localhost",62400);
      System.out.print("\nConnected.\n\n");

	    while(true) {

        bytesWritten = new PrintWriter(socket.getOutputStream(),true);
        bytesRead = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        if(flag) {
          System.out.print("Password: ");
          user_input = new Scanner(System.in);
          sendTo = user_input.next();
          bytesWritten.println(sendTo);

          recv = bytesRead.readLine();

          if(recv.equals("close")) {
            System.out.print("\nWrong password. Exiting now!\n\n");
            System.exit(0);
          }
          else {
            flag = false;
            System.out.println(recv);
          }
        }
        else {
          System.out.print("Message: ");
          user_input = new Scanner(System.in);
          sendTo = user_input.next();
          bytesWritten.println(sendTo);

          recv = bytesRead.readLine();
          System.out.println("Server: " + recv);
        }

      }
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }

}
