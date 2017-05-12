package Part2;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 * Created by Adar on 5/12/2017.
 */

public class VirtualKeyboardFrame extends JFrame implements ActionListener {
    /**
     * This class represents a virtual keyboard frame
     */

    /* First line buttons */
    private JButton tildaButton = new JButton("` / ~");
    private JButton oneButton = new JButton("1 / !");
    private JButton twoButton = new JButton("2 / @");
    private JButton treeButton = new JButton("3 / #");
    private JButton fourButton = new JButton("4 / $");
    private JButton fiveButton = new JButton("5 / %");
    private JButton sixButton = new JButton("6 / ^");
    private JButton sevenButton = new JButton("7 / &");
    private JButton eightButton = new JButton("8 / *");
    private JButton nineButton = new JButton("9 / (");
    private JButton zeroButton = new JButton("0 / )");
    private JButton underscoreButton = new JButton("- / _");
    private JButton plusButton = new JButton("= / +");
    private JButton backspaceButton = new JButton("Backspace");

    /* Second line buttons */
    private JButton tabButton = new JButton("Tab");
    private JButton qButton = new JButton("Q");
    private JButton wButton = new JButton("W");
    private JButton eButton = new JButton("E");
    private JButton rButton = new JButton("R");
    private JButton tButton = new JButton("T");
    private JButton yButton = new JButton("Y");
    private JButton uButton = new JButton("U");
    private JButton iButton = new JButton("I");
    private JButton oButton = new JButton("O");
    private JButton pButton = new JButton("P");
    private JButton squereLeftButton = new JButton("[ / {");
    private JButton squereRightButton = new JButton("] / }");
    private JButton pipeButton = new JButton("\\  / |");

    /* Tired line buttons */
    private JButton capsButton = new JButton("Caps");
    private JButton aButton = new JButton("A");
    private JButton sButton = new JButton("S");
    private JButton dButton = new JButton("D");
    private JButton fButton = new JButton("F");
    private JButton gButton = new JButton("G");
    private JButton hButton = new JButton("H");
    private JButton jButton = new JButton("J");
    private JButton kButton = new JButton("K");
    private JButton lButton = new JButton("L");
    private JButton semicolonButton = new JButton("; / :");
    private JButton commaButton = new JButton("' / \"");
    private JButton enterButton = new JButton("Enter");
    
    /* Forth line buttons */
    private JButton shift1Button = new JButton("Shift");
    private JButton zButton = new JButton("Z");
    private JButton xButton = new JButton("X");
    private JButton cButton = new JButton("C");
    private JButton vButton = new JButton("V");
    private JButton bButton = new JButton("B");
    private JButton nButton = new JButton("N");
    private JButton mButton = new JButton("M");
    private JButton traingleLeftButton = new JButton(", / <");
    private JButton traingleRightButton = new JButton(". / >");
    private JButton questionButton = new JButton("/ / ?");
    private JButton shift2Button = new JButton("Shift");

    /* Fifth line buttons */
    private JButton spaceButton = new JButton(new String(new char[120]).replace("\0", " "));

    /* Button groups */
    private ArrayList <JButton> letterButtons;
    private ArrayList <JButton> signButtons;

    /* Panels */
    private JTextArea textArea;
    private JPanel textPanel;
    private JPanel rawPanel;
    private JPanel firstLinePanel;
    private JPanel secondLinePanel;
    private JPanel thirdLinePanel;
    private JPanel forthLinePanel;
    private JPanel fifthLinePanel;

    /* States */
    private boolean capsState = false;
    private boolean shiftLeftState = false;
    private boolean shiftRightState = false;
    private Color buttonColor;

    /* Text section */
    private int textAreaWidth;
    private int textAreaHeight;
    private int lineHeight;

    /**
     * Returns an VirtualKeyboardFrame object with an initialized layout
     * @return      the VirtualKeyboardFrame object
     */
    public VirtualKeyboardFrame() {

        /* Initialize panels */
        this.textPanel = new JPanel();
        this.rawPanel = new JPanel();
        this.firstLinePanel = new JPanel();
        this.secondLinePanel = new JPanel();
        this.thirdLinePanel = new JPanel();
        this.forthLinePanel = new JPanel();
        this.fifthLinePanel = new JPanel();

        /* Add listeners */
        addListenerToJButtons();

        /* Define layout */
        this.setLayout(new GridLayout(2, 1));
        this.textAreaWidth = 980;
        this.lineHeight = 20;
        this.textAreaHeight = this.lineHeight * 10;
        configureTextSection();
        configureFirstRow();
        configureSecondRow();
        configureThirdRow();
        configureForthRow();
        configureFifthRow();
        configureKeyboardPanel();
        add(this.textPanel);
        add(this.rawPanel);

        /* Define button groups */
        this.buttonColor = this.shift1Button.getBackground();
        defineSignButtons();
        defineLetterButtons();

        /* Configure frame */
        setTitle("Virtual Keyboard");
        setFont(new Font("Calibri", Font.PLAIN, 15));
        setSize(1000,500);
        setResizable(false);
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    /**
     * Configures the text section of the display
     */
    private void configureTextSection() {
        this.textArea = new JTextArea("");
        this.textArea.setLineWrap(true);
        this.textArea.setWrapStyleWord(true);
        this.textArea.setPreferredSize(new Dimension(this.textAreaWidth, this.textAreaHeight));
        this.textPanel.add(this.textArea,BorderLayout.NORTH);
    }

    /**
     * Configures the first button row in the display
     */
    private void configureFirstRow() {
        this.firstLinePanel.setLayout(new FlowLayout());
        this.firstLinePanel.add(tildaButton);
        this.firstLinePanel.add(oneButton);
        this.firstLinePanel.add(twoButton);
        this.firstLinePanel.add(treeButton);
        this.firstLinePanel.add(fourButton);
        this.firstLinePanel.add(fiveButton);
        this.firstLinePanel.add(sixButton);
        this.firstLinePanel.add(sevenButton);
        this.firstLinePanel.add(eightButton);
        this.firstLinePanel.add(nineButton);
        this.firstLinePanel.add(zeroButton);
        this.firstLinePanel.add(underscoreButton);
        this.firstLinePanel.add(plusButton);
        this.firstLinePanel.add(backspaceButton);
    }

    /**
     * Configures the second button row in the display
     */
    private void configureSecondRow() {
        this.secondLinePanel.setLayout(new FlowLayout());
        this.secondLinePanel.add(tabButton);
        this.secondLinePanel.add(qButton);
        this.secondLinePanel.add(wButton);
        this.secondLinePanel.add(eButton);
        this.secondLinePanel.add(rButton);
        this.secondLinePanel.add(tButton);
        this.secondLinePanel.add(yButton);
        this.secondLinePanel.add(uButton);
        this.secondLinePanel.add(iButton);
        this.secondLinePanel.add(oButton);
        this.secondLinePanel.add(pButton);
        this.secondLinePanel.add(squereLeftButton);
        this.secondLinePanel.add(squereRightButton);
        this.secondLinePanel.add(pipeButton);
    }

    /**
     * Configures the third button row in the display
     */
    private void configureThirdRow() {
        this.thirdLinePanel.setLayout(new FlowLayout());
        this.thirdLinePanel.add(capsButton);
        this.thirdLinePanel.add(aButton);
        this.thirdLinePanel.add(sButton);
        this.thirdLinePanel.add(dButton);
        this.thirdLinePanel.add(fButton);
        this.thirdLinePanel.add(gButton);
        this.thirdLinePanel.add(hButton);
        this.thirdLinePanel.add(jButton);
        this.thirdLinePanel.add(kButton);
        this.thirdLinePanel.add(lButton);
        this.thirdLinePanel.add(semicolonButton);
        this.thirdLinePanel.add(commaButton);
        this.thirdLinePanel.add(enterButton);
    }

    /**
     * Configures the forth button row in the display
     */
    private void configureForthRow() {
        this.forthLinePanel.setLayout(new FlowLayout());
        this.forthLinePanel.add(shift1Button);
        this.forthLinePanel.add(zButton);
        this.forthLinePanel.add(xButton);
        this.forthLinePanel.add(cButton);
        this.forthLinePanel.add(vButton);
        this.forthLinePanel.add(bButton);
        this.forthLinePanel.add(nButton);
        this.forthLinePanel.add(mButton);
        this.forthLinePanel.add(traingleLeftButton);
        this.forthLinePanel.add(traingleRightButton);
        this.forthLinePanel.add(questionButton);
        this.forthLinePanel.add(shift2Button);
    }

    /**
     * Configures the fifth button row in the display
     */
    private void configureFifthRow() {
        this.fifthLinePanel.setLayout(new FlowLayout());
        this.fifthLinePanel.add(spaceButton);
    }

    /**
     * Configures the keyboard panel in the display
     */
    private void configureKeyboardPanel() {
        this.rawPanel.add(this.firstLinePanel);
        this.rawPanel.add(this.secondLinePanel);
        this.rawPanel.add(this.thirdLinePanel);
        this.rawPanel.add(this.forthLinePanel);
        this.rawPanel.add(this.fifthLinePanel);
    }

    /**
     * Add listeners to all buttons in the display
     */
    private void addListenerToJButtons() {
        this.tildaButton.addActionListener(this);
        this.oneButton.addActionListener(this);
        this.twoButton.addActionListener(this);
        this.treeButton.addActionListener(this);
        this.fourButton.addActionListener(this);
        this.fiveButton.addActionListener(this);
        this.sixButton.addActionListener(this);
        this.sevenButton.addActionListener(this);
        this.eightButton.addActionListener(this);
        this.nineButton.addActionListener(this);
        this.zeroButton.addActionListener(this);
        this.underscoreButton.addActionListener(this);
        this.plusButton.addActionListener(this);
        this.backspaceButton.addActionListener(this);
        this.tabButton.addActionListener(this);
        this.qButton.addActionListener(this);
        this.wButton.addActionListener(this);
        this.eButton.addActionListener(this);
        this.rButton.addActionListener(this);
        this.tButton.addActionListener(this);
        this.yButton.addActionListener(this);
        this.uButton.addActionListener(this);
        this.iButton.addActionListener(this);
        this.oButton.addActionListener(this);
        this.pButton.addActionListener(this);
        this.squereLeftButton.addActionListener(this);
        this.squereRightButton.addActionListener(this);
        this.pipeButton.addActionListener(this);
        this.capsButton.addActionListener(this);
        this.aButton.addActionListener(this);
        this.sButton.addActionListener(this);
        this.dButton.addActionListener(this);
        this.fButton.addActionListener(this);
        this.gButton.addActionListener(this);
        this.hButton.addActionListener(this);
        this.jButton.addActionListener(this);
        this.kButton.addActionListener(this);
        this.lButton.addActionListener(this);
        this.semicolonButton.addActionListener(this);
        this.commaButton.addActionListener(this);
        this.enterButton.addActionListener(this);
        this.shift1Button.addActionListener(this);
        this.zButton.addActionListener(this);
        this.xButton.addActionListener(this);
        this.cButton.addActionListener(this);
        this.vButton.addActionListener(this);
        this.bButton.addActionListener(this);
        this.nButton.addActionListener(this);
        this.mButton.addActionListener(this);
        this.traingleLeftButton.addActionListener(this);
        this.traingleRightButton.addActionListener(this);
        this.questionButton.addActionListener(this);
        this.shift2Button.addActionListener(this);
        this.spaceButton.addActionListener(this);
    }

    /**
     * Define all sign buttons
     */
    private void defineSignButtons() {
        this.signButtons = new ArrayList<JButton>();
        this.signButtons.add(this.tildaButton);
        this.signButtons.add(this.oneButton);
        this.signButtons.add(this.twoButton);
        this.signButtons.add(this.treeButton);
        this.signButtons.add(this.fourButton);
        this.signButtons.add(this.fiveButton);
        this.signButtons.add(this.sixButton);
        this.signButtons.add(this.sevenButton);
        this.signButtons.add(this.eightButton);
        this.signButtons.add(this.nineButton);
        this.signButtons.add(this.zeroButton);
        this.signButtons.add(this.underscoreButton);
        this.signButtons.add(this.plusButton);
        this.signButtons.add(this.squereLeftButton);
        this.signButtons.add(this.squereRightButton);
        this.signButtons.add(this.pipeButton);
        this.signButtons.add(this.semicolonButton);
        this.signButtons.add(this.commaButton);
        this.signButtons.add(this.traingleLeftButton);
        this.signButtons.add(this.traingleRightButton);
        this.signButtons.add(this.questionButton);
    }

    /**
     * Define all letter buttons
     */
    private void defineLetterButtons() {
        this.letterButtons = new ArrayList<JButton>();
        this.letterButtons.add(this.qButton);
        this.letterButtons.add(this.wButton);
        this.letterButtons.add(this.eButton);
        this.letterButtons.add(this.rButton);
        this.letterButtons.add(this.tButton);
        this.letterButtons.add(this.yButton);
        this.letterButtons.add(this.uButton);
        this.letterButtons.add(this.iButton);
        this.letterButtons.add(this.oButton);
        this.letterButtons.add(this.pButton);
        this.letterButtons.add(this.aButton);
        this.letterButtons.add(this.sButton);
        this.letterButtons.add(this.dButton);
        this.letterButtons.add(this.fButton);
        this.letterButtons.add(this.gButton);
        this.letterButtons.add(this.hButton);
        this.letterButtons.add(this.jButton);
        this.letterButtons.add(this.kButton);
        this.letterButtons.add(this.lButton);
        this.letterButtons.add(this.zButton);
        this.letterButtons.add(this.xButton);
        this.letterButtons.add(this.cButton);
        this.letterButtons.add(this.vButton);
        this.letterButtons.add(this.bButton);
        this.letterButtons.add(this.nButton);
        this.letterButtons.add(this.mButton);
    }

    /**
     * Display a letter on the frame
     * @param  letter the letter to display
     */
    private void displayLetter(String letter) {
        if(wasMaxLineNumberReached()) {
            return;
        }

        this.textArea.setFont(this.getFont());
        if(this.capsState || this.shiftLeftState || this.shiftRightState)
            this.textArea.append(letter.toUpperCase());
        else
            this.textArea.append(letter.toLowerCase());
    }

    /**
     * Display a sign on the frame
     * @param  sign the sign to display
     */
    private void displaySign(String sign) {
        if(wasMaxLineNumberReached()) {
            return;
        }

        String [] splitSign = sign.split(" / ");
        this.textArea.setFont(getFont());
        if(this.capsState || this.shiftLeftState || this.shiftRightState)
            this.textArea.append(splitSign[1]);
        else
            this.textArea.append(splitSign[0]);
    }

    /**
     * Checks if the maximum number of lines was reached
     * @return   true if the maximum number of lines was reached, false otherwise
     */
    private boolean wasMaxLineNumberReached() {

        if (this.textArea.getLineCount() > this.textAreaHeight / this.lineHeight) {
            JOptionPane.showMessageDialog(null, "The maximum number of lines was reached",
                    "Virtual Keyboard", JOptionPane.ERROR_MESSAGE);
            return true;
        }
        return false;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        /* Display plain signs and letters */
        if (this.signButtons.contains(e.getSource())) {
            displaySign(((JButton)e.getSource()).getText());
        } else if (this.letterButtons.contains(e.getSource())) {
            displayLetter(((JButton)e.getSource()).getText());
        }

        /* Display special test signs */
        else if(e.getSource() == this.enterButton) {
            if(!wasMaxLineNumberReached()) {
                this.textArea.append("\n");
            }
        } else if(e.getSource() == this.tabButton) {
            if(!wasMaxLineNumberReached()) {
                this.textArea.append("    ");
            }
        } else if(e.getSource() == this.spaceButton) {
            if(!wasMaxLineNumberReached()) {
                this.textArea.append(" ");
            }
        }

        /* Tend to special buttons */
        else if(e.getSource() == this.backspaceButton && this.textArea.getText().length() != 0) {
            this.textArea.setText(this.textArea.getText().substring(0, this.textArea.getText().length() - 1));
        } else if(e.getSource() == this.capsButton) {
            if(this.capsState) {
                this.capsButton.setBackground(this.buttonColor);
            } else {
                this.capsButton.setBackground(Color.LIGHT_GRAY);
            }
            this.capsState = !this.capsState;
        } else if(e.getSource() == this.shift1Button) {
            if(this.shiftLeftState) {
                this.shift1Button.setBackground(this.buttonColor);
            } else {
                this.shift1Button.setBackground(Color.LIGHT_GRAY);
            }

            this.shiftLeftState = !this.shiftLeftState;
        } else if(e.getSource() == this.shift2Button) {
            if(this.shiftRightState) {
                this.shift2Button.setBackground(this.buttonColor);
            } else {
                this.shift2Button.setBackground(Color.LIGHT_GRAY);
            }

            this.shiftRightState = !this.shiftRightState;
        }
    }
}