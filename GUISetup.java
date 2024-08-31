package com.company;
import java.awt.*;
import java.net.URL;
import javax.swing.*;
import com.company.Connect4Game;

@SuppressWarnings("serial") //suppresses all compiler warnings

public class GUISetup extends JFrame {
    private Container container1 = null;
    private final Connect4Game game;

    //pull from board???
    int rows;
    int columns;

    //set dimensions
    int windowWidth = 720;
    int windowHeight = 640;


    private ImageIcon iconBlank = null;
    private ImageIcon iconRed = null;
    private ImageIcon iconYellow = null;

    private final String title = "Connect 4 Game - ";

    private void GUI1Class(JButton button) {
        int row10plusCol = Integer.parseInt(button.getName());
        int col = row10plusCol % 10;

        boolean player1turn = game.isOneReallyPlaying();
        if(player1turn) setTitle(title + "Yellow");
        else setTitle(title + "Red");

        int addedRow = game.round(col);

        if(addedRow != -1) {
            JButton updateButton = ((JButton)(container1.getComponent(columns * addedRow + col)));
            //get component parameter targets the specific location in which a tile is located
            //establishes button icon to indicate turn -- hopefully
            if(game.isOneReallyPlaying()) updateButton.setIcon(iconYellow);
            else updateButton.setIcon(iconRed);

            //check for winner - needs 'showMessageDialog' and confirmdialog
            if(game.checkWinnerGUI(col)) {
                JOptionPane.showMessageDialog(null, "You win!");
                int followThrough = JOptionPane.showConfirmDialog(null, "Would you like to restart?", null, JOptionPane.YES_NO_OPTION);
                if(followThrough == JOptionPane.YES_OPTION) {
                    System.out.println("Alright.");
                    game.resetGame(6, 7);
                    resetBoard();
                } else {
                    //allows user to close - hopefully
                    System.exit(0);
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Please select a valid tile.");
        }
    }

    public void resetBoard() {
        for(int row = 0; row < rows; row++) {
            for(int col = 0; col < columns; col++) {
                //reset all icons to empty case, refer to container
                ((JButton)(container1.getComponent(columns * row + col))).setIcon(iconBlank);
            }
        }
    }

    //actual GUI :/
    public GUISetup(boolean player1turn, Connect4Game game, int rows, int columns) {
        this.game = game;
        this.rows = rows;
        this.columns = columns;

        //icons
        //empty
        try {
            String imageEmptyFile = "images/white.jpeg";
            URL imageURL = getClass().getClassLoader().getResource(imageEmptyFile);
            if (imageURL != null) {
                iconBlank = new ImageIcon(imageURL);
                System.out.println(iconBlank.toString());
            }
            else
                System.err.println("Could not source " + imageEmptyFile);

            //red
            String imageRedFile = "images/red.jpeg";
            imageURL =  getClass().getClassLoader().getResource(imageRedFile);//new URL(imageRedFile);
            if (imageURL != null) iconRed = new ImageIcon(imageURL);
            else System.err.println("Could not source " + imageRedFile);

            //yellow
            String imageYellowFile = "images/yellow.jpeg";//"https://commons.wikimedia.org/wiki/File:Solid_yellow.svg";
            imageURL = getClass().getClassLoader().getResource(imageYellowFile);
            if (imageURL != null) iconYellow = new ImageIcon(imageURL);
            else System.err.println("Could not source " + imageYellowFile);

            //connect container to the content
            container1 = getContentPane();
            //from java awt --> centering the buttons
            container1.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));

            // buttons in the board
            for (int row = 0; row < rows; row++) {
                for (int col = 0; col < columns; col++) {
                    JButton button = new JButton(); //allows the user to click the tile, hopefully
                    button.setIcon(iconBlank); //set to default color
                    button.setPreferredSize(new Dimension(100,100));
                    button.setName(Integer.toString(row * 10 + col));
                    //interface tool, leads to command being run once the button is clicked
                    button.addActionListener(actionEvent -> GUI1Class((JButton) (actionEvent.getSource()))); //initial location
                    //button.setVisible(true);
                    container1.add(button);
                }
            }


            // closing the application
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            if (!player1turn) setTitle(title + "Yellow");
            else setTitle(title + "Red");
            //center the window --> must be null
            setLocationRelativeTo(null);
            setSize(windowWidth, windowHeight);
            //displays the window
            setVisible(true);
        }
        catch (Exception ex)
        {
            System.out.println(ex.toString());
        }
    }





}
