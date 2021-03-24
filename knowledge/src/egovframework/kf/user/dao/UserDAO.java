package egovframework.kf.user.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import egovframework.kf.common.CommonUtil;
import egovframework.kf.common.DCUtil;
import egovframework.kf.user.vo.UserVO;
import egovframework.rte.fdl.property.EgovPropertyService;

/**
 * Class Name : SmartMatchDAO.java
 * Description : SmartMatch 관련 로직
 *
 * @since 2019년
 * @version V1.0
 * @see (c) Copyright (C) by KONANTECH All right reserved
 */
@Repository
public class UserDAO {
	private static final Logger logger = LoggerFactory.getLogger(UserDAO.class);
	
	@Resource(name = "sqlSessionTemplate")
    private SqlSessionTemplate sqlSession;
	
	/** 엔진 공통 유틸 */
	@Resource(name = "dcUtil")
	private DCUtil dcUtil;
	
	/** common util Setting */
	@Resource(name = "commonUtil")
	private CommonUtil commonUtil;
			
	/** EgovPropertyService */
	@Resource(name = "konanPropertiesService")
	protected EgovPropertyService konanPropertiesService;	

	public int userCheck(Map<String, String> map) throws Exception {		
		logger.debug("[userCheck] userCheck START");
		return sqlSession.selectOne("UserMapper.selectUserCheck", map);
	}
	
	public UserVO selectUserInfo(Map<String, String> map) throws Exception {		
		logger.debug("[selectUserInfo] selectUserInfo START");
		logger.debug("[selectUserInfo] user Info :: " + map.toString());
		return sqlSession.selectOne("UserMapper.selectUserInfo", map);
	}
	
	public List<UserVO> selectUserList(Map<String, Object> map) throws Exception {
		logger.debug("[selectUserList] selectUserList START");
		return sqlSession.selectList("UserMapper.selectUserList", map);
	}
	
	public int insertUser(UserVO userVO) throws Exception {
		logger.debug("[insertUser] insertUser START");
		logger.debug("userVO.getDept() == "+userVO.getDept());
		return sqlSession.insert("UserMapper.insertUser", userVO);
	}
	
	public int updateUser(UserVO userVO) throws Exception {
		logger.debug("[updateUser] updateUser START");
		return sqlSession.insert("UserMapper.updateUser", userVO);
	}
	
	public int deleteUser(List list) throws Exception {
		logger.debug("[deleteUser] deleteUserList START");
		return sqlSession.insert("UserMapper.deleteUser", list);
	}
	
	public int selectUserCount() throws Exception {
		logger.debug("[selectUserCount] selectUserCount START");
		return sqlSession.selectOne("UserMapper.selectUserCount");
	}
	
	public List<Map> selectUserPartDivCount() throws Exception {
		logger.debug("[selectUserPartDivCount] selectUserPartDivCount START");
		return sqlSession.selectList("UserMapper.selectUserPartDivCount");
	}
	
	public List<Map> selectUserPositionGDCount() throws Exception {
		logger.debug("[selectUserPositionGDCount] selectUserPositionGDCount START");
		return sqlSession.selectList("UserMapper.selectUserPositionGDCount");
	}
	
	public List<Map> selectUserAuthGBCount() throws Exception {
		logger.debug("[selectUserAuthGBCount] selectUserAuthGBCount START");
		return sqlSession.selectList("UserMapper.selectUserAuthGBCount");
	}
}
