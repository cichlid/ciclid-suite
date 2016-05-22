package alpha114.tool.cichlid.autogenerationtool.src.builder;

import alpha114.tool.cichlid.autogenerationtool.Builder;
import alpha114.tool.cichlid.autogenerationtool.data.FrameData;
import alpha114.tool.cichlid.autogenerationtool.data.FrameItemData;
import alpha114.tool.cichlid.autogenerationtool.data.TableListItemData;

/**
 * @author kurisakisatoshi
 */
public class SearchResultCssBuilder implements Builder {

    private final FrameData frameData;

    public SearchResultCssBuilder(FrameData frameData) {
        this.frameData = frameData;
    }

    @Override
    public String build() {

        StringBuilder sb = new StringBuilder();

        sb.append("@charset \"UTF-8\";").append("\n");
        sb.append("").append("\n");
        sb.append("/**").append("\n");
        sb.append(" * ").append(frameData.getFrameName()).append("のカラム幅のスタイルシート").append("\n");
        sb.append(" */").append("\n");
        sb.append("").append("\n");

        if (!frameData.getItems().isEmpty()) {
            sb.append("/* ↓の150px適当な値なので修正すること */").append("\n");
            sb.append("").append("\n");
        }

        for (FrameItemData item : frameData.getItems()) {

            if (!item.isTable()) {
                continue;
            }

            for (TableListItemData clm : item.getTableColumns()) {

                if (clm.isHeader()) {

                    sb.append("/* ").append(clm.getLabel()).append(" */").append("\n");
                    sb.append("td.").append(clm.getId()).append(" {").append("\n");
                    sb.append("    width:").append(150).append("px;").append("\n");
                    sb.append("}").append("\n");
                    sb.append("").append("\n");
                }
            }
        }

        sb.append("").append("\n");

        return sb.toString();
    }
}
