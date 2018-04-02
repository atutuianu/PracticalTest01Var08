package ro.pub.cs.systems.eim.practicaltest01.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import ro.pub.cs.systems.eim.practicaltest01.general.Constants;

/**
 * Created by Ana-Maria on 4/2/2018.
 */

public class PracticalTest01Var08Service extends Service {

    ProcessingThread processingThread = null;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String answerText = intent.getStringExtra("answer");
        System.out.println("in serviciu am " + answerText);
        processingThread = new ProcessingThread(this, answerText);
        processingThread.start();
        return Service.START_REDELIVER_INTENT;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        processingThread.stopThread();
    }

}
