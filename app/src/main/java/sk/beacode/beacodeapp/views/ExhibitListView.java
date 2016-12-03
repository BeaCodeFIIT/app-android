package sk.beacode.beacodeapp.views;

import android.content.Context;
import android.util.AttributeSet;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

import sk.beacode.beacodeapp.R;
import sk.beacode.beacodeapp.adapters.ExhibitAdapter;
import sk.beacode.beacodeapp.models.Exhibit;

@EViewGroup(R.layout.view_exhibit_list)
public class ExhibitListView extends LinearLayout {

    public interface ExhibitListListener {
        void onExhibitItemClick(Exhibit exhibit);
    }

    @ViewById(R.id.exhibits)
    ListView exhibitList;

    @ViewById(R.id.start_tour)
    Button startTourButton;

    private ExhibitAdapter adapter;

    public ExhibitListView(Context context) {
        super(context);
    }

    public ExhibitListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ExhibitListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @AfterViews
    void initViews() {
        adapter = new ExhibitAdapter(getContext(), 0);
        exhibitList.setAdapter(adapter);
    }

    public void bind(List<Exhibit> exhibitions) {
        if (exhibitions != null) {
            adapter.addAll(exhibitions);
        }
    }

    @Click(R.id.select_all_exhibits)
    void onSelectAll() {
        int all = exhibitList.getCount();
        int checked = exhibitList.getCheckedItemCount();
        for (int i = 0; i < all; ++i) {
            exhibitList.setItemChecked(i, checked < all);
        }
    }

    List<Exhibit> getCheckedExhibits() {
        SparseBooleanArray checked = exhibitList.getCheckedItemPositions();
        List<Exhibit> checkedExhibits = new ArrayList<>();

        for (int i = 0; i < adapter.getCount(); ++i) {
            if (checked.get(i)) {
                checkedExhibits.add(adapter.getItem(i));
            }
        }

        return checkedExhibits;
    }

    public void setStartTourListener(OnClickListener l) {
        startTourButton.setOnClickListener(l);
    }

    public void setExhibitOnClickListener(ExhibitListListener l) {
        this.adapter.setOnClickListener(l);
    }
}
