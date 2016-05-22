//package alpha114.tool.cichlid.autogenerationtool;
//
//
///**
// * @author kurisakisatoshi
// */
//public class Constants2 {
//
//    public static final String DIST_DIR_COMMON = Constants.DIST_DIR_COMMON;
//    public static final String DIST_DIR_WEB = Constants.DIST_DIR_WEB;
//
//
//    public static final String[] DB_INSTANCE_NAME = Constants.DB_INSTANCE_NAME;
//
//    public static final String BASE_PACKAGE = Constants.BASE_PACKAGE;
//    public static final String BASE_PACKAGE_DIR = BASE_PACKAGE.replaceAll("\\.", "\\\\");
//
//    public static final String ENTITY_PACKAGE = BASE_PACKAGE + ".common.entity";
//    public static final String ENTITY_PACKAGE_DIR = ENTITY_PACKAGE.replaceAll("\\.", "\\\\");
//    public static final String PRJ_ENTITY_PACKAGE = Constants.PRJ_BASE_PACKAGE + ".entity";
//    public static final String PRJ_ENTITY_PACKAGE_DIR = PRJ_ENTITY_PACKAGE.replaceAll("\\.", "\\\\");
//
//    public static final String COMPARATOR_PACKAGE = BASE_PACKAGE + ".common.util.comparator";
//    public static final String COMPARATOR_PACKAGE_DIR = COMPARATOR_PACKAGE.replaceAll("\\.", "\\\\");
//
//    public static final String RELATION_PACKAGE = ENTITY_PACKAGE + ".common.relation";
//    public static final String RELATION_PACKAGE_DIR = RELATION_PACKAGE.replaceAll("\\.", "\\\\");
//
//    public static final String DAO_PACKAGE = BASE_PACKAGE + ".common.dao";
//    public static final String DAO_PACKAGE_DIR = DAO_PACKAGE.replaceAll("\\.", "\\\\");
//
//    public static final String DAO_CUD_PACKAGE = BASE_PACKAGE + ".common.dao.cud";
//    public static final String DAO_CUD_PACKAGE_DIR = DAO_CUD_PACKAGE.replaceAll("\\.", "\\\\");
//
//    public static final String WEB_PACKAGE = BASE_PACKAGE + ".web";
//    public static final String WEB_PACKAGE_DIR = WEB_PACKAGE.replaceAll("\\.", "\\\\");
//
//    public static final String WEB_MESSAGE_ID_PACKAGE = BASE_PACKAGE + ".web.view";
//    public static final String WEB_MESSAGE_ID_PACKAGE_DIR = WEB_MESSAGE_ID_PACKAGE.replaceAll("\\.", "\\\\");
//
//    public static final String WEB_CONDITION_FORM_PACKAGE = BASE_PACKAGE + ".web.form.searchcondition";
//    public static final String WEB_CONDITION_FORM_PACKAGE_DIR = WEB_CONDITION_FORM_PACKAGE.replaceAll("\\.", "\\\\");
//    public static final String PRJ_WEB_CONDITION_FORM_PACKAGE = Constants.PRJ_BASE_PACKAGE + ".web.form.searchcondition";
//    public static final String PRJ_WEB_CONDITION_FORM_PACKAGE_DIR = PRJ_WEB_CONDITION_FORM_PACKAGE.replaceAll("\\.", "\\\\");
//
//    public static final String WEB_SETTING_FORM_PACKAGE = BASE_PACKAGE + ".web.form.setting";
//    public static final String WEB_SETTING_FORM_PACKAGE_DIR = WEB_SETTING_FORM_PACKAGE.replaceAll("\\.", "\\\\");
//    public static final String PRJ_WEB_SETTING_FORM_PACKAGE = Constants.PRJ_BASE_PACKAGE + ".web.form.setting";
//    public static final String PRJ_WEB_SETTING_FORM_PACKAGE_DIR = PRJ_WEB_SETTING_FORM_PACKAGE.replaceAll("\\.", "\\\\");
//
//    public static final String VIEW_MODEL_PACKAGE = BASE_PACKAGE + ".viewmodel";
//    public static final String VIEW_MODEL_PACKAGE_DIR = VIEW_MODEL_PACKAGE.replaceAll("\\.", "\\\\");
//    public static final String PRJ_VIEW_MODEL_PACKAGE = Constants.PRJ_BASE_PACKAGE + ".viewmodel";
//    public static final String PRJ_VIEW_MODEL_PACKAGE_DIR = PRJ_VIEW_MODEL_PACKAGE.replaceAll("\\.", "\\\\");
//
//    public static final String VIEW_MODEL_PACKAGE_SETTING = BASE_PACKAGE + ".viewmodel.setting";
//    public static final String VIEW_MODEL_PACKAGE_SETTING_DIR = VIEW_MODEL_PACKAGE_SETTING.replaceAll("\\.", "\\\\");
//    public static final String PRJ_VIEW_MODEL_PACKAGE_SETTING = Constants.PRJ_BASE_PACKAGE + ".viewmodel.setting";
//    public static final String PRJ_VIEW_MODEL_PACKAGE_SETTING_DIR = PRJ_VIEW_MODEL_PACKAGE_SETTING.replaceAll("\\.", "\\\\");
//
//    public static final String VIEW_MODEL_PACKAGE_DETAIL = BASE_PACKAGE + ".viewmodel.detail";
//    public static final String VIEW_MODEL_PACKAGE_DETAIL_DIR = VIEW_MODEL_PACKAGE_DETAIL.replaceAll("\\.", "\\\\");
//
//    public static final String VIEW_MODEL_PACKAGE_SEARCHRESULT = BASE_PACKAGE + ".viewmodel.searchresult";
//    public static final String VIEW_MODEL_PACKAGE_SEARCHRESULT_DIR = VIEW_MODEL_PACKAGE_SEARCHRESULT.replaceAll("\\.", "\\\\");
//    public static final String PRJ_VIEW_MODEL_PACKAGE_SEARCHRESULT = Constants.PRJ_BASE_PACKAGE + ".viewmodel.searchresult";
//    public static final String PRJ_VIEW_MODEL_PACKAGE_SEARCHRESULT_DIR = PRJ_VIEW_MODEL_PACKAGE_SEARCHRESULT.replaceAll("\\.", "\\\\");
//
//    public static final String VIEW_MODEL_PACKAGE_SEARCHCONDITION = BASE_PACKAGE + ".viewmodel.searchcondition";
//    public static final String VIEW_MODEL_PACKAGE_SEARCHCONDITION_DIR = VIEW_MODEL_PACKAGE_SEARCHCONDITION.replaceAll("\\.", "\\\\");
//    public static final String PRJ_VIEW_MODEL_PACKAGE_SEARCHCONDITION = Constants.PRJ_BASE_PACKAGE + ".viewmodel.searchcondition";
//    public static final String PRJ_VIEW_MODEL_PACKAGE_SEARCHCONDITION_DIR = PRJ_VIEW_MODEL_PACKAGE_SEARCHCONDITION.replaceAll("\\.", "\\\\");
//
//    public static final String VIEW_MODEL_PACKAGE_TABLE = BASE_PACKAGE + ".viewmodel.table";
//    public static final String VIEW_MODEL_PACKAGE_TABLE_DIR = VIEW_MODEL_PACKAGE_TABLE.replaceAll("\\.", "\\\\");
//    public static final String PRJ_VIEW_MODEL_PACKAGE_TABLE = Constants.PRJ_BASE_PACKAGE + ".viewmodel.table";
//    public static final String PRJ_VIEW_MODEL_PACKAGE_TABLE_DIR = PRJ_VIEW_MODEL_PACKAGE_TABLE.replaceAll("\\.", "\\\\");
//
//    public static final String VIEW_MODEL_PACKAGE_ASSOC = BASE_PACKAGE + ".viewmodel.assoc";
//    public static final String VIEW_MODEL_PACKAGE_ASSOC_DIR = VIEW_MODEL_PACKAGE_ASSOC.replaceAll("\\.", "\\\\");
//
//    public static final String PROPRTY_CONF_PACKAGE = BASE_PACKAGE + ".common.property";
//    public static final String PROPRTY_CONF_PACKAGE_DIR = PROPRTY_CONF_PACKAGE.replaceAll("\\.", "\\\\");
//    public static final String PRJ_PROPRTY_CONF_PACKAGE = Constants.PRJ_BASE_PACKAGE + ".property";
//    public static final String PRJ_PROPRTY_CONF_PACKAGE_DIR = PRJ_PROPRTY_CONF_PACKAGE.replaceAll("\\.", "\\\\");
//
//    public static final String DTO_PACKAGE = BASE_PACKAGE + ".common.dto";
//    public static final String DTO_PACKAGE_DIR = DTO_PACKAGE.replaceAll("\\.", "\\\\");
//
//    public static final String[] date = new String[]{"YYYY", "MM", "DD", "HH", "MI"};
//
//
//    public static final String[] COMMON_BUTTONS = new String[]{"カレンダー", "検索", "検索条件クリア", "先頭のページ", "前ページ", "次ページ", "最後のページ", "ページ移動", "XML出力", "CSV出力"};
//
//    private Constants2() {
//    }
//}
