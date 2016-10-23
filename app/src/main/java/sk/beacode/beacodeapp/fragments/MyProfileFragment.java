package sk.beacode.beacodeapp.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.graphics.BitmapFactory;
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
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

import sk.beacode.beacodeapp.R;
import sk.beacode.beacodeapp.models.Interest;
import sk.beacode.beacodeapp.models.User;

@EFragment(R.layout.fragment_my_profile)
public class MyProfileFragment extends Fragment {

    @ViewById(R.id.profile_picture)
    static ImageView profileImageView;

    @ViewById(R.id.user_name)
    TextView userNameView;

    @ViewById(R.id.tag_group)
    static
    TagView tagGroup = null;

    @ViewById(R.id.btn_add_interest)
    Button btnAddInterest;

    public static User user;
    public static ArrayList<Tag> tags = new ArrayList<>();
    private boolean mIsCreated;


    /**
     * Method is called when the fragment has been attached
     * @param activity
     */
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (!mIsCreated){
            user = initzializeUser();
        }
        mIsCreated = true;
    }

    /**
     * Method is called when the fragment has been detached
     */
    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(R.string.title_fragment_my_profile);
        }

        return null;
    }

    /**
     * Defining view components
     */
    @AfterViews
    void initViews() {
        this.getView().setBackgroundColor(Color.WHITE);
        setListenerProperties();
        profileImageView.setImageBitmap(user.getPhoto());
        userNameView.setText(user.getName() + user.getSurname());
        tagGroup.addTags(getTags());

    }

    /**
     *
     * @return list of tags = list of interests
     */
    public ArrayList<Tag> getTags(){
        tags = new ArrayList<>();
        for (int i = 0; i < user.getInterests().size(); i++){
            Tag myTag = new Tag(user.getInterests().get(i).getName());
            tags.add(myTag);
            setTagAttributes(tags.get(i));
        }
        return tags;
    }

    /**
     * Adding listeners to some of the view components
     */
    public void setListenerProperties(){
        profileImageView.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                DialogChangeProfileFragment dialog = new DialogChangeProfileFragment();
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

        btnAddInterest.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                DialogAddInterestFragment dialog = new DialogAddInterestFragment();
                dialog.setTargetFragment(MyProfileFragment.this,0);
                dialog.show(getFragmentManager(),"DialogAdd");
            }
        });
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

    /**
     * The User is initialized
     * @return User object
     */
    private User initzializeUser(){
        User user = new User();
        Interest interest1 = new Interest();
        Interest interest2 = new Interest();
        Interest interest3 = new Interest();
        Interest interest4 = new Interest();
        Interest interest5 = new Interest();
        List<Interest> listOfInterests = new ArrayList<Interest>();

        interest1.setName("Cars");
        listOfInterests.add(interest1);
        interest2.setName("Technology");
        listOfInterests.add(interest2);
        interest3.setName("Computers");
        listOfInterests.add(interest3);
        interest4.setName("Nature");
        listOfInterests.add(interest4);
        interest5.setName("Food");
        listOfInterests.add(interest5);

        user.setName("Michal");
        user.setSurname("Moravksy");
        user.setInterests(listOfInterests);
        user.setPhoto(BitmapFactory.decodeResource(getResources(), R.drawable.my_profile_picture));

        return user;
    }
}
