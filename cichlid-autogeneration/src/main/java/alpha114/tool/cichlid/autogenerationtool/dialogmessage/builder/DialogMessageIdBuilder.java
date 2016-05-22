package alpha114.tool.cichlid.autogenerationtool.dialogmessage.builder;

import java.util.List;

import alpha114.tool.cichlid.autogenerationtool.Builder;
import alpha114.tool.cichlid.autogenerationtool.Constants;
import alpha114.tool.cichlid.autogenerationtool.data.DialogMessageData;

/**
 * @author kurisakisatoshi
 */
public class DialogMessageIdBuilder implements Builder {

    private final List<DialogMessageData> msgs;

    public DialogMessageIdBuilder(List<DialogMessageData> msgs) {
        this.msgs = msgs;
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
        sb.append(" * 備考    ：メッセージ一覧から自動生成").append("\n");
        sb.append(" * 更新履歴：").append("\n");
        sb.append(" *   日付       更新者       内容").append("\n");
        sb.append(" *   ").append(Constants.DATE_CREATED).append("   kurisakisatoshi   新規作成").append("\n");
        sb.append(" * ").append("\n");
        sb.append(" */").append("\n");

        // package
        sb.append("package ").append(Constants.PRJ_WEB_MESSAGE_ID_PACKAGE).append(";").append("\n");
        sb.append("\n");

        // class看板
        sb.append("/**").append("\n");
        sb.append(" * クラス名：DialogMessageKey").append("\n");
        sb.append(" * 機能概要：ダイアログに表示するメッセージのキーを定義したインタフェースです。").append("\n");
        sb.append(" * 備考：メッセージ一覧から自動生成").append("\n");
        sb.append(" * ").append("\n");
        sb.append(" */").append("\n");

        // class
        sb.append("public final class DialogMessageKey {").append("\n");
        sb.append("    ").append("\n");
        sb.append("    // ★★自動生成のため本ファイルを直接修正しないこと★★").append("\n");

        String fName = "";
        for (DialogMessageData data : msgs) {

            if (fName.equals("") || !fName.equals(data.frameName)) {

                fName = data.frameName;
            }

            sb.append("    ").append("\n");
            sb.append("    /**").append("\n");
            sb.append("     * ").append(fName).append("<br>").append("\n");
            sb.append("     * ").append(data.message.replaceAll("\\\\\\\\r\\\\\\\\n", "<br>")).append("\n");
            sb.append("     */").append("\n");
            sb.append("    public static final String ").append(data.frameId.replaceAll("-", "_")).append("_").append(data.index).append(" = \"").append(data.frameId).append("_").append(data.index).append("\";").append("\n");

        }

        sb.append("    ").append("\n");
        sb.append("    /**").append("\n");
        sb.append("     * コンストラクタ。").append("\n");
        sb.append("     */").append("\n");
        sb.append("    private DialogMessageKey() {").append("\n");
        sb.append("        // インスタンス化不可").append("\n");
        sb.append("    }").append("\n");
        sb.append("}").append("\n");

        return sb.toString();
    }
}
