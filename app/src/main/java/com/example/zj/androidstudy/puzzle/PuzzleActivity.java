package com.example.zj.androidstudy.puzzle;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.zj.androidstudy.R;
import com.example.zj.androidstudy.tool.ImageUtil;
import com.example.zj.androidstudy.tool.ScreenUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PuzzleActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String CACHE_PATH = Environment.getExternalStorageDirectory().getPath() + "/AndroidStudy/Cache";
    public static String PICTURE_PATH = CACHE_PATH + "/picCache.png";
    public static String CAMERA_PIC_PATH = CACHE_PATH + "/camera.png";
    private static final int RESULT_IMAGE = 100;
    private static final int RESULT_CAMERA = 200;
    private static final String IMAGE_TYPE = "image/*";
    private GridView mPicListGridView;
    private GridViewAdapter mGridViewAdapter;
    private GridView mItemGridView;
    private GridItemsAdapter mGridItemsAdapter;
    private LinearLayout mBtnLinearLayout;
    private Button mSourceButton;
    private Button mResetButton;
    private ImageView mSourceView;
    private PopupWindow mPopupWindow;
    private Toolbar mToolbar;
    private View mPopupView;

    private int mType = 4;
    private Bitmap mLastBitmap;
    private Bitmap mSelectBitmap;
    // 主页图片资源ID
    private int[] mResPicId;
    // 本地图册、相机选择
    private String[] mCustomItems = new String[]{"应用内", "本地图册", "相机拍照"};
    private String[] mTypeItems = new String[]{"2 X 2", "3 X 3", "4 X 4"};
    private List<Bitmap> mPicList = new ArrayList<>();
    private List<Bitmap> mItemList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puzzle);

        mPicListGridView = findViewById(R.id.gv_puzzle_main_pic_list);
        mItemGridView = findViewById(R.id.gv_puzzle_main_detail);
        mToolbar = findViewById(R.id.toolbar_puzzle);
        mBtnLinearLayout = findViewById(R.id.ll_puzzle_main_btns);
        mSourceButton = findViewById(R.id.btn_puzzle_main_img);
        mResetButton = findViewById(R.id.btn_puzzle_main_restart);

        setSupportActionBar(mToolbar);

        // 初始化Bitmap数据
        mResPicId = new int[]{
                R.drawable.pic1, R.drawable.pic2, R.drawable.pic3,
                R.drawable.pic4, R.drawable.pic5, R.drawable.pic6,
                R.drawable.pic7, R.drawable.pic8, R.drawable.pic9,
                R.drawable.pic10, R.drawable.pic11, R.drawable.pic12,
                R.drawable.pic13, R.drawable.pic14,
                R.drawable.pic15, R.mipmap.ic_blank};
        for (int i = 0; i < 16; i++) {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), mResPicId[i]);
            mPicList.add(bitmap);
        }
        mGridViewAdapter = new GridViewAdapter(this, mPicList);
        mPicListGridView.setAdapter(mGridViewAdapter);
        mPicListGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position < mResPicId.length - 1) {
                    mSource = position;
                    mSelectBitmap = handlerImageSize(BitmapFactory.decodeResource(getResources(), mResPicId[position]));
                    refreshPuzzle();
                }
            }
        });

        mGridItemsAdapter = new GridItemsAdapter(this, mItemList);
        mItemGridView.setAdapter(mGridItemsAdapter);
        mItemGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 判断是否可以移动
                if (GameUtil.isMoveable(mType, position)) {
                    // 交换点击Item与空格的位置
                    GameUtil.swapItems(GameUtil.mPuzzleItems.get(position), GameUtil.mBlankItem);
                    // 重新获取图片
                    createPuzzle();
                    if (GameUtil.isSuccess(mType)) {
                        mItemList.remove(mItemList.size() - 1);
                        mItemList.add(mLastBitmap);
                        mGridItemsAdapter.notifyDataSetChanged();
                        mItemGridView.setEnabled(false);
                        Toast.makeText(PuzzleActivity.this, "拼图成功", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        mSourceButton.setOnClickListener(this);
        mResetButton.setOnClickListener(this);
    }

    private boolean mIsShowImg = false;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_puzzle_main_img:
                Animation animShow = AnimationUtils.loadAnimation(PuzzleActivity.this, R.anim.image_show_anim);
                Animation animHide = AnimationUtils.loadAnimation(
                        PuzzleActivity.this, R.anim.image_hide_anim);
                if (mIsShowImg) {
                    mSourceView.startAnimation(animHide);
                    mSourceView.setVisibility(View.GONE);
                } else {
                    mSourceView.startAnimation(animShow);
                    mSourceView.setVisibility(View.VISIBLE);
                }
                mIsShowImg = !mIsShowImg;
                break;
            case R.id.btn_puzzle_main_restart:
//                if (mSource >= 16) {
//                    mSelectBitmap = BitmapFactory.decodeFile(PICTURE_PATH);
//                } else {
//                    mSelectBitmap = BitmapFactory.decodeResource(getResources(), mResPicId[mSource]);
//                }
                refreshPuzzle();
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_puzzle, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.actions_choose_image:
                showPicDialog();
                break;
            case R.id.actions_choose_type:
                showTypeDialog();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void addSourceView() {
        RelativeLayout relativeLayout = findViewById(R.id.rl_puzzle_main_layout);
        if (mSourceView == null) {
            mSourceView = new ImageView(this);
            relativeLayout.addView(mSourceView);
        }
        mSourceView.setImageBitmap(mSelectBitmap);
        RelativeLayout.LayoutParams sourceParams = new RelativeLayout.LayoutParams(
                (int) (mSelectBitmap.getWidth() * 0.9), (int) (mSelectBitmap.getHeight() * 0.9));
        sourceParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        mSourceView.setLayoutParams(sourceParams);
        mSourceView.setVisibility(View.GONE);
    }

    private int mSource = 0; // 0-15表示选择应用提供的图片，16为手机本地图片，17为拍照图片

    // 显示选择系统图库 相机对话框
    private void showPicDialog() {
        AlertDialog.Builder builder  = new AlertDialog.Builder(PuzzleActivity.this);
        builder.setTitle("图片来源:");
        builder.setItems(mCustomItems, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    mPicListGridView.setVisibility(View.VISIBLE);
                    mItemGridView.setVisibility(View.GONE);
                    mBtnLinearLayout.setVisibility(View.GONE);
                } else if (which == 1) {
                    mSource = 16;
                    getImageFromLocal();
                } else {
                    mSource = 17;
                    getImageFromCamera();
                }
            }
        });
        builder.create().show();
    }

    // 显示选择游戏类型对话框
    private void showTypeDialog() {
        AlertDialog.Builder builder  = new AlertDialog.Builder(PuzzleActivity.this);
        builder.setTitle("选择难度:");
        builder.setItems(mTypeItems, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    mType = 2;
                } else if (which == 1) {
                    mType = 3;
                } else {
                    mType = 4;
                }
//                if (mSource >= 16) {
//                    mSelectBitmap = handlerImageSize(BitmapFactory.decodeFile(PICTURE_PATH));
//                } else {
//                    mSelectBitmap = handlerImageSize(BitmapFactory.decodeResource(getResources(), mResPicId[mSource]));
//                }
                refreshPuzzle();
            }
        });
        builder.create().show();
    }

    private void getImageFromLocal() {
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, IMAGE_TYPE);
        startActivityForResult(intent, RESULT_IMAGE);
    }

    private void getImageFromCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Uri photoUri = Uri.fromFile(new File(CAMERA_PIC_PATH));
        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
        startActivityForResult(intent, RESULT_CAMERA);
    }

    private String mImagePath;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == RESULT_IMAGE && data != null) {
                // 相册
                Cursor cursor = getContentResolver().query(data.getData(), null, null, null, null);
                cursor.moveToFirst();
                mImagePath = cursor.getString(cursor.getColumnIndex("_data"));
                cursor.close();
            } else if (requestCode == RESULT_CAMERA){
                // 相机
                mImagePath = CAMERA_PIC_PATH;
            }
            if (!TextUtils.isEmpty(mImagePath)) {
                File file = new File(CACHE_PATH);
                if (!file.exists()) {
                    file.mkdirs();
                }
                ImageUtil.doCompressBySize(mImagePath, PICTURE_PATH);
                mSelectBitmap = handlerImageSize(BitmapFactory.decodeFile(PICTURE_PATH));

                refreshPuzzle();
            }
        }
    }

    /**
     * 对图片处理 自适应大小
     *
     * @param bitmap bitmap
     */
    private Bitmap handlerImageSize(Bitmap bitmap) {
        // 将图片放大到固定尺寸
        int screenWidth = ScreenUtil.getScreenSize(this).widthPixels;
        int screenHeight = ScreenUtil.getScreenSize(this).heightPixels;
        return ImageUtil.resizeBitmap(bitmap, screenWidth * 0.8f, screenHeight * 0.6f);
    }

    private void refreshPuzzle() {
        // 添加原图视图
        addSourceView();
        // 设置为N*N显示
        mItemGridView.setNumColumns(mType);
        // 水平居中
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(mSelectBitmap.getWidth(), mSelectBitmap.getHeight());
        params.addRule(RelativeLayout.CENTER_IN_PARENT);
        // 其他格式属性
        params.addRule(
                RelativeLayout.BELOW,
                R.id.ll_puzzle_main_spinner);
        params.addRule(
                RelativeLayout.ABOVE,
                R.id.ll_puzzle_main_btns);
        mItemGridView.setLayoutParams(params);

        handleImageToItem(mSelectBitmap);
        generatePuzzle();

        mPicListGridView.setVisibility(View.GONE);
        mItemGridView.setVisibility(View.VISIBLE);
        mBtnLinearLayout.setVisibility(View.VISIBLE);
    }

    private void handleImageToItem(Bitmap sourceBitmap) {
        int width = sourceBitmap.getWidth();
        int height = sourceBitmap.getHeight();
        List<Bitmap> bitmapList = ImageUtil.createBitmapItem(this, mType, sourceBitmap);
        if (bitmapList == null || bitmapList.size() == 0) return;
        PuzzleItem temItem;
        GameUtil.mPuzzleItems.clear();
        for (int i = 1; i <= mType; i++) {
            for (int j = 1; j <= mType; j++) {
                temItem = new PuzzleItem((i - 1) * mType + j, (i - 1) * mType + j, bitmapList.get((i - 1) * mType + j - 1));
                GameUtil.mPuzzleItems.add(temItem);
            }
        }
        // 保存最后一块图片在完成拼图时使用
        mLastBitmap = bitmapList.get(bitmapList.size() - 1);

        // 设置最后一块为空
        Bitmap blankBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.blank);
        blankBitmap= Bitmap.createBitmap(blankBitmap, 0, 0 , width / mType, height / mType);
        GameUtil.mPuzzleItems.remove(GameUtil.mPuzzleItems.size() - 1);
        GameUtil.mPuzzleItems.add(new PuzzleItem(mType * mType, 0, blankBitmap));
        GameUtil.mBlankItem = GameUtil.mPuzzleItems.get(GameUtil.mPuzzleItems.size() - 1);
    }

    private void generatePuzzle() {
        GameUtil.generatePuzzle(mType);
        createPuzzle();
    }

    private void createPuzzle() {
        mItemList.clear();
        for (PuzzleItem item : GameUtil.mPuzzleItems) {
            mItemList.add(item.getBitmap());
        }
        mGridItemsAdapter.notifyDataSetChanged();
    }

    private void showPopup(View view) {
        int density = (int) ScreenUtil.getDeviceDensity(this);
        // 显示Popup
        mPopupView = LayoutInflater.from(this).inflate(R.layout.layout_popup, null);
        mPopupWindow = new PopupWindow(mPopupView, 200 * density, 50 * density);
        mPopupWindow.setFocusable(true);
        mPopupWindow.setOutsideTouchable(true);
        // 设置背景为透明
        ColorDrawable drawable = new ColorDrawable(Color.TRANSPARENT);
        mPopupWindow.setBackgroundDrawable(drawable);
        // 获取位置
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        mPopupWindow.showAtLocation(view, Gravity.NO_GRAVITY, location[0] - 40 * density, location[1] + 30 * density);
    }
}
