package com.wsf.frame;

import com.wsf.data.TestInfo;

import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class TestModel implements ICommonModel{

    @Override
    public void getData(ICommonPresenter pPresenter, int whichApi, Object[] params) {
        int loadType = (int) params[0];
        Map param = (Map) params[1];
        int pageId= (int) params[2];
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://static.owspace.com/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(new OkHttpClient())
                .build();

        Observable<TestInfo> data = retrofit.create(IService.class).getTestData(param,pageId);
        data.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<TestInfo>() {
                    @Override
                    public void accept(TestInfo pTestInfo) throws Exception {
                        pPresenter.onSuccess(whichApi,loadType,pTestInfo);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable pThrowable) throws Exception {
                        pPresenter.onFailed(whichApi,pThrowable);
                    }
                });
    }

//    @Override
//    public void getData(ICommonPresenter pPresenter, int whichApiint, int dataType, Object[] params) {
//        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://static.owspace.com/")
//                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                .addConverterFactory(GsonConverterFactory.create())
//                .client(new OkHttpClient())
//                .build();
//
//        Observable<TestInfo> data = retrofit.create(IService.class).getTestData((Map<String, Object>)params[0]);
//        data.subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Consumer<TestInfo>() {
//                    @Override
//                    public void accept(TestInfo pTestInfo) throws Exception {
//                        pPresenter.onSuccess(whichApi,pTestInfo);
//                    }
//                }, new Consumer<Throwable>() {
//                    @Override
//                    public void accept(Throwable pThrowable) throws Exception {
//                        pPresenter.onFailed(whichApi,pThrowable);
//                    }
//                });
//    }

//    @Override
//    public void getTrendsData(ICommonPresenter pPresenter, int whichApi, int dataType, Object[] params) {
//
//    }
}
