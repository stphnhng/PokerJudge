/*
    Stephen Hung
    PokerJudge.java

    The purpose of this .java file is to take in two poker hands and return 
    which poker hand is better according to the following rankings:
    - http://www.pokerlistings.com/poker-hand-ranking
*/

import java.util.Arrays;

public class PokerJudge{



    // Main Method
    public static void main(String[] args){
        // Declare poker hands here as Strings for usage later in the program.
        String hand1 = "";
        String hand2 = "";

        // Try & Catch Statement to initialize poker hands to catch if both hands are entered.
        try{
            hand1 = args[0];
            hand2 = args[1];
        }catch(Exception e){
            System.out.println("Please enter two poker hands into the program like such: \n");
            System.out.println("\tjava PokerJudge 2HQS2D2S5H 5C3CASQH9H\n");
            System.exit(0);
        }
        PokerJudge judge = new PokerJudge();
        System.out.println("Hand 1: " + hand1);
        System.out.println("Hand 2: " + hand2);
        judge.returnValue(hand1);
        judge.returnValue(hand2);
    }

    // Return Value of the hand passed into the method.
    public int returnValue(String hand){
        int value = 0;
        int[] ranks = new int[15]; // No item in 0 or 1.
        int[] suits = new int[5];
        // Try & Catch Statement to make sure hands are entered correctly and to count the cards.
        try{
            for(int i = 0; i < 5; i++){
                char cValue = hand.charAt(i*2);
                int rankIndex = Character.getNumericValue(cValue);
                switch(cValue){
                    case 'T': rankIndex = 10;
                    break;
                    case 'J': rankIndex = 11;
                    break;
                    case 'Q': rankIndex = 12;
                    break;
                    case 'K': rankIndex = 13;
                    break;
                    case 'A': rankIndex = 14;
                    break;
                }
                ranks[rankIndex]++;
                char cSuit = hand.charAt((i*2)+1);
                switch(cSuit){
                    case 'H': suits[0]++;
                    break;
                    case 'S': suits[1]++;
                    break;
                    case 'D': suits[2]++;
                    break;
                    case 'C': suits[3]++;
                    break;
                }
            }    
        }catch(Exception e){
            System.out.println("The hand entered as a parameter does not fit with the rules: \n");
            System.out.println("\t" + hand + "\n");
            e.printStackTrace(System.out);
            System.exit(1);
        }
        // TODO:
        //      Royal Flush: straight (10 - Ace) with 5 cards of the same suit
        //      Straight Flush: Any straight with all 5 cards of the same suit
        //      Four of a Kind: 4 cards of the same rank, 5th card is kicker
        //      Full House: 3 cards of the same rank and 2 cards of another rank (highest rank of 3 of a kind determines rank)
        //      Flush: 5 cards of the same suit (highest card determines rank of flush)
        //      Straight: 5 consecutive cards of different suits (highest card)
        //      Three of a Kind: 3 cards of same rank, highest non related card is kicker
        //      Two Pair: Two cards of same rank with another 2 of same rank, highest non related card is kicker
        //      One Pair: Two cards of same rank, highest non related card is kicker.
        //      High Card: Any hand not in the above hands, highest card is the rank.

        System.out.println("Is Straight?: " + Boolean.toString(isStraight(1,ranks)));
        System.out.println("Is Straight Flush?: " + Boolean.toString(isStraightFlush(ranks, suits)));
        System.out.println("Is Royal Flush?: " + Boolean.toString(isRoyalFlush(ranks, suits)));


        System.out.println(Arrays.toString(ranks));
        System.out.println(Arrays.toString(suits));
        return value;
    }

    /*
        Determines if a given hand's cards are all the same suit.
        Params:
            int[] suitCount: an int array counting each suit and their count in the hand.
    */
    public boolean handSameSuit(int[] suitCount){
        boolean allSameSuit = false;
        for(int i = 0; i < 4; i++){
            // check if not same suit by checking each suit count
            // if there is a suit count with 5, set allSameSuit to true.
            if(suitCount[i] == 5){
                allSameSuit = true;
            }
        }
        if(allSameSuit == false){
            return false;
        }
        return true;
    }

    public boolean isStraight(int startIndex, int[] rankCount){
        boolean possibleStraight = false;
        int handCounter = 0;
        for(int i = 1; i < rankCount.length; i++){
            if(rankCount[i] == 1){
                possibleStraight = true;
                handCounter++;
                if(handCounter == 5){
                    return true;
                }
            }else{
                possibleStraight = false;
                if(handCounter == 5){
                    return true;
                }
                handCounter = 0;
            }
        }
        return false;
    }

    public boolean isStraightFlush(int[] rankCount, int[] suitCount){
        // Any straight with all 5 cards of the same suit.

        if(!handSameSuit(suitCount)){
            return false;
        }

        if(isStraight(1, rankCount)){
            return true;
        }

        return false;
    }

    /*
        Determines if a given hand is a royal flush.
        Params:
            int[] rankCount: an int array counting each rank and their count in the hand.
            int[] suitCount: an int array counting each suit and their count in the hand.
    */
    public boolean isRoyalFlush(int[] rankCount, int[] suitCount){
        // Straight from 10 - Ace with all 5 cards of same suit.

        // entire hand is the same suit.
        if(!handSameSuit(suitCount)){
            return false;
        }

        if(isStraight(10,rankCount)){
            return true;
        }

        return false;
    }



    /*
        Get the Kicker (Tie-Breaker) of a hand
        Params:
            String hand: String containing the cards in a hand
            String type: String containing the type of a hand.
    */
    public int getKicker(String hand, String type){

        return -1;
    }




}