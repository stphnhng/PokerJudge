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
        double h1Value, h2Value = 0;
        int[] h1Ranks, h1Suits, h2Ranks, h2Suits = null;

        h1Ranks = judge.countRanks(hand1);
        h1Suits = judge.countSuits(hand1);
        h1Value = judge.getHandValue(h1Ranks, h1Suits);

        h2Ranks = judge.countRanks(hand2);
        h2Suits = judge.countSuits(hand2);
        h2Value = judge.getHandValue(h2Ranks, h2Suits);

        // If the hands have the same Poker Hand TYPE.
        if(h1Value == h2Value){
            if(judge.isRoyalFlush(h1Ranks, h1Suits)){
                // If the hand is a Royal Flush.
                System.out.println("\n\tAll Suits are equal, thus these hands are equal.\n");
                System.exit(2);
            }else if(judge.isFourOfKind(h1Ranks) || judge.isThreeOfKind(h1Ranks) || judge.isFullHouse(h1Ranks) || 
                judge.isStraight(1,h1Ranks) || judge.isFlush(h1Suits)){
                // If it is a four of kind, three of a kind, full house, straight or flush.
                // Find highest possible card and if there is a tie, find the next highest.
                double h1HighValue = judge.highestCard(h1Ranks);
                double h2HighValue = judge.highestCard(h2Ranks);
                while(h1HighValue == h2HighValue && h1HighValue != 0){
                    h1Ranks[judge.highestIndex(h1Ranks)] = 0;
                    h2Ranks[judge.highestIndex(h2Ranks)] = 0;
                    h1HighValue = judge.highestCard(h1Ranks);
                    h2HighValue = judge.highestCard(h2Ranks);
                }
                h1Value += h1HighValue;
                h2Value += h2HighValue;
            }else if(judge.isTwoPair(h1Ranks) || judge.isOnePair(h1Ranks)){
                // If the hand is a two pair or one pair.
                double h1Max = 0.0;
                double h2Max = 0.0;
                for(int i = h1Ranks.length - 1; i > 0; i--){
                    if(h1Ranks[i] == 2 && h1Ranks[i] == h2Ranks[i]){
                        // if both hands have a pair in the same location.
                        h1Ranks[i] = 0;
                        h2Ranks[i] = 0;
                    }
                    // If it is a pair, then it is the current max.
                    if(h1Ranks[i] == 2 && i > h1Max){
                        h1Max = i * 1.0;
                    }
                    if(h2Ranks[i] == 2 && i > h2Max){
                        h2Max = i * 1.0;
                    }
                }
                if(h1Max == 0){ // if there are no pairs, then choose the highest card (kicker)
                    h1Max = judge.highestCard(h1Ranks);
                }
                if(h2Max == 0){ 
                    h2Max = judge.highestCard(h2Ranks);
                }
                h1Value += h1Max;
                h2Value += h2Max;
            }else{
                // If there is no hand type, then choose the highest value card.
                double h1HighValue = judge.highestCard(h1Ranks);
                double h2HighValue = judge.highestCard(h2Ranks);
                while(h1HighValue == h2HighValue && h1HighValue != 0){
                    h1Ranks[judge.highestIndex(h1Ranks)] = 0;
                    h2Ranks[judge.highestIndex(h2Ranks)] = 0;
                    h1HighValue = judge.highestCard(h1Ranks);
                    h2HighValue = judge.highestCard(h2Ranks);
                }
                h1Value += h1HighValue;
                h2Value += h2HighValue;
            }
        }

        if(h1Value > h2Value){
            System.out.println("Hand one wins");
        }else if(h1Value < h2Value){
            System.out.println("Hand two wins");
        }else{
            // If the values are completely the same.
            System.out.println("Hands tied");
        }
    }

    /*
        Counts the number of each rank in a given hand.
        Params:
            String hand: The String representation of a pokerhand.
    */
    public int[] countRanks(String hand){
        int[] ranks = new int[15]; // No item in 0 or 1.
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
            }    
        }catch(Exception e){
            System.out.println("The ranks in the hand entered as a parameter does not fit with the rules: \n");
            System.out.println("\t" + hand + "\n");
            System.exit(1);
        }
        if(isStraight(1,ranks)){
            // Dealing with choosing ace for straights & straight flush.
            if(ranks[1] == ranks[2]  && ranks[2] == 1){
                ranks[14] = 0;
            }else if(ranks[13] == ranks[14] && ranks[14] == 1){
                ranks[1] = 0;
            }
        }
        return ranks;
    }

    /*
        Counts the number of suits in a given hand.
        Params:
            String hand: The String representation of a pokerhand.
    */
    public int[] countSuits(String hand){
        int[] suits = new int[5];
        // Try & Catch Statement to make sure hands are entered correctly and to count the cards.
        try{
            for(int i = 0; i < 5; i++){
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
            System.out.println("The suits in the hand entered as a parameter does not fit with the rules: \n");
            System.out.println("\t" + hand + "\n");
            System.exit(1);
        }
        return suits;
    }

    /*
        Return Value of the hand passed into the method.
        Params:
            int[] ranks: An int array where the index is the card # and the value is the count in the hand.
            int[] suits: An int array where the index is the card suit and the value is the count in the hand.
    */
    public double getHandValue(int[] ranks, int[] suits){

        double handRank = 0.0;

        if(isRoyalFlush(ranks,suits)){
            handRank += 10;
        }else if(isStraightFlush(ranks, suits)){
            handRank += 9;
        }else if(isFourOfKind(ranks)){
            handRank += 8;
        }else if(isFullHouse(ranks)){
            handRank += 7;
        }else if(isFlush(suits)){
            handRank += 6;
        }else if(isStraight(1, ranks)){
            handRank += 5;
        }else if(isThreeOfKind(ranks)){
            handRank += 4;
        }else if(isTwoPair(ranks)){
            handRank += 3;
        }else if(isOnePair(ranks)){
            handRank += 2;
        }else{
            handRank += highestCard(ranks); 
        }
        return handRank;
    }

    /*
        Returns the highest weighted card rank (weighted by the amount of such rank).
        Params:
            int[] rankCount: An int array where the index is the card # and the value is the count in the hand.
    */
    public double highestCard(int[] rankCount){
        int i = highestIndex(rankCount);
        return i * 1.0 * rankCount[i]; // weights the highest card based on how many there are
        // (pair of 2 is greater than a single 3).
    }

    /*
        Returns the rank of the highest card.
        Params:
            int[] rankCount: An int array where the index is the card # and the value is the count in the hand.
    */
    public int highestIndex(int[] rankCount){
        int max = 0;
        for(int i = rankCount.length - 1; i>= 0; i--){
            if(rankCount[i] > 0){
                return i;
            }
        }
        return max;
    }

    /*
        Returns whether or not the hand has one pair.
        Params:
            int[] rankCount: An int array where the index is the card # and the value is the count in the hand.
    */
    public boolean isOnePair(int[] rankCount){
        for(int i = 0; i < rankCount.length; i++){
            if(rankCount[i] == 2){
                return true;
            }
        }
        return false;
    }

    /*
        Determines if a given hand has Two Pairs in its hand.
        Params:
            int[] rankCount: An int array where the index is the card # and the value is the count in the hand.
    */
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

    /*
        Determines if a given hand is a Three of a Kind
        Params:
            int[] rankCount: An int array where the index is the card # and the value is the count in the hand.
    */
    public boolean isThreeOfKind(int[] rankCount){
        // Three of a Kind: 3 cards of same rank, highest non related card is kicker
        for(int i = 0; i < rankCount.length; i++){
            if(rankCount[i] == 3){
                return true;
            }
        }
        return false;
    }

    /*
        Determines if a given hand is a Full House
        Params:
            int[] rankCount: An int array where the index is the card # and the value is the count in the hand.
    */
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

    /*
        Determines if a given hand is a Four of a Kind
        Params:
            int[] rankCount: An int array where the index is the card # and the value is the count in the hand.
    */
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
            int[] rankCount: An int array where the index is the card # and the value is the count in the hand.
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
            int[] suitCount: An int array where the index is the card suit and the value is the count in the hand.
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
            int[] rankCount: An int array where the index is the card # and the value is the count in the hand.
            int[] suitCount: An int array where the index is the card suit and the value is the count in the hand.
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
            int[] rankCount: An int array where the index is the card # and the value is the count in the hand.
            int[] suitCount: An int array where the index is the card suit and the value is the count in the hand.
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




}