package net.clientSide;

import main.Deck;
import main.Main;

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
    private WaitForSubmissionThread waitForSubmissionThread;
    public Client(Deck deck){
        try {
            this.deck = deck;
            socket = new Socket(host, port);
            readThread = new ReadThread(this, deck, socket);
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
        writer.println(Main.getMyTurn()  + ": " + i);
    }
    public void sendTookCard(int i){
        writer.println(Main.getMyTurn() + "tk:" + i);
    }
    public void sendAllPlayersDealt(){
        writer.println("APD");
    }
    //only the host will ever be able to call this method
    public boolean startGame(){
        System.out.println("Start game");
        System.out.println("Enough to start: " + enoughToStart);
        if(enoughToStart){
            System.out.println(true);
            writer.println("S");
            //draw the 10 cards one player at a time, start with the host since they're the only one who'll call this method
            Main.drawTenWhite();
            return true;
        }
        return false;
    }
    public int createGame(){
        leader = true;
        int key = generateKey();
        writer.println(key);
        return key;
    }
    public void sendTurnDone(){
        writer.println("D");
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
    public void sendBlackDrawn(int i){
        writer.println(Main.getMyTurn() + "bd:" + i);
    }
    public void startWaitingForSubmissions(){
        waitForSubmissionThread = new WaitForSubmissionThread(Main.getMaxTurn()-1, Main.getMyTurn() == Main.getWhoTurn());
    }
    public int joinGame(int tag) throws IOException {
        writer.println("J");
        writer.println(tag);
        int status = Integer.parseInt(reader.readLine());
        while(true){
            String response = reader.readLine();
            if(response.length() == 3){
                int numOpponents = Integer.parseInt(response.substring(2));
                System.out.println(numOpponents);
                Main.setMaxTurn(numOpponents);
                if(Main.getMyTurn() == 1){
                    Main.setMyTurn(numOpponents);
                }
                if(numOpponents >= 2){
                    setEnoughToStart();
                }
            }
            if(response.equals("0:10") || response.equals("S") || response.equals("HS")){
                writer.println("S");
                readThread.start();
                break;
            }
        }
        return status;
    }
}
