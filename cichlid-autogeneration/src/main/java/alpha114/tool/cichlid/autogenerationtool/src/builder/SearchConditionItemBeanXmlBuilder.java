package alpha114.tool.cichlid.autogenerationtool.src.builder;

import java.util.List;

import alpha114.tool.cichlid.autogenerationtool.Builder;
import alpha114.tool.cichlid.autogenerationtool.Constants;
import alpha114.tool.cichlid.autogenerationtool.Utils;
import alpha114.tool.cichlid.autogenerationtool.data.FrameData;
import alpha114.tool.cichlid.autogenerationtool.data.FrameItemData;

/**
 * @author kurisakisatoshi
 */
public class SearchConditionItemBeanXmlBuilder implements Builder {

    private final List<FrameData> datas;

    public SearchConditionItemBeanXmlBuilder(List<FrameData> datas) {
        this.datas = datas;
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

        for (FrameData frameData : datas) {

            if (!frameData.isSearchFlag()) {
                continue;
            }

            sb.append("\t<!-- ").append(frameData.getFrameName()).append(" -->").append("\n");
            sb.append("\t<bean id=\"").append(frameData.getFrameId()).append("_SearchConditionViewModel\" ").append("\n");
            sb.append("\t\tclass=\"").append(Constants.VIEW_MODEL_PACKAGE_SEARCHCONDITION).append(".DefaultSearchConditionViewModel\"").append("\n");
            sb.append("\t\tscope=\"prototype\"").append("\n");
            sb.append("\t\tp:searchConditionItemList-ref=\"").append(frameData.getFrameId()).append("_SearchConditionItemList\">").append("\n");
            sb.append("\t</bean>").append("\n");
            sb.append("").append("\n");
            sb.append("\t<util:list id=\"").append(frameData.getFrameId()).append("_SearchConditionItemList\"").append("\n");
            sb.append("\t\t\tscope=\"prototype\"").append("\n");
            sb.append("\t\t\tlist-class=\"java.util.ArrayList\">").append("\n");
            sb.append("").append("\n");

            for (FrameItemData item : frameData.getItems()) {

                if (!item.getId().startsWith(FrameItemData.ItemType.SEARCH_CONDITION.type)) {
                    continue;
                }

                if (item.isCalendar()) {
                    if (item.getLabel().indexOf("終了") > -1) {
                        continue;
                    }
                }

                sb.append("\t\t<!-- ").append(item.getLabel()).append(" -->").append("\n");
                sb.append("\t\t<bean class=\"").append(Constants.VIEW_MODEL_PACKAGE_SEARCHCONDITION).append(".SearchConditionItemInfo\"").append("\n");
                sb.append("\t\t\tscope=\"prototype\"").append("\n");
                sb.append("\t\t\tp:itemId=\"").append(item.getId()).append("\"").append("\n");
                sb.append("\t\t\tp:labelId=\"").append(Utils.getLabelId_cal(frameData, item)).append("\"").append("\n");
                sb.append("\t\t\tp:itemType=\"").append(getType(item)).append("\"");

                if (item.isCalendar()) {
                    FrameItemData from = item;
                    FrameItemData to = Utils.getTo(frameData, item);

                    sb.append("\n");
                    sb.append("\t\t\tp:fromId=\"").append(from.getId()).append("\"").append("\n");
                    sb.append("\t\t\tp:toId=\"").append(to.getId()).append("\"");
                }

                sb.append(">").append("\n");
                sb.append("\t\t</bean>").append("\n");
                sb.append("").append("\n");
            }

            sb.append("\t</util:list>").append("\n");
            sb.append("").append("\n");

        }

        sb.append("</beans>").append("\n");
        sb.append("").append("\n");

        return sb.toString();
    }

    private String getType(FrameItemData item) {

        if (item.isCalendar()) {
            return "CALENDAR";
        }

        if (item.isText()) {
            return "TEXT";

        } else if (item.isPulldown()) {
            return "PULLDOWN";

        } else if (item.isRadioButton()) {
            return "RADIO";
        }

        return "OTHER";
    }
}
