package ro.pub.cs.systems.eim.practicaltest01.service;

import android.content.Context;
import android.content.Intent;
import android.os.Process;
import android.util.Log;

import java.util.Random;

import ro.pub.cs.systems.eim.practicaltest01.general.Constants;

/**
 * Created by Ana-Maria on 4/2/2018.
 */

public class ProcessingThread extends Thread {


    private Context context = null;
    private boolean isRunning = true;
    private Random random = new Random();
    private String answerText;

    public ProcessingThread(Context context, String answerText) {
        this.context = context;
        this.answerText = answerText;
    }

    @Override
    public void run() {
        Log.d(Constants.PROCESSING_THREAD_TAG, "Thread has started! PID: " + Process.myPid() + " TID: " + Process.myTid());
        System.out.println("in thread lucrez cu " + answerText);
        while (isRunning) {
            int position = random.nextInt(answerText.length());
            char[] chars = answerText.toCharArray();
            for(int i = 0; i < answerText.length(); i++){
                if(i != position) {
                    chars[i] = '*';
                }
            }
            String sendText = new String(chars);
            Intent intent = new Intent();
            intent.setAction(Constants.ACTION);
            intent.putExtra(Constants.BROADCAST_RECEIVER_EXTRA, sendText);
            context.sendBroadcast(intent);
            System.out.println("am trimis broadcast " + sendText);
            Log.d("[ProcessingThread]", "Broadcast sent " + sendText);
            sleep();
        }
        Log.d(Constants.PROCESSING_THREAD_TAG, "Thread has stopped!");
    }


    private void sleep() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }
    }

    public void stopThread() {
        isRunning = false;
    }
}
