package trackerapp.ui;

import trackerapp.dao.*;
import trackerapp.domain.Controller;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Properties;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 *
 * @author lehtonep
 */
public class TrackerAppUi extends Application {

    private String title, welcomeHeader, welcomeText;

    private Scene welcomeScene;
    private Scene settingsScene;
    private Scene mainScene;

    private TextFileDao filereader = new TextFileDao();
    MasterpieceDao masterpieceDao = new FileMasterpieceDao();
    private Controller controller;
    
    private Font masterpieceGridFont;

    @Override
    public void init() throws Exception {
        Properties properties = new Properties();

        properties.load(new FileInputStream("config.properties"));

        title = properties.getProperty("title", "Masterpiece Tracker");
        welcomeHeader = properties.getProperty("welcomeHeader", "Hola!");
        String welcomeFile = properties.getProperty("welcomeTextFile");
        welcomeText = filereader.getAsString(welcomeFile);

        controller = new Controller(masterpieceDao);
        masterpieceGridFont = new Font(15);
    }

    @Override
    public void start(Stage stage) {

        setWelcomeScene(stage);

        BorderPane mainPane = new BorderPane();
        ToolBar toolBar = new ToolBar();
        ScrollPane masterpieceView = new ScrollPane();
        Button playButton = new Button("play");
        Button stopButton = new Button("stop");
        Button pauseButton = new Button("pause");
        Button bpmButton = new Button("aseta");
        Button randomMasterpieceButton = new Button("satunnainen mestariteos");

        GridPane masterpieceGrid = new GridPane();
        HBox infoBar = new HBox(30);
        Label infoLabel = new Label(controller.getInfo());
        TextField bpmField = new TextField();
        bpmField.setPrefWidth(40);
        bpmField.setText("180");

        toolBar.getItems().addAll(playButton, stopButton, pauseButton, new Separator(), new Label("iskua minuutissa: "), bpmField, bpmButton, new Separator(), randomMasterpieceButton);
        infoBar.getChildren().add(infoLabel);
        infoLabel.setFont(new Font(20));
        controller.setInfoBar(infoLabel);

        pauseButton.setDisable(true);

        playButton.setOnAction(e -> {
            pauseButton.setDisable(false);
            controller.play();

        });
        stopButton.setOnAction(e -> {
            pauseButton.setDisable(true);
            controller.stop();
        });
        pauseButton.setOnAction(e -> {
            controller.pause();
        });
        bpmButton.setOnAction(e -> {
            String bpmString = bpmField.getText();
            try {
                int bpm = Integer.valueOf(bpmString);
                if (bpm > 0 && bpm <= 600) {
                    controller.setBpm(bpm);
                } else {
                    showMessage("bpm", "syötä luku väliltä 1-600");
                }
            } catch (Exception ex) {
                showMessage("bpm", "syötä luku");
            }
        });
        randomMasterpieceButton.setOnAction(e -> {
            controller.setNewMasterpiece(119, 4);
            controller.randomMasterpiece();
            updateMasterpieceGrid(masterpieceGrid);
        });

        masterpieceView.setPadding(new Insets(10, 10, 10, 10));
        masterpieceView.setContent(masterpieceGrid);
        masterpieceGrid.setPadding(new Insets(30, 30, 30, 30));
        masterpieceGrid.setHgap(50);
        masterpieceGrid.setVgap(5);
        
        mainPane.setTop(toolBar);
        mainPane.setCenter(masterpieceView);
        mainPane.setBottom(infoBar);

        mainPane.setPadding(new Insets(30, 30, 30, 30));
        mainScene = new Scene(mainPane, 1000, 600);

        stage.setScene(welcomeScene);

        new AnimationTimer() {
            @Override
            public void handle(long now) {
                controller.run();               
                
            }
        }.start();

        stage.setTitle(title);
        stage.show();
    }

    private void updateMasterpieceGrid(GridPane masterpieceGrid) {
        masterpieceGrid.getChildren().clear();
        ArrayList<String[]> trackInfo = controller.getMasterpieceTrackInfo();
        if (trackInfo != null) {
            int row = 0;
            for (String[] objectId : trackInfo) {
                Label rowInfo = new Label("" + (row + 1));
                rowInfo.setFont(masterpieceGridFont);
                masterpieceGrid.add(rowInfo, 0, row);
                for (int track = 0; track < objectId.length; track++) {
                    Label objectInfo = new Label(objectId[track]);
                    objectInfo.setFont(masterpieceGridFont);
                    masterpieceGrid.add(objectInfo, track + 1, row);
                }
                row++;
            }
        }
    }

    private void setWelcomeScene(Stage stage) {

        VBox welcomePane = new VBox(20);
        HBox welcomeButtons = new HBox(30);
        Separator separator = new Separator();
        ScrollPane welcomeTextPane = new ScrollPane();

        Label welcomeLabel = new Label(welcomeHeader);
        welcomeTextPane.setContent(new Label(welcomeText));

        Button openMasterpieceButton = new Button("lataa");
        Button newMasterpieceButton = new Button("uusi");
        Button closeButton = new Button("sulje");

        welcomeButtons.getChildren().addAll(newMasterpieceButton, openMasterpieceButton, closeButton);
        welcomePane.setAlignment(Pos.CENTER);
        welcomePane.setPadding(new Insets(20, 20, 20, 20));

        welcomeButtons.setAlignment(Pos.CENTER);

        welcomePane.getChildren().addAll(welcomeLabel, separator, welcomeTextPane, welcomeButtons);

        newMasterpieceButton.setOnAction(e -> {
            controller.setNewMasterpiece(119, 4);
            stage.setScene(mainScene);
        });

        openMasterpieceButton.setOnAction(e -> {
            showMessage("lataa", "Tämä ei vielä ole mahdollista.");
        });

        closeButton.setOnAction(e -> {
            stage.close();
        });
        welcomeScene = new Scene(welcomePane, 600, 400);
    }

    private void showMessage(String notYetTitle, String message) {
        Stage notYet = new Stage();

        Label notYetLabel = new Label(message);
        Button okButton = new Button("selvä sitte...");
        VBox pane = new VBox(20);
        pane.setAlignment(Pos.CENTER);
        pane.getChildren().addAll(notYetLabel, okButton);
        okButton.setOnAction(e -> {
            notYet.close();
        });
        Scene notYetScene = new Scene(pane, 200, 100);
        notYet.setScene(notYetScene);

        notYet.setTitle(notYetTitle);
        notYet.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
