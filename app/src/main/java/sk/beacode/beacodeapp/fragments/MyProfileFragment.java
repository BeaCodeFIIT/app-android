package sk.beacode.beacodeapp.fragments;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.cunoraz.tagview.Tag;
import com.cunoraz.tagview.TagView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.rest.spring.annotations.RestService;

import java.util.ArrayList;

import sk.beacode.beacodeapp.R;
import sk.beacode.beacodeapp.managers.InterestManager;
import sk.beacode.beacodeapp.managers.UserManager;
import sk.beacode.beacodeapp.models.Interest;
import sk.beacode.beacodeapp.models.User;

@EFragment(R.layout.fragment_my_profile)
public class MyProfileFragment extends Fragment {

    @ViewById(R.id.profile_picture)
    ImageView profileImageView;

    @ViewById(R.id.user_name)
    TextView userNameView;

    @ViewById(R.id.tag_group)
    TagView tagGroup;

    @ViewById(R.id.btn_add_interest)
    Button btnAddInterest;

    @RestService
    InterestManager interestManager;

    @RestService
    UserManager userManager;

    public User user;

    public ArrayList<Tag> tags = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(R.string.title_fragment_my_profile);
        }
        getInterest();
        return null;
    }

    /**
     * Defining view components
     */
    @AfterViews
    void initViews() {
        getInterest();
        getView().setBackgroundColor(Color.WHITE);
        profileImageView.setImageBitmap(user.getImage());
        userNameView.setText(user.getFirstName() + " " + user.getLastName());
        tagGroup.addTags(getTags());
        profileImageView.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                ChangeProfilePhotoDialog dialog = new ChangeProfilePhotoDialog();
                dialog.setTargetFragment(MyProfileFragment.this,0);
                dialog.show(getFragmentManager(),"DialogChangeProfile");
            }

        });
        tagGroup.setOnTagDeleteListener(new TagView.OnTagDeleteListener() {
            @Override
            public void onTagDeleted(final TagView view, final Tag tag, final int position) {
                alertDeleteInterestDialog(view,tag,position);

            }
        });

        tagGroup.setOnTagLongClickListener(new TagView.OnTagLongClickListener() {
            @Override
            public void onTagLongClick(Tag tag, int position) {
                alertDeleteInterestDialog(null,tag,position);
            }
        });;

        btnAddInterest.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AddInterestDialog dialog = new AddInterestDialog();
                dialog.setTargetFragment(MyProfileFragment.this,0);
                dialog.show(getFragmentManager(), "DialogAdd");
            }
        });
    }

    @Background
    void getInterest(){
        user.setInterests(interestManager.getInterests().getInterests());
    }

    /**
     *
     * @return list of tags = list of interests
     */
    public ArrayList<Tag> getTags(){
        tags = new ArrayList<>();
        getInterest();
        for (int i = 0; i < user.getInterests().size(); i++){
            Tag myTag = new Tag(user.getInterests().get(i).getName());
            tags.add(myTag);
            setTagAttributes(tags.get(i));
        }
        return tags;
    }

    /**
     * After clicking the X icon on the tag, an alert dialog is shown
     * @param view
     * @param tag
     * @param position
     */
    public void alertDeleteInterestDialog(final TagView view, final Tag tag, final int position){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                getActivity());

        alertDialogBuilder
                .setMessage("Are you sure you want to delete this interest?")
                .setCancelable(false)
                .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        for (int i = 0; i < tags.size(); i++){
                            if(tags.get(i) == tag){
                                interestManager.deleteInterest(user.getInterests().get(i).getId());
                                user.getInterests().remove(i);
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

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLACK);
        alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.BLACK);
    }

    /**
     * Defining tag attributes of every tag in the tag list
     * @param myTag
     */
    public void setTagAttributes(Tag myTag){
        myTag.deleteIndicatorColor = Color.GRAY;
        myTag.layoutColor = Color.WHITE;
        myTag.layoutBorderSize = 1;
        myTag.layoutBorderColor = Color.GRAY;
        myTag.tagTextColor = Color.GRAY;
        myTag.radius = 20;
        myTag.isDeletable = true;
    }

    public void addInterest(Interest interest) {
        user.getInterests().add(interest);
        Tag myTag = new Tag(user.getInterests().get(user.getInterests().size() - 1).getName());
        myTag.deleteIndicatorColor = Color.GRAY;
        myTag.layoutColor = Color.WHITE;
        myTag.layoutBorderSize = 1;
        myTag.layoutBorderColor = Color.GRAY;
        myTag.tagTextColor = Color.GRAY;
        myTag.radius = 20;
        myTag.isDeletable = true;
        tagGroup.addTag(myTag);
        tags.add(myTag);
    }

    public void setPhoto(Bitmap photo) {
        user.setPhoto(photo);
        profileImageView.setImageBitmap(photo);
    }

    public void bind(User user) {
        this.user = user;
    }
}
