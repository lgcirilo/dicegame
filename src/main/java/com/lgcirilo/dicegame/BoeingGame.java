package com.lgcirilo.dicegame;


import java.util.Arrays;

public class BoeingGame implements Game{
    int numberOfTurns;
    int numberOfDice;
    BoeingGamePlayer[] boeingGamePlayers;
    Die[] dice;

    public BoeingGame(int numberOfTurns, int numberOfDice, BoeingGamePlayer[] boeingGamePlayers) {
        this.numberOfTurns = numberOfTurns;
        this.numberOfDice = numberOfDice;
        dice = new SixSidedDie[numberOfDice];
        for (int i = 0; i < numberOfDice; i++) {
            dice[i] = new SixSidedDie();
        }
        this.boeingGamePlayers = boeingGamePlayers;
    }

    // Plays the game
    @Override
    public BoeingGamePlayer play() {
        for (int currentTurn = 1; currentTurn <= numberOfTurns; currentTurn++) {
            for (BoeingGamePlayer boeingGamePlayer : boeingGamePlayers) {
                int totalPointsForTurn = 0;
                do {
                    rollDice();
                    showRollMessage(boeingGamePlayer, currentTurn, dice);
                    if (isMiniBonus(dice, currentTurn, boeingGamePlayer)) {
                        rollDice();
                        showRollMessage(boeingGamePlayer, currentTurn, dice);
                        if(isPointsScored(dice, currentTurn, boeingGamePlayer)) {
                            showMiniBonusMessage(boeingGamePlayer);
                            totalPointsForTurn += Constants.MINI_BONUS_POINTS;
                        }
                    } else if (isBonus(dice, currentTurn, boeingGamePlayer)) {
                        rollDice();
                        showRollMessage(boeingGamePlayer, currentTurn, dice);
                        if(isPointsScored(dice, currentTurn, boeingGamePlayer)) {
                            showBonusMessage(boeingGamePlayer);
                            totalPointsForTurn += Constants.BONUS_POINTS;
                        }
                    } else {
                        showPointsScoredMessage(boeingGamePlayer, dice, currentTurn);
                        totalPointsForTurn = totalPointsForTurn + computePoints(boeingGamePlayer, dice, currentTurn);
                    }
                } while (isPointsScored(dice, currentTurn, boeingGamePlayer));

                boeingGamePlayer.setScore(boeingGamePlayer.getScore() + totalPointsForTurn);
            }
        }
        for(BoeingGamePlayer boeingGamePlayer : boeingGamePlayers) {
            System.out.println(boeingGamePlayer.toString());
        }


        return winner(boeingGamePlayers);

    }

    // Checks whether current turn is over
    protected boolean isTurnOver(BoeingGamePlayer boeingGamePlayer, Die[] dice, int currentTurn) {
        return computePoints(boeingGamePlayer, dice, currentTurn) == 0;
    }

    // Rolls all the three dice
    protected void rollDice() {
        for (Die die: dice) {
            die.roll();
        }
    }

    // Computes points for current turn
    protected int computePoints(BoeingGamePlayer boeingGamePlayer, Die[] dice, int currentTurn) {
        int totalPoints = 0;
        for (Die die: dice) {
            if(die.getFaceValue() == currentTurn) {
                totalPoints++;
            }
        }
        return totalPoints;
    }

    // Checks whether all dice have the saem face value. Useful to check for bonuses and mini bonuses
    protected boolean isRollThreeOfAKind(Die[] dice) {
        int firstDieFaceValue = dice[0].getFaceValue();
        for (int i = 1; i < dice.length; i++) {
            if (dice[i].getFaceValue() != firstDieFaceValue) {
                return false;
            }
        }
        return true;
    }

    // Checks whether this roll of dice is a mini bonus
    protected boolean isMiniBonus(Die[] dice, int currentTurn, BoeingGamePlayer boeingGamePlayer) {
        if (isRollThreeOfAKind(dice) && dice[0].getFaceValue() != currentTurn) {
            boeingGamePlayer.increaseMiniBonusRolls();
            return true;
        }
        return false;
    }

    // Checks whether this roll of dice is a bonus
    protected boolean isBonus(Die[] dice, int currentTurn, BoeingGamePlayer boeingGamePlayer) {
        if (isRollThreeOfAKind(dice) && dice[0].getFaceValue() == currentTurn) {
            boeingGamePlayer.increaseBonusRolls();
            return true;
        }

        return false;
    }

    // Checks if a point was score according to game definition
    protected boolean isPointsScored(Die[] dice, int currentTurn, BoeingGamePlayer boeingGamePlayer) {
        return isMiniBonus(dice, currentTurn, boeingGamePlayer) ||
                isBonus(dice, currentTurn, boeingGamePlayer) ||
                computePoints(boeingGamePlayer, dice, currentTurn) > 0;
    }

    // Returns the winner
    protected BoeingGamePlayer winner(BoeingGamePlayer[] boeingGamePlayers) {
            if (boeingGamePlayers[0].getScore() > boeingGamePlayers[1].getScore()) return boeingGamePlayers[0];
            if (boeingGamePlayers[0].getScore() < boeingGamePlayers[1].getScore()) return boeingGamePlayers[1];
            if (boeingGamePlayers[0].getBonusRollsCount() > boeingGamePlayers[1].getBonusRollsCount()) return boeingGamePlayers[0];
            if (boeingGamePlayers[0].getBonusRollsCount() < boeingGamePlayers[1].getBonusRollsCount()) return boeingGamePlayers[1];
            if (boeingGamePlayers[0].getMiniBonusRollsCount() > boeingGamePlayers[1].getMiniBonusRollsCount()) return boeingGamePlayers[0];
            if (boeingGamePlayers[0].getMiniBonusRollsCount() < boeingGamePlayers[1].getMiniBonusRollsCount()) return boeingGamePlayers[1];
            return tieBreaker(boeingGamePlayers);
    }

    // Does a tie breaker
    protected BoeingGamePlayer tieBreaker(BoeingGamePlayer[] boeingGamePlayers) {
        int currentTurn = this.numberOfTurns;
        do {
            for (BoeingGamePlayer boeingGamePlayer : boeingGamePlayers) {
                rollDice();
                boeingGamePlayer.setScore(boeingGamePlayer.getScore() + computePoints(boeingGamePlayer, dice,currentTurn));
            }
        } while (boeingGamePlayers[0].getScore() == boeingGamePlayers[1].getScore());

        return boeingGamePlayers[0].getScore() > boeingGamePlayers[1].getScore() ? boeingGamePlayers[0] : boeingGamePlayers[1];
    }

    protected void showRollMessage(BoeingGamePlayer boeingGamePlayer, int currentTurn, Die[] dice) {
        System.out.println(String.format(Messages.ROLL_MESSAGE, boeingGamePlayer.getName(),
                currentTurn,  Arrays.toString(dice)));
    }

    protected void showMiniBonusMessage(BoeingGamePlayer boeingGamePlayer) {
        System.out.println(String.format(Messages.MINI_BONUS_POINTS_AWARDED, boeingGamePlayer.getName()));
    }

    protected void showBonusMessage(BoeingGamePlayer boeingGamePlayer) {
        System.out.println(String.format(Messages.BONUS_POINTS_AWARDED, boeingGamePlayer.getName()));
    }

    protected void showPointsScoredMessage(BoeingGamePlayer boeingGamePlayer, Die[] dice, int currentTurn) {
        System.out.println(String.format(Messages.POINTS_SCORED,
                boeingGamePlayer.getName(), computePoints(boeingGamePlayer, dice, currentTurn)));
    }
}
