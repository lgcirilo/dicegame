package com.lgcirilo.dicegame;


import java.util.Arrays;

// TODO - can I remove every ocurrence of the parameter Die[] dice?
// TODO - Unit tests
// TODO - review for object oriented design and code resusability
// TODO - write comments
// TODO - remove all System.out.printlns
public class BoeingGame implements Game{
    int numberOfTurns;
    int numberOfDice;
    Player[] players;
    Die[] dice;

    public BoeingGame(int numberOfTurns, int numberOfDice, Player[] players) {
        this.numberOfTurns = numberOfTurns;
        dice = new SixSidedDie[numberOfDice];
        for (int i = 0; i < numberOfDice; i++) {
            dice[i] = new SixSidedDie();
        }
        this.players = players;
    }


    // TODO - check if points score after a bonus or mini bonus are to be computed
    @Override
    public Player play() {
        for (int currentTurn = 1; currentTurn <= numberOfTurns; currentTurn++) {
            for (Player player: players) {
                int totalPointsForTurn = 0;
                do {
                    rollDice();
                    showRollMessage(player, currentTurn, dice);
                    if (isMiniBonus(dice, currentTurn, player)) {
                        rollDice();
                        showRollMessage(player, currentTurn, dice);
                        if(isPointsScored(dice, currentTurn, player)) {
                            showMiniBonusMessage(player);
                            totalPointsForTurn += Constants.MINI_BONUS_POINTS;
                        }
                    } else if (isBonus(dice, currentTurn, player)) {
                        rollDice();
                        showRollMessage(player, currentTurn, dice);
                        if(isPointsScored(dice, currentTurn, player)) {
                            showBonusMessage(player);
                            totalPointsForTurn += Constants.BONUS_POINTS;
                        }
                    } else {
                        showPointsScoredMessage(player, dice, currentTurn);
                        totalPointsForTurn = totalPointsForTurn + computePoints(player, dice, currentTurn);
                    }
                } while (!isTurnOver(player, dice, currentTurn));

                player.setScore(player.getScore() + totalPointsForTurn);
            }
        }
        for(Player player: players) {
            System.out.println(player.toString());
        }

        // check for winner
        return winner(players);

    }

    protected boolean isTurnOver(Player player, Die[] dice, int currentTurn) {
        return computePoints(player, dice, currentTurn) == 0;
    }

    protected void rollDice() {
        for (Die die: dice) {
            die.roll();
        }
    }

    // TODO - turn this method into computePoints returning an int. Set Player's - OK
    // TODO - score elsewhere (important because the way it is now points are being computed twice.
    // TODO - Maybe do so in play() method. - OK
    //  TODO - After each roll considering bonuses and mini bonuses
    protected int computePoints(Player player, Die[] dice, int currentTurn) {
        int totalPoints = 0;
        for (Die die: dice) {
            if(die.getFaceValue() == currentTurn) {
                totalPoints++;
            }
        }
        return totalPoints;
    }

    protected boolean isRollThreeOfAKind(Die[] dice) {
        int firstDieFaceValue = dice[0].getFaceValue();
        for (int i = 1; i < dice.length; i++) {
            if (dice[i].getFaceValue() != firstDieFaceValue) {
                return false;
            }
        }
        return true;
    }

    protected boolean isMiniBonus(Die[] dice, int currentTurn, Player player) {
        if (isRollThreeOfAKind(dice) && dice[0].getFaceValue() != currentTurn) {
            player.setMiniBonusRollsCount(player.getMiniBonusRollsCount() + 1);
            return true;
        }
        return false;
    }

    protected boolean isBonus(Die[] dice, int currentTurn, Player player) {
        if (isRollThreeOfAKind(dice) && dice[0].getFaceValue() == currentTurn) {
            player.setBonusRollsCount(player.getBonusRollsCount() + 1);
            return true;
        }

        return false;
    }

    protected boolean isPointsScored(Die[] dice, int currentTurn, Player player) {
        return isMiniBonus(dice, currentTurn, player) ||
                isBonus(dice, currentTurn, player) ||
                computePoints(player, dice, currentTurn) > 0;
    }

    // TODO - implement

    protected Player winner(Player[] players) {
            if (players[0].getScore() > players[1].getScore()) return players[0];
            if (players[0].getScore() < players[1].getScore()) return players[1];
            if (players[0].getBonusRollsCount() > players[1].getBonusRollsCount()) return players[0];
            if (players[0].getBonusRollsCount() < players[1].getBonusRollsCount()) return players[1];
            if (players[0].getMiniBonusRollsCount() > players[1].getMiniBonusRollsCount()) return players[0];
            if (players[0].getMiniBonusRollsCount() < players[1].getMiniBonusRollsCount()) return players[1];
            return tieBreaker(players);
    }
    protected Player tieBreaker(Player[] players) {
        int currentTurn = this.numberOfTurns;
        do {
            for (Player player: players) {
                rollDice();
                player.setScore(player.getScore() + computePoints(player, dice,currentTurn));
            }
            System.out.println(players[0].getScore() +  "    " + players[1].getScore());
        } while (players[0].getScore() == players[1].getScore());

        System.out.println(players[0].getScore() +  "    " + players[1].getScore());

        return players[0].getScore() > players[1].getScore() ? players[0] : players[1];
    }

    protected void showRollMessage(Player player, int currentTurn, Die[] dice) {
        System.out.println(String.format(Messages.ROLL_MESSAGE, player.getName(),
                currentTurn,  Arrays.toString(dice)));
    }

    protected void showMiniBonusMessage(Player player) {
        System.out.println(String.format(Messages.MINI_BONUS_POINTS_AWARDED, player.getName()));
    }

    protected void showBonusMessage(Player player) {
        System.out.println(String.format(Messages.BONUS_POINTS_AWARDED, player.getName()));
    }

    protected void showPointsScoredMessage(Player player, Die[] dice, int currentTurn) {
        System.out.println(String.format(Messages.POINTS_SCORED,
                player.getName(), computePoints(player, dice, currentTurn)));
    }
}
