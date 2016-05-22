package alpha114.tool.cichlid.autogenerationtool.src.builder;

import java.util.HashSet;
import java.util.Set;

import alpha114.tool.cichlid.autogenerationtool.Builder;
import alpha114.tool.cichlid.autogenerationtool.Constants;
import alpha114.tool.cichlid.autogenerationtool.Utils;
import alpha114.tool.cichlid.autogenerationtool.data.FrameData;
import alpha114.tool.cichlid.autogenerationtool.data.FrameItemData;
import alpha114.tool.cichlid.autogenerationtool.data.TableListItemData;

/**
 * @author kurisakisatoshi
 */
public class SettingDataDtoBuilder implements Builder {

    private final FrameData frameData;

    public SettingDataDtoBuilder(FrameData frameData) {
        this.frameData = frameData;
    }


    /* (非 Javadoc)
     * @see alpha114_117.tool.cichlid.autogenerationtool2.Builder#build()
     */
    @Override
    public String build() {

        StringBuilder sb = new StringBuilder();
        // ファイル看板
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

        // package
        sb.append("package ").append(Constants.PRJ_DTO_PACKAGE).append(";").append("\n");
        sb.append("\n");

        // import
        sb.append("import ").append(Constants.DTO_PACKAGE).append(".SettingData;").append("\n");
        sb.append("\n");

        // class看板
        sb.append("/**").append("\n");
        sb.append(" * クラス名：").append(frameData.getFrameId()).append("_SettingData").append("\n");
        sb.append(" * 機能概要：").append(frameData.getFrameName()).append("の設定情報のインタフェースです。").append("\n");
        sb.append(" * 備考：").append("\n");
        sb.append(" * ").append("\n");
        sb.append(" */").append("\n");

        // class
        sb.append("public interface ").append(frameData.getFrameId()).append("_SettingData extends SettingData {").append("\n");
        sb.append("    ").append("\n");
        sb.append("    // ★★自動生成のため本ファイルを直接修正しないこと★★").append("\n");
        sb.append("    ").append("\n");

        sb.append("    /**").append("\n");
        sb.append("     * SYSIDを返します。").append("\n");
        sb.append("     * ").append("\n");
        sb.append("     * @return SYSID").append("\n");
        sb.append("     */").append("\n");
        sb.append("    int getSysid();").append("\n");

        Set<String> radio = new HashSet<String>();

        for (FrameItemData item : frameData.getItems()) {

            if (!item.getId().startsWith(FrameItemData.ItemType.SETTING_DATA.type)) {
                continue;
            }

            if (!item.isInput()) {
                continue;
            }

            if (item.isRadioButton()) {

                String radioName = Utils.getRadioButtonName(frameData, item);
                if (radio.contains(radioName)) {
                    continue;
                }
                radio.add(radioName);

                sb.append("    ").append("\n");
                sb.append("    /**").append("\n");
                sb.append("     * ").append(item.getLabel()).append("の入力値を返します。<br>").append("\n");
                if (!Utils.isDBEmpty(item)) {
                    sb.append("     * 対応テーブル：{@link " + Constants.PRJ_ENTITY_PACKAGE + "." + Utils.toUCC(item.getTableName()) + " " + item.getTableName() + "} <br>").append("\n");
                    sb.append("     * 対応カラム：{@link " + Constants.PRJ_ENTITY_PACKAGE + "." + Utils.toUCC(item.getTableName()) + ".COLUMN#" + item.getColumnName().toUpperCase() + " " + item.getColumnName().toUpperCase() + "} <br>").append("\n");
                }
                sb.append("     * ").append("\n");
                sb.append("     * @return ").append(item.getLabel()).append("の入力値").append("\n");
                sb.append("     */").append("\n");
                sb.append("    String get").append(radioName).append("_radio();").append("\n");

            } else {
                sb.append("    ").append("\n");
                sb.append("    /**").append("\n");
                sb.append("     * ").append(item.getLabel()).append("の入力値を返します。<br>").append("\n");
                if (!Utils.isDBEmpty(item)) {
                    sb.append("     * 対応テーブル：{@link " + Constants.PRJ_ENTITY_PACKAGE + "." + Utils.toUCC(item.getTableName()) + " " + item.getTableName() + "} <br>").append("\n");
                    sb.append("     * 対応カラム：{@link " + Constants.PRJ_ENTITY_PACKAGE + "." + Utils.toUCC(item.getTableName()) + ".COLUMN#" + item.getColumnName().toUpperCase() + " " + item.getColumnName().toUpperCase() + "} <br>").append("\n");
                }
                sb.append("     * ").append("\n");
                sb.append("     * @return ").append(item.getLabel()).append("の入力値").append("\n");
                sb.append("     */").append("\n");
                sb.append("    String get").append(item.getId()).append("();").append("\n");
            }
        }

        boolean table = false;

        for (FrameItemData item : frameData.getItems()) {
            if (item.isTable()) {
                for (TableListItemData tableItem : item.getTableColumns()) {
                    if (!tableItem.isInput()) {
                        continue;
                    }
                    table = true;
                    break;
                }
            }
            if (table) {
                break;
            }
        }

        if (table) {
            sb.append("    ").append("\n");
            sb.append("    /**").append("\n");
            sb.append("     * テーブルの入力情報を返します。<br>").append("\n");
            sb.append("     * ").append("\n");
            sb.append("     * ").append("\n");
            sb.append("     * @return TableViewModel").append("\n");
            sb.append("     */").append("\n");
            sb.append("    <T extends ").append(Constants.PRJ_ENTITY_PACKAGE).append(".Entity> ").append(Constants.VIEW_MODEL_PACKAGE_TABLE).append(".TableViewModel<T> getTableViewModel();").append("\n");
        }

        sb.append("}").append("\n");

        return sb.toString();
    }
}
