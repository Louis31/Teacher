package com.haiku.wateroffer.bean;

import com.haiku.wateroffer.common.OrderStatus;

import java.io.Serializable;
import java.util.List;

/**
 * 订单item bean
 * Created by hyming on 2016/7/11.
 */
public class OrderItem implements Serializable {
    private int order_id;// 订单编号
    private String serial_number;// 支付流水号
    private String order_amount;// 订单金额
    private int product_count;// 商品数量
    private String order_time;// 下单时间
    private String send_time;// 发货时间
    private int wepay_method;//
    private String remark;// 订单备注
    private String status;// 订单状态
    private String express_code;// 快递单号
    private String exptime;// 快递时间
    private String express_company;// 快递公司名称
    private String order_diliveryman_id;

    private List<Goods> details;// 订单商品列表
    private AddressInfo address_info;

    private OrderStatus mOrderStatus;

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public String getSerial_number() {
        return serial_number;
    }

    public void setSerial_number(String serial_number) {
        this.serial_number = serial_number;
    }

    public String getOrder_amount() {
        return order_amount;
    }

    public void setOrder_amount(String order_amount) {
        this.order_amount = order_amount;
    }

    public int getProduct_count() {
        return product_count;
    }

    public void setProduct_count(int product_count) {
        this.product_count = product_count;
    }

    public String getOrder_time() {
        return order_time;
    }

    public void setOrder_time(String order_time) {
        this.order_time = order_time;
    }

    public String getSend_time() {
        return send_time;
    }

    public void setSend_time(String send_time) {
        this.send_time = send_time;
    }

    public int getWepay_method() {
        return wepay_method;
    }

    public void setWepay_method(int wepay_method) {
        this.wepay_method = wepay_method;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getExpress_code() {
        return express_code;
    }

    public void setExpress_code(String express_code) {
        this.express_code = express_code;
    }

    public String getExptime() {
        return exptime;
    }

    public void setExptime(String exptime) {
        this.exptime = exptime;
    }

    public String getExpress_company() {
        return express_company;
    }

    public void setExpress_company(String express_company) {
        this.express_company = express_company;
    }

    public List<Goods> getDetails() {
        return details;
    }

    public void setDetails(List<Goods> details) {
        this.details = details;
    }

    public AddressInfo getAddress_info() {
        return address_info;
    }

    public void setAddress_info(AddressInfo address_info) {
        this.address_info = address_info;
    }

    public OrderStatus getOrderStatus() {
        return mOrderStatus;
    }

    public String getOrder_diliveryman_id() {
        return order_diliveryman_id;
    }

    public void setOrder_diliveryman_id(String order_diliveryman_id) {
        this.order_diliveryman_id = order_diliveryman_id;
    }

    public void setOrderStatus() {
        for (OrderStatus orderStatus : OrderStatus.values()) {
            if (status.equals(orderStatus.getStatus())) {
                this.mOrderStatus = orderStatus;
                break;
            }
        }
    }
}
