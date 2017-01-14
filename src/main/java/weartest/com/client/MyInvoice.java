package weartest.com.client;

import android.os.Build;

import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.rey.material.widget.Button;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by alekseyg on 07/04/2016.
 */
public class MyInvoice {
    public int myCount;
    ProgressBar progress;
    private static MyInvoice invoice=null;
    int maxCount=100;
    HashMap<Integer,Integer> washCount;

    HashMap <Integer,Integer>ironCount;
    HashMap<Integer,Integer> dryWashCount;
    HashMap<Integer,Integer> capterCount;

    HashMap<Integer,TextView> vviron = new HashMap<>();
    HashMap<Integer,TextView> vvDryWash = new HashMap<>();
    HashMap<Integer,TextView> vvCapter = new HashMap<>();
    HashMap<Integer,TextView> vvWash = new HashMap<>();

    HashMap<Integer,Integer> totalInvoice = new HashMap<>();
    HashMap<String,String> coments ;

    HashMap<String,String> totalServiceName = new HashMap<>();
    Button getInvoice;
    int totalVolume = 0;
    public HashMap<Integer, Integer> getCapterCount() {
        return capterCount;
    }

    public void setCapterCount(HashMap<Integer, Integer> capterCount) {
        this.capterCount = capterCount;
    }

    public HashMap<Integer, TextView> getVvDryWash() {
        return vvDryWash;
    }

    public void setVvDryWash(HashMap<Integer, TextView> vvDryWash) {
        this.vvDryWash = vvDryWash;
    }

    public HashMap<Integer, TextView> getVvCapter() {
        return vvCapter;
    }

    public void setVvCapter(HashMap<Integer, TextView> vvCapter) {
        this.vvCapter = vvCapter;
    }

    public HashMap<Integer, TextView> getVviron() {
        return vviron;
    }

    public void setVviron(HashMap<Integer, TextView> vviron) {
        this.vviron = vviron;
    }


    public HashMap<Integer, TextView> getVvWash() {
        return vvWash;
    }

    public void setVvWash(HashMap<Integer, TextView> vvWash) {
        this.vvWash = vvWash;
    }


    public Button getGetInvoice() {
        return getInvoice;
    }

    public void setGetInvoice(Button getInvoice) {
        this.getInvoice = getInvoice;
    }

    public int getMaxCount() {
        return maxCount;
    }

    public void setMaxCount(int maxCount) {
        this.maxCount = maxCount;
    }

    public void setProgress(ProgressBar progress) {
        this.progress = progress;
    }

    public ProgressBar getProgress() {
        return progress;
    }

private MyInvoice()
{
   // if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
        ironCount = new HashMap<>();
        dryWashCount = new HashMap<>();
        capterCount = new HashMap<>();
        coments = new HashMap<>();
        washCount = new HashMap<>();

   // }
   
    /*ironCount.put(0);
    ironCount.add(0);
    dryWashCount.add(0);
    dryWashCount.add(0);*/

}

    public int getTotalVolume() {
        return totalVolume;
    }

    public void setTotalVolume(int totalVolume) {
        this.totalVolume = totalVolume;
    }

    public static void clearInvoice()
    {
        invoice=null;
    }
    public static MyInvoice newInstance() {

        if(invoice==null)invoice=new MyInvoice();
          return invoice;
    }

    public void setCount(int count)
    {

        myCount=count;
        if(myCount>0)
        {
            getInvoice.setVisibility(View.VISIBLE);
        }
        else{ getInvoice.setVisibility(View.GONE); }
    }
    public int getCount(){return myCount;}

    public HashMap<Integer,Integer> getWashCount() {
        return washCount;
    }

    public HashMap<Integer,Integer> getIronCount() {
        return ironCount;
    }

    public HashMap<Integer,Integer> getDryWashCount() {
        return dryWashCount;
    }


    public void setWashCount(HashMap<Integer,Integer> washCount) {
        this.washCount = washCount;
    }

    public void setIronCount(HashMap<Integer,Integer> ironCount) {
        this.ironCount = ironCount;
    }

    public void setDryWashCount(HashMap<Integer,Integer> dryWashCount) {
        this.dryWashCount = dryWashCount;
    }

    public HashMap<Integer, Integer> getTotalInvoice() {
        return totalInvoice;
    }

    public void setTotalInvoice(int id, int count) {
       totalInvoice.put(id,count);
    }



}


