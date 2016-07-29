package com.haiku.wateroffer.mvp.model.impl;

import android.support.annotation.NonNull;

import com.haiku.wateroffer.App;
import com.haiku.wateroffer.bean.Goods;
import com.haiku.wateroffer.bean.GoodsCategory;
import com.haiku.wateroffer.bean.ResultData;
import com.haiku.wateroffer.common.util.data.GsonUtils;
import com.haiku.wateroffer.common.util.data.LogUtils;
import com.haiku.wateroffer.common.util.data.ParamUtils;
import com.haiku.wateroffer.common.util.net.IRequestCallback;
import com.haiku.wateroffer.common.util.net.XUtils;
import com.haiku.wateroffer.common.util.net.XUtilsCallback;
import com.haiku.wateroffer.constant.UrlConstant;
import com.haiku.wateroffer.mvp.model.IGoodsModel;

import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Goods Model实现类
 * Created by hyming on 2016/7/11.
 */
public class GoodsModelImpl implements IGoodsModel {
    private final String TAG = "GoodsModelImpl";

    // 获取商品列表
    @Override
    public void getGoodsList(Map<String, Object> params, @NonNull final GoodsListCallback callback) {
        XUtils.Post(UrlConstant.Goods.listUrl(), ParamUtils.Goods.getListParams(params), new XUtilsCallback<ResultData>(callback) {
            @Override
            public void onSuccess(ResultData result) {
                super.onSuccess(result);
                LogUtils.showLogE(TAG, result.toString());
                if (result.isSuccess()) {
                    List<Goods> list = new ArrayList<>();
                    if (!result.getRetmsg().isJsonNull()) {
                        list = GsonUtils.gsonToList(result.getRetmsg().getAsJsonArray().toString(), Goods.class);
                    }
                    callback.getListDataSuccess(list);
                } else {
                    callback.onError(result.getRetcode(), App.getInstance().getErrorMsg(result.getRetcode()));
                }
            }
        });
    }

    // 删除商品
    @Override
    public void deleteGoods(Map<String, Object> params, @NonNull final GoodsListCallback callback) {
        XUtils.Get(UrlConstant.Goods.deleteUrl(), params, new XUtilsCallback<ResultData>(callback) {
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

    // 下架商品
    @Override
    public void offShelfGoods(Map<String, Object> params, @NonNull final IRequestCallback callback) {
        XUtils.Get(UrlConstant.Goods.offShelfUrl(), params, new XUtilsCallback<ResultData>(callback) {
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

    // 上架商品
    @Override
    public void upShelfGoods(Map<String, Object> params, @NonNull final IRequestCallback callback) {
        XUtils.Get(UrlConstant.Goods.upShelfUrl(), params, new XUtilsCallback<ResultData>(callback) {
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

    // 上传商品图片
    @Override
    public void uploadGoodsImage(Map<String, Object> params, @NonNull final IGoodsEditCallback callback) {
        RequestParams requestParams = new RequestParams(UrlConstant.Goods.uploadImageUrl());
        // 有上传文件时使用multipart表单, 否则上传原始文件流.
        // params.setMultipart(true);
        // 上传文件方式 1
        // params.uploadFile = new File("/sdcard/test.txt");
        // 上传文件方式 2
        // requestParams.addBodyParameter("attach", FileUtils.getBytes(path));
        requestParams.addBodyParameter("attach", new File((String) params.get("attach")));
        requestParams.setMultipart(true);
        x.http().post(requestParams, new XUtilsCallback<ResultData>(callback) {
            @Override
            public void onSuccess(ResultData result) {
                super.onSuccess(result);
                LogUtils.showLogE(TAG, result.toString());
                if (result.isSuccess()) {
                    String imgUrl = result.getRetmsg().getAsString();
                    callback.uploadImageSuccess(imgUrl);
                } else {
                    callback.onError(result.getRetcode(), App.getInstance().getErrorMsg(result.getRetcode()));
                }
            }
        });
    }

    // 获取商品分类
    @Override
    public void getGoodsCategory(Map<String, Object> params, @NonNull final IGoodsEditCallback callback) {
        XUtils.Get(UrlConstant.Goods.getCategoryUrl(), params, new XUtilsCallback<ResultData>(callback) {
            @Override
            public void onSuccess(ResultData result) {
                super.onSuccess(result);
                LogUtils.showLogE(TAG, result.toString());
                if (result.isSuccess()) {
                    List<GoodsCategory> list = new ArrayList<>();
                    if (!result.getRetmsg().isJsonNull()) {
                        list = GsonUtils.gsonToList(result.getRetmsg().getAsJsonArray().toString(), GoodsCategory.class);
                    }
                    callback.getCategorySuccess(list);
                } else {
                    callback.onError(result.getRetcode(), App.getInstance().getErrorMsg(result.getRetcode()));
                }
            }
        });
    }

    // 添加商品
    @Override
    public void addGoods(Map<String, Object> params, @NonNull final IGoodsEditCallback callback) {
        XUtils.Post(UrlConstant.Goods.addGoodsUrl(), params, new XUtilsCallback<ResultData>(callback) {
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

    // 更新商品
    @Override
    public void modifyGoods(Map<String, Object> params, @NonNull final IGoodsEditCallback callback) {
        XUtils.Post(UrlConstant.Goods.modifyGoodsUrl(), params, new XUtilsCallback<ResultData>(callback) {
            @Override
            public void onSuccess(ResultData result) {
                super.onSuccess(result);
                LogUtils.showLogE(TAG, result.toString());
                if (result.isSuccess()) {
                    callback.updateInfoSuccess(GsonUtils.gsonToBean(result.getRetmsg().getAsJsonObject().toString(), Goods.class));
                } else {
                    callback.onError(result.getRetcode(), App.getInstance().getErrorMsg(result.getRetcode()));
                }
            }
        });
    }

    @Override
    public void getGoodsInfo(Map<String, Object> params, @NonNull final IGoodsEditCallback callback) {
        XUtils.Get(UrlConstant.Goods.getGoodsInfo(), params, new XUtilsCallback<ResultData>(callback) {
            @Override
            public void onSuccess(ResultData result) {
                super.onSuccess(result);
                LogUtils.showLogE(TAG, result.toString());
                if (result.isSuccess()) {
                    callback.getGoodsInfoSuccess(GsonUtils.gsonToBean(result.getRetmsg().getAsJsonObject().toString(), Goods.class));
                } else {
                    callback.onError(result.getRetcode(), App.getInstance().getErrorMsg(result.getRetcode()));
                }
            }
        });
    }
}
