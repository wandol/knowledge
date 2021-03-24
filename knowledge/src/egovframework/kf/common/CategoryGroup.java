package egovframework.kf.common;


/**
 * 카테고리 값에 따라 option을 정의
 * @author konan
 *
 */
public enum CategoryGroup {
	BOILER("보일러", new CategoryOption[] {
		CategoryOption.BOILER_FIELD, 
		CategoryOption.BOILER_FROM, 
		CategoryOption.BOILER_HILIGHT, 
		CategoryOption.BOILER_PFFIELD, 
		CategoryOption.BOILER_PFFROM, 
		CategoryOption.BOILER_PFHILIGHT, 
		CategoryOption.BOILER_PARTNAME, 
		CategoryOption.BOILER_PARTMID,
		CategoryOption.BOILER_DETAIL_VOLUME,
		CategoryOption.BOILER_TROUBLE_VOLUME
	}),
	GT_TURBINE("가스터빈", new CategoryOption[] {
		CategoryOption.TURBINE_FIELD, 
		CategoryOption.GT_TURBINE_FROM, 
		CategoryOption.TURBINE_HILIGHT, 
		CategoryOption.TURBINE_PFFIELD, 
		CategoryOption.GT_TURBINE_PFFROM, 
		CategoryOption.TURBINE_PFHILIGHT, 
		CategoryOption.TURBINE_PARTNAME, 
		CategoryOption.TURBINE_PARTMID,
		CategoryOption.GT_TURBINE_DETAIL_VOLUME,
		CategoryOption.GT_TURBINE_TROUBLE_VOLUME 
	}),
	ST_TURBINE("증기터빈", new CategoryOption[] {
		CategoryOption.TURBINE_FIELD, 
		CategoryOption.ST_TURBINE_FROM, 
		CategoryOption.TURBINE_HILIGHT, 
		CategoryOption.TURBINE_PFFIELD, 
		CategoryOption.ST_TURBINE_PFFROM, 
		CategoryOption.TURBINE_PFHILIGHT, 
		CategoryOption.TURBINE_PARTNAME, 
		CategoryOption.TURBINE_PARTMID,
		CategoryOption.ST_TURBINE_DETAIL_VOLUME,
		CategoryOption.ST_TURBINE_TROUBLE_VOLUME 
	}),
	GEN_PREV("발전기_예방진단", new CategoryOption[] {
		CategoryOption.GEN_FIELD, 
		CategoryOption.GEN_PREV_FROM, 
		CategoryOption.GEN_HILIGHT, 
		CategoryOption.GEN_PREV_PFFIELD, 
		CategoryOption.GEN_PREV_PFFROM, 
		CategoryOption.GEN_PREV_PFHILIGHT, 
		CategoryOption.GEN_PARTNAME, 
		CategoryOption.GEN_PARTMID,
		CategoryOption.GEN_PREV_VOLUME
	}),
	GEN_INS("발전기_절연진단", new CategoryOption[] {
		CategoryOption.GEN_FIELD, 
		CategoryOption.GEN_INS_FROM, 
		CategoryOption.GEN_HILIGHT, 
		CategoryOption.GEN_INS_PFFIELD, 
		CategoryOption.GEN_INS_PFFROM, 
		CategoryOption.GEN_INS_PFHILIGHT, 
		CategoryOption.GEN_PARTNAME, 
		CategoryOption.GEN_PARTMID,
		CategoryOption.GEN_INS_VOLUME
	}),
	KEPRI("발전기_누설흡습", new CategoryOption[] {
		CategoryOption.GEN_FIELD, 
		CategoryOption.KEPRI_FROM, 
		CategoryOption.GEN_HILIGHT, 
		CategoryOption.KEPRI_PFFIELD, 
		CategoryOption.KEPRI_FROM, 
		CategoryOption.KEPRI_PFHILIGHT, 
		CategoryOption.GEN_PARTNAME, 
		CategoryOption.GEN_PARTMID,
		CategoryOption.KEPRI_VOLUME
	}),
	PERFORM("성능", new CategoryOption[] {
		CategoryOption.PERFORM_FIELD, 
		CategoryOption.PERFORM_FROM, 
		CategoryOption.PERFORM_HILIGHT, 
		CategoryOption.EMPTY, 
		CategoryOption.EMPTY, 
		CategoryOption.EMPTY, 
		CategoryOption.EMPTY, 
		CategoryOption.EMPTY,
		CategoryOption.PERFORM_VOLUME
	}),
	GEN_DOC("발전문서", new CategoryOption[] {
		CategoryOption.GEN_DOC_FILED, 
		CategoryOption.GEN_DOC_FROM, 
		CategoryOption.GEN_DOC_HILIGHT, 
		CategoryOption.EMPTY, 
		CategoryOption.EMPTY, 
		CategoryOption.EMPTY, 
		CategoryOption.EMPTY, 
		CategoryOption.EMPTY,
		CategoryOption.GEN_DOC_VOLUME
	});
     
    final private String name;
    final private CategoryOption[] containsCategory;
     
    public String getName() {
        return name;
    }
    
    // 카테고리 정보(List)를 return
    public CategoryOption[] getContainsCategory() {
    	return containsCategory;
    }
 
    private CategoryGroup(String name, CategoryOption[] containsCategory){
        this.name = name;
        this.containsCategory = containsCategory;
    }
	
}
