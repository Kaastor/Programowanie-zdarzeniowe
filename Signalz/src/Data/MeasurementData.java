package Data;

import java.util.LinkedList;
/**
 * Created by Przemek on 2016-03-02.
 */
public class MeasurementData {

    private LinkedList<LinkedList> measurementsList; //lista pomiarów
    private int[] iterators;

    public MeasurementData(int length) {
        this.measurementsList = new LinkedList<>();
        initMeasurementDataList(length);
    }

    public MeasurementData() {
        this.measurementsList = new LinkedList<>();
    }

    public LinkedList<LinkedList> getMeasurementsList() {
        return measurementsList;
    }

    public void initMeasurementDataList(int lenght) {
        for (int i = 0; i < lenght; i++) {
            measurementsList.add(new LinkedList());
        }
        iterators = new int[lenght];
    }

    public void addDataToMeasurementList(int index, double data) {
        measurementsList.get(index).add(data);
    }

    public void showMeasurementListData(int index) {
        for (int i = 0; i < measurementsList.get(index).size(); i++) {
            System.out.println(measurementsList.get(index).get(i));
        }
    }

    public void clearDataLists() {
        for (int i = 0; i < measurementsList.size(); i++) {
            measurementsList.get(i).clear();
        }
        measurementsList.clear();
    }

    public void setMeasurementsList(LinkedList<LinkedList> measurementsList) {
        this.measurementsList = measurementsList;
    }

    public int getAndIncIterator(int index) {
        return iterators[index]++; //??????????????moze byc zle
    }

    public void setIterator(int index, int data) {
        iterators[index] = data;
    }

    public int getIterator(int index) {
        return iterators[index];
    }

    public void resetIterators(){
        for(int i = 0; i< iterators.length ; i++){
            iterators[i] = 0;
        }
    }
}
