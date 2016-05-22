package alpha114.tool.cichlid.autogenerationtool.src.builder;

import java.util.List;

import alpha114.tool.cichlid.autogenerationtool.Builder;
import alpha114.tool.cichlid.autogenerationtool.Utils;
import alpha114.tool.cichlid.autogenerationtool.data.ColumnData;
import alpha114.tool.cichlid.autogenerationtool.data.TableData;

/**
 * @author kurisakisatoshi
 */
public class NotificationTargetBeanXmlBuilder implements Builder {

    private final List<TableData> tables;

    public NotificationTargetBeanXmlBuilder(List<TableData> tables) {
        this.tables = tables;
    }

    @Override
    public String build() {

        StringBuilder sb = new StringBuilder();

        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>").append("\n");
        sb.append("").append("\n");
        sb.append("<!-- 自動生成 -->").append("\n");
        sb.append("").append("\n");
        sb.append("<beans xmlns=\"http://www.springframework.org/schema/beans\"").append("\n");
        sb.append("\txmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"").append("\n");
        sb.append("\txmlns:p=\"http://www.springframework.org/schema/p\"").append("\n");
        sb.append("\txmlns:util=\"http://www.springframework.org/schema/util\"").append("\n");
        sb.append("\txsi:schemaLocation=\"http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-3.0.xsd http://www.springframework.org/schema/util http://www.springframework.org/schema/util/spring-util-2.5.xsd\">").append("\n");
        sb.append("").append("\n");

        // insert
        sb.append("\t<!-- Insert時の通知対象テーブル -->").append("\n");
        sb.append("\t<util:set id=\"InsertNotificationTargetEntity\"").append("\n");
        sb.append("\t\t\tset-class=\"java.util.HashSet\"").append("\n");
        sb.append("\t\t\tvalue-type=\"java.lang.String\">").append("\n");
        for (TableData table : tables) {
            if (table.isNotifyByInsert()) {
                sb.append("\t\t<!-- ").append(table.getTableName()).append(" -->").append("\n");
                sb.append("\t\t<value>").append(table.getName()).append("</value>").append("\n");
            }
        }
        sb.append("\t</util:set>").append("\n");
        sb.append("").append("\n");

        // delete
        sb.append("\t<!-- Delete時の通知対象テーブル -->").append("\n");
        sb.append("\t<util:set id=\"DeleteNotificationTargetEntity\"").append("\n");
        sb.append("\t\t\tset-class=\"java.util.HashSet\"").append("\n");
        sb.append("\t\t\tvalue-type=\"java.lang.String\">").append("\n");
        for (TableData table : tables) {
            if (table.isNotifyByDelete()) {
                sb.append("\t\t<!-- ").append(table.getTableName()).append(" -->").append("\n");
                sb.append("\t\t<value>").append(table.getName()).append("</value>").append("\n");
            }
        }
        sb.append("\t</util:set>").append("\n");
        sb.append("").append("\n");

        // update
        sb.append("\t<!-- Update時の通知対象テーブル -->").append("\n");
        sb.append("\t<util:map id=\"UpdateNotificationTargetEntity\"").append("\n");
        sb.append("\t\t\tmap-class=\"java.util.HashMap\">").append("\n");
        for (TableData table : tables) {
            if (table.isNotifyByUpdate()) {
                sb.append("\t\t<!-- ").append(table.getTableName()).append(" -->").append("\n");
                sb.append("\t\t<entry key=\"").append(table.getName()).append("\" value-ref=\"UpdateNotificationTarget_").append(Utils.toUCC(table.getName())).append("_Columns\" />").append("\n");
            }
        }
        sb.append("\t</util:map>").append("\n");
        sb.append("").append("\n");

        // update-column
        for (TableData table : tables) {
            if (table.isNotifyByUpdate()) {
                sb.append("\t<!-- ").append(Utils.toUCC(table.getTableName())).append("のUpdate時の通知対象カラム -->").append("\n");
                sb.append("\t<util:set id=\"UpdateNotificationTarget_").append(Utils.toUCC(table.getName())).append("_Columns\"").append("\n");
                sb.append("\t\t\tset-class=\"java.util.HashSet\"").append("\n");
                sb.append("\t\t\tvalue-type=\"java.lang.String\">").append("\n");
                for (ColumnData column : table.getColumns()) {
                    if (column.isNotify()) {
                        sb.append("\t\t<!-- ").append(column.getColumnName()).append(" -->").append("\n");
                        sb.append("\t\t<value>").append(column.getName()).append("</value>").append("\n");
                    }
                }
                sb.append("\t</util:set>").append("\n");
                sb.append("").append("\n");
            }
        }

        sb.append("</beans>").append("\n");

        // check
        for (TableData table : tables) {
            for (ColumnData column : table.getColumns()) {
                if (column.isNotify() && !table.isNotifyByUpdate()) {
                    System.out.println(table.getTableName() + "("  + table.getName() + ")の" + column.getName() + "が通知○なのにUpdate契機の通知有無が「なし」になっている");
                }
            }
        }

        return sb.toString();
    }
}
