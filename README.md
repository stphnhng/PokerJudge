# PokerJudge

A Java program that decides whether which of two given hands is the better poker hand.

## How to use

### Compile Java Code
javac PokerJudge.java

### Run Java Code
java PokerJudge hand1 hand2

Where hand1 and hand2 are poker hands with 5 cards represented in a two character format: (Value)(Suit).
An example of this is: AS (Ace)(Spades)

Example run command:
java PokerJudge AHKHQHJHTH ASKSQSJSTS

#### Suits
* S = Spades
* C = Clubs
* D = Diamonds
* H = Hearts

#### Values
* 2-9 = 2-9
* T = Ten
* J = Jack
* Q = Queen
* K = King
* A = Ace
