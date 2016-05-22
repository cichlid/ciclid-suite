package alpha114.tool.cichlid.autogenerationtool.src.builder;

import alpha114.tool.cichlid.autogenerationtool.Builder;
import alpha114.tool.cichlid.autogenerationtool.Constants;
import alpha114.tool.cichlid.autogenerationtool.Utils;
import alpha114.tool.cichlid.autogenerationtool.data.FrameData;
import alpha114.tool.cichlid.autogenerationtool.data.FrameItemData;
import alpha114.tool.cichlid.autogenerationtool.data.TableListItemData;

/**
 * @author kurisakisatoshi
 */
public class SettingTableRowFormBuilder implements Builder {

    private final FrameData frameData;


    public SettingTableRowFormBuilder(FrameData frameData) {
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
        sb.append(" * 備考    ：").append("\n");
        sb.append(" * 更新履歴：").append("\n");
        sb.append(" *   日付       更新者       内容").append("\n");
        sb.append(" *   ").append(Constants.DATE_CREATED).append("   kurisakisatoshi   新規作成").append("\n");
        sb.append(" * ").append("\n");
        sb.append(" */").append("\n");

        // package
        sb.append("package ").append(Constants.PRJ_WEB_SETTING_FORM_PACKAGE_DIR).append(";").append("\n");
        sb.append("\n");

        // import
        sb.append("import java.io.Serializable;").append("\n");
        sb.append("\n");
        sb.append("import org.apache.commons.lang3.builder.ToStringBuilder;").append("\n");
        sb.append("import org.apache.commons.lang3.builder.ToStringStyle;").append("\n");
        sb.append("\n");
        sb.append("import ").append(Constants.WEB_PACKAGE).append(".form.setting.TableRowForm;").append("\n");
        sb.append("\n");

        // class看板
        sb.append("/**").append("\n");
        sb.append(" * クラス名：").append(frameData.getFrameId()).append("_SettingForm").append("\n");
        sb.append(" * 機能概要：").append(frameData.getFrameName()).append("の入力フォームクラスです。").append("\n");
        sb.append(" * 備考：").append("\n");
        sb.append(" * ").append("\n");
        sb.append(" */").append("\n");

        // class
        sb.append("public class ").append(frameData.getFrameId()).append("_TableRowForm implements TableRowForm, Serializable {").append("\n");
        sb.append("    ").append("\n");
        sb.append("    // ★★自動生成のため本ファイルを直接修正しないこと★★").append("\n");
        sb.append("    ").append("\n");
        sb.append("    private static final long serialVersionUID = 1L;").append("\n");
        sb.append("    ").append("\n");

        StringBuilder field = new StringBuilder();
        StringBuilder getset = new StringBuilder();

        field.append(sysidField());
        getset.append(sysidGetSet());

        for (FrameItemData tableItem : frameData.getItems()) {

            if (!tableItem.isTable()) {
                continue;
            }

            for (TableListItemData item : tableItem.getTableColumns()) {
                if (!item.isInput()) {
                    continue;
                }

                field.append("    /**").append("\n");
                field.append("     * ").append(item.getLabel()).append("の入力値<br>").append("\n");
                if (!Utils.isDBEmpty(item)) {
                    field.append("     * 対応テーブル：{@link " + Constants.PRJ_ENTITY_PACKAGE + "." + Utils.toUCC(item.getTableName()) + " " + item.getTableName() + "} <br>").append("\n");
                    field.append("     * 対応カラム：{@link " + Constants.PRJ_ENTITY_PACKAGE + "." + Utils.toUCC(item.getTableName()) + ".COLUMN#" + item.getColumnName().toUpperCase() + " " + item.getColumnName().toUpperCase() + "} <br>").append("\n");
                }
                field.append("     */").append("\n");
                field.append("    private String ").append(item.getId().toLowerCase()).append(";").append("\n");
                field.append("    ").append("\n");

                getset.append(itemGetSet(item));
            }
        }

        sb.append(field.toString());
        sb.append(getset.toString());

        sb.append("    /**").append("\n");
        sb.append("     * {@inheritDoc}").append("\n");
        sb.append("     */").append("\n");
        sb.append("    @Override").append("\n");
        sb.append("    public String toString() {").append("\n");
        sb.append("        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);").append("\n");
        sb.append("    }").append("\n");

        sb.append("}").append("\n");

        return sb.toString();
    }


    private String sysidField() {

        StringBuilder field = new StringBuilder();

        field.append("    /**").append("\n");
        field.append("     * SYSID<br>").append("\n");
        field.append("     */").append("\n");
        field.append("    private int sysid;").append("\n");
        field.append("    ").append("\n");
//
//        field.append("    /**").append("\n");
//        field.append("     * 更新No<br>").append("\n");
//        field.append("     */").append("\n");
//        field.append("    private int updateNo;").append("\n");
//        field.append("    ").append("\n");

        return field.toString();
    }

    private String sysidGetSet() {

        StringBuilder sb = new StringBuilder();

        sb.append("    /**").append("\n");
        sb.append("     * SYSIDを返します。").append("\n");
        sb.append("     * ").append("\n");
        sb.append("     * @return SYSID").append("\n");
        sb.append("     */").append("\n");
        sb.append("    @Override").append("\n");
        sb.append("    public int getSysid() {").append("\n");
        sb.append("        return sysid;").append("\n");
        sb.append("    }").append("\n");
        sb.append("    ").append("\n");
        sb.append("    /**").append("\n");
        sb.append("     * SYSIDを設定します。").append("\n");
        sb.append("     * ").append("\n");
        sb.append("     * @param sysid SYSID").append("\n");
        sb.append("     */").append("\n");
        sb.append("    @Override").append("\n");
        sb.append("    public void setSysid(int sysid) {").append("\n");
        sb.append("        this.sysid = sysid;").append("\n");
        sb.append("    }").append("\n");
        sb.append("    ").append("\n");
//
//        sb.append("    /**").append("\n");
//        sb.append("     * 更新Noを返します。").append("\n");
//        sb.append("     * ").append("\n");
//        sb.append("     * @return 更新No").append("\n");
//        sb.append("     */").append("\n");
//        sb.append("    public int getUpdateNo() {").append("\n");
//        sb.append("        return updateNo;").append("\n");
//        sb.append("    }").append("\n");
//        sb.append("    ").append("\n");
//        sb.append("    /**").append("\n");
//        sb.append("     * 更新Noを設定します。").append("\n");
//        sb.append("     * ").append("\n");
//        sb.append("     * @param updateNo 更新No").append("\n");
//        sb.append("     */").append("\n");
//        sb.append("    public void setUpdateNo(int updateNo) {").append("\n");
//        sb.append("        this.updateNo = updateNo;").append("\n");
//        sb.append("    }").append("\n");
//        sb.append("    ").append("\n");

        return sb.toString();
    }

    private String itemGetSet(TableListItemData item) {

        StringBuilder sb = new StringBuilder();

        sb.append("    /**").append("\n");
        sb.append("     * ").append(item.getLabel()).append("の入力値を返します。<br>").append("\n");
        if (!Utils.isDBEmpty(item)) {
            sb.append("     * 対応テーブル：{@link " + Constants.PRJ_ENTITY_PACKAGE + "." + Utils.toUCC(item.getTableName()) + " " + item.getTableName() + "} <br>").append("\n");
            sb.append("     * 対応カラム：{@link " + Constants.PRJ_ENTITY_PACKAGE + "." + Utils.toUCC(item.getTableName()) + ".COLUMN#" + item.getColumnName().toUpperCase() + " " + item.getColumnName().toUpperCase() + "} <br>").append("\n");
        }
        sb.append("     * ").append("\n");
        sb.append("     * @return ").append(item.getId()).append("の入力値").append("\n");
        sb.append("     */").append("\n");
        sb.append("    public String get").append(item.getId()).append("() {").append("\n");
        sb.append("        return ").append(item.getId().toLowerCase()).append(";").append("\n");
        sb.append("    }").append("\n");
        sb.append("    ").append("\n");
        sb.append("    /**").append("\n");
        sb.append("     * ").append(item.getLabel()).append("の入力値を設定します。<br>").append("\n");
        if (!Utils.isDBEmpty(item)) {
            sb.append("     * 対応テーブル：{@link " + Constants.PRJ_ENTITY_PACKAGE + "." + Utils.toUCC(item.getTableName()) + " " + item.getTableName() + "} <br>").append("\n");
            sb.append("     * 対応カラム：{@link " + Constants.PRJ_ENTITY_PACKAGE + "." + Utils.toUCC(item.getTableName()) + ".COLUMN#" + item.getColumnName().toUpperCase() + " " + item.getColumnName().toUpperCase() + "} <br>").append("\n");
        }
        sb.append("     * ").append("\n");
        sb.append("     * @param ").append(item.getId().toLowerCase()).append(" ").append(item.getLabel()).append("の入力値").append("\n");
        sb.append("     */").append("\n");
//        sb.append(validator(item));
        sb.append("    public void set").append(item.getId()).append("(String ").append(item.getId().toLowerCase()).append(") {").append("\n");
        sb.append("        this.").append(item.getId().toLowerCase()).append(" = ").append(item.getId().toLowerCase()).append(";").append("\n");
        sb.append("    }").append("\n");
        sb.append("    ").append("\n");

        return sb.toString();
    }

//    private String getHeaderItemId(FrameItemData tableItem, TableListItemData clm) {
//        return Utils.getHeaderItem(frameData, tableItem.getTableColumns(), clm).getId();
//    }
}
