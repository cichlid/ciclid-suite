package alpha114.tool.cichlid.autogenerationtool.src.builder;

import alpha114.tool.cichlid.autogenerationtool.Builder;
import alpha114.tool.cichlid.autogenerationtool.data.FrameData;
import alpha114.tool.cichlid.autogenerationtool.data.FrameItemData;

/**
 * @author kurisakisatoshi
 */
public class CreateEmptyCssBuilder implements Builder {

    private final FrameData frameData;

    public CreateEmptyCssBuilder(FrameData frameData) {
        this.frameData = frameData;
    }

    @Override
    public String build() {

        StringBuilder sb = new StringBuilder();

        sb.append("@charset \"UTF-8\";").append("\n");
        sb.append("").append("\n");
        sb.append("/**").append("\n");
        sb.append(" * ").append(frameData.getFrameName()).append("のスタイルシート").append("\n");
        sb.append(" */").append("\n");
        sb.append("").append("\n");

        for (FrameItemData item : frameData.getItems()) {

            sb.append("/* ").append(item.getLabel()).append(" */").append("\n");
            if (item.getId().toLowerCase().startsWith("t")) {
                // T分はそのまま大文字始まりで「Txx」
                sb.append(".").append(item.getId()).append(" {").append("\n");

            } else {
                // ほかは小文字始まりで「sxx」
                sb.append(".").append(item.getId().toLowerCase()).append(" {").append("\n");
            }

            sb.append("    ").append("\n");
            sb.append("}").append("\n");
            sb.append("").append("\n");
        }

        return sb.toString();
    }
}
