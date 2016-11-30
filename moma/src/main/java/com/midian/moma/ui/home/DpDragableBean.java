package com.midian.moma.ui.home;

import midian.baselib.bean.NetResult;

/**
 * 可拖动自定义按钮状态实体 Created by XuYang on 15/4/22.
 */
public class DpDragableBean extends NetResult {
	private boolean isEidtable;// 是否可编辑
	private boolean isVisible = true;// 是否显示
	private boolean isDraggable;// 是否可拖动
	private boolean isDotRectShow;// 是否显示虚线框
	private boolean isDeleteShow;// 是否显示删除按钮
	private String name;// 名称
	private String command;// 命令
	private int bgType=100;//100 0:胶囊形 , 1000:圆形

	public DpDragableBean() {
	}

	public DpDragableBean(boolean isEidtable, boolean isVisible,
			boolean isDraggable, boolean isDotRectShow, boolean isDeleteShow,
			String name, String command, int bgType) {
		this.isEidtable = isEidtable;
		this.isVisible = isVisible;
		this.isDraggable = isDraggable;
		this.isDotRectShow = isDotRectShow;
		this.isDeleteShow = isDeleteShow;
		this.name = name;
		this.command = command;
		this.bgType = bgType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	public int getBgType() {
		return bgType;
	}

	public void setBgType(int bgType) {
		this.bgType = bgType;
	}

	public boolean isVisible() {
		return isVisible;
	}

	public void setIsVisible(boolean isVisible) {
		this.isVisible = isVisible;
	}

	public boolean isDraggable() {
		return isDraggable;
	}

	public void setIsDraggable(boolean isDraggable) {
		this.isDraggable = isDraggable;
	}

	public boolean isDotRectShow() {
		return isDotRectShow;
	}

	public void setIsDotRectShow(boolean isDotRectShow) {
		this.isDotRectShow = isDotRectShow;
	}

	public boolean isDeleteShow() {
		return isDeleteShow;
	}

	public void setIsDeleteShow(boolean isDeleteShow) {
		this.isDeleteShow = isDeleteShow;
	}

	public boolean isEidtable() {
		return isEidtable;
	}

	public void setIsEidtable(boolean isEidtable) {
		this.isEidtable = isEidtable;
	}

	@Override
	public String toString() {
		return "DpDragableButtonBean [isEidtable=" + isEidtable
				+ ", isVisible=" + isVisible + ", isDraggable=" + isDraggable
				+ ", isDotRectShow=" + isDotRectShow + ", isDeleteShow="
				+ isDeleteShow + ", name=" + name + ", command=" + command
				+ ", bgType=" + bgType + "]";
	}

	// @Override
	// public String toString() {
	// // TODO Auto-generated method stub
	// return super.toString();
	// }

}
