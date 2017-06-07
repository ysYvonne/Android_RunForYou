package xzh.com.materialdesign.ui;

import xzh.com.materialdesign.R;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatDialog;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Window;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;


public class RatingDialog extends AppCompatDialog {




    public interface DialogCallBackListener{//通过该接口回调Dialog需要传递的值
        void callBack(float msg);//具体方法
    }

    public RatingDialog(Context context) {
        super(context);
    }

    public RatingDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        ((AppCompatDialog) this).supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.dialog_rating);
//        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
//
//
//        tvTitle = (TextView) findViewById(R.id.dialog_rating_title);
//        tvNegative = (TextView) findViewById(R.id.dialog_rating_button_negative);
//        tvPositive = (TextView) findViewById(R.id.dialog_rating_button_positive);
//        tvFeedback = (TextView) findViewById(R.id.dialog_rating_feedback_title);
//        tvSubmit = (TextView) findViewById(R.id.dialog_rating_button_feedback_submit);
//        tvCancel = (TextView) findViewById(R.id.dialog_rating_button_feedback_cancel);
//        ratingBar = (RatingBar) findViewById(R.id.dialog_rating_rating_bar);
//        ivIcon = (ImageView) findViewById(R.id.dialog_rating_icon);
//        etFeedback = (EditText) findViewById(R.id.dialog_rating_feedback);
//        ratingButtons = (LinearLayout) findViewById(R.id.dialog_rating_buttons);
//        feedbackButtons = (LinearLayout) findViewById(R.id.dialog_rating_feedback_buttons);
//
//        init();
//    }
//
//    private void init() {
//
//        tvTitle.setText(builder.title);
//        tvPositive.setText(builder.positiveText);
//        tvNegative.setText(builder.negativeText);
//
//        tvFeedback.setText(builder.formTitle);
//        tvSubmit.setText(builder.submitText);
//        tvCancel.setText(builder.cancelText);
//        etFeedback.setHint(builder.feedbackFormHint);
//
//        TypedValue typedValue = new TypedValue();
//        context.getTheme().resolveAttribute(R.attr.colorAccent, typedValue, true);
//        int color = typedValue.data;
//
//        tvTitle.setTextColor(builder.titleTextColor != 0 ? ContextCompat.getColor(context, builder.titleTextColor) : ContextCompat.getColor(context, R.color.black_alpha));
//        tvPositive.setTextColor(builder.positiveTextColor != 0 ? ContextCompat.getColor(context, builder.positiveTextColor) : color);
//        tvNegative.setTextColor(builder.negativeTextColor != 0 ? ContextCompat.getColor(context, builder.negativeTextColor) : ContextCompat.getColor(context, R.color.selected_gray));
//
//        tvFeedback.setTextColor(builder.titleTextColor != 0 ? ContextCompat.getColor(context, builder.titleTextColor) : ContextCompat.getColor(context, R.color.black_alpha));
//        tvSubmit.setTextColor(builder.positiveTextColor != 0 ? ContextCompat.getColor(context, builder.positiveTextColor) : color);
//        tvCancel.setTextColor(builder.negativeTextColor != 0 ? ContextCompat.getColor(context, builder.negativeTextColor) : ContextCompat.getColor(context, R.color.selected_gray));
//
//        if (builder.feedBackTextColor != 0) {
//            etFeedback.setTextColor(ContextCompat.getColor(context, builder.feedBackTextColor));
//        }
//
//        if (builder.positiveBackgroundColor != 0) {
//            tvPositive.setBackgroundResource(builder.positiveBackgroundColor);
//            tvSubmit.setBackgroundResource(builder.positiveBackgroundColor);
//
//        }
//        if (builder.negativeBackgroundColor != 0) {
//            tvNegative.setBackgroundResource(builder.negativeBackgroundColor);
//            tvCancel.setBackgroundResource(builder.negativeBackgroundColor);
//        }
//
//        if (builder.ratingBarColor != 0) {
//                Drawable stars = ratingBar.getProgressDrawable();
//        }
//
//        Drawable d = context.getPackageManager().getApplicationIcon(context.getApplicationInfo());
//        ivIcon.setImageDrawable(builder.drawable != null ? builder.drawable : d);
//
//
//    }
//
//
//    public TextView getTitleTextView() {
//        return tvTitle;
//    }
//
//    public TextView getPositiveButtonTextView() {
//        return tvPositive;
//    }
//
//    public TextView getNegativeButtonTextView() {
//        return tvNegative;
//    }
//
//    public ImageView getIconImageView() {
//        return ivIcon;
//    }
//
//    public RatingBar getRatingBarView() {
//        return ratingBar;
//    }

    @Override
    public void show() {
            super.show();
    }


    public static class Builder {

        private TextView tvTitle, tvNegative, tvPositive, tvFeedback, tvSubmit, tvCancel;
        private RatingBar ratingBar;
        private ImageView ivIcon;
        private EditText etFeedback;
        private DialogCallBackListener mDialogCallBackListener1;
        private float review;

        private final Context context;

        private String title, positiveText, negativeText;
        private String formTitle, submitText, cancelText, feedbackFormHint;
        private int positiveTextColor, negativeTextColor, titleTextColor, ratingBarColor, feedBackTextColor;
        private int positiveBackgroundColor, negativeBackgroundColor;
        private RatingDialogFormListener ratingDialogFormListener;
        private RatingDialogListener ratingDialogListener;
        private DialogCallBackListener mDialogCallBackListener;
        private DialogInterface.OnClickListener positiviOnclickListener,negativeOnclickListener;
        private Drawable drawable;



        public interface RatingDialogFormListener {
            void onFormSubmitted(String feedback);
        }

        public interface RatingDialogListener {
            void onRatingSelected(float rating, boolean thresholdCleared);
        }

        public Builder(Context context) {
            this.context = context;
            initText();
        }

        private void initText() {
            title = context.getString(R.string.rating_dialog_experience);
            positiveText = context.getString(R.string.rating_dialog_maybe_later);
            negativeText = context.getString(R.string.rating_dialog_never);
            formTitle = context.getString(R.string.rating_dialog_feedback_title);
            //submitText = context.getString(R.string.rating_dialog_submit);
            cancelText = context.getString(R.string.rating_dialog_cancel);
            //feedbackFormHint = context.getString(R.string.rating_dialog_suggestions);
        }

        public Builder setCallBackListener(DialogCallBackListener mDialogCallBackListener){//设置回调
            this.mDialogCallBackListener = mDialogCallBackListener;
            return this;
        }

        public Builder setPositiveButton(DialogInterface.OnClickListener positiviOnclickListener) {//dialog的确认按钮
            this.positiviOnclickListener = positiviOnclickListener;
            return this;
        }

        public Builder setNegativeButton(DialogInterface.OnClickListener negativeOnclickListener) {//dialog的取消按钮
            this.negativeOnclickListener = negativeOnclickListener;
            return this;
        }

        public Builder ratingBarColor(int ratingBarColor) {
            this.ratingBarColor = ratingBarColor;
            return this;
        }

        public RatingDialog build() {

            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final RatingDialog mCustomDialog = new RatingDialog(context);
            mCustomDialog.setCanceledOnTouchOutside(false);
            View view = mInflater.inflate(R.layout.dialog_rating, null);
            mCustomDialog.addContentView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

            final TextView tvTitle = (TextView) view.findViewById(R.id.dialog_rating_title);
            final TextView tvNegative = (TextView) view.findViewById(R.id.dialog_rating_button_negative);
            final TextView tvPositive = (TextView) view.findViewById(R.id.dialog_rating_button_positive);
            final RatingBar ratingBar = (RatingBar) view.findViewById(R.id.dialog_rating_rating_bar);
            final ImageView ivIcon = (ImageView) view.findViewById(R.id.dialog_rating_icon);
            final LinearLayout ratingButtons = (LinearLayout) view.findViewById(R.id.dialog_rating_buttons);

            tvTitle.setText(title);
            tvPositive.setText(positiveText);
            tvNegative.setText(negativeText);


            TypedValue typedValue = new TypedValue();
            context.getTheme().resolveAttribute(R.attr.colorAccent, typedValue, true);
            int color = typedValue.data;

            tvTitle.setTextColor(titleTextColor != 0 ? ContextCompat.getColor(context, titleTextColor) : ContextCompat.getColor(context, R.color.black_alpha));
            tvPositive.setTextColor(positiveTextColor != 0 ? ContextCompat.getColor(context, positiveTextColor) : color);
            tvNegative.setTextColor(negativeTextColor != 0 ? ContextCompat.getColor(context, negativeTextColor) : ContextCompat.getColor(context, R.color.selected_gray));


            if (ratingBarColor != 0) {
                Drawable stars = ratingBar.getProgressDrawable();
            }

            Drawable d = context.getPackageManager().getApplicationIcon(context.getApplicationInfo());
            ivIcon.setImageDrawable(drawable != null ? drawable : d);

            tvPositive.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    positiviOnclickListener.onClick(mCustomDialog, BUTTON_POSITIVE);
                    if(mDialogCallBackListener != null )
                        mDialogCallBackListener.callBack(ratingBar.getRating());  //触发回调
                }
            });

            tvNegative.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    positiviOnclickListener.onClick(mCustomDialog, BUTTON_POSITIVE);
                    if(mDialogCallBackListener != null )
                        mDialogCallBackListener.callBack(ratingBar.getRating());  //触发回调
                }
            });

            mCustomDialog.setContentView(view);
            return mCustomDialog;
        }
    }
}
