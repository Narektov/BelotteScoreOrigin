package traveling.com.belottescore;

import io.realm.RealmObject;

/**
 * Created by narek on 10/2/16.
 */
public class Model extends RealmObject {
    public int ourScore;
    public int yourScore;
    public int toldScore;
    public int combination;

    protected int getCombination() {
        return combination;
    }

    protected void setCombination(int pCombination) {
        combination = pCombination;
    }

    protected int getOurScore() {
        return ourScore;
    }

    protected void setOurScore(int pOurScore) {
        ourScore = pOurScore;
    }

    protected int getYourScore() {
        return yourScore;
    }

    protected void setYourScore(int pYourScore) {
        yourScore = pYourScore;
    }

    protected int getToldScore() {
        return toldScore;
    }

    protected void setToldScore(int pToldScore) {
        toldScore = pToldScore;
    }
}
