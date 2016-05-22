package alpha114.tool.cichlid.autogenerationtool.src.builder;

import java.util.HashMap;
import java.util.Map;

import alpha114.tool.cichlid.autogenerationtool.Builder;
import alpha114.tool.cichlid.autogenerationtool.Constants;
import alpha114.tool.cichlid.autogenerationtool.Utils;
import alpha114.tool.cichlid.autogenerationtool.data.FrameData;
import alpha114.tool.cichlid.autogenerationtool.data.FrameItemData;

/**
 * @author kurisakisatoshi
 */
public class SearchConditionFormBuilder implements Builder {

    private final FrameData frameData;

    /**
     * コンストラクタ
     *
     * @param frameData フレームデータ
     */
    public SearchConditionFormBuilder(FrameData frameData) {
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
        sb.append("package ").append(Constants.PRJ_WEB_CONDITION_FORM_PACKAGE).append(";").append("\n");
        sb.append("\n");

        // import
        sb.append("import java.io.Serializable;").append("\n");
        sb.append("\n");
        sb.append("import org.apache.commons.lang3.StringUtils;").append("\n");
        sb.append("import org.apache.commons.lang3.builder.ToStringBuilder;").append("\n");
        sb.append("import org.apache.commons.lang3.builder.ToStringStyle;").append("\n");
        sb.append("\n");
        sb.append("import ").append(Constants.WEB_CONDITION_FORM_PACKAGE).append(".SearchConditionForm;").append("\n");
        sb.append("\n");
        sb.append("import ").append(Constants.PRJ_BASE_PACKAGE).append(".dto.").append(frameData.getFrameId()).append("_SearchCondition;").append("\n");
        sb.append("\n");

        // class看板
        sb.append("/**").append("\n");
        sb.append(" * クラス名：").append(frameData.getFrameId()).append("_SearchConditionForm").append("\n");
        sb.append(" * 機能概要：").append(frameData.getFrameName()).append("の検索条件フォームクラスです。").append("\n");
        sb.append(" * 備考：").append("\n");
        sb.append(" * ").append("\n");
        sb.append(" */").append("\n");

        sb.append(dateValidator());

        // class
        sb.append("public class ").append(frameData.getFrameId()).append("_SearchConditionForm implements Serializable, SearchConditionForm, ").append(frameData.getFrameId()).append("_SearchCondition {").append("\n");
        sb.append("    ").append("\n");
        sb.append("    // ★★自動生成のため本ファイルを直接修正しないこと★★").append("\n");
        sb.append("    ").append("\n");
        sb.append("    private static final long serialVersionUID = 1L;").append("\n");
        sb.append("    ").append("\n");

        for (FrameItemData item : frameData.getItems()) {

            if (!item.getId().startsWith(FrameItemData.ItemType.SEARCH_CONDITION.type)) {
                continue;
            }

            if (item.isCalendar()) {
                // 日時
                sb.append(dateField(item));

            } else {
                // 日時以外

                if (item.getLabel().equals("種別") && (item.getTableName().equals("") || item.getTableName().equals("-"))) {
                    sb.append("    /** ").append(item.getLabel()).append("の検索条件 */").append("\n");
                } else {
                    sb.append("    /**").append("\n");
                    sb.append("     * ").append(item.getLabel()).append("の検索条件<br>").append("\n");
                    if (!Utils.isDBEmpty(item)) {
                        sb.append("     * 対応テーブル：{@link " + Constants.PRJ_ENTITY_PACKAGE + "." + Utils.toUCC(item.getTableName()) + " " + item.getTableName() + "} <br>").append("\n");
                        sb.append("     * 対応カラム：{@link " + Constants.PRJ_ENTITY_PACKAGE + "." + Utils.toUCC(item.getTableName()) + ".COLUMN#" + item.getColumnName().toUpperCase() + " " + item.getColumnName().toUpperCase() + "} <br>").append("\n");
                        sb.append("     * ").append("\n");
                    }
                    sb.append("     */").append("\n");
                }
                sb.append("    private String ").append(item.getId().toLowerCase()).append(";").append("\n");
                sb.append("    ").append("\n");
            }
        }

        for (FrameItemData item : frameData.getItems()) {

            if (!item.getId().startsWith(FrameItemData.ItemType.SEARCH_CONDITION.type)) {
                continue;
            }

            if (item.isCalendar()) {
                sb.append(dateGetterSetter(item));

            } else {
                sb.append("    /**").append("\n");
                sb.append("     * ").append(item.getLabel()).append("の検索条件を返します。<br>").append("\n");
                if (item.getLabel().equals("種別") && (item.getTableName().equals("") || item.getTableName().equals("-"))) {
                } else {
                    if (!Utils.isDBEmpty(item)) {
                        sb.append("     * 対応テーブル：{@link " + Constants.PRJ_ENTITY_PACKAGE + "." + Utils.toUCC(item.getTableName()) + " " + item.getTableName() + "} <br>").append("\n");
                        sb.append("     * 対応カラム：{@link " + Constants.PRJ_ENTITY_PACKAGE + "." + Utils.toUCC(item.getTableName()) + ".COLUMN#" + item.getColumnName().toUpperCase() + " " + item.getColumnName().toUpperCase() + "} <br>").append("\n");
                    }
                }
                sb.append("     * ").append("\n");
                sb.append("     * @return ").append(item.getLabel()).append("の検索条件").append("\n");
                sb.append("     */").append("\n");
                sb.append("    public String get").append(item.getId()).append("() {").append("\n");
                sb.append("        return ").append(item.getId().toLowerCase()).append(";").append("\n");
                sb.append("    }").append("\n");
                sb.append("    ").append("\n");
                sb.append("    /**").append("\n");
                sb.append("     * ").append(item.getLabel()).append("の検索条件を設定します。<br>").append("\n");
                if (item.getLabel().equals("種別") && (item.getTableName().equals("") || item.getTableName().equals("-"))) {
                } else {
                    if (!Utils.isDBEmpty(item)) {
                        sb.append("     * 対応テーブル：{@link " + Constants.PRJ_ENTITY_PACKAGE + "." + Utils.toUCC(item.getTableName()) + " " + item.getTableName() + "} <br>").append("\n");
                        sb.append("     * 対応カラム：{@link " + Constants.PRJ_ENTITY_PACKAGE + "." + Utils.toUCC(item.getTableName()) + ".COLUMN#" + item.getColumnName().toUpperCase() + " " + item.getColumnName().toUpperCase() + "} <br>").append("\n");
                    }
                }
                sb.append("     * ").append("\n");
                sb.append("     * @param ").append(item.getId().toLowerCase()).append(" ").append(item.getLabel()).append("の検索条件").append("\n");
                sb.append("     */").append("\n");

                StringBuilder valiSb = new StringBuilder();
                if (item.getRange() != 0) {
                    valiSb.append("                    @com.opensymphony.xwork2.validator.annotations.CustomValidator(type = \"bytelength\", key = ").append(Constants.PRJ_BASE_PACKAGE).append(".web.view.DialogMessageKey.Common_C008,").append("\n");
                    valiSb.append("                            parameters = {@com.opensymphony.xwork2.validator.annotations.ValidationParameter(name = \"itemId\", value = \"").append(frameData.getFrameId()).append(".").append(getLabelId(item)).append("\"),").append("\n");
                    valiSb.append("                                          @com.opensymphony.xwork2.validator.annotations.ValidationParameter(name = \"maxLength\", value = \"").append(item.getRange()).append("\") })");
                }

                if (item.isRequired()) {
                    if (valiSb.length() > 0) {
                        valiSb.append(",").append("\n");
                    }
                    String msdId = (item.isText() || item.isPassword() || item.isTextArea()) ? "C014" : "C013";
                    valiSb.append("                    @com.opensymphony.xwork2.validator.annotations.CustomValidator(type = \"requiredstring\", key = ").append(Constants.PRJ_BASE_PACKAGE).append(".web.view.DialogMessageKey.Common_").append(msdId).append(",").append("\n");
                    valiSb.append("                            parameters = {@com.opensymphony.xwork2.validator.annotations.ValidationParameter(name = \"itemId\", value = \"").append(frameData.getFrameId()).append(".").append(getLabelId(item)).append("\") })");
                }

                if (item.isRequired() && item.isPulldown()) {
                    if (valiSb.length() > 0) {
                        valiSb.append(",").append("\n");
                    }
                    // 必須入力のプルダウン（空白の選択を許さないプルダウン）
                    String msdId = "C013";
                    valiSb.append("                    @com.opensymphony.xwork2.validator.annotations.CustomValidator(type = \"requiredselectpulldown\", key = ").append(Constants.PRJ_BASE_PACKAGE).append(".web.view.DialogMessageKey.Common_").append(msdId).append(",").append("\n");
                    valiSb.append("                            parameters = {@com.opensymphony.xwork2.validator.annotations.ValidationParameter(name = \"itemId\", value = \"").append(frameData.getFrameId()).append(".").append(getLabelId(item)).append("\") })");
                }

                if (valiSb.length() > 0) {
                    sb.append("    @com.opensymphony.xwork2.validator.annotations.Validations(").append("\n");
                    if (valiSb.length() > 0) {
                        sb.append("            customValidators = {").append("\n");
                        sb.append(valiSb.toString()).append("\n");
                        sb.append("            }").append("\n");
                    }
                    sb.append("    )").append("\n");
                }

                sb.append("    public void set").append(item.getId()).append("(String ").append(item.getId().toLowerCase()).append(") {").append("\n");
                sb.append("        this.").append(item.getId().toLowerCase()).append(" = ").append(item.getId().toLowerCase()).append(";").append("\n");
                sb.append("    }").append("\n");
                sb.append("    ").append("\n");
            }
        }

        sb.append("    /**").append("\n");
        sb.append("     * {@inheritDoc}").append("\n");
        sb.append("     */").append("\n");
        sb.append("    @Override").append("\n");
        sb.append("    public String toString() {").append("\n");
        sb.append("        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);").append("\n");
        sb.append("    }").append("\n");
        sb.append("    ").append("\n");
        sb.append("    ").append("\n");

        sb.append(implementsSearchCondition());

        sb.append("}").append("\n");

        return sb.toString();
    }

    /**
     * @param item FrameItemData
     * @return 日時フィールド
     */
    private String dateField(FrameItemData item) {

        StringBuilder sb = new StringBuilder();

        for (String s : date) {
            sb.append("    /**").append("\n");
            sb.append("     * ").append(item.getLabel()).append("の検索条件(").append(s).append(")<br>").append("\n");
            sb.append("     * 対応テーブル：{@link " + Constants.PRJ_ENTITY_PACKAGE + "." + Utils.toUCC(item.getTableName()) + " " + item.getTableName() + "} <br>").append("\n");
            sb.append("     * 対応カラム：{@link " + Constants.PRJ_ENTITY_PACKAGE + "." + Utils.toUCC(item.getTableName()) + ".COLUMN#" + item.getColumnName().toUpperCase() + " " + item.getColumnName().toUpperCase() + "} <br>").append("\n");
            sb.append("     * ").append("\n");
            sb.append("     */").append("\n");
            sb.append("    private String ").append(item.getId().toLowerCase()).append("_").append(s).append(";").append("\n");
            sb.append("    ").append("\n");
        }

        return sb.toString();
    }

    /**
     * @param item FrameItemData
     * @return 日時のGetter/Setter
     */
    private String dateGetterSetter(FrameItemData item) {

        StringBuilder sb = new StringBuilder();

        for (String s : date) {

            sb.append("    /**").append("\n");
            sb.append("     * ").append(item.getLabel()).append("の検索条件(").append(s).append(")を返します。<br>").append("\n");
            if (item.getLabel().equals("種別") && item.getTableName().equals("-")) {
            } else {
                if (!Utils.isDBEmpty(item)) {
                    sb.append("     * 対応テーブル：{@link " + Constants.PRJ_ENTITY_PACKAGE + "." + Utils.toUCC(item.getTableName()) + " " + item.getTableName() + "} <br>").append("\n");
                    sb.append("     * 対応カラム：{@link " + Constants.PRJ_ENTITY_PACKAGE + "." + Utils.toUCC(item.getTableName()) + ".COLUMN#" + item.getColumnName().toUpperCase() + " " + item.getColumnName().toUpperCase() + "} <br>").append("\n");
                }
            }
            sb.append("     * ").append("\n");
            sb.append("     * @return ").append(item.getLabel()).append("の検索条件(").append(s).append(")").append("\n");
            sb.append("     */").append("\n");
            sb.append("    public String get").append(item.getId()).append("_").append(s).append("() {").append("\n");
            sb.append("        return ").append(item.getId().toLowerCase()).append("_").append(s).append(";").append("\n");
            sb.append("    }").append("\n");
            sb.append("    ").append("\n");
            sb.append("    /**").append("\n");
            sb.append("     * ").append(item.getLabel()).append("の検索条件(").append(s).append(")を設定します。<br>").append("\n");
            if (item.getLabel().equals("種別") && item.getTableName().equals("-")) {
            } else {
                if (!Utils.isDBEmpty(item)) {
                    sb.append("     * 対応テーブル：{@link " + Constants.PRJ_ENTITY_PACKAGE + "." + Utils.toUCC(item.getTableName()) + " " + item.getTableName() + "} <br>").append("\n");
                    sb.append("     * 対応カラム：{@link " + Constants.PRJ_ENTITY_PACKAGE + "." + Utils.toUCC(item.getTableName()) + ".COLUMN#" + item.getColumnName().toUpperCase() + " " + item.getColumnName().toUpperCase() + "} <br>").append("\n");
                }
            }
            sb.append("     * ").append("\n");
            sb.append("     * @param ").append(item.getId().toLowerCase()).append("_").append(s).append(" ").append(item.getLabel()).append("の検索条件").append("\n");
            sb.append("     */").append("\n");
            sb.append("    public void set").append(item.getId()).append("_").append(s).append("(String ").append(item.getId().toLowerCase()).append("_").append(s).append(") {").append("\n");
            sb.append("        this.").append(item.getId().toLowerCase()).append("_").append(s).append(" = ").append(item.getId().toLowerCase()).append("_").append(s).append(";").append("\n");
            sb.append("    }").append("\n");
            sb.append("    ").append("\n");
        }

        return sb.toString();
    }

    /**
     * @param item FrameItemData
     * @return ラベルID
     */
    private String getLabelId(FrameItemData item) {
        return Utils.getLabelId(frameData, item);
    }

    /**
     * @param item FrameItemData
     * @return ラベルID
     */
    private String getLabelId_cal(FrameItemData item) {
        return Utils.getLabelId_cal(frameData, item);
    }

    /**
     *
     * @return 日時Validation
     */
    private String dateValidator() {

        StringBuilder custom = new StringBuilder("");

        StringBuilder sb = new StringBuilder("");

        for (FrameItemData item : frameData.getItems()) {

            if (!item.getId().startsWith(FrameItemData.ItemType.SEARCH_CONDITION.type)) {
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
            custom.append("        })").append("\n");
        }

        return custom.toString();
    }

    /**
     *
     * @return 検索条件設定
     */
    private String implementsSearchCondition() {

        StringBuilder sb = new StringBuilder();

        sb.append("    /**").append("\n");
        sb.append("     * {@inheritDoc}").append("\n");
        sb.append("     */").append("\n");
        sb.append("    @Override").append("\n");
        sb.append("    public boolean isEmpty() {").append("\n");
        sb.append("        return ");

        Map<String, Integer> map = new HashMap<String, Integer>();
        Map<FrameItemData, Integer> map2 = new HashMap<FrameItemData, Integer>();

        StringBuilder condition = new StringBuilder();
        for (FrameItemData item : frameData.getItems()) {

            if (!item.getId().startsWith(FrameItemData.ItemType.SEARCH_CONDITION.type)) {
                continue;
            }

            if (condition.length() != 0) {
                condition.append(" && ");
            }

            if (item.isCalendar()) {
                for (String s : date) {
                    condition.append("StringUtils.isEmpty(").append(item.getId().toLowerCase()).append("_").append(s).append(")");
                }

            } else {
                if (item.isPulldown()) {
                    condition.append("(StringUtils.isEmpty(").append(item.getId().toLowerCase()).append(") || String.valueOf(").append(item.getId().toLowerCase()).append(").equals(String.valueOf(Integer.MIN_VALUE)))");

                } else {
                    condition.append("StringUtils.isEmpty(").append(item.getId().toLowerCase()).append(")");
                }
            }

            if (Utils.isDBEmpty(item)) {
                continue;
            }

            String key = item.getTableName().toUpperCase() + "___" + item.getColumnName().toUpperCase();
            if (map.containsKey(key)) {
                int i = map.get(key) + 1;
                map.put(key, i);
            } else {
                map.put(key, 1);
            }
            map2.put(item, map.get(key));
        }
        sb.append(condition).append(";").append("\n");
        sb.append("    }").append("\n");
        sb.append("    ").append("\n");

        for (FrameItemData item : frameData.getItems()) {

            if (!item.getId().startsWith(FrameItemData.ItemType.SEARCH_CONDITION.type)) {
                continue;
            }

            if (Utils.isDBEmpty(item)
                    && !(item.getLabel().equals("種別") && (item.getTableName().equals("") || item.getTableName().equals("-")))) {
                continue;
            }

            sb.append("    /**").append("\n");
            sb.append("     * {@inheritDoc}").append("\n");
            sb.append("     */").append("\n");
            sb.append("    @Override").append("\n");

            if (item.isCalendar()) {

                String fromTo = item.getLabel().indexOf("(開始)") > 0 ? "From" : "To";

                sb.append("    public java.util.Date getCondition_").append(item.getTableName().toUpperCase()).append("___").append(item.getColumnName().toUpperCase()).append("_").append(fromTo).append("() {").append("\n");
                sb.append("        if (");
                for (int i = 0; i < date.length; i++) {
                    sb.append("StringUtils.isEmpty(").append(item.getId().toLowerCase() + "_" + date[i]).append(")");
                    if (i != date.length - 1) {
                        sb.append(" || ");
                    }
                }
                sb.append(") {").append("\n");
                sb.append("            return null;").append("\n");
                sb.append("        }").append("\n");
                sb.append("        return ").append(Constants.BASE_PACKAGE).append(".util.DateUtil.YYMMDDHH24MI.newDate(");
                for (int i = 0; i < date.length; i++) {
                    sb.append(item.getId().toLowerCase() + "_" + date[i]);
                    if (i != date.length - 1) {
                        sb.append(", ");
                    }
                }
                sb.append(");").append("\n");
                sb.append("    }").append("\n");
                sb.append("    ").append("\n");

            } else {

                sb.append("    public ");

                if (item.getLabel().equals("種別") && (item.getTableName().equals("") || item.getTableName().equals("-"))) {

                    sb.append("String getCondition_TYPE() {").append("\n");
                    sb.append("        return ").append(item.getId().toLowerCase()).append(";").append("\n");
                    sb.append("    }").append("\n");

                } else {

                    String key = item.getTableName().toUpperCase() + "___" + item.getColumnName().toUpperCase();
                    int i = map.get(key);
                    if (i > 1) {
                        key += "_" + map2.get(item);
                    }

                    if (item.isPulldown() && !item.getInputDataType().equals("文字列")) {
                        sb.append("int");
                        sb.append(" getCondition_").append(key).append("() {").append("\n");
                        sb.append("        return org.apache.commons.lang.math.NumberUtils.isNumber(").append(item.getId().toLowerCase()).append(") ? Integer.parseInt(").append(item.getId().toLowerCase()).append(") : Integer.MIN_VALUE;").append("\n");
                        sb.append("    }").append("\n");

                    } else {
                        sb.append("String");
                        sb.append(" getCondition_").append(key).append("() {").append("\n");
                        sb.append("        return ").append(item.getId().toLowerCase()).append(";").append("\n");
                        sb.append("    }").append("\n");
                    }
                }
                sb.append("    ").append("\n");
            }
        }

        return sb.toString();
    }

    private static final String[] date = Constants.date;
}
