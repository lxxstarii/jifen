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
import java.util.List;

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
    public boolean save(JfSystemConfig config) {
        JfSystemConfig c = new JfSystemConfig();
        c.setCode(config.getCode());
        List<JfSystemConfig> configList = configDao.find(c);
        if (configList != null && configList.size() > 0) {
            return false;
        }
        configDao.save(config);
        return true;
    }

    @Override
    public boolean update(JfSystemConfig config) {
        JfSystemConfig c = new JfSystemConfig();
        c.setCode(config.getCode());
        List<JfSystemConfig> configList = configDao.find(c);
        if (configList != null) {
            if (configList.size() > 1) {
                return false;
            }
            if (configList.size() == 1 && !configList.get(0).getId().equals(config.getId())) {
                return false;
            }
        }
        configDao.update(config);
        return true;
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
