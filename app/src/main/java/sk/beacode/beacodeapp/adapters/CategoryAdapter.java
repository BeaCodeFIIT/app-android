package sk.beacode.beacodeapp.adapters;

import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import sk.beacode.beacodeapp.R;
import sk.beacode.beacodeapp.models.Category;
import sk.beacode.beacodeapp.models.Exhibit;

public class CategoryAdapter extends BaseExpandableListAdapter {

    public static final class GroupViewHolder {

        Category mItem;
        View mView;
        @BindView(R.id.name) TextView mName;

        GroupViewHolder(View view) {
            mView = view;
            ButterKnife.bind(this, view);
        }
    }

    public static final class ChildViewHolder {
        Exhibit mItem;
        View mView;
        @BindView(R.id.name)
        TextView mName;
        @BindView(R.id.description)
        TextView mDescription;
        @BindView(R.id.exibit_start)
        TextView mstart;
        @BindView(R.id.exibit_end)
        TextView mEnd;
        @BindView(R.id.checkbox)
        CheckBox mCheckBox;

        ChildViewHolder(View view) {
            mView = view;
            ButterKnife.bind(this, view);

        }
    }

    private final List<Category> mItems;
    private final LayoutInflater mInflater;
    private List<List<Boolean>> checked_exibits = new ArrayList<>();

    public CategoryAdapter(Context context, List<Category> items) {
        mItems = items;
        mInflater = LayoutInflater.from(context);
        for (int j=0; j < mItems.size(); j++) {
            List<Boolean> checked = new ArrayList<Boolean>(Arrays.asList(new Boolean[mItems.get(j).getExhibits().size()]));
            Collections.fill(checked, Boolean.FALSE);
            checked_exibits.add(checked);
        }
    }

    @Override
    public int getGroupCount() {
        return mItems.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return mItems.get(i).getExhibits().size();
    }

    @Override
    public Object getGroup(int i) {
        return mItems.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return mItems.get(i).getExhibits().get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        GroupViewHolder holder;

        if (view == null) {
            view = mInflater.inflate(R.layout.item_exhibit_category, null);
            holder = new GroupViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (GroupViewHolder) view.getTag();
        }

        holder.mItem = mItems.get(i);
        ((TextView) view.findViewById(R.id.name)).setText(holder.mItem.getName());
        ((ImageView) view.findViewById(R.id.indicator)).setImageResource(b ? R.drawable.ic_up : R.drawable.ic_down);

        return view;
    }

    @Override
    public View getChildView(final int i, final int i1, boolean b, View view, ViewGroup viewGroup) {
        final ChildViewHolder holder;

        if (view == null) {
            view = mInflater.inflate(R.layout.item_exhibit, null);
            holder = new ChildViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ChildViewHolder) view.getTag();
        }

        final View finalView = view;
        ((CheckBox)view.findViewById(R.id.checkbox)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (((CheckBox) finalView.findViewById(R.id.checkbox)).isChecked()) {
                        checked_exibits.get(i).set(i1, true);
                    } else {
                        checked_exibits.get(i).set(i1, false);
                    }
                }
            });

        holder.mItem = mItems.get(i).getExhibits().get(i1);
        if (holder.mItem.getStart() != null && holder.mItem.getEnd() != null) {
            ((TextView) view.findViewById(R.id.name)).setText(holder.mItem.getName());
            ((TextView) view.findViewById(R.id.description)).setText(holder.mItem.getDescription());

            Calendar cal = Calendar.getInstance();
            String stringMonthToday = (String) DateFormat.format("MMMM", cal.getTime());
            String dayToday = (String) DateFormat.format("dd", cal.getTime());

            String stringMonthFrom = (String) DateFormat.format("MMMM", holder.mItem.getStart());
            String stringMonthTo = (String) DateFormat.format("MMMM", holder.mItem.getEnd());
            String dayFrom = (String) DateFormat.format("dd", holder.mItem.getStart());
            String dayTo = (String) DateFormat.format("dd", holder.mItem.getEnd());
            String hourTodayFrom = (String) DateFormat.format("HH", holder.mItem.getStart());
            String hourTodayTo = (String) DateFormat.format("HH", holder.mItem.getEnd());
            String minTodayFrom = (String) DateFormat.format("mm", holder.mItem.getStart());
            String minTodayTo = (String) DateFormat.format("mm", holder.mItem.getEnd());
            String date_from, date_to;

            if (stringMonthFrom.equals(stringMonthToday) && dayFrom.equals(dayToday)) {
                date_from = "Today"; // TODO: remove hardcoded string !
            } else {
                date_from = dayFrom + ". " + stringMonthFrom + ", " + hourTodayFrom + ":" + minTodayFrom + "   -";
            }

            if (stringMonthTo.equals(stringMonthToday) && dayTo.equals(dayToday)) {
                date_to = "Today"; // TODO: remove hardcoded string !
            } else {
                date_to = dayTo + ". " + stringMonthTo + ", " + hourTodayTo + ":" + minTodayTo;
            }

            ((TextView) view.findViewById(R.id.exibit_start)).setText(date_from);
            ((TextView) view.findViewById(R.id.exibit_end)).setText(date_to);
        }

        if (checked_exibits.get(i).get(i1)) {
            ((CheckBox) finalView.findViewById(R.id.checkbox)).setChecked(true);
        } else {
            ((CheckBox) finalView.findViewById(R.id.checkbox)).setChecked(false);
        }

        return view;
    }


    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
}
