package com.example.elderlycaresystem;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ElderlyAdapter extends RecyclerView.Adapter<ElderlyAdapter.ViewHolder>{
    ArrayList<Elderly> items = new ArrayList<Elderly>();

    @NonNull
    @Override
    // ViewHolder 객체가 만들어질 때 자동 호출
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        // 인플레이션을 통해 View 객체 만들기
        View elderlyView = inflater.inflate(R.layout.elderly_item,parent,false);
        // 뷰홀더 객체를 생성하면서 View 객체를 전달하고 그 ViewHolder 객체를 return!!
        return new ViewHolder(elderlyView);
    }

    // ViewHolder 객체가 재사용될 때 자동 호출
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        Elderly item = items.get(position);
        holder.setItem(item);
    }
    // Adapter에서 관리하는 Item의 개수를 반환
    @Override
    public int getItemCount() {
        return items.size();
    }


    // TODO 외부에서 이 Adapter를 사용할 때 Elderly객체를 넣거나 가져갈 수 있도록 addItem(),setItems(),getItem(),setItem() 메서드 정의!!
    // addItem() : 들어온 item을 ArrayList<Elderly>에 추가!!
    public void addItem(Elderly item){
        items.add(item);
    }
    // setItems{} : 들어온 ArrayList<Elderly> items을 해당 클래스의 items로 초기화!!
    public void setItems(ArrayList<Elderly> items){
        this.items = items;
    }
    // getItems : Adapter에 있는 i(position)번째 아이템 return!!
    public Elderly getItem(int position){
        return items.get((position));
    }
    // setItem : i번째 item을 전달받은 item으로 return!!
    public Elderly setItem(int position, Elderly item){
        return items.set(position,item);
    }


    // TODO Item을 담아둘 ViewHolder Class 정의!!
    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        Button tell;
        TextView stat;
        Button info;

        //  ViewHolder 생성자로 전달되는 View 객체 참조하기
        public ViewHolder(View ElderView){
            super(ElderView);

            // View 객체에 들어있는 button,text 참조하기
            name = ElderView.findViewById(R.id.nameText);
            stat = ElderView.findViewById(R.id.statText);
            tell = ElderView.findViewById(R.id.tellButton);
            info = ElderView.findViewById(R.id.infoButton);
        }

        public void setItem(final Elderly item){
            name.setText(item.getElderlyName());
            stat.setText(item.getElderlyAddr());


            // TODO : 전화 & Info버튼 이벤트 등록
            tell.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String tellNumber = ("tel:" + item.getElderlyTell().toString());
                    Intent intent = new Intent(Intent.ACTION_CALL,Uri.parse(tellNumber));
                    Toast.makeText(v.getContext(),"tel:"+item.getElderlyTell(),Toast.LENGTH_LONG).show();
                    v.getContext().startActivity(intent);
                }
            });

            // TODO : View객체 누르면 상세정보 엑티비티로 이동하기
            info.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext() , InfoActivity.class);
                    intent.putExtra("KEY",item.getElderlyId());
                    v.getContext().startActivity(intent);
                }
            });


        }

    }

}

/*
리스트로 보일 각각의 아이템(Elderly)들은 뷰로 만들어지며
각각의 아이템을 위한 View는 ViewHolder에 담아두게 된다.
이러한 ViewHolder 역할을 하는 Class를 Adapter안에 넣어둔다!

ViewHolder의 생성자에는 View 객체가 전달된다.
이때 전달받은 이 객체를 부모 클래스의 변수에 담아두게 되는데
생성자 안에서 super()메서드를 호출하면 된다!

전달받은 View객체의 이미지나 텍스트뷰를 findViewById() 메서드로 찾아 변수에 할당하면
setItem() 메서드로 참조할 수 있다.
setItem() 메서드는 ViewHolder에 들어있는 View 객체의 데이터를 다른 것으로 보이도록 하는 역할!!
*/