package alpha114.tool.cichlid.autogenerationtool.src.builder;

import alpha114.tool.cichlid.autogenerationtool.Builder;
import alpha114.tool.cichlid.autogenerationtool.Constants;
import alpha114.tool.cichlid.autogenerationtool.data.FrameData;
import alpha114.tool.cichlid.autogenerationtool.data.FrameItemData;

/**
 * @author kurisakisatoshi
 */
public class SettingDataViewModelBuilder implements Builder {

    private final FrameData frameData;

    public SettingDataViewModelBuilder(FrameData frameData) {
        this.frameData = frameData;
    }

    @Override
    public String build() {

        StringBuilder sb = new StringBuilder();
        // ファイル看板
        sb.append("/* ").append("\n");
        sb.append(" * ").append(Constants.COPYRIGHT).append("\n");
        sb.append(" * ").append("\n");
        sb.append(" * $Id").append(": $").append("\n");
        sb.append(" * ").append("\n");
        sb.append(" * 備考    ：画面表示項目から自動生成").append("\n");
        sb.append(" * 更新履歴：").append("\n");
        sb.append(" *   日付         更新者            内容").append("\n");
        sb.append(" *   ").append(Constants.DATE_CREATED).append("   kurisakisatoshi   新規作成").append("\n");
        sb.append(" * ").append("\n");
        sb.append(" */").append("\n");

        // package
        sb.append("package ").append(Constants.PRJ_VIEW_MODEL_PACKAGE_SETTING).append(";").append("\n");
        sb.append("\n");

        // import
        sb.append("import ").append(Constants.VIEW_MODEL_PACKAGE_SETTING).append(".SettingDataViewModel;").append("\n");

        // class看板
        sb.append("/**").append("\n");
        sb.append(" * クラス名：").append(frameData.getFrameId()).append("_SettingDataViewModel").append("\n");
        sb.append(" * 機能概要：").append(frameData.getFrameName()).append("のViewModelインターフェースです。").append("\n");
        sb.append(" * 備考：").append("\n");
        sb.append(" * ").append("\n");
        sb.append(" */").append("\n");

        // class
        sb.append("public interface ").append(frameData.getFrameId()).append("_SettingDataViewModel extends SettingDataViewModel {").append("\n");

        sb.append("    ").append("\n");
        sb.append("    // ★★自動生成のため本ファイルを直接修正しないこと★★").append("\n");
        sb.append("    ").append("\n");

//        sb.append("    /**").append("\n");
//        sb.append("     * 更新Noを返します。").append("\n");
//        sb.append("     * ").append("\n");
//        sb.append("     * @return 更新No").append("\n");
//        sb.append("     */").append("\n");
//        sb.append("    String getUpdateNo();").append("\n");
//        sb.append("    ").append("\n");

        boolean hasInput = false;

        for (FrameItemData item : frameData.getItems()) {

//            if (!item.isInput()) {
//                continue;
//            }

            if (item.isLabel()
                    || item.isButton()
                    || item.isTable()) {
                continue;
            }

            if (!item.getId().startsWith(FrameItemData.ItemType.SETTING_DATA.type)) {
                continue;
            }

            if (item.isCalendar()) {

                for (String s : Constants.date) {
                    sb.append("    /**").append("\n");
                    sb.append("     * ").append(item.getLabel()).append("の画面表示内容(").append(s).append(")を返します。").append("\n");
                    sb.append("     * ").append("\n");
                    sb.append("     * @return ").append(item.getLabel()).append("の画面表示内容(").append(s).append(")").append("\n");
                    sb.append("     */").append("\n");
                    sb.append("    String get").append(item.getId()).append("_").append(s).append("();").append("\n");
                    sb.append("    ").append("\n");
                    sb.append("    /**").append("\n");
                    sb.append("     * ").append(item.getLabel()).append("の画面表示内容(").append(s).append(")を設定します。").append("\n");
                    sb.append("     * ").append("\n");
                    sb.append("     * @param ").append(item.getId().toLowerCase()).append(" ").append(item.getLabel()).append("の画面表示内容(").append(s).append(")").append("\n");
                    sb.append("     */").append("\n");
                    sb.append("    void set").append(item.getId()).append("_").append(s).append("(String ").append(item.getId().toLowerCase()).append("_").append(s).append(");").append("\n");
                    sb.append("    ").append("\n");
                }

            } else {
                if (item.isPulldown()) {
                    sb.append("    /**").append("\n");
                    sb.append("     * ").append(item.getLabel()).append("のプルダウンメニューを返します。<br>").append("\n");
                    sb.append("     * プルダウンのメニュー順にソートしておくこと").append("\n");
                    sb.append("     * ").append("\n");
                    sb.append("     * @return ").append(item.getLabel()).append("のプルダウンメニュー").append("\n");
                    sb.append("     */").append("\n");
                    sb.append("    java.util.SortedMap<? extends Object, ? extends Object> get").append(item.getId()).append("();").append("\n");
                    sb.append("    ").append("\n");
                    sb.append("    /**").append("\n");
                    sb.append("     * ").append(item.getLabel()).append("のプルダウンメニューを設定します。<br>").append("\n");
                    sb.append("     * プルダウンのメニュー順にソートしておくこと").append("\n");
                    sb.append("     * ").append("\n");
                    sb.append("     * @param ").append(item.getId().toLowerCase()).append(" ").append(item.getLabel()).append("のプルダウンメニュー").append("\n");
                    sb.append("     */").append("\n");
                    sb.append("    void set").append(item.getId()).append("(java.util.SortedMap<? extends Object, ? extends Object> ").append(item.getId().toLowerCase()).append(");").append("\n");
                    sb.append("    ").append("\n");
                    sb.append("    /**").append("\n");
                    sb.append("     * ").append(item.getLabel()).append("(プルダウンメニュー)の初期選択値を返します。<br>").append("\n");
                    sb.append("     * 初期選択値は{@link #set").append(item.getId()).append("(java.util.SortedMap) ").append(item.getLabel()).append("プルダウンメニュー}のマップのキー").append("\n");
                    sb.append("     * ").append("\n");
                    sb.append("     * @return ").append(item.getLabel()).append("の初期選択値").append("\n");
                    sb.append("     */").append("\n");
                    sb.append("    String get").append(item.getId()).append("Value();").append("\n");
                    sb.append("    ").append("\n");
                    sb.append("    /**").append("\n");
                    sb.append("     * ").append(item.getLabel()).append("の初期選択値を設定します。<br>").append("\n");
                    sb.append("     * 初期選択値は{@link #set").append(item.getId()).append("(java.util.SortedMap) ").append(item.getLabel()).append("プルダウンメニュー}のマップのキー").append("\n");
                    sb.append("     * ").append("\n");
                    sb.append("     * @param ").append(item.getId().toLowerCase()).append("Value ").append(item.getLabel()).append("の初期選択値").append("\n");
                    sb.append("     */").append("\n");
                    sb.append("    void set").append(item.getId()).append("Value(String ").append(item.getId().toLowerCase()).append("Value);").append("\n");
                    sb.append("    ").append("\n");

//                } else if (item.isIpAddress()) {
//                    sb.append("    /**").append("\n");
//                    sb.append("     * ").append(item.getLabel()).append("の画面表示内容を返します。").append("\n");
//                    sb.append("     * ").append("\n");
//                    sb.append("     * @return ").append(item.getLabel()).append("の画面表示内容").append("\n");
//                    sb.append("     */").append("\n");
//                    sb.append("    java.util.List<String> get").append(item.getId()).append("List();").append("\n");
//                    sb.append("    ").append("\n");

                } else if (item.isRadioButton()
                        || item.isCheckbox()) {

                    sb.append("    /**").append("\n");
                    sb.append("     * ").append(item.getLabel()).append("(").append(item.getInputType()).append(")").append("の選択状態を返します。").append("\n");
                    sb.append("     * ").append("\n");
                    sb.append("     * @return ").append(item.getLabel()).append("(").append(item.getInputType()).append(")").append("の選択状態").append("\n");
                    sb.append("     */").append("\n");
                    sb.append("    String get").append(item.getId()).append("();").append("\n");
                    sb.append("    ").append("\n");
                    sb.append("    /**").append("\n");
                    sb.append("     * ").append(item.getId()).append("(").append(item.getInputType()).append(")").append("の選択状態を設定します。<br>").append("\n");
                    sb.append("     * 選択状態にする場合は\"checked\"という文字列を設定します。<br>").append("\n");
                    sb.append("     * 未選択状態にする場合は空文字列を設定します。").append("\n");
                    sb.append("     * ").append("\n");
                    sb.append("     * @param ").append(item.getId().toLowerCase()).append(" ").append(item.getLabel()).append("(").append(item.getInputType()).append(")").append("の選択状態").append("\n");
                    sb.append("     */").append("\n");
                    sb.append("    void set").append(item.getId()).append("(String ").append(item.getId().toLowerCase()).append(");").append("\n");
                    sb.append("    ").append("\n");

                } else {
                    sb.append("    /**").append("\n");
                    sb.append("     * ").append(item.getLabel()).append("(").append(item.getInputType()).append(")").append("の画面表示内容を返します。").append("\n");
                    sb.append("     * ").append("\n");
                    sb.append("     * @return ").append(item.getLabel()).append("(").append(item.getInputType()).append(")").append("の画面表示内容").append("\n");
                    sb.append("     */").append("\n");
                    sb.append("    String get").append(item.getId()).append("();").append("\n");
                    sb.append("    ").append("\n");
                    sb.append("    /**").append("\n");
                    sb.append("     * ").append(item.getLabel()).append("(").append(item.getInputType()).append(")").append("の画面表示内容を設定します。").append("\n");
                    sb.append("     * ").append("\n");
                    sb.append("     * @param ").append(item.getId().toLowerCase()).append(" ").append(item.getLabel()).append("(").append(item.getInputType()).append(")").append("の画面表示内容").append("\n");
                    sb.append("     */").append("\n");
                    sb.append("    void set").append(item.getId()).append("(String ").append(item.getId().toLowerCase()).append(");").append("\n");
                    sb.append("    ").append("\n");
                }
            }

            hasInput = true;
        }

        if (!hasInput) {
            sb.append("    // 保持しておく情報はなし").append("\n");
            sb.append("    ").append("\n");
        }

        sb.append("}").append("\n");

        return sb.toString();
    }
}
