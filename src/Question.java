public class Question {
    private String question;
    private String answer;

    public Question(String pQuestion, String pAnswer) {
        this.question = pQuestion;
        this.answer = pAnswer;
    }

    public static void main(String[] args) {

    }

    /**
     * Vergleicht die Antwort mit der Lösung
     * @param input
     * @param input Die Antwort des Benutzers
     * @return True, wenn die Antwort wahr ist, false wenn nicht
     */
    public boolean validateAnswer(String input) {
        if (input.contains(this.answer)) {
            return true;
        } else {
            return false;
        }
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }
}
