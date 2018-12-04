package com.fei.zzqghelper;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * @author fei
 * @date on 2018/12/4 0004
 * @describe TODO :
 **/
public class RatoImageView extends View {

    private Bitmap srcMap;
    private int width,hight;


    public RatoImageView(Context context) {
        super(context);
    }

    public RatoImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RatoImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int length = width<hight ? width : hight;
        RectF rectF = new RectF(0, 0, length, length);
        canvas.drawBitmap(srcMap, null, rectF, null);

//        Paint paint = new Paint();
//        paint.setColor(Color.RED);
//        paint.setStrokeWidth(2);
//        canvas.drawLine(width/2,0,width/2,hight,paint);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        hight = h;
    }

    public void setImageView(Bitmap srcMap){
        this.srcMap = srcMap;
        int[] location = new int[2];
        getLocationOnScreen(location);
        int x = location[0];
        int y = location[1];
        setRotationX(width/2);
        setRotationY(y+hight/2);
        invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int centerScreen = ScreenUtil.getScreenWidth()/2;
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN :
                break;
            case MotionEvent.ACTION_MOVE :
                break;
            case MotionEvent.ACTION_UP :
                break;
        }
        return super.onTouchEvent(event);
    }

}
