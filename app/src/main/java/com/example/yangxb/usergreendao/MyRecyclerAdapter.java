package com.example.yangxb.usergreendao;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.db.DaoSession;
import com.example.db.UserInfo;

import java.util.List;

/**
 * Created by yangxb on 17-7-6.
 */
public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.MyViewHolder>{
    private List<UserInfo> mDatas;
    private Context mContext;
    private LayoutInflater inflater;
    DaoSession daoSession;

    public MyRecyclerAdapter(Context context, List<UserInfo> datas, DaoSession daoSession){
        this. mContext=context;
        this. mDatas=datas;
        this. daoSession=daoSession;
        inflater=LayoutInflater. from(mContext);//
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_user,parent, false);
        MyViewHolder holder= new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.id.setText(mDatas.get(position).getId()+"");
        holder.nu.setText(mDatas.get(position).getUsernumber());
        holder.pass.setText(mDatas.get(position).getUserpassword());
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                daoSession.delete(mDatas.get(position));
                mDatas.remove(position);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView id;
        TextView nu;
        TextView pass;
        Button change;
        Button delete;

        public MyViewHolder(View itemView) {
            super(itemView);
            id=(TextView) itemView.findViewById(R.id.textViewid);
            nu=(TextView) itemView.findViewById(R.id.textViewnu);
            pass=(TextView) itemView.findViewById(R.id.textViewpass);
            change= (Button) itemView.findViewById(R.id.buttonchange);
            delete= (Button) itemView.findViewById(R.id.buttondelete);
            change.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(v.getId() == R.id.buttonchange){
                showAddDialog();
            }
        }

        protected void showAddDialog() {

            LayoutInflater factory = LayoutInflater.from(mContext);
            final View textEntryView = factory.inflate(R.layout.dialog, null);
            final EditText editTextName = (EditText) textEntryView.findViewById(R.id.editTextName);
            final EditText editTextpassword = (EditText)textEntryView.findViewById(R.id.editTextNum);
            editTextName.setText(nu.getText());
            editTextpassword.setText(pass.getText());
            final AlertDialog.Builder ad1 = new AlertDialog.Builder(mContext);
            ad1.setTitle("修改用户信息:");
            ad1.setIcon(android.R.drawable.ic_dialog_info);
            ad1.setView(textEntryView);
            ad1.setPositiveButton("是", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int i) {
                    System.out.println("befor=="+mDatas.get(getAdapterPosition()).getUsernumber());
                    mDatas.get(getAdapterPosition()).setUsernumber(editTextName.getText().toString());
                    mDatas.get(getAdapterPosition()).setUserpassword(editTextpassword.getText().toString());
                    nu.setText(editTextName.getText().toString());//同步显示
                    pass.setText(editTextpassword.getText().toString());//同步显示
                    System.out.println("after=="+mDatas.get(getAdapterPosition()).getUsernumber());
                    daoSession.update(mDatas.get(getAdapterPosition()));

                }
            });
            ad1.setNegativeButton("否", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int i) {
                }
            });
            ad1.show();// 显示对话框

        }
    }
}
