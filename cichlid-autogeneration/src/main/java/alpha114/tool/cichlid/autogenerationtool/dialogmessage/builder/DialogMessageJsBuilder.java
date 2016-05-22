package alpha114.tool.cichlid.autogenerationtool.dialogmessage.builder;

import java.util.ArrayList;
import java.util.List;

import alpha114.tool.cichlid.autogenerationtool.Builder;
import alpha114.tool.cichlid.autogenerationtool.Constants;
import alpha114.tool.cichlid.autogenerationtool.data.DialogMessageData;

/**
 * @author kurisakisatoshi
 */
public class DialogMessageJsBuilder implements Builder {

    private final List<DialogMessageData> msgs;

    public DialogMessageJsBuilder(List<DialogMessageData> msgs) {
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
        sb.append("\n");
        sb.append("/**").append("\n");
        sb.append(" * ★★自動生成のため本ファイルを直接修正しないこと★★").append("\n");
        sb.append(" */").append("\n");
        sb.append("\n");
//        sb.append("/*@cc_on _d=document;eval('var document=_d')@*/").append("\n");
//        sb.append("\n");

        for (DialogMessageData data : msgs) {

            if (data.isConfirm()) {
                sb.append("/**").append("\n");
                sb.append(" * 確認ダイアログ").append("\n");
                sb.append(" * ").append(data.message.replaceAll("\\\\\\\\r\\\\\\\\n", "\\\\n")).append("\n");
                sb.append(" */").append("\n");
                String[] param = split(data.message);
                if (param.length == 1) {
                    sb.append("var confirm_").append(data.frameId.replaceAll("-", "_")).append("_").append(data.index).append(" = function() {").append("\n");
                    sb.append("    return confirm('").append(data.message.replaceAll("\\\\\\\\r\\\\\\\\n", "\\\\n")).append("');").append("\n");

                } else {
                    sb.append("var confirm_").append(data.frameId.replaceAll("-", "_")).append("_").append(data.index).append(" = function(");
                    for (int i = 0; i < param.length - 1; i++) {
                        if (i != 0) {
                            sb.append(", ");
                        }
                        sb.append("param" + i);
                    }
                    sb.append(") {").append("\n");
                    sb.append("    return confirm('");
                    for (int i = 0; i < param.length; i++) {
                        if (i != 0) {
                            sb.append(" + '");
                        }
                        sb.append(param[i]);
                        if (i != param.length - 1) {
                            sb.append("' + param" + i);
                        }
                    }
                    sb.append("');").append("\n");
                }
                sb.append("};").append("\n");
                sb.append("\n");

            } else {
                sb.append("/**").append("\n");
                sb.append(" * 警告ダイアログ").append("\n");
                sb.append(" * ").append(data.message.replaceAll("\\\\\\\\r\\\\\\\\n", "\\\\n")).append("\n");
                sb.append(" */").append("\n");

                String[] param = split(data.message);
                if (param.length == 1) {
                    sb.append("var alert_").append(data.frameId.replaceAll("-", "_")).append("_").append(data.index).append(" = function() {").append("\n");
                    sb.append("    alert('").append(data.message.replaceAll("\\\\\\\\r\\\\\\\\n", "\\\\n")).append("');").append("\n");

                } else {
                    sb.append("var alert_").append(data.frameId.replaceAll("-", "_")).append("_").append(data.index).append(" = function(");
                    for (int i = 0; i < param.length - 1; i++) {
                        if (i != 0) {
                            sb.append(", ");
                        }
                        sb.append("param" + i);
                    }
                    sb.append(") {").append("\n");
                    sb.append("    alert('");
                    for (int i = 0; i < param.length; i++) {
                        if (i != 0) {
                            sb.append(" + '");
                        }
                        sb.append(param[i]);
                        if (i != param.length - 1) {
                            sb.append("' + param" + i);
                        }
                    }
                    sb.append("');").append("\n");
                }
                sb.append("};").append("\n");
                sb.append("\n");
            }
        }

        return sb.toString();
    }

    private static String[] split(String str) {

        List<String> s = new ArrayList<String>();
        int i = 0;
        int idx = 0;

        while ((idx = str.indexOf("{" + i + "}")) != -1) {

            s.add(str.substring(0, idx));

            idx = idx + 3;

            str = str.substring(idx, str.length());

            i++;
        }

        s.add(str);

        return s.toArray(new String[]{});
    }
}
