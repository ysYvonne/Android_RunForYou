package xzh.com.materialdesign.ui;

import xzh.com.materialdesign.R;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatDialog;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;


public class RatingDialog extends AppCompatDialog implements RatingBar.OnRatingBarChangeListener, View.OnClickListener {

    private static final String SESSION_COUNT = "session_count";
    private static final String SHOW_NEVER = "show_never";
    private String MyPrefs = "RatingDialog";

    private Context context;
    private Builder builder;
    private TextView tvTitle, tvNegative, tvPositive, tvFeedback, tvSubmit, tvCancel;
    private RatingBar ratingBar;
    private ImageView ivIcon;
    private EditText etFeedback;
    private LinearLayout ratingButtons, feedbackButtons;

    private float threshold;
    private int session;
    private boolean thresholdPassed = true;

    public RatingDialog(Context context, Builder builder) {
        super(context);
        this.context = context;
        this.builder = builder;

        this.session = builder.session;
        this.threshold = builder.threshold;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ((AppCompatDialog) this).supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_rating);
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));


        tvTitle = (TextView) findViewById(R.id.dialog_rating_title);
        tvNegative = (TextView) findViewById(R.id.dialog_rating_button_negative);
        tvPositive = (TextView) findViewById(R.id.dialog_rating_button_positive);
        tvFeedback = (TextView) findViewById(R.id.dialog_rating_feedback_title);
        tvSubmit = (TextView) findViewById(R.id.dialog_rating_button_feedback_submit);
        tvCancel = (TextView) findViewById(R.id.dialog_rating_button_feedback_cancel);
        ratingBar = (RatingBar) findViewById(R.id.dialog_rating_rating_bar);
        ivIcon = (ImageView) findViewById(R.id.dialog_rating_icon);
        etFeedback = (EditText) findViewById(R.id.dialog_rating_feedback);
        ratingButtons = (LinearLayout) findViewById(R.id.dialog_rating_buttons);
        feedbackButtons = (LinearLayout) findViewById(R.id.dialog_rating_feedback_buttons);

        init();
    }

    private void init() {

        tvTitle.setText(builder.title);
        tvPositive.setText(builder.positiveText);
        tvNegative.setText(builder.negativeText);

        tvFeedback.setText(builder.formTitle);
        tvSubmit.setText(builder.submitText);
        tvCancel.setText(builder.cancelText);
        etFeedback.setHint(builder.feedbackFormHint);

        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(R.attr.colorAccent, typedValue, true);
        int color = typedValue.data;

        tvTitle.setTextColor(builder.titleTextColor != 0 ? ContextCompat.getColor(context, builder.titleTextColor) : ContextCompat.getColor(context, R.color.black_alpha));
        tvPositive.setTextColor(builder.positiveTextColor != 0 ? ContextCompat.getColor(context, builder.positiveTextColor) : color);
        tvNegative.setTextColor(builder.negativeTextColor != 0 ? ContextCompat.getColor(context, builder.negativeTextColor) : ContextCompat.getColor(context, R.color.selected_gray));

        tvFeedback.setTextColor(builder.titleTextColor != 0 ? ContextCompat.getColor(context, builder.titleTextColor) : ContextCompat.getColor(context, R.color.black_alpha));
        tvSubmit.setTextColor(builder.positiveTextColor != 0 ? ContextCompat.getColor(context, builder.positiveTextColor) : color);
        tvCancel.setTextColor(builder.negativeTextColor != 0 ? ContextCompat.getColor(context, builder.negativeTextColor) : ContextCompat.getColor(context, R.color.selected_gray));

        if (builder.feedBackTextColor != 0) {
            etFeedback.setTextColor(ContextCompat.getColor(context, builder.feedBackTextColor));
        }

        if (builder.positiveBackgroundColor != 0) {
            tvPositive.setBackgroundResource(builder.positiveBackgroundColor);
            tvSubmit.setBackgroundResource(builder.positiveBackgroundColor);

        }
        if (builder.negativeBackgroundColor != 0) {
            tvNegative.setBackgroundResource(builder.negativeBackgroundColor);
            tvCancel.setBackgroundResource(builder.negativeBackgroundColor);
        }

        if (builder.ratingBarColor != 0) {
                Drawable stars = ratingBar.getProgressDrawable();
        }

        Drawable d = context.getPackageManager().getApplicationIcon(context.getApplicationInfo());
        ivIcon.setImageDrawable(builder.drawable != null ? builder.drawable : d);

        ratingBar.setOnRatingBarChangeListener(this);
        tvPositive.setOnClickListener(this);
        tvNegative.setOnClickListener(this);
        tvSubmit.setOnClickListener(this);
        tvCancel.setOnClickListener(this);

        if (session == 1) {
            tvNegative.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.dialog_rating_button_negative) {

            dismiss();
        } else if (view.getId() == R.id.dialog_rating_button_positive) {

            dismiss();

        }

    }

    @Override
    public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {


        if (ratingBar.getRating() >= threshold) {
            thresholdPassed = true;

            if (builder.ratingThresholdClearedListener == null) {
                setRatingThresholdClearedListener();
            }
            builder.ratingThresholdClearedListener.onThresholdCleared(this, ratingBar.getRating(), thresholdPassed);

        } else {
            thresholdPassed = false;

            if (builder.ratingThresholdFailedListener == null) {
                setRatingThresholdFailedListener();
            }
            builder.ratingThresholdFailedListener.onThresholdFailed(this, ratingBar.getRating(), thresholdPassed);
        }

        if (builder.ratingDialogListener != null) {
            builder.ratingDialogListener.onRatingSelected(ratingBar.getRating(), thresholdPassed);
        }
    }

    private void setRatingThresholdClearedListener() {
        builder.ratingThresholdClearedListener = new Builder.RatingThresholdClearedListener() {
            @Override
            public void onThresholdCleared(RatingDialog ratingDialog, float rating, boolean thresholdCleared) {
               // dismiss();
            }
        };
    }

    private void setRatingThresholdFailedListener() {
        builder.ratingThresholdFailedListener = new Builder.RatingThresholdFailedListener() {
            @Override
            public void onThresholdFailed(RatingDialog ratingDialog, float rating, boolean thresholdCleared) {
                //openForm();
            }
        };
    }


    public TextView getTitleTextView() {
        return tvTitle;
    }

    public TextView getPositiveButtonTextView() {
        return tvPositive;
    }

    public TextView getNegativeButtonTextView() {
        return tvNegative;
    }

    public TextView getFormTitleTextView() {
        return tvFeedback;
    }

    public TextView getFormSumbitTextView() {
        return tvSubmit;
    }

    public TextView getFormCancelTextView() {
        return tvCancel;
    }

    public ImageView getIconImageView() {
        return ivIcon;
    }

    public RatingBar getRatingBarView() {
        return ratingBar;
    }

    @Override
    public void show() {
            super.show();
    }




    public static class Builder {

        private final Context context;
        private String title, positiveText, negativeText;
        private String formTitle, submitText, cancelText, feedbackFormHint;
        private int positiveTextColor, negativeTextColor, titleTextColor, ratingBarColor, feedBackTextColor;
        private int positiveBackgroundColor, negativeBackgroundColor;
        private RatingThresholdClearedListener ratingThresholdClearedListener;
        private RatingThresholdFailedListener ratingThresholdFailedListener;
        private RatingDialogFormListener ratingDialogFormListener;
        private RatingDialogListener ratingDialogListener;
        private Drawable drawable;

        private int session = 1;
        private float threshold = 1;

        public interface RatingThresholdClearedListener {
            void onThresholdCleared(RatingDialog ratingDialog, float rating, boolean thresholdCleared);
        }

        public interface RatingThresholdFailedListener {
            void onThresholdFailed(RatingDialog ratingDialog, float rating, boolean thresholdCleared);
        }

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

        public Builder session(int session) {
            this.session = session;
            return this;
        }

        public Builder threshold(float threshold) {
            this.threshold = threshold;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        /*public Builder icon(int icon) {
            this.icon = icon;
            return this;
        }*/

        public Builder icon(Drawable drawable) {
            this.drawable = drawable;
            return this;
        }

        public Builder positiveButtonText(String positiveText) {
            this.positiveText = positiveText;
            return this;
        }

        public Builder negativeButtonText(String negativeText) {
            this.negativeText = negativeText;
            return this;
        }

        public Builder titleTextColor(int titleTextColor) {
            this.titleTextColor = titleTextColor;
            return this;
        }

        public Builder positiveButtonTextColor(int positiveTextColor) {
            this.positiveTextColor = positiveTextColor;
            return this;
        }

        public Builder negativeButtonTextColor(int negativeTextColor) {
            this.negativeTextColor = negativeTextColor;
            return this;
        }

        public Builder positiveButtonBackgroundColor(int positiveBackgroundColor) {
            this.positiveBackgroundColor = positiveBackgroundColor;
            return this;
        }

        public Builder negativeButtonBackgroundColor(int negativeBackgroundColor) {
            this.negativeBackgroundColor = negativeBackgroundColor;
            return this;
        }

        public Builder onThresholdCleared(RatingThresholdClearedListener ratingThresholdClearedListener) {
            this.ratingThresholdClearedListener = ratingThresholdClearedListener;
            return this;
        }

        public Builder onThresholdFailed(RatingThresholdFailedListener ratingThresholdFailedListener) {
            this.ratingThresholdFailedListener = ratingThresholdFailedListener;
            return this;
        }

        public Builder onRatingChanged(RatingDialogListener ratingDialogListener) {
            this.ratingDialogListener = ratingDialogListener;
            return this;
        }

        public Builder onRatingBarFormSumbit(RatingDialogFormListener ratingDialogFormListener) {
            this.ratingDialogFormListener = ratingDialogFormListener;
            return this;
        }

        public Builder formTitle(String formTitle) {
            this.formTitle = formTitle;
            return this;
        }

        public Builder formHint(String formHint) {
            this.feedbackFormHint = formHint;
            return this;
        }

        public Builder formSubmitText(String submitText) {
            this.submitText = submitText;
            return this;
        }

        public Builder formCancelText(String cancelText) {
            this.cancelText = cancelText;
            return this;
        }

        public Builder ratingBarColor(int ratingBarColor) {
            this.ratingBarColor = ratingBarColor;
            return this;
        }

        public Builder feedbackTextColor(int feedBackTextColor) {
            this.feedBackTextColor = feedBackTextColor;
            return this;
        }

        public RatingDialog build() {
            return new RatingDialog(context, this);
        }
    }
}
