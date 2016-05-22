package alpha114.tool.cichlid.autogenerationtool.data;

/**
 * @author kurisakisatoshi
 */
public class DialogMessageData {

    public String frameName;
    public String frameId;
    public String index;
    public String type;
    public String message;


    public boolean isConfirm() {
        return type.equals("確認ダイアログ");
    }
}
