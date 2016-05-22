package alpha114.tool.cichlid.autogenerationtool.src.builder;

import alpha114.tool.cichlid.autogenerationtool.Builder;
import alpha114.tool.cichlid.autogenerationtool.Constants;
import alpha114.tool.cichlid.autogenerationtool.data.FrameData;

/**
 * @author kurisakisatoshi
 */
public class ViewModelImplBuilder implements Builder {

    private final FrameData frameData;

    private final boolean emptyViewModel;

    public ViewModelImplBuilder(FrameData frameData) {
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
        sb.append("package ").append(Constants.PRJ_VIEW_MODE_PACKAGE).append(".impl;").append("\n");
        sb.append("\n");

        // import
        sb.append("import java.io.Serializable;").append("\n");
        sb.append("\n");

        if (emptyViewModel) {
            // 空のやつ
            sb.append("import ").append(Constants.PRJ_VIEW_MODE_PACKAGE).append(".").append(frameData.getFrameId()).append("_ViewModel;").append("\n");
            sb.append("\n");

        } else {

            if (frameData.isTableFlag()) {
                sb.append("import ").append(Constants.ENTITY_PACKAGE).append(".Entity;").append("\n");
            }
            if (frameData.isTableFlag()) {
                sb.append("import ").append(Constants.VIEW_MODEL_PACKAGE_TABLE).append(".TableViewModel;").append("\n");
            }
            if (frameData.isSearchFlag()) {
                sb.append("import ").append(Constants.VIEW_MODEL_PACKAGE_SEARCHCONDITION).append(".DefaultSearchConditionViewModel;").append("\n");
            }
            if (frameData.isSettiongFlag()) {
                sb.append("import ").append(Constants.VIEW_MODEL_PACKAGE_SETTING).append(".SettingDataViewModel;").append("\n");
            }

            sb.append("import ").append(Constants.PRJ_VIEW_MODE_PACKAGE).append(".").append(frameData.getFrameId()).append("_ViewModel;").append("\n");

            sb.append("\n");
        }

        // class看板
        sb.append("/**").append("\n");
        sb.append(" * クラス名：").append(frameData.getFrameId()).append("_ViewModelImpl").append("\n");
        sb.append(" * 機能概要：").append(frameData.getFrameName()).append("のViewModelクラスです。").append("\n");
        sb.append(" * 備考：").append("\n");
        sb.append(" * ").append("\n");
        sb.append(" */").append("\n");

        // class
        if (emptyViewModel) {
            // 空のやつ
            sb.append("public class ").append(frameData.getFrameId()).append("_ViewModelImpl implements ").append(frameData.getFrameId()).append("_ViewModel, Serializable {").append("\n");

            sb.append("    ").append("\n");
            sb.append("    // ★★自動生成のため本ファイルを直接修正しないこと★★").append("\n");
            sb.append("    ").append("\n");
            sb.append("    private static final long serialVersionUID = 1L;").append("\n");
            sb.append("}").append("\n");

        } else {

            sb.append("public class ").append(frameData.getFrameId()).append("_ViewModelImpl");

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

            sb.append(" implements ").append(frameData.getFrameId()).append("_ViewModel");
            if (frameData.isTableFlag() || frameData.isSettiongFlag()) {

                sb.append("<");

                if (frameData.isTableFlag()) {
                    sb.append("T");
                }

                if (frameData.isSettiongFlag()) {
                    if (frameData.isTableFlag()) {
                        sb.append(", ");
                    }
                    sb.append("S");
                }

                sb.append(">");
            }
            sb.append(", Serializable {").append("\n");

            sb.append("    ").append("\n");
            sb.append("    // ★★自動生成のため本ファイルを直接修正しないこと★★").append("\n");

            // メンバ変数のところ
            if (frameData.isSearchFlag()) {

                sb.append("    ").append("\n");
                sb.append("    /** 検索条件用のViewModel */").append("\n");
                sb.append("    private final DefaultSearchConditionViewModel searchConditionViewModel;").append("\n");
            }

            if (frameData.isTableFlag()) {

                sb.append("    ").append("\n");
                sb.append("    /** 検索結果＆テーブル用のViewModel */").append("\n");
                sb.append("    private final TableViewModel<T> tableViewModel;").append("\n");
            }

            if (frameData.isSettiongFlag()) {

                sb.append("    ").append("\n");
                sb.append("    /** 設定用のViewModel */").append("\n");
                sb.append("    private final S settingDataViewModel;").append("\n");
            }


            // コンストラクタのところ
            StringBuilder constr = new StringBuilder();

            constr.append("    ").append("\n");
            constr.append("    public ").append(frameData.getFrameId()).append("_ViewModelImpl(");

            // コンストラクタの引数
            if (frameData.isSearchFlag()) {
                constr.append("DefaultSearchConditionViewModel searchConditionViewModel");
            }
            if (frameData.isTableFlag()) {
                if (frameData.isSearchFlag()) {
                    constr.append(", ");
                }
                constr.append("TableViewModel<T> tableViewModel");
            }
            if (frameData.isSettiongFlag()) {
                if (frameData.isSearchFlag() || frameData.isTableFlag()) {
                    constr.append(", ");
                }
                constr.append("S settingDataViewModel");
            }
            constr.append(") {").append("\n");

            // コンストラクタのなかみ
            if (frameData.isSearchFlag()) {
                constr.append("        this.searchConditionViewModel = searchConditionViewModel;").append("\n");
            }
            if (frameData.isTableFlag()) {
                constr.append("        this.tableViewModel = tableViewModel;").append("\n");
            }
            if (frameData.isSettiongFlag()) {
                constr.append("        this.settingDataViewModel = settingDataViewModel;").append("\n");
            }
            constr.append("    }").append("\n");

            sb.append(constr.toString());


            // インタフェースのオーバライド

            if (frameData.isSearchFlag()) {

                sb.append("    ").append("\n");
                sb.append("    /**").append("\n");
                sb.append("     * {@inheritDoc}").append("\n");
                sb.append("     */").append("\n");
                sb.append("    @Override").append("\n");
                sb.append("    public DefaultSearchConditionViewModel getSearchConditionViewModel() {").append("\n");
                sb.append("        return searchConditionViewModel;").append("\n");
                sb.append("    }").append("\n");
            }
            if (frameData.isTableFlag()) {

                sb.append("    ").append("\n");
                sb.append("    /**").append("\n");
                sb.append("     * {@inheritDoc}").append("\n");
                sb.append("     */").append("\n");
                sb.append("    @Override").append("\n");
                sb.append("    public TableViewModel<T> getTableViewModel() {").append("\n");
                sb.append("        return tableViewModel;").append("\n");
                sb.append("    }").append("\n");
            }
            if (frameData.isSettiongFlag()) {

                sb.append("    ").append("\n");
                sb.append("    /**").append("\n");
                sb.append("     * {@inheritDoc}").append("\n");
                sb.append("     */").append("\n");
                sb.append("    @Override").append("\n");
                sb.append("    public S getSettingDataViewModel() {").append("\n");
                sb.append("        return settingDataViewModel;").append("\n");
                sb.append("    }").append("\n");
            }

            sb.append("    ").append("\n");
            sb.append("    private static final long serialVersionUID = 1L;").append("\n");
            sb.append("}").append("\n");
        }

        return sb.toString();
    }
}
