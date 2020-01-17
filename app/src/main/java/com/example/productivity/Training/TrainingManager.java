package com.example.productivity.Training;

import com.example.productivity.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class TrainingManager {

    private final String trainingsKey = "com.example.productivity.TrainingListActivity.trainingKey";
    private final String templateWorkoutsKey = "com.example.productivity.TrainingListActivity.templateWorkoutsKey";

    private List<Training> trainingsLog;
    //todo
    private List<Training> templateWorkouts;

    private int currentTrainingId = 0;

    TrainingManager() {
        trainingsLog = (List<Training>) MainActivity.store.read(trainingsKey);

        if (trainingsLog == null) {
            trainingsLog = new ArrayList<>();
        }
        templateWorkouts = (List<Training>) MainActivity.store.read(templateWorkoutsKey);

        if (templateWorkouts == null) {
            templateWorkouts = new ArrayList<>();
        }
    }

    void startTraining(String name, TrainingType type) {
        startTraining(0);
        add(new Training(name, type));
    }

    void startTraining(int currentTrainingId) {
        this.currentTrainingId = currentTrainingId;
    }

    Training getTemplate() {
        String name = trainingsLog.get(currentTrainingId).getName();

        Training template = null;

        for (int i = currentTrainingId + 1; i < trainingsLog.size(); i++) {
            //todo better search
            if (trainingsLog.get(i).getName().equals(name)) {
                template = trainingsLog.get(i);
            }
        }

        return template;
    }

    void setCurrentTraining(Training training) {
        set(currentTrainingId, training);
    }

    Training getCurrentTrainingId() {
        return trainingsLog.get(currentTrainingId);
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
