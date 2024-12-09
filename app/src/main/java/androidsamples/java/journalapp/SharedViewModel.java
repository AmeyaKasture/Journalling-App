package androidsamples.java.journalapp;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SharedViewModel extends ViewModel {
    private final MutableLiveData<String> date ;
    private final MutableLiveData<String> startTime;
    private final MutableLiveData<String> endTime;
    private boolean startTimeClicked;

    SharedViewModel(){
        date = new MutableLiveData<>();
        startTime = new MutableLiveData<>();
        endTime = new MutableLiveData<>();
        startTimeClicked = true;
        date.setValue("DATE");
        startTime.setValue("START TIME");
        endTime.setValue("END TIME");
    }
    public void setDate(String date){
        this.date.setValue(date);
    }

    public void setTime(int h, int m){
        if(startTimeClicked) startTime.setValue(h + ":" + m);
        else endTime.setValue(h + ":" + m);
    }

    public void setStartTime(String time){
        startTime.setValue(time);
    }

    public void setEndTime(String time){
        endTime.setValue(time);
    }

    public void setTimeClicked(boolean start){
        startTimeClicked = start;
    }

    public MutableLiveData<String> getLiveDate(){
        return date;
    }

    public MutableLiveData<String> getLiveStartTime(){
        return startTime;
    }
    public MutableLiveData<String> getLiveEndTime(){
        return endTime;
    }

}
