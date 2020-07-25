package com.example.film_review.firstpage.search;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.film_review.MainActivity;
import com.example.film_review.R;
import com.example.film_review.firstpage.RetrofitAPI;
import com.example.film_review.firstpage.review_content.ContentActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class activity_search extends AppCompatActivity implements View.OnClickListener{
    private FlowLayout mFlowLayout;
    private LayoutInflater mInflater;

    private String mSave = "";
    private TextView mTextView;
    private int count_printf = 0;

    /*以上是和流水标签有关的*/
    public static final String EXTRA_KEY_TYPE = "extra_key_type";
    public static final String EXTRA_KEY_KEYWORD = "extra_key_keyword";
    public static final String KEY_SEARCH_HISTORY_KEYWORD = "key_search_history_keyword";
    private SharedPreferences mPref;//记录搜索历史
    private String mType;
    private EditText input;
    private Button btn_search;
    private List<String> mHistoryKeyWords;//记录文本
    private ArrayAdapter<String> mArrayAdapter;//搜索历史适配器
    private LinearLayout mSearchHistory;
    private searchData msearchData;
    private List<searchData> searchList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_search_flowlayout);


        initFlowView();

        initHistoryView();
        btn_search =findViewById(R.id.btn_search);
        btn_search.setOnClickListener(this);
    }

    private void initFlowView() {
        mInflater = LayoutInflater.from(this);
        mFlowLayout = (FlowLayout)findViewById(R.id.flowlayout);
        initData();
    }

    //将数据放入流式布局
    private void initData(){
        int count = 0;
        SharedPreferences sharedPreferences = getSharedPreferences("User",0);
        mSave = sharedPreferences.getString("name","");

        if(!mSave.equals("")){
            final String[] array = mSave.split(",");
            for (int i=array.length -1; i>=0; i--){
                mTextView = (TextView) mInflater.inflate(R.layout.search_label_tv,mFlowLayout,false);
                count++;
                if(count>10){
                    break;
                }else{
                    count_printf++;
                }
                mTextView.setText(array[i]);
                mFlowLayout.addView(mTextView);

                final String str = mTextView.getText().toString();

                mTextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        input.setText(str);//加入搜索历史记录
                    }
                });
            }
        }
        //Toast.makeText(activity_search.this,count_printf+"",Toast.LENGTH_LONG).show();
    }//以上和流式标签有关

    private void initHistoryView() {
        input = findViewById(R.id.et_input);
        btn_search = findViewById(R.id.btn_search);
        btn_search.setOnClickListener(this);

        mPref = getSharedPreferences("input", Activity.MODE_PRIVATE);
        mType = getIntent().getStringExtra(EXTRA_KEY_TYPE);
        String keyword = getIntent().getStringExtra(EXTRA_KEY_KEYWORD);
        if(!TextUtils.isEmpty(keyword)){
            input.setText(keyword);
        }
        mHistoryKeyWords = new ArrayList<String>();
        input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() == 0){
                    mSearchHistory.setVisibility(View.VISIBLE);
                }else{
                    mSearchHistory.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        initSearchHistory();
    }

    //初始化搜索历史记录
    public void initSearchHistory() {
        mSearchHistory = findViewById(R.id.search_history_ll);
        ListView listView = findViewById(R.id.search_history_lv);
        findViewById(R.id.clear_history_btn).setOnClickListener((View.OnClickListener) this);
        String history = mPref.getString(KEY_SEARCH_HISTORY_KEYWORD,"");
        if(!TextUtils.isEmpty(history)){
            List<String> list = new ArrayList<String>();
            for (Object o :history.split(",")){
                list.add((String) o);
            }
            mHistoryKeyWords = list;
        }
        if(mHistoryKeyWords.size()>0){
            mSearchHistory.setVisibility(View.VISIBLE);
        }else{
            mSearchHistory.setVisibility(View.VISIBLE);
        }
        mArrayAdapter = new ArrayAdapter<String>(this,R.layout.item_search_history,mHistoryKeyWords);
        listView.setAdapter(mArrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                input.setText(mHistoryKeyWords.get(position));
                mSearchHistory.setVisibility(View.GONE);
            }
        });
        mArrayAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.btn_search:
                String et_new = input.getText().toString().trim();
                if(et_new.equals("")){
                    return;
                }
                else {
                    searchList=new ArrayList<>();
                    SearchText searchText=new SearchText();
                    searchText.setWords(et_new);
                    Retrofit retrofit= new Retrofit.Builder()
                            .baseUrl("http://114.215.201.204:9091")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                    RetrofitAPI API=retrofit.create(RetrofitAPI.class);
                    Call<List<searchData>> task=API.search(searchText);
                    task.enqueue(new Callback<List<searchData>>() {
                        @Override
                        public void onResponse(Call<List<searchData>> call, Response<List<searchData>> response) {
                            int code=response.code();
                            if(code==200) {
                                Toast.makeText(getApplicationContext(),"搜索成功，正在跳转到具体影评界面",Toast.LENGTH_SHORT).show();
                                if(response.body()!=null){
                                    searchList.addAll(response.body());
                                    Log.d("i", "i" + response.body().get(0).getName() + code);
                                    Intent intent = new Intent(getApplicationContext(), ContentActivity.class);
                                    Bundle bundle=new Bundle();
//                        传入影评请求是需要的id
                                    bundle.putInt("Id",searchList.get(0).getReview_id());
                                    bundle.putString("content",searchList.get(0).getContent());
                                    bundle.putString("UserName",searchList.get(0).getName());
                                    bundle.putString("reviewTitle",searchList.get(0).getTitle());
                                    bundle.putString("reviewTag",searchList.get(0).getTag());
                                    bundle.putString("reviewPicture",searchList.get(0).getPicture());
                                    bundle.putString("reviewTime",searchList.get(0).getTime());
                                    bundle.putString("reviewIcon",searchList.get(0).getUser_picture());
                                    intent.putExtras(bundle);
                                    startActivity(intent);}
                                else Toast.makeText(getApplicationContext(),"没有找到相应的影评",Toast.LENGTH_SHORT).show();
                            }
                            else if(code==404)
                                Toast.makeText(getApplicationContext(),"没有找到相应的影评",Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<List<searchData>> call, Throwable t) {

                        }
                    });
                }

                mSave = mSave + et_new +",";
                SharedPreferences sharedPreferences = getSharedPreferences("User",0);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("name",mSave);
                editor.commit();
                search_btn_addView();

                break;

            case R.id.clear_history_btn:
                mFlowLayout.removeAllViews();
                cleanHistory();
                break;
            default:
                break;
        }
    }

    private void search_btn_addView() {
        mTextView = (TextView) mInflater.inflate(R.layout.search_label_tv,mFlowLayout,false);
        mTextView.setText(input.getText().toString());
        count_printf++;
        if(count_printf>10){
            mFlowLayout.removeViews(9,1);
        }
        mFlowLayout.addView(mTextView,0);
        final String str = mTextView.getText().toString();
        mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                input.setText(str);
            }
        });
        input.setText("");
    }//把新搜索的放到flow_layout第一个

    private void cleanHistory() {
        SharedPreferences sharedPreferences = getSharedPreferences("User",0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("name","");
        mSave = "";
        count_printf = 0;
        editor.commit();
    }//清除历史记录

    //储存搜索历史
    public void save(){
        String text = input.getText().toString();
        String oldText = mPref.getString(KEY_SEARCH_HISTORY_KEYWORD,"");
        Log.e("tag",""+oldText);
        Log.e("Tag",""+text);
        Log.e("Tag",""+oldText.contains(text));
        if (!TextUtils.isEmpty(text)&& !(oldText.contains(text))){
            if(mHistoryKeyWords.size()>5){
                return;
            }
            SharedPreferences.Editor editor = mPref.edit();
            editor.putString(KEY_SEARCH_HISTORY_KEYWORD,text+","+oldText);
            editor.commit();
            mHistoryKeyWords.add(0,text);
        }
        mArrayAdapter.notifyDataSetChanged();
    }
}
