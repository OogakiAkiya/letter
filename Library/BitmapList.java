package com.example.akiya.deliverymyfeelings.Library;

import com.example.akiya.deliverymyfeelings.Main.OriginalView;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class BitmapList {
    //コンストラクタ
    public BitmapList(OriginalView _ov){
        owner=_ov;
        BitmapList= new HashMap<String, Bitmap>();                               //連想配列作成
    }

    //BitmapがList内にあればそのままBitmapを返しなければロードして返す
    public Bitmap SearchBitmap(String _pass){
        for (String key : BitmapList.keySet()) {                                 //リストの数だけ繰り返す
            if(key==_pass){
                return BitmapList.get(key);                                      //見つかったらそのまま値を返す
            }
        }
        //以下_passをロードしたBitmapが見つからなかった場合の処理
        BitmapList.put(_pass,LoadBitmap(_pass));                                 //Bitmapをロード
        return BitmapList.get(_pass);                                           //Bitmapを返す
    }

    //リストの中の処理を一括で削除する
    public void Clear(){
        Iterator<String> mapKeyIter = BitmapList.keySet().iterator();          //イテレータの作成
        while(mapKeyIter.hasNext()) {                                          //リストの数だけ回す
            String key = mapKeyIter.next();                                    //次へ
            if(key.equals(key)) {
                mapKeyIter.remove();                                           //リストから削除
            }
        }
    }

    //Bitmapをロードする
    private Bitmap LoadBitmap(String _pass){
        AssetManager am=owner.GetContext().getResources().getAssets();        //AssetManagerはassetsファイルの管理
        try{
            Bitmap bmp;                                                        //ローカル変数のbitマップはこの関数から抜けるとloadした画像を解放するが返り値でbmpを渡すので実体化しない
            InputStream is=am.open(_pass);
            bmp= BitmapFactory.decodeStream(is);                               //どんな形式のファイルでもbitマップファイルに変換してくれる
            return bmp;
        } catch (Exception e) {
            return null;
        }
    }

    //----------------------------------------------------------------------------------------------
    //変数
    //----------------------------------------------------------------------------------------------
    private Map<String, Bitmap> BitmapList=null;                                //連想配列
    private OriginalView owner =null;                                           //OriginalViewを格納


}
