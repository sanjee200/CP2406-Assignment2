import com.intellij.util.ui.JBUI;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Arrays;
import java.util.Random;

/**
 * Created by Sanjeewa on 22/10/2016.
 */

public class MyDisplayScreen extends JFrame implements ActionListener, MouseListener
{

    // Create all the players card pack
    // 0.Physical 1-5. Computer
    protected MyPlayer[] myCardsPackPlayers = new MyPlayer[ MyConfig.NUMBER_OF_PLAYERS_MAXIMUM + 1 ] ; // Max + 1

    protected Random myRandom = new Random() ;      // Random generator, only once
    protected int myDealer = 0 ;                    // Select the player to deal first

    int myCurrentPlayer = 0 ;               // start from the randomise number
    int myCurrentHand = 1 ;                 // if Current hand is 1 can select the category

    int[] myPlayerWon = new int[ MyConfig.myNumberOfPlayers + 1 ] ;     // 1.Still playing 0.Won
    int[] myPlayerPass = new int[ MyConfig.myNumberOfPlayers + 1 ] ;    // 1.Can play 0.Pass
    int myTotalWonPlayers = 0 ;             // Total number of won players
    int myTotalPassPlayers = 0 ;                // Total number of Active players (not passed)

    int myPlayerCardChoicePhysical = 0 ;    // Physical player selects the card, initially display top card
    int myPlayerCardChoiceComputer = 0 ;    // Computer player selects the card

    // Category
    int myPlayerCategoryChoice = 1 ;        // Default category choice of the computer player
    double myHighestCategoryValue = 0.0 ;   // Highest value in the selected category ( double, array element )

    // Panels
    JPanel panelMain  = new JPanel() ;
    JPanel panelInput = new JPanel(new GridLayout(4,2)) ;
    JPanel panelMessage = new JPanel() ;
    // physical player select card panel
    JPanel panelPhysicalSelectCard = new JPanel() ;

    // display cards panels
    JPanel panelDisplayCards = new JPanel() ;
    // display players cards panels
    JPanel panelPhysicalPlayer  = new JPanel() ;
    JPanel panelComputerPlayer1 = new JPanel() ;
    JPanel panelComputerPlayer2 = new JPanel() ;
    JPanel panelComputerPlayer3 = new JPanel() ;
    JPanel panelComputerPlayer4 = new JPanel() ;
    JPanel panelComputerPlayer5 = new JPanel() ;

    // Inputs
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

    // Select Physical Player card
    JLabel labPhysicalMessage = new JLabel("Select a card") ;
    JLabel labPhysicalSelectCard = new JLabel("") ;
    JLabel labPhysicalCurrentCard = new JLabel("2 out of 5") ;
    JButton butPhysicalPrev = new JButton("<- Previous") ;
    JButton butPhysicalNext = new JButton("Next ->") ;
    JButton butPhysicalPass = new JButton("Pass") ;
    JLabel labPhysicalTrump = new JLabel(
        "1.Hardness, 2.Specific Gravity, 3.Cleavage, 4.Crustal Abundance, 5.Economic Value") ;
    JTextField txtPhysicalTrump = new JTextField(10) ;

    // Display Players Cards
    JLabel labPhysicalPlayerName = new JLabel("") ;
    JLabel labPhysicalPlayerCard = new JLabel("") ;
    JLabel labPhysicalPlayerTrump = new JLabel("Trump") ;
    JLabel labPhysicalPlayerValue = new JLabel("Value") ;
    JLabel labPhysicalPlayerCardsInHand = new JLabel("Number of cards") ;

    JLabel labComputerPlayer1Card = new JLabel("") ;
    JLabel labComputerPlayer1Trump = new JLabel("Trump") ;
    JLabel labComputerPlayer1Value = new JLabel("Value") ;
    JLabel labComputerPlayer1CardsInHand = new JLabel("Number of cards") ;

    JLabel labComputerPlayer2Card = new JLabel("") ;
    JLabel labComputerPlayer2Trump = new JLabel("Trump") ;
    JLabel labComputerPlayer2Value = new JLabel("Value") ;
    JLabel labComputerPlayer2CardsInHand = new JLabel("Number of cards") ;

    JLabel labComputerPlayer3Card = new JLabel("") ;
    JLabel labComputerPlayer3Trump = new JLabel("Trump") ;
    JLabel labComputerPlayer3Value = new JLabel("Value") ;
    JLabel labComputerPlayer3CardsInHand = new JLabel("Number of cards") ;

    JLabel labComputerPlayer4Card = new JLabel("") ;
    JLabel labComputerPlayer4Trump = new JLabel("Trump") ;
    JLabel labComputerPlayer4Value = new JLabel("Value") ;
    JLabel labComputerPlayer4CardsInHand = new JLabel("Number of cards") ;

    JLabel labComputerPlayer5Card = new JLabel("") ;
    JLabel labComputerPlayer5Trump = new JLabel("Trump") ;
    JLabel labComputerPlayer5Value = new JLabel("Value") ;
    JLabel labComputerPlayer5CardsInHand = new JLabel("Number of cards") ;


    // Display screen
    public MyDisplayScreen()
    {
        myInputControls() ;     // Input variables
        myEventListeners();     // Set event listeners

    }

    // Display screen - called by main program
    public static void displayScreenMain()
    {
        MyDisplayScreen myFrameMain = new MyDisplayScreen();
        // myFrameMain.setVisible(true);
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


        // Physical player - Select card
        labPhysicalSelectCard.addMouseListener(new MouseInputAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                super.mouseClicked(e);
                selectCardPhysical() ;
            }
        }) ;

        // Physical player previous card
        butPhysicalPrev.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                displayPhysicalPrevCard() ;
            }

        });

        // Physical player next card
        butPhysicalNext.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                displayPhysicalNextCard();
            }

        });

        // Physical player pass card
        butPhysicalPass.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                displayPhysicalPassCard();
            }

        });
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

            // Assign input values to variables
            setValues();

            // Initialise variables
            if ( !(gameInitialise()) )
            {
                break ;
            }

            // display physical players top card
            displayPhysicalSelectCards(myPlayerCardChoicePhysical, 0) ;
            displayEmptyCards() ;
            if (MyConfig.myNumberOfPlayers < 5)
            {
                panelComputerPlayer5.setVisible(false);
            }
            if (MyConfig.myNumberOfPlayers < 4)
            {
                panelComputerPlayer4.setVisible(false);
            }

            startPlay() ;

            myExitGame = true ;
            break ;
        }
        return myExitGame ;

    }

    // start game
    private void startPlay()
    {

        if (myCurrentPlayer == 0)   // Physical player
        {
            if ( myCurrentHand == 1 )   // Select trump
            {
                labMessage1.setText("Select a trump");
                labPhysicalPlayerTrump.setText("Select Trump");
                labPhysicalPlayerValue.setText("Select Value");
                labPhysicalTrump.setVisible(true);
                txtPhysicalTrump.setVisible(true);
                butPhysicalPass.setEnabled(false);
                repaint() ;
            }

        }
        else        // Computer players
        {
            while (true)
            {

                while (myCurrentPlayer <= MyConfig.myNumberOfPlayers)  // myCurrent(0,1-3)
                {
                    if (myPlayerPass[myCurrentPlayer] == 1)
                    {
                        myCurrentPlayer++;
                        continue;

                    }

                    if (myCurrentHand == 1)     // select trump
                    {
                        // Card Index - randomise between 0 to (length-1) of the hand
                        myPlayerCardChoiceComputer = MyCommon.randomInt(
                            myRandom, 0, myCardsPackPlayers[myCurrentPlayer].myLength() - 1);
                        // category (trump) - randomise between 1-5
                        myPlayerCategoryChoice = MyCommon.randomInt(myRandom, 1, 5);
                    }
                    else    // play for trump
                    {
                        myPlayerCardChoiceComputer =
                            myCardsPackPlayers[myCurrentPlayer].
                                isCategoryHigherAll(myPlayerCategoryChoice, myHighestCategoryValue);
                    }

                    // play (>=0) or pass (<0)
                    if (myPlayerCardChoiceComputer >= 0) {
                        // if a higher category found move that card from the player to deck
                        // Display card with category details

                        // Update the current category value
                        myHighestCategoryValue = myCardsPackPlayers[myCurrentPlayer].
                            getCategoryValue(myPlayerCardChoiceComputer, myPlayerCategoryChoice);

                        displayPlayersCards(myPlayerCardChoiceComputer, -1);
                        moveCardToDeck(myPlayerCardChoiceComputer);
                    }
                    else    // pass players
                    {
                        // if a higher category not found move a card from the deck to the player
                        // pass
                        myPlayerPass[myCurrentPlayer] = 1;
                        myTotalPassPlayers++;
                        displayPlayersCards(61, 1);
                        moveCardToPlayer();
                        if (myTotalPassPlayers >= MyConfig.myNumberOfPlayers)
                        {
                            passInitialise() ;
                            if (myCurrentPlayer == 0)
                            {
                                break ;
                            }
                            else
                            {
                                continue;
                            }
                        }
                    }   // if play or pass

                    myCurrentPlayer++;
                    myCurrentHand++;
                }   // while current <  no
                if (myPlayerPass[0] == 0)   // physical player active
                {
                    myCurrentPlayer = 0 ;
                    break ;
                }
                myCurrentPlayer = 1 ;
            }   // true

        }   // else   // physical, computer players

    }

    void passInitialise()
    {
        myCurrentPlayer = MyCommon.findInteger( myPlayerPass, 0 ) ;
        Arrays.fill( myPlayerPass, 0 ) ;         // 1.Won  0.Playing
        myTotalPassPlayers = 0 ;
        myCurrentHand = 1 ;
        displayEmptyCards() ;
    }

    // physical player selects a card
    private void selectCardPhysical()
    {
        labMessage1.setText("Select a card");
        int myCardIndex = 0  ;
        int myNumberOfCards = myCardsPackPlayers[ myCurrentPlayer ].myLength() ;

        while (true)
        {
            // if changing the trump no need to validate, select a trump
            if (!validatePhysicalPlayerCard())
            {
                break;
            }

            // display the selected card on the deck
            displayPlayersCards( myPlayerCardChoicePhysical, -1 ) ;
            // physically move card
            moveCardToDeck(myPlayerCardChoicePhysical);

            if (myNumberOfCards == 1 ) // last card
            {
                labPhysicalSelectCard.setEnabled(false);
                butPhysicalPrev.setEnabled(false);
                butPhysicalNext.setEnabled(false);
                butPhysicalPass.setEnabled(false);
                labPhysicalMessage.setText(MyConfig.myName + " you have won");
                displayPhysicalSelectCards(64, 0);   // display won Card (64)
                // Physical Player Won
                // initialise won array
            }
            else
            {
                // display the current card, if last then -1
                if ( myPlayerCardChoicePhysical >= myCardsPackPlayers[myCurrentPlayer].myLength() )
                {
                    myPlayerCardChoicePhysical -- ;
                }
                displayPhysicalSelectCards(myPlayerCardChoicePhysical, 0 ) ;
            }

            labPhysicalTrump.setVisible(false);
            txtPhysicalTrump.setVisible(false);
            butPhysicalPass.setEnabled(true);
            myCurrentPlayer ++ ;
            startPlay() ;

            break ;
        }

    }

    // validate selected card by physical player, myCurrentPlayer = 0
    private boolean validatePhysicalPlayerCard()
    {
        boolean myValid = false ;
        String myMessage = "" ;

        while (true)
        {
            if ( myCurrentHand == 1 )   // Select the trump
            {
                labPhysicalTrump.setVisible(true);
                txtPhysicalTrump.setVisible(true);
                labMessage1.setText("Select Category");
                repaint();
                myMessage = MyCommon.validateInteger(
                    txtPhysicalTrump.getText(), "Trump number", 1, 5) ;
                if ( !myMessage.isEmpty() )
                {
                    labMessage1.setText( myMessage );
                    break;
                }

                // get Values
                myPlayerCategoryChoice = Integer.parseInt(txtPhysicalTrump.getText()) ;
                myHighestCategoryValue = myCardsPackPlayers[ myCurrentPlayer ]
                    .getCategoryValue( myPlayerCardChoicePhysical, myPlayerCategoryChoice ) ;

            }
            else        // play for trump
            {
                if ( !myCardsPackPlayers[ myCurrentPlayer ].isCategoryHigherOne(
                    myPlayerCardChoicePhysical, myPlayerCategoryChoice, myHighestCategoryValue ))
                {
                    labMessage1.setText( "Trump value has to be more" );
                    break ;
                }

                myHighestCategoryValue = myCardsPackPlayers[ myCurrentPlayer ]
                    .getCategoryValue( myPlayerCardChoicePhysical, myPlayerCategoryChoice ) ;

            }


            myCurrentHand ++ ;
            myValid = true ;
            break;
        }
        return myValid ;
    }


    // physical player selects previous card
    private void displayPhysicalPrevCard()
    {
        int myCardIndexMax = myCardsPackPlayers[myCurrentPlayer].myLength() - 1 ;
        myPlayerCardChoicePhysical =
            myPlayerCardChoicePhysical == 0 ? myCardIndexMax : myPlayerCardChoicePhysical - 1 ;
        displayPhysicalSelectCards(myPlayerCardChoicePhysical, 0) ;
    }

    // physical player selects next card
    private void displayPhysicalNextCard()
    {
        int myCardIndexMax = myCardsPackPlayers[myCurrentPlayer].myLength() - 1 ;
        myPlayerCardChoicePhysical =
            myPlayerCardChoicePhysical == myCardIndexMax ? 0 : myPlayerCardChoicePhysical + 1 ;
        displayPhysicalSelectCards(myPlayerCardChoicePhysical, 0) ;
    }

    private void displayPhysicalPassCard()
    {
        moveCardToPlayer() ;
        labPhysicalCurrentCard.setText( ( myPlayerCardChoicePhysical + 1 )
            + " of " + ( myCardsPackPlayers[myCurrentPlayer].myLength() ) + " cards" ) ;
        displayPlayersCards(61, 0) ;
        myPlayerPass[ myCurrentPlayer ] = 1 ;
        myTotalPassPlayers++ ;
        myCurrentHand ++ ;
        myCurrentPlayer ++ ;
        if ( myTotalPassPlayers >= MyConfig.myNumberOfPlayers )
        {
            passInitialise();
        }
        startPlay();
    }

    // display physical player select card
    public void displayPhysicalSelectCards(int pCardIndex, int pLength)
    {
        String myCards = "" ;
        int myCardNumber = 0 ;

        // 64 - Won
        if (pCardIndex == 64)
        {
            myCardNumber = pCardIndex ;
            myCards = "No cards" ;
        }
        else
        {
            myCardNumber = myCardsPackPlayers[ myCurrentPlayer ].myGet( pCardIndex ) ;
            myCards = ( pCardIndex + 1 ) + " of "
                + ( myCardsPackPlayers[myCurrentPlayer].myLength() + pLength ) + " cards" ;
        }

        labPhysicalSelectCard.setIcon( new ImageIcon(
            "Cards/Slide" + String.format( "%1$02d", myCardNumber ) + ".jpg" ) ) ;
        labPhysicalCurrentCard.setText( myCards ) ;
    }

    // displays each players card
    public void displayPlayersCards(int pIndex, int pLength)
    {
        int myCardNumber = pIndex ; // 61. Pass, 62. Empty, 63. Won
        String myCardFile ;
        String myCardTrump = "Trump : " ;
        String myCardValue = "Value : " ;
        String myCardCount = "Number of cards in hand : " +
            (myCardsPackPlayers[myCurrentPlayer].myLength() + pLength ) ;

        if ( pIndex <= 60 )
        {
            myCardNumber = myCardsPackPlayers[ myCurrentPlayer ].myGet( pIndex ) ;
            myCardValue += myHighestCategoryValue ;
        }
        myCardFile = "Cards/Slide" + String.format( "%1$02d", myCardNumber ) + ".jpg"  ;
        myCardTrump += MyCategory.myCategory[myPlayerCategoryChoice] ;

        // Display

        switch (myCurrentPlayer)
        {
            case (0) :
                labPhysicalPlayerCard.setIcon( new ImageIcon( myCardFile ) ) ;
                labPhysicalPlayerTrump.setText(myCardTrump) ;
                labPhysicalPlayerValue.setText(myCardValue) ;
                labPhysicalPlayerCardsInHand.setText( myCardCount );
                break ;
            case (1) :
                labComputerPlayer1Card.setIcon( new ImageIcon( myCardFile ) ) ;
                labComputerPlayer1Trump.setText(myCardTrump) ;
                labComputerPlayer1Value.setText(myCardValue) ;
                labComputerPlayer1CardsInHand.setText( myCardCount );
                break ;
            case (2) :
                labComputerPlayer2Card.setIcon( new ImageIcon( myCardFile ) ) ;
                labComputerPlayer2Trump.setText(myCardTrump) ;
                labComputerPlayer2Value.setText(myCardValue) ;
                labComputerPlayer2CardsInHand.setText( myCardCount );
                break ;
            case (3) :
                labComputerPlayer3Card.setIcon( new ImageIcon( myCardFile ) ) ;
                labComputerPlayer3Trump.setText(myCardTrump) ;
                labComputerPlayer3Value.setText(myCardValue) ;
                labComputerPlayer3CardsInHand.setText( myCardCount );
                break ;
            case (4) :
                labComputerPlayer4Card.setIcon( new ImageIcon( myCardFile ) ) ;
                labComputerPlayer4Trump.setText(myCardTrump) ;
                labComputerPlayer4Value.setText(myCardValue) ;
                labComputerPlayer4CardsInHand.setText( myCardCount );
                break ;
            case (5) :
                labComputerPlayer5Card.setIcon( new ImageIcon( myCardFile ) ) ;
                labComputerPlayer5Trump.setText(myCardTrump) ;
                labComputerPlayer5Value.setText(myCardValue) ;
                labComputerPlayer5CardsInHand.setText( myCardCount );
                break ;
            default:
                break ;
        }
    }

    // set values
    public void setValues()
    {
        labMessage1.setText("Select a card");

        MyConfig.myName = txtName.getText() ;
        MyConfig.myNumberOfPlayers = Integer.parseInt( txtPlayers.getText() ) ;
        MyConfig.myNumberOfShuffles = Integer.parseInt( txtShuffle.getText() ) ;

        labPhysicalPlayerName.setText(MyConfig.myName);
        //butStart.setEnabled(false);
        // **
        panelInput.removeAll();         // remove panelInput from main screen
        panelInput.setVisible(false);
        panelPhysicalSelectCard.setVisible(true);
        panelDisplayCards.setVisible(true);
    }

    public void displayEmptyCards()
    {
        labPhysicalPlayerCard.setIcon(  new ImageIcon( "Cards/Slide63.jpg" ) ) ;
        labComputerPlayer1Card.setIcon( new ImageIcon( "Cards/Slide63.jpg" ) ) ;
        labComputerPlayer2Card.setIcon( new ImageIcon( "Cards/Slide63.jpg" ) ) ;
        labComputerPlayer3Card.setIcon( new ImageIcon( "Cards/Slide63.jpg" ) ) ;
        labComputerPlayer4Card.setIcon( new ImageIcon( "Cards/Slide63.jpg" ) ) ;
        labComputerPlayer5Card.setIcon( new ImageIcon( "Cards/Slide63.jpg" ) ) ;

        String myCardCount = "Number of cards in hand : " ;
        labPhysicalPlayerCardsInHand.setText(myCardCount + myCardsPackPlayers[0].myLength() );
        labComputerPlayer1CardsInHand.setText(myCardCount + myCardsPackPlayers[1].myLength() );
        labComputerPlayer2CardsInHand.setText(myCardCount + myCardsPackPlayers[2].myLength() );
        labComputerPlayer3CardsInHand.setText(myCardCount + myCardsPackPlayers[3].myLength() );
        labComputerPlayer4CardsInHand.setText(myCardCount + myCardsPackPlayers[4].myLength() );
        labComputerPlayer5CardsInHand.setText(myCardCount + myCardsPackPlayers[5].myLength() );
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

            if ( !MyConfig.shuffleCardPackDeck( myRandom ) )
            {
                break ;
            }

            // Get the dealer -- generate random number from 0 to number of players
            // **
            myDealer = MyCommon.randomInt(myRandom, 0, MyConfig.myNumberOfPlayers - 1 ) ;
            myCurrentPlayer = myDealer ;


            // Create each players card pack
            // (0) - Physical, (1-5) Computer
            for (int i = 0; i <= MyConfig.NUMBER_OF_PLAYERS_MAXIMUM; i++ )
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

    // Move the card from player (myCardsPackPlayers) to the deck (myCardPackDeck)
    public void moveCardToDeck( int pCardIndex )
    {
        MyConfig.myCardsPackDeck.add( myCardsPackPlayers[ myCurrentPlayer ].myGet( pCardIndex ) ) ;   // Appends the players card number to the deck
        myCardsPackPlayers[ myCurrentPlayer ].myRemove( pCardIndex ) ;    // Removes card number (element) from the players hand
    }


    // Move the top card from deck (myCardPackDeck) to player (myCardsPackPlayers)
    public void moveCardToPlayer( )
    {
        int myCardNumber = MyConfig.myCardsPackDeck.get( 0 ) ;       // Card Number in top of the deck
        myCardsPackPlayers[ myCurrentPlayer ].myAdd( myCardNumber ) ; // Appends the card number to player (pCardsPackPlayers)
        MyConfig.myCardsPackDeck.remove( 0 ) ;  // Removes top card from the deck (myCardsPackDeck)
    }

    // Display Input Screen
    private void myInputControls()
    {
        setLayout(new FlowLayout()) ; //new BoxLayout(this, BoxLayout.PAGE_AXIS) ) ; // FlowLayout() ) ;
        setTitle("Mineral Super Trumps");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1300, 700);

        // Main panels
        //add(panelMain) ;
        //panelMain.setLayout(new FlowLayout());
        //panelMain.setLayout( new BoxLayout(panelMain, BoxLayout.PAGE_AXIS)) ;

        add(panelInput) ;
        add(panelMessage) ;
        add(panelPhysicalSelectCard) ;
        add(panelDisplayCards) ;
        panelPhysicalSelectCard.setVisible(false);
        panelDisplayCards.setVisible(false);

        // Set Layouts
        panelPhysicalSelectCard.setLayout( new BoxLayout(panelPhysicalSelectCard, BoxLayout.PAGE_AXIS)) ;
        panelPhysicalPlayer.setLayout( new BoxLayout(panelPhysicalPlayer, BoxLayout.PAGE_AXIS)) ;
        panelComputerPlayer1.setLayout( new BoxLayout(panelComputerPlayer1, BoxLayout.PAGE_AXIS)) ;
        panelComputerPlayer2.setLayout( new BoxLayout(panelComputerPlayer2, BoxLayout.PAGE_AXIS)) ;
        panelComputerPlayer3.setLayout( new BoxLayout(panelComputerPlayer3, BoxLayout.PAGE_AXIS)) ;
        panelComputerPlayer4.setLayout( new BoxLayout(panelComputerPlayer4, BoxLayout.PAGE_AXIS)) ;
        panelComputerPlayer5.setLayout( new BoxLayout(panelComputerPlayer5, BoxLayout.PAGE_AXIS)) ;


        // Display players cards
        panelDisplayCards.add(panelPhysicalPlayer) ;
        panelDisplayCards.add(panelComputerPlayer1) ;
        panelDisplayCards.add(panelComputerPlayer2) ;
        panelDisplayCards.add(panelComputerPlayer3) ;
        panelDisplayCards.add(panelComputerPlayer4) ;
        panelDisplayCards.add(panelComputerPlayer5) ;

        // Input
        panelInput.setBackground( Color.LIGHT_GRAY );
        panelInput.add(labName) ;
        panelInput.add(txtName) ;
        panelInput.add(labPlayers) ;
        panelInput.add(txtPlayers) ;
        panelInput.add(labShuffle) ;
        panelInput.add(txtShuffle) ;
        panelInput.add(butStart) ;
        panelInput.add(butExit) ;

        // Message
        panelMessage.add(labMessage1) ;

        // Select Card
        panelPhysicalSelectCard.add(labPhysicalMessage) ;
        panelPhysicalSelectCard.setBorder(JBUI.Borders.customLine(Color.BLUE));
        panelPhysicalSelectCard.add(labPhysicalSelectCard) ;
        panelPhysicalSelectCard.add(butPhysicalPrev) ;
        panelPhysicalSelectCard.add(butPhysicalNext) ;
        panelPhysicalSelectCard.add(butPhysicalPass) ;
        panelPhysicalSelectCard.add(labPhysicalCurrentCard) ;
        panelPhysicalSelectCard.add(labPhysicalTrump) ;
        panelPhysicalSelectCard.add(txtPhysicalTrump) ;
        labPhysicalTrump.setVisible(false);
        //labPhysicalTrump.setEnabled(false);
        txtPhysicalTrump.setVisible(false);
        //txtPhysicalTrump.setEnabled(false);
        txtPhysicalTrump.setSize(20,1);

        // Display Cards
        panelPhysicalPlayer.add(labPhysicalPlayerName) ;
        panelPhysicalPlayer.setBorder(JBUI.Borders.customLine(Color.BLUE));
        panelPhysicalPlayer.add(labPhysicalPlayerCard) ;
        panelPhysicalPlayer.add(labPhysicalPlayerTrump) ;
        panelPhysicalPlayer.add(labPhysicalPlayerValue) ;
        panelPhysicalPlayer.add(labPhysicalPlayerCardsInHand) ;

        panelComputerPlayer1.add(new JLabel( "Player 1")) ;
        panelComputerPlayer1.setBorder(JBUI.Borders.customLine(Color.BLUE));
        panelComputerPlayer1.add(labComputerPlayer1Card) ;
        panelComputerPlayer1.add(labComputerPlayer1Trump) ;
        panelComputerPlayer1.add(labComputerPlayer1Value) ;
        panelComputerPlayer1.add(labComputerPlayer1CardsInHand) ;

        panelComputerPlayer2.add(new JLabel( "Player 2")) ;
        panelComputerPlayer2.setBorder(JBUI.Borders.customLine(Color.BLUE));
        panelComputerPlayer2.add(labComputerPlayer2Card) ;
        panelComputerPlayer2.add(labComputerPlayer2Trump) ;
        panelComputerPlayer2.add(labComputerPlayer2Value) ;
        panelComputerPlayer2.add(labComputerPlayer2CardsInHand) ;

        panelComputerPlayer3.add(new JLabel( "Player 3")) ;
        panelComputerPlayer3.setBorder(JBUI.Borders.customLine(Color.BLUE));
        panelComputerPlayer3.add(labComputerPlayer3Card) ;
        panelComputerPlayer3.add(labComputerPlayer3Trump) ;
        panelComputerPlayer3.add(labComputerPlayer3Value) ;
        panelComputerPlayer3.add(labComputerPlayer3CardsInHand) ;

        panelComputerPlayer4.add(new JLabel( "Player 4")) ;
        panelComputerPlayer4.setBorder(JBUI.Borders.customLine(Color.BLUE));
        panelComputerPlayer4.add(labComputerPlayer4Card) ;
        panelComputerPlayer4.add(labComputerPlayer4Trump) ;
        panelComputerPlayer4.add(labComputerPlayer4Value) ;
        panelComputerPlayer4.add(labComputerPlayer4CardsInHand) ;

        panelComputerPlayer5.add(new JLabel( "Player 5")) ;
        panelComputerPlayer5.setBorder(JBUI.Borders.customLine(Color.BLUE));
        panelComputerPlayer5.add(labComputerPlayer5Card) ;
        panelComputerPlayer5.add(labComputerPlayer5Trump) ;
        panelComputerPlayer5.add(labComputerPlayer5Value) ;
        panelComputerPlayer5.add(labComputerPlayer5CardsInHand) ;

        labPhysicalSelectCard.setBorder(JBUI.Borders.customLine(Color.BLACK));

        setVisible(true);

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
