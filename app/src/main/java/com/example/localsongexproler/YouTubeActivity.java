package com.example.localsongexproler;

import android.os.Bundle;
//import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class YouTubeActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {
    private static YouTubePlayer player = null;

    private static final String DEVELOPER_KEY = new DeveloperKey().DEVELOPER_KEY;
    private static final int RECOVERY_DIALOG_REQUEST = 1;
    private static String watch_id, prev_id;

//    private static String sample_watch_id = "CZB-CaxLjyw";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setCountView(R.layout.activity_main);

        // YouTubePlayerViewにDeveloperキーを設定

        //youTubeView.initialize(DEVELOPER_KEY, this);
    }


    public YouTubeActivity(YouTubePlayerView youTubePlayerView, String id) {
        youTubePlayerView.initialize(DEVELOPER_KEY, this);
        watch_id = id;
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult errorReason) {
        // 初期化に失敗したときの処理
        if (errorReason.isUserRecoverableError()) {
            // エラー回避が可能な場合のエラーダイアログを表示
            errorReason.getErrorDialog(this, RECOVERY_DIALOG_REQUEST).show();
        } else {
            // エラー回避が不可能な場合、Toastのみを表示
            //String errorMessage = String.format(getString(R.string.text_error_player), errorReason.toString());
            //Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
        }
    }


    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean wasRestored) {
        //Tell the player you want to control the fullscreen change
        this.player = player;
        player.setFullscreenControlFlags(YouTubePlayer.FULLSCREEN_FLAG_CUSTOM_LAYOUT);
        YouTubePlayer.PlayerStyle style = YouTubePlayer.PlayerStyle.MINIMAL;    // 全画面表示ボタンを削除
        player.setPlayerStyle(style);                                           // https://codeday.me/jp/qa/20190314/402078.html

        //Tell the player how to control the change
        //player.setOnFullscreenListener(new YouTubePlayer.OnFullscreenListener(){
        //    @Override
        //    public void onFullscreen(boolean arg0) {
            // do full screen stuff here, or don't. I started a YouTubeStandalonePlayer
            // to go to full screen
        //    }});

    // YouTubeの動画IDを設定
        if (!wasRestored) {
            player.cueVideo(watch_id);
            //player.loadVideo(watch_id);
        }
    }

/*
    public void setVideoId(final String videoId) {
        if (videoId != null && !videoId.equals(this.videoId)) {
            this.videoId = videoId;
            if (youtubePlayer != null) {
                try {
                    youtubePlayer.loadVideo(videoId);
                } catch (IllegalStateException e) {
                    initialize(API_KEY, this);
                }
            }
        }
    }

    @Override
    public void onDestroy() {
        if (youtubePlayer != null) {
            youtubePlayer.release();
        }
        super.onDestroy();
    }
*/
/*
    public void checkVideoID(final String videoID){
        if(videoID != null && !videoID.equals(watch_id)) {
            if(player != null) {
                try {
                    onDestroy(player);
                } catch (IllegalStateException e) {

                }
            }
        }
    }

//    @Override
    public void onDestroy(YouTubePlayer youtubePlayer) {
        if (youtubePlayer != null) {
            youtubePlayer.release();
        }
        super.onDestroy();
    }
*/

    public static void setNewPlayer(String watch_id) {
        player.setFullscreenControlFlags(YouTubePlayer.FULLSCREEN_FLAG_CUSTOM_LAYOUT);
        YouTubePlayer.PlayerStyle style = YouTubePlayer.PlayerStyle.MINIMAL;    // 全画面表示ボタンを削除
        player.setPlayerStyle(style);                                           // https://codeday.me/jp/qa/20190314/402078.html

        // YouTubeの動画IDを設定
        player.cueVideo(watch_id);
    }

}