package com.lgcirilo.dicegame.components;

import com.lgcirilo.dicegame.BoeingGame;
import com.lgcirilo.dicegame.BoeingGamePlayer;
import com.lgcirilo.dicegame.Messages;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class MainGameLoop implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        BoeingGamePlayer[] boeingGamePlayers = new BoeingGamePlayer[2];
        boeingGamePlayers[0] = new BoeingGamePlayer("Player 1");
        boeingGamePlayers[1] = new BoeingGamePlayer("Player 2");
        int numberOfDice = 3;
        int numberOfTurns = 6;
        BoeingGame bg = new BoeingGame(numberOfTurns, numberOfDice, boeingGamePlayers);
        System.out.println(String.format(Messages.WINNER_MESSAGE, bg.play().getName()));

    }
}
