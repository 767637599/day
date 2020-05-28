package com.wsf.frame;

public class CommonPresenter implements ICommonPresenter {
    private ICommonView view;
    private ICommonModel mModel;

    public CommonPresenter(ICommonView view, ICommonModel mModel) {
        this.view = view;
        this.mModel = mModel;
    }

    @Override
    public void onSuccess(int whichApi, int loadType, Object... pD) {
        view.onSuccess(whichApi, loadType, pD);
    }

    @Override
    public void onFailed(int whichApi, Throwable pThrowable) {
        view.onFailed(whichApi, pThrowable);
    }

    /**
     * 发起普通网络请求
     */
    @Override
    public void getData(int whichApi, Object... pObjects) {
        mModel.getData(this,whichApi,pObjects);
    }

//    @Override
//    public void getData(int whichApi, int loadType, Object[] pPS) {
//        mModel.getData(this, whichApi, loadType, pPS);
//    }

    /**
     * 发起涉及刷新和加载的网络请求
     */
//    @Override
//    public void getThrendsData(int whichApi, int loadType, Object... pObjects) {
//
//    }
}
