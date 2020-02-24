package com.pomelo.searchcustomer.utils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.net.Uri;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.youth.banner.loader.ImageLoader;

/**
 * Created by wanghaoxiang on 2020-01-07.
 */

public class GlideImageLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        /**
         注意：
         1.图片加载器由自己选择，这里不限制，只是提供几种使用方法
         2.返回的图片路径为Object类型，由于不能确定你到底使用的那种图片加载器，
         传输的到的是什么格式，那么这种就使用Object接收和返回，你只需要强转成你传输的类型就行，
         切记不要胡乱强转！
         */

        //Glide 加载图片简单用法
        Glide.with(context).load(path).into(imageView);
    }

    /**
     * 自定义圆角类
     *
     * @param context
     * @return
     */
    @Override
    public ImageView createImageView(Context context) {
        RoundImageView img = new RoundImageView(context);
        return img;

    }

    public class RoundImageView extends android.support.v7.widget.AppCompatImageView {


        float width, height;

        public RoundImageView(Context context) {
            this(context, null);
        }

        public RoundImageView(Context context, AttributeSet attrs) {
            this(context, attrs, 0);
        }

        public RoundImageView(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
            if (Build.VERSION.SDK_INT < 18) {
                setLayerType(View.LAYER_TYPE_SOFTWARE, null);
            }
        }

        @Override
        protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
            super.onLayout(changed, left, top, right, bottom);
            width = getWidth();
            height = getHeight();
        }

        @Override
        protected void onDraw(Canvas canvas) {

            if (width > 12 && height > 12) {
                Path path = new Path();
                path.moveTo(12, 0);
                path.lineTo(width - 12, 0);
                path.quadTo(width, 0, width, 12);
                path.lineTo(width, height - 12);
                path.quadTo(width, height, width - 12, height);
                path.lineTo(12, height);
                path.quadTo(0, height, 0, height - 12);
                path.lineTo(0, 12);
                path.quadTo(0, 0, 12, 0);
                canvas.clipPath(path);
            }

            super.onDraw(canvas);
        }
    }
}
