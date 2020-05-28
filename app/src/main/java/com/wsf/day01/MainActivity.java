package com.wsf.day01;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.wsf.data.TestInfo;
import com.wsf.day01.adapter.TestAdapter;
import com.wsf.frame.ApiConfig;
import com.wsf.frame.CommonPresenter;
import com.wsf.frame.ICommonPresenter;
import com.wsf.frame.ICommonView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.wsf.frame.LoadTypeConfig;
import com.wsf.frame.TestModel;
import com.wsf.frame.utils.ParamHashMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements ICommonView {

    private ICommonPresenter presenter;
    private RecyclerView mRecyclerView;
    private SmartRefreshLayout mRefreshLayout;
    private TestAdapter mTestAdapter;
    private TestModel mModel;
    private int pageId = 0;
    private List<TestInfo.DataInfo> datas = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mModel = new TestModel();
        presenter = new CommonPresenter(this,mModel);

        mRecyclerView = findViewById(R.id.recyclerView);
        mRefreshLayout = findViewById(R.id.refreshLayout);
        final Map<String, Object> params = new ParamHashMap().add("c", "api").add("a", "getList");

        mRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                pageId++;
                presenter.getData(ApiConfig.TEST_GET, LoadTypeConfig.MORE,params,pageId);
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                pageId = 0;
                presenter.getData(ApiConfig.TEST_GET, LoadTypeConfig.REFRESH,params,pageId);
            }
        });
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mTestAdapter = new TestAdapter(datas, this);
        mRecyclerView.setAdapter(mTestAdapter);
        presenter.getData(ApiConfig.TEST_GET, LoadTypeConfig.NORMAL,params,pageId);
    }

    @Override
    public void onSuccess(int whichApi, int loadType, Object[] pD) {
        switch (whichApi){
            case ApiConfig.TEST_GET:
                if(loadType==LoadTypeConfig.MORE){
                    mRefreshLayout.finishLoadMore();
                }else if (loadType==LoadTypeConfig.REFRESH){
                    mRefreshLayout.finishRefresh();
                    datas.clear();
                }
                break;
        }
        List<TestInfo.DataInfo> datas = ((TestInfo)pD[0]).datas;
        MainActivity.this.datas.addAll(datas);
        mTestAdapter.notifyDataSetChanged();
    }

    @Override
    public void onFailed(int whichApi, Throwable pThrowable) {
        Toast.makeText(MainActivity.this, pThrowable.getMessage()!=null ? pThrowable.getMessage():"网络请求发生错误", Toast.LENGTH_SHORT).show();

    }
}
