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
        int hand1Value, hand2Value = 0;
        System.out.println("Hand 1: " + hand1);
        hand1Value = judge.returnValue(hand1);
        System.out.println(hand1Value);
        System.out.println("Hand 2: " + hand2);
        hand2Value = judge.returnValue(hand2);
        System.out.println(hand2Value);

        if(hand1Value > hand2Value){
            System.out.println("Hand one wins");
        }else if(hand1Value < hand2Value){
            System.out.println("Hand two wins");
        }else{
            // tie.
            // Can ignore Royal Flush for ties because only 1 hand can get it.
            // Straight Flush ranked by highest ranking card.
            // Four of Kind ranked by highest ranking 4-card set.
            // Full House ranked by highest ranking 3-card set.
            // Flush ranked by highest ranking card.
            // Straight ranked by highest ranking card.
            // Three of a Kind ranked by highest 3 of a kind - if same, then highest kicker
            // Two Pairs ranked by highest pair - lowest pair - kicker card
            // One Pair ranked by highest pair - kicker card
            

        }
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
                    case 'A': 
                        ranks[1]++; // can also be 1.
                        rankIndex = 14;
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

        int handRank = 0;

        if(isRoyalFlush(ranks,suits)){
            handRank = 9;
        }else if(isStraightFlush(ranks, suits)){
            handRank = 8;
        }else if(isFourOfKind(ranks)){
            handRank = 7;
        }else if(isFullHouse(ranks)){
            handRank = 6;
        }else if(isFlush(suits)){
            handRank = 5;
        }else if(isStraight(1, ranks)){
            handRank = 4;
        }else if(isThreeOfKind(ranks)){
            handRank = 3;
        }else if(isTwoPair(ranks)){
            handRank = 2;
        }else if(isOnePair(ranks)){
            handRank = 1;
        }

        /*
        System.out.println("Highest Card?: " + Integer.toString(highestCard(ranks)));
        System.out.println("Is One Pair?: " + Boolean.toString(isOnePair(ranks)));
        System.out.println("Is Two Pair?: " + Boolean.toString(isTwoPair(ranks)));
        System.out.println("Is Three of a Kind?: " + Boolean.toString(isThreeOfKind(ranks)));
        System.out.println("Is Full House?: " + Boolean.toString(isFullHouse(ranks)));
        System.out.println("Is Four of a Kind?: " + Boolean.toString(isFourOfKind(ranks)));
        System.out.println("Is Flush?: " + Boolean.toString(isFlush(suits)));
        System.out.println("Is Straight?: " + Boolean.toString(isStraight(1,ranks)));
        System.out.println("Is Straight Flush?: " + Boolean.toString(isStraightFlush(ranks, suits)));
        System.out.println("Is Royal Flush?: " + Boolean.toString(isRoyalFlush(ranks, suits)));
        */
        value = handRank;

        System.out.println(Arrays.toString(ranks));
        System.out.println(Arrays.toString(suits));
        return value;
    }

    /*
        Returns the card index with the highest value in the hand.
    */
    public int highestCard(int[] rankCount){
        for(int i = rankCount.length - 1; i>= 0; i--){
            if(rankCount[i] > 0){
                return i;
            }
        }
        return 0;
    }

    /*
        Returns whether or not the hand has one pair.
    */
    public boolean isOnePair(int[] rankCount){
        for(int i = 0; i < rankCount.length; i++){
            if(rankCount[i] == 2){
                return true;
            }
        }
        return false;
    }

    public boolean isTwoPair(int[] rankCount){
        boolean firstPair = false;
        boolean secondPair = false;
        for(int i = 0; i < rankCount.length; i++){
            if(rankCount[i] == 2){
                firstPair = true;
            }
            if(firstPair && rankCount[i] == 2){
                secondPair = true;
            }
        }
        if(firstPair && secondPair){
            return true;
        }
        return false;
    }

    public boolean isThreeOfKind(int[] rankCount){
        // Three of a Kind: 3 cards of same rank, highest non related card is kicker
        for(int i = 0; i < rankCount.length; i++){
            if(rankCount[i] == 3){
                return true;
            }
        }
        return false;
    }

    public boolean isFullHouse(int[] rankCount){
        // Full House: 3 cards of the same rank and 2 cards of another rank (highest rank of 3 of a kind determines rank)
        boolean threeSame = false;
        boolean twoSame = false;
        for(int i = 0; i < rankCount.length; i++){
            if(rankCount[i] == 3){
                threeSame = true;
            }
            if(rankCount[i] == 2){
                twoSame = true;
            }
        }
        if(threeSame && twoSame){
            return true;
        }
        return false;
    }

    public boolean isFourOfKind(int[] rankCount){
        // Four of a Kind: 4 cards of the same rank, 5th card is kicker

        for(int i = 0; i < rankCount.length; i++){
            if(rankCount[i] == 4){
                return true;
            }
        }
        return false;
    }

    /*
        Determines if a given hand is a Straight.
        Params:
            int startIndex: the start point from which the cards will be checked.
                (i.e start from 1 starts checking for straights from 1 onward.)
            int[] rankCount: an int array counting each rank and their count in the hand.
    */
    public boolean isStraight(int startIndex, int[] rankCount){
        // Straight: 5 consecutive cards of different suits (highest card)
        boolean possibleStraight = false;
        int handCounter = 0;
        for(int i = startIndex; i < rankCount.length; i++){
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

    /*
        Determines if a given hand's cards are all the same suit.
        Params:
            int[] suitCount: an int array counting each suit and their count in the hand.
    */
    public boolean isFlush(int[] suitCount){
        // Flush: 5 cards of the same suit (highest card determines rank of flush)
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

    /*
        Determines if a given hand is a Straight Flush.
        Params:
            int[] rankCount: an int array counting each rank and their count in the hand.
            int[] suitCount: an int array counting each suit and their count in the hand.
    */
    public boolean isStraightFlush(int[] rankCount, int[] suitCount){
        // Any straight with all 5 cards of the same suit.

        if(!isFlush(suitCount)){
            return false;
        }

        if(isStraight(1, rankCount)){ // Starts at 1 to check for all cards.
            return true;
        }

        return false;
    }

    /*
        Determines if a given hand is a Royal Flush.
        Params:
            int[] rankCount: an int array counting each rank and their count in the hand.
            int[] suitCount: an int array counting each suit and their count in the hand.
    */
    public boolean isRoyalFlush(int[] rankCount, int[] suitCount){
        // Straight from 10 - Ace with all 5 cards of same suit.

        // entire hand is the same suit.
        if(!isFlush(suitCount)){
            return false;
        }

        if(isStraight(10,rankCount)){ // Starts at 10.
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