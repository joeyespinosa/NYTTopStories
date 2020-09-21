package com.axelia.nyttopstories.ui.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.widget.RemoteViews;

import com.axelia.nyttopstories.R;
import com.axelia.nyttopstories.ui.list.browse.MainActivity;
import com.axelia.nyttopstories.utils.Constants;

import static android.content.Context.MODE_PRIVATE;


public class StoryWidgetProvider extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_recently_saved);

        SharedPreferences prefs = context.getSharedPreferences(Constants.MY_PREFS_NAME, MODE_PRIVATE);
        String name = prefs.getString(context.getString(R.string.story_title), "Nothing recently added");

        remoteViews.setTextViewText(R.id.textview_widget_story_title, name);
        Intent itemIntent = new Intent(context, MainActivity.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, appWidgetId, itemIntent, 0);
        remoteViews.setOnClickPendingIntent(R.id.widget_list_container, pendingIntent);

        appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
    }

    @Override
    public void onDisabled(Context context) {
    }
}

