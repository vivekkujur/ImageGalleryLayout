package com.example.uchiha.imagegallerylayout;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.text.Html;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.aakira.expandablelayout.ExpandableRelativeLayout;


import java.util.ArrayList;
import java.util.List;

import mehdi.sakout.fancybuttons.FancyButton;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private ViewPager slidepager;
    private LinearLayout dotslayout;
    private SliderAdapter sliderAdapter;
    private TextView[] mDots;
    private APIInterface apiInterface;
    private Call<MultipleResources> listcall;
    private TextView name;
    private ImageButton descriptionBtn;
    private List<String> images, product_size;
    private int size;
    private FancyButton button1,button2,button3;
    private TextView product_price;
     int collapse=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        setContentView(R.layout.activity_main);

        getpagedata();
        slidepager=findViewById(R.id.SlideViewPager);
        dotslayout=findViewById(R.id.DotsLayout);
        name=findViewById(R.id.nametextview);
        product_price=findViewById(R.id.buy_product_price);

        button1=findViewById(R.id.button1);
        button1.setIconResource(R.drawable.share);
        button1.setTextSize(12);

        button2=findViewById(R.id.button2);
        button2.setIconResource(R.drawable.gift);
        button2.setTextSize(12);

        button3=findViewById(R.id.button3);
        button3.setIconResource(R.drawable.collections);
        button3.setTextSize(12);

        images=new ArrayList<>();
        product_size=new ArrayList<>();

        descriptionBtn=findViewById(R.id.descriptionBtn);

        final ExpandableRelativeLayout expandableLayout
                = (ExpandableRelativeLayout) findViewById(R.id.expandableLayout);
        expandableLayout.collapse();

        descriptionBtn.setOnClickListener(new View.OnClickListener() {

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                if(collapse==1){
                    expandableLayout.expand();
                    descriptionBtn.setImageDrawable(getDrawable(R.drawable.collapse));
                    collapse =0;
                    }else {
                    expandableLayout.collapse();
                    descriptionBtn.setImageDrawable(getDrawable(R.drawable.expand));
                    collapse =1;

                }
            }
        });


    }

    private void getpagedata() {
        apiInterface= APIClient.getClient().create(APIInterface.class);
        listcall= apiInterface.getlist();
        listcall.enqueue(new Callback<MultipleResources>() {
            @Override
            public void onResponse(Call<MultipleResources> call, Response<MultipleResources> response) {

                size=response.body().getImages().size();
                name.setText(response.body().getName());
                product_price.setText(response.body().getPrice().toString());

                for(int i=0;i < response.body().getImages().size();i++){
                    assert response.body() != null;
                    images.add(response.body().getImages().get(i));
                }

                for(int i = 0; i <response.body().getSizes().size(); i++){

                    product_size.add(response.body().getSizes().get(i));
                }

                sliderAdapter=new SliderAdapter(getApplicationContext(),images);
                slidepager.setAdapter(sliderAdapter);
                adddots(0,size);
                slidepager.addOnPageChangeListener(viewListener);
            }

            @Override
            public void onFailure(Call<MultipleResources> call, Throwable t) {

            }
        });


    }

    public void adddots(int position,int size){

        mDots=new TextView[size];
        dotslayout.removeAllViews();

        for(int i=0;i<mDots.length;i++){

            mDots[i]=new TextView(this);
            mDots[i].setText(Html.fromHtml("&#8226"));
            mDots[i].setTextSize(20);
            mDots[i].setTextColor(getResources().getColor(R.color.divider));
            dotslayout.addView(mDots[i]);

        }

        if(position>0){
            mDots[position].setTextColor(getResources().getColor(R.color.colorAccent));
        }
    }

    ViewPager.OnPageChangeListener viewListener= new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
                adddots(position,size);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
}
