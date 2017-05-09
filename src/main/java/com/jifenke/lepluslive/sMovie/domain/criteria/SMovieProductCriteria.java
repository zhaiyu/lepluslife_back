package com.jifenke.lepluslive.sMovie.domain.criteria;

/**
 * Created by Administrator on 2017/5/5.
 */
public class SMovieProductCriteria {
    private Integer offset;
    private Integer state = 1;   //状态  1=上架|0=下架

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
}
