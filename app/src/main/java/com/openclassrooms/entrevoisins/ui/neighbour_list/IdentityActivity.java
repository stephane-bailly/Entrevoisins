package com.openclassrooms.entrevoisins.ui.neighbour_list;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.annotation.LongDef;
import android.support.design.button.MaterialButton;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.events.DeleteNeighbourEvent;
import com.openclassrooms.entrevoisins.events.ShowNeighbourDetailsEvent;
import com.openclassrooms.entrevoisins.events.SwitchNeighbourFavoriteStatusEvent;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.service.NeighbourApiService;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class IdentityActivity extends AppCompatActivity {
    Neighbour neighbour;
    private NeighbourApiService mApiService;
    private  Menu menu;
    private boolean favoris;

    @BindView(R.id.textView_NomVoisin)
    TextView nomVoisin;

    @BindView(R.id.textView_NomPhoto)
    TextView nomPhoto;

    @BindView(R.id.textView_Adresse)
    TextView adresse;

    @BindView(R.id.textView_Telephone)
    TextView telephone;

    @BindView(R.id.textView_Message)
    TextView message;

    @BindView(R.id.textView_Facebook)
    TextView facebook;

    @BindView(R.id.imageView_Avatar)
    ImageView avatar;

    @BindView(R.id.floatingActionButton_AddToFavorite)
    FloatingActionButton favoriteButton;

    @BindView(R.id.app_bar)
    AppBarLayout mAppBarLayout;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identity);
        ButterKnife.bind(this);
        mApiService = DI.getNeighbourApiService();
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        // on récupère l'objet Neighbour sur lequel on a cliqué a partir de l'intent

        neighbour = (Neighbour) Objects.requireNonNull(getIntent().getExtras()).getParcelable("Neighbour");
        favoris = (Boolean)neighbour.isFavorite();
        if(favoris){
            favoriteButton.setImageResource(R.drawable.ic_star_yellow_24dp);
        }
        else {
            favoriteButton.setImageResource(R.drawable.ic_star_border_yellow_24dp);
        }
        nomPhoto.setText(neighbour.getName());
        nomVoisin.setText(neighbour.getName());
        adresse.setText(neighbour.getAddress());
        telephone.setText(neighbour.getPhoneNumber());
        facebook.setText("www.facebook.fr/" + neighbour.getName());
        message.setText(neighbour.getAboutMe());
        String url = neighbour.getAvatarUrl();
        Glide.with(this).load(url).into(avatar);

        favoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().postSticky(new SwitchNeighbourFavoriteStatusEvent(neighbour));

            }
        });

        mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {

                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    isShow = true;
                    showOption(R.id.action_Favorite);
                } else if (isShow) {
                    isShow = false;
                    hideOption(R.id.action_Favorite);
                }
            }
        });

    }

    @SuppressLint("ResourceType")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        this.menu = menu;
        getMenuInflater().inflate(R.layout.menu_scrolling, menu);
        if(favoris){
            menu.findItem(R.id.action_Favorite).setIcon(R.drawable.ic_star_yellow_24dp);
        }
        else{
            menu.findItem(R.id.action_Favorite).setIcon(R.drawable.ic_star_border_yellow_24dp);
        }

        hideOption(R.id.action_Favorite);
            return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_Favorite) {
            EventBus.getDefault().postSticky(new SwitchNeighbourFavoriteStatusEvent(neighbour));
            return true;
        }
        else{
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    private void hideOption(int id) {
        MenuItem item = menu.findItem(id);
        item.setVisible(false);
    }

    private void showOption(int id) {
        MenuItem item = menu.findItem(id);
        item.setVisible(true);
    }


    @Subscribe
    public void onSwitchNeighbourFavoriteStatusEvent(SwitchNeighbourFavoriteStatusEvent event){
        // on switch le status du voisin sur lequel on a cliqué
        mApiService.SwitchNeighbourIsFavorite(neighbour);
        if(neighbour.isFavorite()){favoriteButton.setImageResource(R.drawable.ic_star_yellow_24dp);}
        else{favoriteButton.setImageResource(R.drawable.ic_star_border_yellow_24dp);}
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);

    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    // ferme l'activité, pour afficher l'activité parente
    public void fermer(View view) {
        finish();
    }

}