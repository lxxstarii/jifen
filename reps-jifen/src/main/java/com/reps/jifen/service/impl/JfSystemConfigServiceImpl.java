package com.reps.jifen.service.impl;

import com.reps.core.exception.RepsException;
import com.reps.core.orm.ListResult;
import com.reps.core.util.StringUtil;
import com.reps.jifen.dao.JfSystemConfigDao;
import com.reps.jifen.entity.JfSystemConfig;
import com.reps.jifen.service.IJfSystemConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import javax.transaction.Transactional;

/**
 * Created on 2017/9/8.
 */
@Service
@Transactional
public class JfSystemConfigServiceImpl implements IJfSystemConfigService {

    @Autowired
    JfSystemConfigDao configDao;

    @Override
    public ListResult<JfSystemConfig> query(Integer startRow, Integer pageSize, JfSystemConfig config) {
        return configDao.query(startRow, pageSize, config);
    }

    @Override
    public void delete(String id) {
        if (StringUtil.isNotBlank(id)) {
            configDao.delete(id);
        }
    }

    @Override
    public void save(JfSystemConfig config) {
        configDao.save(config);
    }

    @Override
    public void update(JfSystemConfig config) {
        configDao.update(config);
    }

    @Override
    public JfSystemConfig get(String id) {
        if (StringUtil.isBlank(id)) {
            return null;
        }
        return configDao.get(id);
    }

    @Override
    public long count(JfSystemConfig config) {
        return configDao.count(config);
    }

	@Override
	public List<JfSystemConfig> queryAll(JfSystemConfig config) throws RepsException{
		return configDao.find(config);
	}
    
}
