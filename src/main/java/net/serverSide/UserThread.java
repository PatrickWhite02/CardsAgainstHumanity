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
    private ArrayList<UserThread> opponents = new ArrayList<>();
    public void addOpponent(UserThread ut){
        opponents.add(ut);
    }
    public UserThread(Socket socket, Server server){
        this.socket = socket;
        this.server = server;
    }
    private int tag;
    public void run(){
        try {
            InputStream in = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            OutputStream out = socket.getOutputStream();
            printWriter = new PrintWriter(out, true);
            //first for loop handles game connections, this loop will break once I find a game
            while(true){
                String response = reader.readLine();
                System.out.println(response);
                //signal that user is hosting
                if(response.equals("J")){
                    tag = Integer.parseInt(reader.readLine());
                    System.out.println(tag);
                    if(server.getAllUsersHashMap().containsKey(tag)){
                        if(server.getAllUsersHashMap().get(tag).size() == 10){
                            //error code for game full
                            printWriter.println("-1");
                        }else {
                            System.out.println("Game connected");
                            printWriter.println("1");
                            //print the number of opponents
                            System.out.println("O:" + (server.getAllUsersHashMap().get(tag).size()));
                            printWriter.println("O:" + (server.getAllUsersHashMap().get(tag).size()));
                            //code for game connected
                            for (UserThread ut : server.getAllUsersHashMap().get(tag)) {
                                //let them know someone joined
                                ut.getPrintWriter().println("O:" + (server.getAllUsersHashMap().get(tag).size()));
                                ut.addOpponent(this);
                                addOpponent(ut);
                            }
                            server.getAllUsersHashMap().get(tag).add(this);
                            break;
                        }
                    }else{
                        //code for game doesn't exist
                        printWriter.println("0");
                    }
                }
                //host
                else{
                    tag = Integer.parseInt(response);
                    //second input will be the tag
                    if(!server.getAllUsersHashMap().containsKey(tag)){
                        //print whether or not they were successful
                        printWriter.println("1");
                        ArrayList<UserThread> game = new ArrayList<>();
                        game.add(this);
                        server.getAllUsersHashMap().put(tag, game);
                        //number of opponents
                        printWriter.println("O:0");
                        break;
                    }else{
                        //error code for taken
                        printWriter.println("0");
                    }
                }
            }
            System.out.println("Broke first loop");
            //now that the user has a game, start echoing out to other users
            while(true){
                String response = reader.readLine();
                System.out.println(tag);
                System.out.println("User #" + server.getAllUsersHashMap().get(tag).indexOf(this) + "Sent: " + response);
                //terminate
                if(response.equals("T")){
                    for(UserThread ut : opponents){
                        ut.getPrintWriter().println("UL");
                        break;
                    }
                }
                for(UserThread ut : opponents){
                    if(ut != this){
                        ut.getPrintWriter().println(response);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
