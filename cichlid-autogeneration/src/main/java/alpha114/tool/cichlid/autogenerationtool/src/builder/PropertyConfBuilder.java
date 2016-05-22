package alpha114.tool.cichlid.autogenerationtool.src.builder;

import java.util.List;

import alpha114.tool.cichlid.autogenerationtool.Builder;
import alpha114.tool.cichlid.autogenerationtool.Constants;
import alpha114.tool.cichlid.autogenerationtool.Utils;
import alpha114.tool.cichlid.autogenerationtool.data.PropertyData;

/**
 * @author kurisakisatoshi
 */
public class PropertyConfBuilder implements Builder {

    private final String fileName;
    private List<PropertyData> propList;

    public PropertyConfBuilder(String fileName, List<PropertyData> propList) {
        this.fileName = fileName;
        this.propList = propList;
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
        sb.append(" * 備考    ：").append("\n");
        sb.append(" * 更新履歴：").append("\n");
        sb.append(" *   日付       更新者       内容").append("\n");
        sb.append(" *   ").append(Constants.DATE_CREATED).append("   kurisakisatoshi   新規作成").append("\n");
        sb.append(" * ").append("\n");
        sb.append(" */").append("\n");

        // package
        sb.append("package ").append(Constants.PRJ_PROPRTY_CONF_PACKAGE).append(";").append("\n");
        sb.append("\n");

        // import
        sb.append("import java.util.Properties;").append("\n");
        sb.append("\n");
        sb.append("import org.apache.commons.logging.Log;").append("\n");
        sb.append("import org.apache.commons.logging.LogFactory;").append("\n");
        sb.append("\n");
        sb.append("import ").append(Constants.PROPRTY_CONF_PACKAGE).append(".Reloadable;").append("\n");
        sb.append("\n");

        String className = fileName.replaceFirst(".properties", "PropertyConf");

        // class看板
        sb.append("/**").append("\n");
        sb.append(" * クラス名：").append(className).append("\n");
        sb.append(" * 機能概要：").append(fileName).append("の定義内容をキャッシュするクラスです。").append("\n");
        sb.append(" * 備考：").append("\n");
        sb.append(" * ").append("\n");
        sb.append(" */").append("\n");

        // class
        sb.append("public enum ").append(className).append(" implements Reloadable {").append("\n");
        sb.append("    ").append("\n");
        sb.append("    // ★★自動生成のため本ファイルを直接修正しないこと★★").append("\n");
        sb.append("    ").append("\n");
        sb.append("    /** 自インスタンス */").append("\n");
        sb.append("    INSTANCE").append(";").append("\n");
        sb.append("    ").append("\n");
        sb.append("    /**").append("\n");
        sb.append("     * コンストラクタ").append("\n");
        sb.append("     */").append("\n");
        sb.append("    private ").append(className).append("() {").append("\n");
        sb.append("        initialize();").append("\n");
        sb.append("    }").append("\n");
        sb.append("    ").append("\n");
        sb.append("    /**").append("\n");
        sb.append("     * {@inheritDoc}").append("\n");
        sb.append("     */").append("\n");
        sb.append("    @Override").append("\n");
        sb.append("    public void reload() {").append("\n");
        sb.append("        initialize();").append("\n");
        sb.append("    }").append("\n");
        sb.append("    ").append("\n");

        String beanName = fileName.replaceFirst(".properties", "Property");

        sb.append("    /**").append("\n");
        sb.append("     * ").append(fileName).append("を読み込みキャッシュします。").append("\n");
        sb.append("     */").append("\n");
        sb.append("    private synchronized void initialize() {").append("\n");
        sb.append("        ").append("\n");
        sb.append("        getLog().info(\"").append(fileName).append(" 読み込み開始\");").append("\n");
        sb.append("        ").append("\n");
        sb.append("        Properties prop = PropertyUtils.getProperty(\"").append(beanName).append("\", \"Customized").append(beanName).append("\");").append("\n");
        sb.append("        ").append("\n");

        StringBuilder toString = new StringBuilder();
        StringBuilder field = new StringBuilder();
        StringBuilder getter = new StringBuilder();

        toString.append("    ").append("\n");
        toString.append("    /**").append("\n");
        toString.append("     * {@inheritDoc}").append("\n");
        toString.append("     */").append("\n");
        toString.append("    @Override").append("\n");
        toString.append("    public String toString() {").append("\n");
        toString.append("        ").append("\n");
        toString.append("        StringBuilder sb = new StringBuilder();").append("\n");
        toString.append("        ").append("\n");

        field.append("    ").append("\n");
        field.append("    ").append("\n");
        field.append("    ").append("\n");

        getter.append("    ").append("\n");
        getter.append("    ").append("\n");

        for (PropertyData data : propList) {
            if (data.getNote().indexOf("定義削除") > -1) {
                continue;
            }

            String key = Utils.toLCC(data.getKey());

            if (data.isNumeric()) {
                sb.append("        ").append(key).append(" = PropertyUtils.getIntValue(prop, \"").append(data.getKey()).append("\", ").append(data.getMin()).append(", ").append(data.getMax()).append(", ").append(data.getDefaultValue()).append(", ").append(!data.getAbbreviation()).append(");").append("\n");

            } else if (data.isHankaku()) {
                sb.append("        ").append(key).append(" = PropertyUtils.getHankakuStringValue(prop, \"").append(data.getKey()).append("\", \"").append(data.getDefaultValue()).append("\", ").append(!data.getAbbreviation()).append(");").append("\n");

            } else if (data.isZenkaku()) {
                if (!"NULL_SEARCH_KEYWORD".equals(data.getKey())) {
                    sb.append("        ").append(key).append(" = PropertyUtils.getStringValue(prop, \"").append(data.getKey()).append("\", \"").append(data.getDefaultValue()).append("\", ").append(!data.getAbbreviation()).append(");").append("\n");

                } else {
                    sb.append("        ").append(key).append(" = PropertyUtils.getStringValue(prop, \"").append(data.getKey()).append("\", \"").append(data.getDefaultValue()).append("\", ").append(!data.getAbbreviation()).append(", '|');").append("\n");
                }

            } else if (data.isFile()) {
                sb.append("        ").append(key).append(" = PropertyUtils.getFileValue(prop, \"").append(data.getKey()).append("\", \"").append(data.getDefaultValue()).append("\", ").append(!data.getAbbreviation()).append(");").append("\n");
            }


            toString.append("        sb.append(\"").append(data.getKey()).append("\").append(\"=\").append(").append(key).append(").append(\"\\n\");").append("\n");


            field.append("    /** ").append(data.getKey()).append("の定義内容 */").append("\n");
            field.append("    private volatile ").append(data.isNumeric() ? "int" : "String").append(" ").append(key).append(";").append("\n");
            field.append("    ").append("\n");

            getter.append("    /**").append("\n");
            getter.append("     * ").append(data.getKey()).append("の定義内容を返します。").append("\n");
            getter.append("     * ").append("\n");
            getter.append("     * @return ").append(data.getKey()).append("の定義内容").append("\n");
            getter.append("     */").append("\n");
            getter.append("    public ").append(data.isNumeric() ? "int" : "String").append(" get").append(Utils.toUCC(data.getKey())).append("() {").append("\n");
            getter.append("        return ").append(key).append(";").append("\n");
            getter.append("    }").append("\n");
            getter.append("    ").append("\n");
        }

        toString.append("        ").append("\n");
        toString.append("        return sb.toString();").append("\n");
        toString.append("    }").append("\n");
        toString.append("    ").append("\n");

        sb.append("        ").append("\n");
        sb.append("        getLog().info(\"").append(fileName).append(" 読み込み終了\");").append("\n");
        sb.append("        getLog().info(\"キャッシュ内容\\n\" + this.toString());").append("\n");
        sb.append("    }").append("\n");
        sb.append("    ").append("\n");


        sb.append(toString.toString());
        sb.append(field.toString());
        sb.append(getter.toString());

        sb.append("    ").append("\n");
        sb.append("    ").append("\n");
        sb.append("    ").append("\n");
        sb.append("    /** ログ */").append("\n");
        sb.append("    Log getLog() {").append("\n");
        sb.append("        return LogFactory.getLog(this.getClass());").append("\n");
        sb.append("    }").append("\n");
        sb.append("}").append("\n");

        return sb.toString();
    }
}
