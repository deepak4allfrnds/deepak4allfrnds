package com.bhavesh.surveyapp.utils;

public interface Constant {
    String MALE = "MALE";
    String FEMALE = "FEMALE";
    String AGENT_EMAIL="email";
    String AGENT_ID="id";

    interface Religion {
        String HINDU = "HINDU";
        String MUSLIM = "MUSLIM";
        String SIKH = "SIKH";
        String CHRISTIAN = "CHRISTIAN";
    }

    interface ageGroup {
        String ABOVE_18 = "18-25";
        String ABOVE_25 = "26-40";
        String ABOVE_40 = "41-60";
        String ABOVE_60 = "60+";
    }

    interface Caste {
        String SC = "SC";
        String ST = "ST";
        String OBC = "OBC";
        String GENERAL = "GENERAL";
        String OTHERS = "OTHERS";
    }
    interface  Area{
        String RURAL="RURAL";
        String URBAN="URBAN";
        String SEMI_URBAN="SEMI URBAN";
    }
    interface  Education{
        String MASTER="MASTER";
        String POST_GRADUATE="POST GRADUATE";
        String GRADUATE="GRADUATE";
        String EDU_12="12th";
        String EDU_10="10th";
        String EDU_8="8th";
        String ILLETRATE="ILLITERATE";
    }
    interface Occuptant{
        String PRIVATE="PRIVATE";
        String GOVT="GOVERNMENT";
        String SELF="SELF-EMPLOYED";
        String HOUSEWIFE="HOUSEWIFE";
    }
    interface District{
        String LOK_SABHA="LOK SABHA";
        String VIDHAN_SABHA="VIDHAN SABHA";
    }
}
