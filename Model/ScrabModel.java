package Model;

public interface ScrabModel {

    int tryPlaceWord(String name,int row,int col,boolean vertical,char[] _tiles);
    int challenge(String name,int row,int col,boolean vertical,char[] _tiles);
    public char[] getTiles();
    void endTurn();
    void startGame();
    void endGame();
}
