package trackerapp.ui;

import trackerapp.dao.*;
import java.io.FileInputStream;
import java.util.Properties;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
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
    TextFileDao filereader = new TextFileDao();

    @Override
    public void init() throws Exception {
        Properties properties = new Properties();

        properties.load(new FileInputStream("config.properties"));

        this.title = properties.getProperty("title", "Masterpiece Tracker");
        welcomeHeader = properties.getProperty("welcomeHeader", "Hola!");
        String welcomeFile = properties.getProperty("welcomeTextFile");

        this.welcomeText = filereader.getAsString(welcomeFile);
    }

    @Override
    public void start(Stage stage) {

        VBox welcomePane = new VBox();
        HBox welcomeButtons = new HBox();
        Separator separator = new Separator();
        ScrollPane welcomeTextPane = new ScrollPane();

        welcomePane.setSpacing(20);

        Label welcomeLabel = new Label(welcomeHeader);
        welcomeTextPane.setContent(new Label(welcomeText));

        Button openMasterpieceButton = new Button("lataa");
        Button newMasterpieceButton = new Button("uusi");
        Button closeButton = new Button("sulje");

        welcomeButtons.getChildren().addAll(newMasterpieceButton, openMasterpieceButton, closeButton);
        welcomePane.setAlignment(Pos.CENTER);
        welcomePane.setPadding(new Insets(20, 20, 20, 20));

        welcomeButtons.setAlignment(Pos.CENTER);
        welcomeButtons.setSpacing(30);

        welcomePane.getChildren().addAll(welcomeLabel, separator, welcomeTextPane, welcomeButtons);
        
        closeButton.setOnAction(e -> {
            stage.close();
        });

        welcomeScene = new Scene(welcomePane, 600, 400);
        stage.setScene(welcomeScene);

        stage.setTitle(title);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
