package com.reps.jifen.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.reps.core.orm.IdEntity;

/**
 * 积分等级
 * @author qianguobing
 * @date 2017年9月8日 下午2:39:50
 */
@Entity
@Table(name = "reps_jf_point_level")
public class JfPointLevel extends IdEntity implements Serializable{

	private static final long serialVersionUID = -7985358858167270086L;
	
	@Column(name = "level")
	private Integer level;
	
	@Column(name = "point")
	private Integer point;

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public Integer getPoint() {
		return point;
	}

	public void setPoint(Integer point) {
		this.point = point;
	}
	
}
