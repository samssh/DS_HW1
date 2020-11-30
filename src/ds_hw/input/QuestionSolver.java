package ds_hw.input;

import ds_hw.list.LList;
import ds_hw.list.MyIterator;
import ds_hw.model.*;

import java.util.Comparator;
import java.util.Iterator;
import java.util.Scanner;

public class QuestionSolver {
    private QuestionVector questionVector;
    private final SocialMedia socialMedia;
    private final LList<Answer> answers;
    private final Scanner scanner;

    public QuestionSolver(SocialMedia socialMedia, Scanner scanner) {
        this.socialMedia = socialMedia;
        answers = new LList<>();
        this.scanner = scanner;
    }

    public LList<Answer> solve() {
        build();
        buildDepth0();
        check(0);
        for (int i = 0; i < questionVector.depth; i++) {
            multiply();
            check(i + 1);
        }
        answers.sort(Comparator.comparing(Answer::getName).thenComparing(Answer::getDepth));
        Answer last = null;
        for (MyIterator<Answer> i = answers.iterator(); i.hasNext(); ) {
            Answer now = i.next();
            if (last != null && now.name.equals(last.name)) {
                i.remove();
            }
            last = now;
        }
        answers.sort(this::compareAnswer);
        return answers;
    }

    private int compareAnswer(Answer ans1, Answer ans2) {
        if (ans1.depth == ans2.depth) {
            if (Math.abs(ans1.rate - ans2.rate) < 1e-20) {
                return ans1.name.compareTo(ans2.name);
            } else {
                return Double.compare(ans2.rate, ans1.rate);
            }
        } else {
            return Integer.compare(ans1.depth, ans2.depth);
        }
    }

    private void build() {
        questionVector = new QuestionVector();
        int topicNumber = scanner.nextInt();
        for (int i = 0; i < topicNumber; i++) {
            questionVector.questions.addLast(scanner.next());
        }
        questionVector.questions.sort(String::compareTo);
        questionVector.depth = scanner.nextInt();
    }

    private void buildDepth0() {
        for (Person person : socialMedia.persons) {
            Relation topicRelation = new Relation(person.name, 0);
            int topics = 0;
            for (Relation tr : person.topicRelations) {
                if (questionVector.questions.contains(String::equals, tr.name)) {
                    topicRelation.rate = topicRelation.rate + tr.rate;
                    topics++;
                }
            }
            if (topics == questionVector.questions.size() && Math.abs(topicRelation.rate) > 1e-20) {
                questionVector.vector.addLast(topicRelation);
            }
        }
    }

    private void check(int depth) {
        for (Relation tr : questionVector.vector) {
            if (Math.abs(tr.rate) > 1e-20) {
                answers.addLast(new Answer(tr.name, tr.rate, depth));
            }
        }
    }

    private void multiply() {
        LList<Relation> result = new LList<>();
        for (Person person : socialMedia.persons) {
            if (questionVector.vector.size() == 0 || person.friendlyRelation.size() == 0) continue;
            Iterator<Relation> i1 = questionVector.vector.iterator();
            Iterator<Relation> i2 = person.friendlyRelation.iterator();
            Relation res = new Relation(person.name, 0), tr = i1.next();
            Relation fr = i2.next();
            while (true) {
                int status = tr.name.compareTo(fr.name);
                if (status > 0) {
                    if (i2.hasNext()) {
                        fr = i2.next();
                    } else {
                        break;
                    }
                } else if (status == 0) {
                    res.rate += tr.rate * fr.rate;
                    if (i2.hasNext()) {
                        fr = i2.next();
                    } else {
                        break;
                    }
                    if (i1.hasNext()) {
                        tr = i1.next();
                    } else {
                        break;
                    }
                } else {
                    if (i1.hasNext()) {
                        tr = i1.next();
                    } else {
                        break;
                    }
                }
            }
            if (Math.abs(res.rate) > 1e-20) result.addLast(res);
        }
        questionVector.vector = result;
    }
}

