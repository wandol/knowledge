package egovframework.kf.common.dao;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import egovframework.kf.common.vo.LogVO;
import egovframework.kf.common.vo.RecentVO;
import egovframework.rte.fdl.property.EgovPropertyService;

@Repository("logDAO")
public class LogDAO{
	
	//@Autowired
	@Resource(name = "sqlSessionTemplate")
    private SqlSessionTemplate sqlSession;
	
	/** EgovPropertyService */
	@Resource(name = "konanPropertiesService")
	private EgovPropertyService konanPropertiesService;
	
	/**
	 * 파일 다운로드 log 남기기
	 * @param logVO
	 * @return
	 * @throws Exception
	 */
	public int insertFiledownLog(LogVO logVO) throws Exception {
		return sqlSession.insert("LogMapper.insertDownloadHistory", logVO);
	}
	
	/**
	 * 최근 본 지식 history log 남기기
	 * @param recentVO
	 * @return
	 * @throws Exception
	 */
	public int insertRecentKnowledgeLog(RecentVO recentVO) throws Exception {
		return sqlSession.insert("LogMapper.insertRecentHistory", recentVO);
	}
}