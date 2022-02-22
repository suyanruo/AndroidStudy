package com.example.zj.androidstudy.music;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import com.example.zj.androidstudy.R;
import com.example.zj.androidstudy.base.BaseActivity;

import java.util.Timer;
import java.util.TimerTask;

public class MusicActivity extends BaseActivity {
    private static final String TAG = "MusicActivity";

    private ImageView mIvRotation;
    private MediaPlayer mBackgroundMediaPlayer;
    private float mLeftVolume;
    private float mRightVolume;
    private boolean mIsPaused;
    private boolean mIsPausedManually;
    private String mCurrentPath;
    private int mPosition = 0;
    private ObjectAnimator mRotationAnimator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);
        initView();
        initData();
    }

    private void initView() {
        mIvRotation = findViewById(R.id.iv_rotation);
        mIvRotation.setOnClickListener(v -> {
            if (mIsPaused) {
                playBackgroundMusic();
            } else {
                mIsPausedManually = true;
                pauseBackgroundMusic();
            }
        });

        mRotationAnimator = ObjectAnimator.ofFloat(mIvRotation, "rotation", 0f, 360f);
        mRotationAnimator.setDuration(4000);
        mRotationAnimator.setInterpolator(new LinearInterpolator());
        mRotationAnimator.setRepeatCount(ObjectAnimator.INFINITE);
        mRotationAnimator.setRepeatMode(ObjectAnimator.RESTART);
    }

    // 初始化一些数据
    private void initData() {
        mBackgroundMediaPlayer = null;
        mLeftVolume = 0.5f;
        mRightVolume = 0.5f;
        mIsPaused = false;
        mIsPausedManually = false;
        mCurrentPath = null;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!mIsPausedManually) {
            mIvRotation.postDelayed(this::playBackgroundMusic, 400);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        pauseBackgroundMusic();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        end();
    }

    private void playBackgroundMusic() {
        if (mBackgroundMediaPlayer == null) {
            mBackgroundMediaPlayer = createMediaPlayerFromRaw();
        }
        if (mBackgroundMediaPlayer != null) {
            mBackgroundMediaPlayer.stop();
            mBackgroundMediaPlayer.setLooping(true);
            try {
                mBackgroundMediaPlayer.prepare();
                mBackgroundMediaPlayer.seekTo(mPosition);
                mBackgroundMediaPlayer.start();
                mIsPaused = false;
                mIsPausedManually = false;
            } catch (Exception e) {
                Log.e(TAG, "playBackgroundMusic: error state");
            }
            if (!mRotationAnimator.isRunning()) {
                mRotationAnimator.start();
            } else {
                mRotationAnimator.resume();
            }
        }
    }

    /**
     * 根据path路径播放背景音乐
     *
     * @param path   :assets中的音频路径
     * @param isLoop :是否循环播放
     */
    public void playBackgroundMusic(String path, boolean isLoop) {
        if (mCurrentPath == null) {
            // 这是第一次播放背景音乐--- it is the first time to play background music
            // 或者是执行end()方法后，重新被叫---or end() was called
            mBackgroundMediaPlayer = createMediaPlayerFromAssets(path);
            mCurrentPath = path;
        } else {
            if (!mCurrentPath.equals(path)) {
                // 播放一个新的背景音乐--- play new background music
                // 释放旧的资源并生成一个新的----release old resource and create a new one
                if (mBackgroundMediaPlayer != null) {
                    mBackgroundMediaPlayer.release();
                }
                mBackgroundMediaPlayer = createMediaPlayerFromAssets(path);
                // 记录这个路径---record the path
                mCurrentPath = path;
            }
        }
        if (mBackgroundMediaPlayer == null) {
            Log.e(TAG, "playBackgroundMusic: background media player is null");
        } else {
            // 如果音乐正在播放或已近中断，停止它---if the music is playing or paused, stop it
            mBackgroundMediaPlayer.stop();
            mBackgroundMediaPlayer.setLooping(isLoop);
            try {
                mBackgroundMediaPlayer.prepare();
                mBackgroundMediaPlayer.seekTo(0);
                mBackgroundMediaPlayer.start();
                mIsPaused = false;
            } catch (Exception e) {
                Log.e(TAG, "playBackgroundMusic: error state");
            }
        }
    }


    /**
     * 停止播放背景音乐
     */
    public void stopBackgroundMusic() {
        if (mBackgroundMediaPlayer != null) {
            mPosition = mBackgroundMediaPlayer.getCurrentPosition();
            mBackgroundMediaPlayer.stop();
            // should set the state, if not , the following sequence will be
            // error
            // play -  pause -  stop -  resume
            mIsPaused = false;
        }
    }

    /**
     * 暂停播放背景音乐
     */
    public void pauseBackgroundMusic() {
        if (mBackgroundMediaPlayer != null
                && mBackgroundMediaPlayer.isPlaying()) {
            mPosition = mBackgroundMediaPlayer.getCurrentPosition();
            mBackgroundMediaPlayer.pause();
            mIsPaused = true;
            if (!mRotationAnimator.isPaused()) {
                mRotationAnimator.pause();
            }
        }
    }

    /**
     * 继续播放背景音乐
     */
    public void resumeBackgroundMusic() {
        if (mBackgroundMediaPlayer != null && this.mIsPaused) {
            mBackgroundMediaPlayer.start();
            this.mIsPaused = false;
        }
    }

    /**
     * 重新播放背景音乐
     */
    public void rewindBackgroundMusic() {
        if (mBackgroundMediaPlayer != null) {
            mBackgroundMediaPlayer.stop();
            try {
                mBackgroundMediaPlayer.prepare();
                mBackgroundMediaPlayer.seekTo(0);
                mBackgroundMediaPlayer.start();
                this.mIsPaused = false;
            } catch (Exception e) {
                Log.e(TAG, "rewindBackgroundMusic: error state");
            }
        }
    }

    /**
     * 判断背景音乐是否正在播放
     *
     * @return：返回的boolean值代表是否正在播放
     */
    public boolean isBackgroundMusicPlaying() {
        boolean ret = false;
        if (mBackgroundMediaPlayer == null) {
            ret = false;
        } else {
            ret = mBackgroundMediaPlayer.isPlaying();
        }
        return ret;
    }

    /**
     * 结束背景音乐，并释放资源
     */
    public void end() {
        if (mBackgroundMediaPlayer != null) {
            mBackgroundMediaPlayer.release();
        }
        // 重新“初始化数据”
        initData();
    }

    /**
     * 得到背景音乐的“音量”
     *
     * @return
     */
    public float getBackgroundVolume() {
        if (this.mBackgroundMediaPlayer != null) {
            return (this.mLeftVolume + this.mRightVolume) / 2;
        } else {
            return 0.0f;
        }
    }

    /**
     * 设置背景音乐的音量
     *
     * @param volume ：设置播放的音量，float类型
     */
    public void setBackgroundVolume(float volume) {
        this.mLeftVolume = this.mRightVolume = volume;
        if (this.mBackgroundMediaPlayer != null) {
            this.mBackgroundMediaPlayer.setVolume(this.mLeftVolume,
                    this.mRightVolume);
        }
    }

    /**
     * create mediaplayer for music
     *
     * @param path
     *      the path relative to assets
     * @return
     */
    private MediaPlayer createMediaPlayerFromAssets(String path) {
        MediaPlayer mediaPlayer = null;
        try {
            AssetFileDescriptor assetFileDescriptor = getAssets()
                    .openFd(path);
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(assetFileDescriptor.getFileDescriptor(),
                    assetFileDescriptor.getStartOffset(),
                    assetFileDescriptor.getLength());
            mediaPlayer.prepare();
            mediaPlayer.setVolume(mLeftVolume, mRightVolume);
        } catch (Exception e) {
            mediaPlayer = null;
            Log.e(TAG, "error: " + e.getMessage(), e);
        }
        return mediaPlayer;
    }

    /**
     * 打开raw目录下的音乐mp3文件
     */
    private MediaPlayer createMediaPlayerFromRaw() {
        return MediaPlayer.create(this, R.raw.shengsheng);
    }

    private static final int PERIOD = 10 * 1000;
    private static final int DELAY = 1000;
    private Timer mTimer;
    private TimerTask mTimerTask;

    private void startLoop() {
        mTimer = new Timer();
        mTimerTask = new TimerTask() {
            @Override
            public void run() {

            }
        };
        mTimer.scheduleAtFixedRate(mTimerTask, DELAY, PERIOD);
    }

    private void stopLoop() {
        if (mTimer != null) {
            mTimer.cancel();
        }
    }
}