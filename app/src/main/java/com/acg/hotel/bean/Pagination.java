package com.acg.hotel.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @Classname Pagination
 * @Description TODO 用于做分页的
 * @Version 1.0.0
 * @Date 2022/9/4 22:24
 * @Created by an
 */
public class Pagination<T> implements Serializable {

    //            "pageNum": 1,
    //            "pageSize": 5,
    //            "totalPage": null,
    //            "total": null,
    //            "data":{}
    private static final long serialVersionUID = 1L;
    private int pageNum;
    private int pageSize;
    private int totalPage;  // 总页数
    private Long total;      // 总共数据

    private List<T> list;//这一页的数据


    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<T> getData() {
        return list;
    }

    public void setData(List<T> data) {
        this.list = data;
    }
}
