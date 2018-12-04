package com.fei.zzqghelper;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

/**
 * @author fei
 * @date on 2018/12/4 0004
 * @describe TODO :
 **/
public class MoveRelativeLayout extends RelativeLayout {



    public MoveRelativeLayout(Context context) {
        super(context);
    }

    public MoveRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MoveRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private float startX = 0 ,startY = 0; //拖动时单点按下时的坐标
    private int BaseX = 0, BaseY = 0;  //完成一次拖动后需要保存的坐标
    private int x = 0, y = 0;  //  实际拖动时的X,Y 轴的增量

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.i("fei","进入了");
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX = event.getX();
                startY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                x = (int) (startX - event.getX()) + BaseX;
                y = (int) (startY - event.getY()) + BaseY;
                setScrollX(x);
                setScrollY(y);
                break;
            case MotionEvent.ACTION_UP:
                BaseX = x;
                BaseY = y;
                break;
        }

        return super.onTouchEvent(event);
    }
}
