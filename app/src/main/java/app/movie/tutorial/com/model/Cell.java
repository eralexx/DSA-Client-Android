package app.movie.tutorial.com.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Cell {

     @SerializedName("sizeX")
     int CellType; //0 = normal, 1= objective, 2= player start
    @SerializedName("posX")
     int PosX;
    @SerializedName("posY")
     int PosY;
    @SerializedName("generatorNum")
     int GeneratorNum;
    @SerializedName("userInCell")
     User UserInCell =  new User();
    @SerializedName("moves")
     List<Character> Moves =  new ArrayList<>(3);

     public Cell(int cellType, int posX, int posY, int generatorNum) {
          CellType = cellType;
          PosX = posX;
          PosY = posY;
          GeneratorNum = generatorNum;
     }
     public int getCellType() {
          return CellType;
     }

     public void setCellType(int cellType) {
          CellType = cellType;
     }

     public int getPosX() {
          return PosX;
     }

     public void setPosX(int posX) {
          PosX = posX;
     }

     public int getPosY() {
          return PosY;
     }

     public void setPosY(int posY) {
          PosY = posY;
     }

    public int getGeneratorNum() {
        return GeneratorNum;
    }

    public void setGeneratorNum(int generatorNum) {
        GeneratorNum = generatorNum;
    }

    public List<Character> getMoves() {
        return Moves;
    }

    public void setMoves(List<Character> moves) {
        Moves = moves;
    }

    public User getUserInCell() {
        return UserInCell;
    }

    public void setUserInCell(User userInCell) {
        UserInCell = userInCell;
    }

    public Cell(){}
}
