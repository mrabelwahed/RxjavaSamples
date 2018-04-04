package com.example.mahmoud.caching;

import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.mahmoud.samples.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by mahmoud on 27/03/18.
 */

public class RoomPlayground extends AppCompatActivity {

    @BindView(R.id.user_name)
    TextView usernameTV;
    @BindView(R.id.user_name_input)
    EditText usernameInput;
    @BindView(R.id.user_bio_input)
    EditText userBioInput;
    @BindView(R.id.update_user)
    Button updateBtn;


    private ViewModelFactory mViewModelFactory;

    private UserViewModel mViewModel;

    private final CompositeDisposable mDisposable = new CompositeDisposable();

    private AppDB appDB;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_playground);
        ButterKnife.bind(this);

        mViewModelFactory = Injection.provideViewModelFactory(this);
        mViewModel = ViewModelProviders.of(this, mViewModelFactory)
                .get(UserViewModel.class);


    }

    @Override
    protected void onStart() {
        super.onStart();
        mDisposable.add(mViewModel.getUser()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(user -> usernameTV.setText(user.getUsername()+"\n"+user.getBio()),
                        throwable -> Log.d("error", throwable.getMessage())));
    }


    @Override
    protected void onStop() {
        super.onStop();
        mDisposable.clear();
    }

    @OnClick(R.id.update_user)
    public void onUpdateUserNameClicked() {
        String usrInput = usernameInput.getText().toString();
        String bioInput = userBioInput.getText().toString();
        updateBtn.setEnabled(false);
        updateBtn.setTextColor(Color.parseColor("#f7f7f7"));
        mDisposable.add(mViewModel.updateUserName(usrInput,bioInput)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(() -> {
                    updateBtn.setEnabled(true);
                    updateBtn.setTextColor(Color.parseColor("#000000"));
                }));
    }
}
