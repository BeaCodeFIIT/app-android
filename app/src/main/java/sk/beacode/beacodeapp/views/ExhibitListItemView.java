package sk.beacode.beacodeapp.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.rest.spring.annotations.RestService;

import hugo.weaving.DebugLog;
import sk.beacode.beacodeapp.R;
import sk.beacode.beacodeapp.managers.ExhibitManager;
import sk.beacode.beacodeapp.models.Exhibit;

@EViewGroup(R.layout.item_exhibit)
public class ExhibitListItemView extends LinearLayout implements Checkable {

    @ViewById(R.id.name)
    TextView nameView;

    @ViewById(R.id.description)
    TextView descriptionView;

    @ViewById(R.id.exhibit_image)
    ImageView photoView;

//    @ViewById(R.id.photo)
//    ImageView photoView;

    @ViewById(R.id.checkbox)
    CheckBox checkBox;

    private Exhibit exhibit;

    private ExhibitListView.ExhibitListListener listener;

    @RestService
    ExhibitManager exhibitManager;

    public ExhibitListItemView(Context context) {
        super(context);
    }

    public ExhibitListItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ExhibitListItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @DebugLog
    public void bind(Exhibit exhibit) {
        this.exhibit = exhibit;
//        photoView.setImageBitmap(exhibit.getMainImage());
        nameView.setText(exhibit.getName());
        descriptionView.setText(exhibit.getDescription());
        Glide.with(getContext()).load(exhibit.getMainImage().getUri()).into(photoView);
}

    @Override
    public void setChecked(boolean b) {
        checkBox.setChecked(b);
    }

    @Override
    public boolean isChecked() {
        return checkBox.isChecked();
    }

    @Override
    public void toggle() {
        checkBox.setChecked(!isChecked());
    }

    @Click({R.id.name, R.id.description})
    void onClick() {
        if (listener != null) {
            listener.onExhibitItemClick(exhibit);
        }
    }

    public void setOnClickListener(ExhibitListView.ExhibitListListener l) {
        this.listener = l;
    }
}
