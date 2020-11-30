package ds_hw.input;

import ds_hw.model.Person;
import ds_hw.model.Relation;
import ds_hw.model.SocialMedia;

import java.util.Iterator;
import java.util.Scanner;

public class SocialMediaBuilder {
    SocialMedia result = new SocialMedia();

    public SocialMedia build(Scanner scanner) {
        getTopicNames(scanner);
        getTopicRelation(scanner);
        getPersonRelation(scanner);
        return result;
    }

    private void getTopicNames(Scanner scanner) {
        int topicNumber = scanner.nextInt();
        for (int i = 0; i < topicNumber; i++) {
            result.topicNames.addLast(scanner.next());
        }
    }

    private void getTopicRelation(Scanner scanner) {
        int personNumber = scanner.nextInt();
        for (int i = 0; i < personNumber; i++) {
            Person person = new Person(scanner.next());
            int topicRelationNumber = scanner.nextInt();
            int lastIndex = 0;
            Iterator<String> iterator = result.topicNames.iterator();
            for (int j = 0; j < topicRelationNumber; j++) {
                String s = scanner.next();
                int d = s.indexOf(":");
                int index = Integer.parseInt(s.substring(0, d));
                double rate = Double.parseDouble(s.substring(d + 1));
                for (int k = lastIndex; k < index; k++) {
                    iterator.next();
                }
                Relation relation = new Relation(iterator.next(), rate);
                person.topicRelations.addLast(relation);
                lastIndex = index + 1;
            }
            result.persons.addLast(person);
        }
    }

    private void getPersonRelation(Scanner scanner) {
        int personNumber = scanner.nextInt();
        int lastIndex = 0;
        Iterator<Person> iterator = result.persons.iterator();
        for (int i = 0; i < personNumber; i++) {
            int personIndex = scanner.nextInt();
            for (int j = lastIndex; j < personIndex; j++) {
                iterator.next();
            }
            lastIndex = personIndex + 1;
            Person person = iterator.next();
            int lIndex = 0;
            Iterator<Person> iterator2 = result.persons.iterator();
            int relationNumber = scanner.nextInt();
            for (int j = 0; j < relationNumber; j++) {
                String s = scanner.next();
                int d = s.indexOf(":");
                int index = Integer.parseInt(s.substring(0, d));
                double rate = Double.parseDouble(s.substring(d + 1));
                for (int k = lIndex; k < index; k++) {
                    iterator2.next();
                }
                lIndex = index + 1;
                person.friendlyRelation.addLast(new Relation(iterator2.next().name, rate));
            }

        }
    }
}