/**
 * In dieser Klasse sind die Fragen mit ihren Attributen definiert
 *
 * @author Jan Roth, Moritz Rathberger
 */

class Question {
    private String question;
    private String[] answerWord;
    private String answer;
    private String category;
    private int rightAnswered;
    private int questioned;
    private double percentage;

    public Question(String pCategory, String pQuestion, String[] pAnswerWord, String pAnswer, int pRightAnswered, int pQuestioned) {
        this.question = pQuestion;
        this.answerWord = pAnswerWord;
        this.answer = pAnswer;
        this.category = pCategory;
        this.rightAnswered = pRightAnswered;
        this.questioned = pQuestioned;
        if (this.questioned != 0) {
            this.percentage = this.rightAnswered / this.questioned;
        } else {
            this.percentage = 0;
        }
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
        for (String s : answerWord) {
            for (int i = 0; i < arr.length; i++) {
                if (arr[i].toLowerCase().contains(s.toLowerCase())) {
                    rightAnswer = true;
                }
            }
        }
        return rightAnswer;
    }

    public double getPercentage() {
        return percentage;
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

    public void increaseQuestioned() {
        questioned++;
    }

    public void increaseRightAnswered() {
        rightAnswered++;
    }

    public int getQuestioned() {
        return questioned;
    }

    public int getRightAnswered() {
        return rightAnswered;
    }
}


