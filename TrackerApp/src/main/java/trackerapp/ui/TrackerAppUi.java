
package trackerapp.ui;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 *
 * @author lehtonep
 */
public class TrackerAppUi extends Application {
    private String title;
    
    @Override
    public void init() {
        this.title = "Masterpiece tracker";        
    }
    
    @Override
    public void start(Stage stage) {
        
        stage.setTitle(title);
        stage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }    
}
