import java.util.*;
import java.net.*;
import java.io.*;

class Server {

  public static boolean close;
  public static boolean flag = true;

  public static String keys;
  public static String recv;
  public static String passwd;
  public static String sendTo;

  final static String quit     = "quit";
  final static String gps_on   = "gps-on";
  final static String location = "location";

  public static Scanner user_input;
  public static StringBuilder text;
  public static Socket clientSocket;
  public static ServerSocket socket;
  public static BufferedReader brKeys;
  public static BufferedReader bytesRead;
  public static PrintWriter bytesWritten;


  public static void main(String[] args) {

	  try {

      System.out.print("Starting server.\n");

	    socket = new ServerSocket(62400);
	    clientSocket = socket.accept();

	    while(true) {

        bytesRead = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        bytesWritten = new PrintWriter(clientSocket.getOutputStream(),true);

        if(flag) {

          recv = bytesRead.readLine();

          if(!(passwd(recv))) {
            System.out.print("Wrong password. Exiting now!\n");
            bytesWritten.println("close");
            System.exit(0);
          }
          else {
            flag = false;
            bytesWritten.println("Successfully logged in.\n");
          }
        }
        else {

          recv = bytesRead.readLine();

          if(recv.equals(location)) {
            System.out.print("Location sent.\n");
          }
          else if(recv.equals(quit)) {
            System.out.print("Client manually closed connection.\n");
            System.exit(0);
          }
          else {
            System.out.println("CLIENT: " + recv);
          }

          bytesWritten = new PrintWriter(clientSocket.getOutputStream(),true);
          System.out.print("Message: ");
          user_input = new Scanner(System.in);
          sendTo = user_input.next();
          bytesWritten.println(sendTo);

        }

	    }

    }
		catch(IOException e) {
		System.out.println(e);
		}
  }

  public static boolean passwd(String passwd) {

    File file = new File(".config");
    text = new StringBuilder();

    try {

      brKeys = new BufferedReader(new FileReader(file));

      while(( keys = brKeys.readLine()) != null ) {
        if(keys.equals(passwd)) {
          flag = true;
        }
        else {
          flag = false;
        }
      }
      brKeys.close();
    }
    catch (IOException e) {
      e.printStackTrace();
    }     
  return flag;
  }

}

