
package trackerapp.domain;

/**
 *
 * @author lehtonep
 */
public class TrackContainer {
    private TrackObject[] objects;
    
    public TrackContainer(int tracks) {
        this.objects = new TrackObject[tracks];        
    }
    
    public boolean addObject(int track, TrackObject object) {
        if (track >= 0 && track < this.objects.length) {
            this.objects[track] = object;
            return true;
        }
        return false;
    }
    
    public TrackObject[] getAllObjects() {
        return this.objects;
    }
    
    public TrackObject getObject(int track) {
        if (track >= 0 && track < this.objects.length) {
            return this.objects[track];
        }
        return null;
    }
   
}
