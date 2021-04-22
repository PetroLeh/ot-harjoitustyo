package trackerapp.ui;

import java.io.File;
import trackerapp.dao.*;
import trackerapp.domain.TrackerService;
import trackerapp.domain.Player;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Properties;
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
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 *
 * @author lehtonep
 */
public class TrackerAppUi extends Application {

    private String title, welcomeHeader, welcomeText;

    private Scene welcomeScene, settingsScene, mainScene;

    private FileChooser fileChooser;
    private TextFileDao filereader = new TextFileDao();
    private FileMasterpieceDao masterpieceDao = new FileMasterpieceDao();

    private TrackerService tracker;
    private Player player;

    private Font masterpieceGridFont;

    @Override
    public void init() throws Exception {
        Properties properties = new Properties();
        properties.load(new FileInputStream("config.properties"));

        title = properties.getProperty("title", "Masterpiece Tracker");
        welcomeHeader = properties.getProperty("welcomeHeader", "Hola!");
        String welcomeFile = properties.getProperty("welcomeTextFile");
        welcomeText = filereader.getAsString(welcomeFile);

        fileChooser = new FileChooser();
        fileChooser.setTitle(title + " - avaa");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("masterpiece files", "*.mp"));

        tracker = new TrackerService(masterpieceDao);
        player = new Player(tracker);
        masterpieceGridFont = new Font(15);
    }

    @Override
    public void start(Stage stage) {
        setMainScene(stage);
        setWelcomeScene(stage);

        stage.setScene(welcomeScene);
        stage.setTitle(title);
        stage.show();
    }

    public void setMainScene(Stage stage) {
        BorderPane mainPane = new BorderPane();
        ToolBar toolBar = new ToolBar();
        ScrollPane masterpieceView = new ScrollPane();
        GridPane masterpieceGrid = new GridPane();
        Button playButton = new Button("play");
        Button stopButton = new Button("stop");
        Button pauseButton = new Button("pause");
        Button bpmButton = new Button("aseta");
        Button addNewRowButton = new Button("lisää rivi");
        Button randomMasterpieceButton = new Button("satunnainen mestariteos");
        Button saveButton = new Button("tallenna");

        HBox infoBar = new HBox(30);
        Label infoLabel = new Label(tracker.getInfo());
        TextField bpmField = new TextField();
        bpmField.setPrefWidth(40);
        bpmField.setText("180");

        toolBar.getItems().addAll(playButton, stopButton, pauseButton, new Separator(),
                new Label("iskua minuutissa: "), bpmField, bpmButton, new Separator(),
                addNewRowButton, new Separator(),
                randomMasterpieceButton, new Separator(), saveButton);
        infoBar.getChildren().add(infoLabel);
        infoLabel.setFont(new Font(20));
        tracker.setInfoBar(infoLabel);

        pauseButton.setDisable(true);

        playButton.setOnAction(e -> {
            if (!tracker.getMasterpiece().isEmpty()) {
                pauseButton.setDisable(false);
                player.start();
            }
        });
        stopButton.setOnAction(e -> {
            pauseButton.setDisable(true);
            player.stop();
        });
        pauseButton.setOnAction(e -> {
            player.pause();
        });
        bpmButton.setOnAction(e -> {
            String bpmString = bpmField.getText();
            try {
                int bpm = Integer.valueOf(bpmString);
                if (bpm > 0 && bpm <= 600) {
                    tracker.setBpm(bpm);
                } else {
                    showMessage("bpm", "syötä luku väliltä 1-600");
                }
            } catch (Exception ex) {
                showMessage("bpm", "syötä luku");
            }
        });
        addNewRowButton.setOnAction(e -> {
            tracker.addNewRow();
            tracker.updateInfoBar();
            updateMasterpieceGrid(masterpieceGrid);
        });
        randomMasterpieceButton.setOnAction(e -> {
            tracker.setNewMasterpiece(119, 4);
            tracker.randomMasterpiece();
            updateMasterpieceGrid(masterpieceGrid);
        });
        saveButton.setOnAction(e -> {
            //masterpieceDao.setFile(fileChooser.showSaveDialog(stage));
            showMessage("tallenna", "Tämä ei ole vielä mahdollista");
        });

        masterpieceView.setPadding(new Insets(10, 10, 10, 10));
        masterpieceView.setContent(masterpieceGrid);
        masterpieceGrid.setPadding(new Insets(30, 30, 30, 30));
        masterpieceGrid.setHgap(50);
        masterpieceGrid.setVgap(5);
        updateMasterpieceGrid(masterpieceGrid);

        mainPane.setTop(toolBar);
        mainPane.setCenter(masterpieceView);
        mainPane.setBottom(infoBar);
        mainPane.setPadding(new Insets(30, 30, 30, 30));
        mainScene = new Scene(mainPane, 1100, 600);
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
        welcomeButtons.setAlignment(Pos.CENTER);

        welcomePane.setAlignment(Pos.CENTER);
        welcomePane.setPadding(new Insets(20, 20, 20, 20));
        welcomePane.getChildren().addAll(welcomeLabel, separator, welcomeTextPane, welcomeButtons);

        newMasterpieceButton.setOnAction(e -> {
            tracker.setNewMasterpiece(0, 4);
            stage.setScene(mainScene);
        });
        openMasterpieceButton.setOnAction(e -> {
            showMessage("lataa", "Tämä ei vielä ole mahdollista.");
            //File file = fileChooser.showOpenDialog(stage);
        });
        closeButton.setOnAction(e -> {
            stage.close();
        });
        welcomeScene = new Scene(welcomePane, 600, 400);
    }

    private void showMessage(String messageTitle, String message) {
        Stage messageStage = new Stage();
        Label messageLabel = new Label(message);
        Button okButton = new Button("selvä sitte...");
        VBox pane = new VBox(20);
        pane.setAlignment(Pos.CENTER);
        pane.getChildren().addAll(messageLabel, okButton);
        okButton.setOnAction(e -> {
            messageStage.close();
        });
        Scene messageScene = new Scene(pane, 200, 100);
        messageStage.setScene(messageScene);
        messageStage.setTitle(messageTitle);
        messageStage.show();
    }

    private void updateMasterpieceGrid(GridPane masterpieceGrid) {
        masterpieceGrid.getChildren().clear();
        ArrayList<String[]> trackInfo = tracker.getMasterpieceInfo();
        if (trackInfo != null) {
            int row = 0;
            for (String[] objectId : trackInfo) {
                Label rowInfo = new Label("" + (row + 1));
                rowInfo.setFont(masterpieceGridFont);
                masterpieceGrid.add(rowInfo, 0, row);
                for (int track = 0; track < objectId.length; track++) {
                    Button objectLabel = new Button(objectId[track]);
                    objectLabel.setFont(masterpieceGridFont);
                    String id = row + "," + track;
                    objectLabel.setId(id);
                    objectLabel.setOnAction(e -> {
                        System.out.println("napitettu: " + id);
                    });
                    masterpieceGrid.add(objectLabel, track + 1, row);
                }
                row++;
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
