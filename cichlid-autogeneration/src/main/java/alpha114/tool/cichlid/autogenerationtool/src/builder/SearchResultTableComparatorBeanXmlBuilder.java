package alpha114.tool.cichlid.autogenerationtool.src.builder;

import java.util.List;

import alpha114.tool.cichlid.autogenerationtool.Builder;
import alpha114.tool.cichlid.autogenerationtool.Utils;
import alpha114.tool.cichlid.autogenerationtool.data.FrameData;
import alpha114.tool.cichlid.autogenerationtool.data.FrameItemData;
import alpha114.tool.cichlid.autogenerationtool.data.TableListItemData;

/**
 * @author kurisakisatoshi
 */
public class SearchResultTableComparatorBeanXmlBuilder implements Builder {

    private final List<FrameData> frameDataList;

    public SearchResultTableComparatorBeanXmlBuilder(List<FrameData> frameDataList) {
        this.frameDataList = frameDataList;
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

        for (FrameData frameData : frameDataList) {

            if (!frameData.isTableFlag()) {
                continue;
            }

            sb.append("\t<!-- ").append(frameData.getFrameName()).append(" -->").append("\n");
            sb.append("\n");

            for (FrameItemData item : frameData.getItems()) {

                if (!item.isTable()) {
                    continue;
                }

                for (TableListItemData clm : item.getTableColumns()) {

                    if (clm.isTable()) {
                        continue;
                    }

                    if (clm.isHeader()) {
                        continue;
                    }

                    if (Utils.getHeaderItem(frameData, item.getTableColumns(), clm).isSort() && !Utils.isDBEmpty(clm)) {
                        sb.append("\t<!-- ").append(clm.getLabel()).append(" -->").append("\n");
                        sb.append("\t<bean id=\"").append(frameData.getFrameId()).append(".").append(Utils.getHeaderItem(frameData, item.getTableColumns(), clm).getId()).append("_comparator\" class=\"java.lang.String\">").append("\n");
                        sb.append("\t\t<constructor-arg value=\"").append(Utils.toLCC(clm.getTableName()).toLowerCase()).append(".").append(Utils.toLCC(clm.getColumnName()).toLowerCase()).append("comparator").append("\"/>").append("\n");
                        sb.append("\t</bean>").append("\n");
                        sb.append("\n");
                    }
                }
            }
            sb.append("\n");
            sb.append("\n");
        }

        sb.append("</beans>").append("\n");

        return sb.toString();
    }
}
