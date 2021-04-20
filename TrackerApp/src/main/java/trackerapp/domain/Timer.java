package trackerapp.domain;

/**
 *
 * @author lehtonep
 */
public class Timer {

    private int tickTime;
    public long time;

    public Timer(int bpm) {
        setBpm(bpm);
        this.time = System.currentTimeMillis();
    }

    public boolean tick() {
        if (System.currentTimeMillis() - time >= this.tickTime) {
            time = System.currentTimeMillis();
            return true;
        }
        return false;
    }

    public void setBpm(int bpm) {
        if (bpm > 0) {
            setTickTimeInMillis(60000 / bpm);
        }
    }

    public void setTickTimeInMillis(int tickTime) {
        this.tickTime = tickTime;
    }

    public int getTickTime() {
        return tickTime;
    }
}
