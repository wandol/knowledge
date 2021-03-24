package egovframework.kf.kepri.vo;

import java.util.List;

public class DocCategoryVO {
	private String cat_idx;
	private String cat_nm;
	private int order_no;
	private String use;
	private String operator_id;
	private String operate_nm;
	private String operate_tm;
	private int row_count;
	private int file_count;
	
	private List<DocCategoryVO> docCateList;
	
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
	public int getOrder_no() {
		return order_no;
	}
	public void setOrder_no(int order_no) {
		this.order_no = order_no;
	}
	public String getUse() {
		return use;
	}
	public void setUse(String use) {
		this.use = use;
	}
	public String getOperator_id() {
		return operator_id;
	}
	public void setOperator_id(String operator_id) {
		this.operator_id = operator_id;
	}
	public String getOperate_nm() {
		return operate_nm;
	}
	public void setOperate_nm(String operate_nm) {
		this.operate_nm = operate_nm;
	}
	public String getOperate_tm() {
		return operate_tm;
	}
	public void setOperate_tm(String operate_tm) {
		this.operate_tm = operate_tm;
	}
	public List<DocCategoryVO> getDocCateList() {
		return docCateList;
	}
	public void setDocCateList(List<DocCategoryVO> docCateList) {
		this.docCateList = docCateList;
	}
	public int getRow_count() {
		return row_count;
	}
	public void setRow_count(int row_count) {
		this.row_count = row_count;
	}
	public int getFile_count() {
		return file_count;
	}
	public void setFile_count(int file_count) {
		this.file_count = file_count;
	}
	@Override
	public String toString() {
		return "DocCategoryVO [cat_idx=" + cat_idx + ", cat_nm=" + cat_nm + ", order_no=" + order_no + ", use="
				+ use + ", operator_id=" + operator_id + ", operate_nm=" + operate_nm + ", operate_tm=" + operate_tm
				+ ", row_count=" + row_count + ", file_count=" + file_count + ", docCateList=" + docCateList + "]";
	}
	
}
