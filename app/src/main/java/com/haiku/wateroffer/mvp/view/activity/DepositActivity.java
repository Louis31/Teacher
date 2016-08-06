package com.haiku.wateroffer.mvp.view.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.alipay.sdk.app.PayTask;
import com.haiku.wateroffer.R;
import com.haiku.wateroffer.bean.Deposit;
import com.haiku.wateroffer.bean.WechatParams;
import com.haiku.wateroffer.common.UserManager;
import com.haiku.wateroffer.common.listener.TitlebarListenerAdapter;
import com.haiku.wateroffer.common.pay.PayResult;
import com.haiku.wateroffer.common.pay.SignUtils;
import com.haiku.wateroffer.common.util.data.LogUtils;
import com.haiku.wateroffer.common.util.ui.ToastUtils;
import com.haiku.wateroffer.constant.SecurityConstant;
import com.haiku.wateroffer.constant.UrlConstant;
import com.haiku.wateroffer.mvp.base.BaseActivity;
import com.haiku.wateroffer.mvp.contract.DepositContract;
import com.haiku.wateroffer.mvp.model.impl.DepositModelImpl;
import com.haiku.wateroffer.mvp.persenter.DepositPresenter;
import com.haiku.wateroffer.mvp.view.widget.Titlebar;
import com.tencent.mm.sdk.constants.Build;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

/**
 * 保证金页面
 * Created by hyming on 2016/7/13.
 */
@ContentView(R.layout.act_deposit)
public class DepositActivity extends BaseActivity implements DepositContract.View {
    private static final int SDK_PAY_FLAG = 1;

    private int uid;
    private String goodsName;
    private String goodsDescribe;
    private String goodsPrice;

    private ProgressDialog mDialog;

    private IWXAPI api;
    private WechatParams mWechatParams;

    private DepositContract.Presenter mPresenter;

    @ViewInject(R.id.titlebar)
    private Titlebar mTitlebar;

    @ViewInject(R.id.btn_wechat)
    private Button btn_wechat;

    @ViewInject(R.id.btn_alipay)
    private Button btn_alipay;

    @ViewInject(R.id.btn_play_finish)
    private Button btn_play_finish;


    // 微信支付
    @Event(R.id.btn_wechat)
    private void wechatClick(View v) {

    }

    // 支付宝支付
    @Event(R.id.btn_alipay)
    private void alipayClick(View v) {
        mPresenter.alipayPayment(uid);
    }

    /**
     * 支付宝支付回调
     */
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    PayResult payResult = new PayResult((String) msg.obj);
                    /**
                     * 同步返回的结果必须放置到服务端进行验证（验证的规则请看https://doc.open.alipay.com/doc2/
                     * detail.htm?spm=0.0.0.0.xdvAU6&treeId=59&articleId=103665&
                     * docType=1) 建议商户依赖异步通知
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息

                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                    if (TextUtils.equals(resultStatus, "9000")) {
                        ToastUtils.getInstant().showToast("支付成功");
                        setResult(Activity.RESULT_OK);
                        finishDelayed(1500);
                    } else {
                        // 判断resultStatus 为非"9000"则代表可能支付失败
                        // "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000")) {
                            ToastUtils.getInstant().showToast("支付结果确认中");

                        } else {
                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                            ToastUtils.getInstant().showToast("支付失败");
                        }
                    }
                    break;
                }
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDatas();
        initViews();
        // 创建Presenter
        new DepositPresenter(new DepositModelImpl(), this);
    }

    private void initDatas() {
        uid = UserManager.getInstance().getUser().getUid();
        goodsName = "保证金";
        goodsDescribe = getString(R.string.deposit_tip);
        goodsPrice = "0.01";
    }

    private void initViews() {
        mTitlebar.initDatas(R.string.deposit, true);
        mTitlebar.setListener(new TitlebarListenerAdapter() {
            @Override
            public void onReturnIconClick() {
                finish();
            }
        });

        if (UserManager.getInstance().isPayDeposit()) {
            btn_wechat.setVisibility(View.GONE);
            btn_alipay.setVisibility(View.GONE);
        } else {
            btn_play_finish.setVisibility(View.GONE);
        }
    }

    @Override
    public void setPresenter(@NonNull DepositContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    // TODO 支付宝支付
    /*******************    支付宝支付   *****************************/
    /**
     * call alipay sdk pay. 调用SDK支付
     */
    public void aliPay(Deposit bean) {
       /* if (TextUtils.isEmpty(SecurityConstant.PARTNER) || TextUtils.isEmpty(SecurityConstant.RSA_PRIVATE) || TextUtils.isEmpty(SecurityConstant.SELLER)) {
            new AlertDialog.Builder(this).setTitle("警告").setMessage("需要配置PARTNER | RSA_PRIVATE| SELLER")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialoginterface, int i) {
                            finish();
                        }
                    }).show();
            return;
        }*/
        String orderInfo = bean.getPayment_package();//getOrderInfo(goodsName, goodsDescribe, goodsPrice);
        LogUtils.showLogE("orderInfo", orderInfo);
        /**
         * 特别注意，这里的签名逻辑需要放在服务端，切勿将私钥泄露在代码中！
         */
     /*   String sign = sign(orderInfo);
        try {
            *//**
         * 仅需对sign 做URL编码
         *//*
            sign = URLEncoder.encode(sign, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }*/

        /**
         * 完整的符合支付宝参数规范的订单信息
         */
        orderInfo = orderInfo.replaceAll("\\\\", "");
        final String payInfo = orderInfo;// + "&sign=\"" + sign + "\"&" + getSignType();

        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                // 构造PayTask 对象
                PayTask alipay = new PayTask(DepositActivity.this);
                // 调用支付接口，获取支付结果
                String result = alipay.pay(payInfo, true);

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    /**
     * create the order info. 创建订单信息
     */
    private String getOrderInfo(String subject, String body, String price) {

        // 签约合作者身份ID
        String orderInfo = "partner=" + "\"" + SecurityConstant.PARTNER + "\"";

        // 签约卖家支付宝账号
        orderInfo += "&seller_id=" + "\"" + SecurityConstant.SELLER + "\"";

        // 商户网站唯一订单号
        orderInfo += "&out_trade_no=" + "\"" + getOutTradeNo() + "\"";

        // 商品名称
        orderInfo += "&subject=" + "\"" + subject + "\"";

        // 商品详情
        orderInfo += "&body=" + "\"" + body + "\"";

        // 商品金额
        orderInfo += "&total_fee=" + "\"" + price + "\"";

        // 服务器异步通知页面路径
        orderInfo += "&notify_url=" + "\"" + UrlConstant.ALIPAY_URL + "\"";

        // 服务接口名称， 固定值
        orderInfo += "&service=\"mobile.securitypay.pay\"";

        // 支付类型， 固定值
        orderInfo += "&payment_type=\"1\"";

        // 参数编码， 固定值
        orderInfo += "&_input_charset=\"utf-8\"";

        // 设置未付款交易的超时时间
        // 默认30分钟，一旦超时，该笔交易就会自动被关闭。
        // 取值范围：1m～15d。
        // m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
        // 该参数数值不接受小数点，如1.5h，可转换为90m。
        orderInfo += "&it_b_pay=\"30m\"";

        // extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
        // orderInfo += "&extern_token=" + "\"" + extern_token + "\"";

        // 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
        orderInfo += "&return_url=\"m.alipay.com\"";

        // 调用银行卡支付，需配置此参数，参与签名， 固定值 （需要签约《无线银行卡快捷支付》才能使用）
        // orderInfo += "&paymethod=\"expressGateway\"";

        return orderInfo;
    }

    /**
     * get the out_trade_no for an order. 生成商户订单号，该值在商户端应保持唯一（可自定义格式规范）
     */
    private String getOutTradeNo() {
        SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss", Locale.getDefault());
        Date date = new Date();
        String key = format.format(date);

        Random r = new Random();
        key = key + r.nextInt();
        key = key.substring(0, 15);
        return key;
    }

    /**
     * sign the order info. 对订单信息进行签名
     *
     * @param content 待签名订单信息
     */
    private String sign(String content) {
        return SignUtils.sign(content, SecurityConstant.RSA_PRIVATE);
    }

    /**
     * get the sign type we use. 获取签名方式
     */
    private String getSignType() {
        return "sign_type=\"RSA\"";
    }

    /*******************
     * 微信支付
     *****************************/
    /*微信支付*/
    public void wechatPay() {
        if (mWechatParams == null) {
            // 从网络获取订单信息
            // mWechatParams = mOrder.getWechatParams();

        }
        api = WXAPIFactory.createWXAPI(mContext, SecurityConstant.WECHAT_APP_ID, true);
        try {
            if (api != null && mWechatParams != null && isWXAppInstalled()) {

                api.registerApp(SecurityConstant.WECHAT_APP_ID);

                PayReq req = new PayReq();
                if (!TextUtils.isEmpty(mWechatParams.getAppid()))
                    req.appId = mWechatParams.getAppid();
                if (!TextUtils.isEmpty(mWechatParams.getPartnerid()))
                    req.partnerId = mWechatParams.getPartnerid();
                if (!TextUtils.isEmpty(mWechatParams.getPrepayid()))
                    req.prepayId = mWechatParams.getPrepayid();
                req.packageValue = "Sign=WXPay";
                if (!TextUtils.isEmpty(mWechatParams.getNoncestr()))
                    req.nonceStr = mWechatParams.getNoncestr();
                if (!TextUtils.isEmpty(mWechatParams.getTimestamp()))
                    req.timeStamp = mWechatParams.getTimestamp();
                if (!TextUtils.isEmpty(mWechatParams.getSign()))
                    req.sign = mWechatParams.getSign();
                boolean isConnected = api.sendReq(req);
            } else {
                Log.d("PAY_GET", "服务器请求错误");
                ToastUtils.getInstant().showToast("服务器请求错误");
            }
        } catch (Exception e) {
            Log.e("PAY_GET", "异常：" + e.getMessage());
            ToastUtils.getInstant().showToast("异常：" + e.getMessage());
        }
    }

    /**
     * 判断微信是否更新版本，支持支付？
     *
     * @return
     */
    private boolean isWXAppInstalled() {
        boolean isPaySupported = api.getWXAppSupportAPI() >= Build.PAY_SUPPORTED_SDK_INT;
        return isPaySupported;
    }

    /**
     * View 相应接口回调
     */

    @Override
    public void showLoadingDialog(boolean isShow) {
        if (isShow) {
            mDialog = ProgressDialog.show(mContext, "", getString(R.string.dlg_submit_order));
            mDialog.setCancelable(false);
        } else {
            if (mDialog != null && mDialog.isShowing()) {
                mDialog.dismiss();
            }
        }
    }

    @Override
    public void showMessage(String msg) {
        ToastUtils.getInstant().showToast(msg);
    }

    @Override
    public void showAliPayView(Deposit bean) {
        aliPay(bean);
    }

    @Override
    public void showWechatPayView(Deposit bean) {

    }
}
