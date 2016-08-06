package com.haiku.wateroffer.bean;

/**
 * Created by hyming on 2016/8/5.
 */
public class OrderStatus {
    private String id;// ID号
    private String order_id;// 订单id
    private String diliveryman_id;// 配送员ID
    private String merchant_id;// 店铺ID
    // （配送状态:unpay：未支付订单， payed：（买家已支付商家已接单）,sending：商家派单中、
    // delivering：配送员已接单、received：已完成、reject：配送已取消）
    private String status;
    private String reject_by;// （取消配送者：merchant店铺、diliverman配送员）
    private String create_time;// （创建时间）
    private String reject_time;// （订单取消配送时间）
    private String diliveryman_name;// (配送员名字)
    private String diliveryman_phone;// (配送员电话)

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getDiliveryman_id() {
        return diliveryman_id;
    }

    public void setDiliveryman_id(String diliveryman_id) {
        this.diliveryman_id = diliveryman_id;
    }

    public String getMerchant_id() {
        return merchant_id;
    }

    public void setMerchant_id(String merchant_id) {
        this.merchant_id = merchant_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReject_by() {
        return reject_by;
    }

    public void setReject_by(String reject_by) {
        this.reject_by = reject_by;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getReject_time() {
        return reject_time;
    }

    public void setReject_time(String reject_time) {
        this.reject_time = reject_time;
    }

    public String getDiliveryman_name() {
        return diliveryman_name;
    }

    public void setDiliveryman_name(String diliveryman_name) {
        this.diliveryman_name = diliveryman_name;
    }

    public String getDiliveryman_phone() {
        return diliveryman_phone;
    }

    public void setDiliveryman_phone(String diliveryman_phone) {
        this.diliveryman_phone = diliveryman_phone;
    }
}
