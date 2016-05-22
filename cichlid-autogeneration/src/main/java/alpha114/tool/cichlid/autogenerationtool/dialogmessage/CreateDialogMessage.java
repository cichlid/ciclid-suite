package alpha114.tool.cichlid.autogenerationtool.dialogmessage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import alpha114.tool.cichlid.autogenerationtool.Constants;
import alpha114.tool.cichlid.autogenerationtool.CreateSrc;
import alpha114.tool.cichlid.autogenerationtool.data.DialogMessageData;
import alpha114.tool.cichlid.autogenerationtool.dialogmessage.builder.DialogMessageIdBuilder;
import alpha114.tool.cichlid.autogenerationtool.dialogmessage.builder.DialogMessageJsBuilder;
import alpha114.tool.cichlid.autogenerationtool.dialogmessage.builder.DialogMessagePropertyBuilder;

/**
 * @author kurisakisatoshi
 */
public class CreateDialogMessage extends CreateSrc {

    private final String distDir;

    public CreateDialogMessage(String xls, String distDir) {
        this.distDir = distDir;

        Workbook wb = createWorkbook(xls);
        if (wb == null) {
            return;
        }


        Sheet sheet = wb.getSheet("メッセージ一覧");

        List<DialogMessageData> msgs = new ArrayList<DialogMessageData>();
        String frameName = "";
        String frameId = "";
        int row = 2;
        Row r = null;
        while ((r = sheet.getRow(row)) != null) {

            String name = getStringCellValue(((Cell) r.getCell(1)));
            String fId = getStringCellValue(((Cell) r.getCell(2)));
            String index = getStringCellValue(((Cell) r.getCell(3)));
            String type = getStringCellValue(((Cell) r.getCell(4)));
            String message = getStringCellValue(((Cell) r.getCell(6)));
            String ketubann = getStringCellValue(((Cell) r.getCell(8)));

            if ((message == null || message.isEmpty()) && ketubann.indexOf("欠番") == -1) {
                break;
            }

            if (name != null && !name.equals("")) {
                frameName = name;
                frameId = fId;
            }

            if (index.equals("") || index.equals("-") || message.equals("") || message.equals("-")) {
                row++;
                continue;
            }

            if (ketubann.indexOf("欠番") >= 0) {
                row++;
                continue;
            }

            DialogMessageData data = new DialogMessageData();
            data.frameName = frameName;
            data.frameId = frameId;
            data.index = index;
            data.type = type;
            data.message = message;

            msgs.add(data);

            row++;
        }

        createProperty(msgs);

        createMessageId(msgs);

        createMessageJs(msgs);
    }

    private void createProperty(List<DialogMessageData> msgs) {

        String dir = distDir + "\\src\\main\\resources_autogene";

        write(dir, dir + "\\dialogmessage_ja.properties" + "." + "UTF-8", new DialogMessagePropertyBuilder(msgs));

        native2Ascii(dir + "\\dialogmessage_ja.properties" + "." + "UTF-8", dir + "\\dialogmessage_ja.properties");
    }

    private void createMessageId(List<DialogMessageData> msgs) {

        String dir = distDir + "\\src\\main\\java_autogene\\" + Constants.PRJ_WEB_MESSAGE_ID_PACKAGE_DIR;

        write(dir, dir + "\\DialogMessageKey.java", new DialogMessageIdBuilder(msgs));
    }

    private void createMessageJs(List<DialogMessageData> msgs) {

        Map<String, List<DialogMessageData>> map = new HashMap<String, List<DialogMessageData>>();

        for (DialogMessageData data : msgs) {
            List<DialogMessageData> datas = map.get(data.frameId);
            if (datas == null) {
                datas = new ArrayList<DialogMessageData>();
            }
            datas.add(data);
            map.put(data.frameId, datas);
        }

        String dir = distDir + "\\src\\main\\webapp\\js_autogene\\message";

        for (Map.Entry<String, List<DialogMessageData>> entry : map.entrySet()) {

            write(dir, dir + "\\message_" + entry.getKey() + ".js", new DialogMessageJsBuilder(entry.getValue()));
        }
    }



    public static void main(String[] args) {

        for (String arg : args) {
            System.out.println(arg);
        }

        String xls = null;
        String distDir = null;

        for (int i = 0; i < args.length; i++) {
            String arg = args[i];

            if (arg.equals("-xls")) {
                if (i + 1 < args.length) {
                    xls = args[i + 1];
                    i++;
                }

            } else if (arg.equals("-distdir")) {
                if (i + 1 < args.length) {
                    distDir = args[i + 1];
                    i++;
                }
            }
        }

        System.out.println("xls -> " + xls);
        System.out.println("dist -> " + distDir);

        if (StringUtils.isEmpty(xls)
                || StringUtils.isEmpty(distDir)) {

            System.out.println("引数異常");
            return;
        }

        if (!distDir.endsWith("\\")) {
            distDir += "\\";
        }
        distDir += CreateDialogMessage.class.getSimpleName() + "\\" + Constants.DIST_DIR_WEB;

        try {
            FileUtils.deleteDirectory(new File(distDir));
        } catch (IOException ioe) {
            ioe.printStackTrace();
            return;
        }

        new CreateDialogMessage(xls, distDir);

        System.out.println("");
        System.out.println("done.");
    }
}
