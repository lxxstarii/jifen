package com.reps.jifen.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.reps.core.exception.RepsException;
import com.reps.core.orm.ListResult;
import com.reps.core.util.StringUtil;
import com.reps.jifen.entity.JfTeacherPointsAssign;
import com.reps.jifen.repository.IJfTeacherPointsAssignRepository;
import com.reps.jifen.service.IJfTeacherPointsAssignService;

@Service
public class JfTeacherPointsAssignServiceImpl implements IJfTeacherPointsAssignService{
	
	protected final Logger logger = LoggerFactory.getLogger(JfTeacherPointsAssignServiceImpl.class);
	
	@Autowired
	private IJfTeacherPointsAssignRepository repository;

	@Override
	public ListResult<JfTeacherPointsAssign> findByTeacherId(String teacherId, Integer pageIndex, Integer pageSize) throws RepsException{
		if(StringUtil.isBlank(teacherId)){
			throw new RepsException("教师ID不能为空");
		}
		//设置分页参数
		pageIndex = null == pageIndex ? 1 : pageIndex;
		pageSize = null == pageSize ? 20 : pageSize;
		//构建分页对象
		PageRequest pageRequest = new PageRequest(pageSize * (pageIndex - 1), pageSize);
		Page<JfTeacherPointsAssign> page = repository.findByTeacherId(teacherId, pageRequest);
		if(null == page) {
			throw new RepsException("mongodb查询失败");
		}
		ListResult<JfTeacherPointsAssign> listResult = new ListResult<>(page.getContent(), page.getTotalElements());
		listResult.setPageSize(pageSize);
		return listResult;
	}

	@Override
	public void save(JfTeacherPointsAssign data) {
			
	}

}
