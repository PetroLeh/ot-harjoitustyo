package trackerapp.domain;

/**
 *
 * @author lehtonep
 */
public class TrackContainer {

    private TrackObject[] objects;

    public TrackContainer(int tracks) {
        objects = new TrackObject[tracks];
    }

    public void activate() {
        for (TrackObject object : objects) {
            if (object != null) {
                object.activate();
            }
        }
    }

    public boolean addObject(int track, TrackObject object) {
        if (track >= 0 && track < objects.length) {
            objects[track] = object;
            return true;
        }
        return false;
    }

    public TrackObject[] getAllObjects() {
        return objects;
    }

    public TrackObject getObject(int track) {
        if (track >= 0 && track < objects.length) {
            return objects[track];
        }
        return null;
    }

    public String getObjectId(int track) {
        if (track >= 0 && track < objects.length) {
            if (objects[track] != null) {
                return objects[track].getId();
            }
            return ObjectType.EMPTY.toString();
        }
        return "";
    }

    public String getAllObjectIds() {
        String ids = "";
        for (int i = 0; i < objects.length - 1; i++) {
            ids = ids + objects[i].getId() + ";";
        }
        ids += objects[objects.length - 1].getId();
        return ids;
    }

    public int size() {
        return objects.length;
    }

    public String toString() {
        String containerInfo = "";
        for (TrackObject object : objects) {
            if (object == null) {
                containerInfo += ObjectType.EMPTY;
            } else {
                containerInfo += object.getId() + " ";
            }
        }
        return containerInfo;
    }

}
