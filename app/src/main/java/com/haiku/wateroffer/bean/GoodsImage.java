package com.haiku.wateroffer.bean;

import java.io.Serializable;

/**
 * 商品bean
 * Created by hyming on 2016/7/12.
 */
public class GoodsImage implements Serializable {
    private String image_id;
    private String image_path;
    private String image_sort;

    public String getImage_id() {
        return image_id;
    }

    public void setImage_id(String image_id) {
        this.image_id = image_id;
    }

    public String getImage_path() {
        return image_path;
    }

    public void setImage_path(String image_path) {
        this.image_path = image_path;
    }

    public String getImage_sort() {
        return image_sort;
    }

    public void setImage_sort(String image_sort) {
        this.image_sort = image_sort;
    }
}
