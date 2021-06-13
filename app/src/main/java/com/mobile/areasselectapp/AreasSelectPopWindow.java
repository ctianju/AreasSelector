package com.mobile.areasselectapp;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @description 地区选择View
 * @note
 **/
public class AreasSelectPopWindow extends PopupWindow {
    private static final String TAG = AreasSelectPopWindow.class.getName();
    private final int DEFAULT_POSITION = 0; // 默认选择的位置为 0
    private Context context;
    private View view;
    private LinearLayout parentView;

    //省的listView
    private ListView provinceListview;
    // 省级adapter
    private AreaTextAdapter provinceAdapter;
    //市的listView
    private ListView cityListview;
    // 市adapter
    private AreaTextAdapter cityAdapter;
    //区的listView
    private ListView areaListView;
    // 区adapter
    private AreaTextAdapter areaAdapter;

    private View layer;// 遮罩层

    //当前选中省联动的市级别
    private ArrayList<AreaEntity> citys;
    ////当前选中市 联动的区级别
    private ArrayList<AreaEntity> areas;

    //当前省市区的选中位置，
    public int provincePosition = 0;
    public int cityPosition = 0;
    public int areasPosition = 0;

    private ResultCallBack textBack;  // 选中结果返回
    private ArrayList<AreaEntity> areaListProvince = new ArrayList<>();// 省级
    private final Map<String, ArrayList<AreaEntity>> areaMapCity = new HashMap<>(); // 市级
    private final Map<String, ArrayList<AreaEntity>> areaMap = new HashMap<>(); // 区级
    private int provinceId;//省Id
    private int cityId;//市Id
    private int areaId;//区Id
    private String selectAreaName; // 当前选中的名字


    /**
     * 进行分级省，市、区
     */
    public void addData(ArrayList<AreaEntity> list) {
        if (null != list && list.size() > 0) {
            AreaEntity allArea = new AreaEntity("0", "-1", "全部地区", "-1", new ArrayList<>());
            areaListProvince.add(allArea);// 全部地区
            areaListProvince.addAll(list);//一级 省  直接赋值

            for (int i = 0; i < areaListProvince.size(); i++) {
                ArrayList<AreaEntity> cityEntitys = areaListProvince.get(i).getChildren();
                areaMapCity.put(areaListProvince.get(i).getAreaName(), cityEntitys);//二级  市  循环赋值
                for (int j = 0; j < cityEntitys.size(); j++) {

                    ArrayList<AreaEntity> areaEntityArrayList = new ArrayList<>();
                    areaEntityArrayList.add(allArea);
                    areaEntityArrayList.addAll(cityEntitys.get(j).getChildren());

                    areaMap.put(cityEntitys.get(j).getAreaName(), areaEntityArrayList);
                }
            }
        }
        init();
    }

    public AreasSelectPopWindow(Context context) {
        super(context);
        this.context = context;
        this.view = View.inflate(context, R.layout.select_areas_pop, null);
        setContentView(view);
//        setWidth(LinearLayout.LayoutParams.WRAP_CONTENT);
//        setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
//        setTouchable(true);
//        setFocusable(true);
//        setOutsideTouchable(true);
        setBackgroundDrawable(new ColorDrawable());
    }

    /**
     * 最后结果回调
     */
    public interface ResultCallBack {
        void textBack(String areaName, int proviceId, int crtyId, int areaId);
    }

    public void setTextBackListener(ResultCallBack textBack) {
        this.textBack = textBack;
    }


    private void init() {
        layer = view.findViewById(R.id.dissmiss);
        provinceListview = view.findViewById(R.id.listview1);
        parentView = view.findViewById(R.id.parent);
        cityListview = view.findViewById(R.id.listview2);
        areaListView = view.findViewById(R.id.listview3);
        initDefault();
        layer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        provinceListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                provincePosition = position;
                // 选择省Id
                provinceId = Integer.valueOf(areaListProvince.get(provincePosition).getAreaId());

                provinceAdapter.setSelectedPosition(provincePosition);
                if (position == 0) { // 点击了全部地区省
                    selectAreaName = areaListProvince.get(provincePosition).getAreaName();
                    textBack.textBack(selectAreaName, provinceId, 0, 0);
                    cityAdapter.setData(new ArrayList<>());
                    areaAdapter.setData(new ArrayList<>());
//                    AreasSelectPopWindow.this.dismiss();
                    return;
                }
                citys = areaMapCity.get(areaListProvince.get(provincePosition).getAreaName());
                cityAdapter.setData(citys);

//                cityAdapter.setSelectedPosition(DEFAULT_POSITION);
//                cityPosition = DEFAULT_POSITION; // 此处必须手动赋值默认值，防止不点击中间栏
                cityListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {

                        cityPosition = position; // 被点击就直接赋值
                        cityAdapter.setSelectedPosition(cityPosition);

                        selectAreaName = citys.get(cityPosition).getAreaName();
                        // 选择市Id
                        cityId = Integer.valueOf(citys.get(cityPosition).getAreaId());

                        areas = areaMap.get(citys.get(cityPosition).getAreaName());
                        areaAdapter.setData(areas);
//                        areaAdapter.setSelectedPosition(DEFAULT_POSITION);
                    }
                });

//                // 点击完省就要显示区，位置不能变
//                areas = areaMap.get(citys.get(DEFAULT_POSITION).getAreaName());
                areaAdapter.setData(new ArrayList<>());
//                areaAdapter.setSelectedPosition(DEFAULT_POSITION);
            }
        });

        areaListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent,
                                    View view, int position, long id) {
                areasPosition = position;
                if (position != 0) {
                    selectAreaName = areas.get(areasPosition).getAreaName();
                }
                areaAdapter.setSelectedPosition(areasPosition);

                areaId = Integer.valueOf(areas.get(areasPosition).getAreaId());

                // 选择区Id

//                provinceId = Integer.valueOf(areaListProvince.get(provincePosition).getAreaId());
//                cityId = Integer.valueOf(areaMapCity.get(province).get(cityPosition).getAreaName());
//                area = areaMap.get(city).get(areasPosition).getAreaName();

                textBack.textBack(selectAreaName, provinceId, cityId, areaId);
            }
        });
    }

    /**
     * 默认显示以及初始化、设置适配器
     * 第一次打开显示。默认显示第一个
     */
    private void initDefault() {

        //-------------------------------------------------------------------

        provinceAdapter = new AreaTextAdapter(context, areaListProvince);
        provinceListview.setAdapter(provinceAdapter);
        provincePosition = DEFAULT_POSITION;
        provinceAdapter.setSelectedPosition(DEFAULT_POSITION);

        //显示区域屏幕一半
        int scrheight = DensityUtil.getScreenHeight(context) / 2;
        View listItem = provinceAdapter.getView(0, null, provinceListview);
        listItem.measure(0, 0);
        int relheight = listItem.getMeasuredHeight();
        if (relheight * areaListProvince.size() > scrheight) {
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) parentView.getLayoutParams();
            layoutParams.width = LinearLayout.LayoutParams.MATCH_PARENT;
            layoutParams.height = scrheight;
            this.parentView.setLayoutParams(layoutParams);
        }

//        citys = areaMapCity.get(areaListProvince.get(DEFAULT_POSITION).getAreaName());
//        cityPosition = DEFAULT_POSITION;
//
        cityAdapter = new AreaTextAdapter(context, new ArrayList<>());
//        cityAdapter.setSelectedPosition(DEFAULT_POSITION);
        cityListview.setAdapter(cityAdapter);
//
//
//        areasPosition = DEFAULT_POSITION;
//        areas = areaMap.get(citys.get(DEFAULT_POSITION).getAreaName());
        areaAdapter = new AreaTextAdapter(context, new ArrayList<>());
//        areaAdapter.setSelectedPosition(DEFAULT_POSITION);
        areaListView.setAdapter(areaAdapter);
    }
}
