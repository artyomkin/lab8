package common;

import common.dataTransferObjects.FlatTransferObject;

import java.io.Serializable;
import java.util.ArrayList;

public class Response implements Serializable {
    private static final long serialVersionUID = 2L;
    private String additionalInfo;
    private ArrayList<FlatTransferObject> collectionInfo;
    private boolean executionFailed;
    private Instruction instruction;
    private Stage stage;
    private Query query;

    public Response(){
        this.additionalInfo = "";
        this.collectionInfo = new ArrayList();
        this.executionFailed = false;
        this.instruction = Instruction.ASK_COMMAND;
        this.stage = Stage.BEGINNING;
        this.query = new Query();
    }
    public Response(ArrayList<FlatTransferObject> collectionInfo, String additionalInfo, boolean failed, Instruction instruction){
        this.additionalInfo = additionalInfo;
        this.collectionInfo = collectionInfo;
        this.executionFailed = failed;
        this.instruction = instruction;
        this.stage = Stage.BEGINNING;
        this.query = new Query();
    }

    public Response(String additionalInfo, boolean failed, Instruction instruction){
        this.additionalInfo = additionalInfo;
        this.collectionInfo = new ArrayList();
        this.executionFailed = failed;
        this.instruction = instruction;
        this.stage = Stage.BEGINNING;
        this.query = new Query();
    }

    public Response(ArrayList<FlatTransferObject> collectionInfo, boolean failed, Instruction instruction){
        this.additionalInfo = "";
        this.collectionInfo = collectionInfo;
        this.executionFailed = failed;
        this.instruction = instruction;
        this.stage = Stage.BEGINNING;
        this.query = new Query();
    }

    public Response(boolean failed, Instruction instruction, Stage stage, Query query){
        this.executionFailed = failed;
        this.instruction = instruction;
        this.stage = stage;
        this.query = query;
    }
    public Response(ArrayList<FlatTransferObject> collectionInfo, boolean failed, Instruction instruction, Stage stage, Query query){
        this.collectionInfo = collectionInfo;
        this.executionFailed = failed;
        this.instruction = instruction;
        this.stage = stage;
        this.query = query;
    }

    public Instruction getInstruction() {
        return instruction;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }
    public ArrayList<FlatTransferObject> getCollectionInfo() {
        return collectionInfo;
    }


    public boolean failed() {
        return executionFailed;
    }

    public Query getQuery() { return query; }

    public Response setQuery(Query query){
        this.query = query;
        return this;
    }

    public Response setAdditionalInfo(String additionalInfo){
        this.additionalInfo = additionalInfo;
        return this;
    }

    public Response setCollectionInfo(ArrayList<FlatTransferObject> collectionInfo){
        this.collectionInfo = collectionInfo;
        return this;
    }
    public Response setExecutionFailed(boolean flag){
        this.executionFailed = flag;
        return this;
    }
    public Response setInstruction(Instruction instruction){
        this.instruction = instruction;
        return this;
    }
}
