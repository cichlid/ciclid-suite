package alpha114.tool.cichlid.autogenerationtool.src.builder;

import alpha114.tool.cichlid.autogenerationtool.Builder;
import alpha114.tool.cichlid.autogenerationtool.Constants;
import alpha114.tool.cichlid.autogenerationtool.data.FrameData;
import alpha114.tool.cichlid.autogenerationtool.data.FrameItemData;
import alpha114.tool.cichlid.autogenerationtool.data.TableListItemData;

/**
 * @author kurisakisatoshi
 */
public class DetailViewModelBuilder implements Builder {

    private final FrameData frameData;

    public DetailViewModelBuilder(FrameData frameData) {
        this.frameData = frameData;
    }

    @Override
    public String build() {

        StringBuilder sb = new StringBuilder();

        sb.append("/* ").append("\n");
        sb.append(" * ").append(Constants.COPYRIGHT).append("\n");
        sb.append(" * ").append("\n");
        sb.append(" * $Id").append(": $").append("\n");
        sb.append(" * ").append("\n");
        sb.append(" * 備考    ：").append("\n");
        sb.append(" * 更新履歴：").append("\n");
        sb.append(" *   日付       更新者       内容").append("\n");
        sb.append(" *   ").append(Constants.DATE_CREATED).append("   kurisakisatoshi   新規作成").append("\n");
        sb.append(" * ").append("\n");
        sb.append(" */").append("\n");

        sb.append("package ").append(Constants.VIEW_MODE_PACKAGE_DETAIL).append(";\n");

        sb.append("").append("\n");
        sb.append("import ").append(Constants.VIEW_MODE_PACKAGE_DETAIL).append(".DetailViewModel;").append("\n");
        sb.append("").append("\n");

        sb.append("/**").append("\n");
        sb.append(" * ").append(frameData.getFrameName()).append("のViewModelインターフェースです。").append("\n");
        sb.append(" * ").append("\n");
        sb.append(" * @author  kurisakisatoshi").append("\n");
        sb.append(" * @version $Revision").append(": $").append("\n");
        sb.append(" */").append("\n");

        sb.append("public interface ").append(frameData.getFrameId()).append("_DetailViewModel extends DetailViewModel {").append("\n");

        sb.append("    ").append("\n");
        sb.append("    ").append("// ★★自動生成のため本ファイルを直接修正しないこと★★").append("\n");
        sb.append("    ").append("").append("\n");

        for (FrameItemData item : frameData.getItems()) {

            if (item.isText()
                    || item.isTextArea()
                    || item.isPassword()
                    || item.isPulldown()
                    || item.isRadioButton()
                    || item.isCheckbox()) {

                if (item.isCalendar()) {

                    for (String s : Constants.date) {
                        sb.append("    /**").append("\n");
                        sb.append("     * ").append(item.getLabel()).append("の画面表示内容(").append(s).append(")を返します。\n");
                        sb.append("     * ").append("\n");
                        sb.append("     * @return ").append(item.getLabel()).append("の画面表示内容(").append(s).append(")\n");
                        sb.append("     */").append("\n");
                        sb.append("    public String get").append(item.getId()).append("_").append(s).append("();").append("\n");
                        sb.append("    ").append("\n");
                    }

                } else if (item.isIpAddress()) {
                    sb.append("    /**").append("\n");
                    sb.append("     * ").append(item.getLabel()).append("の画面表示内容を返します。\n");
                    sb.append("     * ").append("\n");
                    sb.append("     * @return ").append(item.getLabel()).append("の画面表示内容\n");
                    sb.append("     */").append("\n");
                    sb.append("    public java.util.List<String> get").append(item.getId()).append("List();").append("\n");
                    sb.append("    ").append("\n");

                } else {
                    sb.append("    /**").append("\n");
                    sb.append("     * ").append(item.getLabel()).append("の画面表示内容を返します。\n");
                    if (item.isRadioButton() || item.isCheckbox()) {
                        sb.append("     * ").append("<br/>チェック状態は'checked'という文字列\n");
                    }
                    sb.append("     * ").append("\n");
                    sb.append("     * @return ").append(item.getLabel()).append("の画面表示内容\n");
                    sb.append("     */").append("\n");
                    sb.append("    public String get").append(item.getId()).append("();").append("\n");
                    sb.append("    ").append("\n");
                }

            } else if (item.isTable()) {

                if (frameData.getFrameId().equals("LineInfo_005")
                        || frameData.getFrameId().equals("LineInfo_007")) {

                    for (TableListItemData tableItemData : item.getTableColumns()) {

                        if (tableItemData.isHeader()) {
                            continue;
                        }

                        if (tableItemData.isLabel()) {
                            continue;
                        }

                        sb.append("    /**").append("\n");
                        sb.append("     * ").append(tableItemData.getLabel()).append("の画面表示内容を返します。\n");
                        sb.append("     * ").append("\n");
                        sb.append("     * @return ").append(tableItemData.getLabel()).append("の画面表示内容\n");
                        sb.append("     */").append("\n");
                        sb.append("    public String get").append(tableItemData.getId()).append("();").append("\n");
                        sb.append("    ").append("\n");
                    }
                }
            }
        }

        sb.append("}").append("\n");

        return sb.toString();
    }
}

