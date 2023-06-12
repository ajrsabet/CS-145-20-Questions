/*
* Programmer: Adam Sabet
* Class: CS145 with Jaramiah Ramsey
* Date Due: 5/30/2023
* Lab 6: 20 Questions

* This program will do the following: 
    * This program is the classis guessing game called "20 Questions." Each round of the game 
    * begins with the user thinking of an object. The computer will try to guess the object by
    * asking the user a series of yes or no questions. Eventually the computer will have asked
    * enough questions that it thinks it knows what object the user is thinking of.  It will 
    * make a guess about what the object is.  If this guess is correct, the computer wins; if 
    * not, the user wins. 


* For extra credit I: 
    *
*/

import java.io.PrintStream;
import java.util.Scanner;

public class QuestionTree {
    private QuestionNode root;
    private UserInterface ui;
    private int totalGames;
    private int gamesWon;

    // Create a binary tree and track stats
    public QuestionTree(UserInterface ui) {
        this.ui = ui;
        root = new QuestionNode("A: Computer");
        totalGames = 0;
        gamesWon = 0;
    }

    // play game
    public void play() {
        totalGames++;
        if (root == null) {
            return;
        }
        playRound(root);
    }

    // play a round
    private void playRound(QuestionNode node) {
        if (node.isLeaf()) {
            ui.print("Is it a " + node.data.substring(2) + "? ");
            boolean correctGuess = ui.nextBoolean();
            if (correctGuess) {
                ui.println("I win!");
                gamesWon++;
            } else {
                // get data from user to add a new node
                String object = getObject();
                String newQuestion = getQuestion(node.data, object);
                char answer = getAnswer(object);
                replaceNode(node, newQuestion, object, answer);
            }
        } else {
            // ask next question base on yes/no
            ui.print(node.data + " ");
            boolean answer = ui.nextBoolean();
            if (answer) {
                playRound(node.yesNode);
            } else {
                playRound(node.noNode);
            }
        }
    }
    
    // prompt user for object that they were thinking of
    private String getObject() {
        ui.print("I give up. What are you thinking of? ");
        String object = "A: " + ui.nextLine();
        return object;
    }

    // prompt user for a question for the object that they were thinking of
    private String getQuestion(String oldObject, String newObject) {
        ui.print("Give me a yes/no question that would distinguish a " + newObject.substring(2) +
                " from a " + oldObject.substring(2) + ": ");
        String question = "Q: " + ui.nextLine();
        return question;
    }

    // ask if the answer to the question is yes or no
    private char getAnswer(String object) {
        char answer = 'z';
        
        do {
            ui.print("What would the answer be for a " + object.substring(2) + "? ");
            answer = ui.nextLine().toLowerCase().charAt(0);
            if (answer != 'y' && answer != 'n') {
                ui.print("You must answer yes or no, try again.");
            }
        } while (answer != 'y' && answer != 'n');
        
        return answer;
    }

    // create and insert new node into the tree
    private void replaceNode(QuestionNode node, String question, String object, char answer) {
        String oldData = node.data;
        node.data = question;
        if (answer == 'y') {
            node.yesNode = new QuestionNode(object);
            node.noNode = new QuestionNode(oldData);
        } else if (answer == 'n') {
            node.yesNode = new QuestionNode(oldData);
            node.noNode = new QuestionNode(object);
        }
    }

    // load data from file
    public void load(Scanner input) {
        root = loadNode(input);
    }

    // create a node from each line of the file
    private QuestionNode loadNode(Scanner input) {
        
        //if (input.hasNextLine()) {
            
            String data = input.nextLine();
            QuestionNode node = new QuestionNode(data);
            if (data.startsWith("Q")) {
                node.yesNode = loadNode(input);
                node.noNode = loadNode(input);
            }
            return node;
        //}
    }

    // save the new tree to a file to be played later
    public void save(PrintStream output) {
        saveNode(output, root);
    }

    // save each node to the file
    private void saveNode(PrintStream output, QuestionNode node) {
        if (node.isLeaf()) {
            output.println(node.data);
        } else {
            output.println(node.data);
            saveNode(output, node.yesNode);
            saveNode(output, node.noNode);
        }
    }

    // return game count
    public int totalGames() {
        return totalGames;
    }

    // return games won
    public int gamesWon() {
        return gamesWon;
    }

}
