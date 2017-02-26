package sk.beacode.beacodeapp.adapters;

import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import sk.beacode.beacodeapp.R;
import sk.beacode.beacodeapp.models.Category;
import sk.beacode.beacodeapp.models.Exhibit;

public class CategoryAdapter extends BaseExpandableListAdapter {

    private static final class GroupViewHolder {

        Category mItem;
        View mView;
        @BindView(R.id.name) TextView mName;

        GroupViewHolder(View view) {
            mView = view;
            ButterKnife.bind(this, view);
        }
    }

    private static final class ChildViewHolder {
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

    public CategoryAdapter(Context context, List<Category> items) {
        mItems = items;
        mInflater = LayoutInflater.from(context);
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
        System.out.println("getGroupView " + Integer.toString(i));
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
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        System.out.println("getChildView " + Integer.toString(i));
        ChildViewHolder holder;

        if (view == null) {
            view = mInflater.inflate(R.layout.item_exhibit, null);
            holder = new ChildViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ChildViewHolder) view.getTag();
        }

        holder.mItem = mItems.get(i).getExhibits().get(i1);
        ((TextView) view.findViewById(R.id.name)).setText(holder.mItem.getName());
        ((TextView) view.findViewById(R.id.description)).setText(holder.mItem.getDescription());
        ((TextView) view.findViewById(R.id.exibit_start)).setText(holder.mItem.getStart().toString());

        Calendar cal = Calendar.getInstance();
        String stringMonthToday = (String) DateFormat.format("MMMM", cal.getTime());
        String dayToday = (String) DateFormat.format("dd", cal.getTime()); //20

        String stringMonth = (String) DateFormat.format("MMMM", holder.mItem.getStart());
        String day = (String) DateFormat.format("dd", holder.mItem.getStart()); //20
        System.out.println();

        String hourToday = (String) DateFormat.format("HH", holder.mItem.getStart()); //20
        String minToday = (String) DateFormat.format("mm", holder.mItem.getStart()); //20
        String date;

        if (stringMonth.equals(stringMonthToday) && day.equals(dayToday)){
            date = "Today"; // TODO: remove hardcoded string !
        } else {
            date = day + ". " + stringMonth + " " + hourToday + " " + minToday;
        }
        ((TextView) view.findViewById(R.id.exibit_end)).setText(holder.mItem.getEnd().toString());

        return view;
    }


    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
}
