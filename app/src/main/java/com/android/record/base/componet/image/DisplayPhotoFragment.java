package com.android.record.base.componet.image;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hans on 16/7/23.
 * 用来展示一系列大图片的Fragment
 * <p/>
 * 使用指南:
 * 1.根据工程在Common包下写一个Activity,根据工程UI 设计样式
 * </p>
 * 2.将该Activity替换到预留的Layout容器中replace成{@link DisplayPhotoFragment}
 * <p/>
 * 3.如需要对图片进行单击事件监听,可通过setOnPhotoClickListener进行监听
 * <p/>
 * 4.如需要对图片进行长按事件监听,可通过setOnPhotoLongClickListener进行监听
 * <p/>
 * 5.如需要对ViewPager滑动事件监听,可通过setOnPageChangeListener进行监听
 * <p/>
 * 注意事项:
 * 对于base包下的项目通用模块的修改,一定要仔细思考是否符合所有项目通用的情况.
 * 如果仅仅是针对性的项目扩展,请尝试继承修改的方式,或者其他方式.
 * 一般情况下,是不需要直接修改base的
 */
public class DisplayPhotoFragment extends Fragment {
    private static final String KEY_URL_LIST = "url_list";
    private static final String KEY_CLICK_POSITION = "click_position";
    private ViewPager mViewPager;
    private ViewPager.OnPageChangeListener mOnPageChangeListener;
    private List<String> mUrlList;

    /**
     * @param list          可以是本地路径 也可以是 网络图片
     * @param clickPosition 当前点击的位置
     * @return
     */
    public static DisplayPhotoFragment newInstance(ArrayList<String> list, int clickPosition) {
        DisplayPhotoFragment fragment = new DisplayPhotoFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY_URL_LIST, list);
        bundle.putInt(KEY_CLICK_POSITION, clickPosition);
        fragment.setArguments(bundle);
        return fragment;
    }

    public DisplayPhotoFragment() {
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mViewPager = new ViewPager(getActivity());
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mViewPager.setLayoutParams(params);
        mViewPager.setBackgroundColor(Color.parseColor("#000000"));//设置背景为黑色
        return mViewPager;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(savedInstanceState);
    }

    protected void init(Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        mUrlList = (List<String>) bundle.getSerializable(KEY_URL_LIST);
        int clickPosition = bundle.getInt(KEY_CLICK_POSITION);
        initViewPager(mUrlList, clickPosition);
    }

    private void initViewPager(List<String> urlList, final int clickPosition) {
    }


    public void setOnPageChangeListener(final ViewPager.OnPageChangeListener listener) {
        mOnPageChangeListener = listener;
    }

    public int getCurrentPosition() {
        return mViewPager.getCurrentItem();
    }



}
