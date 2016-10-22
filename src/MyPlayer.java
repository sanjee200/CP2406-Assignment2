/**
 * Created by Sanjeewa on 29/09/2016.
 *
 * Player related methods
 *      myCardsPackPlayers[]
 */

import javax.swing.*;
import java.util.ArrayList;


public class MyPlayer
{
    ArrayList<Integer> myCardsPack = new ArrayList<Integer>() ;

    // Append a number
    public void myAdd( int pCardNumber )
    {
        this.myCardsPack.add( pCardNumber ) ;
    }


    // Remove element
    public void myRemove( int pCardIndex )
    {
        this.myCardsPack.remove( pCardIndex ) ;
    }


    // Return the number in the element
    public int myGet( int pCardIndex )
    {
        return ( this.myCardsPack.get( pCardIndex ) ) ;
    }


    // Return the length of the array
    public int myLength()
    {
        return ( myCardsPack.size() ) ;
    }


    // Return whether empty
    public boolean myIsEmpty()
    {
        return ( myCardsPack.isEmpty() ) ;
    }


    // Display the players card numbers (Number of the card  related to the myCardsPackMain)
    public void displayAllCardNumbers()
    {
        for ( int cardNumberMain : myCardsPack )
        {
            System.out.print( cardNumberMain + "," ) ;
        }
        System.out.println( "" ) ;
    }


    // Display details of All the cards
    public void displayAllCardDetails()
    {
        int j=1 ;
        for ( int cardNumberMain : myCardsPack )
        {
            System.out.println("Card " + j ) ;
            //System.out.println("Card Number : " + i  ) ;
            //System.out.println( MyConfig.myCardsPackMain[i].fileName ) ;
            MyConfig.myCardsPackMain[cardNumberMain].display() ;

            j++ ;
        }

        System.out.println("");
    }


    // Display Title, Category, Top Value of given card (pCardIndex)
    public void displayCardCategory(int pCardIndex, int pCategoryNumber )
    {
        // pCardIndex  => 0 to NoOfCards-1 (Selected card)
        // myCardsPack => 0 to NoOfCards-1
        //    [0-x ] => has all the players card numbers
        // myCardsPack[pCardIndex] => The number of the myCardsPackMain
        // myCardsPackMain[ myCardsPack[pCardIndex] ]
        //    [1-60] => has all the details of 60 cards

        int myCardNumberMain = myCardsPack.get( pCardIndex ) ;
        MyConfig.myCardsPackMain[ myCardNumberMain ].displayCategory( pCategoryNumber, myCardsPack.size()-1 ) ;
    }


    public double getCategoryValue( int pCardIndex, int pCategoryNumber )
    {
        // pCardIndex  => 0 to NoOfCards-1 (Selected card)
        // myCardsPack => 0 to NoOfCards-1
        //    [0-x ] => has all the players card numbers
        // myCardsPack[pCardIndex] => The number of the myCardsPackMain
        // myCardsPackMain[ myCardsPack[pCardIndex] ]
        //    [1-60] => has all the details of 60 cards

        double myValue ;
        int myCardNumberMain = myCardsPack.get( pCardIndex ) ;
        myValue = MyConfig.myCardsPackMain [ myCardNumberMain ].getCategoryValue( pCategoryNumber ) ;
        return myValue ;
    }


    public int isCategoryHigherAll( int pCategoryNumber, double pCurrentValue )
    {
        boolean isHigher = false ;
        int myCardIndex ;
        for ( myCardIndex = 0; myCardIndex < myCardsPack.size(); myCardIndex++ )
        {
            isHigher = isCategoryHigherOne(
                myCardIndex , pCategoryNumber, pCurrentValue ) ;
            if ( isHigher )
            {
                break ;
            }
        }
        myCardIndex = isHigher ? myCardIndex : -1 ;
        return myCardIndex ;
    }

    public boolean isCategoryHigherOne(int pCardIndex, int pCategoryNumber, double pCurrentValue )
    {
        boolean isHigher =
            MyConfig.myCardsPackMain[ myCardsPack.get( pCardIndex ) ]
            .getCategoryValue(pCategoryNumber)
            > pCurrentValue ;
        return isHigher ;
    }

}
