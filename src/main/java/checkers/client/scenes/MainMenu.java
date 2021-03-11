package checkers.client.scenes;

import checkers.client.game.OnlineOpponent;
import checkers.client.network.ClientChannelInitializer;
import checkers.networkutils.Message;
import checkers.client.network.ClientNetworkHelper;
import checkers.client.game.EasyAI;
import checkers.client.game.HumanPlayer;
import checkers.client.game.Player;
import checkers.client.ui.Board;
import checkers.client.ui.ColorToggleMenu;
import checkers.client.ui.Piece;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class MainMenu extends Application {

    private enum SceneType {
        MAIN, LOCAL, ONLINE, SINGLE_PLAYER, TWO_PLAYER;
    }

    static Stage mainStage;

    private static boolean isConnected = false;
    private static BooleanProperty isFindingMatch = new SimpleBooleanProperty(false);

    private static Label serverMessage = new Label();

    static {
        serverMessage.setId("server-info");
    }

    @Override
    public void start(Stage stage) throws Exception {
        Scene mainMenuScene = setUpScene(SceneType.MAIN);
        mainStage = stage;
        stage.setScene(mainMenuScene);
        stage.setTitle("Main Menu");
        stage.show();


    }

    public Scene setUpScene(SceneType sceneType) {
        BorderPane root = getUISkeleton();
        Button defaultBtnOne = new Button();
        Button defaultBtnTwo = new Button();

        defaultBtnOne.setId("accept-btn");
        defaultBtnOne.setPrefSize(200, 55);

        defaultBtnTwo.setId("accept-btn");
        defaultBtnTwo.setPrefSize(200, 55);

        VBox center = (VBox) root.getCenter();

        Button bottomBtn = (Button) root.getBottom();

        if (sceneType == SceneType.MAIN) {

            defaultBtnOne.setText("LOCAL PLAY");
            defaultBtnTwo.setText("ONLINE PLAY");
            bottomBtn.setText("QUIT");
            defaultBtnOne.setOnAction(event -> changeScene(setUpScene(SceneType.LOCAL)));
            defaultBtnTwo.setOnAction(event -> changeScene(setUpScene(SceneType.ONLINE)));
            bottomBtn.setOnAction(event -> Platform.exit());
            center.getChildren().addAll(defaultBtnOne, defaultBtnTwo);
        } else if (sceneType == SceneType.LOCAL) {
            defaultBtnOne.setText("ONE PLAYER");
            defaultBtnTwo.setText("TWO PLAYERS");
            bottomBtn.setText("GO BACK");

            defaultBtnOne.setOnAction(event -> changeScene(setUpScene(SceneType.SINGLE_PLAYER)));
            defaultBtnTwo.setOnAction(event -> changeScene(setUpScene(SceneType.TWO_PLAYER)));
            bottomBtn.setOnAction(event -> changeScene(setUpScene(SceneType.MAIN)));
            center.getChildren().addAll(defaultBtnOne, defaultBtnTwo);
        } else if (sceneType == SceneType.ONLINE) {

            serverMessage.setText("");
            defaultBtnOne.setMinWidth(250);
            defaultBtnOne.setOnAction(event -> findMatch());
            defaultBtnOne.textProperty().bind(new ObjectBinding<String>() {

                {
                    super.bind(isFindingMatchProperty());
                }

                @Override
                protected String computeValue() {
                    if(isFindingMatch.get()) {
                        return "Cancel Matchmaking";
                    } else {
                        defaultBtnOne.setMinWidth(290);
                        return "Find Match";
                    }
                }
            });


            Label connStatus = new Label("Connection Status: ");
            Label actualStatus = new Label();

            connStatus.setId("subtitle-label");
            actualStatus.setId("subtitle-label");

            if (!isConnected) {
                defaultBtnOne.setDisable(true);

                Task<Channel> connectTask = new Task<Channel>() {
                    @Override
                    protected Channel call() throws Exception {
                        EventLoopGroup workerGroup = new NioEventLoopGroup();
                        ClientNetworkHelper.setEventLoopGroup(workerGroup);

                        Bootstrap bootstrap = new Bootstrap();
                        bootstrap.group(workerGroup)
                                .channel(NioSocketChannel.class)
                                .handler(new ClientChannelInitializer());

                        ChannelFuture future = bootstrap.connect(ClientNetworkHelper.getHost(), ClientNetworkHelper.getPort());
                        Channel userChannel = future.channel();

                        future.sync();

                        return userChannel;

                    }

                    @Override
                    protected void succeeded() {
                        ClientNetworkHelper.setUserChannel(getValue());
                        actualStatus.setText("Connected");
                        actualStatus.setTextFill(Color.GREEN);
                        defaultBtnOne.setDisable(false);
                        System.out.println(actualStatus.getTextFill().toString());
                        isConnected = true;


                    }

                    @Override
                    protected void failed() {
                        Alert alert = Message.createConnectionErrorAlert();
                        alert.showAndWait();

                        actualStatus.setText("Error");
                        actualStatus.setTextFill(Color.RED);
                        defaultBtnOne.setDisable(false);
                        defaultBtnOne.setOnAction(event -> changeScene(setUpScene(SceneType.ONLINE)));
                    }
                };

                new Thread(connectTask).start();
            }

            if(isConnected){
                actualStatus.setText("Connected");
                actualStatus.setTextFill(Color.GREEN);
                defaultBtnOne.setDisable(false);
                System.out.println(actualStatus.getId());
                System.out.println(actualStatus.getTextFill().toString());
                System.out.println(connStatus.getTextFill().toString());
            }

            bottomBtn.setText("GO BACK");
            bottomBtn.setOnAction(event -> changeScene(setUpScene(SceneType.MAIN)));


            center.getChildren().addAll(connStatus, actualStatus, defaultBtnOne, serverMessage);


        } else if (sceneType == SceneType.SINGLE_PLAYER || sceneType == SceneType.TWO_PLAYER) {

            Label chooseColorLbl = new Label("CHOOSE A COLOR");
            Button playBtn = new Button("PLAY");
            ColorToggleMenu colorSelector = new ColorToggleMenu();

            chooseColorLbl.setId("subtitle-label");
            playBtn.setId("accept-btn");

            center.setAlignment(Pos.TOP_CENTER);
            bottomBtn.setText("GO BACK");

            center.getChildren().addAll(chooseColorLbl, colorSelector);
            bottomBtn.setOnAction(e -> changeScene(setUpScene(SceneType.LOCAL)));

            if (sceneType == SceneType.SINGLE_PLAYER) {
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
                    try {
                        startSingleGame(((ToggleButton) difficultyToggles.getSelectedToggle()).getText().toLowerCase(), colorSelector.getSelection());
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                });

            } else {
                center.getChildren().add(playBtn);

                playBtn.setOnAction(e ->
                {
                    try {
                        startTwoPlayerGame(colorSelector.getSelection());
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                });

            }


        }


        return new

                Scene(root, 500, 500);

    }


    private void findMatch() {

        if(getIsFindingMatch()) {
            ClientNetworkHelper.cancelMatchMaking();

        } else {
            ClientNetworkHelper.findMatch();
        }



    }

    private void startTwoPlayerGame(Piece.PieceColor colorSelected) throws Exception {
        Board gameBoard = new Board();
        Piece.PieceColor playerTwoColor = colorSelected == Piece.PieceColor.DARK ? Piece.PieceColor.LIGHT : Piece.PieceColor.DARK;
        GameLoop twoPlayerGame = new GameLoop(gameBoard, new HumanPlayer(colorSelected, 1, gameBoard), new HumanPlayer(playerTwoColor, 2, gameBoard));
        twoPlayerGame.start(mainStage);
    }

    private void startSingleGame(String selectedDifficulty, Piece.PieceColor selectedColor) throws Exception {
        Board gameBoard = new Board();
        Player playerOne = new HumanPlayer(selectedColor, 1, gameBoard);
        Piece.PieceColor AIcolor = selectedColor == Piece.PieceColor.DARK ? Piece.PieceColor.LIGHT : Piece.PieceColor.DARK;

        if (selectedDifficulty.equals("easy")) {
            Player playerTwo = new EasyAI(AIcolor, 2, gameBoard);
            GameLoop easyGame = new GameLoop(gameBoard, playerOne, playerTwo);

            easyGame.start(mainStage);

        } else if (selectedDifficulty.equals("medium")) {

        }


    }

    public static void startOnlineGame(int matchId, Piece.PieceColor playerColor)  {
        Board gameBoard = new Board();
        Piece.PieceColor opponentColor = playerColor == Piece.PieceColor.DARK ? Piece.PieceColor.LIGHT : Piece.PieceColor.DARK;

        Player player = new HumanPlayer(playerColor, 1, gameBoard, matchId);
        Player onlineOpponent = new OnlineOpponent(opponentColor, 2, gameBoard);

        GameLoop onlineGame = new GameLoop(gameBoard, player, onlineOpponent);

        try {
            onlineGame.start(mainStage);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public static boolean getIsFindingMatch() {
        return isFindingMatch.get();
    }

    public static BooleanProperty isFindingMatchProperty() {
        return isFindingMatch;
    }

    public static void setIsFindingMatch(boolean isFindingMatch) {
        MainMenu.isFindingMatch.set(isFindingMatch);
    }

    private BorderPane getUISkeleton() {
        BorderPane root = new BorderPane();
        root.getStylesheets().add("menustyle.css");
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

    private void changeScene(Scene scene) {
        mainStage.setScene(scene);
    }

    public static void showServerMsg(String content) {

        serverMessage.setText(content);

    }

    public static void main(String[] args) {
        launch(args);
    }


}
