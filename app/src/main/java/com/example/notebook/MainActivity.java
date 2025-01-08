package com.example.notebook;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ListView listView;
    private ImageView add;
    private MyAdapter myAdapter;
    private MyDBhelper dBhelper;
    private List<Note> resultList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.list);
        add = findViewById(R.id.add);
        dBhelper = new MyDBhelper(MainActivity.this,"note.db",null,10);
        //为控件添加点击事件
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,RecordActivity.class);
                startActivityForResult(intent,1);
            }
        });
        init();
// 设置列表项的监听器,对相应内容进行更新
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                获取到用户选择的哪一项

                Note note = (Note) myAdapter.getItem(i);
//                创建意图，将用户选择的内容传递到修改页面
                Intent intent = new Intent(MainActivity.this,RecordActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("id",note.getId());
                bundle.putString("content",note.getContent());
                bundle.putString("time",note.getTime());
                intent.putExtras(bundle);
                startActivityForResult(intent,1);

            }
        });
//        对相应内容进行删除
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            adapterView是父容器，view是，i是position，l是id
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                AlertDialog dialog =null;
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("删除记录")
                        .setMessage("你确定要删除当前记录吗")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {
                                Note note =(Note) myAdapter.getItem(i);
                                String deleteId = note.getId();
                                if(dBhelper.deleteData(deleteId)){
                                    Toast.makeText(MainActivity.this,"删除成功",Toast.LENGTH_LONG).show();
                                    init();

                                }else {
                                    Toast.makeText(MainActivity.this,"删除失败",Toast.LENGTH_LONG).show();
                                }
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
//                确定删除的记录是哪一条
                dialog = builder.create();
                dialog.show();

                return true;
            }
        });
    }
    public void initItem() {
        listView = findViewById(R.id.list);
        add = findViewById(R.id.add);
        dBhelper = new MyDBhelper(MainActivity.this, "note.db", null, 10);
    }
    public void init(){
        if(resultList!=null){
            resultList.clear();
        }
        dBhelper = new MyDBhelper(MainActivity.this,"note.db",null,10);
        resultList = dBhelper.query();
//        System.out.println("当前数据长度为:"+resultList.size());
        myAdapter = new MyAdapter(resultList,MainActivity.this);
        listView.setAdapter(myAdapter);
    }
    // 数据回传时自动调用的方法

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1&&resultCode==2){
            // 说明数据的添加操作是正常执行的,数据库新增一条记录，主页的内容就应该新增一下
            init();
//            Toast.makeText(MainActivity.this,"执行了初始化",Toast.LENGTH_LONG).show();
            System.out.println("执行了init操作");
        }
    }
}