package com.example.user.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.zip.Inflater;

/**
 * Created by user on 2016/9/21.
 */
public class TwoActicity extends Activity {
    private ArrayList<HashMap<String,String>> listvalue=new ArrayList<HashMap<String,String>>();
    ListView listView;
    Button btn;
    TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listbug);
        initData();
    }

    private void initData() {
        HashMap<String,String> map=new HashMap<String,String>();
        map.put("付款","100");
        HashMap<String,String> map1=new HashMap<String,String>();
        map1.put("付款","200");
        HashMap<String,String> map2=new HashMap<String,String>();
        map2.put("付款","300");
        HashMap<String,String> map3=new HashMap<String,String>();
        map3.put("付款", "400");
        listvalue.add(map);
        listvalue.add(map1);
        listvalue.add(map2);
        listvalue.add(map3);
        MyAdapter adapter=new MyAdapter();
        listView= (ListView) findViewById(R.id.lv_1);

        listView.setAdapter(adapter);
        btn= (Button) findViewById(R.id.btn_sure);
        tv= (TextView) findViewById(R.id.tv_str);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv.setText(listvalue.get(0).get("付款")+"  "+
                        listvalue.get(1).get("付款")+"  "+
                        listvalue.get(2).get("付款")+"  "+
                        listvalue.get(3).get("付款")+"  ");
            }
        });


    }

    class MyAdapter extends BaseAdapter{
        private Integer index = -1;
        @Override
        public int getCount() {
            return listvalue.size();
        }

        @Override
        public Object getItem(int position) {
            return listvalue.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final ViewHolder holder;
            Log.i("TwoActivity","getView方法");
            if(convertView==null){
                holder=new ViewHolder();
                convertView= LayoutInflater.from(TwoActicity.this).inflate(R.layout.list_item,null);
                holder.et_price= (EditText) convertView.findViewById(R.id.et_price);
                holder.tv_title= (TextView) convertView.findViewById(R.id.tv_title);
                holder.et_price.setTag(position);
                holder.et_price.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                            index = (Integer) view.getTag();
                        }
                        return false;
                    }
                });

                class MyTextWatcher implements TextWatcher {
                    ViewHolder holder1;
                    MyTextWatcher(ViewHolder holder){
                        holder1=holder;
                    }


                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        int position = (Integer) holder1.et_price.getTag();

                        listvalue.get(position).put("付款",holder1.et_price.getText().toString());

                    }
                }
                holder.et_price.addTextChangedListener(new MyTextWatcher(holder));
                convertView.setTag(holder);
            }else{
                holder= (ViewHolder) convertView.getTag();
                holder.et_price.setTag(position);
            }

            holder.et_price.setText(listvalue.get(position).get("付款"));
            return convertView;
        }
        class ViewHolder{
            TextView tv_title;
            EditText et_price;
        }
    }
}
