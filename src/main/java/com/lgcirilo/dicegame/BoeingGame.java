package com.lgcirilo.dicegame;


import java.util.Arrays;

// TODO - can I remove every ocurrence of the parameter Die[] dice?
// TODO - Unit tests
// TODO - review for object oriented design and code resusability
// TODO - write comments
public class BoeingGame implements Game{
    int numberOfTurns;
    int numberOfDice;
    Player[] players;
    Die[] dice;

    public BoeingGame(int numberOfTurns, int numberOfDice, Player[] players) {
        this.numberOfTurns = numberOfTurns;
        dice = new Die[numberOfDice];
        for (int i = 0; i < numberOfDice; i++) {
            dice[i] = new SixSidedDie();
        }
        this.players = players;
    }

    // TODO - check if points score after a bonus or mini bonus are to be computed
    @Override
    public void play() {
        for (int currentTurn = 1; currentTurn <= numberOfTurns; currentTurn++) {
            for (Player player: players) {
                int totalPointsForTurn = 0;
                do {
                    rollDice();
                    System.out.println("roll for player " + player.getName() + " in turn "
                        + currentTurn + " is " + Arrays.toString(dice) + " ");

                    if (isMiniBonus(dice, currentTurn, player)) {
                        rollDice();
                        System.out.println("roll for player " + player.getName() + " in turn "
                                + currentTurn + " is " + Arrays.toString(dice) + " ");
                        if(isPointsScored(dice, currentTurn, player)) {
                            totalPointsForTurn += 5; // substitute the number 5 for a global variable
                        }
                    } else if (isBonus(dice, currentTurn, player)) {
                        rollDice();
                        System.out.println("roll for player " + player.getName() + " in turn "
                                + currentTurn + " is " + Arrays.toString(dice) + " ");
                        if(isPointsScored(dice, currentTurn, player)) {
                            totalPointsForTurn += 11; // substitute the number 11 for a global variable
                        }
                    } else {
                        totalPointsForTurn = totalPointsForTurn + computePoints(player, dice, currentTurn);
                    }


//                  if (isMiniBonus(dice, currentTurn, player)) {
//                      rollDice();
//                      System.out.println("roll for player " + player.getName() + " in turn "
//                              + currentTurn + " is " + Arrays.toString(dice) + " ");
//                      if(isPointsScored(dice, currentTurn, player)) {
//                          totalPointsForTurn += 5; // substitute the number 5 for a global variable
//                      }
//                  }

//                  if (isBonus(dice, currentTurn, player)) {
//                      rollDice();
//                      System.out.println("roll for player " + player.getName() + " in turn "
//                              + currentTurn + " is " + Arrays.toString(dice) + " ");
//                      if(isPointsScored(dice, currentTurn, player)) {
//                          totalPointsForTurn += 11; // substitute the number 11 for a global variable
//                      }
//                  }
//
//                  if (!isBonus(dice, currentTurn, player) && !isMiniBonus(dice, currentTurn, player)) {
//                      totalPointsForTurn = totalPointsForTurn + computePoints(player, dice, currentTurn);
//                  }



                } while (!isTurnOver(player, dice, currentTurn));
                player.setScore(player.getScore() + totalPointsForTurn);
            }
        }
        for(Player player: players) {
            System.out.println(player.toString());
        }
    }

    private boolean isTurnOver(Player player, Die[] dice, int currentTurn) {
        return computePoints(player, dice, currentTurn) == 0;
    }

    private void rollDice() {
        for (Die die: dice) {
            die.roll();
        }
    }

    // TODO - turn this method into computePoints returning an int. Set Player's - OK
    // TODO - score elsewhere (important because the way it is now points are being computed twice.
    // TODO - Maybe do so in play() method. - OK
    //  TODO - After each roll considering bonuses and mini bonuses
    private int computePoints(Player player, Die[] dice, int currentTurn) {
        int totalPoints = 0;
        for (Die die: dice) {
            if(die.getFaceValue() == currentTurn) {
                totalPoints++;
                System.out.println("point scored for " + player.getName());
            }
        }
        return totalPoints;
    }

    private boolean isRollThreeOfAKind(Die[] dice) {
        int firstDieFaceValue = dice[0].getFaceValue();
        for (int i = 1; i < dice.length; i++) {
            if (dice[i].getFaceValue() != firstDieFaceValue) {
                return false;
            }
        }
        return true;
    }

    private boolean isMiniBonus(Die[] dice, int currentTurn, Player player) {
        if (isRollThreeOfAKind(dice) && dice[0].getFaceValue() != currentTurn) {
            player.setMiniBonusRollsCount(player.getMiniBonusRollsCount() + 1);
            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>It's a mini bonus for " + player.getName());
            return true;
        }
        return false;
    }

    private boolean isBonus(Die[] dice, int currentTurn, Player player) {
        if (isRollThreeOfAKind(dice) && dice[0].getFaceValue() == currentTurn) {
            player.setBonusRollsCount(player.getBonusRollsCount() + 1);
            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>It's a bonus for " + player.getName());
            return true;
        }

        return false;
    }

    private boolean isPointsScored(Die[] dice, int currentTurn, Player player) {
        return isMiniBonus(dice, currentTurn, player) ||
                isBonus(dice, currentTurn, player) ||
                computePoints(player, dice, currentTurn) > 0;
    }

    // TODO - implement
    private Player tieBreaker(Player[] players) {
        return new Player("dummy");
    }

    public static void main(String[] args) {
        Player[] players = new Player[2];
        players[0] = new Player("Player 1");
        players[1] = new Player("Player 2");
        int numberOfDice = 3;
        int numberOfTurns = 6;
        BoeingGame bg = new BoeingGame(numberOfTurns, numberOfDice, players);
        bg.play();
    }
}
