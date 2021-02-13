package mediator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**responsible for sending/receiving content to/from a given tier 2 node*/
public class ClientHandler extends Thread
{

    /**request interpreter instance (received content forwarded here)*/
    private RequestInterpreter Interpreter;

    private Socket Socket;
    private BufferedReader Reader;
    private PrintWriter Writer;

    /**takes RequestInterpreter which determines where it forwards the incoming messages and a Socket which serves as the actual connection to the Tier 2 node*/
    ClientHandler(Socket socket, RequestInterpreter interpreter)
    {
        Interpreter = interpreter;
        Socket = socket;
        try
        {
            Reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            Writer = new PrintWriter(socket.getOutputStream(), true);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**Thread's run() method, which is responsible for receiving incoming requests from a tier 2 node*/
    @Override
    public void run()
    {
        System.out.println("[NEW CLIENT] " + super.getName());
        while (true)
        {
            try
            {
                String temp = Reader.readLine();
                if (temp == null)
                    break;
                Interpreter.ReceiveContent(temp);
            }
            catch (IOException e)
            {
                //e.printStackTrace();
            }
        }
        System.out.println("[CLIENT DISCONNECTED] " + super.getName());
    }

    /**sends a message to a tier 2 node*/
    void SendContent(String text)
    {
        Writer.println(text);
    }
}
