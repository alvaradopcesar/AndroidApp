package iosco.app.model.entity;

import java.io.Serializable;

/**
 * Created by JTITOIN on 11/04/2016.
 */





    public class SurveyItemResult {
        private int QuestionNumber;
        private int QuestionValue;


        public int getQuestionNumber() {
            return QuestionNumber;
        }

        public void setQuestionNumber(int questionNumber) {
            QuestionNumber = questionNumber;
        }

        public int getQuestionValue() {
            return QuestionValue;
        }

        public void setQuestionValue(int questionValue) {
            QuestionValue = questionValue;
        }
    }


