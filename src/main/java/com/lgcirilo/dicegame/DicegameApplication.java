package com.lgcirilo.dicegame;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DicegameApplication {

    public static void main(String[] args) {
        SpringApplication.run(DicegameApplication.class, args);
        Player[] players = new Player[2];
        players[0] = new Player("Player 1");
        players[1] = new Player("Player 2");
        int numberOfDice = 3;
        int numberOfTurns = 6;
        BoeingGame bg = new BoeingGame(numberOfTurns, numberOfDice, players);
        System.out.println(String.format(Messages.WINNER_MESSAGE, bg.play().getName()));
    }
}
