package Model;


import java.util.ArrayList;

public class Board {

    private static Board board;

    Tile[][] t;
    int[][] hit;
    ArrayList<Word> discovered;
    boolean star;

    private Board() {
        t = new Tile[14][14];
        hit = new int[14][14];
        discovered = new ArrayList<Word>();
        star = true;
    }

    public Tile[][] getTile() {

        return t;
    }

    public static Board getBoard() {

        if (board == null)
            board = new Board();
        return board;
    }

    boolean boardLegal(Word w) {
        int i;
        if (t[7][7] == null) {
            if (!w.isVertical())
                if (w.col < 7 && w.row == 7 && w.col + w.getTiles().length >= 7
                        && w.col + w.getTiles().length <= 14 && w.col >= 0)
                    return true;
            if (w.isVertical())
                if (w.row < 7 && w.col == 7 && w.row + w.getTiles().length >= 7
                        && w.row + w.getTiles().length <= 14 && w.row >= 0)
                    return true;

            return false;

        }
        int flag = 0;

        if (
                w.row + w.getTiles().length <= 14 && w.row >= 0
                        && w.col + w.getTiles().length <= 14 && w.col >= 0) {
            if (!w.isVertical()) {
                i = w.col;

                for (int k = 0; k < w.getTiles().length; k++, i++) {

                    if (t[w.row][i] != null && w.getTiles()[k] == null)
                        flag = 1;
                    if (t[w.row - 1][i] != null || t[w.row + 1][i] != null)
                        flag = 1;
                }
            }
            if (w.isVertical()) {
                i = w.col;

                int c = 0;
                for (int k = w.row; k < w.getTiles().length + w.row; c++, k++) {
                    if (t[k][i] != null && w.getTiles()[c] == null)
                        flag = 1;
                    if (t[k][i - 1] != null || t[k][i + 1] != null)
                        flag = 1;
                }

            }
        }
        if (flag == 0)
            return false;
        else return true;
    }

    boolean dictionaryLegal(Word w) {
        return true;
    }

    ArrayList<Word> getWords(Word w) {
        ArrayList<Word> arr = new ArrayList<Word>();
        int k, i, m, c, row, col, flag = 1,len;

        arr.add(w);
        Tile[] temp=new Tile[15];
        if (t[7][7] == null)
            return arr;

        i = w.col;
        if (!w.isVertical()) {
            for (k = 0; k < w.getTiles().length; k++, i++)
                if (t[w.row - 1][i] == null && t[w.row + 1][i] != null) {
                    m = 0;
                    c = w.row;
                    row = w.row;
                    col = i;
                    len=0;
                    while (t[c][i] != null) {
                        temp[m] = t[c][i];
                        m++;
                        c++;
                        len++;
                    }
                    Tile[] wo=new Tile[len];
                    System.arraycopy(temp, 0, wo, 0, len);

                    Word word = new Word(wo, row, col, true);
                    if (dictionaryLegal(word)) {
                        if (discovered != null)
                            for (Word a : discovered)
                                if (word.row == a.row && word.col == a.col
                                        && word.tiles.length == a.tiles.length)
                                    flag = 0;


                        if (flag == 1) {
                            //discovered.add(word);
                            arr.add(word);
                        }
                        flag=1;
                    }
                }
            else if (t[w.row - 1][i] != null) {
                        m = 0;
                        c = w.row;
                        while (t[c-1][i] != null)
                            c--;
                        len=0;
                        row = c;
                        col = i;
                        while (t[c][i] != null) {
                            temp[m] = t[c][i];
                            m++;
                            c++;
                            len++;
                        }
                    Tile[] wo=new Tile[len];
                    System.arraycopy(temp, 0, wo, 0, len);
                        Word word = new Word(wo, row, col, true);
                        if (dictionaryLegal(word)) {
                            if (discovered != null)
                                for (Word a : discovered)
                                    if (word.row == a.row && word.col == a.col
                                            && word.tiles.length == a.tiles.length) {
                                        flag = 0;
                                        break;
                                    }
                            if (flag == 1) {
                                //discovered.add(word);
                                arr.add(word);
                            }
                            flag=1;
                        }
                    }
                }
        else {
                        i = w.row;
                        for (k = 0; k < w.getTiles().length; k++, i++)
                            if (t[i][w.col - 1] == null && t[i][w.col + 1] != null) {
                                m = 0;
                                len=0;
                                c = w.col;
                                row = i;
                                col = w.col;
                                while (t[i][c] != null) {
                                    temp[m] = t[c][i];
                                    m++;
                                    c++;
                                    len++;
                                }
                                Tile[] wo=new Tile[len];
                                System.arraycopy(temp, 0, wo, 0, len);
                                Word word = new Word(wo, row, col, false);
                                if (dictionaryLegal(word)) {
                                    if (discovered != null)
                                        for (Word a : discovered)
                                            if (word.row == a.row && word.col == a.col
                                                    && word.tiles.length == a.tiles.length) {
                                                flag = 0;
                                                break;
                                            }
                                    if (flag == 1) {
                                        //discovered.add(word);
                                        arr.add(word);
                                    }
                                    flag=1;
                                }
                            }
                        else if (t[i][w.col - 1] != null) {
                                    m = 0;
                                    c = w.col;
                                    while (t[i][c-1] != null)
                                        c--;
                                    len=0;
                                    row = i;
                                    col = c;
                                    while (t[i][c] != null) {
                                        temp[m] = t[i][c];
                                        m++;
                                        c++;
                                        len++;
                                    }
                                Tile[] wo=new Tile[len];
                                System.arraycopy(temp, 0, wo, 0, len);
                                    Word word = new Word(wo, row, col, false);
                                    if (dictionaryLegal(word)) {
                                        if (discovered != null)
                                            for (Word a : discovered)
                                                if (word.row == a.row && word.col == a.col
                                                        && word.tiles.length == a.tiles.length) {
                                                    flag = 0;
                                                    break;
                                                }


                                        if (flag == 1) {
                                            //discovered.add(word);
                                            arr.add(word);
                                        }
                                        else
                                            flag=1;
                                    }

                                }
                            }
                        return arr;
                    }
    int getScore(Word w) {

        ArrayList<Word> words = new ArrayList<Word>(getWords(w));

        int finalScore = 0;


        for (Word value : words) {

            int res = 0;
            int tripleWord = 0, doubleWord = 0;
            int c = value.row;
            int i = value.col;
            for (int k = 0; k < value.getTiles().length; k++) {

                if(true) {
                    res += t[c][i].score;
                    if (i == 0 || i == 7 || i == 14) {
                        if (c == 0 || c == 7 || c == 14) {
                            tripleWord++;

                            if (i == 7 && c == 7) {
                                tripleWord--;
                                if(star) {
                                    doubleWord++;
                                    star = false;
                                }
                            }
                        }
                        if (c == 3 || c == 11) {
                            res += t[c][i].score;

                        }
                    }
                    if (i == 1 || i == 13) {
                        if (c == 13 || c == 1) {
                            doubleWord++;
                        }
                        if (c == 5 || c == 9) {
                            res += 2 * t[c][i].score;
                        }
                    }
                    if (i == 2 || i == 12) {
                        if (c == 2 || c == 12) {
                            doubleWord++;
                        }
                        if (c == 6 || c == 8) {
                            res += t[c][i].score;
                        }

                    }
                    if (i == 3 || i == 11) {
                        if (c == 3 || c == 11) {
                            doubleWord++;
                        }
                        if (c == 7 || c == 14 || c == 0) {
                            res += t[c][i].score;
                        }
                    }
                    if (i == 4 || i == 10) {
                        if (c == 4 || c == 10) {
                            doubleWord++;
                        }
                    }
                    if (i == 5 || i == 9) {
                        if (c == 1 || c == 5 || c == 9 || c == 13) {
                            res += 2 * t[c][i].score;
                        }

                    }
                    if (i == 6 || i == 8) {
                        if (c == 2 || c == 6 || c == 8 || c == 12) {
                            res += t[c][i].score;
                        }
                    }
                    //hit[c][i] = 1;
                    if (!value.isVertical())
                        i++;
                    else c++;
                }
            }

                if (doubleWord > 0)
                    res = 2 * doubleWord * res;
                if (tripleWord > 0)
                    res = 3 * tripleWord * res;
                int flag = 1;
                if (discovered != null)
                    for (Word word : discovered)
                        if (value.row == word.row && value.col == word.col
                                && value.tiles.length == word.tiles.length)
                            flag = 0;


                if (flag == 1) {

                    discovered.add(value);
                    finalScore += res;
                }
            }
        return finalScore;
        }



    int tryPlaceWord(Word w)
    {
        int res=0;
        if (boardLegal(w))
        {

            if(!w.isVertical()) {

                int i=w.col;


                for ( int k = 0; k < w.getTiles().length; k++,i++) {
                    if(t[w.getRow()][i]==null) {
                        t[w.getRow()][i] = w.getTiles()[k];
                    }
                }
                res=getScore(w);
            }
            else {
                int c=w.getRow();

                int i=w.col;


                for(int k=0;k<w.getTiles().length;k++,c++)
                    if(t[c][i]==null)
                        t[c][i]=w.getTiles()[k];

                res=getScore(w);


            }

        }
        return res;
    }
}
