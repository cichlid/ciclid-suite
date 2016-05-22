package alpha114.tool.cichlid.autogenerationtool.src.builder;

import java.io.IOException;
import java.io.Writer;

import org.apache.commons.io.output.StringBuilderWriter;

import alpha114.tool.cichlid.autogenerationtool.Builder;
import alpha114.tool.cichlid.autogenerationtool.Constants;
import alpha114.tool.cichlid.autogenerationtool.Utils;
import alpha114.tool.cichlid.autogenerationtool.data.TableData;

/**
 * @author kurisakisatoshi
 */
public class DaoBuilder implements Builder {

    private final TableData tableData;

    public DaoBuilder(TableData tableData) {
        this.tableData = tableData;
    }

    @Override
    public String build() {

        StringBuilderWriter w = new StringBuilderWriter();

        try {
            createDao(w);

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        return w.toString();
    }

    private void createDao(Writer o) throws IOException {

        boolean insert = Utils.isC(tableData);    //!StringUtils.isEmpty(tableData.getInsertTrigger()) && !tableData.getInsertTrigger().equals("なし");
        boolean update = Utils.isU(tableData);    //!StringUtils.isEmpty(tableData.getUpdateTrigger()) && !tableData.getUpdateTrigger().equals("なし");
        boolean delete = Utils.isD(tableData);    //!StringUtils.isEmpty(tableData.getDeleteTrigger()) && !tableData.getDeleteTrigger().equals("なし");

        String tableName = Utils.toUCC(tableData.getName());

        // 看板のところ
        StringBuilder buffer = new StringBuilder();
        buffer.append("/* ").append("\n");
        buffer.append(" * ").append(Constants.COPYRIGHT).append("\n");
        buffer.append(" * ").append("\n");
        buffer.append(" * $Id").append(": $").append("\n");
        buffer.append(" * ").append("\n");
        buffer.append(" * 備考    ：DB設計書から自動生成").append("\n");
        buffer.append(" * 更新履歴：").append("\n");
        buffer.append(" *   日付         更新者            内容").append("\n");
        buffer.append(" *   ").append(Constants.DATE_CREATED).append("   kurisakisatoshi   新規作成").append("\n");
        buffer.append(" * ").append("\n");
        buffer.append(" */").append("\n");
        buffer.append("package ").append(Constants.PRJ_DAO_CUD_PACKAGE).append(".").append(tableData.getName().toLowerCase()).append(";\n");
        buffer.append("").append("\n");


        // import
        buffer.append("import ").append(Constants.DAO_PACKAGE).append(".Dao;").append("\n");
        if (insert) {
            buffer.append("import ").append(Constants.DAO_PACKAGE).append(".common.Create;").append("\n");
        }
        if (delete) {
            buffer.append("import ").append(Constants.DAO_PACKAGE).append(".common.Delete;").append("\n");
        }
        if (update) {
            buffer.append("import ").append(Constants.DAO_PACKAGE).append(".common.Update;").append("\n");
        }
        buffer.append("import ").append(Constants.PRJ_ENTITY_PACKAGE).append(".").append(tableName).append(";\n");
        buffer.append("").append("\n");


        // class看板
        buffer.append("/**").append("\n");
        buffer.append(" * <p>").append(tableData.getTableName()).append("(").append(tableData.getName()).append(")への").append("\n");
        if (insert) {
            buffer.append(" * ").append("<li>Create</li>\n");
        }
        if (update) {
            buffer.append(" * ").append("<li>Update</li>\n");
        }
        if (delete) {
            buffer.append(" * ").append("<li>Delete</li>\n");
        }
        buffer.append(" * ").append("をサポートします。\n");
        buffer.append(" * ").append("\n");
        buffer.append(" * ").append("<p>※注意）トランザクションの管理は呼び元で行うこと\n");
        buffer.append(" * ").append("\n");
        buffer.append(" * ").append("@see ").append(Constants.DAO_PACKAGE).append(".Dao\n");
        if (insert) {
            buffer.append(" * ").append("@see ").append(Constants.DAO_PACKAGE).append(".common.Create\n");
        }
        if (update) {
            buffer.append(" * ").append("@see ").append(Constants.DAO_PACKAGE).append(".common.Update\n");
        }
        if (delete) {
            buffer.append(" * ").append("@see ").append(Constants.DAO_PACKAGE).append(".common.Delete\n");
        }
        buffer.append(" * @author  kurisakisatoshi").append("\n");
        buffer.append(" * @version $Revision").append(": $").append("\n");
        buffer.append(" */").append("\n");

        // class
        buffer.append("public interface ").append(tableName).append("Dao extends Dao, ");
        if (insert) {
            buffer.append("Create<").append(tableName).append(">");
            if (update || delete) buffer.append(", ");
        }
        if (update) {
            buffer.append("Update<").append(tableName).append(">");
            if (delete) buffer.append(", ");
        }
        if (delete) {
            buffer.append("Delete<").append(tableName).append(">");
        }
        buffer.append(" {").append("\n");
        buffer.append("    ").append("").append("\n");
        buffer.append("    ").append("// ★★自動生成のため本ファイルを直接修正しないこと★★").append("\n");
        buffer.append("    ").append("\n");
        buffer.append("}").append("\n");

        o.write(buffer.toString());
        o.flush();
    }
}
