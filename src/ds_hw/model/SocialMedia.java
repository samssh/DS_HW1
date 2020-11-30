package ds_hw.model;

import ds_hw.list.LList;

import java.util.Comparator;

public class SocialMedia {
    public LList<String> topicNames;
    public LList<Person> persons;

    public SocialMedia() {
        persons = new LList<>();
        topicNames = new LList<>();
    }

    public void sort() {
        topicNames.sort(String::compareTo);
        persons.sort(Comparator.comparing(p -> p.name));
        for (Person person : persons) {
            person.friendlyRelation.sort(Comparator.comparing(fr -> fr.name));
            person.topicRelations.sort(Comparator.comparing(tr -> tr.name));
        }
    }
}

