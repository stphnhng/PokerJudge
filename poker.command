#! /bin/bash

cd ~/Desktop/'Software Interview Prep'/'Limeade Interview Prep'
javac PokerJudge.java 

echo 'Royal Flush'
java PokerJudge AHKHQHJHTH ASKSQSJSTS
echo ''

echo 'Straight'
java PokerJudge 7H6C5D4C3D 5S4D3C2SAC
echo ''

echo 'Flush'
java PokerJudge KC7C6C4C3C JHTH9H5H2H
echo ''

echo 'Straight Flush'
java PokerJudge 7H6H5H4H3H 5S4S3S2SAS
echo ''

echo '4 of a kind'
java PokerJudge QSQHQCQD4H JSJHJCJDKC
java PokerJudge QSQHQCQD4H QSQHQCQDTH
echo ''

echo '3 of a kind'
java PokerJudge JSJHJC9S5H 9H9C9DACKH
java PokerJudge JSJHJC9S5H JSJHJCTS5H
echo ''

echo 'Full House'
java PokerJudge TSTHTD2S2D 9S9C9DACAH
echo ''

echo 'Two Pair'
java PokerJudge 8S8D5S5C4D 7S7C6S6CKS
java PokerJudge KSKDTSTH3S KHKC8S8DQH
java PokerJudge QHQD9H9DAH QSQC9S9CTS
echo ''

echo 'One Pair'
java PokerJudge TCTD8C5H4H 8S8HASTS6C
java PokerJudge 7S7HKS9S4H 7C7DJSTC9C
echo ''

echo 'High Card'
java PokerJudge QSTD9S8H6D TS7S6H4D3D
java PokerJudge QSTD9S8H6D QSTD9S8H6D