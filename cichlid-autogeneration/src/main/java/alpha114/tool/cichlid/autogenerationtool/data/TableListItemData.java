package alpha114.tool.cichlid.autogenerationtool.data;

/**
 * @author kurisakisatoshi
 */
public class TableListItemData extends FrameItemData {

    public boolean sort;
    public String sortOrder = "ASCEND";

    public boolean isHeader() {
        return getInputType().startsWith(InputType.HEADER.text());
    }

    public boolean isLink() {
        return getInputType().equals(InputType.LINK.text());
    }

    public boolean isLinkText() {
        return getInputType().equals(InputType.LINK_OR_TEXT.text());
    }

    public boolean isTextData() {
        return getInputType().equals(InputType.TEXTDATA.text());
    }
}
