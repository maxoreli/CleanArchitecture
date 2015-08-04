package io.patrykpoborca.cleanarchitecture.ui.MVVM;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.patrykpoborca.cleanarchitecture.CleanArchitectureApplication;
import io.patrykpoborca.cleanarchitecture.R;
import io.patrykpoborca.cleanarchitecture.dagger.components.DaggerActivityInjectorComponent;
import io.patrykpoborca.cleanarchitecture.dagger.components.DaggerApplicationComponent;
import io.patrykpoborca.cleanarchitecture.ui.MVPIC.models.UserProfile;
import io.patrykpoborca.cleanarchitecture.ui.MVVM.base.BaseViewModel;
import io.patrykpoborca.cleanarchitecture.ui.MVVM.base.BaseViewModelActivity;
import io.patrykpoborca.cleanarchitecture.dagger.components.ActivityInjectorComponent;

/**
 * Created by Patryk on 7/27/2015.
 */
public class MainActivityMVVM extends BaseViewModelActivity<MainViewModel> {

    @Bind(R.id.fetch_tweet_button)
    Button fetchTweetButton;

    @Bind(R.id.fetch_last_two_tweets)
    Button fetchLastTwoButton;

    @Bind(R.id.current_tweet)
    TextView currentTweetTextView;

    @Bind(R.id.past_tweets_container)
    LinearLayout pastTweetContainer;

    @Bind(R.id.user_login_button)
    Button loginButton;

    @Bind(R.id.user_name)
    TextView userNameTextView;

    @Bind(R.id.user_password)
    TextView userPasswordTextView;

    @Inject
    MainViewModel viewModel;
    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(view == loginButton){
                registerSubscription(
                        getViewModel().login(userNameTextView.getText().toString(),
                        userPasswordTextView.getText().toString())
                        .subscribe(profile ->
                                Toast.makeText(MainActivityMVVM.this, profile.getFormattedCredentials(), Toast.LENGTH_LONG).show())
                        );
            }
            else if(view == fetchLastTwoButton){
                registerSubscription(
                        getViewModel().fetchPreviousTweets()
                        .subscribe(MainActivityMVVM.this::displayTweets)
                );
            }
            else if(view == fetchTweetButton){
                registerSubscription(
                        getViewModel().fetchCurrentTweet()
                        .subscribe(tweet -> currentTweetTextView.setText(tweet))
                );
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        this.fetchLastTwoButton.setOnClickListener(onClickListener);
        this.fetchTweetButton.setOnClickListener(onClickListener);
        this.loginButton.setOnClickListener(onClickListener);
    }

    @Override
    protected MainViewModel getViewModel() {
        if(viewModel == null){
//            .builder()
//                    .baseComponent(CleanArchitectureApplication.getBaseComponent())
//                    .twitterComponent(CleanArchitectureApplication.getTwitterAPIComponent())
//                    .build()
//                    .injectMainActivityMVVM(this);
        }

        return viewModel;
    }

    private void displayTweets(List<String> list) {
        pastTweetContainer.removeAllViews();

        for(int i= 0; i < list.size(); i++){
            TextView textView = new TextView(this);
            textView.setText(list.get(i));
            pastTweetContainer.addView(textView);
        }
    }
}
