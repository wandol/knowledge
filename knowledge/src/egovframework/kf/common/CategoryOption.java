package egovframework.kf.common;

// 각 카테고리 정보를 정의
public enum CategoryOption {
	TABLE_POST("post"),
	TABLE_PART("part"),
	
	//보일러
	BOILER_VOLUME("boiler_brk"),
	BOILER_DETAIL_VOLUME("boiler"),
	BOILER_TROUBLE_VOLUME("boiler_trouble_temp"),
	BOILER_FIELD("REPO_IDX,EQUIP_IDX,REPO_KIND,REPORT_NM,DEPT,REPORTER,PUBLISH_YM,REPO_FILE_NM,POWER_COMP_NM,POWER_ST_NM,ST_NO,ST_FORM,BUILT_YM,PRODUCTOR,FUEL,IMG_FILES,FILEBODY,FILEPATH,section,AUTO_CLASSFICATION,MD5_KEY,SIMILAR_YN"),
	BOILER_FROM("boiler_brk.post"),
	BOILER_HILIGHT("{'REPORT_NM':{'length':100,'begin':'<mark>','end':'</mark>'}},{'FILEBODY':{'length':400,'begin':'<mark>','end':'</mark>'}}"),
	BOILER_PFFIELD("REPO_IDX,EQUIP_IDX,REPO_KIND,REPORT_NM,DEPT,REPORTER,PUBLISH_YM,REPO_FILE_NM,POWER_COMP_NM,POWER_ST_NM,ST_NO,ST_FORM,BUILT_YM,PRODUCTOR,FUEL,FILEPATH,FILENAME,PARTNUM,PARTNAME2,PARTNAME,PART2NUM,PART2NAME,POSITION,SYMPTOM,ACTION,ETC,ETC_S,IMG_TXTS,IMG_FILES,PART2BODY,PART_MID,POSITION_KWD,POSITION2_KWD,SYMPTOM_KWD,ACTION_KWD,GUBUN_NO,PARTNAME_R,SYMPTOM_KWD_R,ACTION_KWD_R,MD5_KEY"),
	BOILER_PFFROM("boiler_brk.part"),
	BOILER_PFHILIGHT("{'REPORT_NM':{'length':100,'begin':'<mark>','end':'</mark>'}},{'SYMPTOM':{'length':300,'begin':'<mark>','end':'</mark>'}},{'PART2BODY':{'length':1000,'begin':'<mark>','end':'</mark>'}},{'REPO_FILE_NM_TXT':{'length':100,'begin':'<mark>','end':'</mark>'}},{'REPORTER_TXT':{'length':100,'begin':'<mark>','end':'</mark>'}}"),
	BOILER_PARTNAME("PARTNAME"),
	BOILER_PARTMID("PART_MID"),
	
	//터빈 - 공통
	TURBINE_FIELD("REPO_IDX,EQUIP_IDX,REPO_KIND,REPO_KIND2,REPORT_NM,DEPT,REPORTER,PUBLISH_YM,REPO_FILE_NM,POWER_COMP_NM,POWER_ST_NM,ST_NO,ST_FORM,BUILT_YM,PRODUCTOR,FUEL,IMG_FILES,FILEBODY,FILEPATH,section,AUTO_CLASSFICATION,MD5_KEY,SIMILAR_YN"),
	TURBINE_HILIGHT("{'REPORT_NM':{'length':100,'begin':'<mark>','end':'</mark>'}},{'FILEBODY':{'length':400,'begin':'<mark>','end':'</mark>'}}"),
	TURBINE_PFFIELD("REPO_IDX,EQUIP_IDX,REPO_KIND,REPO_KIND2,REPORT_NM,DEPT,REPORTER,PUBLISH_YM,REPO_FILE_NM,POWER_COMP_NM,POWER_ST_NM,ST_NO,ST_FORM,BUILT_YM,PRODUCTOR,FUEL,FILEPATH,FILENAME,PARTNUM,PARTNAME,PART2NUM,PART2NAME,PARTNAME_KWD,PART2NAME_KWD,PART2NAME_KWD2,PART2NAME_ETC,POSITION,SYMPTOM,ACTION,ETC,SYMPTOM_SENT,ACTION_SENT,PART2BODY,IMG_TXTS,IMG_FILES,POSITION_KWD,SYMPTOM_KWD,ACTION_KWD,GUBUN_NO,PARTNAME_R,PART2NAME_R,PART2NAME_ETC_R,SYMPTOM_KWD_R,ACTION_KWD_R,MD5_KEY"),
	TURBINE_PFHILIGHT("{'REPORT_NM':{'length':100,'begin':'<mark>','end':'</mark>'}},{'SYMPTOM':{'length':300,'begin':'<mark>','end':'</mark>'}},{'PART2BODY':{'length':1000,'begin':'<mark>','end':'</mark>'}},{'IMG_FILES':{'length':10000000,'begin':'','end':''}},{'PART2BODY':{'length':10000000,'begin':'','end':''}},{'REPO_FILE_NM_TXT':{'length':100,'begin':'<mark>','end':'</mark>'}},{'REPORTER_TXT':{'length':100,'begin':'<mark>','end':'</mark>'}}"),
	TURBINE_PARTNAME("PARTNAME_KWD"),
	TURBINE_PARTMID("PART2NAME_KWD2"),
	
	//가스터빈
	GT_TURBINE_VOLUME("gt_turbine_brk"),
	GT_TURBINE_DETAIL_VOLUME("gs_turbin_temp"),
	GT_TURBINE_TROUBLE_VOLUME("gs_trouble_temp"),
	GT_TURBINE_FROM("gs_turbine_brk.post"),
	GT_TURBINE_PFFROM("gs_turbine_brk.part"),

	//증기터빈
	ST_TURBINE_VOLUME("st_turbine_brk"),
	ST_TURBINE_DETAIL_VOLUME("st_turbine"),
	ST_TURBINE_TROUBLE_VOLUME("st_trouble_temp"),
	ST_TURBINE_FROM("st_turbine_brk.post"),
	ST_TURBINE_PFFROM("st_turbine_brk.part"),
	
	//발전기 - 공통
	GEN_FIELD("REPO_IDX,EQUIP_IDX,REPO_KIND,REPO_KIND2,REPORT_NM,DEPT,REPORTER,PUBLISH_YM,REPO_FILE_NM,POWER_COMP_NM,POWER_ST_NM,ST_NO,ST_FORM,BUILT_YM,PRODUCTOR,FUEL,IMG_FILES,FILEBODY,FILEPATH,section,AUTO_CLASSFICATION,MD5_KEY,SIMILAR_YN"),
	GEN_HILIGHT("{'REPORT_NM':{'length':100,'begin':'<mark>','end':'</mark>'}},{'FILEBODY':{'length':400,'begin':'<mark>','end':'</mark>'}}"),
	GEN_PARTNAME("PARTNAME_KWD"),
	GEN_PARTMID("PART2NAME_KWD"),
	
	//발전기 - 예방진단
	GEN_PREV_VOLUME("gen_prev_temp"),
	GEN_PREV_FROM("gen_prev_temp.post"),
	GEN_PREV_PFFROM("gen_prev_temp.part"),
	GEN_PREV_PFFIELD("REPO_IDX,EQUIP_IDX,REPO_KIND,REPO_KIND2,REPORT_NM,DEPT,REPORTER,PUBLISH_YM,REPO_FILE_NM,POWER_COMP_NM,POWER_ST_NM,ST_NO,ST_FORM,BUILT_YM,PRODUCTOR,FUEL,FILEPATH,FILENAME,PARTNUM,PARTNAME,PART2NUM,PART2NAME,PART2NAME_DETAIL,PARTNAME_KWD,PART2NAME_KWD,POSITION,SYMPTOM,ACTION,ETC,SYMPTOM_SENT,ACTION_SENT,PART2BODY,IMG_TXTS,IMG_FILES,POSITION_KWD,SYMPTOM_KWD,ACTION_KWD,GUBUN_NO,PARTNAME_R,PART2NAME_R,SYMPTOM_KWD_R,ACTION_KWD_R,PART2NAME_D_KWD,MD5_KEY"),
	GEN_PREV_PFHILIGHT("{'REPORT_NM':{'length':100,'begin':'<mark>','end':'</mark>'}},{'SYMPTOM':{'length':300,'begin':'<mark>','end':'</mark>'}},{'PART2BODY':{'length':1000,'begin':'<mark>','end':'</mark>'}},{'REPO_FILE_NM_TXT':{'length':100,'begin':'<mark>','end':'</mark>'}},{'REPORTER_TXT':{'length':100,'begin':'<mark>','end':'</mark>'}}"),
	
	//발전기 - 절연진단
	GEN_INS_VOLUME("gen_ins_temp"),
	GEN_INS_FROM("gen_ins_temp.post"),
	GEN_INS_PFFROM("gen_ins_temp.test_value"),
	GEN_INS_PFFIELD("REPO_IDX,EQUIP_IDX,REPO_KIND,REPO_KIND2,REPORT_NM,DEPT,REPORTER,PUBLISH_YM,REPO_FILE_NM,POWER_COMP_NM,POWER_ST_NM,ST_NO,ST_FORM,BUILT_YM,PRODUCTOR,FUEL,FILEPATH,FILENAME,GUBUN,EQUIP_NM,GEN_GUBUN,GEN_VOLT,PATTERN_IMG,TEST_VOLT,INDI_STD,TEST_ITEM_1,TEST_ITEM_2,TEST_ITEM_3,TEST_ITEM_4,TEST_ITEM_5,TEST_ITEM_6,SUMMARY,GUBUN_NO,MD5_KEY"),
	GEN_INS_PFHILIGHT("{'REPORT_NM':{'length':100,'begin':'<mark>','end':'</mark>'}},{'REPO_FILE_NM_TXT':{'length':100,'begin':'<mark>','end':'</mark>'}},{'REPORTER_TXT':{'length':100,'begin':'<mark>','end':'</mark>'}}"),
	
	//발전기-누설흡습
	KEPRI_VOLUME("kepri_temp"),
	KEPRI_FROM("kepri_temp.post"),
	KEPRI_PFFIELD("REPO_IDX,EQUIP_IDX,REPO_KIND,REPO_KIND2,REPORT_NM,DEPT,REPORTER,PUBLISH_YM,REPO_FILE_NM,POWER_COMP_NM,POWER_ST_NM,ST_NO,ST_FORM,BUILT_YM,PRODUCTOR,FUEL,IMG_FILES,FILEPATH,AUTO_CLASSFICATION,worksheet,section,abstract,keyword,keywordS,abstract_sfx,acc_cause,test_cause,symptom,repair,vaccum_dn_abstract,vaccum_dn_txt,vaccum_dn,vaccum_dn_leak,vaccum_dn_leak_avail,vaccum_dn_result,press_dn_abstract,press_dn_txt,press_dn,press_dn_leak,press_dn_leak_avail,press_dn_result,helium_abstract,helium_txt,helium,helium_result,absorp_abstract,absorp_txt,absorp,absorp_result,sym_act_txt,sym_act,FILEBODY_kwd,worksheet_kwd,FILEBODY_sna,worksheet_sna,MD5_KEY"),
	KEPRI_PFHILIGHT("{'REPORT_NM':{'length':100,'begin':'<mark>','end':'</mark>'}},{'REPO_FILE_NM_TXT':{'length':100,'begin':'<mark>','end':'</mark>'}},{'REPORTER_TXT':{'length':100,'begin':'<mark>','end':'</mark>'}}"),
	
	//성능
	PERFORM_VOLUME("perform_temp"),
	PERFORM_FROM("perform_temp.post"),
	PERFORM_FIELD("REPO_IDX,EQUIP_IDX,REPO_KIND,REPO_KIND2,REPORT_NM,DEPT,REPORTER,PUBLISH_YM,REPO_FILE_NM,POWER_COMP_NM,POWER_ST_NM,ST_NO,ST_FORM,BUILT_YM,PRODUCTOR,FUEL,IMG_FILES,FILEBODY,FILEPATH,AUTO_CLASSFICATION,MD5_KEY,SIMILAR_YN"),
	PERFORM_HILIGHT("{'REPORT_NM':{'length':100,'begin':'<mark>','end':'</mark>'}},{'FILEBODY':{'length':400,'begin':'<mark>','end':'</mark>'}},{'REPO_FILE_NM_TXT':{'length':100,'begin':'<mark>','end':'</mark>'}},{'REPORTER_TXT':{'length':100,'begin':'<mark>','end':'</mark>'}}"),
	
	//발전문서
	GEN_DOC_VOLUME("gen_doc"),
	GEN_DOC_FROM("gen_doc.gen_doc"),
	GEN_DOC_FILED("DOC_IDX,CAT_IDX,CAT_NM,TTL,ABSTRACT,KWD,ATT_CNT,DEL_YN,REG_ID,REG_NM,REG_TM,OPERATE_TM,FILE_LIST,FILE_PATH,FILE_BODY"),
	GEN_DOC_HILIGHT("{'TTL':{'length':500,'begin':'<mark>','end':'</mark>'}},{'ABSTRACT':{'length':10000000,'begin':'<mark>','end':'</mark>'}}"),
	
	EMPTY("");
	
	final private String name;
	
	public String getName() {
		return name;
	}
	
	private CategoryOption(String name) {
		this.name = name;
	}
}
