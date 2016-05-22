package alpha114.tool.cichlid.autogenerationtool.src.builder;

import java.util.List;

import alpha114.tool.cichlid.autogenerationtool.Builder;
import alpha114.tool.cichlid.autogenerationtool.Constants;
import alpha114.tool.cichlid.autogenerationtool.Utils;
import alpha114.tool.cichlid.autogenerationtool.data.ColumnData;
import alpha114.tool.cichlid.autogenerationtool.data.TableData;

/**
 * @author kurisakisatoshi
 */
public class ComparatorBeanConfBuilder implements Builder {

    private final List<TableData> tables;


    public ComparatorBeanConfBuilder(List<TableData> tables) {
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
        sb.append("\txsi:schemaLocation=\"http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-3.0.xsd\">").append("\n");
        sb.append("").append("\n");

        for (TableData tableData : tables) {

            for (ColumnData columnData : tableData.getColumns()) {

                sb.append("\t")
                  .append("<bean id=\"")
                  .append(Utils.toUCC(columnData.getTableName()).toLowerCase())
                  .append(".").append(Utils.toUCC(columnData.getName()).toLowerCase()).append("comparator")
                  .append("\"\n")
                  .append("\t\t")
                  .append("class=\"")
                  .append(Constants.COMPARATOR_PACKAGE).append(".").append(tableData.getName().toLowerCase())
                  .append(".").append(Utils.toUCC(columnData.getName())).append("Comparator")
                  .append("\"\n")
                  .append("\t\t")
                  .append("scope=\"prototype\" />").append("\n\n");
            }
        }

        sb.append("</beans>").append("\n");
        sb.append("").append("\n");

        return sb.toString();
    }
}
