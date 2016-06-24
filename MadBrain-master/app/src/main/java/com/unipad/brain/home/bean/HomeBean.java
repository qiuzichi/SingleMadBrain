package com.unipad.brain.home.bean;
/**
 * @描述：  首页左侧菜单需要的实体
 * @author gongjiebin
 *
 */
public class HomeBean {
    public int selImgId;
    
    public int norImgId;
    
    public String name;
    
    public boolean isSelect;
    
    public ProjectBean projectBean;

	public HomeBean(int selImgId, int norImgId,ProjectBean projectBean ,boolean isSelect) {
		super();
		this.selImgId = selImgId;
		this.norImgId = norImgId;
		this.isSelect = isSelect;
		this.projectBean = projectBean;
	}
    
    
}
