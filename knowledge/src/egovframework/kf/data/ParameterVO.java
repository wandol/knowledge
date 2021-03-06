package egovframework.kf.data;

import java.util.Arrays;

/**
 * Class Name : ParameterVO.java
 * Description : 검색엔진 파라미터 VO
 * Modification Information
 *
 * 수정일                        수정자           수정내용
 * --------------------  -----------  ---------------------------------------
 * 2017년 12월  00일                       최초 작성
 *
 * @since 2017년
 * @version V1.0
 * @see (c) Copyright (C) by KONANTECH All right reserved
 */
public class ParameterVO {

    /** 검색키워드 */
    private String kwd;
    
//    /** 이전검색어 배열 */
//    public String[] preKwds;
    
    /** 이전검색어 배열 */
    public String preKwds;
    
    /** 검색 카테고리(탭) */
    private String category;
    
    /** 검색 서브카테고리 */
    private String subCategory;
		
	/** 검색대상 필드 */
	private String srchFd;

	/** 유저 ID */
	private String userId;
	
	/** 사이트명 */
	private String siteNm;

    /** 추천검색어 정보 */
    private String recKwd;

    /** 재검색 여부 (boolean) */
    private boolean reSrchFlag;

    /** 페이지사이즈 */
    private int pageSize;
    
    /** 검색결과페이지번호 */
    private int pageNum;
    
    /** 검색결과페이지번호 */
    private int offSet;
    
    /** 정렬 */
    private String sort;
    
    /** 정렬명 */
    private String sortNm;

	/** 상세검색 여부 플래그 */
    private boolean detailSearch;
    
	/** 제외어 */
    private String exclusiveKwd;
  
	/** 날짜선택사항 */
    private String date;

	/** 시작일 */
	private String startDate;
	
	/** 종료일 */
	private String endDate;
	
	/** 첨부파일 확장자 */
    private String fileExt;
	
    /** 작성자 */
    private String writer;
    
    /** 검색결과 최종 값 */
    private int total;
    
    /** 연도- 배열*/
    private String year;
    
    /** 호출한 위치 */
    private String callLoc;
    
    /** 이미지 뷰어 rowid */
    private int imageRowNo;
    
    /** 시나리오 */
    private String scenario;
    
    /** 하이라이트 키워드 */
    private String hilightKwd;
 
    /** 오늘날짜 20160706 */
    private String nowDate;
    
    /** 검색영역 20160706*/
    private String fields;   
    
    /** 쿼리 추가*/
    private String originalQuery;
	private String previousQuery;
	private String[] previousQueries; 
	
	
	/**통합검색 발전사*/
	private String powerComp;
	
	/**통합검색 사업소*/
	private String powerSt;
	

	/**통합검색 호기*/
	private String stNo;
	
	/**통합검색 분야*/
	private String powerType;
	
	/**통합검색 보고서 종류*/
	private String repoKind;
	
	/**problem focus 키워드*/
	private String pfKwd;
	private String pfKwd2;
	private String pfKwd3;
	
	/**problem focus 구분 번호*/
	private String gubun_no;
	
	/**problem focus 상세 검색 진단부위*/
	private String partName;
	
	/**problem focus 상세 검색 손상위치*/
	private String partMid;
	
	/**problem focus 상세 검색 손상현상*/
	private String symptomKwd;
	
	/**problem focus 상세 검색 손상대책*/
	private String actionKwd;
	
	/**problem focus 상세 검색 시행일시 시작*/
	private String startPublishYm;
	
	/**problem focus 상세 검색 시행일시 시작*/
	private String endPublishYm;
	
	/** 가중치 필드 */
	private String weight;
	
	/** 보고서명 */
	private String repoName;
	
	/** 페이지명 */
	private String menu;
	
	private String isImg;
	
	/**
	 * 	지식탐색 키워드 클릭 검색시 사용.
	 */
	private String kwdList;
	
	public String getKwdList() {
		return kwdList;
	}

	public void setKwdList(String kwdList) {
		this.kwdList = kwdList;
	}

	public String getMenu() {
		return menu;
	}

	public void setMenu(String menu) {
		this.menu = menu;
	}

	public String getRepoName() {
		return repoName;
	}

	public void setRepoName(String repoName) {
		this.repoName = repoName;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public String getPfKwd2() {
		return pfKwd2;
	}

	public void setPfKwd2(String pfKwd2) {
		this.pfKwd2 = pfKwd2;
	}

	public String getPfKwd3() {
		return pfKwd3;
	}

	public void setPfKwd3(String pfKwd3) {
		this.pfKwd3 = pfKwd3;
	}

	public String getStartPublishYm() {
		return startPublishYm;
	}

	public void setStartPublishYm(String startPublishYm) {
		this.startPublishYm = startPublishYm;
	}

	public String getEndPublishYm() {
		return endPublishYm;
	}

	public void setEndPublishYm(String endPublishYm) {
		this.endPublishYm = endPublishYm;
	}

	public String getPfKwd() {
		return pfKwd;
	}
	
	public String getPartName() {
		return partName;
	}

	public void setPartName(String partName) {
		this.partName = partName;
	}

	public String getPartMid() {
		return partMid;
	}

	public void setPartMid(String partMid) {
		this.partMid = partMid;
	}

	public String getSymptomKwd() {
		return symptomKwd;
	}

	public void setSymptomKwd(String symptomKwd) {
		this.symptomKwd = symptomKwd;
	}

	public String getActionKwd() {
		return actionKwd;
	}

	public void setActionKwd(String actionKwd) {
		this.actionKwd = actionKwd;
	}

	public void setPfKwd(String pfKwd) {
		this.pfKwd = pfKwd;
	}
	
	public String getRepoKind() {
		return repoKind;
	}

	public void setRepoKind(String repoKind) {
		this.repoKind = repoKind;
	}

	public String getPowerComp() {
		return powerComp;
	}

	public void setPowerComp(String powerComp) {
		this.powerComp = powerComp;
	}

	public String getPowerSt() {
		return powerSt;
	}

	public void setPowerSt(String powerSt) {
		this.powerSt = powerSt;
	}

	public String getStNo() {
		return stNo;
	}

	public void setStNo(String stNo) {
		this.stNo = stNo;
	}

	public String getPowerType() {
		return powerType;
	}

	public void setPowerType(String powerType) {
		this.powerType = powerType;
	}

	public String getKwd() {
		return kwd;
	}

	public void setKwd(String kwd) {
		this.kwd = kwd;
	}
	
//	public String[] getPreKwds() {
//		return preKwds;
//	}

//	public void setPreKwds(String[] preKwds) {
//		this.preKwds = preKwds;
//	}
	
	
	
	public String getCategory() {
		return category;
	}

	public String getPreKwds() {
		return preKwds;
	}

	public void setPreKwds(String preKwds) {
		this.preKwds = preKwds;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getRecKwd() {
		return recKwd;
	}

	public void setRecKwd(String recKwd) {
		this.recKwd = recKwd;
	}

	public boolean getReSrchFlag() {
		return reSrchFlag;
	}

	public void setReSrchFlag(boolean reSrchFlag) {
		this.reSrchFlag = reSrchFlag;
	}

	public String getSrchFd() {
		return srchFd;
	}

	public void setSrchFd(String srchFd) {
		this.srchFd = srchFd;
	}
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getSiteNm() {
		return siteNm;
	}

	public void setSiteNm(String siteNm) {
		this.siteNm = siteNm;
	}
	
	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public int getOffSet() {
		return offSet;
	}

	public void setOffSet(int offSet) {
		this.offSet = offSet;
	}
	
	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}
		
    public String getSortNm() {
		return sortNm;
	}

	public void setSortNm(String sortNm) {
		this.sortNm = sortNm;
	}

	public boolean getDetailSearch() {
		return detailSearch;
	}

	public void setDetailSearch(boolean detailSearch) {
		this.detailSearch = detailSearch;
	}
	
	public String getExclusiveKwd() {
		return exclusiveKwd;
	}

	public void setExclusiveKwd(String exclusiveKwd) {
		this.exclusiveKwd = exclusiveKwd;
	}	
	  
    public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getFileExt() {
		return fileExt;
	}

	public void setFileExt(String fileExt) {
		this.fileExt = fileExt;
	}

	public String getWriter() {
		return writer;
	}

	public void setWriter(String writer) {
		this.writer = writer;
	}

	/**
	 * @return the total
	 */
	public int getTotal() {
		return total;
	}

	/**
	 * @param total the total to set
	 */
	public void setTotal(int total) {
		this.total = total;
	}

	/**
	 * @param count
	 */
	public void addTotal(int count) {
		this.total += count;
	}

	public String getSubCategory() {
		return subCategory;
	}

	public void setSubCategory(String subCategory) {
		this.subCategory = subCategory;
	}
	
	public String getYear() {
		return year;
	}
	
	public void setYear(String year) {
		this.year = year;
	}
	
	 public String getCallLoc() {
			return callLoc;
	}

	public void setCallLoc(String callLoc) {
		this.callLoc = callLoc;
	}

	public int getImageRowNo() {
		return imageRowNo;
	}

	public void setImageRowNo(int imageRowNo) {
		this.imageRowNo = imageRowNo;
	}

	public String getScenario() {
		return scenario;
	}

	public void setScenario(String scenario) {
		this.scenario = scenario;
	}

	public String getHilightKwd() {
		return hilightKwd;
	}

	public void setHilightKwd(String hilightKwd) {
		this.hilightKwd = hilightKwd;
	}
	
	public String getNowDate() {
		return nowDate;
	}

	public void setNowDate(String nowDate) {
		this.nowDate = nowDate;
	}
	
	public String getFields() {
		return fields;
	}

	public void setFields(String fields) {
		this.fields = fields;
	}
	
	public String getPreviousQuery() {
		return previousQuery;
	}

	public void setPreviousQuery(String previousQuery) {
		this.previousQuery = previousQuery;
	}
	
	public String getOriginalQuery() {
		return originalQuery;
	}

	public void setOriginalQuery(String originalQuery) {
		this.originalQuery = originalQuery;
	}
	
	public String[] getPreviousQueries() {
		return previousQueries;
	}

	public void setPreviousQueries(String[] previousQueries) {
		this.previousQueries = previousQueries;
	}

	public String getGubun_no() {
		return gubun_no;
	}

	public void setGubun_no(String gubun_no) {
		this.gubun_no = gubun_no;
	}
	
	

	public String getIsImg() {
		return isImg;
	}

	public void setIsImg(String isImg) {
		this.isImg = isImg;
	}

	@Override
	public String toString() {
		return "ParameterVO [kwd=" + kwd + ", preKwds=" + preKwds + ", category=" + category + ", subCategory="
				+ subCategory + ", srchFd=" + srchFd + ", userId=" + userId + ", siteNm=" + siteNm + ", recKwd="
				+ recKwd + ", reSrchFlag=" + reSrchFlag + ", pageSize=" + pageSize + ", pageNum=" + pageNum
				+ ", offSet=" + offSet + ", sort=" + sort + ", sortNm=" + sortNm + ", detailSearch=" + detailSearch
				+ ", exclusiveKwd=" + exclusiveKwd + ", date=" + date + ", startDate=" + startDate + ", endDate="
				+ endDate + ", fileExt=" + fileExt + ", writer=" + writer + ", total=" + total + ", year=" + year
				+ ", callLoc=" + callLoc + ", imageRowNo=" + imageRowNo + ", scenario=" + scenario + ", hilightKwd="
				+ hilightKwd + ", nowDate=" + nowDate + ", fields=" + fields + ", originalQuery=" + originalQuery
				+ ", previousQuery=" + previousQuery + ", previousQueries=" + Arrays.toString(previousQueries)
				+ ", powerComp=" + powerComp + ", powerSt=" + powerSt + ", stNo=" + stNo + ", powerType=" + powerType
				+ ", repoKind=" + repoKind + ", pfKwd=" + pfKwd + ", pfKwd2=" + pfKwd2 + ", pfKwd3=" + pfKwd3
				+ ", gubun_no=" + gubun_no + ", partName=" + partName + ", partMid=" + partMid + ", symptomKwd="
				+ symptomKwd + ", actionKwd=" + actionKwd + ", startPublishYm=" + startPublishYm + ", endPublishYm="
				+ endPublishYm + ", weight=" + weight + ", repoName=" + repoName + ", menu=" + menu + ", isImg=" + isImg
				+ "]";
	}
	
}