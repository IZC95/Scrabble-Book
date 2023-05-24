package Test;

import Model.Guest;
import Model.Host;

public class GuestTest {
    public static void main(String[] args) {

        /* In our project we ask from the user a name to identify him in the game  */
        /* If a name is already taken, the user will be asked to try another name */
        /* If the session is full, the user will be kicked from the session*/
        Host host = new Host();
        Guest g1 = new Guest("localhost", 8082);
        Guest g2 = new Guest("localhost", 8082);
        Guest g3 = new Guest("localhost", 8082);
        /*From this section the session is full*/
        Guest g4 = new Guest("localhost", 8082);

        host.startGame();
        /* we are not sure at the moment how to bind the host's startGame() to the other guests startGame() */
        /* so we manually started each guests method*/
        g1.startGame();
        g2.startGame();
        g3.startGame();

        char[] tiles1= g1.getTiles();
        char[] tiles2= g2.getTiles();
        char[] tiles3= g3.getTiles();
        char[] tiles4= host.getTiles();


        if (tiles1==null)
            System.out.println("Error by getting tiles from host");
        char[] ex=new char[3];
        ex[0]=tiles1[0];
        ex[1]=tiles1[1];
        ex[2]=tiles1[2];
        int result= g1.tryPlaceWord(g1.name,0,0,true,ex);
        /*At the moment dictionaryLegal() returns true for the testing*/
        ex[0]=tiles2[0];
        ex[1]=tiles2[1];
        ex[2]=tiles2[2];
         result+= g2.tryPlaceWord(g1.name,0,0,true,ex);
        ex[0]=tiles3[0];
        ex[1]=tiles3[1];
        ex[2]=tiles3[2];
         result+= g3.tryPlaceWord(g1.name,0,0,true,ex);
        ex[0]=tiles4[0];
        ex[1]=tiles4[1];
        ex[2]=tiles4[2];
         result+= host.tryPlaceWord(g1.name,0,0,true,ex);


        if(result==0)
            System.out.println("Error tryPlaceWord");

        ex[0]=tiles1[4];
        ex[1]=tiles1[5];
        ex[2]=tiles1[6];
        result=g1.challenge(g1.name,10,10,true,ex);

        ex[0]=tiles2[4];
        ex[1]=tiles2[5];
        ex[2]=tiles2[6];
        result+=g2.challenge(g2.name,10,10,true,ex);

        ex[0]=tiles3[4];
        ex[1]=tiles3[5];
        ex[2]=tiles3[6];
        result+=g3.challenge(g3.name,10,10,true,ex);

        ex[0]=tiles4[4];
        ex[1]=tiles4[5];
        ex[2]=tiles4[6];
        result+=host.challenge(host.name,10,10,true,ex);

        if(result==0)
            System.out.println("Error by challenging a word from the guest");

        g1.endGame();
        g2.endGame();
        g3.endGame();
        ex=new char[1];
        ex[0]=tiles1[3];
         result= g1.tryPlaceWord(g1.name,5,5,true,ex);
        ex[0]=tiles2[3];
        result+= g2.tryPlaceWord(g2.name,5,5,true,ex);
        ex[0]=tiles3[3];
        result+= g3.tryPlaceWord(g3.name,5,5,true,ex);

        if(result>0)
            System.out.println("Problem by endGame()");
        /* we know the test are not good as they should be, but we will put more effort on it during the semester*/

    }

}
