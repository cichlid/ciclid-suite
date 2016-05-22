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
public class SettingFormBuilder implements Builder {

    private final FrameData frameData;

    public SettingFormBuilder(FrameData frameData) {
        this.frameData = frameData;
    }

    @Override
    public String build() {

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
        sb.append("package ").append(Constants.PRJ_WEB_SETTING_FORM_PACKAGE).append(";").append("\n");
        sb.append("\n");

        // import
        sb.append("import java.io.Serializable;").append("\n");
        sb.append("\n");
        sb.append("import org.apache.commons.lang3.builder.ToStringBuilder;").append("\n");
        sb.append("import org.apache.commons.lang3.builder.ToStringStyle;").append("\n");
        sb.append("\n");
        if (table) {
            sb.append("import ").append(Constants.BASE_PACKAGE).append(".context.InvocationContextSupport;").append("\n");
        }

        if (table) {
            sb.append("import ").append(Constants.ENTITY_PACKAGE).append(".Entity;").append("\n");
            sb.append("import ").append(Constants.VIEW_MODEL_PACKAGE).append(".TableViewModelProvider;").append("\n");
            sb.append("import ").append(Constants.VIEW_MODEL_PACKAGE_TABLE).append(".TableViewModel;").append("\n");
            sb.append("import ").append(Constants.WEB_PACKAGE).append(".constants.FrameId;").append("\n");
            sb.append("import ").append(Constants.WEB_PACKAGE).append(".form.setting.TableForm;").append("\n");
        } else {
            sb.append("import ").append(Constants.WEB_PACKAGE).append(".form.setting.SettingForm;").append("\n");
        }
        sb.append("\n");
        sb.append("import ").append(Constants.PRJ_DTO_PACKAGE).append(".").append(frameData.getFrameId()).append("_SettingData;").append("\n");
        sb.append("\n");

        // class看板
        sb.append("/**").append("\n");
        sb.append(" * クラス名：").append(frameData.getFrameId()).append("_SettingForm").append("\n");
        sb.append(" * 機能概要：").append(frameData.getFrameName()).append("の入力フォームクラスです。").append("\n");
        sb.append(" * 備考：").append("\n");
        sb.append(" * ").append("\n");
        sb.append(" */").append("\n");

        sb.append(dateValidator());

        // class
        if (table) {
            sb.append("public class ").append(frameData.getFrameId()).append("_SettingForm implements TableForm<").append(frameData.getFrameId()).append("_TableRowForm>, Serializable, ").append(frameData.getFrameId()).append("_SettingData {").append("\n");
        } else {
            sb.append("public class ").append(frameData.getFrameId()).append("_SettingForm implements SettingForm, Serializable, ").append(frameData.getFrameId()).append("_SettingData {").append("\n");
        }
        sb.append("    ").append("\n");
        sb.append("    // ★★自動生成のため本ファイルを直接修正しないこと★★").append("\n");
        sb.append("    ").append("\n");
        sb.append("    private static final long serialVersionUID = 1L;").append("\n");
        sb.append("    ").append("\n");


        StringBuilder field = new StringBuilder();
        StringBuilder getset = new StringBuilder();

        field.append(sysidField());
        getset.append(sysidGetSet());

        Set<String> radio = new HashSet<String>();

        for (FrameItemData item : frameData.getItems()) {

            if (!item.getId().startsWith(FrameItemData.ItemType.SETTING_DATA.type)) {
                continue;
            }

            if (!item.isInput()) {
                continue;
            }

            if (item.isCalendar()) {
                // 日時
                field.append(dateField(item));
                getset.append(dateGetterSetter(item));

            } else {
                if (item.isRadioButton()) {

                    String radioName = Utils.getRadioButtonName(frameData, item);
                    if (radio.contains(radioName)) {
                        continue;
                    }
                    radio.add(radioName);

                    field.append("    /**").append("\n");
                    field.append("     * ").append(item.getLabel()).append("の入力値<br>").append("\n");
                    if (!Utils.isDBEmpty(item)) {
                        field.append("     * 対応テーブル：{@link " + Constants.PRJ_ENTITY_PACKAGE + "." + Utils.toUCC(item.getTableName()) + " " + item.getTableName() + "} <br>").append("\n");
                        field.append("     * 対応カラム：{@link " + Constants.PRJ_ENTITY_PACKAGE + "." + Utils.toUCC(item.getTableName()) + ".COLUMN#" + item.getColumnName().toUpperCase() + " " + item.getColumnName().toUpperCase() + "} <br>").append("\n");
                    }
                    field.append("     */").append("\n");
                    field.append("    private String ").append(radioName.toLowerCase()).append("_radio;").append("\n");
                    field.append("    ").append("\n");

                    getset.append(radioGetSet(item, radioName));

                } else if (item.isTable()) {
//                    field.append("    /**").append("\n");
//                    field.append("     * ").append(item.getLabel()).append("の入力値").append("\n");
//                    field.append("     */").append("\n");
//                    field.append("    @com.opensymphony.xwork2.util.Element(value = ").append(Constants.WEB_SETTING_FORM_PACKAGE).append(".TableItemForm.class)").append("\n");
//                    field.append("    private java.util.List<TableItemForm> ").append(item.getId().toLowerCase()).append("List = new java.util.ArrayList<TableItemForm>();").append("\n");
//                    field.append("    ").append("\n");
//
//                    getset.append(tableGetSet(item));

                } else {
                    if (item.isIpAddress()) {

                        String formClass = getIpFormName(item);

                        field.append("    /**").append("\n");
                        field.append("     * ").append(item.getLabel()).append("の入力値").append("\n");
                        field.append("     */").append("\n");
                        field.append("    @com.opensymphony.xwork2.util.Element(value = ").append(Constants.PRJ_WEB_SETTING_FORM_PACKAGE).append(".").append(formClass).append(".class)").append("\n");
                        field.append("    private java.util.List<").append(formClass).append("> ").append(item.getId().toLowerCase()).append("List = new java.util.ArrayList<").append(formClass).append(">();").append("\n");
                        field.append("    ").append("\n");
                        getset.append(ipaddressGetSet(item));

                    } else {
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
            }
        }


        if (table) {
            field.append(tableField());
            getset.append(tableGetSet());
        }

        sb.append(field.toString());
        sb.append(getset.toString());

        if (table) {
            sb.append("    /**").append("\n");
            sb.append("     * テーブルの入力情報を返します。").append("\n");
            sb.append("     * ").append("\n");
            sb.append("     * ").append("\n");
            sb.append("     * @return TableViewModel").append("\n");
            sb.append("     */").append("\n");
            sb.append("    @SuppressWarnings(\"unchecked\")").append("\n");
            sb.append("    @Override").append("\n");
            sb.append("    public <T extends Entity> TableViewModel<T> getTableViewModel() {").append("\n");
            sb.append("        return ((TableViewModelProvider<T>) InvocationContextSupport.getViewModelFromSession(FrameId.").append(frameData.getFrameId()).append(")).getTableViewModel();").append("\n");
            sb.append("    }").append("\n");
            sb.append("    ").append("\n");
        }

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

    private String dateValidator() {

        StringBuilder custom = new StringBuilder("");

        StringBuilder sb = new StringBuilder("");

        for (FrameItemData item : frameData.getItems()) {

            if (!item.getId().startsWith(FrameItemData.ItemType.SETTING_DATA.type)) {
                continue;
            }

            if (!item.isInput()) {
                continue;
            }

            if (!item.isCalendar()) {
                continue;
            }

            if (sb.length() > 0) {
                sb.append(",").append("\n");
            }

            sb.append("                @com.opensymphony.xwork2.validator.annotations.CustomValidator(type = \"yyyymmddhhmi\", key = ").append(Constants.PRJ_BASE_PACKAGE).append(".web.view.DialogMessageKey.Common_C009,").append("\n");
            sb.append("                        parameters = {@com.opensymphony.xwork2.validator.annotations.ValidationParameter(name = \"itemId\", value = \"").append(frameData.getFrameId()).append(".").append(getLabelId_cal(item)).append("\"),").append("\n");
            sb.append("                                      @com.opensymphony.xwork2.validator.annotations.ValidationParameter(name = \"yyyy\", value = \"").append(item.getId()).append("_YYYY\"),").append("\n");
            sb.append("                                      @com.opensymphony.xwork2.validator.annotations.ValidationParameter(name = \"mm\", value = \"").append(item.getId()).append("_MM\"),").append("\n");
            sb.append("                                      @com.opensymphony.xwork2.validator.annotations.ValidationParameter(name = \"dd\", value = \"").append(item.getId()).append("_DD\"),").append("\n");
            sb.append("                                      @com.opensymphony.xwork2.validator.annotations.ValidationParameter(name = \"hh\", value = \"").append(item.getId()).append("_HH\"),").append("\n");
            sb.append("                                      @com.opensymphony.xwork2.validator.annotations.ValidationParameter(name = \"mi\", value = \"").append(item.getId()).append("_MI\") })");
        }

        if (sb.length() > 0) {
            custom.append("@com.opensymphony.xwork2.validator.annotations.Validations(").append("\n");
            custom.append("        customValidators = {").append("\n");
            custom.append(sb.toString()).append("\n");
            custom.append("        }").append("\n");
            custom.append(")").append("\n");
        }

        return custom.toString();
    }

    private String dateField(FrameItemData item) {

        StringBuilder sb = new StringBuilder();

        for (String s : Constants.date) {
            sb.append("    /**").append("\n");
            sb.append("     * ").append(item.getLabel()).append("の入力値(").append(s).append(")<br>").append("\n");
            if (!Utils.isDBEmpty(item)) {
                sb.append("     * 対応テーブル：{@link " + Constants.PRJ_ENTITY_PACKAGE + "." + Utils.toUCC(item.getTableName()) + " " + item.getTableName() + "} <br>").append("\n");
                sb.append("     * 対応カラム：{@link " + Constants.PRJ_ENTITY_PACKAGE + "." + Utils.toUCC(item.getTableName()) + ".COLUMN#" + item.getColumnName().toUpperCase() + " " + item.getColumnName().toUpperCase() + "} <br>").append("\n");
            }
            sb.append("     */").append("\n");
            sb.append("    private String ").append(item.getId().toLowerCase()).append("_").append(s).append(";").append("\n");
            sb.append("    ").append("\n");
        }

        return sb.toString();
    }

    private String dateGetterSetter(FrameItemData item) {

        StringBuilder sb = new StringBuilder();

        sb.append("    /**").append("\n");
        sb.append("     * ").append(item.getLabel()).append("の入力値(Date型)を返します。<br>").append("\n");
        if (!Utils.isDBEmpty(item)) {
            sb.append("     * 対応テーブル：{@link " + Constants.PRJ_ENTITY_PACKAGE + "." + Utils.toUCC(item.getTableName()) + " " + item.getTableName() + "} <br>").append("\n");
            sb.append("     * 対応カラム：{@link " + Constants.PRJ_ENTITY_PACKAGE + "." + Utils.toUCC(item.getTableName()) + ".COLUMN#" + item.getColumnName().toUpperCase() + " " + item.getColumnName().toUpperCase() + "} <br>").append("\n");
        }
        sb.append("     * ").append("\n");
        sb.append("     * @return ").append(item.getLabel()).append("の入力値(Date型)").append("\n");
        sb.append("     */").append("\n");
        sb.append("    public java.util.Date get").append(item.getId()).append("() {").append("\n");
        sb.append("        return com.fujitsu.proactnes.inventorymanager.util.DateUtil.YYMMDDHH24MI.newDate(");
        for (String s : Constants.date) {
            sb.append(item.getId().toLowerCase()).append("_").append(s);
            if (!Constants.date[Constants.date.length - 1].equals(s)) {
                sb.append(", ");
            }
        }
        sb.append(");").append("\n");
        sb.append("    }").append("\n");
        sb.append("    ").append("\n");

        for (String s : Constants.date) {

            sb.append("    /**").append("\n");
            sb.append("     * ").append(item.getLabel()).append("の入力値(").append(s).append(")を返します。<br>").append("\n");
            sb.append("     * ").append("\n");
            sb.append("     * @return ").append(item.getLabel()).append("の入力値(").append(s).append(")").append("\n");
            sb.append("     */").append("\n");
            sb.append("    public String get").append(item.getId()).append("_").append(s).append("() {").append("\n");
            sb.append("        return ").append(item.getId().toLowerCase()).append("_").append(s).append(";").append("\n");
            sb.append("    }").append("\n");
            sb.append("    ").append("\n");

            sb.append("    /**").append("\n");
            sb.append("     * ").append(item.getLabel()).append("の入力値(").append(s).append(")を設定します。<br>").append("\n");
            sb.append("     * ").append("\n");
            sb.append("     * @param ").append(item.getId().toLowerCase()).append("_").append(s).append(" ").append(item.getLabel()).append("の入力値").append("\n");
            sb.append("     */").append("\n");
            sb.append("    public void set").append(item.getId()).append("_").append(s).append("(String ").append(item.getId().toLowerCase()).append("_").append(s).append(") {").append("\n");
            sb.append("        this.").append(item.getId().toLowerCase()).append("_").append(s).append(" = ").append(item.getId().toLowerCase()).append("_").append(s).append(";").append("\n");
            sb.append("    }").append("\n");
            sb.append("    ").append("\n");
        }

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

    private String radioGetSet(FrameItemData item, String radioName) {

        radioName += "_radio";

        StringBuilder sb = new StringBuilder();

        sb.append("    /**").append("\n");
        sb.append("     * ").append(item.getLabel()).append("の入力値を返します。<br>").append("\n");
        if (!Utils.isDBEmpty(item)) {
            sb.append("     * 対応テーブル：{@link " + Constants.PRJ_ENTITY_PACKAGE + "." + Utils.toUCC(item.getTableName()) + " " + item.getTableName() + "} <br>").append("\n");
            sb.append("     * 対応カラム：{@link " + Constants.PRJ_ENTITY_PACKAGE + "." + Utils.toUCC(item.getTableName()) + ".COLUMN#" + item.getColumnName().toUpperCase() + " " + item.getColumnName().toUpperCase() + "} <br>").append("\n");
        }
        sb.append("     * ").append("\n");
        sb.append("     * @return ").append(item.getLabel()).append("の入力値").append("\n");
        sb.append("     */").append("\n");
        sb.append("    @Override").append("\n");
        sb.append("    public String get").append(radioName).append("() {").append("\n");
        sb.append("        return ").append(radioName.toLowerCase()).append(";").append("\n");
        sb.append("    }").append("\n");
        sb.append("    ").append("\n");
        sb.append("    /**").append("\n");
        sb.append("     * ").append(item.getLabel()).append("の入力値を設定します。<br>").append("\n");
        if (!Utils.isDBEmpty(item)) {
            sb.append("     * 対応テーブル：{@link " + Constants.PRJ_ENTITY_PACKAGE + "." + Utils.toUCC(item.getTableName()) + " " + item.getTableName() + "} <br>").append("\n");
            sb.append("     * 対応カラム：{@link " + Constants.PRJ_ENTITY_PACKAGE + "." + Utils.toUCC(item.getTableName()) + ".COLUMN#" + item.getColumnName().toUpperCase() + " " + item.getColumnName().toUpperCase() + "} <br>").append("\n");
        }
        sb.append("     * ").append("\n");
        sb.append("     * @param ").append(radioName.toLowerCase()).append(" ").append(item.getLabel()).append("の入力値").append("\n");
        sb.append("     */").append("\n");
        sb.append(validator(item));
        sb.append("    public void set").append(radioName).append("(String ").append(radioName.toLowerCase()).append(") {").append("\n");
        sb.append("        this.").append(radioName.toLowerCase()).append(" = ").append(radioName.toLowerCase()).append(";").append("\n");
        sb.append("    }").append("\n");
        sb.append("    ").append("\n");

        return sb.toString();
    }

    private String tableField() {

        String tableRowClass = frameData.getFrameId() + "_TableRowForm";

        StringBuilder sb = new StringBuilder();

        sb.append("    /**").append("\n");
        sb.append("     * テーブルの入力情報のリスト<br>").append("\n");
        sb.append("     */").append("\n");
        sb.append("    @com.opensymphony.xwork2.util.Element(value = ").append(Constants.PRJ_WEB_SETTING_FORM_PACKAGE).append(".").append(tableRowClass).append(".class)").append("\n");
        sb.append("    private java.util.List<").append(tableRowClass).append("> tableRowFormList = new java.util.ArrayList<").append(tableRowClass).append(">();").append("\n");
        sb.append("    ").append("\n");

        return sb.toString();
    }
    private String tableGetSet() {

        String tableRowClass = frameData.getFrameId() + "_TableRowForm";

        StringBuilder sb = new StringBuilder();

        sb.append("    /**").append("\n");
        sb.append("     * テーブルの入力情報のリストを返します。").append("\n");
        sb.append("     * ").append("\n");
        sb.append("     * @return テーブルの入力情報のリスト").append("\n");
        sb.append("     */").append("\n");
        sb.append("    @Override").append("\n");
        sb.append("    public java.util.List<").append(tableRowClass).append("> getTableRowFormList() {").append("\n");
        sb.append("        return tableRowFormList;").append("\n");
        sb.append("    }").append("\n");
        sb.append("    ").append("\n");
        sb.append("    /**").append("\n");
        sb.append("     * テーブルの入力情報のリストを設定します。").append("\n");
        sb.append("     * ").append("\n");
        sb.append("     * @param tableRowFormList テーブルの入力情報のリスト").append("\n");
        sb.append("     */").append("\n");
//        sb.append(tableValidator(item));
        sb.append("    @Override").append("\n");
        sb.append("    public void setTableRowFormList(java.util.List<").append(tableRowClass).append("> tableRowFormList) {").append("\n");
        sb.append("        this.tableRowFormList = tableRowFormList;").append("\n");
        sb.append("    }").append("\n");
        sb.append("    ").append("\n");

        return sb.toString();
    }

    private String ipaddressGetSet(FrameItemData item) {

        String formClass = getIpFormName(item);

        StringBuilder sb = new StringBuilder();

        sb.append("    /**").append("\n");
        sb.append("     * ").append(item.getLabel()).append("の入力値を返します。").append("\n");
        sb.append("     * ").append("\n");
        sb.append("     * @return ").append(item.getLabel()).append("の入力値").append("\n");
        sb.append("     */").append("\n");
        sb.append("    @Override").append("\n");
        sb.append("    public java.util.List<").append(formClass).append("> get").append(item.getId()).append("List() {").append("\n");
        sb.append("        return ").append(item.getId().toLowerCase()).append("List;").append("\n");
        sb.append("    }").append("\n");
        sb.append("    ").append("\n");
        sb.append("    /**").append("\n");
        sb.append("     * ").append(item.getLabel()).append("の入力値を設定します。").append("\n");
        sb.append("     * ").append("\n");
        sb.append("     * @param ").append(item.getId().toLowerCase()).append("List ").append(item.getLabel()).append("の入力値").append("\n");
        sb.append("     */").append("\n");
        sb.append(ipaddressValidator(item));
        sb.append("    @com.opensymphony.xwork2.validator.annotations.VisitorFieldValidator(message=\"\")").append("\n");
        sb.append("    public void set").append(item.getId()).append("List(java.util.List<").append(formClass).append("> ").append(item.getId().toLowerCase()).append("List) {").append("\n");
        sb.append("        this.").append(item.getId().toLowerCase()).append("List = ").append(item.getId().toLowerCase()).append("List;").append("\n");
        sb.append("    }").append("\n");
        sb.append("    ").append("\n");

        return sb.toString();
    }

    private String itemGetSet(FrameItemData item) {

        StringBuilder sb = new StringBuilder();

        sb.append("    /**").append("\n");
        sb.append("     * ").append(item.getLabel()).append("の入力値を返します。<br>").append("\n");
        if (!Utils.isDBEmpty(item)) {
            sb.append("     * 対応テーブル：{@link " + Constants.PRJ_ENTITY_PACKAGE + "." + Utils.toUCC(item.getTableName()) + " " + item.getTableName() + "} <br>").append("\n");
            sb.append("     * 対応カラム：{@link " + Constants.PRJ_ENTITY_PACKAGE + "." + Utils.toUCC(item.getTableName()) + ".COLUMN#" + item.getColumnName().toUpperCase() + " " + item.getColumnName().toUpperCase() + "} <br>").append("\n");
        }
        sb.append("     * ").append("\n");
        sb.append("     * @return ").append(item.getLabel()).append("の入力値").append("\n");
        sb.append("     */").append("\n");
        sb.append("    @Override").append("\n");
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
        sb.append(validator(item));
        sb.append("    public void set").append(item.getId()).append("(String ").append(item.getId().toLowerCase()).append(") {").append("\n");
        sb.append("        this.").append(item.getId().toLowerCase()).append(" = ").append(item.getId().toLowerCase()).append(";").append("\n");
        sb.append("    }").append("\n");
        sb.append("    ").append("\n");

        return sb.toString();
    }

    private String validator(FrameItemData item) {

        StringBuilder validator = new StringBuilder("");

        StringBuilder custom = new StringBuilder("");

        if (item.isRequired()) {
            String msdId = (item.isText() || item.isPassword() || item.isTextArea()) ? "C013" : "C012";
            custom.append("                    @com.opensymphony.xwork2.validator.annotations.CustomValidator(type = \"requiredstring\", key = ").append(Constants.PRJ_BASE_PACKAGE).append(".web.view.DialogMessageKey.Common_").append(msdId).append(",").append("\n");
            custom.append("                            parameters = {@com.opensymphony.xwork2.validator.annotations.ValidationParameter(name = \"itemId\", value = \"").append(frameData.getFrameId()).append(".").append(getLabelId(item)).append("\") })");
        }

        if (item.isRequired() && item.isPulldown()) {
            if (custom.length() > 0) {
                custom.append(",").append("\n");
            }
            // 必須入力のプルダウン（空白の選択を許さないプルダウン）
            String msdId = "C012";
            custom.append("                    @com.opensymphony.xwork2.validator.annotations.CustomValidator(type = \"requiredselectpulldown\", key = ").append(Constants.PRJ_BASE_PACKAGE).append(".web.view.DialogMessageKey.Common_").append(msdId).append(",").append("\n");
            custom.append("                            parameters = {@com.opensymphony.xwork2.validator.annotations.ValidationParameter(name = \"itemId\", value = \"").append(frameData.getFrameId()).append(".").append(getLabelId(item)).append("\") })");


        }


        if (item.isNumeric()) {
            if (custom.length() > 0) {
                custom.append(",").append("\n");
            }
            custom.append("                    @com.opensymphony.xwork2.validator.annotations.CustomValidator(type = \"numeric\", key = ").append(Constants.PRJ_BASE_PACKAGE).append(".web.view.DialogMessageKey.Common_C012,").append("\n");
            custom.append("                            parameters = {@com.opensymphony.xwork2.validator.annotations.ValidationParameter(name = \"itemId\", value = \"").append(frameData.getFrameId()).append(".").append(getLabelId(item)).append("\") })");
        }

        if (item.isAscii()) {
            if (custom.length() > 0) {
                custom.append(",").append("\n");
            }
            custom.append("                    @com.opensymphony.xwork2.validator.annotations.CustomValidator(type = \"alphanumericsign\", key = ").append(Constants.PRJ_BASE_PACKAGE).append(".web.view.DialogMessageKey.Common_C011,").append("\n");
            custom.append("                            parameters = {@com.opensymphony.xwork2.validator.annotations.ValidationParameter(name = \"itemId\", value = \"").append(frameData.getFrameId()).append(".").append(getLabelId(item)).append("\") })");
        }

        if (item.getRange() > 0) {
            if (custom.length() > 0) {
                custom.append(",").append("\n");
            }
            String msgNo = (item.isNumeric() || item.isAscii()) ? "C014" : "C008";
            custom.append("                    @com.opensymphony.xwork2.validator.annotations.CustomValidator(type = \"bytelength\", key = ").append(Constants.PRJ_BASE_PACKAGE).append(".web.view.DialogMessageKey.Common_").append(msgNo).append(",").append("\n");
            custom.append("                            parameters = {@com.opensymphony.xwork2.validator.annotations.ValidationParameter(name = \"maxLength\", value = \"").append(item.getRange()).append("\"),").append("\n");
            custom.append("                                          @com.opensymphony.xwork2.validator.annotations.ValidationParameter(name = \"itemId\", value = \"").append(frameData.getFrameId()).append(".").append(getLabelId(item)).append("\") })");
        }

        if (custom.length() > 0) {
            validator.append("    @com.opensymphony.xwork2.validator.annotations.Validations(").append("\n");
            if (custom.length() > 0) {
                validator.append("            customValidators = {").append("\n");
                validator.append(custom.toString()).append("\n");
                validator.append("            }").append("\n");
            }
            validator.append("    )").append("\n");
        }

        return validator.toString();
    }

    @SuppressWarnings("unused")
    private String tableValidator(FrameItemData item) {

        StringBuilder validator = new StringBuilder("");

        if (item.isRequired()) {
            validator.append("    @com.opensymphony.xwork2.validator.annotations.Validations(").append("\n");
            validator.append("            customValidators = {").append("\n");
            validator.append("                    @com.opensymphony.xwork2.validator.annotations.CustomValidator(type = \"requiredarray\", key = ").append(Constants.PRJ_BASE_PACKAGE).append(".web.view.DialogMessageKey.Common_C013,").append("\n");
            validator.append("                            parameters = {@com.opensymphony.xwork2.validator.annotations.ValidationParameter(name = \"itemId\", value = \"").append(frameData.getFrameId()).append(".").append(getLabelId(item)).append("\") })").append("\n");
            validator.append("            }").append("\n");
            validator.append("    )").append("\n");
        }

        return validator.toString();
    }

    private String ipaddressValidator(FrameItemData item) {

        StringBuilder validator = new StringBuilder("");

        if (item.isRequired()) {
            validator.append("    @com.opensymphony.xwork2.validator.annotations.Validations(").append("\n");
            validator.append("            customValidators = {").append("\n");
            validator.append("                    @com.opensymphony.xwork2.validator.annotations.CustomValidator(type = \"requiredarray\", key = ").append(Constants.PRJ_BASE_PACKAGE).append(".web.view.DialogMessageKey.Common_C013,").append("\n");
            validator.append("                            parameters = {@com.opensymphony.xwork2.validator.annotations.ValidationParameter(name = \"itemId\", value = \"").append(frameData.getFrameId()).append(".").append(getLabelId(item)).append("\") })").append("\n");
            validator.append("            }").append("\n");
            validator.append("    )").append("\n");
        }

        return validator.toString();
    }

    private String getLabelId_cal(FrameItemData item) {
        return Utils.getLabelId_cal(frameData, item);
    }

    private String getLabelId(FrameItemData item) {
        return Utils.getLabelId(frameData, item);
    }


    private String getIpFormName(FrameItemData item) {

        String formName = "IpAddressInputForm";
        if (item.getLabel().equals("IPアドレス")) {
            // 初期値のまま
        } else if (item.getLabel().equals("アドレス範囲")) {
            formName = "IpAddressRangeInputForm";

        } else if (item.getLabel().equals("サブネットマスク")) {
            formName = "SubNetMaskInputForm";

        } else {
            System.out.println("★★★★不明 " + frameData.getFrameName() + ", " + item.getLabel());
            throw new IllegalArgumentException();
        }

        return formName;
    }
}
