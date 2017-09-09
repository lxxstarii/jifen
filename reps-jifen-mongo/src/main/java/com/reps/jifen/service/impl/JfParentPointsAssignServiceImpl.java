package com.reps.jifen.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.reps.core.exception.RepsException;
import com.reps.core.orm.ListResult;
import com.reps.core.util.StringUtil;
import com.reps.jifen.entity.JfParentPointsAssign;
import com.reps.jifen.repository.IJfParentPointsAssignRepository;
import com.reps.jifen.service.IJfParentPointsAssignService;

@Service
public class JfParentPointsAssignServiceImpl implements IJfParentPointsAssignService{

	@Autowired
	private IJfParentPointsAssignRepository repository;
	
	@Override
	public ListResult<JfParentPointsAssign> query(JfParentPointsAssign jfParentPointsAssign, Integer pageIndex, Integer pageSize) throws RepsException {
		if(null == jfParentPointsAssign) {
			throw new RepsException("参数错误");
		}
		String parentId = jfParentPointsAssign.getParentId();
		if(StringUtil.isBlank(parentId)){
			throw new RepsException("家长ID不能为空");
		}
		String studentId = jfParentPointsAssign.getStudentId();
		if(StringUtil.isBlank(studentId)){
			throw new RepsException("孩子ID不能为空");
		}
		//设置分页参数
		pageIndex = null == pageIndex ? 1 : pageIndex;
		pageSize = null == pageSize ? 20 : pageSize;
		//构建分页对象
		PageRequest pageRequest = new PageRequest(pageSize * (pageIndex - 1), pageSize);
		Page<JfParentPointsAssign> page = repository.findByParentIdAndStudentId(parentId, studentId, pageRequest);
		if(null == page) {
			throw new RepsException("mongodb查询失败");
		}
		ListResult<JfParentPointsAssign> listResult = new ListResult<>(page.getContent(), page.getTotalElements());
		listResult.setPageSize(pageSize);
		return listResult;
	}

}
