package com.bwei.customtextview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.widget.TextView;

/**
 * Created by muhanxi on 17/5/15.
 */

public class CustomTextView extends TextView {

    private String text;
    private int textSize;
    private int textColor;
    //画笔
    Paint mPaint ;

    Rect mBound ;

    public CustomTextView(Context context) {
        this(context,null);
    }

    public CustomTextView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CustomTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr,0);
    }

    public CustomTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);


        // 读取 设置的属性
        TypedArray typedArray =  context.obtainStyledAttributes(attrs,R.styleable.custom_textview,defStyleAttr,defStyleRes);

        text = typedArray.getString(R.styleable.custom_textview_customText);

        //sp  px
        textSize = typedArray.getDimensionPixelSize(R.styleable.custom_textview_customTextSize, (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_SP, 17, getResources().getDisplayMetrics()));

        textColor = typedArray.getColor(R.styleable.custom_textview_customTextColor, Color.BLACK);

        //回收
        typedArray.recycle();

        mPaint = new Paint();
        mPaint.setTextSize(textSize);
        mPaint.setColor(textColor);

        mBound = new Rect();
        mPaint.getTextBounds(text,0,text.length(),mBound);


    }
    int viewWidth ;
    int viewHeight ;


    // onMeasure 计算当前View 的大小
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        //获取测量模式   MeasureSpec.EXACTLY (match_parent 100dp)
        // MeasureSpec.AT_MOST (wrap_content)
        //  MeasureSpec.UNSPECIFIED (AadapterView的item的heightMode)

       int widthMode =   MeasureSpec.getMode(widthMeasureSpec);
       int heightMode =  MeasureSpec.getMode(heightMeasureSpec) ;
       int width =  MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);


        if(widthMode == MeasureSpec.EXACTLY){
            viewWidth = width ;
        } else {
            //计算出来内容的宽度 :  文件的宽度 ＋ 左右内间距
            viewWidth = mBound.width() + getPaddingLeft() + getPaddingRight() ;
        }

        if(heightMode == MeasureSpec.EXACTLY){
            viewHeight = height ;
        } else {
            viewHeight = mBound.height() + getPaddingTop() + getPaddingBottom();
        }

        //设置当前view的尺寸
        setMeasuredDimension(viewWidth,viewHeight);



        if(widthMode == MeasureSpec.EXACTLY){
            System.out.println("widthMode = " + widthMode);
        }
        if(heightMode == MeasureSpec.AT_MOST){
            System.out.println("heightMode = " + heightMode);
        }

        System.out.println("widthMeasureSpec = " + width);
        System.out.println("heightMeasureSpec = " + height);





    }


    // 当前view 画出来
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


//        mPaint.setColor(Color.BLUE);
//        canvas.drawRect(0,0,getMeasuredWidth(),getMeasuredHeight(),mPaint);

        mPaint.setColor(textColor);
//        canvas.drawText(text,0,text.length(),viewWidth/2,viewHeight/2,mPaint);


//        getWidth() / 2 - mBound.width() / 2
        canvas.drawText(text, 0, getHeight() / 2 + mBound.height() / 2, mPaint);


    }
}
