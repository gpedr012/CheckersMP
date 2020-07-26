package checkers;

import checkers.player.EasyAI;
import checkers.player.HumanPlayer;
import checkers.player.Player;
import checkers.ui.Board;
import checkers.ui.ColorToggleMenu;
import checkers.ui.Piece;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class MainMenu extends Application
{

    private enum SceneType
    {
        MAIN, LOCAL, ONLINE, SINGLE_PLAYER, TWO_PLAYER;
    }

    Stage mainStage;
    @Override
    public void start(Stage stage) throws Exception
    {
        Scene mainMenuScene = setUpScene(SceneType.MAIN);
        mainStage = stage;
        stage.setScene(mainMenuScene);
        stage.show();

    }

    public Scene setUpScene(SceneType sceneType)
    {
        BorderPane root = getUISkeleton();
        Button defaultBtnOne = new Button();
        Button defaultBtnTwo = new Button();

        defaultBtnOne.setId("accept-btn");
        defaultBtnOne.setPrefSize(200, 55);

        defaultBtnTwo.setId("accept-btn");
        defaultBtnTwo.setPrefSize(200, 55);

        VBox center = (VBox)root.getCenter();

        Button bottomBtn = (Button)root.getBottom();

        if(sceneType == SceneType.MAIN)
        {

            defaultBtnOne.setText("LOCAL PLAY");
            defaultBtnTwo.setText("ONLINE PLAY");
            bottomBtn.setText("QUIT");
            defaultBtnOne.setOnAction(event -> changeScene(setUpScene(SceneType.LOCAL)));
            //defaultBtnTwo.setOnAction(event -> changeScene(setUpScene(SceneType.ONLINE)));
            bottomBtn.setOnAction(event -> Platform.exit());
            center.getChildren().addAll(defaultBtnOne, defaultBtnTwo);
        }
        else if(sceneType == SceneType.LOCAL)
        {
            defaultBtnOne.setText("ONE PLAYER");
            defaultBtnTwo.setText("TWO PLAYERS");
            bottomBtn.setText("GO BACK");

            defaultBtnOne.setOnAction(event -> changeScene(setUpScene(SceneType.SINGLE_PLAYER)));
            defaultBtnTwo.setOnAction(event -> changeScene(setUpScene(SceneType.TWO_PLAYER)));
            bottomBtn.setOnAction(event -> changeScene(setUpScene(SceneType.MAIN)));
            center.getChildren().addAll(defaultBtnOne, defaultBtnTwo);
        }
        else if(sceneType == SceneType.ONLINE)
        {}
        else if(sceneType == SceneType.SINGLE_PLAYER || sceneType == SceneType.TWO_PLAYER)
        {

            Label chooseColorLbl = new Label("CHOOSE A COLOR");
            Button playBtn = new Button("PLAY");
            ColorToggleMenu colorSelector = new ColorToggleMenu();

            chooseColorLbl.setId("subtitle-label");
            playBtn.setId("accept-btn");

            center.setAlignment(Pos.TOP_CENTER);
            bottomBtn.setText("GO BACK");

            center.getChildren().addAll(chooseColorLbl, colorSelector);
            bottomBtn.setOnAction(e -> changeScene(setUpScene(SceneType.LOCAL)));

            if(sceneType == SceneType.SINGLE_PLAYER)
            {
                ToggleGroup difficultyToggles = new ToggleGroup();
                ToggleButton easyToggle = new ToggleButton("EASY");
                ToggleButton mediumToggle = new ToggleButton("MEDIUM");
                HBox difficultyToggleContainer = new HBox(easyToggle, mediumToggle);
                Label difficultyLbl = new Label("CHOOSE A DIFFICULTY");

                easyToggle.setId("accept-btn");
                mediumToggle.setId("accept-btn");
                difficultyLbl.setId("subtitle-label");

                easyToggle.setToggleGroup(difficultyToggles);
                mediumToggle.setToggleGroup(difficultyToggles);
                difficultyToggles.selectToggle(easyToggle);

                difficultyToggleContainer.setAlignment(Pos.CENTER);
                difficultyToggleContainer.setSpacing(20);


                center.getChildren().addAll(difficultyLbl, difficultyToggleContainer, playBtn);

                playBtn.setOnAction(e ->
                {
                    try
                    {
                        startSingleGame(((ToggleButton) difficultyToggles.getSelectedToggle()).getText().toLowerCase(), colorSelector.getSelection());
                    } catch (Exception exception)
                    {
                        exception.printStackTrace();
                    }
                });

            }
            else
            {
                center.getChildren().add(playBtn);

                playBtn.setOnAction(e ->
                {
                    try
                    {
                        startTwoPlayerGame(colorSelector.getSelection());
                    } catch (Exception exception)
                    {
                        exception.printStackTrace();
                    }
                });

            }







        }





        return new Scene(root, 500, 500);
    }

    private void startTwoPlayerGame(Piece.PieceColor colorSelected) throws Exception
    {
        Board gameBoard = new Board();
        Piece.PieceColor playerTwoColor = colorSelected == Piece.PieceColor.DARK ? Piece.PieceColor.LIGHT : Piece.PieceColor.DARK;
        GameLoop twoPlayerGame = new GameLoop(gameBoard, new HumanPlayer(colorSelected, 1, gameBoard), new HumanPlayer(playerTwoColor, 2, gameBoard));
        twoPlayerGame.start(mainStage);
    }

    private void startSingleGame(String selectedDifficulty, Piece.PieceColor selectedColor) throws Exception
    {
        Board gameBoard = new Board();
        Player playerOne = new HumanPlayer(selectedColor, 1, gameBoard);
        Piece.PieceColor AIcolor = selectedColor == Piece.PieceColor.DARK ? Piece.PieceColor.LIGHT : Piece.PieceColor.DARK;

        if(selectedDifficulty.equals("easy"))
        {
            Player playerTwo = new EasyAI(AIcolor, 2, gameBoard);
            GameLoop easyGame = new GameLoop(gameBoard, playerOne, playerTwo);

            easyGame.start(mainStage);

        }
        else if(selectedDifficulty.equals("medium"))
        {

        }


    }

    private BorderPane getUISkeleton()
    {
        BorderPane root = new BorderPane();
        root.getStylesheets().add("checkers/menustyle.css");
        root.setId("pane");

        Label title = new Label("CHECKERS");
        title.setId("title-label");

        BorderPane.setAlignment(title, Pos.TOP_CENTER);
        root.setTop(title);

        VBox centerVBox = new VBox();
        centerVBox.setAlignment(Pos.TOP_CENTER);
        centerVBox.setSpacing(20d);
        BorderPane.setMargin(centerVBox, new Insets(15, 0, 0, 0));
        root.setCenter(centerVBox);

        Button bottomBtn = new Button();
        bottomBtn.setId("cancel-btn");
        bottomBtn.setPrefSize(175, 35);
        BorderPane.setAlignment(bottomBtn, Pos.BOTTOM_CENTER);
        BorderPane.setMargin(bottomBtn, new Insets(20, 0, 33, 0));
        root.setBottom(bottomBtn);

        return root;
    }

    private void changeScene(Scene scene)
    {
        mainStage.setScene(scene);
    }


    public static void main(String[] args)
    {
        launch(args);
    }
}
