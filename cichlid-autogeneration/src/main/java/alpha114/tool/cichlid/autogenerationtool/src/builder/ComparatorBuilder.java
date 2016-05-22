package alpha114.tool.cichlid.autogenerationtool.src.builder;

import java.io.IOException;

import org.apache.commons.io.output.StringBuilderWriter;

import alpha114.tool.cichlid.autogenerationtool.Builder;
import alpha114.tool.cichlid.autogenerationtool.Constants;
import alpha114.tool.cichlid.autogenerationtool.Utils;
import alpha114.tool.cichlid.autogenerationtool.data.ColumnData;
import alpha114.tool.cichlid.autogenerationtool.data.TableData;

/**
 * @author kurisakisatoshi
 */
public class ComparatorBuilder implements Builder {

    private final TableData tableData;
    private final ColumnData column;

    public ComparatorBuilder(TableData tableData, ColumnData column) {
        this.tableData = tableData;
        this.column = column;
    }

    @Override
    public String build() {

        StringBuilderWriter w = new StringBuilderWriter();

        try {
            createComparator(w);

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        return w.toString();
    }

    private void createComparator(StringBuilderWriter o) throws IOException {

        String tableName = Utils.toUCC(tableData.getName());

        String columnUCC = Utils.toUCC(column.getName());

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
        buffer.append("package ").append(Constants.PRJ_COMPARATOR_PACKAGE).append(".").append(tableData.getName().toLowerCase()).append(";\n");
        buffer.append("").append("\n");

        // import
        buffer.append("import ").append(Constants.COMPARATOR_PACKAGE).append(".EntityComparator;").append("\n");
        buffer.append("import ").append(Constants.PRJ_ENTITY_PACKAGE).append(".").append(tableName).append(";\n");
        buffer.append("").append("\n");

        // class看板
        buffer.append("/**").append("\n");
        buffer.append(" * ").append(tableData.getTableName()).append("の").append(column.getColumnName()).append("で比較するコンパレータクラスです。").append("\n");
        buffer.append(" * ").append("\n");
        buffer.append(" * @author  kurisakisatoshi").append("\n");
        buffer.append(" * @version $Revision").append(": $").append("\n");
        buffer.append(" */").append("\n");

        // class
        buffer.append("public final class ").append(columnUCC).append("Comparator extends EntityComparator<").append(tableName).append("> {").append("\n");
        buffer.append("    ").append("").append("\n");
        buffer.append("    ").append("// ★★自動生成のため本ファイルを直接修正しないこと★★").append("\n");
        buffer.append("    ").append("\n");
        buffer.append("    ").append("/**").append("\n");
        buffer.append("    ").append(" * {@inheritDoc}").append("\n");
        buffer.append("    ").append(" */").append("\n");
        buffer.append("    ").append("@Override").append("\n");
        buffer.append("    ").append("public int compare(").append(tableName).append(" src, ").append(tableName).append(" dest) {").append("\n");
        buffer.append("    ").append("    ").append("\n");

        switch (column.dataType) {
        case CHAR:
        case VARCHAR:
        case VARCHAR2:
        case CLOB:
        case TEXT:
            buffer.append("    ").append("    ").append("return compareString(src.get").append(columnUCC).append("(), dest.get").append(columnUCC).append("());").append("\n");
            break;

        case DATE:
        case TIMESTAMP:
            buffer.append("    ").append("    ").append("long srcVal = src.get").append(columnUCC).append("() == null ? 0 : src.get").append(columnUCC).append("().getTime();").append("\n");
            buffer.append("    ").append("    ").append("long destVal = dest.get").append(columnUCC).append("() == null ? 0 : dest.get").append(columnUCC).append("().getTime();").append("\n");
            buffer.append("    ").append("    ").append("\n");
            buffer.append("    ").append("    ").append("return srcVal < destVal ? -1 : (srcVal == destVal ? 0 : 1);").append("\n");
            break;

        case BIGINT:
            buffer.append("    ").append("    ").append("long srcVal = src.get").append(columnUCC).append("();").append("\n");
            buffer.append("    ").append("    ").append("long destVal = dest.get").append(columnUCC).append("();").append("\n");
            buffer.append("    ").append("    ").append("\n");
            buffer.append("    ").append("    ").append("return srcVal < destVal ? -1 : (srcVal == destVal ? 0 : 1);").append("\n");
            break;

        default:
            buffer.append("    ").append("    ").append("int srcVal = src.get").append(columnUCC).append("();").append("\n");
            buffer.append("    ").append("    ").append("int destVal = dest.get").append(columnUCC).append("();").append("\n");
            buffer.append("    ").append("    ").append("\n");
            buffer.append("    ").append("    ").append("return srcVal < destVal ? -1 : (srcVal == destVal ? 0 : 1);").append("\n");
        }

        buffer.append("    ").append("}").append("\n");
        buffer.append("    ").append("\n");
        buffer.append("    ").append("\n");
        buffer.append("    ").append("\n");
        buffer.append("    ").append("/** シリアル番号 */").append("\n");
        buffer.append("    ").append("private static final long serialVersionUID = 1L;").append("\n");
        buffer.append("}").append("\n");

        o.write(buffer.toString());

        o.flush();

        System.out.println(tableName+" "+columnUCC+" comparator created.");
    }
}

