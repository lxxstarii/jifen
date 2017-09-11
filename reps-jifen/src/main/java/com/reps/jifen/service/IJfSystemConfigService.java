package com.reps.jifen.service;

import java.util.List;

import com.reps.core.exception.RepsException;
import com.reps.core.orm.ListResult;
import com.reps.jifen.entity.JfSystemConfig;

public interface IJfSystemConfigService {

    ListResult<JfSystemConfig> query(Integer startRow, Integer pageSize, JfSystemConfig confif);

    void delete(String id);

    boolean save(JfSystemConfig config);

    boolean update(JfSystemConfig config);

    JfSystemConfig get(String id);

    long count(JfSystemConfig config);
    
    /**
	 * 查询接口
	 * @author qianguobing
	 * @date  2017年7月24日 下午4:57:03
	 */
    List<JfSystemConfig> queryAll(JfSystemConfig config) throws RepsException;
}
