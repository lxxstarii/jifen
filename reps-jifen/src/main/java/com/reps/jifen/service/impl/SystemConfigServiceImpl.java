package com.reps.jifen.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reps.core.exception.RepsException;
import com.reps.core.orm.ListResult;
import com.reps.core.util.StringUtil;
import com.reps.jifen.dao.SystemConfigDao;
import com.reps.jifen.entity.SystemConfig;
import com.reps.jifen.service.ISystemConfigService;

/**
 * Created on 2017/9/8.
 */
@Service
@Transactional
public class SystemConfigServiceImpl implements ISystemConfigService {

    @Autowired
    SystemConfigDao configDao;

    @Override
    public ListResult<SystemConfig> query(Integer startRow, Integer pageSize, SystemConfig config) {
        return configDao.query(startRow, pageSize, config);
    }

    @Override
    public void delete(String id) {
        if (StringUtil.isNotBlank(id)) {
            configDao.delete(id);
        }
    }

    @Override
    public boolean save(SystemConfig config) {
        SystemConfig c = new SystemConfig();
        c.setCode(config.getCode());
        List<SystemConfig> configList = configDao.find(c);
        if (configList != null && configList.size() > 0) {
            return false;
        }
        configDao.save(config);
        return true;
    }

    @Override
    public boolean update(SystemConfig config) {
        SystemConfig c = new SystemConfig();
        c.setCode(config.getCode());
        List<SystemConfig> configList = configDao.find(c);
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
    public SystemConfig get(String id) {
        if (StringUtil.isBlank(id)) {
            return null;
        }
        return configDao.get(id);
    }

    @Override
    public long count(SystemConfig config) {
        return configDao.count(config);
    }

	@Override
	public List<SystemConfig> queryAll(SystemConfig config) throws RepsException{
		return configDao.find(config);
	}
    
}
