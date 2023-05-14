package Model;


import java.io.Serializable;

public class Word implements Serializable {
    public Tile[] tiles;
    public boolean vertical;
    int row,col;

    Word(Tile[] _tiles, int _row, int _col, boolean _vertical)
    {
        tiles=(Tile[])_tiles.clone();

        row=_row;
        col=_col;
        vertical=_vertical;
    }
    Tile[] getTiles(){return tiles;}
    int getRow(){return row;}

    public int getCol() {
        return col;
    }

    public boolean isVertical() {
        return vertical;
    }


    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}