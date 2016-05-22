package alpha114.tool.cichlid.autogenerationtool.dialogmessage.builder;

import java.util.List;

import alpha114.tool.cichlid.autogenerationtool.Builder;
import alpha114.tool.cichlid.autogenerationtool.data.DialogMessageData;

/**
 * @author kurisakisatoshi
 */
public class DialogMessagePropertyBuilder implements Builder {

    private final List<DialogMessageData> msgs;

    public DialogMessagePropertyBuilder(List<DialogMessageData> msgs) {
        this.msgs = msgs;
    }

    @Override
    public String build() {

        StringBuilder sb = new StringBuilder();

        String fName = "";
        for (DialogMessageData data : msgs) {

            if (fName.equals("") || !fName.equals(data.frameName)) {
                if (!fName.equals("")) {
                    sb.append("\n");
                }

                fName = data.frameName;
                sb.append("# " + fName);
                sb.append("\n");
            }

            sb.append(data.frameId);
            sb.append("_");
            sb.append(data.index);
            sb.append("=");
            sb.append(data.message.replaceAll("\n", "\\\\n"));
            sb.append("\n");
        }

        return sb.toString();
    }
}
