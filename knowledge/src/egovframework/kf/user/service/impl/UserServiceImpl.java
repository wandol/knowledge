package egovframework.kf.user.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.kf.user.dao.UserDAO;
import egovframework.kf.user.service.UserService;
import egovframework.kf.user.vo.UserVO;

@Service("userService")
public class UserServiceImpl implements UserService {

	@Resource(name = "userDAO")
	private UserDAO userDAO;

	@Override
	public int userCheck(Map<String, String> map) throws Exception {
		// TODO Auto-generated method stub
		return userDAO.userCheck(map);
	}

	@Override
	public UserVO selectUserInfo(Map<String, String> map) throws Exception {
		// TODO Auto-generated method stub
		return userDAO.selectUserInfo(map);
	}

	@Override
	public List<UserVO> selectUserList(Map<String, Object> map) throws Exception {
		// TODO Auto-generated method stub
		return userDAO.selectUserList(map);
	}

	@Override
	public int insertUser(UserVO userVO, String name) throws Exception {
		// TODO Auto-generated method stub
		
		userVO.setUser_pwd("1234");
		userVO.setCreater(name);
		userVO.setModifier(name);
		
		return userDAO.insertUser(userVO);
	}

	@Override
	public int updateUser(UserVO userVO, String name) throws Exception {
		// TODO Auto-generated method stub
		userVO.setModifier(name);	
		
		return userDAO.updateUser(userVO);
	}

	@Override
	public int deleteUser(List list) throws Exception {
		// TODO Auto-generated method stub
		return userDAO.deleteUser(list);
	}

	@Override
	public int selectUserCount() throws Exception {
		// TODO Auto-generated method stub
		return userDAO.selectUserCount();
	}

	@Override
	public List<Map> selectUserPartDivCount() throws Exception {
		// TODO Auto-generated method stub
		return userDAO.selectUserPartDivCount();
	}

	@Override
	public List<Map> selectUserPositionGDCount() throws Exception {
		// TODO Auto-generated method stub
		return userDAO.selectUserPositionGDCount();
	}

	@Override
	public List<Map> selectUserAuthGBCount() throws Exception {
		// TODO Auto-generated method stub
		return userDAO.selectUserAuthGBCount();
	}
	
}
