package alpha114.tool.cichlid.autogenerationtool.src.builder;

import alpha114.tool.cichlid.autogenerationtool.Builder;
import alpha114.tool.cichlid.autogenerationtool.Constants;
import alpha114.tool.cichlid.autogenerationtool.data.FrameData;
import alpha114.tool.cichlid.autogenerationtool.data.FrameItemData;
import alpha114.tool.cichlid.autogenerationtool.data.TableListItemData;

/**
 * @author kurisakisatoshi
 */
public class SettingViewModelBuilder implements Builder {

    private final FrameData frameData;

    public SettingViewModelBuilder(FrameData frameData) {
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

        sb.append("package ").append(Constants.VIEW_MODE_PACKAGE_SETTING).append(";\n");

        sb.append("").append("\n");
        sb.append("import ").append(Constants.VIEW_MODE_PACKAGE_SETTING).append(".SettingViewModel;").append("\n");
        sb.append("").append("\n");

        sb.append("/**").append("\n");
        sb.append(" * ").append(frameData.getFrameName()).append("のViewModelインターフェースです。").append("\n");
        sb.append(" * ").append("\n");
        sb.append(" * @author  kurisakisatoshi").append("\n");
        sb.append(" * @version $Revision").append(": $").append("\n");
        sb.append(" */").append("\n");

        sb.append("public interface ").append(frameData.getFrameId()).append("_SettingViewModel extends SettingViewModel {").append("\n");

        sb.append("    ").append("\n");
        sb.append("    ").append("// ★★自動生成のため本ファイルを直接修正しないこと★★").append("\n");
        sb.append("    ").append("\n");

//        sb.append("    /**").append("\n");
//        sb.append("     * 更新Noを返します。\n");
//        sb.append("     * ").append("\n");
//        sb.append("     * @return 更新No\n");
//        sb.append("     */").append("\n");
//        sb.append("    public String getUpdateNo();").append("\n");
//        sb.append("    ").append("\n");

        for (FrameItemData item : frameData.getItems()) {

            if (!item.isInput() && !item.isTable() && !item.isText() && !item.isTextArea()) {
                continue;
            }

            if (item.isTable()) {

                if (frameData.getFrameId().equals("LineInfo_006")
                        ||frameData.getFrameId().equals("LineInfo_004")) {

                    for (TableListItemData tableItemData : item.getTableColumns()) {

                        if (tableItemData.isHeader()) {
                            continue;
                        }

                        if (tableItemData.isLabel()) {
                            continue;
                        }

                        if (tableItemData.isInput()) {
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

                continue;
            }

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

            } else {
                if (item.isPulldown()) {
                    sb.append("    /**").append("\n");
                    sb.append("     * ").append(item.getLabel()).append("のプルダウンメニューを返します。</br>\n");
                    sb.append("     * ").append("プルダウンのメニュー順にソートしておくこと").append("\n");
                    sb.append("     * ").append("\n");
                    sb.append("     * @return ").append(item.getLabel()).append("のプルダウンメニュー\n");
                    sb.append("     */").append("\n");
                    sb.append("    public java.util.SortedMap<? extends Object, ? extends Object> get").append(item.getId()).append("();").append("\n");
                    sb.append("    ").append("\n");
                    sb.append("    /**").append("\n");
                    sb.append("     * ").append(item.getLabel()).append("の初期選択値を返します。</br>\n");
                    sb.append("     * ").append("\n");
                    sb.append("     * @return ").append(item.getLabel()).append("の初期選択値\n");
                    sb.append("     */").append("\n");
                    sb.append("    public String get").append(item.getId()).append("Value();").append("\n");
                    sb.append("    ").append("\n");

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
                    sb.append("     * ").append("\n");
                    sb.append("     * @return ").append(item.getLabel()).append("の画面表示内容\n");
                    sb.append("     */").append("\n");
                    sb.append("    public String get").append(item.getId()).append("();").append("\n");
                    sb.append("    ").append("\n");
                }
            }
        }

        sb.append("}").append("\n");

        return sb.toString();
    }
}

