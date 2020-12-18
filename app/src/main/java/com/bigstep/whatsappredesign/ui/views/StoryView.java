package com.bigstep.whatsappredesign.ui.views;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.bigstep.whatsappredesign.R;
import com.bigstep.whatsappredesign.data.model.story.StoryObject;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;


public class StoryView extends View {
    public static final int STORY_IMAGE_RADIUS_IN_DP = 36;
    public static final int STORY_INDICATOR_WIDTH_IN_DP = 4;
    public static final int SPACE_BETWEEN_IMAGE_AND_INDICATOR = 2;
    public static final int START_ANGLE = 270;
    public static int ANGEL_OF_GAP = 15;
    private int mStoryImageRadiusInPx;
    private int mStoryIndicatorWidthInPx;
    private int mSpaceBetweenImageAndIndicator;
    private int mPendingIndicatorColor;
    private int mVisistedIndicatorColor;
    private int mViewWidth;
    private int mViewHeight;
    private int mIndicatoryOffset;
    private int mIndicatorImageOffset;
    private Resources resources;
    private StoryObject storyObject;
    private Paint mIndicatorPaint;
    private int indicatorCount;
    private int indicatorSweepAngle;
    private Bitmap mIndicatorImageBitmap;
    private Rect mIndicatorImageRect;

    private Context context;

    public StoryView(Context context) {
        super(context);
        init(context);
        setDefaults();
    }

    private void init(Context context) {
        resources = context.getResources();
        this.context = context;
        mIndicatorPaint = new Paint();
        mIndicatorPaint.setAntiAlias(true);
        mIndicatorPaint.setStyle(Paint.Style.STROKE);
        mIndicatorPaint.setStrokeCap(Paint.Cap.ROUND);
    }

    private void setDefaults() {
        mStoryImageRadiusInPx = getPxFromDp(STORY_IMAGE_RADIUS_IN_DP);
        mStoryIndicatorWidthInPx = getPxFromDp(STORY_INDICATOR_WIDTH_IN_DP);
        mSpaceBetweenImageAndIndicator = getPxFromDp(SPACE_BETWEEN_IMAGE_AND_INDICATOR);
        mPendingIndicatorColor = ContextCompat.getColor(context,R.color.custom_green);
        mVisistedIndicatorColor = ContextCompat.getColor(context,R.color.custom_green);
        prepareValues();
    }

    private int getPxFromDp(int dpValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, resources.getDisplayMetrics());
    }

    private void prepareValues() {
        mViewHeight = 2 * (mStoryIndicatorWidthInPx + mSpaceBetweenImageAndIndicator + mStoryImageRadiusInPx);
        mViewWidth = mViewHeight;
        mIndicatoryOffset = mStoryIndicatorWidthInPx / 2;
        mIndicatorImageOffset = mStoryIndicatorWidthInPx + mSpaceBetweenImageAndIndicator;
        mIndicatorImageRect = new Rect(mIndicatorImageOffset, mIndicatorImageOffset, mViewWidth - mIndicatorImageOffset, mViewHeight - mIndicatorImageOffset);
    }

    public StoryView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.StoryView, 0, 0);
        try {
            mStoryImageRadiusInPx = getPxFromDp((int) ta.getDimension(R.styleable.StoryView_storyImageRadius, STORY_IMAGE_RADIUS_IN_DP));
            mStoryIndicatorWidthInPx = getPxFromDp((int) ta.getDimension(R.styleable.StoryView_storyItemIndicatorWidth, STORY_INDICATOR_WIDTH_IN_DP));
            mSpaceBetweenImageAndIndicator = getPxFromDp((int) ta.getDimension(R.styleable.StoryView_spaceBetweenImageAndIndicator, SPACE_BETWEEN_IMAGE_AND_INDICATOR));
            mPendingIndicatorColor = ta.getColor(R.styleable.StoryView_pendingIndicatorColor, ContextCompat.getColor(context,R.color.custom_green));
            mVisistedIndicatorColor = ta.getColor(R.styleable.StoryView_visitedIndicatorColor, ContextCompat.getColor(context,R.color.custom_green));
        }
        finally {
            ta.recycle();
        }
        prepareValues();
    }


    public void setStoryObject(StoryObject storyObject) {
        this.storyObject = storyObject;
        this.indicatorCount = storyObject.getStoryCount();
        this.storyObject = storyObject;
        calculateSweepAngle(indicatorCount);
        invalidate();
        loadFirstImageBitamp();
    }

    private void calculateSweepAngle(int itemCounts) {
        if (itemCounts == 1) {
            ANGEL_OF_GAP = 0;
        }
        this.indicatorSweepAngle = (360 / itemCounts) - ANGEL_OF_GAP / 2;
    }

    private void loadFirstImageBitamp() {
        Glide.with(this)
                .asBitmap()
                .circleCrop()
                .load(storyObject.getImageIcon())
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                        mIndicatorImageBitmap = resource;
                        invalidate();
                    }
                });
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mIndicatorPaint.setColor(mPendingIndicatorColor);
        mIndicatorPaint.setStrokeWidth(mStoryIndicatorWidthInPx);
        int startAngle = START_ANGLE + ANGEL_OF_GAP / 2;
        for (int i = 0; i < indicatorCount; i++) {
            mIndicatorPaint.setColor(getIndicatorColor(i));
            canvas.drawArc(mIndicatoryOffset, mIndicatoryOffset, mViewWidth - mIndicatoryOffset, mViewHeight - mIndicatoryOffset, startAngle, indicatorSweepAngle - ANGEL_OF_GAP / 2, false, mIndicatorPaint);
            startAngle += indicatorSweepAngle + ANGEL_OF_GAP / 2;
        }
        if (mIndicatorImageBitmap != null) {
            canvas.drawBitmap(mIndicatorImageBitmap, null, mIndicatorImageRect, null);
        }
    }

    private int getIndicatorColor(int index) {
        return mPendingIndicatorColor;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = getPaddingStart() + getPaddingEnd() + mViewWidth;
        int height = getPaddingTop() + getPaddingBottom() + mViewHeight;
        int w = resolveSizeAndState(width, widthMeasureSpec, 0);
        int h = resolveSizeAndState(height, heightMeasureSpec, 0);
        setMeasuredDimension(w, h);
    }
}