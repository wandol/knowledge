package egovframework.kf.user.vo;

public class UserVO {
	private String user_id;
	private String user_nm;
	private String user_pw;
	private String dept;
	private String part_div;
	private char admin_yn;
	private char del_yn;
	private String create_dt;
	private String mod_dt;
	private String creater;
	private String modifier;
	private String position;
	private String position_gd;
	private String tel;
	private String mobile;
	private String auth_gb;
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getUser_nm() {
		return user_nm;
	}
	public void setUser_nm(String user_nm) {
		this.user_nm = user_nm;
	}
	public String getUser_pw() {
		return user_pw;
	}
	public void setUser_pwd(String user_pw) {
		this.user_pw = user_pw;
	}
	public String getDept() {
		return dept;
	}
	public void setDept(String dept) {
		this.dept = dept;
	}
	public String getPart_div() {
		return part_div;
	}
	public void setPart_div(String part_div) {
		this.part_div = part_div;
	}
	public char getAdmin_yn() {
		return admin_yn;
	}
	public void setAdmin_yn(char admin_yn) {
		this.admin_yn = admin_yn;
	}
	public char getDel_yn() {
		return del_yn;
	}
	public void setDel_yn(char del_yn) {
		this.del_yn = del_yn;
	}
	public String getCreate_dt() {
		return create_dt;
	}
	public void setCreate_dt(String create_dt) {
		this.create_dt = create_dt;
	}
	public String getMod_dt() {
		return mod_dt;
	}
	public void setMod_dt(String mod_dt) {
		this.mod_dt = mod_dt;
	}
	public String getCreater() {
		return creater;
	}
	public void setCreater(String creater) {
		this.creater = creater;
	}
	public String getModifier() {
		return modifier;
	}
	public void setModifier(String modifier) {
		this.modifier = modifier;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getPosition_gd() {
		return position_gd;
	}
	public void setPosition_gd(String position_gd) {
		this.position_gd = position_gd;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getAuth_gb() {
		return auth_gb;
	}
	public void setAuth_gb(String auth_gb) {
		this.auth_gb = auth_gb;
	}
	@Override
	public String toString() {
		return "UserVO [user_id=" + user_id + ", user_nm=" + user_nm + ", user_pw=" + user_pw + ", dept=" + dept
				+ ", part_div=" + part_div + ", admin_yn=" + admin_yn + ", del_yn=" + del_yn + ", create_dt="
				+ create_dt + ", mod_dt=" + mod_dt + ", creater=" + creater + ", modifier=" + modifier + ", position="
				+ position + ", position_gd=" + position_gd + ", tel=" + tel + ", mobile=" + mobile + ", auth_gb="
				+ auth_gb + "]";
	}
	
	
}
