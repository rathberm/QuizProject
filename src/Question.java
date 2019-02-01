public class Question {
    private String question;
    private String[] answerWord;
    private String answer;
    private String category;

    private boolean alreadyAnswered;

    public Question(String pCategory, String pQuestion, String[] pAnswerWord, String pAnswer, int rightAnswered, int questioned) {
        this.question = pQuestion;
        this.answerWord = pAnswerWord;
        this.answer = pAnswer;
        this.category = pCategory;
        this.alreadyAnswered = false;
    }

    /**
     * Vergleicht die Antwort mit der Lösung
     *
     * @param input Die Antwort des Benutzers
     * @return True, wenn die Antwort wahr ist, false wenn nicht
     */
    public boolean validateAnswer(String input) {
        String[] arr = input.split(" ");
        boolean rightAnswer = false;
        for (int i = 0; i < arr.length; i++) {
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

    public boolean isAlreadyAnswered() {
        return alreadyAnswered;
    }

    public void setAlreadyAnswered(boolean alreadyAnswered) {
        this.alreadyAnswered = alreadyAnswered;
    }
}


