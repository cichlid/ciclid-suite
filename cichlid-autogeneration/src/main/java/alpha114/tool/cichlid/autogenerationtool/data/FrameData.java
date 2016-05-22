package alpha114.tool.cichlid.autogenerationtool.data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author kurisakisatoshi
 */
public class FrameData {


    public String frameId;
    public String frameName;
    public String title;
    public String titleId;
    private FrameType frameType;

    public String tableDefaultSortKey = "";
    public String tableDefaultSortOrder = "ASCEND";

    private List<FrameItemData> items = new ArrayList<FrameItemData>();

    public boolean searchFlag;
    public boolean tableFlag;
    public boolean settiongFlag;

    public void addItem(String id, String label) {
        FrameItemData item = new FrameItemData();
        item.id = id;
        item.label = label;
        items.add(item);
    }

    public String getLabel(String id) {
        for (FrameItemData item : items) {
            if (id.equals(item.id)) {
                return item.label;
            }
        }
        return "";
    }

    public FrameItemData getItemData(String id) {
        for (FrameItemData item : items) {
            if (id.equals(item.id)) {
                return item;
            }
        }
        return null;
    }

    public List<FrameItemData> getItems() {
        return items;
    }

    public FrameType getFrameType() {
        return frameType;
    }

    public void setFrameType(FrameType frameType) {
//        if (frameType == null) throw new NullPointerException("frameType is null. frameId is " + getFrameId());
        if (frameType == null) {
            System.out.println("frameType is null. frameId is " + this.frameId);
            frameType = FrameType.OTHER;
        }

        this.frameType = frameType;
    }

    public enum FrameType {

        Search,
        Setting,
        Detail,
        Assoc,
        OTHER,
        CUSTOM;
    }
}

