/**
 * Created by Sanjeewa on 20/10/2016.
 * Displays The Input Screen
 */

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;


public class MyDisplay extends JPanel implements ActionListener, MouseListener
{

    int x = 40 , y= 40;
    int size = 30;


    // MyGame

    // MyGame

    protected boolean myDisplayDetails1 = false ;   // Display additional information
    protected int myDealer = 0 ;                    // Select the player to deal first
    protected Random myRandom = new Random() ;      // Random generator, only once

    // Create all the players card pack
    // 0.Physical 1-5. Computer
    protected MyPlayer[] myCardsPackPlayers = new MyPlayer[ MyConfig.NUMBER_OF_PLAYERS_MAXIMUM + 1 ] ; // Max + 1


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

    // *** remove static
    public static JLabel labCardsSelect = new JLabel("Select a cardaaaaa") ;
    public static JLabel labCardsDisplay = new JLabel("Display Cards") ;

    // Images
    // BufferedImage imgCardsSelect = null;

    public MyDisplay()
    {
        myDisplayControls();
    }

    public static void displayMainScreen()
    {
        JFrame myFrameMain = new JFrame();
        myFrameMain.add(new MyDisplay());
        myFrameMain.setTitle("Mineral Super Trumps");
        myFrameMain.setSize(900, 700);
        myFrameMain.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myFrameMain.setVisible(true);
    }


    // Methods

    // Display Controls
    public void myDisplayControls()
    {
        setBackground(Color.LIGHT_GRAY);

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

        // Display Cards
        panelCardsDisplay.add(labCardsDisplay) ;


        // Action Listeners
        butStart.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                gameValidateAndStart() ;
                // end game
            }
        }) ;

        butExit.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                System.exit(0);
            }
        }) ;

        addMouseListener(this);
    }

    public void gameValidateAndStart()
    {
        while (true)
        {
            if ( !validateInput() )
            {
                break ;
            }
            labMessage1.setText("Validated");
            butStart.setEnabled(false);

            gameStart() ;

            break ;
        }
    }


    // MyGame //
    // **
    public boolean gameStart()
    {
        // MyGame

        boolean myExitGame = false ;

        while ( true)
        {
            // Initialise variables
            if ( !(myExitGame = gameInitialise()) )
            {
                break ;
            }

            // Start Playing the cards
            if ( !(myExitGame = gameStartPlay()) )
            {
                break ;
            }

            break ;
        }

        return myExitGame ;
    }



    // Initialise and deal the cards to players
    public boolean gameStartPlay()
    {

        // Start the game
        int myExitGameStatus = 0 ;              // If greater than 0 Exit game

        // Player
        int myCurrentPlayer = 0 ;               // start from the randomise number
        int myPlayerCardChoicePhysical = 1 ;    // Physical player selects the card
        int myPlayerCardChoiceComputer = 1 ;    // Computer player selects the card
        int myCardIndex = 0 ;                   // User selected card from myCardsPackPlayers[myCurrentPlayer]
        int myCardNumber ;                      // Actual Card Number

        int[] myPlayerWon = new int[ MyConfig.myNumberOfPlayers + 1 ] ;     // 1.Still playing 0.Won
        int[] myPlayerPass = new int[ MyConfig.myNumberOfPlayers + 1 ] ;    // 1.Can play 0.Pass
        Arrays.fill( myPlayerWon, 0 ) ;         // 1.Won  0.Playing
        int myTotalWonPlayers = 0 ;             // Total number of won players
        int myTotalPassPlayers ;                // Total number of Active players (not passed)
        int myCurrentHand = 1 ;                 // Current hand

        // Category
        int myPlayerCategoryChoice = 1 ;        // Default category choice of the computer player
        double myHighestCategoryValue = 0.0 ;   // Highest value in the selected category ( double, array element )


        // Display Physical Players Top Card
        displayPhysicalPlayersCards( myCardsPackPlayers[myCurrentPlayer].myGet(0) );


        return ( true ) ;
    }


    public void displayPhysicalPlayersCards(int pCardNumber)
    {
        labCardsSelect.setIcon( new ImageIcon( "Cards/Slide01.jpg" ) ) ;

    }


    // Move the card from player (myCardsPackPlayers) to the deck (myCardPackDeck)
    public void moveCardToDeck( MyPlayer[] pCardsPackPlayers, int pCurrentPlayer, int pCardIndex )
    {
        MyConfig.myCardsPackDeck.add( pCardsPackPlayers[ pCurrentPlayer ].myGet( pCardIndex ) ) ;   // Appends the players card number to the deck
        pCardsPackPlayers[ pCurrentPlayer ].myRemove( pCardIndex ) ;    // Removes card number (element) from the players hand

    }


    // Move the top card from deck (myCardPackDeck) to player (myCardsPackPlayers)
    public void moveCardToPlayer( MyPlayer[] pCardsPackPlayers, int pCurrentPlayer )
    {
        int myCardNumber = MyConfig.myCardsPackDeck.get( 0 ) ;       // Card Number in top of the deck
        pCardsPackPlayers[ pCurrentPlayer ].myAdd( myCardNumber ) ; // Appends the card number to player (pCardsPackPlayers)
        MyConfig.myCardsPackDeck.remove( 0 ) ;  // Removes top card from the deck (myCardsPackDeck)
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

            // Display card numbers of all the players
            if ( myDisplayDetails1 )
            {
                for ( int myPlayerNumber=0; myPlayerNumber<= MyConfig.myNumberOfPlayers ; myPlayerNumber++ )
                {
                    System.out.print( myPlayerNumber == 0
                        ? "Physical Player   => "
                        : "Computer Player " + myPlayerNumber + " => " ) ;
                    myCardsPackPlayers[myPlayerNumber].displayAllCardNumbers() ;
                }
                System.out.println("") ;
            }

            System.out.println("\n\n");
            isNoErrors = true ;
            break ;
        }
        return isNoErrors ;
    }

    // Listeners

    // MouseListener
    @Override
    public void mouseClicked(MouseEvent e)
    {
        if(e.getClickCount() == 2)
        {
            size = 30;
        }
        else
        {
            size = 20;
        }
        repaint() ;
    }

    @Override
    public void mousePressed(MouseEvent e)
    {
        x = e.getX();
        y = e.getY();
    }

    @Override
    public void mouseReleased(MouseEvent e)
    {

    }

    @Override
    public void mouseEntered(MouseEvent e)
    {
        //setBackground(Color.GREEN);
    }

    @Override
    public void mouseExited(MouseEvent e)
    {
        //setBackground(Color.ORANGE);
    }

    // ActionListener
    @Override
    public void actionPerformed(ActionEvent e)
    {

    }

    // Graphics
    @Override
    public void paintComponent(Graphics pGraphics)
    {
        super.paintComponent(pGraphics);
        pGraphics.drawOval(x - size, x - size, size * 2, size * 2);
        // pGraphics.drawImage(imgCardsSelect, 0, 0, 100, 200, this);
    }

}
