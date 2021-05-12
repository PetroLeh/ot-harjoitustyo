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
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
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

    private String title, welcomeHeader, welcomeText, currentFileLocation;
    private Scene welcomeScene, settingsScene, mainScene;
    private FileChooser fileChooser;
    private TextFileDao filereader = new TextFileDao();
    private FileMasterpieceDao masterpieceDao = new FileMasterpieceDao();
    private FileInstrumentLibraryDao instrumentLibrary;
    private TrackerService tracker;
    private Player player;
    private Font masterpieceGridFont;
    private GridPane masterpieceGrid;

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
        currentFileLocation = "pieces";
        instrumentLibrary = new FileInstrumentLibraryDao("instruments.csv");
        tracker = new TrackerService(masterpieceDao, instrumentLibrary);
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
        ToolBar playerBar = new ToolBar();
        ScrollPane masterpieceView = new ScrollPane();
        ScrollPane instrumentView = new ScrollPane();
        masterpieceGrid = new GridPane();

        VBox instrumentBox = new VBox(10);
        HBox infoBar = new HBox(30);
        Label infoLabel = new Label("");
        TextField bpmField = new TextField();

        Button playButton = new Button("play");
        Button stopButton = new Button("stop");
        Button pauseButton = new Button("pause");
        Button bpmButton = new Button("aseta");
        Button addNewRowButton = new Button("lisää rivi");
        Button removeRowButton = new Button("poista rivi");
        Button newMasterpieceButton = new Button("uusi mestariteos");
        Button saveButton = new Button("tallenna");
        Button openButton = new Button("avaa");
        Button emptySelectionButton = new Button("tyhjä");

        TreeView objectTree = new TreeView();
        TreeItem items = new TreeItem("items");
        TreeItem instruments = new TreeItem("instrumentit");
        TreeItem controls = new TreeItem("kontrollit");

        items.getChildren().addAll(instruments, controls);
        Label currentInstrument = new Label("Valittuna:\nei mitään");

        objectTree.setRoot(items);
        objectTree.setShowRoot(false);

        instrumentLibrary.getInstruments().forEach(instrument -> {
            TreeItem instrumentItem = new TreeItem(instrument);
            instrumentLibrary.getInstrumentIdList(instrument).forEach(id -> {
                TreeItem item = new TreeItem(id);
                Button selectItemButton = new Button(id);
                selectItemButton.setPrefWidth(100);
                selectItemButton.setOnAction(e -> {
                    tracker.setSelectedObject(instrument, id);
                    currentInstrument.setText("Valittuna:\n" + instrument + ": " + id);
                });
                item.setGraphic(selectItemButton);
                instrumentItem.getChildren().add(item);
            });
            instruments.getChildren().add(instrumentItem);
        });

        emptySelectionButton.setOnAction(e -> {
            currentInstrument.setText("Valittuna:\ntyhjä");
            tracker.setSelectedObject(null, null);
        });

        instrumentBox.getChildren().addAll(objectTree, new Separator(), emptySelectionButton, new Separator(), currentInstrument);
        instrumentBox.setPadding(new Insets(20, 20, 20, 20));

        bpmField.setPrefWidth(40);
        bpmField.setText("180");

        playerBar.getItems().addAll(playButton, stopButton, pauseButton, new Separator(),
                new Label("iskua minuutissa: "), bpmField, bpmButton, new Separator(),
                addNewRowButton, removeRowButton, new Separator(), saveButton, openButton, new Separator(), newMasterpieceButton);
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
                    tracker.getMasterpiece().setBpm(bpm);
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
            updateMasterpieceGrid();
        });
        removeRowButton.setOnAction(e -> {
            tracker.removeRow();
            tracker.updateInfoBar();
            updateMasterpieceGrid();
        });
        newMasterpieceButton.setOnAction(e -> {
            tracker.setNewMasterpiece(0, 6);
            updateMasterpieceGrid();
        });
        saveButton.setOnAction(e -> {
            pauseButton.setDisable(true);
            player.stop();
            fileChooser.setInitialFileName(tracker.getMasterpiece().getName() + ".mp");
            File dir = new File("pieces");
            if (dir.exists()) {
                fileChooser.setInitialDirectory(dir);
            }

            File file = fileChooser.showSaveDialog(stage);

            if (file != null) {

                String name = file.getName();
                if (name.endsWith(".mp")) {
                    name = name.replace(".mp", "");
                } else {
                    file = new File(file.getPath() + ".mp");
                }
                tracker.getMasterpiece().setName(name);
                tracker.updateInfoBar();
                masterpieceDao.setFile(file);
                masterpieceDao.saveMasterpiece(tracker.getMasterpiece(), instrumentLibrary);
            }
        });
        openButton.setOnAction(e -> {
            pauseButton.setDisable(true);
            player.stop();
            File dir = new File("pieces");
            if (dir.exists()) {
                fileChooser.setInitialDirectory(dir);
            }
            File file = fileChooser.showOpenDialog(stage);

            if (file != null) {
                masterpieceDao.setFile(file);
                tracker.setMasterpiece(masterpieceDao.loadMasterpiece());
                updateMasterpieceGrid();
            }
        });

        masterpieceView.setPadding(new Insets(10, 10, 10, 10));
        masterpieceView.setContent(masterpieceGrid);
        masterpieceGrid.setPadding(new Insets(30, 30, 30, 30));
        masterpieceGrid.setHgap(50);
        masterpieceGrid.setVgap(5);
        updateMasterpieceGrid();

        instrumentView.setContent(instrumentBox);
        mainPane.setTop(playerBar);
        mainPane.setCenter(masterpieceView);
        mainPane.setRight(instrumentView);
        mainPane.setBottom(infoBar);
        mainPane.setPadding(new Insets(30, 30, 30, 30));

        mainScene = new Scene(mainPane, 1200, 800);
    }

    private void setWelcomeScene(Stage stage) {

        VBox welcomePane = new VBox(20);
        HBox welcomeButtons = new HBox(30);

        ScrollPane welcomeTextPane = new ScrollPane();

        Button openMasterpieceButton = new Button("lataa");
        Button newMasterpieceButton = new Button("uusi");
        Button closeButton = new Button("sulje");

        Label welcomeLabel = new Label(welcomeHeader);
        welcomeTextPane.setContent(new Label(welcomeText));

        welcomeButtons.getChildren().addAll(newMasterpieceButton, openMasterpieceButton, closeButton);

        welcomePane.getChildren().addAll(welcomeLabel, new Separator(), welcomeTextPane, welcomeButtons);

        welcomePane.setPadding(new Insets(20, 20, 20, 20));
        welcomeButtons.setAlignment(Pos.CENTER);
        welcomePane.setAlignment(Pos.CENTER);

        newMasterpieceButton.setOnAction(e -> {
            tracker.setNewMasterpiece(0, 6);
            stage.setScene(mainScene);
            stage.setMaximized(true);
        });
        openMasterpieceButton.setOnAction(e -> {
            File dir = new File("pieces");
            if (dir.exists()) {
                fileChooser.setInitialDirectory(dir);
            }
            File file = fileChooser.showOpenDialog(stage);
            if (file != null) {
                masterpieceDao.setFile(file);
                tracker.setMasterpiece(masterpieceDao.loadMasterpiece());
                stage.setScene(mainScene);
                updateMasterpieceGrid();
            }
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
        messageStage.setAlwaysOnTop(true);
        messageStage.show();
    }

    private void updateMasterpieceGrid() {
        masterpieceGrid.getChildren().clear();
        ArrayList<String[]> trackInfo = tracker.getMasterpieceInfo();
        if (trackInfo != null) {
            int row = 0;
            for (String[] objectId : trackInfo) {
                Label rowInfo = new Label("" + (row + 1));
                rowInfo.setFont(masterpieceGridFont);
                masterpieceGrid.add(rowInfo, 0, row);
                for (int track = 0; track < objectId.length; track++) {
                    Button objectButton = new Button(objectId[track]);
                    objectButton.setFont(masterpieceGridFont);
                    objectButton.setPrefWidth(150);
                    String id = row + "," + track;
                    objectButton.setId(id);
                    int onRow = row;
                    int onTrack = track;
                    objectButton.setOnAction(e -> {
                        tracker.addObject(onRow, onTrack, tracker.getSelectedObject());
                        objectButton.setText(tracker.getMasterpiece().getTrackContainer(onRow).getObjectId(onTrack));
                    });
                    masterpieceGrid.add(objectButton, track + 1, row);
                }
                row++;
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
