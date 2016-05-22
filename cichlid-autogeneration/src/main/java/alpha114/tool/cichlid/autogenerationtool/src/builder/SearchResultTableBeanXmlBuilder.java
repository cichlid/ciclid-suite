package alpha114.tool.cichlid.autogenerationtool.src.builder;

import java.util.List;

import alpha114.tool.cichlid.autogenerationtool.Builder;
import alpha114.tool.cichlid.autogenerationtool.Constants;
import alpha114.tool.cichlid.autogenerationtool.Utils;
import alpha114.tool.cichlid.autogenerationtool.data.FrameData;
import alpha114.tool.cichlid.autogenerationtool.data.FrameItemData;
import alpha114.tool.cichlid.autogenerationtool.data.TableListItemData;

/**
 * @author kurisakisatoshi
 */
public class SearchResultTableBeanXmlBuilder implements Builder {

    private final List<FrameData> frameDataList;

    public SearchResultTableBeanXmlBuilder(List<FrameData> frameDataList) {
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
        sb.append("\txmlns:util=\"http://www.springframework.org/schema/util\"").append("\n");
        sb.append("\txsi:schemaLocation=\"http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-3.0.xsd http://www.springframework.org/schema/util http://www.springframework.org/schema/util/spring-util-2.5.xsd\">").append("\n");
        sb.append("").append("\n");

        for (FrameData frameData : frameDataList) {

            if (!frameData.isTableFlag()) {
                continue;
            }

            sb.append("\t<!-- ").append(frameData.getFrameName()).append(" -->").append("\n");
            sb.append("\t<bean id=\"").append(frameData.getFrameId()).append("_TableConstructInfo\"").append("\n");
            sb.append("\t\tclass=\"").append(Constants.VIEW_MODEL_PACKAGE_TABLE).append(".TableConstructInfo\"").append("\n");
            sb.append("\t\tp:viewMode-ref=\"").append(frameData.getFrameId()).append("_ViewModel\"").append("\n");
            sb.append("\t\tp:defaultSort=\"").append(getDefaultSortKey(frameData)).append("\"").append("\n");
            sb.append("\t\tp:defaultSortOrder=\"").append(getDefaultSortOrder(frameData)).append("\"").append("\n");
            sb.append("\t\tp:columnInfoList-ref=\"").append(frameData.getFrameId()).append("_TableColumnConstructInfo\"").append("\n");
            sb.append("\t\tscope=\"prototype\">").append("\n");
            sb.append("\t</bean>").append("\n");
            sb.append("").append("\n");
            sb.append("\t<util:list id=\"").append(frameData.getFrameId()).append("_TableColumnConstructInfo\"").append("\n");
            sb.append("\t\t\tlist-class=\"java.util.ArrayList\">").append("\n");
            sb.append("").append("\n");

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

                    if (clm.getLabel().startsWith("管理項目")) {
                        continue;
                    }

                    TableListItemData headerItem = getHeaderItem(frameData, item.getTableColumns(), clm);

                    sb.append("\t\t<!-- ").append(clm.getLabel()).append(" -->").append("\n");
                    sb.append("\t\t<bean class=\"").append(Constants.VIEW_MODEL_PACKAGE_TABLE).append(".TableColumnConstructInfo\"").append("\n");
                    sb.append("\t\t\tp:sort=\"").append(headerItem.isSort()).append("\"").append("\n");
                    if (headerItem.isSort()) {
                        sb.append("\t\t\tp:defaultSortOrder=\"").append(headerItem.getSortOrder()).append("\"").append("\n");
                        sb.append("\t\t\tp:comparatorBeanId-ref=\"").append(frameData.getFrameId()).append(".").append(getHeaderItem(frameData, item.getTableColumns(), clm).getId()).append("_comparator\"").append("\n");
                    }
                    sb.append("\t\t\tp:csv=\"").append(isCsvOut(clm)).append("\"").append("\n");
                    sb.append("\t\t\tp:itemId=\"").append(clm.getId()).append("\"").append("\n");
                    sb.append("\t\t\tp:headerItemId=\"").append(getHeaderItem(frameData, item.getTableColumns(), clm).getId()).append("\"").append("\n");
                    sb.append("\t\t\tp:columnType=\"").append(getColumnType(clm));
                    if (clm.isCheckbox() && !clm.getLabel().equals("選択")) {
                        sb.append("\"");
                        sb.append("\n");
                        sb.append("\t\t\tp:checkboxLabelId=\"").append(frameData.getFrameId()).append(".").append(getHeaderItem(frameData, item.getTableColumns(), clm).getId()).append("\"");
                        sb.append("\n");
                        sb.append("\t\t\tp:enableAllCheck=\"false");
                    }
                    sb.append("\">").append("\n");
                    sb.append("\t\t</bean>").append("\n");
                    sb.append("").append("\n");
                }
            }

            sb.append("\t</util:list>").append("\n");
            sb.append("").append("\n");
            sb.append("\t<!-- ").append(frameData.getFrameName()).append("のSearchResultViewModel -->").append("\n");
            sb.append("\t<bean id=\"").append(frameData.getFrameId()).append("_ViewModel\"").append("\n");
//            if (frameData.getFrameType() == FrameType.Assoc) {
//                sb.append("\t\tclass=\"").append(Constants.VIEW_MODEL_PACKAGE_ASSOC).append(".impl.").append(frameData.getFrameId()).append("_ViewModelImpl\">").append("\n");
//            } else {
//                sb.append("\t\tclass=\"").append(Constants.VIEW_MODEL_PACKAGE_SEARCHRESULT).append(".impl.").append(frameData.getFrameId()).append("_SearchResultViewModelImpl\">").append("\n");
//            }
            sb.append("\t\tclass=\"").append(Constants.PRJ_VIEW_MODEL_PACKAGE_SEARCHRESULT).append(".impl.").append(frameData.getFrameId()).append("_SearchResultViewModelImpl\">").append("\n");
            sb.append("\t</bean>").append("\n");
            sb.append("").append("\n");
        }

        sb.append("</beans>").append("\n");

        return sb.toString();
    }

    private TableListItemData getHeaderItem(FrameData frameData, List<TableListItemData> clmList, TableListItemData clm) {
        return Utils.getHeaderItem(frameData, clmList, clm);
    }

    private String getDefaultSortKey(FrameData frameData) {

        String itemName_ja = frameData.getTableDefaultSortKey();

        for (FrameItemData item : frameData.getItems()) {

            if (!item.isTable()) {
                continue;
            }

            for (TableListItemData clm : item.getTableColumns()) {

                if (clm.isHeader()
                        && clm.isSort()
                        && clm.getLabel().equals(itemName_ja)) {

                    if (clm.getLabel().startsWith("管理項目")) {
                        continue;
                    }

                    return clm.getId();
                }
            }
        }

        return "";
    }

    private String getDefaultSortOrder(FrameData frameData) {
//        return Utils.getDefaultSortOrder(frameData);

        return frameData.getTableDefaultSortOrder();
    }

    private boolean isCsvOut(TableListItemData clm) {

        return clm.isText() || clm.isPulldown() || clm.isTextData() || clm.isLinkText() || clm.isLabel();
    }

    private String getColumnType(TableListItemData clm) {
        if (clm.isCheckbox()) {
            return "CHECKBOX";

        } else if (clm.isRadioButton()) {
            return "RADIO";

        } else if (clm.isLink()) {
            return "LINK";

        } else if (clm.isLinkText()) {
            return "LINK_OR_TEXT";

        } else if(clm.isButton()) {
            return "BUTTON";

        } else if(clm.isPulldown()) {
            return "PULLDOWN";

        } else if (clm.isText()){
            return "INPUT";

        } else {
            return "TEXT";
        }
    }
}
