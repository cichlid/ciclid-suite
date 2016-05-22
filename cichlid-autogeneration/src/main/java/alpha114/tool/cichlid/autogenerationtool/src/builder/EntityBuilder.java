package alpha114.tool.cichlid.autogenerationtool.src.builder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.io.output.StringBuilderWriter;

import alpha114.tool.cichlid.autogenerationtool.Builder;
import alpha114.tool.cichlid.autogenerationtool.Constants;
import alpha114.tool.cichlid.autogenerationtool.Utils;
import alpha114.tool.cichlid.autogenerationtool.data.ColumnData;
import alpha114.tool.cichlid.autogenerationtool.data.DBColumnDataTypeEnum;
import alpha114.tool.cichlid.autogenerationtool.data.TableData;

/**
 * @author kurisakisatoshi
 */
public class EntityBuilder implements Builder {

    private final String instanceName;
    private final TableData tableData;

    public EntityBuilder(String instanceName, TableData tableData) {
        this.instanceName = instanceName;
        this.tableData = tableData;
    }


    @Override
    public String build() {

        StringBuilderWriter w = new StringBuilderWriter();

        try {
            createEntity(w);

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        return w.toString();
    }

    private void createEntity(StringBuilderWriter o) throws IOException {

        String className = Utils.toUCC(tableData.getName());

        // 看板のところ
        StringBuilder header = new StringBuilder();
        header.append("/* ").append("\n");
        header.append(" * ").append(Constants.COPYRIGHT).append("\n");
        header.append(" * ").append("\n");
        header.append(" * $Id").append(": $").append("\n");
        header.append(" * ").append("\n");
        header.append(" * 備考    ：DB設計書から自動生成").append("\n");
        header.append(" * 更新履歴：").append("\n");
        header.append(" *   日付         更新者            内容").append("\n");
        header.append(" *   ").append(Constants.DATE_CREATED).append("   kurisakisatoshi   新規作成").append("\n");
        header.append(" * ").append("\n");
        header.append(" */").append("\n");
        header.append("package ").append(Constants.PRJ_ENTITY_PACKAGE).append(";").append("\n");
        header.append("").append("\n");

        // import
        StringBuilder imprt = new StringBuilder();
        imprt.append("import java.io.Serializable;").append("\n");
        imprt.append("import java.sql.Types;").append("\n");
        imprt.append("import java.util.ArrayList;").append("\n");
        imprt.append("import java.util.List;").append("\n");
        imprt.append("").append("\n");
        imprt.append("import ").append(Constants.ENTITY_PACKAGE).append(".Column;").append("\n");

        // class看板
        StringBuilder classHeader = new StringBuilder();
        classHeader.append("").append("\n");
        classHeader.append("/**").append("\n");
        classHeader.append(" * ").append(tableData.getTableName()).append("で保持する情報のエンティティクラス。").append("\n");
        classHeader.append(" * ").append("\n");
        classHeader.append(" * @see     java.io.Serializable").append("\n");
        classHeader.append(" * @see     ").append(Constants.PRJ_ENTITY_PACKAGE).append(".").append(instanceName.toUpperCase()).append("\n");
        classHeader.append(" * @author  kurisakisatoshi").append("\n");
        classHeader.append(" * @version $Revision").append(": $").append("\n");
        classHeader.append(" */").append("\n");
        classHeader.append("public class ").append(className).append(" implements ").append(instanceName.toUpperCase()).append(", Serializable {").append("\n");
        classHeader.append("    ").append("").append("\n");
        classHeader.append("    ").append("// ★★自動生成のため本ファイルを直接修正しないこと★★").append("\n");
        classHeader.append("    ").append("").append("\n");
        classHeader.append("    ").append("/** シリアルバージョン */").append("\n");
        classHeader.append("    ").append("private static final long serialVersionUID = 1L;").append("\n");

        // カラム定義のenum
        StringBuilder clm = new StringBuilder();
        clm.append("    ").append("\n");
        clm.append("    ").append("/**").append("\n");
        clm.append("    ").append(" * テーブルカラム定義の列挙型。").append("\n");
        clm.append("    ").append(" * <p>カラム名とカラムの型を定義する。").append("\n");
        clm.append("    ").append(" * <p>カラムの型は{@link java.sql.Types}で定義されたJDBC型コード(int値)。").append("\n");
        clm.append("    ").append(" * ").append("\n");
        clm.append("    ").append(" * @see ").append(Constants.ENTITY_PACKAGE).append(".Column").append("\n");
        clm.append("    ").append(" * @see java.sql.Types").append("\n");
        clm.append("    ").append(" */").append("\n");
        clm.append("    ").append("public enum COLUMN implements Column {").append("\n");
        for (ColumnData column : tableData.getColumns()) {
            clm.append("    ").append("    ").append("/** ").append(column.getColumnName()).append(" */").append("\n");
            clm.append("    ").append("    ").append(column.getName());
            clm.append("(").append(column.getDataType().getColumnType()).append(", ");
            clm.append(column.isNotNull()).append(", ");

            if (column.getDataType().equals(DBColumnDataTypeEnum.CLOB)) {
                clm.append("Integer.MAX_VALUE /* CLOBはintの最大値を定義しておく(実際は4Gバイトまで可) */").append(", ");
            } else {
                clm.append(column.getBytes()).append(", ");
            }

            if (column.getDataType().equals(DBColumnDataTypeEnum.VARCHAR2)
                || column.getDataType().equals(DBColumnDataTypeEnum.VARCHAR)
                || column.getDataType().equals(DBColumnDataTypeEnum.CHAR)
                || column.getDataType().equals(DBColumnDataTypeEnum.TEXT)) {
                clm.append(column.isNotNull()).append(", ");
                clm.append("\"" + column.getDefaultValue() + "\"").append(", ");

            } else {
                clm.append(!column.getDefaultValue().equals("")).append(", ");
                clm.append(column.getDefaultValue().equals("") ? "null" : "\"" + column.getDefaultValue() + "\"").append(", ");
            }
            clm.append(column.isUpdate()).append("), ").append("\n");
        }
        clm.append("    ").append("    ").append(";").append("\n");
        clm.append("    ").append("    ").append("").append("\n");
        clm.append("    ").append("    ").append("/** カラムの型 */").append("\n");
        clm.append("    ").append("    ").append("private final int columnType;").append("\n");
        clm.append("    ").append("    ").append("").append("\n");
        clm.append("    ").append("    ").append("/** NOT NULL制約の有無 */").append("\n");
        clm.append("    ").append("    ").append("private final boolean notNull;").append("\n");
        clm.append("    ").append("    ").append("").append("\n");
        clm.append("    ").append("    ").append("/** カラムサイズの最大値(バイト) */").append("\n");
        clm.append("    ").append("    ").append("private final int maxLength;").append("\n");
        clm.append("    ").append("    ").append("").append("\n");
        clm.append("    ").append("    ").append("/** DEFAULT制約の有無 */").append("\n");
        clm.append("    ").append("    ").append("private final boolean hasDefault;").append("\n");
        clm.append("    ").append("    ").append("").append("\n");
        clm.append("    ").append("    ").append("/** DEFAULT値 */").append("\n");
        clm.append("    ").append("    ").append("private final String defaultValue;").append("\n");
        clm.append("    ").append("    ").append("").append("\n");
        clm.append("    ").append("    ").append("/** 更新可否 */").append("\n");
        clm.append("    ").append("    ").append("private final boolean updateable;").append("\n");
        clm.append("    ").append("    ").append("").append("\n");
        clm.append("    ").append("    ").append("/**").append("\n");
        clm.append("    ").append("    ").append(" * コンストラクタ").append("\n");
        clm.append("    ").append("    ").append(" * ").append("\n");
        clm.append("    ").append("    ").append(" * @param columnType カラムの型").append("\n");
        clm.append("    ").append("    ").append(" * @param notNull NOT NULL制約の有無").append("\n");
        clm.append("    ").append("    ").append(" * @param maxLength カラムサイズの最大値(バイト)").append("\n");
        clm.append("    ").append("    ").append(" * @param hasDefault DEFAULT制約の有無").append("\n");
        clm.append("    ").append("    ").append(" * @param defaultValue DEFAULT値").append("\n");
        clm.append("    ").append("    ").append(" * @param updateable 更新可否").append("\n");
        clm.append("    ").append("    ").append(" */").append("\n");
        clm.append("    ").append("    ").append("private COLUMN(int columnType, boolean notNull, int maxLength, boolean hasDefault, String defaultValue, boolean updateable) {").append("\n");
        clm.append("    ").append("    ").append("    this.columnType = columnType;").append("\n");
        clm.append("    ").append("    ").append("    this.notNull = notNull;").append("\n");
        clm.append("    ").append("    ").append("    this.maxLength = maxLength;").append("\n");
        clm.append("    ").append("    ").append("    this.hasDefault = hasDefault;").append("\n");
        clm.append("    ").append("    ").append("    this.defaultValue = defaultValue;").append("\n");
        clm.append("    ").append("    ").append("    this.updateable = updateable;").append("\n");
        clm.append("    ").append("    ").append("}").append("\n");
        clm.append("    ").append("    ").append("").append("\n");
        clm.append("    ").append("    ").append("/**").append("\n");
        clm.append("    ").append("    ").append(" * {@inheritDoc}").append("\n");
        clm.append("    ").append("    ").append(" */").append("\n");
        clm.append("    ").append("    ").append("@Override").append("\n");
        clm.append("    ").append("    ").append("public String getColumnName() {").append("\n");
        clm.append("    ").append("    ").append("    return name();").append("\n");
        clm.append("    ").append("    ").append("}").append("\n");
        clm.append("    ").append("    ").append("").append("\n");
        clm.append("    ").append("    ").append("/**").append("\n");
        clm.append("    ").append("    ").append(" * {@inheritDoc}").append("\n");
        clm.append("    ").append("    ").append(" */").append("\n");
        clm.append("    ").append("    ").append("@Override").append("\n");
        clm.append("    ").append("    ").append("public int getColumnType() {").append("\n");
        clm.append("    ").append("    ").append("    return this.columnType;").append("\n");
        clm.append("    ").append("    ").append("}").append("\n");
        clm.append("    ").append("    ").append("/**").append("\n");
        clm.append("    ").append("    ").append(" * {@inheritDoc}").append("\n");
        clm.append("    ").append("    ").append(" */").append("\n");
        clm.append("    ").append("    ").append("@Override").append("\n");
        clm.append("    ").append("    ").append("public boolean isNotNull() {").append("\n");
        clm.append("    ").append("    ").append("    return this.notNull;").append("\n");
        clm.append("    ").append("    ").append("}").append("\n");
        clm.append("    ").append("    ").append("").append("\n");
        clm.append("    ").append("    ").append("/**").append("\n");
        clm.append("    ").append("    ").append(" * {@inheritDoc}").append("\n");
        clm.append("    ").append("    ").append(" */").append("\n");
        clm.append("    ").append("    ").append("@Override").append("\n");
        clm.append("    ").append("    ").append("public int getMaxLength() {").append("\n");
        clm.append("    ").append("    ").append("    return this.maxLength;").append("\n");
        clm.append("    ").append("    ").append("}").append("\n");
        clm.append("    ").append("    ").append("").append("\n");
        clm.append("    ").append("    ").append("/**").append("\n");
        clm.append("    ").append("    ").append(" * {@inheritDoc}").append("\n");
        clm.append("    ").append("    ").append(" */").append("\n");
        clm.append("    ").append("    ").append("@Override").append("\n");
        clm.append("    ").append("    ").append("public boolean hasDefault() {").append("\n");
        clm.append("    ").append("    ").append("    return this.hasDefault;").append("\n");
        clm.append("    ").append("    ").append("}").append("\n");
        clm.append("    ").append("    ").append("").append("\n");
        clm.append("    ").append("    ").append("/**").append("\n");
        clm.append("    ").append("    ").append(" * {@inheritDoc}").append("\n");
        clm.append("    ").append("    ").append(" */").append("\n");
        clm.append("    ").append("    ").append("@Override").append("\n");
        clm.append("    ").append("    ").append("public String getDefaultValue() {").append("\n");
        clm.append("    ").append("    ").append("    return this.defaultValue;").append("\n");
        clm.append("    ").append("    ").append("}").append("\n");
        clm.append("    ").append("    ").append("").append("\n");
        clm.append("    ").append("    ").append("/**").append("\n");
        clm.append("    ").append("    ").append(" * {@inheritDoc}").append("\n");
        clm.append("    ").append("    ").append(" */").append("\n");
        clm.append("    ").append("    ").append("@Override").append("\n");
        clm.append("    ").append("    ").append("public boolean isUpdateable() {").append("\n");
        clm.append("    ").append("    ").append("    return this.updateable;").append("\n");
        clm.append("    ").append("    ").append("}").append("\n");
        clm.append("    ").append("}").append("\n");

        // メンバ変数&getter/setter
        StringBuilder fields = new StringBuilder();
        StringBuilder gs = new StringBuilder();
        List<ColumnData> enumData = new ArrayList<ColumnData>();
        for (ColumnData column : tableData.getColumns()) {

            fields.append("    ").append("\n");
            gs.append("    ").append("\n");

            String fieldName = Utils.toLCC(column.getName());
            String methodName = Utils.toUCC(column.getName());

            boolean isEnum = !column.getRange().equals("") && column.getDataType().getClassType().equals("int");

            if (!isEnum) {
                fields.append("    ").append("/** ").append(column.getColumnName()).append(" */").append("\n");
                fields.append("    ").append("private ").append(column.getDataType().getClassType()).append(" ").append(fieldName).append(";").append("\n");

                // getter
                gs.append("    ").append("/**").append("\n");
                gs.append("    ").append(" * ").append(column.getColumnName()).append("を取得する。").append("\n");
                gs.append("    ").append(" * ").append("\n");
                gs.append("    ").append(" * ").append("@return ").append(column.getColumnName()).append("\n");
                gs.append("    ").append(" */").append("\n");
                gs.append("    ").append("public ").append(column.getDataType().getClassType()).append(" get").append(methodName).append("() {").append("\n");
                gs.append("    ").append("    ").append("return ").append("this.").append(fieldName).append(";").append("\n");
                gs.append("    ").append("}").append("\n");

                if (column.getDataType() == DBColumnDataTypeEnum.DATE
                        || column.getDataType() == DBColumnDataTypeEnum.TIMESTAMP) {
                    gs.append("    ").append("\n");
                    gs.append("    ").append("/**").append("\n");
                    gs.append("    ").append(" * 初期値ではない").append(column.getColumnName()).append("を取得する。").append("\n");
                    gs.append("    ").append(" * <p>初期値の場合は<code>null</code>").append("\n");
                    gs.append("    ").append(" * ").append("\n");
                    gs.append("    ").append(" * @see ").append(Constants.BASE_PACKAGE).append(".common.util.DateUtil#getInitialDate()").append("\n");
                    gs.append("    ").append(" * ").append("@return 初期値ではない").append(column.getColumnName()).append("<p>初期値の場合は<code>null</code>").append("\n");
                    gs.append("    ").append(" */").append("\n");
                    gs.append("    ").append("public ").append(column.getDataType().getClassType()).append(" getNotIntial").append(methodName).append("() {").append("\n");
                    gs.append("    ").append("    ").append("return org.apache.commons.lang.time.DateUtils.isSameDay(").append("this.").append(fieldName).append(", ").append(Constants.BASE_PACKAGE).append(".common.util.DateUtil.getInitialDate()) ? null : this.").append(fieldName).append(";").append("\n");
                    gs.append("    ").append("}").append("\n");
                }

                // setter
                gs.append("    ").append("\n");
                gs.append("    ").append("/**").append("\n");
                gs.append("    ").append(" * ").append(column.getColumnName()).append("を設定する。").append("\n");
                gs.append("    ").append(" * ").append("\n");
                gs.append("    ").append(" * ").append("@param ").append(fieldName).append(" ").append(column.getColumnName()).append("\n");
                gs.append("    ").append(" */").append("\n");
                gs.append("    ").append("public void ").append("set").append(methodName).append("(").append(column.getDataType().getClassType()).append(" ").append(fieldName).append(") {").append("\n");
                if (column.getDataType().getClassType().equals("String") && column.isNotNull()) {
                    gs.append("    ").append("    ").append("this.").append(fieldName).append(" = org.apache.commons.lang.StringUtils.trimToEmpty(").append(fieldName).append(");").append("\n");
                } else {
                    gs.append("    ").append("    ").append("this.").append(fieldName).append(" = ").append(fieldName).append(";").append("\n");
                }
                gs.append("    ").append("}").append("\n");

            } else {
                fields.append("    ").append("/** ").append(column.getColumnName()).append("の列挙型 */").append("\n");
                fields.append("    ").append("private ").append(methodName).append("Enum").append(" ").append(fieldName).append("Enum = ").append(methodName).append("Enum._NULL;").append("\n");

                // getter
                gs.append("    ").append("/**").append("\n");
                gs.append("    ").append(" * ").append(column.getColumnName()).append("のEnum値を取得する。").append("\n");
                gs.append("    ").append(" * ").append("\n");
                gs.append("    ").append(" * ").append("@return ").append(column.getColumnName()).append("のEnum値").append("\n");
                gs.append("    ").append(" */").append("\n");
                gs.append("    ").append("public ").append(methodName).append("Enum get").append(methodName).append("Enum() {").append("\n");
                gs.append("    ").append("    ").append("return ").append("this.").append(fieldName).append("Enum;").append("\n");
                gs.append("    ").append("}").append("\n");
                gs.append("    ").append("\n");
                gs.append("    ").append("/**").append("\n");
                gs.append("    ").append(" * ").append(column.getColumnName()).append("のコード値を取得する。").append("\n");
                gs.append("    ").append(" * ").append("\n");
                gs.append("    ").append(" * ").append("@return ").append(column.getColumnName()).append("のコード値").append("\n");
                gs.append("    ").append(" */").append("\n");
                gs.append("    ").append("public ").append("int ").append("get").append(methodName).append("() {").append("\n");
                gs.append("    ").append("    ").append("return ").append("this.").append(fieldName).append("Enum.getCode();").append("\n");
                gs.append("    ").append("}").append("\n");
                gs.append("    ").append("\n");
                gs.append("    ").append("/**").append("\n");
                gs.append("    ").append(" * ").append(column.getColumnName()).append("の文字列を取得する。").append("\n");
                gs.append("    ").append(" * ").append("\n");
                gs.append("    ").append(" * ").append("@return ").append(column.getColumnName()).append("の文字列").append("\n");
                gs.append("    ").append(" */").append("\n");
                gs.append("    ").append("public ").append("String ").append("get").append(methodName).append("String() {").append("\n");
                gs.append("    ").append("    ").append("return ").append("this.").append(fieldName).append("Enum.getValue();").append("\n");
                gs.append("    ").append("}").append("\n");

                // setter
                gs.append("    ").append("\n");
                gs.append("    ").append("/**").append("\n");
                gs.append("    ").append(" * ").append(column.getColumnName()).append("を設定する。").append("\n");
                gs.append("    ").append(" * ").append("\n");
                gs.append("    ").append(" * ").append("@param code ").append(column.getColumnName()).append("のコード値\n");
                gs.append("    ").append(" */").append("\n");
                gs.append("    ").append("public void ").append("set").append(methodName).append("(int code) {").append("\n");
                gs.append("    ").append("    ").append(methodName).append("Enum enm = ").append(fieldName).append("Map.get(code);").append("\n");
                gs.append("    ").append("    ").append("this.").append(fieldName).append("Enum = (enm == null) ? ").append(methodName).append("Enum._NULL : enm;").append("\n");
                gs.append("    ").append("}").append("\n");

                enumData.add(column);
            }
        }

        StringBuilder enm = new StringBuilder();
        if (!enumData.isEmpty()) {
            imprt.append("import java.util.HashMap;").append("\n");
            imprt.append("import java.util.Map;").append("\n");

            // enum
            for (ColumnData column : enumData) {
                String fieldName = Utils.toLCC(column.getName());
                String enumName = Utils.toUCC(column.getName()) + "Enum";

                enm.append("    ").append("\n");
                enm.append("    ").append("/**").append("\n");
                enm.append("    ").append(" * ").append(column.getColumnName()).append("の列挙型").append("\n");
                enm.append("    ").append(" */").append("\n");
                enm.append("    ").append("public enum ").append(enumName).append(" {").append("\n");

                List<String[]> enumList = new ArrayList<String[]>();
                String[] split = column.getRange().split("\r|\n");
                for (String string : split) {
                    if (string.equals("【値の定義】")) {
                        continue;
                    }

                    String[] tmp = string.split(":|：");
                    if (tmp.length != 2) {
                        System.out.println("なんかへん->" + string);
                        continue;
                    }

                    String code = tmp[0].trim();
                    String value = tmp[1].trim();

                    enm.append("    ").append("    ").append("/** ").append(column.getColumnName()).append("(").append(value).append(")").append(" */").append("\n");
                    enm.append("    ").append("    ").append(column.getName().toUpperCase()).append("_").append(code);
                    enm.append("(").append(code).append(", \"").append(value).append("\"), ").append("\n");

                    enumList.add(tmp);
                }

                enm.append("    ").append("    ").append("/** NULLオブジェクト(初期値ではない) */").append("\n");
//                    enm.append("    ").append("    ").append("_NULL(-1, \"\"),  // NULLオブジェクト").append("\n");
                enm.append("    ").append("    ").append("_NULL(-1, \"\"),").append("\n");
                enm.append("    ").append("    ").append(";").append("\n");
                enm.append("    ").append("    ").append("").append("\n");
                enm.append("    ").append("    ").append("/** コード値 */").append("\n");
                enm.append("    ").append("    ").append("public final int code;").append("\n");
                enm.append("    ").append("    ").append("").append("\n");
                enm.append("    ").append("    ").append("/** 値 */").append("\n");
                enm.append("    ").append("    ").append("public final String value;").append("\n");
                enm.append("    ").append("    ").append("").append("\n");
                enm.append("    ").append("    ").append("/**").append("\n");
                enm.append("    ").append("    ").append(" * コンストラクタ").append("\n");
                enm.append("    ").append("    ").append(" * ").append("\n");
                enm.append("    ").append("    ").append(" * @param code コード値").append("\n");
                enm.append("    ").append("    ").append(" * @param value 値").append("\n");
                enm.append("    ").append("    ").append(" */").append("\n");
                enm.append("    ").append("    ").append("private ").append(enumName).append("(int code, String value) {").append("\n");
                enm.append("    ").append("    ").append("    this.code = code;").append("\n");
                enm.append("    ").append("    ").append("    this.value = value;").append("\n");
                enm.append("    ").append("    ").append("}").append("\n");
                enm.append("    ").append("    ").append("").append("\n");
                enm.append("    ").append("    ").append("/**").append("\n");
                enm.append("    ").append("    ").append(" * ").append(column.getColumnName()).append("のコード値を取得する。").append("\n");
                enm.append("    ").append("    ").append(" * ").append("\n");
                enm.append("    ").append("    ").append(" * @return コード値").append("\n");
                enm.append("    ").append("    ").append(" */").append("\n");
                enm.append("    ").append("    ").append("public int getCode() {").append("\n");
                enm.append("    ").append("    ").append("    return code;").append("\n");
                enm.append("    ").append("    ").append("}").append("\n");
                enm.append("    ").append("    ").append("").append("\n");
                enm.append("    ").append("    ").append("/**").append("\n");
                enm.append("    ").append("    ").append(" * ").append(column.getColumnName()).append("の値を取得する。").append("\n");
                enm.append("    ").append("    ").append(" * ").append("\n");
                enm.append("    ").append("    ").append(" * @return 値").append("\n");
                enm.append("    ").append("    ").append(" */").append("\n");
                enm.append("    ").append("    ").append("public String getValue() {").append("\n");
                enm.append("    ").append("    ").append("    return value;").append("\n");
                enm.append("    ").append("    ").append("}").append("\n");
                enm.append("    ").append("    ").append("").append("\n");
                enm.append("    ").append("    ").append("/**").append("\n");
                enm.append("    ").append("    ").append(" * 同値の場合<code>true</code>を返します。").append("\n");
                enm.append("    ").append("    ").append(" * ").append("\n");
                enm.append("    ").append("    ").append(" * @param ").append(fieldName).append("Enum 比較対象の").append(column.getColumnName()).append("の列挙型").append("\n");
                enm.append("    ").append("    ").append(" * @return 同値の場合<code>true</code>").append("\n");
                enm.append("    ").append("    ").append(" */").append("\n");
                enm.append("    ").append("    ").append("public boolean isEqual(").append(enumName).append(" ").append(fieldName).append("Enum) {").append("\n");
                enm.append("    ").append("    ").append("    return this == ").append(fieldName).append("Enum;").append("\n");
                enm.append("    ").append("    ").append("}").append("\n");
                enm.append("    ").append("    ").append("").append("\n");
                enm.append("    ").append("    ").append("/**").append("\n");
                enm.append("    ").append("    ").append(" * 同値の場合<code>true</code>を返します。").append("\n");
                enm.append("    ").append("    ").append(" * ").append("\n");
                enm.append("    ").append("    ").append(" * @param code 比較対象の").append(column.getColumnName()).append("のコード値").append("\n");
                enm.append("    ").append("    ").append(" * @return 同値の場合<code>true</code>").append("\n");
                enm.append("    ").append("    ").append(" */").append("\n");
                enm.append("    ").append("    ").append("public boolean isEqual(int code) {").append("\n");
                enm.append("    ").append("    ").append("    return this.code == code;").append("\n");
                enm.append("    ").append("    ").append("}").append("\n");
                enm.append("    ").append("    ").append("").append("\n");
                enm.append("    ").append("    ").append("/**").append("\n");
                enm.append("    ").append("    ").append(" * ").append(column.getColumnName()).append("の文字列表現を取得する。").append("\n");
                enm.append("    ").append("    ").append(" * ").append("\n");
                enm.append("    ").append("    ").append(" * @return ").append(column.getColumnName()).append("の文字列表現").append("\n");
                enm.append("    ").append("    ").append(" */").append("\n");
                enm.append("    ").append("    ").append("@Override").append("\n");
                enm.append("    ").append("    ").append("public String toString() {").append("\n");
                enm.append("    ").append("    ").append("    return getClass().getSimpleName() + \"[code=\" + getCode() + \", value=\" + getValue() + \"]\";").append("\n");
                enm.append("    ").append("    ").append("}").append("\n");
                enm.append("    ").append("}").append("\n");

                enm.append("    ").append("").append("\n");

                String mapName = fieldName + "Map";
                enm.append("    ").append("/** ").append(column.getColumnName()).append("のマップ キーはコード値 */").append("\n");
                enm.append("    ").append("private final static Map<Integer, ").append(enumName).append("> ").append(mapName).append(";").append("\n");
                enm.append("    ").append("static {").append("\n");
                enm.append("    ").append("    ").append(mapName).append(" = new HashMap<Integer, ").append(enumName).append(">();").append("\n");
                enm.append("    ").append("    ").append("for (").append(enumName).append(" enm : ").append(enumName).append(".values()) {").append("\n");
                enm.append("    ").append("    ").append("    ").append("if (enm == ").append(enumName).append("._NULL) continue;").append("\n");
                enm.append("    ").append("    ").append("    ").append(mapName).append(".put(enm.getCode(), enm);").append("\n");
                enm.append("    ").append("    ").append("}").append("\n");
                enm.append("    ").append("}").append("\n");

                for (String[] eee : enumList) {

                    String code = eee[0].trim();
                    String value = eee[1].trim();

                    enm.append("    ").append("").append("\n");
                    enm.append("    ").append("/**").append("\n");
                    enm.append("    ").append(" * ").append(column.getColumnName()).append("(").append(value).append(")").append("の場合<code>true</code>を返します。").append("\n");
                    enm.append("    ").append(" * ").append("\n");
                    enm.append("    ").append(" * @return ").append(column.getColumnName()).append("(").append(value).append(")").append("の場合<code>true</code>").append("\n");
                    enm.append("    ").append(" */").append("\n");
                    enm.append("    ").append("public boolean is").append(Utils.toUCC(column.getName())).append("_").append(code).append("() {").append("\n");
                    enm.append("    ").append("    return this.").append(fieldName).append("Enum == ").append(enumName).append(".").append(column.getName().toUpperCase()).append("_").append(code).append(";\n");
                    enm.append("    ").append("}").append("\n");
                }
            }
        }

        StringBuilder toString = new StringBuilder();
        toString.append("    ").append("\n");
        toString.append("    ").append("/**").append("\n");
        toString.append("    ").append(" * ").append(tableData.getTableName()).append("の文字列表現を取得する。").append("\n");
        toString.append("    ").append(" * ").append("\n");
        toString.append("    ").append(" * @return ").append(tableData.getTableName()).append("の文字列表現").append("\n");
        toString.append("    ").append(" */").append("\n");
        toString.append("    ").append("@Override").append("\n");
        toString.append("    ").append("public String toString() {").append("\n");
        toString.append("    ").append("    ").append("StringBuilder sb = new StringBuilder();").append("\n");
        toString.append("    ").append("    ").append("sb.append(getClass().getSimpleName()).append(\"[\");").append("\n");
        for (Iterator<ColumnData> iter = tableData.getColumns().iterator(); iter.hasNext();) {
            ColumnData column = (ColumnData)iter.next();

            toString.append("    ").append("    ").append("sb.append(\"").append(column.getName()).append(" = \")");
            toString.append(".append(String.valueOf(this.").append(Utils.toLCC(column.getName()));
            if (enumData.contains(column)) {
                toString.append("Enum");

            }
            toString.append("));").append("\n");

            if (iter.hasNext()) {
                toString.append("    ").append("    ").append("sb.append(\", \");").append("\n");
            }
        }
        toString.append("    ").append("    ").append("sb.append(\"]\");").append("\n");
        toString.append("    ").append("    ").append("return sb.toString();").append("\n");
        toString.append("    ").append("}").append("\n");

        StringBuilder override = new StringBuilder();
        override.append("    ").append("\n");
        override.append("    ").append("/**").append("\n");
        override.append("    ").append(" * {@inheritDoc}").append("\n");
        override.append("    ").append(" */").append("\n");
        override.append("    ").append("@Override").append("\n");
        override.append("    ").append("public String getTableName() {").append("\n");
        override.append("    ").append("    ").append("return \"").append(tableData.getName()).append("\";").append("\n");
        override.append("    ").append("}").append("\n");
        override.append("    ").append("").append("\n");
        override.append("    ").append("/**").append("\n");
        override.append("    ").append(" * {@inheritDoc}").append("\n");
        override.append("    ").append(" */").append("\n");
        override.append("    ").append("@Override").append("\n");
        override.append("    ").append("public Column getPrimaryKeyColumn() {").append("\n");
        override.append("    ").append("    ").append("return COLUMN.").append(tableData.getPrimaryKeys().get(0).getName()).append(";").append("\n");
        override.append("    ").append("}").append("\n");
        override.append("    ").append("").append("\n");
        override.append("    ").append("/**").append("\n");
        override.append("    ").append(" * {@inheritDoc}").append("\n");
        override.append("    ").append(" */").append("\n");
        override.append("    ").append("@Override").append("\n");
        override.append("    ").append("public long getPrimaryKey() {").append("\n");
        override.append("    ").append("    ").append("return get").append(Utils.toUCC(tableData.getPrimaryKeys().get(0).getName())).append("();").append("\n");
        override.append("    ").append("}").append("\n");
        override.append("    ").append("").append("\n");
        override.append("    ").append("/**").append("\n");
        override.append("    ").append(" * {@inheritDoc}").append("\n");
        override.append("    ").append(" */").append("\n");
        override.append("    ").append("@Override").append("\n");
        override.append("    ").append("public List<Column> getColumns() {").append("\n");
        override.append("    ").append("    ").append("return columns;").append("\n");
        override.append("    ").append("}").append("\n");
        override.append("    ").append("").append("\n");
        override.append("    ").append("/**").append("\n");
        override.append("    ").append(" * {@inheritDoc}").append("\n");
        override.append("    ").append(" */").append("\n");
        override.append("    ").append("@Override").append("\n");
        override.append("    ").append("public String getSequenceName() {").append("\n");
        override.append("    ").append("    ").append("return \"").append(tableData.getPrimaryKeys().get(0).getUseSeq().trim()).append("\";").append("\n");
        override.append("    ").append("}").append("\n");
        override.append("    ").append("").append("\n");
        override.append("    ").append("/**").append("\n");
        override.append("    ").append(" * {@inheritDoc}").append("\n");
        override.append("    ").append(" */").append("\n");
        override.append("    ").append("@Override").append("\n");
        override.append("    ").append("public String getEntityName() {").append("\n");
        override.append("    ").append("    ").append("return \"").append(tableData.getTableName().trim()).append("\";").append("\n");
        override.append("    ").append("}").append("\n");
        override.append("    ").append("").append("\n");
        override.append("    ").append("/**").append("\n");
        override.append("    ").append(" * {@inheritDoc}").append("\n");
        override.append("    ").append(" */").append("\n");
        override.append("    ").append("@Override").append("\n");
        override.append("    ").append("public List<Column> getUpdateableColumns() {").append("\n");
        override.append("    ").append("    ").append("return updateable_columns;").append("\n");
        override.append("    ").append("}").append("\n");
        override.append("    ").append("").append("\n");
        override.append("    ").append("/** カラムのリスト */").append("\n");
        override.append("    ").append("private final static List<Column> columns;").append("\n");
        override.append("    ").append("").append("\n");
        override.append("    ").append("/** 更新可能なカラムのリスト */").append("\n");
        override.append("    ").append("private final static List<Column> updateable_columns;").append("\n");
        override.append("    ").append("").append("\n");
        override.append("    ").append("static {").append("\n");
        override.append("    ").append("    ").append("columns = new ArrayList<Column>();").append("\n");
        override.append("    ").append("    ").append("updateable_columns = new ArrayList<Column>();").append("\n");
        override.append("    ").append("    ").append("for (Column column : COLUMN.values()) {").append("\n");
        override.append("    ").append("    ").append("    ").append("columns.add(column);").append("\n");
        override.append("    ").append("    ").append("    ").append("if (column.isUpdateable()) updateable_columns.add(column);").append("\n");
        override.append("    ").append("    ").append("}").append("\n");
        override.append("    ").append("}").append("\n");

        o.write(header.toString());
        o.write(imprt.toString());
        o.write(classHeader.toString());
        o.write(clm.toString());
        o.write(fields.toString());
        o.write(gs.toString());
        o.write(enm.toString());
        o.write(toString.toString());
        o.write(override.toString());
        o.write("}\n");

        o.flush();

        System.out.println(className+" created.");
    }
}
