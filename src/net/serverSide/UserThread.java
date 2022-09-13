package net.serverSide;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class UserThread extends Thread{
    Socket socket;
    Server server;

    public PrintWriter getPrintWriter() {
        return printWriter;
    }

    private PrintWriter printWriter;
    private ArrayList<UserThread> opponents;
    public void addOpponent(UserThread ut){
        opponents.add(ut);
    }
    public UserThread(Socket socket, Server server){
        this.socket = socket;
        this.server = server;
    }
    public void run(){
        try {
            InputStream in = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            OutputStream out = socket.getOutputStream();
            printWriter = new PrintWriter(out, true);
            //first for loop handles game connections, this loop will break once I find a game
            while(true){
                String response = reader.readLine();
                int tag = Integer.parseInt(reader.readLine());
                //signal that user is hosting
                if(response.equals("H")){
                    //second input will be the tag
                    if(!server.getAllUsersHashMap().containsKey(tag)){
                        //print whether or not they were successful
                        printWriter.println("1");
                        ArrayList<UserThread> game = new ArrayList<>();
                        game.add(this);
                        server.getAllUsersHashMap().put(tag, game);
                        break;
                    }else{
                        //error code for taken
                        printWriter.println("0");
                    }
                }else{
                    if(server.getAllUsersHashMap().containsKey(tag)){
                        if(server.getAllUsersHashMap().get(tag).size() == 10){
                            //error code for game full
                            printWriter.println("-1");
                        }else {
                            //code for game connected
                            printWriter.println("1");
                            for (UserThread ut : server.getAllUsersHashMap().get(tag)) {
                                ut.addOpponent(this);
                                addOpponent(ut);
                                //let them know someone joined
                                ut.getPrintWriter().println("OJ");
                            }
                            break;
                        }
                    }else{
                        //code for game doesn't exist
                        printWriter.println("0");
                    }
                }
            }
            //now that the user has a game, start echoing out to other users
            while(true){
                String response = reader.readLine();
                //terminate
                if(response.equals("T")){
                    for(UserThread ut : opponents){
                        ut.getPrintWriter().println("UL");
                        break;
                    }
                }
                for(UserThread ut : opponents){
                    ut.getPrintWriter().println(response);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
