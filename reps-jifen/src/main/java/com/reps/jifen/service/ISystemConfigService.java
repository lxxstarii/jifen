package com.reps.jifen.service;

import java.util.List;

import com.reps.core.exception.RepsException;
import com.reps.core.orm.ListResult;
import com.reps.jifen.entity.SystemConfig;

public interface ISystemConfigService {

    ListResult<SystemConfig> query(Integer startRow, Integer pageSize, SystemConfig confif);

    void delete(String id);

    boolean save(SystemConfig config);

    boolean update(SystemConfig config);

    SystemConfig get(String id);

    long count(SystemConfig config);
    
    /**
	 * 查询接口
	 * @author qianguobing
	 * @date  2017年7月24日 下午4:57:03
	 */
    List<SystemConfig> queryAll(SystemConfig config) throws RepsException;
}
