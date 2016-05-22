package alpha114.tool.cichlid.autogenerationtool.src;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import alpha114.tool.cichlid.autogenerationtool.Constants;
import alpha114.tool.cichlid.autogenerationtool.data.FrameData;
import alpha114.tool.cichlid.autogenerationtool.data.FrameItemData;
import alpha114.tool.cichlid.autogenerationtool.src.builder.SearchResultViewModelBuilder;
import alpha114.tool.cichlid.autogenerationtool.src.builder.SettingDataViewModelBuilder;
import alpha114.tool.cichlid.autogenerationtool.src.builder.SettingDataViewModelImplBuilder;
import alpha114.tool.cichlid.autogenerationtool.src.builder.ViewModelBuilder;
import alpha114.tool.cichlid.autogenerationtool.src.builder.ViewModelImplBuilder;

/**
 * @author kurisakisatoshi
 */
public class CreateViewModel extends CreateSearchCondition {

    public CreateViewModel(String frameListXls, String xls, String distDir) {
        super(frameListXls, xls, distDir);
    }

    /*
     * (非 Javadoc)
     *
     * @see
     * alpha114_117.tool.cichlid.autogenerationtool2.src.CreateSearchCondition#create(
     * java.util.List)
     */
    @Override
    void create(List<FrameData> datas) {

        for (FrameData f : datas) {

            for (FrameItemData item : f.getItems()) {

                if (item.getId().startsWith(FrameItemData.ItemType.SEARCH_CONDITION.type)) {
                    f.setSearchFlag(true);
                } else if (item.getId().startsWith(FrameItemData.ItemType.TABLE_COLUMN.type)) {
                    f.setTableFlag(true);
                } else if (item.getId().startsWith(FrameItemData.ItemType.SETTING_DATA.type)) {
                    f.setSettiongFlag(true);
                }
            }

            createViewModel(f);
            createViewModelImpl(f);

            if (f.isSearchFlag()) {
                // 現状、検索条件用のViewModel作成は不要のため、処理なし
            }
            if (f.isTableFlag()) {
                createSearchResultViewModel(f);
            }
            if (f.isSettiongFlag()) {
                createSettingDataViewModel(f);

                // ↓は初回のみ
//				createSettingDataViewModelImpl(f);
                System.out.println("SettingDataViewModelImplの生成は初回だけなのでスキップ");
            }

        }
    }

    private void createViewModel(FrameData frameData) {

        String dir = distDir + "\\src\\main\\java_autogene\\" + Constants.PRJ_VIEW_MODE_PACKAGE_DIR;

        write(dir, dir + "\\" + frameData.getFrameId() + "_ViewModel.java",
                new ViewModelBuilder(frameData));
    }

    private void createViewModelImpl(FrameData frameData) {

        String dir = distDir + "\\src\\main\\java_autogene\\" + Constants.PRJ_VIEW_MODE_PACKAGE_DIR + "\\impl";

        write(dir, dir + "\\" + frameData.getFrameId() + "_ViewModelImpl.java",
                new ViewModelImplBuilder(frameData));
    }

    private void createSearchResultViewModel(FrameData frameData) {

        String dir = distDir + "\\src\\main\\java_autogene\\" + Constants.PRJ_VIEW_MODEL_PACKAGE_SEARCHRESULT_DIR;

        write(dir, dir + "\\" + frameData.getFrameId() + "_SearchResultViewModel.java", new SearchResultViewModelBuilder(frameData));
    }

    private void createSettingDataViewModel(FrameData frameData) {

        String dir = distDir + "\\src\\main\\java_autogene\\" + Constants.PRJ_VIEW_MODEL_PACKAGE_SETTING_DIR;

        write(dir, dir + "\\" + frameData.getFrameId() + "_SettingDataViewModel.java", new SettingDataViewModelBuilder(frameData));
    }


    @SuppressWarnings("unused")
    private void createSettingDataViewModelImpl(FrameData frameData) {

        // 最初だけかな

        String dir = distDir + "\\src\\main\\java_autogene\\" + Constants.PRJ_VIEW_MODEL_PACKAGE_SETTING_DIR + "\\impl";

        write(dir, dir + "\\" + frameData.getFrameId() + "_SettingDataViewModelImpl.java", new SettingDataViewModelImplBuilder(frameData));
    }

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

        if (StringUtils.isEmpty(xls) || StringUtils.isEmpty(distDir)
                || StringUtils.isEmpty(framelistxls)) {

            System.out.println("引数異常");
            return;
        }

        if (!distDir.endsWith("\\")) {
            distDir += "\\";
        }
        distDir += CreateViewModel.class.getSimpleName() + "\\" + Constants.DIST_DIR_WEB;

        try {
            FileUtils.deleteDirectory(new File(distDir));
        } catch (IOException ioe) {
            ioe.printStackTrace();
            return;
        }

        new CreateViewModel(framelistxls, xls, distDir);

        System.out.println("");
        System.out.println("done.");
    }
}
