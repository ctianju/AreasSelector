package com.mobile.areasselectapp;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private LinearLayout selectLL;
    private TextView selectTv;
    private AreasSelectPopWindow areasPopWindow;
    private ArrayList<AreaEntity> arrayList;
    //------- ID 需要传递给后台的，可选---------
    private int provinceId; //省ID
    private int cityId;//市ID
    private int areaId;// 区ID

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        selectLL = findViewById(R.id.selectLL);
        selectTv = findViewById(R.id.selectTv);
        selectLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAreaPop();
            }
        });
        initView();
        initData();
    }

    private void initView() {
        areasPopWindow = new AreasSelectPopWindow(this);
        areasPopWindow.setTextBackListener(new AreasSelectPopWindow.ResultCallBack() {
            @Override
            public void textBack(String mareaName, int mproviceId, int mcrtyId, int mareaId) {
                selectTv.setText(mareaName);
                provinceId = mproviceId;
                cityId = mcrtyId;
                areaId = mareaId;
                dismissAllPop();

            }
        });
        areasPopWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                checkUiUpdate(selectTv, false);
            }
        });
    }

    /**
     * 初始化数据
     */
    private void initData() {
        parseFile2Json();
        areasPopWindow.addData(arrayList);

    }

    /**
     * 真是popView
     */
    private void showAreaPop() {
        if (areasPopWindow != null && areasPopWindow.isShowing()) {
            dismissAllPop();
        } else {
            checkUiUpdate(selectTv, true);
            areasPopWindow.showAsDropDown(selectLL, 0, 0);
        }
    }

    /**
     * 从assert文件夹中读取省市区的json文件，然后转化为json对象
     */
    private void parseFile2Json() {
        try {
            StringBuffer sb = new StringBuffer();
            InputStream is = getAssets().open("areas.json");
            int len = -1;
            byte[] buf = new byte[1024];
            while ((len = is.read(buf)) != -1) {
                sb.append(new String(buf, 0, len, "utf-8"));
            }
            is.close();
            arrayList = new Gson().fromJson(sb.toString(), new TypeToken<List<AreaEntity>>() {
            }.getType());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 关闭所有的popWindow
     */
    private void dismissAllPop() {
        if (areasPopWindow != null && areasPopWindow.isShowing()) {
            areasPopWindow.dismiss();
        }
    }

    /**
     * 选中点击的文字
     */
    private void checkUiUpdate(TextView tv, boolean flag) {
        tv.setSelected(flag);
    }
}