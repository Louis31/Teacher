package com.haiku.wateroffer.bean;

import java.io.Serializable;

/**
 * 商品bean
 * Created by hyming on 2016/7/12.
 */
public class Goods implements Serializable {
    private int product_id;// 商品id
    private String product_name;// 商品名称
    private String product_image;// 商品图片
    private String sell_price;// 价格
    private int product_count;// 商品数量
    private String product_unit;// 商品单位
    private String product_breif;// 商品简介
    private int product_instocks;// 库存
    private String sales;// 销量

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_image() {
        return product_image;
    }

    public void setProduct_image(String product_image) {
        this.product_image = product_image;
    }

    public String getSell_price() {
        return sell_price;
    }

    public void setSell_price(String sell_price) {
        this.sell_price = sell_price;
    }

    public int getProduct_count() {
        return product_count;
    }

    public void setProduct_count(int product_count) {
        this.product_count = product_count;
    }

    public String getProduct_unit() {
        return product_unit;
    }

    public void setProduct_unit(String product_unit) {
        this.product_unit = product_unit;
    }

    public String getProduct_breif() {
        return product_breif;
    }

    public void setProduct_breif(String product_breif) {
        this.product_breif = product_breif;
    }

    public int getProduct_instocks() {
        return product_instocks;
    }

    public void setProduct_instocks(int product_instocks) {
        this.product_instocks = product_instocks;
    }

    public String getSales() {
        return sales;
    }

    public void setSales(String sales) {
        this.sales = sales;
    }
}
