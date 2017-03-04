package sk.beacode.beacodeapp.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import java.util.List;

import sk.beacode.beacodeapp.R;
import sk.beacode.beacodeapp.models.Image;

@EViewGroup(R.layout.view_horizontal_gallery)
public class HorizontalGalleryView extends HorizontalScrollView {

    public interface ThumbnailClickListener {
        void onClick(int id, Uri photo);
    }

    @ViewById(R.id.gallery_layout)
    LinearLayout layout;

    private List<Image> photos;
    private ThumbnailClickListener thumbnailClickListener;

    public HorizontalGalleryView(Context context) {
        super(context);
    }

    public HorizontalGalleryView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public HorizontalGalleryView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setThumbnailClickListener(ThumbnailClickListener l) {
        thumbnailClickListener = l;
    }

    public void bind(List<Image> photos) {
        this.photos = photos;

        layout.removeAllViews();

        if (this.photos == null) {
            return;
        }

        for (int i = 0; i < this.photos.size(); ++i) {
            final ImageView imageView = new ImageView(getContext());
            imageView.setId(i);

            int left = 0;
            int top = 25;
            int right = i == (this.photos.size() - 1) ? 0 : 15;
            int bottom = 25;
            imageView.setPadding(left, top, right, bottom);

            final Uri imageUri = this.photos.get(i).getUri();
            Glide.with(getContext()).load(imageUri).into(imageView);
            imageView.setAdjustViewBounds(true);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (thumbnailClickListener != null) {
                        int id = view.getId();
                        thumbnailClickListener.onClick(id, imageUri);
                    }
                }
            });
            layout.addView(imageView);
        }
    }
}
