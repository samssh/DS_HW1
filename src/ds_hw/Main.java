package ds_hw;

import ds_hw.input.QuestionSolver;
import ds_hw.input.SocialMediaBuilder;
import ds_hw.input.SocialMediaMerger;
import ds_hw.list.LList;
import ds_hw.model.Answer;
import ds_hw.model.SocialMedia;

import java.io.PrintStream;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        PrintStream out = System.out;
        Scanner scanner = new Scanner(System.in);
        int socialMediaNumber = scanner.nextInt();
        LList<SocialMedia> socialMediaList = new LList<>();
        for (int i = 0; i < socialMediaNumber; i++) {
            socialMediaList.addLast(new SocialMediaBuilder().build(scanner));
        }
        SocialMedia socialMedia = socialMediaList.first();
        SocialMediaMerger merger = new SocialMediaMerger();
        if (1 < socialMediaList.size()) socialMedia = merger.mergeSocialMedia(socialMedia, socialMediaList.get(1));
        if (2 < socialMediaList.size()) socialMedia = merger.mergeSocialMedia(socialMedia, socialMediaList.get(2));
        int questionNumbers = scanner.nextInt();
        socialMedia.sort();
        for (int i = 0; i < questionNumbers; i++) {
            LList<Answer> answers = new QuestionSolver(socialMedia, scanner).solve();
            for (Answer answer : answers) out.println(answer);
        }
        out.flush();
    }
}
