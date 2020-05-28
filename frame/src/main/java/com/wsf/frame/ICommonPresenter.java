package com.wsf.frame;

public interface ICommonPresenter<P> extends ICommonView{
    //1.由他作为中间层来发起网络请求 2.将请求的结果回调到view层
    void getData(int whichApi,P...pPS);
//    void getData(int whichApi,int loadType,P...pPS);
//    default void getThrendsData(int whichApi,int loadType,P...pPS){};
}
