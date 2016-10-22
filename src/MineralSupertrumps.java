/**
 * Created by Sanjeewa on 26/09/2016.
 *
 * Mineral Super Trumps - Main Program
 *
 */
// **
// number of players in myCardsPackPlayer


public class MineralSupertrumps
{


    public static void main( String[] args )
    {

        while ( true )
        {
            // NEW
            // validate
            // - all the images Slide01 - Slide60

            // Read XML card details
            if ( !MyConfig.readCardsDetails( false ) )
            {
                break ;
            }

            // Display Main Screen
            MyDisplay.displayMainScreen() ;


            // end
            System.out.println(MyConfig.myName);
            System.out.println( "-------------Done (With No Errors)------------------" ) ;
            break ;
        }
    }
}
