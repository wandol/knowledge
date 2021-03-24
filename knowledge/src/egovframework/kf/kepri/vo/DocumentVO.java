package egovframework.kf.kepri.vo;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class DocumentVO {
	private String doc_idx;
	private String cat_idx;
	private String cat_nm;
	private String docTitle;
	private String docContents;
	private String docKeyword;
	private int att_cnt;
	private char del_yn;
	private String reg_id;
	private String reg_nm;
	private String reg_tm;
	private String operate_tm;
	private String file_nm;
	private String file_list;
	private String file_path;
	
	// file
	private List<MultipartFile> files;

	public String getDoc_idx() {
		return doc_idx;
	}

	public void setDoc_idx(String doc_idx) {
		this.doc_idx = doc_idx;
	}

	public String getCat_idx() {
		return cat_idx;
	}

	public void setCat_idx(String cat_idx) {
		this.cat_idx = cat_idx;
	}

	public String getCat_nm() {
		return cat_nm;
	}

	public void setCat_nm(String cat_nm) {
		this.cat_nm = cat_nm;
	}

	public String getDocTitle() {
		return docTitle;
	}

	public void setDocTitle(String docTitle) {
		this.docTitle = docTitle;
	}

	public String getDocContents() {
		return docContents;
	}

	public void setDocContents(String docContents) {
		this.docContents = docContents;
	}

	public String getDocKeyword() {
		return docKeyword;
	}

	public void setDocKeyword(String docKeyword) {
		this.docKeyword = docKeyword;
	}

	public int getAtt_cnt() {
		return att_cnt;
	}

	public void setAtt_cnt(int att_cnt) {
		this.att_cnt = att_cnt;
	}

	public char getDel_yn() {
		return del_yn;
	}

	public void setDel_yn(char del_yn) {
		this.del_yn = del_yn;
	}

	public String getReg_id() {
		return reg_id;
	}

	public void setReg_id(String reg_id) {
		this.reg_id = reg_id;
	}

	public String getReg_nm() {
		return reg_nm;
	}

	public void setReg_nm(String reg_nm) {
		this.reg_nm = reg_nm;
	}

	public String getReg_tm() {
		return reg_tm;
	}

	public void setReg_tm(String reg_tm) {
		this.reg_tm = reg_tm;
	}

	public String getOperate_tm() {
		return operate_tm;
	}

	public void setOperate_tm(String operate_tm) {
		this.operate_tm = operate_tm;
	}

	public List<MultipartFile> getFiles() {
		return files;
	}

	public void setFiles(List<MultipartFile> files) {
		this.files = files;
	}

	public String getFile_nm() {
		return file_nm;
	}

	public void setFile_nm(String file_nm) {
		this.file_nm = file_nm;
	}
	
	public String getFile_list() {
		return file_list;
	}

	public void setFile_list(String file_list) {
		this.file_list = file_list;
	}

	public String getFile_path() {
		return file_path;
	}

	public void setFile_path(String file_path) {
		this.file_path = file_path;
	}

	@Override
	public String toString() {
		return "DocumentVO [doc_idx=" + doc_idx + ", cat_idx=" + cat_idx + ", cat_nm=" + cat_nm + ", docTitle="
				+ docTitle + ", docContents=" + docContents + ", docKeyword=" + docKeyword + ", att_cnt=" + att_cnt
				+ ", del_yn=" + del_yn + ", reg_id=" + reg_id + ", reg_nm=" + reg_nm + ", reg_tm=" + reg_tm
				+ ", operate_tm=" + operate_tm + ", file_nm=" + file_nm + ", file_list=" + file_list + ", file_path="
				+ file_path + ", files=" + files + "]";
	}
}
