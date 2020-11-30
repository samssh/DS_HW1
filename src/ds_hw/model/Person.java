package ds_hw.model;

import ds_hw.list.LList;

public class Person {
    public String name;
    public LList<Relation> topicRelations;
    public LList<Relation> friendlyRelation;

    public Person(String name) {
        this.name = name;
        topicRelations = new LList<>();
        friendlyRelation = new LList<>();
    }
}

