package alpha114.tool.cichlid.autogenerationtool.src.builder;

import alpha114.tool.cichlid.autogenerationtool.Builder;
import alpha114.tool.cichlid.autogenerationtool.Utils;
import alpha114.tool.cichlid.autogenerationtool.data.FrameData;
import alpha114.tool.cichlid.autogenerationtool.data.FrameItemData;

/**
 * @author kurisakisatoshi
 */
public class SearchResultButtonJspBuilder implements Builder {

    private final FrameData frameData;

    public SearchResultButtonJspBuilder(FrameData frameData) {
        this.frameData = frameData;
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
        sb.append("<% // ").append(frameData.getFrameName()).append("の画面下部ボタン %>").append("\n");
        sb.append("").append("\n");

        for (FrameItemData item : frameData.getItems()) {

            if (!item.isButton()) {
                continue;
            }

            if (Utils.isCommonButton(item)) {
                continue;
            }

            if ((frameData.getFrameId().equals("CompositionInformationControl_19"))
                    && (item.getLabel().equals("集計")
                            || item.getLabel().equals("集計条件クリア"))) {
                continue;
            }

            if (frameData.getFrameId().equals("CompositionInformationControl_35")
                    && item.getLabel().equals("削除")) {
                continue;
            }

            if (item.getLabel().equals("閉じる")) {
                sb.append("<% // ").append(item.getLabel()).append(" %>\n");
                sb.append("<input type=\"button\"")
                  .append(" value=\"<s:property value=\"%{getText('common.button.close')}\"/>\"")
                  .append(" onclick=\"window.open('about:blank','_self').close(); return false;\"")
                  .append(" class=\"closebutton\"/>").append("\n");
                continue;
            }

            sb.append("<% // ").append(item.getLabel()).append(" %>\n");
            sb.append("<input type=\"button\"")
              .append(" value=\"<s:property value=\"%{getText('").append(frameData.getFrameId()).append(".").append(Utils.getLabelId_cal(frameData, item)).append("')}\"/>\"")
              .append(" name=\"").append(item.getId().toLowerCase()).append("\"")
              .append(" id=\"").append(item.getId().toLowerCase()).append("\"")
              .append(" class=\"").append(item.getId()).append("\"/>").append("\n");
        }

        return sb.toString();
    }
}
