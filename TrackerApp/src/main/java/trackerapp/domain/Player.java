package trackerapp.domain;

import javafx.animation.AnimationTimer;

/**
 *
 * @author lehtonep
 */
public class Player extends AnimationTimer {

    private TrackerService tracker;
    private Timer timer;
    private boolean pause;

    public Player(TrackerService tracker) {
        this.tracker = tracker;
        this.timer = new Timer(120);
        this.pause = false;
    }

    @Override
    public void start() {
        timer.setBpm(tracker.getCurrentBpm());
        pause = false;
        super.start();
    }

    @Override
    public void handle(long now) {
        if (!pause) {
            if (timer.tick()) {
                int currentRow = tracker.getCurrentRow();
                timer.setBpm(tracker.getCurrentBpm());
                tracker.activateTrackContainer(currentRow);
                tracker.setPlayerStatus("toistetaan (" + (currentRow + 1) + "): " + tracker.getTrackInfo(currentRow));
                tracker.updateInfoBar();
                tracker.nextRow();
            }
        }
    }

    @Override
    public void stop() {
        tracker.setPlayerStatus("pysäytetty");
        tracker.updateInfoBar();
        tracker.setCurrentRow(0);
        pause = false;
        super.stop();
    }

    public void pause() {
        int currentRow = tracker.getCurrentRow();
        tracker.setPlayerStatus("pause (" + (currentRow + 1) + "): " + tracker.getTrackInfo(currentRow));
        tracker.updateInfoBar();
        pause = !pause;
    }
}
