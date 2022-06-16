package com.lgcirilo.dicegame;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DicegameApplication {

    public static void main(String[] args) {
        SpringApplication.run(DicegameApplication.class, args);
        BoeingGamePlayer[] boeingGamePlayers = new BoeingGamePlayer[2];
        boeingGamePlayers[0] = new BoeingGamePlayer("Player 1");
        boeingGamePlayers[1] = new BoeingGamePlayer("Player 2");
        int numberOfDice = 3;
        int numberOfTurns = 6;
        BoeingGame bg = new BoeingGame(numberOfTurns, numberOfDice, boeingGamePlayers);
        System.out.println(String.format(Messages.WINNER_MESSAGE, bg.play().getName()));
    }
}
