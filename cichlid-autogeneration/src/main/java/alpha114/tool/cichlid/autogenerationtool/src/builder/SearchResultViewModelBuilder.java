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
public class SearchResultViewModelBuilder implements Builder {

    private final FrameData frameData;

    public SearchResultViewModelBuilder(FrameData frameData) {
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
        sb.append("package ").append(Constants.PRJ_VIEW_MODEL_PACKAGE_SEARCHRESULT).append(";").append("\n");
        sb.append("\n");

        // import
        sb.append("import java.io.Serializable;").append("\n");
        sb.append("\n");
        sb.append("import ").append(Constants.ENTITY_PACKAGE).append(".Entity;").append("\n");
        sb.append("import ").append(Constants.VIEW_MODEL_PACKAGE_SEARCHRESULT).append(".SearchResultViewModel;").append("\n");
        sb.append("\n");

        // class看板
        sb.append("/**").append("\n");
        sb.append(" * クラス名：").append(frameData.getFrameId()).append("_SearchResultViewModel").append("\n");
        sb.append(" * 機能概要：").append(frameData.getFrameName()).append("の検索結果ViewModelインターフェースです。").append("\n");
        sb.append(" * 備考：").append("\n");
        sb.append(" * ").append("\n");
        sb.append(" */").append("\n");

        // class
        sb.append("public interface ").append(frameData.getFrameId()).append("_SearchResultViewModel<T extends Entity> extends SearchResultViewModel<T>, Serializable {").append("\n");

        sb.append("    ").append("\n");
        sb.append("    // ★★自動生成のため本ファイルを直接修正しないこと★★").append("\n");
        sb.append("    ").append("\n");

        for (FrameItemData item : frameData.getItems()) {

            if (!item.isTable()) {
                continue;
            }

            for (TableListItemData clm : item.getTableColumns()) {

                if (!clm.getId().startsWith(FrameItemData.ItemType.TABLE_COLUMN.type)) {
                    continue;
                }

                if (clm.isHeader()) {
                    continue;
                }

                if (clm.getLabel().equals("管理項目")) {
                    continue;
                }

                if (clm.isCheckbox()) {
                    continue;
                }

                if (clm.isRadioButton()) {
                    continue;
                }

                if (clm.isLinkText()) {
                    sb.append("    /**").append("\n");
                    sb.append("     * ").append(clm.getLabel()).append("がリンクの場合<code>true</code>を返します。<br>").append("\n");
                    sb.append("     * ").append("\n");
                    sb.append("     * @return ").append(clm.getLabel()).append("がリンクの場合<code>true</code>").append("\n");
                    sb.append("     */").append("\n");
                    sb.append("    boolean isLink_").append(clm.getId()).append("();").append("\n");
                    sb.append("    ").append("\n");
                }

                if (clm.isLink() || clm.isLinkText()) {
                    sb.append("    /**").append("\n");
                    sb.append("     * ").append(clm.getLabel()).append("(リンク)のパラメータを返します。<br>").append("\n");
                    if (!Utils.isDBEmpty(clm)) {
                        sb.append("     * 対応テーブル：{@link " + Constants.ENTITY_PACKAGE + "." + Utils.toUCC(clm.getTableName()) + " " + clm.getTableName() + "} <br>").append("\n");
                        sb.append("     * 対応カラム：{@link " + Constants.ENTITY_PACKAGE + "." + Utils.toUCC(clm.getTableName()) + ".COLUMN#" + clm.getColumnName().toUpperCase() + " " + clm.getColumnName().toUpperCase() + "} <br>").append("\n");
                    }
                    sb.append("     * ").append("\n");
                    sb.append("     * @return ").append(clm.getLabel()).append("(リンク)のパラメータ").append("\n");
                    sb.append("     */").append("\n");
                    sb.append("    String get").append(clm.getId()).append("_LinkParamerter();").append("\n");
                    sb.append("    ").append("\n");
                }

                if (clm.isButton()) {
                    sb.append("    /**").append("\n");
                    sb.append("     * ").append(clm.getLabel()).append("(ボタン)のパラメータを返します。<br>").append("\n");
                    if (!Utils.isDBEmpty(clm)) {
                        sb.append("     * 対応テーブル：{@link " + Constants.ENTITY_PACKAGE + "." + Utils.toUCC(clm.getTableName()) + " " + clm.getTableName() + "} <br>").append("\n");
                        sb.append("     * 対応カラム：{@link " + Constants.ENTITY_PACKAGE + "." + Utils.toUCC(clm.getTableName()) + ".COLUMN#" + clm.getColumnName().toUpperCase() + " " + clm.getColumnName().toUpperCase() + "} <br>").append("\n");
                    }
                    sb.append("     * ").append("\n");
                    sb.append("     * @return ").append(clm.getLabel()).append("(ボタン)のパラメータ").append("\n");
                    sb.append("     */").append("\n");
                    sb.append("    String get").append(clm.getId()).append("_ButtonParamerter();").append("\n");
                    sb.append("    ").append("\n");
                }


                if (clm.isPulldown()) {
                    sb.append("    /**").append("\n");
                    sb.append("     * ").append(clm.getLabel()).append("がプルダウンの場合<code>true</code>を返します。<br>").append("\n");
                    sb.append("     * ").append("\n");
                    sb.append("     * @return ").append(clm.getLabel()).append("がプルダウンの場合<code>true</code>").append("\n");
                    sb.append("     */").append("\n");
                    sb.append("    boolean isPulldown_").append(clm.getId()).append("();").append("\n");
                    sb.append("    ").append("\n");
                }

                if (clm.isText()) {
                    sb.append("    /**").append("\n");
                    sb.append("     * ").append(clm.getLabel()).append("がテキスト入力の場合<code>true</code>を返します。<br>").append("\n");
                    sb.append("     * ").append("\n");
                    sb.append("     * @return ").append(clm.getLabel()).append("がテキスト入力の場合<code>true</code>").append("\n");
                    sb.append("     */").append("\n");
                    sb.append("    boolean isInput_").append(clm.getId()).append("();").append("\n");
                    sb.append("    ").append("\n");
                }

                sb.append("    /**").append("\n");
                sb.append("     * ").append(clm.getLabel()).append("の表示内容を返します。<br>").append("\n");
                if (!Utils.isDBEmpty(clm)) {
                    sb.append("     * 対応テーブル：{@link " + Constants.ENTITY_PACKAGE + "." + Utils.toUCC(clm.getTableName()) + " " + clm.getTableName() + "} <br>").append("\n");
                    sb.append("     * 対応カラム：{@link " + Constants.ENTITY_PACKAGE + "." + Utils.toUCC(clm.getTableName()) + ".COLUMN#" + clm.getColumnName().toUpperCase() + " " + clm.getColumnName().toUpperCase() + "} <br>").append("\n");
                }
                sb.append("     * ").append("\n");
                sb.append("     * @return ").append(clm.getLabel()).append("の表示内容").append("\n");
                sb.append("     */").append("\n");
                sb.append("    String get").append(clm.getId()).append("();").append("\n");
                sb.append("    ").append("\n");
            }
        }

        sb.append("}").append("\n");

        return sb.toString();
    }
}
