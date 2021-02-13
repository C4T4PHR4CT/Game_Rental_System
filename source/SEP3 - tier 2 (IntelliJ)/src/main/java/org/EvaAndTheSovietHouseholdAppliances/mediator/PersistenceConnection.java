package org.EvaAndTheSovietHouseholdAppliances.mediator;

import javax.net.ssl.*;
import java.io.*;

/**responsible for sending/receiving content to/from a tier 1 node*/
public class PersistenceConnection
{

    /**SSL ClientSocket*/
    private SSLSocket ClientSocket;
    /**predefined port for communication*/
    private int Port;
    /**tier 1 node address*/
    private String Host;
    /**PersistenceFormatter instance*/
    private PersistenceFormatter Formatter;

    private PrintWriter Writer;
    private BufferedReader Reader;

    /**starts client socket and instantiates all instance variables*/
    PersistenceConnection(PersistenceFormatter formatter, String host)
    {
        Formatter = formatter;
        Host = host;
        Port = 42070;
        SSLSocketFactory sslsocketfactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
        try
        {
            ClientSocket = (SSLSocket) sslsocketfactory.createSocket(Host, Port);
            ClientSocket.startHandshake();
            Writer = new PrintWriter(ClientSocket.getOutputStream(), true);
            Reader = new BufferedReader(new InputStreamReader(ClientSocket.getInputStream()));
            new Thread(() -> {
                while (true)
                {
                    try
                    {
                        Formatter.ReceiveContent(Reader.readLine());
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

    /**sends requests to tier 1 node*/
    void SendContent(String text)
    {
        Writer.println(text);
    }
}