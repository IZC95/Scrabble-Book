package Model;


import java.io.Serializable;
import java.util.Arrays;

public class Word implements Serializable {
    public Tile[] tiles;
    public boolean vertical;
    int row,col;
Word(){
    tiles=null;
    row=-1;
    col=-1;
    vertical=false;
}
    Word(Tile[] _tiles, int _row, int _col, boolean _vertical)
    {
        tiles=_tiles;

        row=_row;
        col=_col;
        vertical=_vertical;
    }

    @Override
    public String toString() {
    String word="";
    for(Tile t:tiles)
        word+=t.letter;

        return word;
    }

    Tile[] getTiles(){return tiles;}
    int getRow(){return row;}

    public int getCol() {
        return col;
    }

    public boolean isVertical() {
        return vertical;
    }

    public void setTiles(Tile[] tiles) {
        this.tiles = tiles;
    }

    public void setVertical(boolean vertical) {
        this.vertical = vertical;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setCol(int col) {
        this.col = col;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
