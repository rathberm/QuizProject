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
     * @param pInput Die Antwort des Benutzers
     * @return True, wenn die Antwort richtig ist, false wenn nicht
     */
    public boolean validateAnswer(String pInput) {
        String[] arr = pInput.split(" ");
        boolean rightAnswer = false;
        for (int j = 0; j < answerWord.length; j++) {
            for (int i = 0; i < arr.length; i++) {
                if (arr[i].contains(this.answerWord[j])) {
                    rightAnswer = true;
                }
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


