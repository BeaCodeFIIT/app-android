package sk.beacode.beacodeapp.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.CheckBox;
import android.widget.Checkable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import sk.beacode.beacodeapp.R;
import sk.beacode.beacodeapp.models.Exhibit;

@EViewGroup(R.layout.view_exhibit_list_item)
public class ExhibitListItemView extends LinearLayout implements Checkable {

    @ViewById(R.id.name)
    TextView nameView;

    @ViewById(R.id.description)
    TextView descriptionView;

    @ViewById(R.id.photo)
    ImageView photoView;

    @ViewById(R.id.check_box)
    CheckBox checkBox;

    public ExhibitListItemView(Context context) {
        super(context);
    }

    public ExhibitListItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ExhibitListItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void bind(Exhibit exhibit) {
        photoView.setImageBitmap(exhibit.getPhoto());
        nameView.setText(exhibit.getName());
        descriptionView.setText(exhibit.getDescription());
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
}
