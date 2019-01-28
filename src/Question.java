public class Question {
    private String question;
    private String answer;
    private String category;

    private boolean alreadyAnswered;

    public Question(String pQuestion, String pAnswer, String pCategory) {
        this.question = pQuestion;
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

    public String getCategory() {
        return category;
    }

    public boolean isAlreadyAnswered() {
        return alreadyAnswered;
    }

    public void setAlreadyAnswered(boolean alreadyAnswered) {
        this.alreadyAnswered = alreadyAnswered;
    }
}


