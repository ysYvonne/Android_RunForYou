package xzh.com.materialdesign.adapter;

import android.content.Context;
import android.text.SpannableString;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;



import xzh.com.materialdesign.utils.StringUtils;
import xzh.com.materialdesign.utils.TypefaceUtils;


/**
 * 通用性极高的ViewHolder
 */
public class ViewHolder {
    private SparseArray<View> mViews;

    private View mConvertView;

    protected Context mContext;

    private int position;

    public ViewHolder(Context context, ViewGroup parent, int layoutId, int position) {
        this.mViews = new SparseArray<>();
        this.mConvertView = LayoutInflater.from(context).inflate(layoutId, parent, false);
        this.mConvertView.setTag(this);
        this.mContext = context;
        this.position = position;
    }

    /**
     * 获取一个viewHolder
     * @param context     context
     * @param convertView view
     * @param parent      parent view
     * @param layoutId    布局资源id
     * @param position    索引
     * @return
     */
    public static ViewHolder getViewHolder(Context context, View convertView, ViewGroup parent,
                                           int layoutId, int position) {
        if (convertView == null) {
            return new ViewHolder(context, parent, layoutId, position);
        }

        return (ViewHolder) convertView.getTag();
    }

    public int getPosition() {
        return this.position;
    }

    // 通过一个viewId来获取一个view
    public <T extends View> T getView(int viewId) {

        View view = mViews.get(viewId);
        if (view == null) {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    // 返回viewHolder的容器类
    public View getConvertView() {
        return this.mConvertView;
    }

    // 给TextView设置文字
    public void setText(int viewId, String text) {
        if (StringUtils.isEmpty(text)) return;
        TextView tv = getView(viewId);
        tv.setText(text);
    }

    // 给TextView设置文字
    public void setText(int viewId, SpannableString text) {
        if (text == null) return;
        TextView tv = getView(viewId);
        tv.setText(text);
    }

    // 给TextView设置文字
    public void setText(int viewId, int textRes) {
        TextView tv = getView(viewId);
        tv.setText(textRes);
    }

    public void setText(int viewId, String text, int emptyRes) {
        TextView tv = getView(viewId);
        if (StringUtils.isEmpty(text)) {
            tv.setText(emptyRes);
        } else {
            tv.setText(text);
        }
    }

    public void setText(int viewId, String text, String emptyText) {
        TextView tv = getView(viewId);
        if (StringUtils.isEmpty(text)) {
            tv.setText(emptyText);
        } else {
            tv.setText(text);
        }
    }

    /**
     * @param viewId      id
     * @param text        内容
     * @param semanticRes 资源
     */
    public void setTextWithSemantic(int viewId, String text, int semanticRes) {
        TextView tv = getView(viewId);
        TypefaceUtils.setSemantic(tv, text, semanticRes);
    }

    public void setTextWithOcticon(int viewId, String text, int iconRes) {
        TextView tv = getView(viewId);
        TypefaceUtils.setOcticons(tv, text, iconRes);
    }

    // 给ImageView设置图片资源
    public void setImageResource(int viewId, int resId) {
        ImageView iv = getView(viewId);
        iv.setImageResource(resId);
    }

}
