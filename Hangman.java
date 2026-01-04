import javax.swing.*; 
import java.awt.event.*; 
import java.util.Random;
import java.util.HashSet;
import java.util.Set;

public class Hangman {

    static String targetWord;
    static Set<Character> guessedLetters = new HashSet<>();

    // Track wrong guesses to put under Letters Guessed:
    static Set<Character> wrongGuesses = new HashSet<>();
    static Random random = new Random();
    static String[] wordList = {"banana", "trigonometry", "popmart", "keyboard", "mickey", "mountain", "wizard","apple", "isolationism","appeasement","expansionism","military","marbles"};

    public static void main (String[] args){

        // Making window
        JFrame frame = new JFrame("Claire's Hangman Game");

        // Defining textboxes, buttons, words
        JTextField textField = new JTextField(10);
        JButton submitButton = new JButton("Submit");
        JLabel resultLabel = new JLabel("Your guess will show here"); 
        JButton restartButton = new JButton("Restart");
        restartButton.setEnabled(false); // Initially disabled until the game ends

        // Hangman graphic panel
        HangmanGraphic hangmanPanel = new HangmanGraphic(); // Instantiate 
        hangmanPanel.setPreferredSize(new java.awt.Dimension(400, 300)); // Set size for hangman drawing

        // Word user has to guess
        targetWord = wordList[random.nextInt(wordList.length)];

        JLabel wordDisplay = new JLabel(maskWord()); // Displaying the masked word initially

        // Create the label for displaying wrong guesses (Letters Guessed)
        JLabel guessedLettersLabel = new JLabel("Letters Guessed: ");  // Added guessedLettersLabel initialization

        // Initial wrong guesses text
        String wrongGuessesText = "Letters Guessed: " + wrongGuesses.toString();
        guessedLettersLabel.setText(wrongGuessesText); //update words on screen for that

        // ActionListener for Submit button
        ActionListener submitAction = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	if (hangmanPanel.getWrongGuesses() >= 6) {
            		resultLabel.setText("You lose! The word was: " + targetWord);
            		submitButton.setEnabled(false);  // Disable further guesses
            		restartButton.setEnabled(true);
            		return;  // Exit the method to prevent further guesses
        		}

                String userInput = textField.getText().trim();
                resultLabel.setText("You entered: " + userInput);
                textField.setText(""); // Clear field

                if (userInput.length() != 1 || !Character.isLetter(userInput.charAt(0))) {
                    resultLabel.setText("Please enter a single letter.");
                    return;
                }

                char guess = Character.toLowerCase(userInput.charAt(0));

                if (guessedLetters.contains(guess)) {
                    resultLabel.setText("You already guessed '" + guess + "'.");
                    return;
                }

                guessedLetters.add(guess);

                if (targetWord.contains(userInput)) {
                    resultLabel.setText("Correct!");
                } else {
                    resultLabel.setText("Wrong!");
                    wrongGuesses.add(guess);  // Add wrong guess to wrongGuesses set
                    hangmanPanel.setWrongGuesses(hangmanPanel.getWrongGuesses() + 1);  // Add the figure
                }

                wordDisplay.setText(maskWord()); // Update the masked word display
                guessedLettersLabel.setText("Letters Guessed: " + wrongGuesses);  // Update the guessed letters label

                if (!maskWord().contains("_")) {
                    resultLabel.setText("You won! The word was: " + targetWord);
                    submitButton.setEnabled(false); // Disable submit button after win
                    restartButton.setEnabled(true); // Enable restart button after win
                }
            }
        };

        // Attach submit action to the button
        submitButton.addActionListener(submitAction);

        // KeyListener for Enter key
        textField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER && submitButton.isEnabled()) {
                    submitAction.actionPerformed(null); // Trigger the submit action when Enter is pressed
                }
            }
        });

        restartButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Reset the guessed letters and wrong guesses
                guessedLetters.clear();
                wrongGuesses.clear();  // Clear wrong guesses
                hangmanPanel.setWrongGuesses(0);

                // Select a new word
                targetWord = wordList[random.nextInt(wordList.length)];

                // Update the masked word display
                wordDisplay.setText(maskWord());

                // Enable the submit button for new guesses
                submitButton.setEnabled(true);

                // Reset result label and text field
                resultLabel.setText("Your guess will show here");
                textField.setText("");

                // Re-enable the restart button for future use
                restartButton.setEnabled(false);

                guessedLettersLabel.setText("Letters Guessed: ");  // Reset guessed letters label
            }
        });

        // Add all the previous code into the screen
        JPanel inputPanel = new JPanel();
        BoxLayout boxLayout = new BoxLayout(inputPanel, BoxLayout.Y_AXIS);
        inputPanel.setLayout(boxLayout);  // Set BoxLayout for vertical stacking

        inputPanel.add(new JLabel("Enter a letter:"));
        inputPanel.add(textField);
        inputPanel.add(submitButton);
        inputPanel.add(restartButton);

        inputPanel.add(resultLabel);
        inputPanel.add(wordDisplay);
        inputPanel.add(restartButton);
        
        // Trying to simulate a blank line
        JPanel blankPanel = new JPanel();
        blankPanel.setPreferredSize(new java.awt.Dimension(0, 20)); // 20 pixels of vertical space
        inputPanel.add(blankPanel); // Add the blank space to the panel

        // Add the guessed letters label
        inputPanel.add(guessedLettersLabel);

        // Container panel to hold both input and hangman graphic panel, so can show at the same time
        JPanel mainPanel = new JPanel();
        mainPanel.add(inputPanel);  
        mainPanel.add(hangmanPanel);

        // Set up main frame
        frame.add(mainPanel);
        frame.setSize(750, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false); //stops full screen
        frame.setVisible(true);

        // Event-driven model so it will continue to loop + event listener registering interactions
    }

    // The _ _ _ _ part of the game 
    // Separate method but still part of Hangman class because it's easier 
    static String maskWord() { 
        StringBuilder masked = new StringBuilder();
        for (char c : targetWord.toCharArray()) {
            if (guessedLetters.contains(c)) {
                masked.append(c).append(" ");
            } else {
                masked.append("_ ");
            }
        }
        return masked.toString().trim();
    }
}
