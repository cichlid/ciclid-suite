package alpha114.tool.cichlid.autogenerationtool.src.builder;

import java.util.List;

import alpha114.tool.cichlid.autogenerationtool.Builder;
import alpha114.tool.cichlid.autogenerationtool.Constants;
import alpha114.tool.cichlid.autogenerationtool.Utils;
import alpha114.tool.cichlid.autogenerationtool.data.TableData;

/**
 * @author kurisakisatoshi
 */
public class DaoBeanConfBuilder implements Builder {

    private final String instanceName;
    private final List<TableData> tables;


    public DaoBeanConfBuilder(String instanceName, List<TableData> tables) {
        this.instanceName = instanceName;
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

        makeBeanDef(sb, "GenericDao".toLowerCase() + "_" + instanceName, Constants2.DAO_PACKAGE + ".common.DefaultJdbcDaoSupport");
//        makeBeanDef(sb, "DefaultJdbcDaoSupport".toLowerCase() + "_" + instanceName, Constants.DAO_PACKAGE + ".common.DefaultJdbcDaoSupport");

        for (TableData tableData : tables) {

            if (!Utils.isCUD(tableData)) {
                continue;
            }

            String ifName = Utils.toUCC(tableData.getName()) + "Dao";
            String classPath = Constants.DAO_CUD_PACKAGE + "." + tableData.getName().toLowerCase() + ".impl." + ifName + "Impl";

            mekBeanDef___(sb, ifName.toLowerCase(), classPath);
        }

        sb.append("</beans>").append("\n");
        sb.append("").append("\n");

        return sb.toString();
    }


    private String makeBeanDef(StringBuilder sb, String ifName, String classPath) {

        sb.append("\t<bean id=\"").append(ifName).append("\" ").append("\n");
        sb.append("\t\tclass=\"").append(classPath).append("\" ").append("\n");
        sb.append("\t\tscope=\"prototype\" ").append("\n");
        sb.append("\t\tp:dataSource-ref=\"dataSource_").append(instanceName).append("\">").append("\n");
        sb.append("\t</bean>").append("\n");
        sb.append("").append("\n");

        return sb.toString();
    }

    private String mekBeanDef___(StringBuilder sb, String ifName, String classPath) {

        sb.append("\t<bean id=\"").append(ifName).append("\" ").append("\n");
        sb.append("\t\tclass=\"").append(classPath).append("\" ").append("\n");
        sb.append("\t\tscope=\"prototype\" ").append("\n");
        sb.append("\t\tp:dataSource-ref=\"dataSource_").append(instanceName).append("\">").append("\n");
        sb.append("\t</bean>").append("\n");
        sb.append("").append("\n");

        return sb.toString();
    }
}
