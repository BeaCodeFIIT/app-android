package sk.beacode.beacodeapp.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.support.annotation.NonNull;
import android.util.AttributeSet;

import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;

import java.util.ArrayList;
import java.util.List;

import sk.beacode.beacodeapp.models.Pin;
import sk.beacode.beacodeapp.models.PinBuilderImpl;
import sk.beacode.beacodeapp.models.Pixel;
import sk.beacode.beacodeapp.models.Point;

/**
 * Image view with the possibility to add pins.
 */
public class PinView extends SubsamplingScaleImageView {

    private final List<Pin> pins = new ArrayList<>();
    private Pin userPosition;

    public PinView(Context context) {
        this(context, null);
    }

    public PinView(Context context, AttributeSet attr) {
        super(context, attr);
        initialise();
    }

    @NonNull
    private Point pixelToPoint(Pixel pixel) {
        return new Point(pixel.getX() / 100.0, pixel.getY() / 100.0);
    }

    private Pixel pointToPixel(Point point) {
        return new Pixel((int) Math.ceil(point.getX() * 56 + 222), (int) Math.ceil((729 - (point.getY() * 37))));
    }

    /**
     * Set user position
     * @param pixel user position
     */
    public void setUserPosition(Pixel pixel) {
        //Pixel pixel = pointToPixel(location);

        userPosition = new PinBuilderImpl().setColor(Pin.Color.RED).setLocation(new PointF(pixel.getX(), pixel.getY())).build();

        initialise();
        invalidate();
    }

    /**
     * Add pin onto specified location
     * @param location location
     * @param color pin color
     * @param minor beacon minor id
     */
    public void addPin(Pixel location, Pin.Color color, int minor) {
        Pin pin = new Pin();
        pin.setColor(color);
        pin.setLocation(new PointF(location.getX(), location.getY()));
        pin.setMinor(minor);
        pins.add(pin);

        initialise();
        invalidate();
    }

    public void clearPins() {
        pins.clear();
        initialise();
        invalidate();
    }

    public List<Pin> getPins() {
        return pins;
    }

    private void initialise() {
//        float density = getResources().getDisplayMetrics().densityDpi;
//
//        for (Pin pin : pins) {
//            Bitmap bitmap = pin.getBitmap(getContext());
//            float w = (density/420f) * bitmap.getWidth();
//            float h = (density/420f) * bitmap.getHeight();
//            // pin.setBitmap(Bitmap.createScaledBitmap(bitmap, (int)w, (int)h, true));
//        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Don't draw pinImages before image is ready so it doesn't move around during setup.
        if (!isReady()) {
            return;
        }

        Paint paint = new Paint();
        paint.setAntiAlias(true);

        if (userPosition != null) {
            PointF vPin = sourceToViewCoord(userPosition.getLocation());
            float vX = vPin.x - (userPosition.getBitmap(getContext()).getWidth()/2);
            float vY = vPin.y - userPosition.getBitmap(getContext()).getHeight();
            canvas.drawBitmap(userPosition.getBitmap(getContext()), vX, vY, paint);
        }

        for (Pin pin : pins) {
            PointF vPin = sourceToViewCoord(pin.getLocation());
            float vX = vPin.x - (pin.getBitmap(getContext()).getWidth()/2);
            float vY = vPin.y - pin.getBitmap(getContext()).getHeight();
            canvas.drawBitmap(pin.getBitmap(getContext()), vX, vY, paint);
        }

    }

}
