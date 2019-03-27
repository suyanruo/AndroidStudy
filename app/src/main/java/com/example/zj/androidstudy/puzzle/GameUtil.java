package com.example.zj.androidstudy.puzzle;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;

public class GameUtil {
    public static List<PuzzleItem> mPuzzleItems = new ArrayList<>();
    public static PuzzleItem mBlankItem;

    public static void generatePuzzle(int type) {
        int index;
        List<Integer> dataList = new ArrayList<>();
        // 随机打乱顺序
        for (int i = 0; i < mPuzzleItems.size(); i++) {
            index = (int) (Math.random() * Math.pow(type, 2));
            swapItems(mPuzzleItems.get(index), mBlankItem);
        }
        for (int i = 0; i < mPuzzleItems.size(); i++) {
            dataList.add(mPuzzleItems.get(i).getBitmapId());
        }
        // 判断生成的矩阵是否有解
        if (!canSolve(type, dataList)) {
            generatePuzzle(type);
        }
    }

    public static void swapItems(PuzzleItem fromItem, PuzzleItem blankItem) {
        // 对两个item对bitmapId和bitmap进行交换
        int id = fromItem.getBitmapId();
        Bitmap bitmap = fromItem.getBitmap();
        fromItem.setBitmapId(blankItem.getBitmapId());
        fromItem.setBitmap(blankItem.getBitmap());
        blankItem.setBitmapId(id);
        blankItem.setBitmap(bitmap);
        // 设置新的blank
        mBlankItem = fromItem;
    }

    /**
     * 判断该数据是否有解
     * @param data
     * @return
     */
    public static boolean canSolve(int type, List<Integer> data) {
        // 获取空格Id
        int blankId = mBlankItem.getItemId();
        if (data.size() % 2 == 1) {
            return getInversions(data) % 2 == 0;
        } else {
            // 从下往上数，空格位于奇数行
            if (((blankId - 1) / type) % 2 == 1) {
                return getInversions(data) % 2 == 0;
            } else {
                return getInversions(data) % 2 == 1;
            }
        }
    }

    /**
     * 获取倒置和算法
     * @param data
     * @return
     */
    public static int getInversions(List<Integer> data) {
        int inversions = 0;
        int inversionCount = 0;
        int temp = 0;
        for (int i = 0; i < data.size(); i++) {
            for (int j = i + 1; j < data.size(); j++) {
                temp = data.get(i);
                if (data.get(j) != 0 && temp > data.get(j)) {
                    inversionCount++;
                }
            }
            inversions += inversionCount;
            inversionCount = 0;
        }
        return inversions;
    }

    /**
     * 判断item是否能移动
     * @param position
     * @return
     */
    public static boolean isMoveable(int type, int position) {
        int blankId = mBlankItem.getItemId() - 1;
        // 不同行itemId相差type，同行相差1时为可移动
        if (Math.abs(blankId - position) == type) {
            return true;
        }
        if ((blankId / type == position / type) && Math.abs(blankId - position) == 1) {
            return true;
        }
        return false;
    }

    public static boolean isSuccess(int type) {
        for (PuzzleItem item : mPuzzleItems) {
            if (item.getBitmapId() != 0 && item.getBitmapId() == item.getItemId()) {
                continue;
            } else if (item.getBitmapId() == 0 && item.getItemId() == Math.pow(type, 2)) {
                continue;
            } else return false;
        }
        return true;
    }
}
