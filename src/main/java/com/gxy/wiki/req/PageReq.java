package com.gxy.wiki.req;

/**
 * @author GUO
 * @Classname PageReq
 * @Description TODO
 * @Date 2021/9/22 23:00
 */
public class PageReq {
    private int page;
    private int size;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return "PageReq{" +
                "page=" + page +
                ", size=" + size +
                '}';
    }
}
