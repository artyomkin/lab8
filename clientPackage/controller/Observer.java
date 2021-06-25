package clientPackage.controller;

import common.Instruction;
import common.taskClasses.Flat;

import java.util.ArrayList;

public interface Observer {

    void update(Instruction instruction, Flat flat);

    void update(ArrayList<Flat> newData);

}
