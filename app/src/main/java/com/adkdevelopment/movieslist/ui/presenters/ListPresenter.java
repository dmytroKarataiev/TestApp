/*
 * MIT License
 *
 * Copyright (c) 2016. Dmytro Karataiev
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */

package com.adkdevelopment.movieslist.ui.presenters;

import android.util.Log;

import com.adkdevelopment.movieslist.App;
import com.adkdevelopment.movieslist.data.remote.Results;
import com.adkdevelopment.movieslist.ui.base.BaseMvpPresenter;
import com.adkdevelopment.movieslist.ui.contracts.ListContract;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Presenter for the ListFragment, which performs all intermediary work.
 * Created by karataev on 9/15/16.
 */

public class ListPresenter
        extends BaseMvpPresenter<ListContract.View>
        implements ListContract.Presenter {

    private static final String TAG = ListPresenter.class.getSimpleName();

    private Subscription mSubscription;

    @Override
    public void fetchData() {
        checkViewAttached();
        getMvpView().showProgress(true);

        // TODO: 9/15/16 add pagination
        mSubscription = App.getApiManager().getMoviesService().getMoviesPopular(1)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<Results>() {
                    @Override
                    public void onCompleted() {
                        getMvpView().showProgress(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: ", e);
                        getMvpView().showError();
                    }

                    @Override
                    public void onNext(Results results) {
                        if (results.getResults() != null && results.getResults().size() > 0) {
                            getMvpView().showData(results.getResults());
                        } else {
                            getMvpView().showEmpty();
                        }
                    }
                });
    }

    @Override
    public void detachView() {
        super.detachView();
        if (mSubscription != null && !mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
    }

}
