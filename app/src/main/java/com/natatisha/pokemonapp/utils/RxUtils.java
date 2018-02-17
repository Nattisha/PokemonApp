package com.natatisha.pokemonapp.utils;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class RxUtils {

    public static <T> Observable<T> wrapAsync(Observable<T> observable) {
        return wrapAsync(observable, Schedulers.io());
    }

    public static <T> Flowable<T> wrapAsync(Flowable<T> flowable) {
        return wrapAsync(flowable, Schedulers.io());
    }

    public static <T> Single<T> wrapAsync(Single<T> single) {
        return wrapAsync(single, Schedulers.io());
    }

    public static Completable wrapAsync(Completable completable) {
        return wrapAsync(completable, Schedulers.io());
    }

    public static <T> Observable<T> wrapAsync(Observable<T> observable, Scheduler scheduler) {
        return observable
                .subscribeOn(scheduler)
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static <T> Flowable<T> wrapAsync(Flowable<T> flowable, Scheduler scheduler) {
        return flowable
                .subscribeOn(scheduler)
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static Completable wrapAsync(Completable completable, Scheduler scheduler) {
        return completable
                .subscribeOn(scheduler)
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static <T> Single<T> wrapAsync(Single<T> single, Scheduler scheduler) {
        return single
                .subscribeOn(scheduler)
                .observeOn(AndroidSchedulers.mainThread());
    }
}