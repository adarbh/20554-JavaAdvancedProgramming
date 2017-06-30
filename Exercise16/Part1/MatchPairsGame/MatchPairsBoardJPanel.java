package MatchPairsGame;

import MatchPairsGameProtocol.MatchPairsGameProtocolMessage;
import MatchPairsGameProtocol.MatchPairsGameProtocolSelectCardsMessageData;
import javafx.util.Pair;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.*;
import java.awt.FlowLayout;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Adar on 6/26/2017.
 */
public class MatchPairsBoardJPanel extends JPanel  implements ActionListener {
    private MatchPairsBoard board;
    private int frameDimensions;
    private boolean isPlayerTurn;
    private ArrayList<Pair> colors;
    private ArrayList<Pair> selectedCards;
    private JButton[][] buttonGrid;
    private MatchPairsConnection playerSession;

    public MatchPairsBoardJPanel(MatchPairsBoard board, int frameDimensions,
                                 boolean isPlayerFirst, MatchPairsConnection playerSession) {
        this.board = board;
        this.frameDimensions = frameDimensions;
        this.isPlayerTurn = isPlayerFirst;
        this.playerSession = playerSession;
        this.selectedCards = new ArrayList<Pair>();

        this.colors = new ArrayList<Pair>();
        this.colors.add(new Pair(0, Color.orange));
        this.colors.add(new Pair(1, Color.blue));
        this.colors.add(new Pair(2, Color.white));
        this.colors.add(new Pair(3, Color.yellow));
        this.colors.add(new Pair(4, Color.cyan));
        this.colors.add(new Pair(5, Color.pink));
        this.colors.add(new Pair(6, Color.red));
        this.colors.add(new Pair(7, Color.green));

        createComponents();
    }

    public void setBoard(MatchPairsBoard board) {
        this.board = board;

        this.removeAll();
        createComponents();
    }

    public void setIsPlayerTurn(boolean isPlayerTurn) {
        this.isPlayerTurn = isPlayerTurn;
    }

    public void createComponents() {
        System.out.println("Painting board");

        setLayout(new GridLayout(this.board.getDimensions(), 1));
        //setLayout(new GridLayout(this.board.getDimensions(), this.board.getDimensions()));
        buttonGrid = new JButton[this.board.getDimensions()][this.board.getDimensions()];
        JButton button = null;

        for (int i = 0 ; i < this.board.getDimensions() ; i++) {
            JPanel linePanel = new JPanel();
            linePanel.setLayout(new FlowLayout());

            for (int j = 0 ; j < this.board.getDimensions() ; j++) {

                button = new JButton("" + this.board.getCard(i, j).getPictureId() + "");

                if (!this.board.getCard(i, j).isRevealed()) {
                    button.setBackground(Color.LIGHT_GRAY);
                } else {
                    for (int k = 0 ; k < this.colors.size() ; k++) {
                        if (this.board.getCard(i, j).getPictureId() == (int)this.colors.get(k).getKey()) {
                            button.setBackground((Color) this.colors.get(k).getValue());
                        }
                    }
                }

                linePanel.add(button);
                buttonGrid[i][j] = button;
                //add(button);
                button.addActionListener(this);
            }

            add(linePanel);
        }

        revalidate();
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        /* Check if it is the player turn */
        if (!isPlayerTurn) {
            JOptionPane.showMessageDialog(null, "This is not you turn to play");
            return;
        }

        /* Find the location of the button in the grid */
        Pair cardIndex = null;

        for (int i = 0 ; i < this.board.getDimensions() ; i++) {
            for (int j = 0 ; j < this.board.getDimensions() ; j++) {
                if (e.getSource() == buttonGrid[i][j]) {
                    cardIndex = new Pair(i, j);
                }
            }
        }
        this.selectedCards.add(cardIndex);

        /* Check if this is the second card selected */
        if (this.selectedCards.size() < 2) {
            return;
        }

        /* Send a request to select cards */
        MatchPairsGameProtocolMessage message = new MatchPairsGameProtocolMessage(
                MatchPairsGameProtocolMessage.MatchPairsGameProtocolMessageType.SELECT_CARDS_MESSAGE,
                new MatchPairsGameProtocolSelectCardsMessageData(this.selectedCards));
        try {
            System.out.println("Sending SELECT_CARDS_MESSAGE to server.");
            playerSession.getOut().writeObject(message);
        } catch (IOException ex) {
            ex.printStackTrace();
            //handleCommunicateErrorWithServer(playerSession);
            //TODO
        }

        this.selectedCards = new ArrayList<Pair>();
    }
}
