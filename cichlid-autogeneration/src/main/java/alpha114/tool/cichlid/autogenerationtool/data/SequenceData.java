package alpha114.tool.cichlid.autogenerationtool.data;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * @author kurisakisatoshi
 */
public class SequenceData {

    public String instanceName;
    public String seqName;
    public String name;
    public int start;
    public int minValue;
    public int maxValue;
    public int increment = 1;
    public boolean cycle = true;
    public int cache = 0;
    public boolean order = true;


    public String toString() {
        return new ToStringBuilder(this).toString();
    }

}
