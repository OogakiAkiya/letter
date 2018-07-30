package com.example.akiya.deliverymyfeelings.Library;

import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Build;

import com.example.akiya.deliverymyfeelings.Main.OriginalView;

import java.io.IOException;

import static java.security.AccessController.getContext;

/**
 * Created by akiya on 2018/06/21.
 */

public class Sound {
    //コンストラクタ
    public  Sound(OriginalView _ov) {
        owner = _ov;
        Init();
    }

    //BGMのループ再生
    public  void PlayBGM(String _pass) {
        if (mp != null) {
            mp.stop();                                                                              //既に再生されているものを止める
            mp = null;
        }
        mp = new MediaPlayer();                                                                     //メディアプレイヤーを管理
        AssetManager am = owner.GetContext().getResources().getAssets();                            //アセットマネージャー管理
        try {
            AssetFileDescriptor fd = am.openFd(_pass);                                              //assetsフォルダ以下のパス
            mp.setDataSource(fd.getFileDescriptor(), fd.getStartOffset(), fd.getLength());
            mp.setLooping(true);                                                                    //ループの設定
            mp.prepare();                                                                           //再生可能になるまでまつ
            mp.start();                                                                             //再生
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //BGMストップ
    public void StopBGM(){
        mp.stop();
    }

    //SEのロード処理
    public int LoadSE(String _pass){
        AssetManager am=owner.GetContext().getResources().getAssets();                              //アセットマネージャー管理
        int id=-1;
        try{
            AssetFileDescriptor fd=am.openFd(_pass);
            id=soundPool.load(fd,1);
        }catch (IOException e){
            e.printStackTrace();
        }
        return id;                                                                                  //読み込んだidを返す
    }

    //SEの再生
    public void PlaySE(int _id){
        soundPool.play(_id,1.0F,1.0F,1,0,1.0F);           //再生
        //soundPool.play(_id,左の音量,右の音量,優先度,ループ再生(無し:0,有り:1),音の速度);
    }

    //----------------------------------------------------------------------------------------------
    //コンストラクタ
    //----------------------------------------------------------------------------------------------
    //初期化
    private void Init(){
        final int SOUND_POOL_MAX =6;
        if(Build.VERSION.SDK_INT< Build.VERSION_CODES.LOLLIPOP){
            soundPool=new SoundPool(SOUND_POOL_MAX, AudioManager.STREAM_MUSIC,0);
        }else{
            AudioAttributes attr=new AudioAttributes.Builder()
                                     .setUsage(AudioAttributes.USAGE_MEDIA)
                                     .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC).build();
            soundPool=new SoundPool.Builder().setAudioAttributes(attr).setMaxStreams(SOUND_POOL_MAX).build();
        }
    }

    //----------------------------------------------------------------------------------------------
    //変数
    //----------------------------------------------------------------------------------------------
    private OriginalView owner =null;
    private MediaPlayer mp=null;
    private SoundPool soundPool=null;           //soundを貯めておくもの( )


}


