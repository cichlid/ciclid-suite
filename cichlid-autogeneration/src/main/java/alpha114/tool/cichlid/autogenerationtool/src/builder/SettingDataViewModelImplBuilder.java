package alpha114.tool.cichlid.autogenerationtool.src.builder;

import alpha114.tool.cichlid.autogenerationtool.Builder;
import alpha114.tool.cichlid.autogenerationtool.Constants;
import alpha114.tool.cichlid.autogenerationtool.data.FrameData;
import alpha114.tool.cichlid.autogenerationtool.data.FrameItemData;

/**
 * @author kurisakisatoshi
 */
public class SettingDataViewModelImplBuilder implements Builder {

    private final FrameData frameData;

    public SettingDataViewModelImplBuilder(FrameData frameData) {
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
        sb.append("package ").append(Constants.PRJ_VIEW_MODEL_PACKAGE_SETTING).append(".impl;").append("\n");
        sb.append("\n");

        // import
        sb.append("import ").append(Constants.PRJ_VIEW_MODEL_PACKAGE_SETTING).append(".").append(frameData.getFrameId()).append("_SettingDataViewModel;").append("\n");
        sb.append("\n");

        // class看板
        sb.append("/**").append("\n");
        sb.append(" * クラス名：").append(frameData.getFrameId()).append("_SettingDataViewModelImpl").append("\n");
        sb.append(" * 機能概要：").append(frameData.getFrameName()).append("のViewModelクラスです。").append("\n");
        sb.append(" * 備考：").append("\n");
        sb.append(" * ").append("\n");
        sb.append(" */").append("\n");

        // class
        sb.append("public class ").append(frameData.getFrameId()).append("_SettingDataViewModelImpl implements ").append(frameData.getFrameId()).append("_SettingDataViewModel {").append("\n");

        sb.append("    ").append("\n");
        sb.append("    private static final long serialVersionUID = 1L;").append("\n");
        sb.append("    ").append("\n");

        boolean hasInput = false;

        StringBuilder field = new StringBuilder();
        StringBuilder gs = new StringBuilder();

        for (FrameItemData item : frameData.getItems()) {

            if (!item.isInput()) {
                continue;
            }

            if (!item.getId().startsWith(FrameItemData.ItemType.SETTING_DATA.type)) {
                continue;
            }

            if (item.isCalendar()) {

//                for (String s : Constants.date) {
//                    gs.append("    /**").append("\n");
//                    gs.append("     * {@inheritDoc}").append("\n");
//                    gs.append("     */").append("\n");
//                    gs.append("    @Override").append("\n");
//                    gs.append("    String get").append(item.getId()).append("_").append(s).append("();").append("\n");
//                    gs.append("    ").append("\n");
//                    gs.append("    /**").append("\n");
//                    gs.append("     * ").append(item.getLabel()).append("の画面表示内容(").append(s).append(")を設定します。").append("\n");
//                    gs.append("     * ").append("\n");
//                    gs.append("     * @param ").append(item.getId().toLowerCase()).append(" ").append(item.getLabel()).append("の画面表示内容(").append(s).append(")").append("\n");
//                    gs.append("     */").append("\n");
//                    gs.append("    void set").append(item.getId()).append("_").append(s).append("(String ").append(item.getId().toLowerCase()).append("_").append(s).append(");").append("\n");
//                    gs.append("    ").append("\n");
//                }

            } else {
                if (item.isPulldown()) {

                    field.append("    ").append("/** ").append(item.getLabel()).append("のプルダウンメニュー").append(" */").append("\n");
                    field.append("    ").append("private java.util.SortedMap<? extends Object, ? extends Object> ").append(item.getId().toLowerCase()).append(" = new java.util.TreeMap<Object, Object>();").append("\n");
                    field.append("    ").append("\n");
                    field.append("    ").append("/** ").append(item.getLabel()).append("(プルダウンメニュー)の初期選択値").append(" */").append("\n");
                    field.append("    ").append("private String ").append(item.getId().toLowerCase()).append("Value = \"\";").append("\n");
                    field.append("    ").append("\n");

                    gs.append("    /**").append("\n");
                    gs.append("     * ").append(item.getLabel()).append("のプルダウンメニューを返します。<br>").append("\n");
                    gs.append("     * プルダウンのメニュー順にソートしておくこと").append("\n");
                    gs.append("     * ").append("\n");
                    gs.append("     * @return ").append(item.getLabel()).append("のプルダウンメニュー").append("\n");
                    gs.append("     */").append("\n");
                    gs.append("    @Override").append("\n");
                    gs.append("    public java.util.SortedMap<? extends Object, ? extends Object> get").append(item.getId()).append("() {").append("\n");
                    gs.append("    ").append("    return ").append(item.getId().toLowerCase()).append(";").append("\n");
                    gs.append("    }").append("\n");
                    gs.append("    ").append("\n");

                    gs.append("    /**").append("\n");
                    gs.append("     * ").append(item.getLabel()).append("のプルダウンメニューを設定します。<br>").append("\n");
                    gs.append("     * プルダウンのメニュー順にソートしておくこと").append("\n");
                    gs.append("     * ").append("\n");
                    gs.append("     * @param ").append(item.getId().toLowerCase()).append(" ").append(item.getLabel()).append("のプルダウンメニュー").append("\n");
                    gs.append("     */").append("\n");
                    gs.append("    @Override").append("\n");
                    gs.append("    public void set").append(item.getId()).append("(java.util.SortedMap<? extends Object, ? extends Object> ").append(item.getId().toLowerCase()).append(") {").append("\n");
                    gs.append("    ").append("    this.").append(item.getId().toLowerCase()).append(" = ").append(item.getId().toLowerCase()).append(";").append("\n");
                    gs.append("    }").append("\n");
                    gs.append("    ").append("\n");

                    gs.append("    /**").append("\n");
                    gs.append("     * ").append(item.getLabel()).append("(プルダウンメニュー)の初期選択値を返します。<br>").append("\n");
                    gs.append("     * 初期選択値は{@link #set").append(item.getId()).append("(java.util.SortedMap) ").append(item.getLabel()).append("プルダウンメニュー}のマップのキー").append("\n");
                    gs.append("     * ").append("\n");
                    gs.append("     * @return ").append(item.getLabel()).append("の初期選択値").append("\n");
                    gs.append("     */").append("\n");
                    gs.append("    @Override").append("\n");
                    gs.append("    public String get").append(item.getId()).append("Value() {").append("\n");
                    gs.append("    ").append("    return ").append(item.getId().toLowerCase()).append("Value;").append("\n");
                    gs.append("    }").append("\n");
                    gs.append("    ").append("\n");

                    gs.append("    /**").append("\n");
                    gs.append("     * ").append(item.getLabel()).append("の初期選択値を設定します。<br>").append("\n");
                    gs.append("     * 初期選択値は{@link #set").append(item.getId()).append("(java.util.SortedMap) ").append(item.getLabel()).append("プルダウンメニュー}のマップのキー").append("\n");
                    gs.append("     * ").append("\n");
                    gs.append("     * @param ").append(item.getId().toLowerCase()).append("Value ").append(item.getLabel()).append("の初期選択値").append("\n");
                    gs.append("     */").append("\n");
                    gs.append("    @Override").append("\n");
                    gs.append("    public void set").append(item.getId()).append("Value(String ").append(item.getId().toLowerCase()).append("Value) {").append("\n");
                    gs.append("    ").append("    this.").append(item.getId().toLowerCase()).append("Value = ").append(item.getId().toLowerCase()).append("Value;").append("\n");
                    gs.append("    }").append("\n");
                    gs.append("    ").append("\n");

//                } else if (item.isIpAddress()) {
//                    gs.append("    /**").append("\n");
//                    gs.append("     * ").append(item.getLabel()).append("の画面表示内容を返します。").append("\n");
//                    gs.append("     * ").append("\n");
//                    gs.append("     * @return ").append(item.getLabel()).append("の画面表示内容").append("\n");
//                    gs.append("     */").append("\n");
//                    gs.append("    java.util.List<String> get").append(item.getId()).append("List();").append("\n");
//                    gs.append("    ").append("\n");

                } else if (item.isRadioButton() || item.isCheckbox()) {

                    field.append("    ").append("/** ").append(item.getLabel()).append("(").append(item.getInputType()).append(")").append("の選択状態").append(" */").append("\n");
                    field.append("    ").append("private String ").append(item.getId().toLowerCase()).append(" = \"\";").append("\n");
                    field.append("    ").append("\n");

                    gs.append("    /**").append("\n");
                    gs.append("     * ").append(item.getLabel()).append("(").append(item.getInputType()).append(")").append("の選択状態を返します。").append("\n");
                    gs.append("     * ").append("\n");
                    gs.append("     * @return ").append(item.getLabel()).append("(").append(item.getInputType()).append(")").append("の選択状態").append("\n");
                    gs.append("     */").append("\n");
                    gs.append("    @Override").append("\n");
                    gs.append("    public String get").append(item.getId()).append("() {").append("\n");
                    gs.append("    ").append("    return ").append(item.getId().toLowerCase()).append(";").append("\n");
                    gs.append("    }").append("\n");
                    gs.append("    ").append("\n");

                    gs.append("    /**").append("\n");
                    gs.append("     * ").append(item.getId()).append("(").append(item.getInputType()).append(")").append("の選択状態を設定します。<br>").append("\n");
                    gs.append("     * 選択状態にする場合は\"checked\"という文字列を設定します。<br>").append("\n");
                    gs.append("     * 未選択状態にする場合は空文字列を設定します。").append("\n");
                    gs.append("     * ").append("\n");
                    gs.append("     * @param ").append(item.getId().toLowerCase()).append(" ").append(item.getLabel()).append("(").append(item.getInputType()).append(")").append("の選択状態").append("\n");
                    gs.append("     */").append("\n");
                    gs.append("    @Override").append("\n");
                    gs.append("    public void set").append(item.getId()).append("(String ").append(item.getId().toLowerCase()).append(") {").append("\n");
                    gs.append("    ").append("    this.").append(item.getId().toLowerCase()).append(" = ").append(item.getId().toLowerCase()).append(";").append("\n");
                    gs.append("    }").append("\n");
                    gs.append("    ").append("\n");

                } else {

                    field.append("    ").append("/** ").append(item.getLabel()).append("(").append(item.getInputType()).append(")").append("の画面表示内容").append(" */").append("\n");
                    field.append("    ").append("private String ").append(item.getId().toLowerCase()).append(" = \"\";").append("\n");
                    field.append("    ").append("\n");

                    gs.append("    /**").append("\n");
                    gs.append("     * ").append(item.getLabel()).append("(").append(item.getInputType()).append(")").append("の画面表示内容を返します。").append("\n");
                    gs.append("     * ").append("\n");
                    gs.append("     * @return ").append(item.getLabel()).append("(").append(item.getInputType()).append(")").append("の画面表示内容").append("\n");
                    gs.append("     */").append("\n");
                    gs.append("    @Override").append("\n");
                    gs.append("    public String get").append(item.getId()).append("() {").append("\n");
                    gs.append("    ").append("    return ").append(item.getId().toLowerCase()).append(";").append("\n");
                    gs.append("    }").append("\n");
                    gs.append("    ").append("\n");

                    gs.append("    /**").append("\n");
                    gs.append("     * ").append(item.getLabel()).append("(").append(item.getInputType()).append(")").append("の画面表示内容を設定します。").append("\n");
                    gs.append("     * ").append("\n");
                    gs.append("     * @param ").append(item.getId().toLowerCase()).append(" ").append(item.getLabel()).append("(").append(item.getInputType()).append(")").append("の画面表示内容").append("\n");
                    gs.append("     */").append("\n");
                    gs.append("    @Override").append("\n");
                    gs.append("    public void set").append(item.getId()).append("(String ").append(item.getId().toLowerCase()).append(") {").append("\n");
                    gs.append("    ").append("    this.").append(item.getId().toLowerCase()).append(" = ").append(item.getId().toLowerCase()).append(";").append("\n");
                    gs.append("    }").append("\n");
                    gs.append("    ").append("\n");
                }
            }

            hasInput = true;
        }

        sb.append(field.toString());
        sb.append(gs.toString());

        if (!hasInput) {
            sb.append("    // 空実装").append("\n");
            sb.append("    ").append("\n");
        } else {
            sb.append("    /**").append("\n");
            sb.append("     * {@inheritDoc}").append("\n");
            sb.append("     */").append("\n");
            sb.append("    @Override").append("\n");
            sb.append("    public String toString() {").append("\n");
            sb.append("        return org.apache.commons.lang.builder.ToStringBuilder.reflectionToString(this, org.apache.commons.lang.builder.ToStringStyle.SHORT_PREFIX_STYLE);").append("\n");
            sb.append("    }").append("\n");
        }

        sb.append("}").append("\n");

        return sb.toString();
    }
}
