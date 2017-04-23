package sk.beacode.beacodeapp.models;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import sk.beacode.beacodeapp.R;

public class BlueBitmaps implements BitmapApi {
	@Override
	public Bitmap getPin(Context context) {
		return BitmapFactory.decodeResource(context.getResources(), R.drawable.pin_icon_blue);
	}
}
