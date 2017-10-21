package com.tuhua.conference.view.home;

import com.hyphenate.chat.EMClient;
import com.tuhua.conference.R;
import com.tuhua.conference.base.activity.BaseActivity;
import com.tuhua.conference.service.InitializationService;
import com.tuhua.conference.util.RxUtil;

import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;

/**
 * 首页
 */
public class MainActivity extends BaseActivity {

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initEventAndSimpleData() {
        InitializationService.start(this);
    }

    @OnClick(R.id.bt_register)
    public void register() {
        Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Boolean> observableEmitter) throws Exception {
                EMClient.getInstance().createAccount("790699763", "123yangtuhua");//同步方法
                observableEmitter.onNext(true);
            }
        }).compose(RxUtil.<Boolean>rxObservableHelper()).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {

            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {

            }
        });
    }
}
