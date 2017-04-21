package sk.beacode.beacodeapp.managers;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

import java.util.HashSet;
import java.util.Set;

import sk.beacode.beacodeapp.R;

public class NotificationManager {
	private Set<Integer> shownNotifications = new HashSet<>();

	private static final NotificationManager instance = new NotificationManager();

	private NotificationManager() {}

	public static NotificationManager getInstance() {
		return instance;
	}

	public boolean shouldDisplayNotification(int id) {
		return !shownNotifications.contains(id);
	}

	public void showNotification(Context context, Class resultActivity, int id, String title, String text) {
		shownNotifications.add(id);

		NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
				.setContentTitle(title)
				.setContentText(text)
				.setSmallIcon(R.drawable.pin_icon_red)
				.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));

		Intent resultIntent = new Intent(context, resultActivity);
		resultIntent.putExtra("exhibitId", id);

		TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
		stackBuilder.addParentStack(resultActivity);
		stackBuilder.addNextIntent(resultIntent);

		PendingIntent resultPendingIntent
				= stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
		builder.setContentIntent(resultPendingIntent);

		android.app.NotificationManager notificationManager
				= (android.app.NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		notificationManager.notify(id, builder.build());
	}
}
