package com.example.appdocbao;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class mysqlAdapter extends BaseAdapter {
    private mysql context;
    private int layout;
    private List<DBmysql> DBmysqlList;

    public mysqlAdapter(mysql context, int layout, List<DBmysql> DBmysqlList) {
        this.context = context;
        this.layout = layout;
        this.DBmysqlList = DBmysqlList;
    }

    @Override
    public int getCount() {
        return DBmysqlList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }
    private class ViewHolder{
        TextView txtname,txtlink;
        ImageView Delet,Edit;
    }

    @Override
    public View getView(int i, View view, ViewGroup parent) {
        ViewHolder holder;
        if (view == null){
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout,null);
            holder.txtname=(TextView) view.findViewById(R.id.tv_ten);
            holder.txtlink=(TextView) view.findViewById(R.id.tv_link);
            holder.Delet = (ImageView) view.findViewById(R.id.delete);
            holder.Edit = (ImageView) view.findViewById(R.id.edit);
            view.setTag(holder);
        }else {
            holder =(ViewHolder) view.getTag();
        }
        DBmysql dBmysql =  DBmysqlList.get(i);
        holder.txtname.setText(dBmysql.getName());
        holder.txtlink.setText(dBmysql.getLink());
        holder.Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,updaterss.class);
                intent.putExtra("datarss",dBmysql);
                context.startActivity(intent);
            }
        });
        holder.Delet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                xacnhanxoa(dBmysql.getName(), dBmysql.getId());
            }
        });
        return view;
    }
    private void xacnhanxoa(String name , int id){
        AlertDialog.Builder dialogXoa = new AlertDialog.Builder(context);
        dialogXoa.setMessage("bạn muốn xóa báo " + name + " không");
        dialogXoa.setPositiveButton("có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                context.deleterss(id);
            }
        });
        dialogXoa.setNegativeButton("không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dialogXoa.show();

    }
}
