package net.clientSide;

import com.Deck;

import java.io.*;
import java.net.Socket;

public class Client {
    private Deck deck;
    private boolean leader = false;
    public boolean isHost(){
        return leader;
    }
    private String host = "localhost";
    private int port = 8282;
    private Socket socket;
    private OutputStream output;
    private InputStream input;
    private BufferedReader reader;
    public PrintWriter getWriter() {
        return writer;
    }

    public ReadThread getReadThread() {
        return readThread;
    }

    private ReadThread readThread;

    private PrintWriter writer;
    private boolean enoughToStart = false;
    public void setEnoughToStart(){ enoughToStart = true;}
    private WaitForOpponentThread waitForOpponentThread;
    public Client(Deck deck){
        try {
            this.deck = deck;
            socket = new Socket(host, port);
            readThread = new ReadThread(deck, socket);
            output = socket.getOutputStream();
            input = socket.getInputStream();
            reader = new BufferedReader(new InputStreamReader(input));
            writer = new PrintWriter(output, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void sendMove(int i){
        System.out.println("Sending move: " + i);
        writer.println(i);
    }
    //only the host will ever be able to call this method
    public boolean startGame(){
        System.out.println("Start game");
        //if(enoughToStart){
            writer.println("S");
            return true;
       // }
        //return false;
    }
    public int createGame(){
        leader = true;
        int key = generateKey();
        writer.println(key);
        return key;
    }
    public void startWaitForOpponentThread(){
        waitForOpponentThread = new WaitForOpponentThread(socket, this);
        waitForOpponentThread.start();
    }
    private int generateKey(){
        int r = (int) (Math.random() * 8999) + 1000;
        writer.println(r);
        try {
            if(!reader.readLine().equals("0")){
                //if the key is accepted, return the value. Otherwise recur
                return r;
            }else{
                return generateKey();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return -1;
    }
    public int joinGame(int tag) throws IOException {
        writer.println("J");
        writer.println(tag);
        return Integer.parseInt(reader.readLine());
    }
}
