package com.asiainfo.mainFrame.entity;

import java.util.List;

/**
 * 菜单项实体
 * @author huxm
 *
 */
public class MenuItem {

    private int menuID;//菜单ID
    private int menuLevel;//菜单级别
    private int parentID;//父菜单ID
    private String menuName;//菜单名
    private String menuURL;//菜单地址
    private String menuMemo;//菜单说明
    private List<MenuItem> subMenuList;//子菜单列表
    private int menuIdx;
    
    public int getMenuID() {
        return menuID;
    }
    public void setMenuID(int menuID) {
        this.menuID = menuID;
    }
    public int getMenuLevel() {
        return menuLevel;
    }
    public void setMenuLevel(int menuLevel) {
        this.menuLevel = menuLevel;
    }
    public int getParentID() {
        return parentID;
    }
    public void setParentID(int parentID) {
        this.parentID = parentID;
    }
    public String getMenuName() {
        return menuName;
    }
    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }
    public String getMenuURL() {
        return menuURL;
    }
    public void setMenuURL(String menuURL) {
        this.menuURL = menuURL;
    }
    public String getMenuMemo() {
        return menuMemo;
    }
    public void setMenuMemo(String menuMemo) {
        this.menuMemo = menuMemo;
    }
    public List<MenuItem> getSubMenuList() {
        return subMenuList;
    }
    public void setSubMenuList(List<MenuItem> subMenuList) {
        this.subMenuList = subMenuList;
    }
    public int getMenuIdx() {
        return menuIdx;
    }
    public void setMenuIdx(int menuIdx) {
        this.menuIdx = menuIdx;
    }
      
    
}
