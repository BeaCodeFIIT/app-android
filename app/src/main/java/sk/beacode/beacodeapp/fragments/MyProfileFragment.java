package sk.beacode.beacodeapp.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.cunoraz.tagview.Tag;
import com.cunoraz.tagview.TagView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;

import sk.beacode.beacodeapp.R;

@EFragment(R.layout.fragment_my_profile)
public class MyProfileFragment extends Fragment {

    ArrayList<Tag> tags = new ArrayList<>();
    @ViewById(R.id.profile_picture)
    ImageView profile_image;

    @ViewById(R.id.user_name)
    TextView userName;

    @ViewById(R.id.tag_group)
    TagView tagGroup;

    @ViewById(R.id.btn_add_interest)
    Button btnAddInterest;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(R.string.title_fragment_my_profile);
        }
        return null;
    }

    @AfterViews
    void initViews() {
        this.getView().setBackgroundColor(Color.WHITE);
        setListenerProperties();
        profile_image.setImageResource(R.drawable.my_profile_picture);
        userName.setText("Michal Majorsky");
        tagGroup.addTags(getTags());
    }

    public ArrayList<Tag> getTags(){

        Tag myTag1 = new Tag("Cars");
        Tag myTag2 = new Tag("Technology");
        Tag myTag3 = new Tag("Nature");
        Tag myTag4 = new Tag("Computers");
        Tag myTag5 = new Tag("Food");
        Tag myTag6 = new Tag("Programming");
        tags.add(myTag1);
        tags.add(myTag2);
        tags.add(myTag3);
        tags.add(myTag4);
        tags.add(myTag5);
        tags.add(myTag6);
        for (int i = 0; i < tags.size(); i++){
            tags.get(i).deleteIndicatorColor = Color.GRAY;
            tags.get(i).layoutColor = Color.WHITE;
            tags.get(i).layoutBorderSize = 1;
            tags.get(i).layoutBorderColor = Color.GRAY;
            tags.get(i).tagTextColor = Color.GRAY;
            tags.get(i).radius = 20;
            tags.get(i).isDeletable = true;

        }

        return tags;
    }

    public void setListenerProperties(){

        //set click listener
        tagGroup.setOnTagClickListener(new TagView.OnTagClickListener() {
            @Override
            public void onTagClick(Tag tag, int position) {

            }
        });

        //set delete listener
        tagGroup.setOnTagDeleteListener(new TagView.OnTagDeleteListener() {
            @Override
            public void onTagDeleted(final TagView view, final Tag tag, final int position) {
                alertDeleteInterestDialog(view,tag,position);

            }
        });

        btnAddInterest.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                DialogFragment dialog = new DialogAddInterestFragment();
                dialog.show(getFragmentManager(),"DialogAdd");
            }
        });

    }


    public void alertDeleteInterestDialog(final TagView view, final Tag tag, final int position){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                getActivity());

        // set dialog message
        alertDialogBuilder
                .setMessage("Are you sure you want to delete this interest?")
                .setCancelable(false)
                .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        for (int i = 0; i < tags.size(); i++){
                            if(tags.get(i) == tag){
                                tags.remove(i);
                                tagGroup.remove(position);
                            }

                        }
                    }
                })
                .setNegativeButton("No",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        dialog.cancel();
                    }
                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();
        // show it
        alertDialog.show();

    }


}
