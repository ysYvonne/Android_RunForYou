package xzh.com.materialdesign.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class ActivityHelper {
	
	public static void startActivity(Context context, Class<? extends Activity> toklass) {
		Intent intent = new Intent(context, toklass);
		context.startActivity(intent);
	}

	public static void startActivity(Context context,Intent intent,Bundle extras) {
		intent.putExtras(extras);
		context.startActivity(intent);
	}

	public static void startActivity(Context context, Class<? extends Activity> toklass, Bundle extras) {
		Intent intent = new Intent(context, toklass);
		intent.putExtras(extras);
		context.startActivity(intent);
	}

	public static void startActivity(Activity fromActivity, Class<? extends Activity> toklass, Bundle extras, int enterAnim, int exitAnim) {
		Intent intent = new Intent(fromActivity, toklass);
		intent.putExtras(extras);
		fromActivity.startActivity(intent);
		fromActivity.overridePendingTransition(enterAnim, exitAnim);
	}
	
	public static void startActivityForResult(Activity fromActivity, Class<? extends Activity> toklass, 
			int requestCode) {
		Intent intent = new Intent(fromActivity, toklass);
		fromActivity.startActivityForResult(intent, requestCode);
	}

    public static void startActivityForResult(Activity fromActivity, Class<? extends Activity> toklass,
                                              int requestCode, int enterAnim, int exitAnim) {
        Intent intent = new Intent(fromActivity, toklass);
        fromActivity.startActivityForResult(intent, requestCode);
        fromActivity.overridePendingTransition(enterAnim, exitAnim);
    }
	
	public static void startActivityForResult(Activity fromActivity, Class<? extends Activity> toklass, 
			int requestCode, Bundle extras) {
		Intent intent = new Intent(fromActivity, toklass);
		intent.putExtras(extras);
		fromActivity.startActivityForResult(intent, requestCode);
	}

    public static void startActivityForResult(Activity fromActivity, Class<? extends Activity> toklass,
                                              int requestCode, Bundle extras, int enterAnim, int exitAnim) {
        Intent intent = new Intent(fromActivity, toklass);
        intent.putExtras(extras);
        fromActivity.startActivityForResult(intent, requestCode);
        fromActivity.overridePendingTransition(enterAnim, exitAnim);
    }

}
