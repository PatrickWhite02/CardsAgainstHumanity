package net.clientSide;

import main.Main;

public class WaitForSubmissionThread extends Thread{
    int numWaitingFor;
    boolean isMyTurn;
    int currentCount;
    public WaitForSubmissionThread(int numWaitingFor, boolean isMyTurn){
        this.numWaitingFor = numWaitingFor;
        this.isMyTurn = isMyTurn;
        this.currentCount = isMyTurn ? 0:1;
    }
    public void newSubmission(){
        currentCount++;
    }
    public void run(){
        //wait until all submissions are in, then prompt the czar to pick a winner
        while(currentCount != numWaitingFor){

        }
        Main.pickWinner();
    }
}
