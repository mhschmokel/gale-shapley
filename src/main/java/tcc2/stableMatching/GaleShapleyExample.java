package tcc2.stableMatching;

import java.util.*;

public class GaleShapleyExample {
    private int N;
    private int[][] menPreference;
    private int[][] womenPreference;
    private int[] womenPartner;
    private int[] menPartner;
    private boolean[] menEngaged;

    public GaleShapleyExample(int[][] menPreference, int[][] womenPreference) {
        N = menPreference.length;
        this.menPreference = menPreference;
        this.womenPreference = womenPreference;
        womenPartner = new int[N];
        menPartner = new int[N];
        menEngaged = new boolean[N];
        Arrays.fill(womenPartner, -1);
        Arrays.fill(menPartner, -1);
    }

    public void match() {
        while (!allMenEngaged()) {
            for (int i = 0; i < N; i++) {
                if (!menEngaged[i]) {
                    for (int j = 0; j < N; j++) {
                        int woman = menPreference[i][j];
                        if (womenPartner[woman] == -1) {
                            womenPartner[woman] = i;
                            menPartner[i] = woman;
                            menEngaged[i] = true;
                            break;
                        } else {
                            int currentMan = womenPartner[woman];
                            if (prefersNewMan(woman, i, currentMan)) {
                                womenPartner[woman] = i;
                                menPartner[i] = woman;
                                menEngaged[i] = true;
                                menEngaged[currentMan] = false;
                                break;
                            }
                        }
                    }
                }
            }
        }
    }

    private boolean allMenEngaged() {
        for (boolean engaged : menEngaged) {
            if (!engaged) return false;
        }
        return true;
    }

    private boolean prefersNewMan(int woman, int newMan, int currentMan) {
        for (int i = 0; i < N; i++) {
            if (womenPreference[woman][i] == newMan) return true;
            if (womenPreference[woman][i] == currentMan) return false;
        }
        return false;
    }

    public int[] getWomenPartners() {
        return womenPartner;
    }

    public int[] getMenPartners() {
        return menPartner;
    }
}

