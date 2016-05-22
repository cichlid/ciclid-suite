package alpha114.tool.cichlid.autogenerationtool.data;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * @author kurisakisatoshi
 */
public class TriggerData {

    public String triggerName;
    public TableData tableData;
    public Trigger trigger;
    public String info;

    public void setTrigger(String trig) {

        for (Trigger t : Trigger.values()) {
            String[] s = t.name().toLowerCase().split("_");
            if (trig.toLowerCase().startsWith(s[0])
                    && trig.toLowerCase().endsWith(s[1])) {
                trigger = t;
                break;
            }
            System.out.println("おかしい->" + trig);
        }
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).toString();
    }



    public enum Trigger {
        BEFORE_DELETE,
        AFTER_DELETE;

        public String getTriggerName() {
            return name().replace("_", " ");
        }
    }
}
