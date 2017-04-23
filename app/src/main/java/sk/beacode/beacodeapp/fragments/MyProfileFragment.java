package sk.beacode.beacodeapp.fragments;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cunoraz.tagview.Tag;
import com.cunoraz.tagview.TagView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

import sk.beacode.beacodeapp.R;
import sk.beacode.beacodeapp.models.Image;
import sk.beacode.beacodeapp.models.Interest;
import sk.beacode.beacodeapp.models.User;

@EFragment(R.layout.fragment_my_profile)
public class MyProfileFragment extends Fragment {

    public interface ProfileListener {
        void onChangeUserName(String firstName, String lastName);
        void onChangeUserPicture(Uri picture);
        void onAddInterest(Interest interest);
        void onDeleteInterest(Interest interest);
    }

    ProfileListener listener;

    private User user;

    @ViewById(R.id.picture)
    ImageView pictureView;

    @ViewById(R.id.name)
    TextView nameView;

    @ViewById(R.id.tags)
    TagView tagView;

    @ViewById(R.id.add_interest)
    Button addInterestButton;

    public void setProfileListener(ProfileListener listener) {
        this.listener = listener;
    }

    private Tag createTag(String name) {
        Tag t = new Tag(name);
        t.deleteIndicatorColor = Color.GRAY;
        t.layoutColor = Color.WHITE;
        t.layoutBorderSize = 1;
        t.layoutBorderColor = Color.GRAY;
        t.tagTextColor = Color.GRAY;
        t.radius = 20;
        t.isDeletable = true;
        return t;
    }

    @Click(R.id.picture)
    void onPictureViewClick() {
        ChangeProfilePhotoDialog dialog = new ChangeProfilePhotoDialog();
        dialog.setTargetFragment(this, 0);
        dialog.show(getFragmentManager(), null);
    }

    @Click(R.id.add_interest)
    void onAddInterestButtonClick() {
        AddInterestDialog dialog = new AddInterestDialog();
        dialog.setTargetFragment(this, 0);
        dialog.show(getFragmentManager(), null);
    }

    @AfterViews
    void initViews() {
        if (getActivity() != null) {
            ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
            if (actionBar != null) {
                actionBar.setTitle(R.string.title_fragment_my_profile);
                actionBar.show();
            }
        }

        if (user == null) {
            return;
        }

        Image image = user.getImage();
        if (pictureView != null && image != null) {
            Glide.with(this).load(image.getUri()).into(pictureView);
        }

        if (nameView != null) {
            nameView.setText(user.getFirstName() + " " + user.getLastName());
        }

        if (tagView != null) {
            List<Tag> tags = new ArrayList<>();
            for (Interest i : user.getInterests()) {
                tags.add(createTag(i.getName()));
            }
            tagView.addTags(tags);

            tagView.setOnTagDeleteListener(new TagView.OnTagDeleteListener() {
                @Override
                public void onTagDeleted(final TagView view, final Tag tag, final int position) {
                    showDeleteInterestDialog(tag);
                }
            });

            tagView.setOnTagLongClickListener(new TagView.OnTagLongClickListener() {
                @Override
                public void onTagLongClick(Tag tag, int position) {
                    showDeleteInterestDialog(tag);
                }
            });
        }
    }

    private void showDeleteInterestDialog(final Tag tag){
        new AlertDialog.Builder(getActivity())
                .setMessage("Are you sure you want to delete this interest?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        List<Interest> toDelete = new ArrayList<>();
                        for (Interest interest : user.getInterests()) {
                            if (interest.getName().equals(tag.text)) {
                                toDelete.add(interest);
                            }
                        }
                        for (Interest interest : toDelete) {
                            if (listener != null) {
                                listener.onDeleteInterest(interest);
                            }
                        }
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                })
                .create().show();
    }

    public void bind(User user) {
        this.user = user;
        initViews();
    }
}
