package com.example.dove0012.yupaopao.presenter;

import com.example.dove0012.yupaopao.bean.HttpResponeBean;
import com.example.dove0012.yupaopao.interfaces.GamesView;

/**
 * Created by liuzl on 2016/5/4.
 */
public class GamesPresenter extends HttpResponePresenter {
    private GamesView gamesView;
    private static GamesPresenter instance = null;

    private static synchronized void syncInit(GamesView gamesView) {
        if (instance == null) {
            instance = new GamesPresenter(gamesView);
        }
    }

    public static GamesPresenter getInstance(GamesView gamesView) {
        if (instance == null) {
            syncInit(gamesView);
        }
        instance.gamesView = gamesView;
        return instance;
    }

    private GamesPresenter(GamesView gamesView) {
        this.gamesView = gamesView;
    }

    public void getGames() {
        this.getRespone("God/games", new HttpResponePresenter.httpSubscriber<HttpResponeBean>() {
            @Override
            public void onStart() {
                gamesView.startGames();
            }

            public void mOnCompleted() {
                gamesView.gamesFinish();
            }

            public void mOnNext(HttpResponeBean respone) {
                gamesView.setGames(respone.getInfo());
            }
        });
    }
}