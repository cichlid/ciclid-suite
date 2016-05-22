package alpha114.tool.cichlid.autogenerationtool.src.builder;

import alpha114.tool.cichlid.autogenerationtool.Builder;
import alpha114.tool.cichlid.autogenerationtool.data.FrameData;

/**
 * @author kurisakisatoshi
 */
public class CreateEmptyJsBuilder implements Builder {

    private final FrameData frameData;

    public CreateEmptyJsBuilder(FrameData frameData) {
        this.frameData = frameData;
    }

    @Override
    public String build() {

        StringBuilder sb = new StringBuilder();

        sb.append("/**").append("\n");
        sb.append(" * ").append(frameData.getFrameName()).append("のJavascript").append("\n");
        sb.append(" */").append("\n");
        sb.append("").append("\n");
        sb.append("").append("\n");
        sb.append("$(function() {").append("\n");
        sb.append("").append("\n");
        sb.append("});").append("\n");

        sb.append("").append("\n");

        return sb.toString();
    }
}
