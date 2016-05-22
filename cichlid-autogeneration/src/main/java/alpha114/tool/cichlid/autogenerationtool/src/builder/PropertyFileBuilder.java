package alpha114.tool.cichlid.autogenerationtool.src.builder;

import java.util.List;

import alpha114.tool.cichlid.autogenerationtool.Builder;
import alpha114.tool.cichlid.autogenerationtool.data.PropertyData;

/**
 * @author kurisakisatoshi
 */
public class PropertyFileBuilder implements Builder {

    private final String fileName;
    private List<PropertyData> propList;

    public PropertyFileBuilder(String fileName, List<PropertyData> propList) {
        this.fileName = fileName;
        this.propList = propList;
    }

    @Override
    public String build() {

        StringBuilder sb = new StringBuilder();

        sb.append("#").append("\n");
        sb.append("# ").append(fileName).append("\n");
        sb.append("#").append("\n");

        for (PropertyData data : propList) {
            if (data.getNote().indexOf("定義削除") > -1) {
                continue;
            }
            if (data.getValue().equals("(定義なし)")) {
                continue;
            }
            if (data.getValue().equals("(コメントアウト)")) {
                sb.append("#").append(data.getKey()).append("=").append("\n");
                continue;
            }
            sb.append(data.getKey()).append("=").append(data.getValue()).append("\n");
        }

        return sb.toString();
    }
}
