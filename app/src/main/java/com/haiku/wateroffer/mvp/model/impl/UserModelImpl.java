package com.haiku.wateroffer.mvp.model.impl;

import android.support.annotation.NonNull;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.haiku.wateroffer.App;
import com.haiku.wateroffer.bean.Bill;
import com.haiku.wateroffer.bean.Deliver;
import com.haiku.wateroffer.bean.ResultData;
import com.haiku.wateroffer.bean.User;
import com.haiku.wateroffer.common.UserManager;
import com.haiku.wateroffer.common.util.data.GsonUtils;
import com.haiku.wateroffer.common.util.data.LogUtils;
import com.haiku.wateroffer.common.util.net.IRequestCallback;
import com.haiku.wateroffer.common.util.net.XUtils;
import com.haiku.wateroffer.common.util.net.XUtilsCallback;
import com.haiku.wateroffer.constant.BaseConstant;
import com.haiku.wateroffer.constant.UrlConstant;
import com.haiku.wateroffer.mvp.model.IUserModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * User Model实现类
 * Created by hyming on 2016/7/6.
 */
public class UserModelImpl extends BaseModelImpl implements IUserModel {
    private final String TAG = "UserModelImpl";

    // 登陆
    @Override
    public void login(Map<String, Object> params, @NonNull final IRequestCallback callback) {
        XUtils.Post(UrlConstant.User.loginUrl(), params, new XUtilsCallback<ResultData>(callback) {
            @Override
            public void onSuccess(ResultData result) {
                super.onSuccess(result);
                LogUtils.showLogE(TAG, result.toString());
                if (result.getRetcode() == BaseConstant.SUCCESS) {
                    UserManager.getInstance().setUser(GsonUtils.gsonToBean(result.getRetmsg().toString(), User.class));
                    // TODO 设置默认uid
                    UserManager.getInstance().getUser().setUid(1431);
                    callback.onSuccess();
                } else {
                    callback.onError(result.getRetcode(), App.getInstance().getErrorMsg(result.getRetcode()));
                }
            }
        });
    }

    // 获取验证码
    @Override
    public void getVerifyCode(Map<String, Object> params, @NonNull final GetVerifyCodeCallback callback) {
        XUtils.Get(UrlConstant.smsCodeUrl(), params, new XUtilsCallback<ResultData>(callback) {
            @Override
            public void onSuccess(ResultData result) {
                super.onSuccess(result);
                LogUtils.showLogE(TAG, result.toString());
                if (result.isSuccess()) {
                    callback.onSuccess();
                } else {
                    callback.onError(result.getRetcode(), App.getInstance().getErrorMsg(result.getRetcode()));
                }
            }
        });
    }

    // 获取我的账单列表
    @Override
    public void getBillList(Map<String, Object> params, @NonNull final MyBillCallback callback) {
        XUtils.Get(UrlConstant.User.getBillList(), params, new XUtilsCallback<ResultData>(callback) {
            @Override
            public void onSuccess(ResultData result) {
                super.onSuccess(result);
                LogUtils.showLogE(TAG, result.toString());
                if (result.isSuccess()) {
                    List<Bill> list = new ArrayList<Bill>();
                    if (!result.getRetmsg().isJsonNull()) {
                       /* JsonObject jobj = result.getRetmsg().getAsJsonObject();
                        Set<Map.Entry<String, JsonElement>> set = jobj.entrySet();
                        for (Map.Entry<String, JsonElement> entry : set) {
                            JsonElement je = entry.getValue();
                            Bill bill = GsonUtils.gsonToBean(je.toString(), Bill.class);
                            list.add(bill);
                        }*/
                        list = GsonUtils.gsonToList(result.getRetmsg().getAsJsonArray().toString(), Bill.class);
                    }
                    callback.getBillSuccess(list);
                } else {
                    callback.onError(result.getRetcode(), App.getInstance().getErrorMsg(result.getRetcode()));
                }
            }
        });
    }

    // 查询账单
    @Override
    public void searchBill(Map<String, Object> params, @NonNull final MyBillCallback callback) {
        XUtils.Get(UrlConstant.User.searchBill(), params, new XUtilsCallback<ResultData>(callback) {
            @Override
            public void onSuccess(ResultData result) {
                super.onSuccess(result);
                LogUtils.showLogE(TAG, result.toString());
                if (result.isSuccess()) {
                    Bill bean = GsonUtils.gsonToBean(result.getRetmsg().getAsJsonObject().toString(), Bill.class);
                    callback.searchBillSuccess(bean);
                } else {
                    callback.onError(result.getRetcode(), App.getInstance().getErrorMsg(result.getRetcode()));
                }
            }
        });
    }

    // 获取配送员列表
    @Override
    public void getDeliverList(Map<String, Object> params, @NonNull final DeliverCallback callback) {
        XUtils.Get(UrlConstant.User.getDeliverList(), params, new XUtilsCallback<ResultData>(callback) {
            @Override
            public void onSuccess(ResultData result) {
                super.onSuccess(result);
                LogUtils.showLogE(TAG, result.toString());
                if (result.isSuccess()) {
                    List<Deliver> list = new ArrayList<Deliver>();
                    if (!result.getRetmsg().isJsonNull()) {
                        list = GsonUtils.gsonToList(result.getRetmsg().getAsJsonArray().toString(), Deliver.class);
                    }
                    callback.getDeliverListSuccess(list);
                } else {
                    callback.onError(result.getRetcode(), App.getInstance().getErrorMsg(result.getRetcode()));
                }
            }
        });
    }

    // 编辑配送员
    @Override
    public void changeDeliverStatus(Map<String, Object> params, @NonNull final DeliverCallback callback) {
        XUtils.Get(UrlConstant.User.editDeliver(), params, new XUtilsCallback<ResultData>(callback) {
            @Override
            public void onSuccess(ResultData result) {
                super.onSuccess(result);
                LogUtils.showLogE(TAG, result.toString());
                if (result.getRetcode() == BaseConstant.SUCCESS) {
                    callback.changeStatusSuccess();
                } else {
                    callback.onError(result.getRetcode(), App.getInstance().getErrorMsg(result.getRetcode()));
                }
            }
        });
    }

    // 添加配送员
    @Override
    public void addDeliver(Map<String, Object> params, @NonNull final DeliverCallback callback) {
        XUtils.Get(UrlConstant.User.addDeliver(), params, new XUtilsCallback<ResultData>(callback) {
            @Override
            public void onSuccess(ResultData result) {
                super.onSuccess(result);
                LogUtils.showLogE(TAG, result.toString());
                if (result.getRetcode() == BaseConstant.SUCCESS) {
                    callback.addDeliverSuccess(GsonUtils.gsonToBean(result.getRetmsg().toString(), Deliver.class));
                } else {
                    callback.onError(result.getRetcode(), App.getInstance().getErrorMsg(result.getRetcode()));
                }
            }
        });
    }

    // 修改店铺名称
    @Override
    public void addShopName(Map<String, Object> params, @NonNull final IRequestCallback callback) {
        XUtils.Post(UrlConstant.User.addShopNameUrl(), params, new XUtilsCallback<ResultData>(callback) {
            @Override
            public void onSuccess(ResultData result) {
                super.onSuccess(result);
                LogUtils.showLogE(TAG, result.toString());
                if (result.getRetcode() == BaseConstant.SUCCESS) {
                    callback.onSuccess();
                } else {
                    callback.onError(result.getRetcode(), App.getInstance().getErrorMsg(result.getRetcode()));
                }
            }
        });
    }

    // 修改店铺电话
    @Override
    public void changePhone(Map<String, Object> params, @NonNull final GetVerifyCodeCallback callback) {
        XUtils.Post(UrlConstant.User.changePhone(), params, new XUtilsCallback<ResultData>(callback) {
            @Override
            public void onSuccess(ResultData result) {
                super.onSuccess(result);
                LogUtils.showLogE(TAG, result.toString());
                if (result.getRetcode() == BaseConstant.SUCCESS) {
                    callback.onSuccess();
                } else {
                    callback.onError(result.getRetcode(), App.getInstance().getErrorMsg(result.getRetcode()));
                }
            }
        });
    }

    // 修改店铺logo
    public void changeShopLogo(Map<String, Object> params, @NonNull final MyShopCallback callback) {
        XUtils.Post(UrlConstant.User.changeShopLogo(), params, new XUtilsCallback<ResultData>(callback) {
            @Override
            public void onSuccess(ResultData result) {
                super.onSuccess(result);
                LogUtils.showLogE(TAG, result.toString());
                if (result.getRetcode() == BaseConstant.SUCCESS) {
                    // ResultData{retcode=0, retmsg={"logoUrl":"http://sendwater.api.youdians.com/uploads/8/05f03c7b0f90889dc8a9ba8b158ecbf7."}}
                    JsonObject jObj = result.getRetmsg().getAsJsonObject();
                    if (null != jObj) {
                        callback.uploadLogoSuccess(jObj.get("logoUrl").getAsString());
                    } else {
                        callback.uploadLogoSuccess("");
                    }
                } else {
                    callback.onError(result.getRetcode(), App.getInstance().getErrorMsg(result.getRetcode()));
                }
            }
        });
    }

    // 修改店铺地址
    @Override
    public void addShopAddress(Map<String, Object> params, @NonNull final IRequestCallback callback) {
        XUtils.Post(UrlConstant.User.addShopAddress(), params, new XUtilsCallback<ResultData>(callback) {
            @Override
            public void onSuccess(ResultData result) {
                super.onSuccess(result);
                LogUtils.showLogE(TAG, result.toString());
                if (result.getRetcode() == BaseConstant.SUCCESS) {
                    callback.onSuccess();
                } else {
                    callback.onError(result.getRetcode(), App.getInstance().getErrorMsg(result.getRetcode()));
                }
            }
        });
    }
}
