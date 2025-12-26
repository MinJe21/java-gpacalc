package gpacalc;

import java.util.ArrayList;
import java.util.Scanner;

public class Application {
    //scanner 호출
    private static final Scanner keyboard = new Scanner(System.in);

    //질문 및 입력받는 함수
    public static String inputValue(String question){
        System.out.println(question);
        return keyboard.nextLine();
    }

    //항목에 맞게 스필릿 후 저장
    public static void addSubjects(ArrayList<Gpa> list, String input, String majorType) {
        if(input == null || input.isEmpty()){
            throw new IllegalArgumentException("input is empty");
        }

        String[] subjects = input.split(",");

        for (String subject : subjects) {
            String[] parts = subject.split("-");
            if (parts.length != 3) {
                throw new IllegalArgumentException("subject part's number must be 3");
            }

            //className 유효성 검사
            String className = parts[0].trim();

            // credit 유효성 검사
            String creditString = parts[1].trim();
            int credit;
            try {
                credit = Integer.parseInt(creditString);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("credit part must be an integer");
            }
            if (credit < 1) {
                throw new IllegalArgumentException("학점은 1점 이상이어야 합니다. -> " + credit);
            }

            //gradeSymbol 유효성 검사
            String gradeSymbol = parts[2].trim();
            if (!Gpa.Grade.isValid(gradeSymbol)) {
                throw new IllegalArgumentException("gradeSymbol is invalid: " + gradeSymbol);
            }

            list.add(new Gpa(className, credit, gradeSymbol, majorType));
        }
    }

    //전체 credit 구하기
    public static int earnedCredits(ArrayList<Gpa> gpaList) {
        int allCredit = 0;
        for (Gpa gpa : gpaList) {
            if (gpa.getGrade().equals("F") || gpa.getGrade().equals("NP")) {
                continue;
            }

            allCredit += gpa.getCredit();
        }
        return allCredit;
    }

    //전체 평균 grade 구하기
    public static double averageGrade(ArrayList<Gpa> gpaList) {
        double allWeightedScore = 0;
        int allCredits = 0;

        for (Gpa gpa : gpaList) {
            if (gpa.getGrade().equals("P") || gpa.getGrade().equals("NP")) {
                continue;
            }

            double gradePoint = gpa.getGradePoint();
            int credit = gpa.getCredit();

            allWeightedScore += (gradePoint * credit);
            allCredits += credit;
        }

        if (allCredits == 0) return 0.0;

        return allWeightedScore / allCredits;
    }

    //전공 평균 grade 구하기
    public static double majorAverageGrade(ArrayList<Gpa> gpaList) {
        double allMajorWeightedScore = 0;
        int allMajorCredits = 0;
        for (Gpa gpa : gpaList) {
            if (gpa.getGrade().equals("P") || gpa.getGrade().equals("NP") || gpa.getMajor().equals("[교양]")) {
                continue;
            }

            double gradePoint = gpa.getGradePoint();
            int credit = gpa.getCredit();

            allMajorWeightedScore += (gradePoint * credit);
            allMajorCredits += credit;
        }
        if (allMajorCredits == 0) return 0.0;
        return allMajorWeightedScore / allMajorCredits;
    }

    public static void main(String[] args) {
        ArrayList<Gpa> gpaList = new ArrayList<>();
        try {
            // 전공 입력
            String inputMajor = inputValue("전공 과목명과 이수학점, 평점을 입력해주세요(예시: 프로그래밍언어론-3-A+,소프트웨어공학-3-B+): ");
            addSubjects(gpaList, inputMajor, "[전공]");

            System.out.println();

            // 교양 입력
            String inputGeneralEducation = inputValue("교양 과목명과 이수학점, 평점을 입력해주세요(예시: 선형대수학-3-C0,인간관계와자기성장-3-P): ");
            addSubjects(gpaList, inputGeneralEducation, "[교양]");

            // 과목목록 출력
            System.out.println("<과목 목록>");
            for (Gpa gpa : gpaList) {
                System.out.println(gpa.toString());
            }

            // 취득학점 출력
            System.out.println("<취득학점>");
            System.out.println(earnedCredits(gpaList) + "학점");

            // 평점평균 출력
            System.out.println("<평점평균>");
            System.out.println(String.format("%.2f", averageGrade(gpaList)) + " / 4.5");

            // 전공 평점평균 출력
            System.out.println("<전공 평점평균>");
            System.out.println(String.format("%.2f", majorAverageGrade(gpaList)) + " / 4.5");

        } catch (IllegalArgumentException e) {
            System.out.println("\n[오류] " + e.getMessage());
            System.out.println("프로그램을 종료합니다.");
            return;
        }
    }
}
