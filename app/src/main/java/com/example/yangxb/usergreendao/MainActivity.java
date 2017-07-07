package com.example.yangxb.usergreendao;

import android.database.sqlite.SQLiteDatabase;
import android.provider.SyncStateContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.db.DaoMaster;
import com.example.db.DaoSession;
import com.example.db.UserInfo;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.dao.query.QueryBuilder;

public class MainActivity extends AppCompatActivity {
    DaoSession daoSession;
    private DaoMaster dm;
    private SQLiteDatabase db;
    private List<UserInfo> mDatas;
    private MyRecyclerAdapter recycleAdapter;
    private RecyclerView recyclerView;
    private Button sercherButton;
    private EditText number;
    private EditText password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        mDatas = new ArrayList<UserInfo>();
        insertDb();
        setupDatabase();
        queryDb();
    }

    private void queryDb() {
        //查询数据
        QueryBuilder qb = daoSession.queryBuilder(UserInfo.class);
        qb.list();
        mDatas.clear();
        for (int i = 0; i < qb.list().size(); i++) {
            UserInfo bl = (UserInfo) qb.list().get(i);
            mDatas.add(bl);
        }
        setUpRecyclerView();
    }
public void sercherAll(View v){
    Toast.makeText(this,"查询成功",Toast.LENGTH_LONG).show();
    queryDb();

}//
    public void addUser(View v){
        Toast.makeText(this,"添加成功",Toast.LENGTH_LONG).show();
        insertDb();
        queryDb();
    }
    private void init() {
        sercherButton = (Button) findViewById(R.id.sercher);
        number = (EditText) findViewById(R.id.editText);
        password = (EditText) findViewById(R.id.editText2);

    }

    private void setUpRecyclerView() {
        recyclerView = (RecyclerView) findViewById(R.id.view_user );
        recycleAdapter= new MyRecyclerAdapter(MainActivity.this , mDatas,daoSession);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        //设置布局管理器
        recyclerView.setLayoutManager(layoutManager);
        //设置为垂直布局，这也是默认的
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        //设置Adapter
        recyclerView.setAdapter(recycleAdapter);

    }

    private void insertDb() {
        UserInfo userInfo = new UserInfo();
    //    userInfo.setId(1L);
        userInfo.setUsernumber(number.getText().toString());
        userInfo.setUserpassword(password.getText().toString());
        //存入数据库
        if (daoSession != null) {
            daoSession.insert(userInfo);
        }
    //    mDatas.add(userInfo);
    }

    /**
     * 数据库相关
     */
    private void setupDatabase() {
        //创建数据库
        DaoMaster.DevOpenHelper helper =
                new DaoMaster.DevOpenHelper(this,"user.db", null);
        //获取可写数据库
        db = helper.getWritableDatabase();
        //获取数据库对象
        dm = new DaoMaster(db);
        //获取Dao对象的管理者
        daoSession = dm.newSession();
    }
}
