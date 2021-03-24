package egovframework.kf.user.service;

import java.util.List;
import java.util.Map;

import egovframework.kf.user.vo.UserVO;

public interface UserService {
	
	public int userCheck(Map<String, String> map) throws Exception;
	
	public UserVO selectUserInfo(Map<String, String> map) throws Exception;
	
	public List<UserVO> selectUserList(Map<String, Object> map) throws Exception;
	
	public int insertUser(UserVO userVO, String name) throws Exception;
	
	public int updateUser(UserVO userVO, String name) throws Exception;
	
	public int deleteUser(List list) throws Exception;
	
	public int selectUserCount() throws Exception;
	
	public List<Map> selectUserPartDivCount() throws Exception;
	
	public List<Map> selectUserPositionGDCount() throws Exception;
	
	public List<Map> selectUserAuthGBCount() throws Exception;
}
