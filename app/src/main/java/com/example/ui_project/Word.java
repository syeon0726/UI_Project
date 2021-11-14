package com.example.ui_project;

public class Word {

        private String term;
        private String definition;

        public Word(String term, String definition){
            this.term=term;
            this.definition=definition;
        }
        public String getTerm(){
            return term;
        }

        public String getDefinition() {
            return definition;
        }

}
