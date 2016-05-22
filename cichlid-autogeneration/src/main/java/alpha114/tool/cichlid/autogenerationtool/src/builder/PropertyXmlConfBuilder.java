package alpha114.tool.cichlid.autogenerationtool.src.builder;

import java.util.List;

import alpha114.tool.cichlid.autogenerationtool.Builder;

/**
 * @author kurisakisatoshi
 */
public class PropertyXmlConfBuilder implements Builder {

    private final List<String> propFiles;

    public PropertyXmlConfBuilder(List<String> propFiles) {
        this.propFiles = propFiles;
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

        for (String fileName : propFiles) {

            String beanName = fileName.replaceFirst(".properties", "Property");

            sb.append("<bean id=\"").append(beanName).append("\" class=\"org.springframework.beans.factory.config.PropertiesFactoryBean\">").append("\n");
            sb.append("\t<property name=\"locations\">").append("\n");
            sb.append("\t\t<list>").append("\n");
            sb.append("\t\t\t<value>classpath:").append(fileName).append("</value>").append("\n");
            sb.append("\t\t</list>").append("\n");
            sb.append("\t</property>").append("\n");
            sb.append("</bean>").append("\n");
            sb.append("").append("\n");
            sb.append("<bean id=\"Customized").append(beanName).append("\" class=\"org.springframework.beans.factory.config.PropertiesFactoryBean\">").append("\n");
            sb.append("\t<property name=\"locations\">").append("\n");
            sb.append("\t\t<list>").append("\n");
            sb.append("\t\t\t<value>file:${CONFDIR}/").append(fileName).append("</value>").append("\n");
            sb.append("\t\t</list>").append("\n");
            sb.append("\t</property>").append("\n");
            sb.append("\t<property name=\"ignoreResourceNotFound\">").append("\n");
            sb.append("\t\t<value>true</value>").append("\n");
            sb.append("\t</property>").append("\n");
            sb.append("</bean>").append("\n");
            sb.append("").append("\n");
        }

        sb.append("</beans>").append("\n");

        return sb.toString();
    }
}
