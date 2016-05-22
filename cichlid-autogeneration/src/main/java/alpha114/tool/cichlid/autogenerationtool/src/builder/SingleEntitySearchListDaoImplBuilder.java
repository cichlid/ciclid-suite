package alpha114.tool.cichlid.autogenerationtool.src.builder;

import java.util.HashMap;
import java.util.Map;

import alpha114.tool.cichlid.autogenerationtool.Builder;
import alpha114.tool.cichlid.autogenerationtool.Constants;
import alpha114.tool.cichlid.autogenerationtool.Utils;
import alpha114.tool.cichlid.autogenerationtool.data.FrameData;
import alpha114.tool.cichlid.autogenerationtool.data.FrameItemData;
import alpha114.tool.cichlid.autogenerationtool.data.TableListItemData;

/**
 * @author kurisakisatoshi
 */
public class SingleEntitySearchListDaoImplBuilder implements Builder {

    private final String tableName;
    private final FrameData frameData;

    public SingleEntitySearchListDaoImplBuilder(FrameData frameData, String tableName) {
        this.frameData = frameData;
        this.tableName = tableName;
    }


    @Override
    public String build() {

        Map<String, Integer> map = new HashMap<String, Integer>();
        Map<FrameItemData, Integer> map2 = new HashMap<FrameItemData, Integer>();

        for (FrameItemData item : frameData.getItems()) {

            if (!item.getId().startsWith(FrameItemData.ItemType.SEARCH_CONDITION.type)) {
                continue;
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
        sb.append("package ").append(Constants.PRJ_DAO_PACKAGE).append(".impl;").append("\n");
        sb.append("\n");

        // import
        sb.append("import ").append(Constants.DAO_PACKAGE).append(".common.builder.SelectSqlBuilder;").append("\n");
        sb.append("import ").append(Constants.DAO_PACKAGE).append(".common.impl.AbstractSingleEntitySearchListDaoImpl;").append("\n");
        sb.append("\n");
        sb.append("import ").append(Constants.PRJ_ENTITY_PACKAGE).append(".").append(Utils.toUCC(tableName)).append(";").append("\n");
        sb.append("import ").append(Constants.PRJ_DTO_PACKAGE).append(".").append(frameData.getFrameId()).append("_SearchCondition;").append("\n");
        sb.append("\n");

        // class看板
        sb.append("/**").append("\n");
        sb.append(" * クラス名：").append(frameData.getFrameId()).append("_SearchListDaoImpl").append("\n");
        sb.append(" * 機能概要：").append(tableName).append("の検索クラスです。").append("\n");
        sb.append(" * 備考：").append("\n");
        sb.append(" * ").append("\n");
        sb.append(" */").append("\n");

        // class
        sb.append("public class ").append(frameData.getFrameId()).append("_SearchListDaoImpl extends AbstractSingleEntitySearchListDaoImpl<").append(Utils.toUCC(tableName)).append(", ").append(frameData.getFrameId()).append("_SearchCondition> {").append("\n");
        sb.append("    ").append("\n");
        sb.append("    // ★★自動生成のため本ファイルを直接修正しないこと★★").append("\n");

        sb.append("    ").append("\n");
        sb.append("    /**").append("\n");
        sb.append("     * {@inheritDoc}").append("\n");
        sb.append("     */").append("\n");
        sb.append("    @Override").append("\n");
        sb.append("    protected void addSearchCondition(").append(frameData.getFrameId()).append("_SearchCondition searchCondition, SelectSqlBuilder builder) {").append("\n");

        for (FrameItemData item : frameData.getItems()) {

            if (!item.getId().startsWith(FrameItemData.ItemType.SEARCH_CONDITION.type)) {
                continue;
            }

            if (Utils.isDBEmpty(item)) {
                continue;
            }

            if (item.isCalendar()) {
                if (item.getLabel().indexOf("(開始)") > 0) {
                    sb.append("        // ").append(getLabel_cal(item)).append("の検索条件").append("\n");
                    sb.append("        addDateCondition(").append(Utils.toUCC(tableName)).append(".COLUMN.").append(item.getColumnName().toUpperCase()).append(".getColumnName(), searchCondition.getCondition_").append(item.getTableName().toUpperCase()).append("___").append(item.getColumnName().toUpperCase()).append("_From(), searchCondition.getCondition_").append(item.getTableName().toUpperCase()).append("___").append(item.getColumnName().toUpperCase()).append("_To(), builder);").append("\n");
                }

            } else {
                String key = item.getTableName().toUpperCase() + "___" + item.getColumnName().toUpperCase();
                int i = map.get(key);
                if (i > 1) {
                    key += "_" + map2.get(item);
                }

                sb.append("        // ").append(item.getLabel()).append("の検索条件").append("\n");
                if (item.getLabel().equals("種別") && (item.getTableName().equals("") || item.getTableName().equals("-"))) {
                } else {
                    sb.append("        addCondition(").append(Utils.toUCC(tableName)).append(".COLUMN.").append(item.getColumnName().toUpperCase()).append(".getColumnName(), searchCondition.getCondition_").append(key).append("(), builder);").append("\n");
                }
            }
        }
        sb.append("    }").append("\n");
        sb.append("    ").append("\n");
        sb.append("    /**").append("\n");
        sb.append("     * {@inheritDoc}").append("\n");
        sb.append("     */").append("\n");
        sb.append("    @Override").append("\n");
        sb.append("    protected Class<").append(Utils.toUCC(tableName)).append("> entityClass() {").append("\n");
        sb.append("        return ").append(Utils.toUCC(tableName)).append(".class;").append("\n");
        sb.append("    }").append("\n");

        if (hasOrderBySentence()) {

            sb.append("    ").append("\n");
            sb.append("    /**").append("\n");
            sb.append("     * {@inheritDoc}").append("\n");
            sb.append("     */").append("\n");
            sb.append("    @Override").append("\n");
            sb.append("    protected void addOrderby(SelectSqlBuilder builder) {").append("\n");
            sb.append("        builder.add(\"ORDER\").add(\"BY\").add(\"").append(getOrderby()).append("\")");
            if (frameData.getTableDefaultSortOrder().equals("DESCEND")) {
                sb.append(".add(\"DESC\")");
            }
            sb.append(";").append("\n");
            sb.append("    }").append("\n");
        }

        sb.append("}").append("\n");

        return sb.toString();
    }

    private String getLabel_cal(FrameItemData item) {
        return Utils.getLabel_cal(frameData, item);
    }

    private boolean hasOrderBySentence() {
        // 日時のみ

        String sortItemId = frameData.getTableDefaultSortKey();

        for (FrameItemData item : frameData.getItems()) {

            if (item.getLabel().equals(sortItemId)) {
                return item.getLabel().endsWith("日時");
//                return !item.getLabel().startsWith("管理項目");
            }
        }

        return false;
    }

    private String getOrderby() {

        String itemName_ja = frameData.getTableDefaultSortKey();

        for (FrameItemData item : frameData.getItems()) {

            if (!item.isTable()) {
                continue;
            }

            for (TableListItemData clm : item.getTableColumns()) {

                if (!clm.isHeader()
//                        && clm.isSort()
                        && clm.getLabel().equals(itemName_ja)) {

                    if (clm.getLabel().startsWith("管理項目")) {
                        continue;
                    }

                    return clm.getColumnName().toUpperCase();
                }
            }
        }

        throw new NullPointerException(frameData.getFrameName() + ", " + itemName_ja);
    }
}
