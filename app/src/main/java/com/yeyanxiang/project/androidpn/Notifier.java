package com.yeyanxiang.project.androidpn;

import java.util.Random;

import org.androidpn.client.Constants;
import org.androidpn.client.LogUtil;

import com.yeyanxiang.project.R;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

/**
 * @author 叶雁翔
 * 
 * @Email yanxiang1120@gmail.com
 * 
 * @version 1.0
 * 
 * @update 2014年3月13日
 * 
 * @简介 This class is to notify the user of messages with NotificationManager.
 */
public class Notifier {

	private static final String LOGTAG = LogUtil.makeLogTag(Notifier.class);

	private static final Random random = new Random(System.currentTimeMillis());

	private Context context;

	private SharedPreferences sharedPrefs;

	private NotificationManager notificationManager;

	public Notifier(Context context) {
		this.context = context;
		this.sharedPrefs = context.getSharedPreferences(
				AndroidpnMainActivity.USERDATA, Context.MODE_PRIVATE);
		this.notificationManager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
	}

	public void notify(String notificationId, String apiKey, String title,
			String message, String uri) {
		// Log.d(LOGTAG, "notify()...");
		//
		// Log.d(LOGTAG, "notificationId=" + notificationId);
		// Log.d(LOGTAG, "notificationApiKey=" + apiKey);
		// Log.d(LOGTAG, "notificationTitle=" + title);
		// Log.d(LOGTAG, "notificationMessage=" + message);
		// Log.d(LOGTAG, "notificationUri=" + uri);

		if (isNotificationEnabled()) {
			// Show the toast
			if (isNotificationToastEnabled()) {
				Toast.makeText(context, message, Toast.LENGTH_LONG).show();
			}

			// Notification
			Notification notification = new Notification();
			notification.icon = getNotificationIcon();
			notification.defaults = Notification.DEFAULT_LIGHTS;
			if (isNotificationSoundEnabled()) {
				notification.defaults |= Notification.DEFAULT_SOUND;
			}
			if (isNotificationVibrateEnabled()) {
				notification.defaults |= Notification.DEFAULT_VIBRATE;
			}
			notification.flags |= Notification.FLAG_AUTO_CANCEL;
			notification.when = System.currentTimeMillis();
			notification.tickerText = message;

			// Intent intent;
			// if (uri != null
			// && uri.length() > 0
			// && (uri.startsWith("http:") || uri.startsWith("https:")
			// || uri.startsWith("tel:") || uri.startsWith("geo:"))) {
			// intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
			// } else {
			// String callbackActivityPackageName = sharedPrefs.getString(
			// Constants.CALLBACK_ACTIVITY_PACKAGE_NAME, "");
			// String callbackActivityClassName = sharedPrefs.getString(
			// Constants.CALLBACK_ACTIVITY_CLASS_NAME, "");
			// intent = new
			// Intent().setClassName(callbackActivityPackageName,
			// callbackActivityClassName);
			// intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			// intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
			// }

			Intent intent = new Intent(context,
					NotificationDetailsActivity.class);
			intent.putExtra(Constants.NOTIFICATION_ID, notificationId);
			intent.putExtra(Constants.NOTIFICATION_API_KEY, apiKey);
			intent.putExtra(Constants.NOTIFICATION_TITLE, title);
			intent.putExtra(Constants.NOTIFICATION_MESSAGE, message);
			intent.putExtra(Constants.NOTIFICATION_URI, uri);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			intent.setFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
			intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
			intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

			int id = random.nextInt();

			PendingIntent contentIntent = PendingIntent.getActivity(context,
					id, intent, PendingIntent.FLAG_UPDATE_CURRENT);

			notification.setLatestEventInfo(context, title, message,
					contentIntent);
			notificationManager.notify(id, notification);

			// Intent clickIntent = new Intent(
			// Constants.ACTION_NOTIFICATION_CLICKED);
			// clickIntent.putExtra(Constants.NOTIFICATION_ID,
			// notificationId);
			// clickIntent.putExtra(Constants.NOTIFICATION_API_KEY, apiKey);
			// clickIntent.putExtra(Constants.NOTIFICATION_TITLE, title);
			// clickIntent.putExtra(Constants.NOTIFICATION_MESSAGE,
			// message);
			// clickIntent.putExtra(Constants.NOTIFICATION_URI, uri);
			// // positiveIntent.setData(Uri.parse((new StringBuilder(
			// //
			// "notif://notification.adroidpn.org/")).append(apiKey).append(
			// // "/").append(System.currentTimeMillis()).toString()));
			// PendingIntent clickPendingIntent =
			// PendingIntent.getBroadcast(
			// context, 0, clickIntent, 0);
			//
			// notification.setLatestEventInfo(context, title, message,
			// clickPendingIntent);
			//
			// Intent clearIntent = new Intent(
			// Constants.ACTION_NOTIFICATION_CLEARED);
			// clearIntent.putExtra(Constants.NOTIFICATION_ID,
			// notificationId);
			// clearIntent.putExtra(Constants.NOTIFICATION_API_KEY, apiKey);
			// // negativeIntent.setData(Uri.parse((new StringBuilder(
			// //
			// "notif://notification.adroidpn.org/")).append(apiKey).append(
			// // "/").append(System.currentTimeMillis()).toString()));
			// PendingIntent clearPendingIntent =
			// PendingIntent.getBroadcast(
			// context, 0, clearIntent, 0);
			// notification.deleteIntent = clearPendingIntent;
			//
			// notificationManager.notify(random.nextInt(), notification);

		} else {
			Log.w(LOGTAG, "Notificaitons disabled.");
		}
	}

	private int getNotificationIcon() {
		return sharedPrefs.getInt(Constants.NOTIFICATION_ICON,
				R.drawable.icon);
	}

	private boolean isNotificationEnabled() {
		return sharedPrefs.getBoolean(Constants.SETTINGS_NOTIFICATION_ENABLED,
				true);
	}

	private boolean isNotificationSoundEnabled() {
		return sharedPrefs.getBoolean(Constants.SETTINGS_SOUND_ENABLED, true);
	}

	private boolean isNotificationVibrateEnabled() {
		return sharedPrefs.getBoolean(Constants.SETTINGS_VIBRATE_ENABLED, true);
	}

	private boolean isNotificationToastEnabled() {
		return sharedPrefs.getBoolean(Constants.SETTINGS_TOAST_ENABLED, false);
	}

}
