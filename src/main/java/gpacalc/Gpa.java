package gpacalc;

public class Gpa {
    private String className;
    private int credit;
    private String grade;
    private String major;

    //grade enum
    public enum Grade {
        A_PLUS("A+", 4.5),
        A0("A0", 4.0),
        B_PLUS("B+", 3.5),
        B0("B0", 3.0),
        C_PLUS("C+", 2.5),
        C0("C0", 2.0),
        D_PLUS("D+", 1.5),
        D0("D0", 1.0),
        F("F", 0.0),
        P("P", 0.0),
        NP("NP", 0.0);

        private final String symbol; // 성적 예시 (A+)
        private final double point;  // 점수 예시 (4.5)

        Grade(String symbol, double point) {
            this.symbol = symbol;
            this.point = point;
        }

        public static boolean isValid(String inputSymbol) {
            for (Grade g : Grade.values()) {
                if (g.symbol.equals(inputSymbol)) {
                    return true; // 목록에 있으면 유효함
                }
            }
            return false; // 없으면 무효함
        }

        public static double getPointBySymbol(String inputSymbol) {
            for (Grade g : Grade.values()) {
                if (g.symbol.equals(inputSymbol)) {
                    return g.point;
                }
            }
            return 0.0;
        }
    }

    //constructor
    public Gpa(String className, int credit, String grade, String major) {
        this.className = className;
        this.credit = credit;
        this.grade = grade;
        this.major = major;
    }

    //getter & setter
    public double getGradePoint() {
        return Grade.getPointBySymbol(grade);
    }

    public String getClassName() {
        return className;
    }
    public void setClassName(String className) {
        this.className = className;
    }

    public int getCredit() {
        return credit;
    }
    public void setCredit(int credit) {
        this.credit = credit;
    }

    public String getGrade() {
        return grade;
    }
    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getMajor() {
        return major;
    }
    public void setMajor(String major) {
        this.major = major;
    }

    //toString
    @Override
    public String toString() {
        // 형식: [전공] 과목명,학점,성적
        return major + " " + className + "," + credit + "," + grade;
    }
}
