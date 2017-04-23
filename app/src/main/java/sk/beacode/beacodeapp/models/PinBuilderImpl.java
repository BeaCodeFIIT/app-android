package sk.beacode.beacodeapp.models;

import android.graphics.PointF;

public class PinBuilderImpl implements PinBuilder {
	private Pin pin;

	public PinBuilderImpl() {
		pin = new Pin();
	}

	@Override
	public PinBuilder setLocation(PointF location) {
		pin.setLocation(location);
		return this;
	}

	@Override
	public PinBuilder setColor(Pin.Color color) {
		pin.setColor(color);
		return this;
	}

	@Override
	public PinBuilder setMinor(int minor) {
		pin.setMinor(minor);
		return this;
	}

	@Override
	public Pin build() {
		return pin;
	}
}
