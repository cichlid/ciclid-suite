package alpha114.tool.cichlid.autogenerationtool.src.builder;

import java.io.IOException;
import java.io.Writer;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.io.output.StringBuilderWriter;

import alpha114.tool.cichlid.autogenerationtool.Builder;
import alpha114.tool.cichlid.autogenerationtool.Constants;
import alpha114.tool.cichlid.autogenerationtool.Utils;
import alpha114.tool.cichlid.autogenerationtool.data.TableData;

/**
 * @author kurisakisatoshi
 */
public class EntityWithRelationBuilder implements Builder {

    @SuppressWarnings("unused")
    private final String instanceName;
    private final TableData tableData;
    private final List<TableData> tables;

    public EntityWithRelationBuilder(String instanceName, TableData tableData, List<TableData> tables) {
        this.instanceName = instanceName;
        this.tableData = tableData;
        this.tables = tables;
    }


    @Override
    public String build() {

        StringBuilderWriter w = new StringBuilderWriter();

        try {
            createRelation(w);

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        return w.toString();
    }

    private void createRelation(Writer o) throws IOException {

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
        header.append("package ").append(Constants.PRJ_RELATION_PACKAGE).append(";").append("\n");
        header.append("").append("\n");

        // import
        StringBuilder imprt = new StringBuilder();
        imprt.append("import java.io.Serializable;").append("\n");

        // class看板
        StringBuilder classHeader = new StringBuilder();
        classHeader.append("").append("\n");
        classHeader.append("/**").append("\n");
        classHeader.append(" * ").append(tableData.getTableName()).append("と関連するエンティティクラスを保持する。").append("\n");
        classHeader.append(" * ").append("\n");
        classHeader.append(" * @see     java.io.Serializable").append("\n");
        classHeader.append(" * @see     ").append(Constants.PRJ_ENTITY_PACKAGE).append(".").append(className).append("\n");
        classHeader.append(" * @author  kurisakisatoshi").append("\n");
        classHeader.append(" * @version $Revision").append(": $").append("\n");
        classHeader.append(" */").append("\n");
        classHeader.append("public class ").append(className).append("WithRelation extends ").append(className).append(" implements Serializable {").append("\n");
        classHeader.append("    ").append("").append("\n");
        classHeader.append("    ").append("// ★★自動生成のため本ファイルを直接修正しないこと★★").append("\n");
        classHeader.append("    ").append("").append("\n");
        classHeader.append("    ").append("/** シリアルバージョン */").append("\n");
        classHeader.append("    ").append("private static final long serialVersionUID = 1L;").append("\n");

        StringBuilder fields = new StringBuilder();
        if (tableData.getOneToManyRelations().isEmpty()
                && tableData.getOneToOneRelations().isEmpty()) {
            fields.append("    ").append("").append("\n");
            fields.append("    ").append("// 関連情報なし").append("\n");
            fields.append("    ").append("").append("\n");
        }
        else if (!tableData.getOneToManyRelations().isEmpty()){
            imprt.append("import java.util.ArrayList;").append("\n");
            imprt.append("import java.util.Collections;").append("\n");
            imprt.append("import java.util.Comparator;").append("\n");
            imprt.append("import java.util.Iterator;").append("\n");
            imprt.append("import java.util.List;").append("\n");
        }

        StringBuilder gs = new StringBuilder();

        for (String relation : tableData.getOneToOneRelations()) {
            TableData relationTable = null;
            for (TableData tbl : tables) {
                if (relation.equals(tbl.getName())) {
                    relationTable = tbl;
                    break;
                }
            }

            if (relationTable == null) {
                throw new NullPointerException("関連テーブル名が間違っていると思われる。" + tableData.getTableName() + ", 関連テーブル:" + relation);
            }

            fields.append("    ").append("").append("\n");
            fields.append("    ").append("/** ").append(relationTable.getTableName()).append(" */").append("\n");
            fields.append("    ").append("private ").append(toUCC(relationTable.getName())).append("WithRelation ").append(toLCC(relationTable.getName())).append(";").append("\n");

            gs.append("    ").append("").append("\n");
            gs.append("    ").append("/**").append("\n");
            gs.append("    ").append(" * ").append(relationTable.getTableName()).append("を取得する。").append("\n");
            gs.append("    ").append(" * ").append("\n");
            gs.append("    ").append(" * @return ").append(relationTable.getTableName()).append("\n");
            gs.append("    ").append(" */").append("\n");
            gs.append("    ").append("public ").append(toUCC(relationTable.getName())).append("WithRelation get").append(toUCC(relationTable.getName())).append("WithRelation() {").append("\n");
            gs.append("    ").append("    ").append("return this.").append(toLCC(relationTable.getName())).append(";").append("\n");
            gs.append("    ").append("}").append("\n");
            gs.append("    ").append("").append("\n");
            gs.append("    ").append("/**").append("\n");
            gs.append("    ").append(" * ").append(relationTable.getTableName()).append("を設定する。").append("\n");
            gs.append("    ").append(" * ").append("\n");
            gs.append("    ").append(" * @param ").append(toLCC(relationTable.getName())).append(" ").append(relationTable.getTableName()).append("\n");
            gs.append("    ").append(" */").append("\n");
            gs.append("    ").append("public void set").append(toUCC(relationTable.getName())).append("WithRelation(").append(toUCC(relationTable.getName())).append("WithRelation ").append(toLCC(relationTable.getName())).append(") {").append("\n");
            gs.append("    ").append("    ").append("this.").append(toLCC(relationTable.getName())).append(" = ").append(toLCC(relationTable.getName())).append(";").append("\n");
            gs.append("    ").append("}").append("\n");
        }

        for (String relation : tableData.getOneToManyRelations()) {
            TableData relationTable = null;
            for (TableData tbl : tables) {
                if (relation.equals(tbl.getName())) {
                    relationTable = tbl;
                    break;
                }
            }

            if (relationTable == null) {
                throw new NullPointerException("関連テーブル名が間違っていると思われる。" + tableData.getTableName() + ", 関連テーブル:" + relation);
            }

            fields.append("    ").append("").append("\n");
            fields.append("    ").append("/** ").append(relationTable.getTableName()).append("のリスト */").append("\n");
            fields.append("    ").append("private List<").append(toUCC(relationTable.getName())).append("WithRelation> ").append(toLCC(relationTable.getName())).append("List = new ArrayList<").append(toUCC(relationTable.getName())).append("WithRelation>();").append("\n");

            gs.append("    ").append("").append("\n");
            gs.append("    ").append("/**").append("\n");
            gs.append("    ").append(" * ").append(relationTable.getTableName()).append("リストのイテレータを取得する。").append("\n");
            gs.append("    ").append(" * ").append("\n");
            gs.append("    ").append(" * @return ").append(relationTable.getTableName()).append("リストのイテレータ").append("\n");
            gs.append("    ").append(" */").append("\n");
            gs.append("    ").append("public Iterator<").append(toUCC(relationTable.getName())).append("WithRelation> get").append(toUCC(relationTable.getName())).append("List() {").append("\n");
            gs.append("    ").append("    ").append("return this.").append(toLCC(relationTable.getName())).append("List.iterator();").append("\n");
            gs.append("    ").append("}").append("\n");
            gs.append("    ").append("").append("\n");
            gs.append("    ").append("/**").append("\n");
            gs.append("    ").append(" * ").append(relationTable.getTableName()).append("を追加する。").append("\n");
            gs.append("    ").append(" * ").append("\n");
            gs.append("    ").append(" * @param ").append(toLCC(relationTable.getName())).append(" 追加する").append(relationTable.getTableName()).append("\n");
            gs.append("    ").append(" */").append("\n");
            gs.append("    ").append("public void add").append(toUCC(relationTable.getName())).append("(").append(toUCC(relationTable.getName())).append("WithRelation ").append(toLCC(relationTable.getName())).append(") {").append("\n");
            gs.append("    ").append("    ").append("this.").append(toLCC(relationTable.getName())).append("List.add(").append(toLCC(relationTable.getName())).append(");").append("\n");
            gs.append("    ").append("}").append("\n");
            gs.append("    ").append("").append("\n");
            gs.append("    ").append("/**").append("\n");
            gs.append("    ").append(" * ").append(relationTable.getTableName()).append("を削除する。").append("\n");
            gs.append("    ").append(" * ").append("\n");
            gs.append("    ").append(" * @param ").append(toLCC(relationTable.getName())).append(" 削除する").append(relationTable.getTableName()).append("\n");
            gs.append("    ").append(" */").append("\n");
            gs.append("    ").append("public void remove").append(toUCC(relationTable.getName())).append("(").append(toUCC(relationTable.getName())).append("WithRelation ").append(toLCC(relationTable.getName())).append(") {").append("\n");
            gs.append("    ").append("    ").append("this.").append(toLCC(relationTable.getName())).append("List.remove(").append(toLCC(relationTable.getName())).append(");").append("\n");
            gs.append("    ").append("}").append("\n");
            gs.append("    ").append("").append("\n");
            gs.append("    ").append("/**").append("\n");
            gs.append("    ").append(" * ").append(relationTable.getTableName()).append("をクリアする。").append("\n");
            gs.append("    ").append(" */").append("\n");
            gs.append("    ").append("public void clear").append(toUCC(relationTable.getName())).append("List() {").append("\n");
            gs.append("    ").append("    ").append("this.").append(toLCC(relationTable.getName())).append("List.clear();").append("\n");
            gs.append("    ").append("}").append("\n");
            gs.append("    ").append("").append("\n");
            gs.append("    ").append("/**").append("\n");
            gs.append("    ").append(" * ").append(relationTable.getTableName()).append("の数を取得する。").append("\n");
            gs.append("    ").append(" * ").append("\n");
            gs.append("    ").append(" * @return ").append(relationTable.getTableName()).append("の数").append("\n");
            gs.append("    ").append(" */").append("\n");
            gs.append("    ").append("public int get").append(toUCC(relationTable.getName())).append("Count() {").append("\n");
            gs.append("    ").append("    ").append("return this.").append(toLCC(relationTable.getName())).append("List.size();").append("\n");
            gs.append("    ").append("}").append("\n");
            gs.append("    ").append("").append("\n");
            gs.append("    ").append("/**").append("\n");
            gs.append("    ").append(" * ").append(relationTable.getTableName()).append("の数が0かを取得する。").append("\n");
            gs.append("    ").append(" * ").append("\n");
            gs.append("    ").append(" * @return ").append(relationTable.getTableName()).append("の数が0の場合<code>true</code>").append("\n");
            gs.append("    ").append(" */").append("\n");
            gs.append("    ").append("public boolean is").append(toUCC(relationTable.getName())).append("Empty() {").append("\n");
            gs.append("    ").append("    ").append("return this.").append(toLCC(relationTable.getName())).append("List.isEmpty();").append("\n");
            gs.append("    ").append("}").append("\n");
            gs.append("    ").append("").append("\n");
            gs.append("    ").append("/**").append("\n");
            gs.append("    ").append(" * ").append(relationTable.getTableName()).append("リストをソートする。").append("\n");
            gs.append("    ").append(" * ").append("\n");
            gs.append("    ").append(" * @param comparator コンパレータ").append("\n");
            gs.append("    ").append(" */").append("\n");
            gs.append("    ").append("public void sort").append(toUCC(relationTable.getName())).append("List(Comparator<").append(toUCC(relationTable.getName())).append("WithRelation> comparator) {").append("\n");
            gs.append("    ").append("    ").append("Collections.sort(this.").append(toLCC(relationTable.getName())).append("List, comparator);").append("\n");
            gs.append("    ").append("}").append("\n");
        }

        StringBuilder toString = new StringBuilder();
        if (!tableData.getOneToManyRelations().isEmpty()
                || !tableData.getOneToOneRelations().isEmpty()) {
            toString.append("    ").append("").append("\n");
            toString.append("    ").append("/**").append("\n");
            toString.append("    ").append(" * ").append(tableData.getTableName()).append("(関連情報あり)の文字列表現を取得する。").append("\n");
            toString.append("    ").append(" * ").append("\n");
            toString.append("    ").append(" * @return ").append(tableData.getTableName()).append("(関連情報あり)の文字列表現").append("\n");
            toString.append("    ").append(" */").append("\n");
            toString.append("    ").append("@Override").append("\n");
            toString.append("    ").append("public String toString() {").append("\n");
            toString.append("    ").append("    ").append("StringBuilder sb = new StringBuilder(super.toString());").append("\n");

            if (!tableData.getOneToOneRelations().isEmpty()) {
                toString.append("    ").append("    ").append("sb.append(\", RelationInfo[\");").append("\n");
                for (Iterator<String> iter = tableData.getOneToOneRelations().iterator(); iter.hasNext();) {
                    toString.append("    ").append("    ").append("sb.append(String.valueOf(").append(toLCC(iter.next())).append("));").append("\n");
                    if (iter.hasNext()) {
                        toString.append("    ").append("    ").append("sb.append(\", \");").append("\n");
                    }
                }
                toString.append("    ").append("    ").append("sb.append(\"]\");").append("\n");
            }

            if (!tableData.getOneToManyRelations().isEmpty()) {
                toString.append("    ").append("    ").append("sb.append(\", RelationInfoList[\");").append("\n");
                for (Iterator<String> iter = tableData.getOneToManyRelations().iterator(); iter.hasNext();) {
                    String relation = iter.next();
                    toString.append("    ").append("    ").append("for (Iterator<").append(toUCC(relation)).append("WithRelation> iter = ").append(toLCC(relation)).append("List.iterator(); iter.hasNext();) {").append("\n");
                    toString.append("    ").append("    ").append("    ").append("sb.append(String.valueOf(iter.next()));").append("\n");
                    toString.append("    ").append("    ").append("    ").append("if (iter.hasNext()) {").append("\n");
                    toString.append("    ").append("    ").append("    ").append("    ").append("sb.append(\", \");").append("\n");
                    toString.append("    ").append("    ").append("    ").append("}").append("\n");
                    toString.append("    ").append("    ").append("}").append("\n");
                    if (iter.hasNext()) {
                        toString.append("    ").append("    ").append("sb.append(\"]\");").append("\n");
                        toString.append("    ").append("    ").append("sb.append(\", RelationInfoList[\");").append("\n");
                    }
                }
                toString.append("    ").append("    ").append("sb.append(\"]\");").append("\n");
            }
            toString.append("    ").append("    ").append("return sb.toString();").append("\n");
            toString.append("    ").append("}").append("\n");
        }

        imprt.append("").append("\n");
        imprt.append("import ").append(Constants.PRJ_ENTITY_PACKAGE).append(".").append(className).append(";\n");

        o.write(header.toString());
        o.write(imprt.toString());
        o.write(classHeader.toString());
        o.write(fields.toString());
        o.write(gs.toString());
        o.write(toString.toString());
        o.write("}\n");

        o.flush();

        System.out.println(className+"WithRelation created.");
    }

    private String toUCC(String s) {
        return Utils.toUCC(s);
    }

    private String toLCC(String s) {
        return Utils.toLCC(s);
    }
}
