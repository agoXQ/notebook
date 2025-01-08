
package com.example.notebook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class RecordActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView backtomain,delete,saveNote;
    private TextView title,showtime;
    private EditText content;
    private MyDBhelper myDBhelper;
    public String noteId = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
        init();
        //为控件设置监听器
        backtomain.setOnClickListener(this);
        delete.setOnClickListener(this);
        saveNote.setOnClickListener(this);
        //接收传来的数据
        Intent intent = this.getIntent();
        noteId = intent.getStringExtra("id");
        if(noteId==null){
            title.setText("^V^ 添加数据");
        }else{
            title.setText("^V^ 修改数据");
            showtime.setVisibility(View.VISIBLE);
            showtime.setText(intent.getStringExtra("time"));
            content.setText(intent.getStringExtra("content"));
        }
    }
    public void init(){
        backtomain = findViewById(R.id.backtomain);
        delete=findViewById(R.id.deletefile);
        saveNote=findViewById(R.id.addfile);
        title=findViewById(R.id.title);
        showtime = findViewById(R.id.showTime);
        content=findViewById(R.id.content);
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.addfile){
            String contentdata = content.getText().toString();

            if(noteId==null){
//            System.out.println(contentdata+"114514114514");
                if(contentdata==null){
                    Toast.makeText(RecordActivity.this,"内容不能为空",Toast.LENGTH_LONG).show();
                }else {
                    // 数据的添加
                    myDBhelper= new MyDBhelper(RecordActivity.this,"note.db",null,10);
                    Boolean flag = myDBhelper.insertData(contentdata);
                    if(flag==true){
                        //如果添加成功，将数据的回传码设置为2
                        Toast.makeText(RecordActivity.this,"添加成功",Toast.LENGTH_LONG).show();
                        setResult(2);
                        finish();
                    }else {
                        Toast.makeText(RecordActivity.this,"添加失败",Toast.LENGTH_LONG).show();
                    }
                }
            }
            else{
                if(contentdata==null){
                    Toast.makeText(RecordActivity.this,"内容不能为空",Toast.LENGTH_LONG).show();
                }else {
                    // 数据的添加
                    myDBhelper= new MyDBhelper(RecordActivity.this,"note.db",null,10);
                    Boolean flag = myDBhelper.updateData(noteId,contentdata);
                    if(flag==true){
                        //如果添加成功，将数据的回传码设置为2
                        Toast.makeText(RecordActivity.this,"修改成功",Toast.LENGTH_LONG).show();
                        setResult(2);
                        finish();
                    }else {
                        Toast.makeText(RecordActivity.this,"修改失败",Toast.LENGTH_LONG).show();
                    }
                }
            }
        }else if(view.getId()==R.id.deletefile){
            content.setText("");
        }else if(view.getId()==R.id.backtomain){
            finish();
        }
    }
}