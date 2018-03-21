package com.example.mahmoud.networking.state;

import com.example.mahmoud.networking.model.Repo;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.BehaviorSubject;

/**
 * Created by mahmoud on 21/03/18.
 */

public class ApiRequest {
    public BehaviorSubject<Long> trigger = BehaviorSubject.create();
    public BehaviorSubject<RequestState> state = BehaviorSubject.createDefault(RequestState.IDLE);
    public BehaviorSubject<Throwable> errors = BehaviorSubject.create();
    public BehaviorSubject<List<Repo>> repos = BehaviorSubject.create();

    public ApiRequest(Observable<List<Repo>> observable) {
        trigger.doOnNext(val -> state.onNext(RequestState.IDLE))
                .observeOn(Schedulers.io())
                .flatMap(trigger -> observable)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(throwable -> state.onNext(RequestState.ERROR))
                .onErrorResumeNext(Observable.empty())
                .doOnNext(val -> state.onNext(RequestState.COMPLETED))
                .subscribe(repos);
    }

   public void execute() {
        trigger.onNext(System.currentTimeMillis());
    }

}
