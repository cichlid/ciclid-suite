package alpha114.tool.cichlid.autogenerationtool.src.builder;

import alpha114.tool.cichlid.autogenerationtool.Builder;
import alpha114.tool.cichlid.autogenerationtool.Utils;
import alpha114.tool.cichlid.autogenerationtool.data.FrameData;
import alpha114.tool.cichlid.autogenerationtool.data.FrameItemData;

/**
 * @author kurisakisatoshi
 */
public class SearchResultButtonPieceJspBuilder implements Builder {

    private final FrameData frameData;
    private final FrameItemData item;

    public SearchResultButtonPieceJspBuilder(FrameData frameData, FrameItemData item) {
        this.frameData = frameData;
        this.item = item;
    }


    @Override
    public String build() {

        StringBuilder sb = new StringBuilder();

        sb.append("").append("\n");
        sb.append("<% // ★★自動生成のため本ファイルを直接修正しないこと★★ %>").append("\n");
        sb.append("").append("\n");

        sb.append("<%@ page language=\"java\" contentType=\"text/html; charset=UTF-8\" pageEncoding=\"UTF-8\"%>").append("\n");
        sb.append("").append("\n");
        sb.append("<%@ taglib uri=\"/struts-tags\" prefix=\"s\" %>").append("\n");
        sb.append("").append("\n");
        sb.append("").append("\n");
        sb.append("<% // ").append(frameData.getFrameName()).append("のボタン %>").append("\n");
        sb.append("").append("\n");

        if (!Utils.isCommonButton(item)) {
             sb.append("<% // ").append(item.getLabel()).append(" %>\n");
             sb.append("<input type=\"button\"")
               .append(" value=\"<s:property value=\"%{getText('").append(frameData.getFrameId()).append(".").append(Utils.getLabelId_cal(frameData, item)).append("')}\"/>\"")
               .append(" name=\"").append(item.getId().toLowerCase()).append("\"")
               .append(" id=\"").append(item.getId().toLowerCase()).append("\"")
               .append(" class=\"").append(item.getId()).append("\"/>").append("\n");

        } else {
            sb.append("<% // 共通的なボタンのため、個別出力はなし %>\n");

        }

        return sb.toString();
    }
}
