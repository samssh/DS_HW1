package ds_hw.model;

import ds_hw.list.LList;

public class QuestionVector {
    public int depth;
    public LList<String> questions;
    public LList<Relation> vector;

    public QuestionVector() {
        questions = new LList<>();
        vector = new LList<>();
    }
}

