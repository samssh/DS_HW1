package ds_hw.input;

import ds_hw.list.LList;
import ds_hw.list.MyIterator;
import ds_hw.model.Person;
import ds_hw.model.Relation;
import ds_hw.model.SocialMedia;

import java.util.Comparator;

public class SocialMediaMerger {
    public SocialMedia mergeSocialMedia(SocialMedia sm1, SocialMedia sm2) {
        SocialMedia result = new SocialMedia();
        result.topicNames = mergeList(sm1.topicNames, sm2.topicNames, String::compareTo, (x, y) -> x);
        result.persons = mergeList(sm1.persons, sm2.persons, Comparator.comparing(p -> p.name), this::mergePerson);
        return result;
    }

    private Person mergePerson(Person p1, Person p2) {
        p2.topicRelations = mergeList(p1.topicRelations, p2.topicRelations, Comparator.comparing(tr -> tr.name)
                , this::mergeRelation);
        p2.friendlyRelation = mergeList(p1.friendlyRelation, p2.friendlyRelation
                , Comparator.comparing(fr -> fr.name), this::mergeRelation);
        return p2;
    }

    private Relation mergeRelation(Relation tr1, Relation tr2) {
        return new Relation(tr1.name, tr1.rate + tr2.rate);
    }

    private <T> LList<T> mergeList(LList<T> l1, LList<T> l2, Comparator<T> comparator, Merger<T> merger) {
        LList<T> result = new LList<>();
        result.addAll(l1);
        result.addAll(l2);
        result.sort(comparator);
        T last = null;
        for (MyIterator<T> i = result.iterator(); i.hasNext(); ) {
            T now = i.next();
            if (last != null && comparator.compare(now, last) == 0) {
                i.remove();
                i.set(merger.merge(now, last));
            }
            last = now;
        }
        return result;
    }
}
