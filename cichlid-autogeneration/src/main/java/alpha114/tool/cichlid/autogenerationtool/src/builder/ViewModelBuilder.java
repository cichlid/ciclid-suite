package alpha114.tool.cichlid.autogenerationtool.src.builder;

import alpha114.tool.cichlid.autogenerationtool.Builder;
import alpha114.tool.cichlid.autogenerationtool.Constants;
import alpha114.tool.cichlid.autogenerationtool.data.FrameData;

/**
 * @author kurisakisatoshi
 */
public class ViewModelBuilder implements Builder {

    private final FrameData frameData;

    private final boolean emptyViewModel;

    public ViewModelBuilder(FrameData frameData) {
        this.frameData = frameData;
        this.emptyViewModel = !frameData.isTableFlag() && !frameData.isSearchFlag() && !frameData.isSettiongFlag();
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
        sb.append("package ").append(Constants.PRJ_VIEW_MODE_PACKAGE).append(";").append("\n");
        sb.append("\n");

        // import
        if (!emptyViewModel) {

            if (frameData.isTableFlag()) {
                sb.append("import ").append(Constants.ENTITY_PACKAGE).append(".Entity;").append("\n");
            }
            if (frameData.isSearchFlag()) {
                sb.append("import ").append(Constants.VIEW_MODEL_PACKAGE).append(".SearchConditionViewModelProvider;").append("\n");
            }
            if (frameData.isSettiongFlag()) {
                sb.append("import ").append(Constants.VIEW_MODEL_PACKAGE).append(".SettingDataViewModelProvider;").append("\n");
            }
            if (frameData.isTableFlag()) {
                sb.append("import ").append(Constants.VIEW_MODEL_PACKAGE).append(".TableViewModelProvider;").append("\n");
            }

            if (frameData.isSearchFlag()) {
                sb.append("import ").append(Constants.VIEW_MODEL_PACKAGE_SEARCHCONDITION).append(".DefaultSearchConditionViewModel;").append("\n");
            }
            if (frameData.isSettiongFlag()) {
                sb.append("import ").append(Constants.VIEW_MODEL_PACKAGE_SETTING).append(".SettingDataViewModel;").append("\n");
            }
            if (frameData.isTableFlag()) {
                sb.append("import ").append(Constants.VIEW_MODEL_PACKAGE_TABLE).append(".TableViewModel;").append("\n");
            }
            sb.append("\n");
        }

        // class看板
        sb.append("/**").append("\n");
        sb.append(" * クラス名：").append(frameData.getFrameId()).append("_ViewModel").append("\n");
        sb.append(" * 機能概要：").append(frameData.getFrameName()).append("のViewModelインターフェースです。").append("\n");
        sb.append(" * 備考：").append("\n");
        sb.append(" * ").append("\n");
        sb.append(" */").append("\n");

        // class
        if (!emptyViewModel) {

            sb.append("public interface ").append(frameData.getFrameId()).append("_ViewModel");

            if (frameData.isTableFlag() || frameData.isSettiongFlag()) {
                sb.append("<");

                if (frameData.isTableFlag()) {
                    sb.append("T extends Entity");
                }

                if (frameData.isSettiongFlag()) {
                    if (frameData.isTableFlag()) {
                        sb.append(", ");
                    }
                    sb.append("S extends SettingDataViewModel");
                }

                sb.append(">");
            }

            sb.append("\n");
            sb.append("        extends ");

            StringBuilder extendsSb = new StringBuilder();

            if (frameData.isSearchFlag()) {
                extendsSb.append("SearchConditionViewModelProvider<DefaultSearchConditionViewModel>");
            }
            if (frameData.isTableFlag()) {
                if (extendsSb.length() > 0) {
                    extendsSb.append(",").append("\n");
                    extendsSb.append("                ");
                }
                extendsSb.append("TableViewModelProvider<T>");
            }
            if (frameData.isSettiongFlag()) {
                if (extendsSb.length() > 0) {
                    extendsSb.append(",").append("\n");
                    extendsSb.append("                ");
                }
                extendsSb.append("SettingDataViewModelProvider<S>");
            }

            extendsSb.append(" {").append("\n");
            sb.append(extendsSb);

        } else {
            sb.append("public interface ").append(frameData.getFrameId()).append("_ViewModel {").append("\n");
        }

        sb.append("    ").append("\n");
        sb.append("    // ★★自動生成のため本ファイルを直接修正しないこと★★").append("\n");

        if (frameData.isSearchFlag()) {

            sb.append("    ").append("\n");
            sb.append("    /**").append("\n");
            sb.append("     * 検索条件用のViewModelを返します。").append("\n");
            sb.append("     * ").append("\n");
            sb.append("     * @return 検索条件用のViewModel").append("\n");
            sb.append("     */").append("\n");
            sb.append("    @Override").append("\n");
            sb.append("    DefaultSearchConditionViewModel getSearchConditionViewModel();").append("\n");
        }

        if (frameData.isTableFlag()) {

            sb.append("    ").append("\n");
            sb.append("    /**").append("\n");
            sb.append("     * 検索結果＆テーブル用のViewModelを返します。").append("\n");
            sb.append("     * ").append("\n");
            sb.append("     * @return 検索結果＆テーブル用のViewModel").append("\n");
            sb.append("     */").append("\n");
            sb.append("    @Override").append("\n");
            sb.append("    TableViewModel<T> getTableViewModel();").append("\n");

        }

        if (frameData.isSettiongFlag()) {

            sb.append("    ").append("\n");
            sb.append("    /**").append("\n");
            sb.append("     * 設定用のViewModelを返します。").append("\n");
            sb.append("     * ").append("\n");
            sb.append("     * @return 設定用のViewModel").append("\n");
            sb.append("     */").append("\n");
            sb.append("    @Override").append("\n");
            sb.append("    S getSettingDataViewModel();").append("\n");

        }

        sb.append("}").append("\n");

        return sb.toString();
    }
}
