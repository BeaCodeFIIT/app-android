package sk.beacode.beacodeapp.models;

import android.graphics.PointF;

public interface PinBuilder {
	PinBuilder setLocation(PointF location);
	PinBuilder setColor(Pin.Color color);
	PinBuilder setMinor(int minor);
	Pin build();
}
