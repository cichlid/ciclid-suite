package alpha114.tool.cichlid.autogenerationtool.src.builder;

import alpha114.tool.cichlid.autogenerationtool.Builder;
import alpha114.tool.cichlid.autogenerationtool.Utils;
import alpha114.tool.cichlid.autogenerationtool.data.FrameData;
import alpha114.tool.cichlid.autogenerationtool.data.FrameItemData;

/**
 * @author kurisakisatoshi
 */
public class SettingItemJspBuilder implements Builder {

    private final FrameData frameData;
    private final FrameItemData item;
    private final boolean detail;

    public SettingItemJspBuilder(FrameData frameData, FrameItemData item, boolean detail) {
        this.frameData = frameData;
        this.item = item;
        this.detail = detail;
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
        sb.append("<% // ").append(frameData.getFrameName()).append(" %>").append("\n");
        sb.append("").append("\n");
        sb.append("").append("\n");
        sb.append("<% // ").append(item.getLabel()).append(" %>\n");

        if (item.isCalendar()) {
            sb.append(calendar(item));
            return sb.toString();
        }

        if (item.isIpAddress()) {
            sb.append("<% // IPアドレス形式の入力欄は自動生成対象外 %>").append("\n");
            return sb.toString();
        }

        if (item.isLabel()) {
            sb.append("<span class=\"").append(item.getId().toLowerCase()).append("\">")
                .append("<s:text name=\"").append(frameData.getFrameId()).append(".").append(getLabelId(item)).append("\" />")
                .append("</span>").append("\n");

        } else if (item.isTextLink_self()) {
            // リンクテキスト

            sb.append("<span class=\"").append(item.getId().toLowerCase()).append("\">")
                .append("<s:a href=\"javascript:void(0);\" onclick=\"").append(item.getId()).append("();return false;\">")
                    .append("<s:text name=\"")
                    .append(frameData.getFrameId()).append(".").append(item.getId())
                    .append("\" />")
                .append("</s:a>")
            .append("</span>").append("\n");

        } else if (item.isTextLink_blank()) {
            sb.append("<span class=\"").append(item.getId().toLowerCase()).append("\">")
                .append("<s:a href=\"javascript:void(0);\" onclick=\"").append(item.getId()).append("());return false;\">")
                    .append("<s:text name=\"")
                    .append(frameData.getFrameId()).append(".").append(item.getId())
                    .append("\" />")
                .append("</s:a>")
            .append("</span>").append("\n");

        }

        if (item.isText()) {
//            sb.append("<span class=\"").append(item.getId().toLowerCase()).append("\">")
//              .append("<s:text name=\"").append(frameData.getFrameId()).append(".").append(getLabelId(item)).append("\" />")
//              .append("</span>");
            sb.append("<input type=\"text\" value=\"").append("<s:property value=\"viewModel.settingDataViewModel.").append(item.getId().toLowerCase()).append("\"/>")
              .append("\" id=\"").append(item.getId().toLowerCase()).append("\" name=\"").append(item.getId().toLowerCase())
              .append("\" class=\"").append(item.getId().toLowerCase()).append(detail ? " readonly" : "")
              .append("\" ").append(detail ? " readonly" : "").append(" />").append("\n");

        } else if (item.isPassword()) {
//            sb.append("<span class=\"").append(item.getId().toLowerCase()).append("\">")
//              .append("<s:text name=\"").append(frameData.getFrameId()).append(".").append(getLabelId(item)).append("\" />")
//              .append("</span>");
            sb.append("<input type=\"password\" value=\"").append("<s:property value=\"viewModel.settingDataViewModel.").append(item.getId().toLowerCase()).append("\"/>")
              .append("\" id=\"").append(item.getId().toLowerCase()).append("\" name=\"").append(item.getId().toLowerCase())
              .append("\" class=\"").append(item.getId().toLowerCase()).append(detail ? " readonly" : "")
              .append("\" ").append(detail ? " readonly" : "").append(" />").append("\n");

        } else if (item.isTextArea()) {
//            sb.append("<span class=\"").append(item.getId().toLowerCase()).append("\">")
//              .append("<s:text name=\"").append(frameData.getFrameId()).append(".").append(getLabelId(item)).append("\" />")
//              .append("</span>");
            sb.append("<textarea id=\"").append(item.getId().toLowerCase())
              .append("\" name=\"").append(item.getId().toLowerCase())
              .append("\" class=\"").append(item.getId().toLowerCase()).append(detail ? " readonly" : "")
              .append("\" wrap=\"").append("off")
              .append("\" ").append(detail ? " readonly" : "").append(">")
                  .append("<s:property value=\"viewModel.settingDataViewModel.").append(item.getId().toLowerCase()).append("\"/>")
              .append("</textarea>").append("\n");

        } else if (item.isPulldown()) {
//            sb.append("<span class=\"").append(item.getId().toLowerCase()).append("\">")
//              .append("<s:text name=\"").append(frameData.getFrameId()).append(".").append(getLabelId(item)).append("\" />")
//              .append("</span>");
            sb.append("<s:select id=\"").append(item.getId().toLowerCase())
              .append("\" name=\"").append(item.getId().toLowerCase())
              .append("\" cssClass=\"").append(item.getId().toLowerCase()).append(detail ? " readonly" : "");
            if (detail) {
                sb.append("\" disabled=\"disabled");
            }
            sb.append("\" list=\"%{viewModel.settingDataViewModel.").append(item.getId().toLowerCase()).append("}");
            sb.append("\" value=\"%{viewModel.settingDataViewModel.").append(item.getId().toLowerCase()).append("Value}\" />").append("\n");

        } else if (item.isRadioButton()) {
            sb.append("<span class=\"").append(item.getId().toLowerCase()).append("\">")
              .append("<input type=\"radio\" ").append("<s:property value=\"viewModel.settingDataViewModel.").append(item.getId().toLowerCase()).append("\"/>")
              .append(" id=\"").append(item.getId().toLowerCase())
              .append("\" name=\"").append(getRadioButtonName(item)).append("_radio")
              .append("\" value=\"").append(item.getId().toLowerCase())
              .append("\" class=\"").append(item.getId().toLowerCase()).append("\" />");
            sb.append("<s:text name=\"").append(frameData.getFrameId()).append(".").append(getLabelId(item)).append("\" />")
              .append("</span>").append("\n");

        } else if (item.isCheckbox()) {
            sb.append("<span class=\"").append(item.getId().toLowerCase()).append("\">")
              .append("<input type=\"checkbox\" ").append("<s:property value=\"viewModel.settingDataViewModel.").append(item.getId().toLowerCase()).append("\"/>")
              .append(" id=\"").append(item.getId().toLowerCase()).append("\" name=\"").append(item.getId()).append("\"");
            if (detail) {
                sb.append(" disabled");
            }
            sb.append("/>");
            sb.append("<s:text name=\"").append(frameData.getFrameId()).append(".").append(getLabelId(item)).append("\" />")
              .append("</span>").append("\n");

        } else if (item.isTable()) {
            sb.append(table());

        } else if (item.isButton()) {
            sb.append("<input type=\"button\"")
              .append(" value=\"<s:property value=\"%{getText('").append(frameData.getFrameId()).append(".").append(getLabelId(item)).append("')}\"/>\"")
              .append(" name=\"").append(item.getId().toLowerCase()).append("\"")
              .append(" id=\"").append(item.getId().toLowerCase()).append("\"");

            String label = item.getLabel();
            if (label.contains("次へ")) {
                sb.append(" class=\"next_sequence_button ").append(item.getId().toLowerCase())
                    .append("\"/>").append("\n");

            } else if (label.contains("戻る")) {
                sb.append(" class=\"back_button ").append(item.getId().toLowerCase())
                    .append("\"/>").append("\n");

            } else if (label.startsWith("検索")) {
                sb.append(" class=\"db_access_button ").append(item.getId().toLowerCase())
                    .append("\"/>").append("\n");

            } else if (label.contains("コマンド実行")) {
                sb.append(" class=\"device_access_button ").append(item.getId().toLowerCase())
                    .append("\"/>").append("\n");

            } else if (label.contains("ダウンロード")) {
                sb.append(" class=\"download_button ").append(item.getId().toLowerCase())
                    .append("\"/>").append("\n");

            } else if (label.contains("アップロード")) {
                sb.append(" class=\"upload_button ").append(item.getId().toLowerCase())
                    .append("\"/>").append("\n");



            } else {
                sb.append(" class=\"").append(item.getId().toLowerCase())
                    .append("\"/>").append("\n");
            }
        }

        return sb.toString();
    }

    private String calendar(FrameItemData item) {

        String yyy = item.getId().toLowerCase() + "_YYYY";
        String mm = item.getId().toLowerCase() + "_MM";
        String dd = item.getId().toLowerCase() + "_DD";
        String hh = item.getId().toLowerCase() + "_HH";
        String mi = item.getId().toLowerCase() + "_MI";

        StringBuilder sb = new StringBuilder();

        sb.append("<span class=\"").append(item.getId().toLowerCase()).append("\"><s:text name=\"").append(frameData.getFrameId()).append(".").append(getLabelId(item)).append("\" />").append("</span>");
        sb.append("<span style=\"margin:0;\">");
        sb.append("<input type=\"text\" value=\"").append("<s:property value=\"viewModel.settingDataViewModel.").append(yyy).append("\"/>")
          .append("\" size=\"4\" id=\"").append(yyy).append("\" name=\"").append(yyy)
          .append("\" class=\"").append(item.getId().toLowerCase()).append(detail ? " readonly" : "")
          .append("\" ").append(detail ? " readonly" : "")
          .append("/>");
        sb.append("/");
        sb.append("<input type=\"text\" value=\"").append("<s:property value=\"viewModel.settingDataViewModel.").append(mm).append("\"/>")
          .append("\" size=\"2\" id=\"").append(mm).append("\" name=\"").append(mm)
          .append("\" class=\"").append(item.getId().toLowerCase()).append(detail ? " readonly" : "")
          .append("\" ").append(detail ? " readonly" : "")
          .append("/>");
        sb.append("/");
        sb.append("<input type=\"text\" value=\"").append("<s:property value=\"viewModel.settingDataViewModel.").append(dd).append("\"/>")
          .append("\" size=\"2\" id=\"").append(dd).append("\" name=\"").append(dd)
          .append("\" class=\"").append(item.getId().toLowerCase()).append(detail ? " readonly" : "")
          .append("\" ").append(detail ? " readonly" : "")
          .append("/>");
        sb.append("&nbsp;");
        sb.append("<input type=\"text\" value=\"").append("<s:property value=\"viewModel.settingDataViewModel.").append(hh).append("\"/>")
          .append("\" size=\"2\" id=\"").append(hh).append("\" name=\"").append(hh)
          .append("\" class=\"").append(item.getId().toLowerCase()).append(detail ? " readonly" : "")
          .append("\" ").append(detail ? " readonly" : "")
          .append("/>");
        sb.append(":");
        sb.append("<input type=\"text\" value=\"").append("<s:property value=\"viewModel.settingDataViewModel.").append(mi).append("\"/>")
          .append("\" size=\"2\" id=\"").append(mi).append("\" name=\"").append(mi)
          .append("\" class=\"").append(item.getId().toLowerCase()).append(detail ? " readonly" : "")
          .append("\" ").append(detail ? " readonly" : "")
          .append("/>");
        if (!detail) {
            sb.append("<button type=\"button\" id=\"").append(item.getId()).append("_cal\" name=\"").append(item.getId()).append("_cal\" ");
            sb.append("onclick=\"calendar('").append(yyy).append("', ")
                                             .append("'").append(mm).append("', ")
                                             .append("'").append(dd).append("', ")
                                             .append("'").append(hh).append("', ")
                                             .append("'").append(mi).append("');return false;\"").append(">");
            sb.append("<img src=\"<%=request.getContextPath() %>/img/cal.png\">");
            sb.append("</button>");
        }
        sb.append("</span>").append("\n");

        return sb.toString();
    }

    private String table() {

        StringBuilder sb = new StringBuilder();

        sb.append("<span class=\"").append(item.getId().toLowerCase()).append("\">").append("\n");
        sb.append("    <table id=\"" + item.getId().toLowerCase() + "Table\" class=\"tblcommon headTable\" border=\"1\" cellspacing=\"0\" cellpadding=\"3\">").append("\n");
        sb.append("        <tbody>").append("\n");

        sb.append("            <tr class=\"list_table_header\">").append("\n");

        if (item.getTableColumns().isEmpty()) {
            System.out.println(frameData.getFrameName() + ", " + item.getLabel());
            return "";
        }
        System.out.println(frameData.getFrameName() + ", " + item.getLabel());
        FrameItemData clm1 = item.getTableColumns().get(0);
        FrameItemData clm2 = item.getTableColumns().get(1);

        sb.append("                <td class=\"select nowrap\"><s:property value=\"%{getText('").append(getTableHeaderItemId(clm1)).append("')}\"/><br><input type=\"checkbox\" id=\"").append(clm1.getId().toLowerCase()).append("\" name=\"").append(clm1.getId().toLowerCase()).append("\"").append(detail ? " disabled" : "").append("/></td>").append("\n");
        sb.append("                <td class=\"settingtableheader nowrap\"><s:property value=\"%{getText('").append(getTableHeaderItemId(clm2)).append("')}\"/></td>").append("\n");

        sb.append("            </tr>").append("\n");

        sb.append("<s:iterator var=\"").append(item.getId().toLowerCase()).append("\" value=\"viewModel.settingDataViewModel.").append(item.getId().toLowerCase()).append("\" status=\"stat\">").append("\n");

        sb.append("            <tr class=\"list_table_cell\">").append("\n");
        sb.append("                <td class=\"select nowrap\"><input type=\"checkbox\" id=\"<s:property value=\"sysid\" />\" name=\"").append(item.getId().toLowerCase()).append("List[<s:property value=\"#stat.index\"/>].sysid\" value=\"<s:property value=\"sysid\" />\" <s:property value=\"checked\" /> ").append(detail ? " disabled" : "").append("/></td>").append("\n");
        sb.append("                <td class=\"settingtableheader nowrap\" title=\"<s:property value=\"displayStr\" />\" ><span class=\"nowrap\"><s:property value=\"displayStr\" /></span></td>").append("\n");
        sb.append("            </tr>").append("\n");

        sb.append("</s:iterator>").append("\n");

        sb.append("        </tbody>").append("\n");
        sb.append("    </table>").append("\n");
        sb.append("</span>").append("\n");
        sb.append("").append("\n");

        return sb.toString();
    }

    private String getTableHeaderItemId(FrameItemData item) {

        if (item.getLabel().equals("選択")) {
            return "table.checkbox.select";
        }

        return frameData.getFrameId() + "." + item.getId();
    }

    private String getLabelId(FrameItemData item) {
        return Utils.getLabelId_cal(frameData, item);
    }

    private String getRadioButtonName(FrameItemData item) {
        return Utils.getRadioButtonName(frameData, item);
    }
}
