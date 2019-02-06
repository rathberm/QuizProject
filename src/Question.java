public class Question {
    private String question;
    private String[] answerWord;
    private String answer;
    private String category;
    private int rightAnswered;
    private int questioned;

    public Question(String pCategory, String pQuestion, String[] pAnswerWord, String pAnswer, int pRightAnswered, int pQuestioned) {
        this.question = pQuestion;
        this.answerWord = pAnswerWord;
        this.answer = pAnswer;
        this.category = pCategory;
        this.rightAnswered = pRightAnswered;
        this.questioned = pQuestioned;
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

    public String getCategory(){
        return category;
    }

    public void increaseQuestioned(){
        questioned++;
    }

    public void increaseRightAnswered(){ rightAnswered++; }

    public int getQuestioned(){ return questioned; }

    public int getRightAnswered(){ return rightAnswered; }
}


