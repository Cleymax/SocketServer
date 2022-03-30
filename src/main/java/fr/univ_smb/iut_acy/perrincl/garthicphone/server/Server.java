package fr.univ_smb.iut_acy.perrincl.garthicphone.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Inet4Address;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Class Server
 * Created on 30/03/2022
 *
 * @author Clément PERRIN <contact@clementperrin.fr>
 */
public class Server {

    public static void main(String[] args) throws IOException {
        Inet4Address ip = (Inet4Address) Inet4Address.getByName("0.0.0.0");
        int port = 3452;
        InetSocketAddress bindpoint = new InetSocketAddress(ip, port);
        new Server(bindpoint,0);
    }

    private ServerSocket serverSocket;

    public Server(InetSocketAddress bindpoint, int timeout) throws IOException {
        try {
            this.serverSocket = new ServerSocket();
            this.serverSocket.bind(bindpoint);
            this.serverSocket.setSoTimeout(timeout);
            while(true) {
                try {
                    Socket socket = this.waitForConnection();
                    byte[] request = this.read(socket);
                    System.out.println(new String(request));
                    socket.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Socket waitForConnection() throws IOException {
        System.out.println("Waiting for connection ...");
        return this.serverSocket.accept();
    }

    public byte[] read(Socket socket) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String s = "";
        do {
            s += br.readLine() + "\n";
        } while (br.ready());

        return s.getBytes();
    }
}
