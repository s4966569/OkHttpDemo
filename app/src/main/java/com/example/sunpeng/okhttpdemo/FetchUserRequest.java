package com.example.sunpeng.okhttpdemo;

/**
 * Created by sunpeng on 16-11-9.
 */

public class FetchUserRequest extends BaseRequest {
    private String gid;
    private String pageSize;
    private String pageIndex;

    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }

    public String getPageSize() {
        return pageSize;
    }

    public void setPageSize(String pageSize) {
        this.pageSize = pageSize;
    }

    public String getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(String pageIndex) {
        this.pageIndex = pageIndex;
    }
}
