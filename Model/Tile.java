package Model;

import java.util.Objects;
import java.util.Random;

final class Tile {
	 public char letter;
     public int score;
     private static Bag baggy;
private Tile(char _letter, int _score)
{

    letter=_letter;
    score=_score;
}
public static class Bag {
    public int amount;
    public int[] value;
    public Tile[] tiles;

    private Bag() {
        amount = 98;
        value = new int[26];
        tiles = new Tile[26];
        int count = 0;
        for (char i = 'A'; i <= 'Z'; i++
        ) {
            if (i == 'B' || i == 'C' || i == 'F' || i == 'H' || i == 'M' || i == 'V' || i == 'P' || i == 'W' || i == 'Y')
                value[count] = 2;
            if (i == 'E')
                value[count] =12 ;
            if (i == 'D' || i == 'L' || i == 'S' || i == 'U')
                value[count] = 4;
            if (i == 'J' || i == 'K' || i == 'Q' || i == 'Z' || i == 'X')
                value[count] = 1;
            if (i == 'G')
                value[count] = 3;
            if (i == 'R' || i == 'N'||i == 'T')
                value[count] =6;
            if (i == 'A' || i == 'I')
                value[count] = 9;
            if (i == 'O')
                value[count] = 8;

            count++;

        }
         count=0;
            for (char i = 'A'; i <= 'Z'; i++
        ) {
            if (i == 'A' || i == 'E' || i == 'N' || i == 'L' || i == 'I' || i == 'O' || i == 'R' || i == 'S' || i == 'T'||i=='U')
                tiles[count] = new Tile(i, 1);
            if (i == 'D'||i=='G')
                tiles[count] = new Tile(i, 2);
            if (i == 'B' || i == 'C' || i == 'P' || i == 'M')
                tiles[count] = new Tile(i, 3);
            if (i == 'H' || i == 'F' || i == 'Y' || i == 'V' || i == 'W')
                tiles[count] = new Tile(i, 4);
            if (i == 'K')
                tiles[count] = new Tile(i, 5);
            if (i == 'X' || i == 'J')
                tiles[count] = new Tile(i, 8);
            if (i == 'Z' || i == 'Q')
                tiles[count] = new Tile(i, 8);


            count++;
        }
    }

    public Tile getRand() {
        Random rand=new Random();
         int flag=0;
         int rnd=0;
         while(amount>0) {
              rnd = rand.nextInt(25);

             if (value[rnd] > 0) {
                 value[rnd]--;
                 amount--;
                 flag=1;
                 break;
             }
         }
            return tiles[rnd];
    }

    public Tile getTile(char s) {
        int count = 0;
        if(s>='A'&&s<='Z')
            for (Tile a : tiles) {

            if (a.letter == s && value[count] > 0) {

                value[count]--;
                amount--;
                return tiles[count];
            }
            count++;
        }
        return null;
    }


    public void put(Tile t) {
        if (amount < 98) {
            int count = 0;
            for (Tile a : tiles) {
                if(a!=null)
                    if (a.letter == t.letter) {
                    value[count]++;
                    amount++;
                    break;
                }
                count++;
            }
        }
    }
    public int size(){return amount;}
    public int[] getQuantities()
    {
        int[] c=(int[])value.clone();
        return c;
    }
    public static Bag getBag()
    {
        if(baggy==null)
            baggy=new Bag();
        return baggy;

    }
}
@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tile tile = (Tile) o;
        return letter == tile.letter && score == tile.score;
    }

    @Override
    public int hashCode() {
        return Objects.hash(letter, score);
    }

}


