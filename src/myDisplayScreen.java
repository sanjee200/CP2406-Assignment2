import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

/**
 * Created by Sanjeewa on 22/10/2016.
 */

public class myDisplayScreen extends JFrame implements ActionListener, MouseListener
{

    // Create all the players card pack
    // 0.Physical 1-5. Computer
    protected MyPlayer[] myCardsPackPlayers = new MyPlayer[ MyConfig.NUMBER_OF_PLAYERS_MAXIMUM + 1 ] ; // Max + 1

    protected int myDealer = 0 ;                    // Select the player to deal first
    protected Random myRandom = new Random() ;      // Random generator, only once


    // Panels
    JPanel panelInput = new JPanel(new GridLayout(5,2)) ;
    JPanel panelCardsSelect = new JPanel() ;
    JPanel panelCardsDisplay = new JPanel() ;

    // Controls
    JLabel labName      = new JLabel("Your name : ") ;
    JLabel labPlayers   = new JLabel("Number of players ( "
        + MyConfig.NUMBER_OF_PLAYERS_MINIMUM + " - " + MyConfig.NUMBER_OF_PLAYERS_MAXIMUM +  " ) : ") ;
    JLabel labShuffle   = new JLabel("Number of times to shuffle ( 1 - 50 ) : ") ;
    JTextField txtName   = new JTextField(15) ;
    JTextField txtPlayers = new JTextField(10) ;
    JTextField txtShuffle = new JTextField(10) ;
    JButton butStart    = new JButton("Start Game") ;
    JButton butExit     = new JButton("Exit Game") ;
    JLabel labMessage1  = new JLabel("Selects Start or Quit") ;

    JLabel labCardsSelect = new JLabel("") ;
    JLabel labCardsDisplay = new JLabel("") ;


    public myDisplayScreen()
    {
        myInputControls() ;
        myEventListeners();

    }

    public static void displayScreenMain()
    {
        myDisplayScreen myFrameMain = new myDisplayScreen();
        myFrameMain.setVisible(true);
    }


    // Display Input Screen
    private void myInputControls()
    {
        setLayout(new FlowLayout() ) ;
        setTitle("Mineral Super Trumps");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 700);


        add(panelInput) ;
        add(panelCardsSelect) ;
        add(panelCardsDisplay) ;

        // Input
        panelInput.setBackground( Color.GRAY );
        panelInput.add(labName) ;
        panelInput.add(txtName) ;
        panelInput.add(labPlayers) ;
        panelInput.add(txtPlayers) ;
        panelInput.add(labShuffle) ;
        panelInput.add(txtShuffle) ;
        panelInput.add(butStart) ;
        panelInput.add(butExit) ;
        panelInput.add(labMessage1) ;

        // Select Card
        panelCardsSelect.add(labCardsSelect) ;
        panelCardsSelect.setVisible(false);

        // Display Cards
        panelCardsDisplay.add(labCardsDisplay) ;
        panelCardsDisplay.setVisible(false);

    }


    // Action Listeners
    private void myEventListeners()
    {
        // Start Game
        butStart.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                gameValidateAndStart() ;
            }
        }) ;

        // Exit
        butExit.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                System.exit(0);
            }
        }) ;

        // Action Listeners
        // Physical player - Select card
        labCardsSelect.addMouseListener(new MouseInputAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {

                super.mouseClicked(e);
                labMessage1.setText("Physical player selected a card");
            }
        }) ;


    }

    // Validate the inputs and start the game
    public boolean gameValidateAndStart()
    {
        boolean myExitGame = false ;

        while (true)
        {
            // Validate inputs
            if ( !validateInput() )
            {
                break ;
            }

            // Initialise variables
            if ( !(gameInitialise()) )
            {
                break ;
            }

            // Start Playing the cards
            // gameStartPlay() ;
//            if ( !(gameStartPlay()) )
//            {
//                break ;
//            }


            labMessage1.setText("Validated");
            butStart.setEnabled(false);
            panelCardsSelect.setVisible(true);
            panelCardsDisplay.setVisible(true);
            displayCards();

            //gameStart() ;

            myExitGame = true ;
            break ;
        }
        return myExitGame ;

    }




    public void displayCards()
    {
        displayPhysicalPlayersCards(0) ;
        displayComputerPlayersCards(0) ;
    }


    public void displayPhysicalPlayersCards(int pCardNumber)
    {
        labCardsSelect.setIcon( new ImageIcon( "Cards/Slide01.jpg" ) ) ;

    }

    public void displayComputerPlayersCards(int pCardNumber)
    {
        labCardsDisplay.setIcon( new ImageIcon( "Cards/Slide02.jpg" ) ) ;

    }



    // Validate Inputs
    public boolean validateInput()
    {
        boolean isValidated = false ;
        String myMessage = "" , myInputString ;

        while (true)
        {
            // Name
            myInputString = txtName.getText() ;
            if ( !((myMessage = MyCommon.validateString( myInputString ) ).isEmpty()) )
            {
                labMessage1.setText(myMessage);
                break ;
            }
            MyConfig.myName = myInputString ;

            // Players
            myInputString = txtPlayers.getText() ;
            myMessage = MyCommon.validateInteger(myInputString, "Number of players",
                MyConfig.NUMBER_OF_PLAYERS_MINIMUM, MyConfig.NUMBER_OF_PLAYERS_MAXIMUM ) ;
            if (!(myMessage.isEmpty()))
            {
                labMessage1.setText(myMessage);
                break ;
            }
            MyConfig.myNumberOfPlayers = Integer.parseInt( myInputString ) ;

            // Players
            myInputString = txtShuffle.getText() ;
            myMessage = MyCommon.validateInteger(myInputString, "Number of times to shuffle", 1, 50 ) ;
            if (!(myMessage.isEmpty()))
            {
                labMessage1.setText(myMessage);
                break ;
            }
            MyConfig.myNumberOfShuffles = Integer.parseInt( myInputString ) ;

            isValidated = true ;
            break ;
        }
        if ( !(myMessage.isEmpty()) )
        {
            labMessage1.setText(myMessage);
        }
        return isValidated ;

    }


    // Initialise and deal the cards to players
    public boolean gameInitialise()
    {
        boolean isNoErrors = false ;

        // Initialise the dynamic card pack - myCardsPackDeck
        MyConfig.initialiseCardPackDeck();

        while ( true )
        {
            // Shuffle the cards
            // **
/*
            if ( !MyConfig.shuffleCardPackDeck( myRandom ) )
            {
                break ;
            }
*/

            // Get the dealer -- generate random number from 0 to number of players
            // **
            // myDealer = MyCommon.randomInt(myRandom, 0, MyConfig.myNumberOfPlayers - 1 ) ;


            // Create each players card pack
            // (0) - Physical, (1-5) Computer
            for (int i = 0; i <= MyConfig.myNumberOfPlayers; i++ )
            {
                myCardsPackPlayers[i] = new MyPlayer() ;
            }

            // Move the top card from deck (myCardPackDeck) to each player (myCardsPackPlayers)
            for ( int i = 0; i < MyConfig.INITIAL_DEAL; i++ )
            {
                int myPlayer = myDealer ;     // start from the randomise number
                // 0.Physical Player 1>.Computer Players
                for (int j=0; j <= MyConfig.myNumberOfPlayers; j++ )
                {
                    myCardsPackPlayers[myPlayer].myAdd(MyConfig.myCardsPackDeck.get(0)) ;
                    MyConfig.myCardsPackDeck.remove(0) ;
                    myPlayer ++ ;
                    if (myPlayer > MyConfig.myNumberOfPlayers)
                    {
                        myPlayer = 0 ;
                    }
                }
            }


            System.out.println("\n\n");
            isNoErrors = true ;
            break ;
        }
        return isNoErrors ;
    }


    // Listeners
    // Listeners - Action
    @Override
    public void actionPerformed(ActionEvent e) {

    }

    // Listeners - MouseAction
    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
