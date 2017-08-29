package com.reps.jifen.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.reps.core.orm.IdEntity;


/**
 * 奖品分类
 * @author qianguobing
 * @date 2017年8月15日 上午9:59:38
 */
@Entity
@Table(name = "reps_jf_reward_category")
public class JfRewardCategory extends IdEntity implements Serializable {

	private static final long serialVersionUID = 7951341876197753153L;

	public JfRewardCategory(){
		
	}
	
	public JfRewardCategory(String id){
		 super.setId(id);
	}
	
	/** 上级分类ID */
	@Column(name = "parent_id", nullable = false, length = 32)
	private String parentId;

	/** 分类名称 */
	@Column(name = "name", nullable = false, length = 20)
	private String name;

	/** 性质 */
	@Column(name = "type", nullable = false, length = 10)
	private String type;
	
	/** 描述 */
	@Column(name = "description", length = 100)
	private String description;

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}
