package com.example.productivity.Training;

import com.example.productivity.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class TrainingManager {

    private final String trainingsKey = "com.example.productivity.TrainingListActivity.trainingKey";

    private List<Training> trainingsLog;
    private List<Training> templateWorkouts;

    private int currentTraining = 0;

    TrainingManager() {
        trainingsLog = (List<Training>) MainActivity.store.read(trainingsKey);

        if (trainingsLog == null) {
            trainingsLog = new ArrayList<>();
        }
    }

    void startTraining(String name, TrainingType type) {
        currentTraining = 0;
        add(new Training(name, type));
    }

    void setCurrentTrainingId(int currentTraining) {
        this.currentTraining = currentTraining;
    }

    public int getCurrentTrainingId() {
        return currentTraining;
    }

    void setCurrentTraining(Training training) {
        set(currentTraining, training);
    }

    Training getCurrentTraining() {
        return trainingsLog.get(currentTraining);
    }

    List<Training> getTrainings() {
        return trainingsLog;
    }

    public Training get(int index) {
        if (index < trainingsLog.size()) {
            return trainingsLog.get(index);
        } else {
            return null;
        }
    }

    private void add(Training training) {
        trainingsLog.add(0, training);
        write();
    }

    private void set(int index, Training training) {
        if (index < trainingsLog.size()) {
            trainingsLog.set(index, training);
            write();
        }
    }

    public int getSize() {
        return trainingsLog.size();
    }

    void remove(int index) {
        if (index < trainingsLog.size()) {
            trainingsLog.remove(index);
            write();
        }
    }

    private void write() {
        MainActivity.store.write(trainingsKey, trainingsLog);
    }
}
