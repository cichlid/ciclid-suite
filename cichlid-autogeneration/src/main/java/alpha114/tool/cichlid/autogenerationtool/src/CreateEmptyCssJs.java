package alpha114.tool.cichlid.autogenerationtool.src;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import alpha114.tool.cichlid.autogenerationtool.Constants;
import alpha114.tool.cichlid.autogenerationtool.data.FrameData;
import alpha114.tool.cichlid.autogenerationtool.src.builder.CreateEmptyCssBuilder;
import alpha114.tool.cichlid.autogenerationtool.src.builder.CreateEmptyJsBuilder;

/**
 * 素のcss、jsを出力
 *
 * @author kurisakisatoshi
 */
public class CreateEmptyCssJs extends CreateSearchCondition {

    public CreateEmptyCssJs(String frameListXls, String xls, String distDir) {
        super(frameListXls, xls, distDir);
    }

    @Override
    void create(List<FrameData> datas) {

        for (FrameData frameData : datas) {

            String dir = distDir + "\\src\\main\\webapp\\css\\" + frameData.frameId;

            write(dir, dir + "\\" + frameData.frameId + ".css", new CreateEmptyCssBuilder(frameData));


            dir = distDir + "\\src\\main\\webapp\\js\\" + frameData.frameId;

            write(dir, dir + "\\" + frameData.frameId + ".js", new CreateEmptyJsBuilder(frameData));
        }
    }

    /**
     * @param args
     */
    public static void main(String[] args) {

        for (String arg : args) {
            System.out.println(arg);
        }

        String xls = null;
        String distDir = null;
        String framelistxls = null;

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

            } else if (arg.equals("-framelistxls")) {
                if (i + 1 < args.length) {
                    framelistxls = args[i + 1];
                    i++;
                }
            }
        }

        System.out.println("xls -> " + xls);
        System.out.println("dist -> " + distDir);
        System.out.println("framelistxls -> " + framelistxls);

        if (StringUtils.isEmpty(xls)
                || StringUtils.isEmpty(distDir)
                || StringUtils.isEmpty(framelistxls)) {

            System.out.println("引数異常");
            return;
        }

        if (!distDir.endsWith("\\")) {
            distDir += "\\";
        }
        distDir += CreateEmptyCssJs.class.getSimpleName() + "\\" + Constants.DIST_DIR_WEB;

        try {
            FileUtils.deleteDirectory(new File(distDir));
        } catch (IOException ioe) {
            ioe.printStackTrace();
            return;
        }

        new CreateEmptyCssJs(framelistxls, xls, distDir);

        System.out.println("");
        System.out.println("done.");
    }
}
