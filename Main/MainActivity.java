package com.example.akiya.deliverymyfeelings.Main;

import android.app.Activity;
import android.graphics.Point;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.akiya.deliverymyfeelings.Main.OriginalView;


public class MainActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        OriginalView ov =new OriginalView(this);           //OriginalViewの作成
        setContentView(ov);                                        //ovをセット
        ov.start();
        Point p = new Point();
        getWindowManager().getDefaultDisplay().getSize(p);        //ディスプレイ処理
        ov.SetDisplyaSize((float)p.x,(float)p.y);                //解像度対応

    }
}
