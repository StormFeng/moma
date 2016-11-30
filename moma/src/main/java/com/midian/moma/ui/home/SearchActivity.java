package com.midian.moma.ui.home;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.midian.moma.R;
import com.midian.moma.customview.DimedView;
import com.midian.moma.db.RecordSQLiteOpenHelper;
import com.midian.moma.model.KeywordsBean;
import com.midian.moma.utils.AppUtil;

import midian.baselib.base.BaseActivity;
import midian.baselib.bean.NetResult;
import midian.baselib.utils.UIHelper;
import midian.baselib.widget.BaseLibTopbarView;
import midian.baselib.widget.FlowLayouts;
import midian.baselib.widget.MyListView;

/**
 * 广告位搜索页
 * Created by chu on 2016/3/29.
 */
public class SearchActivity extends BaseActivity implements TextView.OnEditorActionListener, AdapterView.OnItemClickListener, View.OnFocusChangeListener {
    private BaseLibTopbarView topbar;
    private TextView clean_tv;
    private MyListView listView;
    private EditText input_et;
    private DimedView dime_view;
    private FlowLayouts mFlowLayout;
    private SQLiteDatabase db;
    private RecordSQLiteOpenHelper helper = new RecordSQLiteOpenHelper(this);
    private BaseAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        inintView();
    }

    private void inintView() {
        topbar = findView(R.id.topbar);
        topbar.setMode(BaseLibTopbarView.MODE_WITH_INPUT).setLeftImageButton(R.drawable.icon_back, null);
        topbar.setLeftText("返回", UIHelper.finish(_activity));
        mFlowLayout = findView(R.id.flow_layout);
        topbar.getInput_et().setHint("请输入搜索内容");
        input_et = topbar.getInput_et();
        input_et.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        input_et.setOnEditorActionListener(this);
        input_et.setOnFocusChangeListener(this);
        clean_tv = findView(R.id.clean);
        listView = findView(R.id.listView);
        dime_view = findView(R.id.dime_view);
        clean_tv.setOnClickListener(this);
        queryData("");//模糊查询·
        loadData();

        // 搜索框的键盘搜索键点击回调
        input_et.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {//修改回车键功能
                    //先隐藏键盘
                    ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromInputMethod(
                            getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    String edit_text=input_et.getText().toString().trim();
                    if (edit_text == null || edit_text.length() == 0 || edit_text.equals("")) {
                        UIHelper.t(_activity,"搜索不能不空!");
                        return false;
                    }
                    //按完搜索键后将当前查询的关键字保存起来，如果该关键字已经存在就不执行保存
                    boolean hasData = hasData(input_et.getText().toString().trim());
                    if (!hasData) {
                        insertData(input_et.getText().toString().trim());//往数据库插入数据
                        queryData("");//模糊查询·
                    }
                    // TODO: 2016/3/30 根据输入的内容模糊查询关键字，并跳到另一个界面，
                    onSearchKey(edit_text);
                }
                return false;
            }
        });

        //搜索框的文本变化实时监听
        input_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                //监听输入框中输入后的文本变化长度
                if (s.toString().trim().length() == 0) {
                    UIHelper.t(_activity,"搜索内容不能为空");
                    return;
                }

                String tempName = input_et.getText().toString();
                //根据tempName去模糊查询数据库中有没有数据
                queryData(tempName);
            }
        });
        listView.setOnItemClickListener(this);
    }
    private void loadData() {
        search();//热词请求接口
    }

    /**
     * 监听输入框焦点事件
     * @param view
     * @param b
     */
    @Override
    public void onFocusChange(View view, boolean b) {
        if (b) {
            System.out.println("输入框焦点改变");

        }
    }


    /**
     * 输入框的编辑操作
     */
    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        return false;
    }


    //请求热词的接口
    private void search() {
        AppUtil.getMomaApiClient(ac).keywords(this);
    }




    @Override
    public void onApiSuccess(NetResult res, String tag) {
        super.onApiSuccess(res, tag);
        if (res.isOK()) {
            if ("keywords".equals(tag)) {
                KeywordsBean keywordsBean = (KeywordsBean) res;
                for (KeywordsBean.KeywordsContent keyword : keywordsBean.getContent()) {
                    String key = keyword.getKeywords();
                    addTextView(key);
                }
            } else {
                input_et.clearFocus();
                ac.handleErrorCode(_activity, res.error_code);
            }
        }
    }

    @Override
    public void onApiFailure(Throwable t, int errorNo, String strMsg, String tag) {
        super.onApiFailure(t, errorNo, strMsg, tag);
        hideLoadingDlg();
    }

    /**
     * 把textview加入到流布局
     */
    private void addTextView(final String keyword) {
        //加载TextView并设置名称，并设置名称
       final TextView tv = (TextView) LayoutInflater.from(this).inflate(R.layout.tv, mFlowLayout, false);
        tv.setText(keyword);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //点击事件后将当前的关键字保存起来，如果该关键字已经存在就不执行保存
                boolean hasData = hasData(keyword);
                if (!hasData) {
                    insertData(keyword);//往数据库插入数据
                    queryData("");//模糊查询·
                }
                onSearchKey(keyword);
            }
        });

        //把TextView加入流式布局
        mFlowLayout.addView(tv);
    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.clean:
                deleteData();
                queryData("");
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        TextView textView = (TextView) view.findViewById(R.id.tv1);
        String name = textView.getText().toString();
        input_et.setText(name);
        ///  2016/3/30  获取到历史记录上的文字，根据关键字跳转页面
        onSearchKey(name);
    }

    /**
     * 插入数据
     */
    private void insertData(String tempName) {
        db = helper.getWritableDatabase();
        db.execSQL("insert into records(name) values('" + tempName + "')");
        db.close();
    }

    /**
     * 模糊查询数据
     */
    private void queryData(String tempName) {
        Cursor cursor = helper.getReadableDatabase().rawQuery(
                "select id as _id,name from records where name like '%" + tempName + "%' order by id desc ", null);
        // 创建adapter适配器对象
        adapter = new SimpleCursorAdapter(this, R.layout.item_search_result, cursor, new String[]{"name"},
                new int[]{R.id.tv1}, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        // 设置适配器
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    /**
     * 检查数据库中是否已经有该条记录
     */
    private boolean hasData(String tempName) {
        Cursor cursor = helper.getReadableDatabase().rawQuery(
                "select id as _id,name from records where name =?", new String[]{tempName});
        //判断是否有下一个
        return cursor.moveToNext();
    }

    /**
     * 清空数据
     */
    private void deleteData() {
        db = helper.getWritableDatabase();
        db.execSQL("delete from records");
        db.close();
    }


    //**根据关键词跳到搜索结果页面*/
    public void onSearchKey(String keywords) {
        Bundle mBundle = new Bundle();
        mBundle.putString("keywords", keywords);
        //全局搜索列表页
        UIHelper.jump(_activity, SearchAdvertResultActivity.class, mBundle);
        //listViewHelper.refresh();
        input_et.clearFocus();
    }



}
