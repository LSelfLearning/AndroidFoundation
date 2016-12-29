package com.start.lewish.sqlitedemo;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.start.lewish.sqlitedemo.R;
import com.start.lewish.sqlitedemo.bean.BlackNumber;
import com.start.lewish.sqlitedemo.dao.BlackNumberDAO;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView lv_blacknumber;
    private BlackNumberAdapter adapter;
    private List<BlackNumber> data;
    private BlackNumberDAO dao;
    private EditText et_main_blacknumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //初始化操作：
        lv_blacknumber = (ListView) findViewById(R.id.lv_blacknumber);
        dao = new BlackNumberDAO(this);
        et_main_blacknumber = (EditText) findViewById(R.id.et_main_blacknumber);
        //从数据库中查询数据，得到一个黑名单的集合
        data = dao.getAll();

        adapter = new BlackNumberAdapter();
        //显示列表
        lv_blacknumber.setAdapter(adapter);

        //给ListView设置长按，显示ContextMenu的监听
        lv_blacknumber.setOnCreateContextMenuListener(this);
    }

    //继承于BaseAdapter的内部子类
    class BlackNumberAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return data.size();
        }

        @Override
        public BlackNumber getItem(int position) {
            // TODO Auto-generated method stub
            return data.get(position);
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView == null){
                convertView = View.inflate(MainActivity.this, R.layout.blacknumber_item, null);
            }
            //方式一：
//			convertView.findViewById(R.id.textView1);
            //方式二：
            TextView tv_number = (TextView) convertView;

            tv_number.setText(data.get(position).getNumber());

            return convertView;
        }

    }
    //点击"添加"button时的回调方法。将EditText中的黑名单号码显示出来
    //数据更新需要考虑的位置：页面显示-内存-存储
    public void add(View v){
        //1.获取EditText中的号码
        String number = et_main_blacknumber.getText().toString().trim();
        //2.将号码封装为BlackNumber的对象
        BlackNumber blackNumber = new BlackNumber(-1, number);
        //3.将得到的BlackNumber的对象保存在数据库中
        dao.insert(blackNumber);
        //4.得到更新以后的集合数据
        data = dao.getAll();
        //5.更新页面显示
        adapter.notifyDataSetChanged();

        Toast.makeText(this, "添加成功", 0).show();
        et_main_blacknumber.setText(null);
    }

    //给ListView设置长按，显示ContextMenu的监听的回调方法
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenuInfo menuInfo) {
        menu.add(Menu.NONE, 1, Menu.NONE, "更新");
        menu.add(Menu.NONE, 2, Menu.NONE, "删除");
//		Log.e("TAG", menuInfo.getClass().toString());
        AdapterContextMenuInfo info = (AdapterContextMenuInfo) menuInfo;
        position = info.position;//position的初始化
    }
    private int position;//记录出现ContextMenu时点击的item的位置
    //点击ContextMenu中具体的某一个item时的回调方法
    //更新界面的操作：显示-内存-存储
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        //错误的写法：
//			dao.deleteById(position);
        //正确的写法：
        final BlackNumber blackNumber = data.get(position);
        switch(item.getItemId()){
            case 1://更新

                final EditText et_new_number = new EditText(this);
                et_new_number.setHint("新的号码");

                new AlertDialog.Builder(this)
                        .setTitle("更新" + blackNumber.getNumber())
                        .setView(et_new_number)
                        .setPositiveButton("更新", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //获取新的号码
                                String newNumber = et_new_number.getText().toString().trim();
                                blackNumber.setNumber(newNumber);
                                dao.update(blackNumber);//存储中的修改
//					data.set(position, blackNumber);//内存的修改。此行代码可省略
                                adapter.notifyDataSetChanged();//显示的修改
                            }
                        })
                        .setNegativeButton("取消", null)
                        .show();

                break;
            case 2://删除
                int id = blackNumber.getId();//得到要删除的数据的id，即为数据表中要删除的_id。
                dao.deleteById(id);//存储中的修改
//			data = dao.getAll();//方法一：
                data.remove(position);//方法二。 内存中的修改
                adapter.notifyDataSetChanged();//显示上的修改
                break;

        }


        return super.onContextItemSelected(item);
    }
}
