package alpha114.tool.cichlid.autogenerationtool.src.builder;

import alpha114.tool.cichlid.autogenerationtool.Builder;
import alpha114.tool.cichlid.autogenerationtool.Utils;
import alpha114.tool.cichlid.autogenerationtool.data.FrameData;
import alpha114.tool.cichlid.autogenerationtool.data.FrameItemData;

/**
 * @author kurisakisatoshi
 */
public class SearchConditionJspBuilder implements Builder {


    private final FrameData frameData;
    private final FrameItemData item;

    public SearchConditionJspBuilder(FrameData frameData, FrameItemData item) {
        this.frameData = frameData;
        this.item = item;
    }

    /* (非 Javadoc)
     * @see alpha114_117.tool.cichlid.autogenerationtool2.Builder#build()
     */
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
        sb.append("<% // ").append(frameData.getFrameName()).append(" %>").append("\n");
        sb.append("").append("\n");
        sb.append("").append("\n");
        sb.append("<% // ").append(item.getLabel()).append(" %>\n");

        if (item.isPulldown()) {

            sb.append("<s:if test=\"%{searchCondition.").append(item.getId().toLowerCase()).append(" == null}\">").append("\n");

            sb.append("\t");
            sb.append("<s:select id=\"").append(item.getId().toLowerCase())
              .append("\" name=\"").append(item.getId().toLowerCase())
              .append("\" cssClass=\"").append(item.getId().toLowerCase())
              .append("\" list=\"%{viewModel.searchConditionViewModel.getSearchConditionItemInfo('").append(item.getId().toLowerCase()).append("').pulldownMenu}")
              .append("\" value=\"%{viewModel.searchConditionViewModel.getSearchConditionItemInfo('").append(item.getId().toLowerCase()).append("').value}\" />").append("\n");

            sb.append("</s:if>").append("\n");
            sb.append("<s:else>").append("\n");

            sb.append("\t");
            sb.append("<s:select id=\"").append(item.getId().toLowerCase())
              .append("\" name=\"").append(item.getId().toLowerCase())
              .append("\" cssClass=\"").append(item.getId().toLowerCase())
              .append("\" list=\"%{viewModel.searchConditionViewModel.getSearchConditionItemInfo('").append(item.getId().toLowerCase()).append("').pulldownMenu}")
              .append("\" value=\"%{searchCondition.").append(item.getId().toLowerCase()).append("}\" />").append("\n");

            sb.append("</s:else>").append("\n");

        } else if (item.isText()) {

            sb.append("<s:if test=\"%{searchCondition.").append(item.getId().toLowerCase()).append(" == null}\">").append("\n");

            sb.append("\t");
            sb.append("<input type=\"text\" value=\"")
              .append("<s:property value=\"%{viewModel.searchConditionViewModel.getSearchConditionItemInfo('").append(item.getId().toLowerCase()).append("').value}\"/>")
              .append("\" id=\"").append(item.getId().toLowerCase())
              .append("\" name=\"").append(item.getId().toLowerCase())
              .append("\" class=\"").append(item.getId().toLowerCase())
              .append("\" />").append("\n");

            sb.append("</s:if>").append("\n");
            sb.append("<s:else>").append("\n");

            sb.append("\t");
            sb.append("<input type=\"text\" value=\"")
              .append("<s:property value=\"%{searchCondition.").append(item.getId().toLowerCase()).append("}\"/>")
              .append("\" id=\"").append(item.getId().toLowerCase())
              .append("\" name=\"").append(item.getId().toLowerCase())
              .append("\" class=\"").append(item.getId().toLowerCase())
              .append("\" />").append("\n");

            sb.append("</s:else>").append("\n");

        } else if (item.isRadioButton()) {

            sb.append("<s:if test=\"%{searchCondition.").append(item.getId().toLowerCase()).append(" == null}\">").append("\n");

            sb.append("\t");
            sb.append("<span class=\"").append(item.getId().toLowerCase()).append("\">");
            sb.append("<input type=\"radio\" ")
              .append("<s:property value=\"%{viewModel.searchConditionViewModel.getSearchConditionItemInfo('").append(item.getId().toLowerCase()).append("').value}\"/>")
              .append(" id=\"").append(item.getId().toLowerCase())
              .append("\" name=\"").append(getRadioButtonName(item)).append("_radio")
              .append("\" value=\"").append(item.getId().toLowerCase())
              .append("\" class=\"").append(item.getId().toLowerCase()).append("\" />");
            sb.append("<s:text name=\"").append(frameData.getFrameId()).append(".").append(getLabelId(item)).append("\" />")
              .append("</span>").append("\n");

            sb.append("</s:if>").append("\n");
            sb.append("<s:else>").append("\n");

            sb.append("\t");
            sb.append("<span class=\"").append(item.getId().toLowerCase()).append("\">");
            sb.append("<input type=\"radio\" ")
              .append("<s:property value=\"%{searchCondition.").append(item.getId().toLowerCase()).append("}\"/>")
              .append(" id=\"").append(item.getId().toLowerCase())
              .append("\" name=\"").append(getRadioButtonName(item)).append("_radio")
              .append("\" value=\"").append(item.getId().toLowerCase())
              .append("\" class=\"").append(item.getId().toLowerCase()).append("\" />");
            sb.append("<s:text name=\"").append(frameData.getFrameId()).append(".").append(getLabelId(item)).append("\" />")
              .append("</span>").append("\n");

            sb.append("</s:else>").append("\n");
        }

        return sb.toString();
    }


    private String getRadioButtonName(FrameItemData item) {
        // 小文字に変換
        return Utils.getRadioButtonName(frameData, item).toLowerCase();
    }
//
//    private String calendar(FrameItemData fromItem, FrameItemData toItem) {
//
//        String fromYYYY = fromItem.getId()+"_YYYY";
//        String fromMM = fromItem.getId()+"_MM";
//        String fromDD = fromItem.getId()+"_DD";
//        String fromHH = fromItem.getId()+"_HH";
//        String fromMi = fromItem.getId()+"_MI";
//
//        String toYYYY = toItem.getId()+"_YYYY";
//        String toMM = toItem.getId()+"_MM";
//        String toDD = toItem.getId()+"_DD";
//        String toHH = toItem.getId()+"_HH";
//        String toMi = toItem.getId()+"_MI";
//
//        StringBuilder sb = new StringBuilder();
//
//        sb.append("\t<span>").append("<s:text name=\"").append(frameData.getFrameId()).append(".").append(getLabelId(fromItem)).append("\" />").append("</span>");
//        sb.append("<span style=\"margin:0;\">");
//        sb.append("<input type=\"text\" value=\"\" size=\"4\" id=\"").append(fromYYYY).append("\" name=\"").append(fromYYYY).append("\" />");
//        sb.append("/");
//        sb.append("<input type=\"text\" value=\"\" size=\"2\" id=\"").append(fromMM).append("\" name=\"").append(fromMM).append("\" />");
//        sb.append("/");
//        sb.append("<input type=\"text\" value=\"\" size=\"2\" id=\"").append(fromDD).append("\" name=\"").append(fromDD).append("\" />");
//        sb.append("&nbsp;");
//        sb.append("<input type=\"text\" value=\"\" size=\"2\" id=\"").append(fromHH).append("\" name=\"").append(fromHH).append("\" />");
//        sb.append(":");
//        sb.append("<input type=\"text\" value=\"\" size=\"2\" id=\"").append(fromMi).append("\" name=\"").append(fromMi).append("\" />");
//        sb.append("<button type=\"button\" id=\"").append(fromItem.getId()).append("_fromCal\" name=\"").append(fromItem.getId()).append("_fromCal\" ");
//        sb.append("onclick=\"calendar('").append(fromYYYY).append("', ")
//                                         .append("'").append(fromMM).append("', ")
//                                         .append("'").append(fromDD).append("', ")
//                                         .append("'").append(fromHH).append("', ")
//                                         .append("'").append(fromMi).append("');return false;\"").append(">");
//        sb.append("<img src=\"<%=request.getContextPath() %>/img/cal.png\">");
//        sb.append("</button>");
//        sb.append("&nbsp;～&nbsp;");
//        sb.append("<input type=\"text\" value=\"\" size=\"4\" id=\"").append(toYYYY).append("\" name=\"").append(toYYYY).append("\" />");
//        sb.append("/");
//        sb.append("<input type=\"text\" value=\"\" size=\"2\" id=\"").append(toMM).append("\" name=\"").append(toMM).append("\" />");
//        sb.append("/");
//        sb.append("<input type=\"text\" value=\"\" size=\"2\" id=\"").append(toDD).append("\" name=\"").append(toDD).append("\" />");
//        sb.append("&nbsp;");
//        sb.append("<input type=\"text\" value=\"\" size=\"2\" id=\"").append(toHH).append("\" name=\"").append(toHH).append("\" />");
//        sb.append(":");
//        sb.append("<input type=\"text\" value=\"\" size=\"2\" id=\"").append(toMi).append("\" name=\"").append(toMi).append("\" />");
//        sb.append("<button type=\"button\" id=\"").append(toItem.getId()).append("_toCal\" name=\"").append(toItem.getId()).append("_toCal\" ");
//        sb.append("onclick=\"calendar('").append(toYYYY).append("', ")
//                                         .append("'").append(toMM).append("', ")
//                                         .append("'").append(toDD).append("', ")
//                                         .append("'").append(toHH).append("', ")
//                                         .append("'").append(toMi).append("');return false;\"").append(">");
//        sb.append("<img src=\"<%=request.getContextPath() %>/img/cal.png\">");
//        sb.append("</button>");
//        sb.append("</span>").append("\n");
//
//        return sb.toString();
//    }
//
//    private FrameItemData getTo(FrameItemData from) {
//        return Utils.getTo(frameData, from);
//    }

    private String getLabelId(FrameItemData item) {
        return Utils.getLabelId_cal(frameData, item);
    }
}
