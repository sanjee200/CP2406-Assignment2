/**
 * Created by Sanjeewa on 26/09/2016.
 *
 * All the common methods used by all the classes
 */

import java.util.Random;
import java.util.Scanner;


public class MyCommon
{


    // User input string
    public static String inputString( String pMessage )
    {
        String myInput ;
        Scanner inputDevice = new Scanner( System.in ) ;

        while ( true )
        {
            System.out.print( pMessage + "\n( Empty to exit ) ==> " ) ;
            myInput = inputDevice.nextLine() ;
            if (!myInput.isEmpty())
            {
                break ;
            }
            if ( inputYesNo("") )
            {
                break ;
            }
        }
        return myInput ;
    }


    // User input integer
    public static int inputInteger( String pMessage, int pMinimum, int pMaximum )
    {
        int myInput ;
        Scanner inputDevice = new Scanner( System.in ) ;

        while ( true )
        {
            try {

                System.out.print(pMessage
                    + "\n( Integer between "
                    + pMinimum + " and " + pMaximum
                    + ", 0 to exit ) ==> "
                );

                myInput = inputDevice.nextInt() ;
                if ( myInput >= pMinimum && myInput <= pMaximum )
                {
                    break ;
                }
                if (myInput == 0)
                {
                    if ( inputYesNo("") )
                    {
                        break ;
                    }
                    else
                    {
                        continue ;
                    }
                }

                System.out.println(" * has to be between "
                    + pMinimum + " and " + pMaximum);
            }

            catch (Exception e)
            {
                System.out.println(" * not an integer value");
                inputDevice.nextLine() ;
            }
            finally
            {
                // nothing
            }

        }
        return myInput ;
    }

    // Validate string
    public static String validateString( String pValue )
    {
        String myMessage = "" ;
        if (pValue.isEmpty())
        {
            myMessage = "Name cannot be empty" ;
        }
        return myMessage ;
    }

    // Validate integer
    public static String validateInteger(String pValue, String pValueName, int pMinimum, int pMaximum )
    {
        String myMessage = "" ;
        int myValue ;

        while ( true )
        {
            if (pValue.isEmpty())
            {
                myMessage = pValueName +  " cannot be empty" ;
                break ;
            }
            try {
                myValue = Integer.parseInt( pValue ) ;
                if ( myValue >= pMinimum && myValue <= pMaximum )
                {
                    break ;
                }
                myMessage = pValueName + " has to be between "
                    + pMinimum + " and " + pMaximum ;
            }

            catch (Exception e)
            {
                myMessage = "'" + pValue + "' for "+ pValueName +" has to be an integer" ;
            }
            finally
            {
                // nothing
            }
            break ;
        }
        return myMessage ;
    }

    // User input Yes or No
    public static boolean inputYesNo(String pMessage)
    {
        char myInput ;
        String myMessage = "" ;
        Scanner inputDevice = new Scanner( System.in ) ;

        myMessage =  pMessage == "" ? "Do you want to exit" : pMessage ;
        myMessage += " (Y,N) ==> " ;

        while ( true )
        {
            System.out.print( myMessage ) ;
            myInput = inputDevice.next().charAt(0) ;

            if ( myInput == 'Y' || myInput == 'N' ||
                 myInput == 'y' || myInput == 'n' )
            {
                break ;
            }
            System.out.println( "Input 'Y' or 'N'" ) ;
        }
        return ( myInput == 'Y' || myInput == 'y' ) ;
    }


    // Search for a character and returns the rest of the string
    public static String findRest( String pString, String pFindChar )
    {
        int myFound = -1 ;
        String myRest ;

        for (int i=0; i<pFindChar.length(); i++)
        {
            myFound = pString.indexOf( pFindChar.charAt(i) ) ;
            if ( myFound >= 0 )
            {
                break ;
            }
        }
        myRest = pString.substring( myFound + 1 ) ;
        return ( myRest ) ;
    }


    // Search for an integer in an array
    public static int findInteger( int[] pArray, int pValue )
    {
        int returnValue = -1 ;
        for (int i=0; i< pArray.length; i++)
        {
            if ( pArray[i] == pValue )
            {
                returnValue = i ;
                break ;
            }
        }
        return ( returnValue ) ;
    }


    // Search for an string in an array
    public static int findString( String[] pArray, String pValue )
    {
        int returnValue = -1 ;
        for (int i=0; i< pArray.length; i++)
        {
            if ( pArray[i].equals( pValue ) )
            {
                returnValue = i ;
                break ;
            }
        }
        return ( returnValue ) ;
    }


    // Generates a random number between a given range
    public static int randomInt(Random myRandom, int pFrom, int pUpto)
    {
        // double myRange  = pUpto - pFrom + 1 ;
        // double myValue  = myRange * myRandom.nextDouble() ;
        // int    myReturn = (int)(myValue + pFrom) ;
        //return myReturn ;
        return ( myRandom.nextInt((pUpto - pFrom) + 1) + pFrom ) ;
    }


}
