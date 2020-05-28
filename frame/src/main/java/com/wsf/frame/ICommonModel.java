package com.wsf.frame;

public interface ICommonModel<T> {
    //用于model层执行耗时任务,不处理刷新和加载逻辑
    //whichApi 区别接口标识
    //params 泛型可变参数
    void getData(ICommonPresenter pPresenter,int whichApi,T...params);

    //用于model层执行耗时任务,不处理刷新和加载逻辑
    //whichApi 区别接口标识
    //params 泛型可变参数
    //void getData(ICommonPresenter pPresenter,int whichApiint,int dataType,T...params);
    /**
     * 用于model层执行耗时任务,不处理刷新和加载逻辑
     * 等到8.0以下手机淘汰的那一天，我们可以将以下方法替代上边的方法，这样可以避免烦躁的强制重写
     * */
//    default void getTrendsData(ICommonPresenter pPresenter,int whichApi,int dataType,T...params){};
}
