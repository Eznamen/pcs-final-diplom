import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {


    public static void main(String[] args) throws IOException {
        int cl = 7;
        Scanner scanner = new Scanner(System.in);
        while (true) {
            try (Socket socket = new Socket("127.0.0.1", Main.port);) {
                try (PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                     BufferedReader in = new BufferedReader((new InputStreamReader((socket.getInputStream()))));) {
//                    String answer = in.readLine();
//                    System.out.println(answer);
                    System.out.println("Введите слово для поиска");
                    String word = scanner.nextLine();
                    out.println(word.toLowerCase());
                    String answer = in.readLine();
                    System.out.println(answer);


                }
            }
        }
    }
}