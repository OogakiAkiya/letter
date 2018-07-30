package com.example.akiya.deliverymyfeelings.Library;
import android.content.Context;
import android.util.Log;

import com.example.akiya.deliverymyfeelings.Main.OriginalView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;


public class FileOperation {
    //コンストラクタ
    public FileOperation(OriginalView _ov){
        owner=_ov;
    }

    //ファイルに値の書き込みを行う
    public void SaveFile(String _filepass){
        try {
            FileOutputStream ofs = owner.GetContext().openFileOutput(_filepass, Context.MODE_PRIVATE);         //ファイルを指定して開く
            ofs.write("文字列の書き込み\n".getBytes());                                                        //ファイル書き込み
            ofs.write("データには改行も含む\n".getBytes());                                                    //ファイル書き込み
            ofs.write(("" + 100).getBytes());                                                                   //数値を文字列に変換
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    //ファイルのロードを行う
    public void LoadFile(String _filepass){
        try{
            FileInputStream ifs=owner.GetContext().openFileInput(_filepass);                                    //ファイルを指定して開く
            BufferedReader br =new BufferedReader(new InputStreamReader(ifs,"UTF-8"));            //文字コードの設定
            String str=null;
            while((str=br.readLine())!=null){
                Log.d("fileIO",str);
                try{
                    int a=Integer.parseInt(str);
                    Log.d("fileIO",""+a);
                }catch (NumberFormatException e){
                }
            }

        }catch(IOException e){
            e.printStackTrace();
        }
    }

    /*
    マップデータなど最初からあるデータはassetsへ
    InputStream is=owner.GetContext().getAssets.open("atest.txt");をLoadFileの34行目に書き込む
    */

    //----------------------------------------------------------------------------------------------
    //変数
    //----------------------------------------------------------------------------------------------
    private OriginalView owner =null;                                                             //OriginalViewを格納

}
