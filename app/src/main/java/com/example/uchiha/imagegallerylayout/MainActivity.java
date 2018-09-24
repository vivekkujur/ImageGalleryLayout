package com.example.uchiha.imagegallerylayout;

import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.ArrayList;
import java.util.List;

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
    private Button priceBtn;
    private List<String> images, product_size;
    private int size;
    private  MaterialSpinner spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getpagedata();
        slidepager=findViewById(R.id.SlideViewPager);
        dotslayout=findViewById(R.id.DotsLayout);
        name=findViewById(R.id.textView);
        priceBtn=findViewById(R.id.priceBotton);
        images=new ArrayList<>();
        product_size=new ArrayList<>();
        spinner =findViewById(R.id.spinner);


    }

    private void getpagedata() {
        apiInterface= APIClient.getClient().create(APIInterface.class);
        listcall= apiInterface.getlist();
        listcall.enqueue(new Callback<MultipleResources>() {
            @Override
            public void onResponse(Call<MultipleResources> call, Response<MultipleResources> response) {

                size=response.body().getImages().size();
                name.setText(response.body().getName());
                priceBtn.setText("Buy @"+ response.body().getPrice().toString());

                for(int i=0;i < response.body().getImages().size();i++){
                    images.add(response.body().getImages().get(i));
                }

                for(int i=0; i <response.body().getSizes().size();i++){

                    product_size.add(response.body().getSizes().get(i));
                }
                spinner.setTextColor(getResources().getColor(R.color.secondary_text));
                spinner.setItems(product_size);
                spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                        Snackbar.make(view, " " + item, Snackbar.LENGTH_SHORT).show();
                    }
                });

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
