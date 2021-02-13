package mediator;

import javax.net.ssl.*;
import java.io.IOException;
import java.util.ArrayList;

/**responsible for instantiating new ClientHandler Threads for every incoming connection from Tier 2 nodes*/
public class PersistenceServer
{

    /**SSL ServerSocket to handle create SSL connections*/
    private SSLServerSocket ServerSocket;
    /**predefined port for communication*/
    private int Port;
    /**RequestInterpreter instance*/
    private RequestInterpreter Interpreter;
    /**all active ClientHandler Threads*/
    private ArrayList<Thread> Clients;

    /**starts the server socket and instantiates new ClientHandler Threads for every incoming connection from Tier 2 nodes*/
    PersistenceServer(RequestInterpreter interpreter)
    {
        Port = 42070;
        Interpreter = interpreter;
        Clients = new ArrayList<>();
        SSLServerSocketFactory sslserversocketfactory = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
        try
        {
            ServerSocket = (SSLServerSocket) sslserversocketfactory.createServerSocket(Port);
            new Thread(() -> {
                while(true)
                {
                    try
                    {
                        ClientHandler client = new ClientHandler(ServerSocket.accept(), Interpreter);
                        Clients.add(client);
                        client.start();
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /**sends reply back to the same Tier 2 node where the request came from in the first place*/
    void SendContent(String text)
    {
        for (Thread client : Clients)
            if (client.equals(Thread.currentThread())) {
                ((ClientHandler) client).SendContent(text);
                System.out.println( "[SENT] " + text);
                break;
            }
    }
}