public class Question {
    private String question;
    private String answer;

    public Question(String pQuestion, String pAnswer) {
        this.question = pQuestion;
        this.answer = pAnswer;
    }

    /**
     * Vergleicht die Antwort mit der Lösung
     * @param input Die Antwort des Benutzers
     * @return True, wenn die Antwort wahr ist, false wenn nicht
     */
    public boolean validateAnswer(String input) {
        String[] arr = input.split(" ");
        boolean rightAnswer = false;
        for (int i = 0; i < arr.length; i++){
            if (arr[i].contains(this.answer)) {
                rightAnswer = true;
            }
        }
        return rightAnswer;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }
}
