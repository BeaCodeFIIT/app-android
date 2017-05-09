package sk.beacode.beacodeapp.adapters;

import android.app.FragmentManager;
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

import com.bumptech.glide.Glide;

import org.androidannotations.annotations.UiThread;

import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import hugo.weaving.DebugLog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sk.beacode.beacodeapp.R;
import sk.beacode.beacodeapp.activities.EventActivity_;
import sk.beacode.beacodeapp.fragments.ExhibitionDetailDialog;
import sk.beacode.beacodeapp.managers.Manager;
import sk.beacode.beacodeapp.managers.SelectedExhibitsApi;
import sk.beacode.beacodeapp.models.AddSelectedExhibit;
import sk.beacode.beacodeapp.models.Category;
import sk.beacode.beacodeapp.models.Exhibit;
import sk.beacode.beacodeapp.models.Image;
import sk.beacode.beacodeapp.models.SelectedExhibit;
import sk.beacode.beacodeapp.models.SelectedExhibitList;

/**
 * Adapter for displaying categorized exhibits in the EventActivity.
 */
public class CategoryAdapter extends BaseExpandableListAdapter {

    FragmentManager fragmentManager;

    public static final class GroupViewHolder {

        Category mItem;
        View mView;
        @BindView(R.id.name)
        TextView mName;

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
    //private List<List<Boolean>> checked_exibits = new ArrayList<>();
    //private List<Exhibit> checkedExhibitsApi = new ArrayList<>();

    public CategoryAdapter(FragmentManager fragmentManager, Context context, List<Category> items) {
        mItems = items;
        mInflater = LayoutInflater.from(context);
        this.fragmentManager = fragmentManager;

//        SelectedExhibitsApi api = Manager.getInstance().getSelectedExhibitsApi();
//
//        for (Category c : items) {
//            Call<ExhibitList> call = api.getSelectedExhibits(c.getId());
//            call.enqueue(new Callback<ExhibitList>() {
//                @DebugLog
//                @Override
//                public void onResponse(Call<ExhibitList> call, Response<ExhibitList> response) {
//                    for (Exhibit e : response.body().getExhibits()) {
//                        checkedExhibitsApi.add(e);
//                    }
//                    notifyDataSetChanged();
//                }
//
//                @DebugLog
//                @Override
//                public void onFailure(Call<ExhibitList> call, Throwable t) {
//
//                }
//            });
//        }
//        for (int j = 0; j < mItems.size(); j++) {
//            List<Boolean> checked = new ArrayList<>(Arrays.asList(new Boolean[mItems.get(j).getExhibits().size()]));
//            Collections.fill(checked, Boolean.FALSE);
//            checked_exibits.add(checked);
//        }
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
    @DebugLog
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
//        ((CheckBox) view.findViewById(R.id.checkbox)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if (((CheckBox) finalView.findViewById(R.id.checkbox)).isChecked()) {
//                    checked_exibits.get(i).set(i1, true);
//                } else {
//                    checked_exibits.get(i).set(i1, false);
//                }
//            }
//        });

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
                date_from = "Today";
            } else {
                date_from = dayFrom + ". " + stringMonthFrom + ", " + hourTodayFrom + ":" + minTodayFrom + "   -";
            }

            if (stringMonthTo.equals(stringMonthToday) && dayTo.equals(dayToday)) {
                date_to = "Today";
            } else {
                date_to = dayTo + ". " + stringMonthTo + ", " + hourTodayTo + ":" + minTodayTo;
            }

            ((TextView) view.findViewById(R.id.exibit_start)).setText(date_from);
            ((TextView) view.findViewById(R.id.exibit_end)).setText(date_to);
        }

        final SelectedExhibit selectedExhibit = new SelectedExhibit();
        SelectedExhibitsApi api = Manager.getInstance().getSelectedExhibitsApi();
        Call<SelectedExhibitList> call = api.getSelectedExhibits(EventActivity_.getEvent().getId());
        call.enqueue(new Callback<SelectedExhibitList>() {
            @Override
            @DebugLog
            public void onResponse(Call<SelectedExhibitList> call, Response<SelectedExhibitList> response) {
                ((CheckBox) finalView.findViewById(R.id.checkbox)).setOnCheckedChangeListener(null);
                System.out.println("abcdef" + response.body().getSelectedExhibits().size());
                for (SelectedExhibit e : response.body().getSelectedExhibits()) {
                    System.out.println("xxx" + e.getExhibit().getName());
                    if (e.getExhibit().getId() == holder.mItem.getId()) {
                        ((CheckBox) finalView.findViewById(R.id.checkbox)).setChecked(true);
                        selectedExhibit.setId(e.getId());
                        break;
                    } else {
                        ((CheckBox) finalView.findViewById(R.id.checkbox)).setChecked(false);
                    }
                }

                ((CheckBox) finalView.findViewById(R.id.checkbox)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                        SelectedExhibitsApi api = Manager.getInstance().getSelectedExhibitsApi();
                        Call<Void> call;
                        System.out.println("xxx event" + EventActivity_.getEvent().getId());
                        System.out.println("xxx exhibit" + holder.mItem.getId());
                        if (checked) {
                            System.out.println("xxx add");
                            call = api.addSelectedExhibit(EventActivity_.getEvent().getId(), new AddSelectedExhibit(holder.mItem.getId()));
                            //checkedExhibitsApi.add(holder.mItem);
                        } else {
                            System.out.println("xxx delete");

                            call = api.deleteSelectedExhibit(EventActivity_.getEvent().getId(), selectedExhibit.getId());
                            //checkedExhibitsApi.remove(holder.mItem);
                        }
                        call.enqueue(new Callback<Void>() {
                            @DebugLog
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {

                            }

                            @DebugLog
                            @Override
                            public void onFailure(Call<Void> call, Throwable t) {

                            }
                        });
                    }
                });
            }

            @Override
            @DebugLog
            public void onFailure(Call<SelectedExhibitList> call, Throwable t) {

            }
        });

//        if (checked_exibits.get(i) != null && checked_exibits.get(i).get(i1)) {
//            ((CheckBox) finalView.findViewById(R.id.checkbox)).setChecked(true);
//        } else {
//            ((CheckBox) finalView.findViewById(R.id.checkbox)).setChecked(false);
//        }

        ((TextView) view.findViewById(R.id.description)).setText(holder.mItem.getDescription());
        ((TextView) view.findViewById(R.id.name)).setText(holder.mItem.getName());
        Image mainImage = holder.mItem.getMainImage();
        if (mainImage != null) {
            ImageView photoView = (ImageView) view.findViewById(R.id.exhibit_image);
            Glide.with(photoView.getContext()).load(mainImage.getUri()).into(photoView);
        }

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExhibitionDetailDialog dialog = new ExhibitionDetailDialog();
                dialog.bind(holder.mItem);
                try {
                    dialog.show(fragmentManager, "");
                } catch (IllegalStateException e) {
                    e.printStackTrace();
                }
            }
        });

        return view;
    }


    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
}
