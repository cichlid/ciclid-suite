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
public class SearchConditionInterfaceBuilder implements Builder {

    private final FrameData frameData;

    public SearchConditionInterfaceBuilder(FrameData frameData) {
        this.frameData = frameData;
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
        sb.append("package ").append(Constants.PRJ_BASE_PACKAGE).append(".dto;").append("\n");
        sb.append("\n");

        // import
        sb.append("import ").append(Constants.DTO_PACKAGE).append(".SearchCondition;").append("\n");
        sb.append("").append("\n");

        // class看板
        sb.append("/**").append("\n");
        sb.append(" * クラス名：").append(frameData.getFrameId()).append("_SearchCondition").append("\n");
        sb.append(" * 機能概要：").append(frameData.getFrameName()).append("の検索条件インターフェースです。").append("\n");
        sb.append(" * 備考：").append("\n");
        sb.append(" * ").append("\n");
        sb.append(" */").append("\n");

        // class
        sb.append("public interface ").append(frameData.getFrameId()).append("_SearchCondition extends SearchCondition {").append("\n");
        sb.append("    ").append("\n");
        sb.append("    // ★★自動生成のため本ファイルを直接修正しないこと★★").append("\n");

        for (FrameItemData item : frameData.getItems()) {

            if (!item.getId().startsWith(FrameItemData.ItemType.SEARCH_CONDITION.type)) {
                continue;
            }

            if (Utils.isDBEmpty(item)
                    && !(item.getLabel().equals("種別") && (item.getTableName().equals("") || item.getTableName().equals("-")))) {
                continue;
            }

            sb.append("    ").append("\n");
            sb.append("    /**").append("\n");
            sb.append("     * ").append(item.getLabel()).append("の検索条件を返します。").append("\n");
            sb.append("     * ").append("\n");
            sb.append("     * @return ").append(item.getLabel()).append("の検索条件").append("\n");
            sb.append("     */").append("\n");

            if (item.isCalendar()) {

                String fromTo = item.getLabel().indexOf("(開始)") > 0 ? "From" : "To";

                sb.append("    java.util.Date getCondition_").append(item.getTableName().toUpperCase()).append("___").append(item.getColumnName().toUpperCase()).append("_").append(fromTo).append("();").append("\n");

            } else {

                sb.append("    ");

                if (item.getLabel().equals("種別") && (item.getTableName().equals("") || item.getTableName().equals("-"))) {
                    sb.append("String getCondition_TYPE();").append("\n");

                } else {

                    String key = item.getTableName().toUpperCase() + "___" + item.getColumnName().toUpperCase();
                    int i = map.get(key);
                    if (i > 1) {
                        key += "_" + map2.get(item);
                    }

                    if (item.isPulldown() && !item.getInputDataType().equals("文字列")) {
                        sb.append("int");
                        sb.append(" getCondition_").append(key).append("();").append("\n");

                    } else {
                        sb.append("String");
                        sb.append(" getCondition_").append(key).append("();").append("\n");
                    }
                }
            }
        }

        sb.append("}").append("\n");

        return sb.toString();
    }

    public static final String[] date = Constants.date;
}
