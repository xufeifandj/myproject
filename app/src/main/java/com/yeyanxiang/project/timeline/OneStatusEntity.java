package com.yeyanxiang.project.timeline;

import java.util.List;

/**
 * @author 叶雁翔
 * 
 * @Email yanxiang1120@gmail.com
 * 
 * @version 1.0
 * 
 * @update 2014年4月9日
 * 
 * @简介 一级状态实体类
 */
public class OneStatusEntity {
	/* 状态名称 */
	private String statusName;
	/* 预计完成时间 */
	private String completeTime;
	/* 二级状态list */
	private List<TwoStatusEntity> twoList;

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public String getCompleteTime() {
		return completeTime;
	}

	public void setCompleteTime(String completeTime) {
		this.completeTime = completeTime;
	}

	public List<TwoStatusEntity> getTwoList() {
		return twoList;
	}

	public void setTwoList(List<TwoStatusEntity> twoList) {
		this.twoList = twoList;
	}

}
