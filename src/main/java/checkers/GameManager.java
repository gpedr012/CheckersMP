package checkers;

import checkers.player.Player;
import checkers.ui.Board;
import checkers.ui.Piece;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import java.util.List;

public class GameManager
{
    private Board board;
    private Player playerOne;
    private Player playerTwo;

    public GameManager(Board board, Player playerOne, Player playerTwo)
    {
        this.board = board;
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
        initListeners();
    }

    public void startGame()
    {
        playerOne.setHasTurn(true);

    }

    private void initListeners()
    {
        playerOne.hasTurnProperty().addListener(new ChangeListener<Boolean>()
        {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean oldV, Boolean newV)
            {
                if(newV)
                {
                    playerOne.processTurn();
                }
                else
                {
                    playerTwo.setHasTurn(true);
                }
            }
        });

        playerTwo.hasTurnProperty().addListener(new ChangeListener<Boolean>()
        {
            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean oldV, Boolean newV)
            {
                if(newV)
                {
                    playerTwo.processTurn();
                }
                else
                {
                    playerOne.setHasTurn(true);
                }
            }
        });


    }


}
