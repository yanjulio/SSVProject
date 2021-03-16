package com.soft.ssvapp.Fragment_Menu.Navigation;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.view.animation.Interpolator;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;

import com.soft.ssvapp.R;

public class NavigationIconclickListener implements View.OnClickListener {

    private final AnimatorSet animatorSet = new AnimatorSet();
    private Context context;
    private View sheet;
    private Interpolator interpolator;
    private int height;
    private boolean backdropShown = false;
    private Drawable openIcon;
    private Drawable closeIcon;

    NavigationIconclickListener(Context context, View sheet)
    {
        this(context, sheet, null);
    }

    NavigationIconclickListener(Context context, View sheet, Interpolator interpolator)
    {
        this(context, sheet, interpolator, null, null);
    }

    NavigationIconclickListener(Context context, View sheet, Interpolator interpolator, Drawable openIcon, Drawable closeIcon)
    {
        this.context = context;
        this.sheet = sheet;
        this.interpolator = interpolator;
        this.openIcon = openIcon;
        this.closeIcon = closeIcon;

        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity)context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        height = displayMetrics.heightPixels;
    }

    @Override
    public void onClick(View v) {
        backdropShown = !backdropShown;

        // cancel the existing animations
        animatorSet.removeAllListeners();
        animatorSet.end();
        animatorSet.cancel();

        updateIcon(v);

        final int translateY = height - context.getResources().getDimensionPixelSize(R.dimen.Kp_menu_grid_reveal_heigt);

        ObjectAnimator animator = ObjectAnimator.ofFloat(sheet, "translationY", backdropShown ? translateY : 0);
        animator.setDuration(500);
        if (interpolator != null)
        {
            animator.setInterpolator(interpolator);
        }
        animatorSet.play(animator);
        animator.start();
    }

    void updateIcon(View view)
    {
        if ((openIcon != null && closeIcon != null))
        {
            if (!(view instanceof ImageView))
            {
                throw  new IllegalArgumentException("updateIcon() must be called on an ImageView");
            }
            if (backdropShown)
            {
                ((ImageView)view).setImageDrawable(closeIcon);
            }
            else
            {
                ((ImageView)view).setImageDrawable(openIcon);
            }
        }
    }
}
