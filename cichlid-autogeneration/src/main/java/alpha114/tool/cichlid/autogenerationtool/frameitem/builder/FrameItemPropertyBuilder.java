package alpha114.tool.cichlid.autogenerationtool.frameitem.builder;

import alpha114.tool.cichlid.autogenerationtool.Builder;
import alpha114.tool.cichlid.autogenerationtool.data.FrameData;
import alpha114.tool.cichlid.autogenerationtool.data.FrameItemData;

/**
 * @author kurisakisatoshi
 */
public class FrameItemPropertyBuilder implements Builder {

    private final FrameData data;

    public FrameItemPropertyBuilder(FrameData data) {
        this.data = data;
    }

    @Override
    public String build() {

        StringBuilder sb = new StringBuilder();

        sb.append("#").append("\n");
        sb.append("# ").append(data.frameName).append("\n");
        sb.append("#").append("\n");
        sb.append("").append("\n");
        sb.append("# ★★自動生成のため本ファイルを直接修正しないこと★★").append("\n");
        sb.append("").append("\n");

        for (FrameItemData item : data.getItems()) {
            sb.append(data.frameId).append(".").append(item.id).append("=").append(item.label.replaceAll("\n|\r", "\\\\n")).append("\n");
        }

        sb.append("").append("\n");

        return sb.toString();
    }
}
