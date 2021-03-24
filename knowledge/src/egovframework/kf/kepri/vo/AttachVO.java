package egovframework.kf.kepri.vo;

public class AttachVO {
	private String doc_idx;
	private int seq;
	private String file_nm;
	private String file_path;
	private int file_size;
	private char del_yn;
	private String reg_id;
	private String reg_nm;
	private String reg_tm;
	private String operate_tm;
	
	public String getDoc_idx() {
		return doc_idx;
	}
	public void setDoc_idx(String doc_idx) {
		this.doc_idx = doc_idx;
	}
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public String getFile_nm() {
		return file_nm;
	}
	public void setFile_nm(String file_nm) {
		this.file_nm = file_nm;
	}
	public String getFile_path() {
		return file_path;
	}
	public void setFile_path(String file_path) {
		this.file_path = file_path;
	}
	public int getFile_size() {
		return file_size;
	}
	public void setFile_size(int file_size) {
		this.file_size = file_size;
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
	@Override
	public String toString() {
		return "AttachVO [doc_idx=" + doc_idx + ", seq=" + seq + ", file_nm=" + file_nm + ", file_size=" + file_size
				+ ", del_yn=" + del_yn + ", reg_id=" + reg_id + ", reg_nm=" + reg_nm + ", reg_tm=" + reg_tm
				+ ", operate_tm=" + operate_tm + "]";
	}
	
	
}
