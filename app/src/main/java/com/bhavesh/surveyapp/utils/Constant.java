package com.bhavesh.surveyapp.utils;

public interface Constant {
    String MALE = "MALE";
    String FEMALE = "FEMALE";

    interface Religion {
        String HINDU = "Hindu";
        String MUSLIM = "Muslim";
        String SIKH = "Sikh";
    }

    interface ageGroup {
        String ABOVE_18 = "18-25";
        String ABOVE_25 = "26-40";
        String ABOVE_40 = "41-60";
        String ABOVE_60 = "60+";
    }

    interface Caste {
        String SC = "Sc";
        String ST = "St";
        String OBC = "Obc";
        String GENERAL = "General";
        String OTHERS = "Others";
    }
    interface  Area{
        String RURAL="Rural";
        String URBAN="Urban";
        String SEMI_URBAN="Semi Urban";
    }
    interface  Education{
        String MASTER="Master";
        String POST_GRADUATE="PostGraduate";
        String GRADUATE="Graduate";
        String EDU_12="12th";
        String EDU_10="10th";
        String EDU_8="8th";
        String ILLETRATE="Illiterate";
    }
    interface Occuptant{
        String PRIVATE="Private";
        String GOVT="Government";
        String SELF="Self Employed";
        String HOUSEWIFE="HouseWife";
    }
    interface District{
        String LOK_SABHA="Lok Sabha";
        String VIDHAN_SABHA="Vidhan Sabha";
    }
}
