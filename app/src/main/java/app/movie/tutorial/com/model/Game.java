package app.movie.tutorial.com.model;

import com.google.gson.annotations.SerializedName;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class Game {

    @SerializedName("id")
    private int Id;

    @SerializedName("nPlayers")
    private int nPlayers;

    @SerializedName("playerTurn")
    private User PlayerTurn;

    @SerializedName("players")
    private List<User> Players;

    @SerializedName("winner")
    private String Winner;

    @SerializedName("timeStamp")
    private String timeStamp ;

    @SerializedName("board")
    private Board Board;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getnPlayers() {
        return nPlayers;
    }

    public void setnPlayers(int nPlayers) {
        this.nPlayers = nPlayers;
    }

    public User getPlayerTurn() {
        return PlayerTurn;
    }

    public void setPlayerTurn(User playerTurn) {
        PlayerTurn = playerTurn;
    }

    public List<User> getPlayers() {
        return Players;
    }

    public void setPlayers(List<User> players) {
        Players = players;
    }

    public String getWinner() {
        return Winner;
    }

    public void setWinner(String winner) {
        Winner = winner;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public Board getBoard() {
        return Board;
    }

    public void setBoard(Board board) {
        Board = board;
    }



    public Game(int id, int nPlayers, User playerTurn, List<User> players, String winner, String timeStamp, app.movie.tutorial.com.model.Board board) {
        Id = id;
        this.nPlayers = nPlayers;
        PlayerTurn = playerTurn;
        Players = players;
        Winner = winner;
        this.timeStamp = timeStamp;
        Board = board;
    }

    public Game(List<User> Players){
        this.Players= Players;
        this.nPlayers= Players.size();
        this.timeStamp = new SimpleDateFormat("dd/MM/yyyy - HH:mm").format(Calendar.getInstance().getTime());
        this.Board = new Board(Players);
        this.PlayerTurn=this.Players.get(0);
    }

    public boolean Move(char Direction , User player ){
        boolean moved= false;
        //if (CheckMovementPosible(Direction , player)){
        while (moved==false) {
            switch (Direction) {
                case 'S':
                    this.Board.MoveSouth(player);
                    moved=true;
                    if (!isGameWon())
                        NextTurn();
                    break;
                case 'N':
                    this.Board.MoveNorth(player);
                    moved=true;
                    if (!isGameWon())
                        NextTurn();
                    break;
                case 'E':
                    this.Board.MoveEast(player);
                    moved=true;
                    if (!isGameWon())
                        NextTurn();
                    break;
                case 'W':
                    this.Board.MoveWest(player);
                    moved=true;
                    if (!isGameWon())
                        NextTurn();
                    break;
            }
        }
        //}
        return moved;
    }
    private boolean isGameWon(){
     Cell EndCell=this.Board.getWinningCell();
     Cell CurrentCell=this.Board.getPlayerPosition(this.PlayerTurn); //Esto no se actualiza
     if (EndCell==CurrentCell){
         this.Winner=PlayerTurn.getEmail();
         for (User player: this.Players) {
             player.setGamesPlayed(player.getGamesPlayed()+1);
         }
         this.PlayerTurn.setGamesWon( this.PlayerTurn.getGamesWon()+1);
         System.out.println("Player "+this.PlayerTurn.getUserName() + " has won the game.");
         return true;
     }
     else {
         return false;
     }
    }
    private void EndGame(){

    }
    /*private boolean CheckMovementPosible(char Direction , User player ){
        if (this.Board.checkPosibleMoves(player) !=null){
            if (this.Board.checkPosibleMoves(player).contains(Direction) && this.PlayerTurn==player){
                return true;
            }
        }
        return false;
    }*/

    private void NextTurn(){
        for(int i=0; i< this.Players.size(); i++){
            if (this.Players.get(i) == this.PlayerTurn){
                if (i==this.Players.size()-1) {
                    this.PlayerTurn = this.Players.get(0);
                    return;
                }
                else{
                    this.PlayerTurn=this.Players.get(i+1);
                    return;
                }
            }
        }
    }
    @Override
    public String toString() {
        return ("Date: " + this.timeStamp + ".    Won by: " + this.Winner + ".    " + this.nPlayers+ " players game.");
    }
    public Game(){}

}
