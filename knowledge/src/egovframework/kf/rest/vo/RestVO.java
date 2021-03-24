package egovframework.kf.rest.vo;

public class RestVO {
	private String req_sys_nm;
	private String cate;
	private String power_comp_nm;
	private String power_st_nm;
	private String st_no;
	private String repo_kind;
	private int limit;
	private int page;
	private String publish_ym_s;
	private String publish_ym_e;
	
	private String partname;
	private String symptom;
	private String action;
	
	private String partid;
	private String gubun_no;
	
	private String kwd;
	private String user;
	
	/** 분석 파라메터 **/
	private String file_nm;
	private String text;
	private String title;
	
	
	public String getKwd() {
		return kwd;
	}
	public void setKwd(String kwd) {
		this.kwd = kwd;
	}
	public String getPartid() {
		return partid;
	}
	public void setPartid(String partid) {
		this.partid = partid;
	}
	public String getSymptom() {
		return symptom;
	}
	public void setSymptom(String symptom) {
		this.symptom = symptom;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getPartname() {
		return partname;
	}
	public void setPartname(String partname) {
		this.partname = partname;
	}
	RestVO(){
		this.limit = 10;
		this.page =1;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public String getReq_sys_nm() {
		return req_sys_nm;
	}
	public void setReq_sys_nm(String req_sys_nm) {
		this.req_sys_nm = req_sys_nm;
	}
	public String getCate() {
		return cate;
	}
	public void setCate(String cate) {
		this.cate = cate;
	}
	public String getPower_comp_nm() {
		return power_comp_nm;
	}
	public void setPower_comp_nm(String power_comp_nm) {
		this.power_comp_nm = power_comp_nm;
	}
	public String getPower_st_nm() {
		return power_st_nm;
	}
	public void setPower_st_nm(String power_st_nm) {
		this.power_st_nm = power_st_nm;
	}
	public String getSt_no() {
		return st_no;
	}
	public void setSt_no(String st_no) {
		this.st_no = st_no;
	}
	public int getLimit() {
		return limit;
	}
	public void setLimit(int limit) {
		this.limit = limit;
	}
	public String getPublish_ym_s() {
		return publish_ym_s;
	}
	public void setPublish_ym_s(String publish_ym_s) {
		this.publish_ym_s = publish_ym_s;
	}
	public String getPublish_ym_e() {
		return publish_ym_e;
	}
	public void setPublish_ym_e(String publish_ym_e) {
		this.publish_ym_e = publish_ym_e;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getRepo_kind() {
		return repo_kind;
	}
	public void setRepo_kind(String repo_kind) {
		this.repo_kind = repo_kind;
	}
	public String getGubun_no() {
		return gubun_no;
	}
	public void setGubun_no(String gubun_no) {
		this.gubun_no = gubun_no;
	}
	public String getFile_nm() {
		return file_nm;
	}
	public void setFile_nm(String file_nm) {
		this.file_nm = file_nm;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
}
