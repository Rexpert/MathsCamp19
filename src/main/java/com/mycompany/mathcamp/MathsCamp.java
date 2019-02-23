/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.mathcamp;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.*;
import java.util.Arrays;
import java.util.StringJoiner;

/**
 *
 * @author User
 */
public class MathsCamp {
    
    private ConstructJson json;
    private int[] curPos = new int[10];
    private Game[] game = new Game[4];
    private final int[] x2Tiles = {20,40,66};
    private final int[] x4Tiles = {30,50};
    
    
    public MathsCamp() throws IOException {
        String fileName = "game.json";
        String appdata = System.getenv("APPDATA");
        StringJoiner joiner = new StringJoiner(File.separator);
        String gameDataFile = joiner
                               .add(appdata)
                               .add("math-camp-19-boardgame")
                               .add("game_data").add(fileName)
                               .toString();
        File file = new File(gameDataFile);
        if(file.exists()) {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String str = br.readLine();
            curPos = Arrays.stream(str.substring(1, str.length()-1).split(",")).map(String::trim).mapToInt(Integer::parseInt).toArray();
        } else {
            for(int i = 0; i < curPos.length; i++) {
                curPos[i] = 1;
            }
            toJson(fileName);
        }
        
        fileName = "data.json";
        file = new File(fileName);
        if(file.exists()) {
            Gson gson = new Gson();
            BufferedReader br = new BufferedReader(new FileReader(file));
            json = gson.fromJson(br, ConstructJson.class);
            game = json.getGame();
        } else {
            int[] empty = new int[10];
            boolean[] def = new boolean[10];
            for (int i = 0; i < game.length; i++) {
                game[i] = new Game(empty, def, empty);
            }
            toJson(fileName);
        }
    }
    
    public void update(int currentSelected, int[] score, boolean[] result, int[] gambling) {
        game[currentSelected] = new Game(score, result, gambling);
        for(int i = 0; i < curPos.length; i++) {
            curPos[i] = 1;
            for (Game game1 : game) {
                curPos[i] += game1.getScore()[i];
                if (game1.getGambling()[i] != 0) {
                    if (checkInt(x2Tiles, curPos[i])) {
                        if (game1.getResult()[i]) {
                            curPos[i] += game1.getGambling()[i] * 2;
                        } else {
                            curPos[i] -= game1.getGambling()[i] * 2;
                        }
                    } else if (checkInt(x4Tiles, curPos[i])) {
                        if (game1.getResult()[i]) {
                            curPos[i] += game1.getGambling()[i] * 4;
                        } else {
                            curPos[i] -= game1.getGambling()[i] * 4;
                        }
                    } else {
                        if (game1.getResult()[i]) {
                            curPos[i] += game1.getGambling()[i];
                        } else {
                            curPos[i] -= game1.getGambling()[i];
                        }
                    }
                }
            }
            if(curPos[i] < 1) {
                curPos[i] = 1;
            }
        }
    }
    
    public void toJson(String fileName) throws IOException {
        if(fileName.equals("game.json")) {
            String appdata = System.getenv("APPDATA");
            StringJoiner joiner = new StringJoiner(File.separator);
            String gameDataFile = joiner
                                   .add(appdata)
                                   .add("math-camp-19-boardgame")
                                   .add("game_data").add(fileName)
                                   .toString();
            try(PrintWriter printer = new PrintWriter(gameDataFile)) {
                printer.print(Arrays.toString(curPos));
            }
        } else if(fileName.equals("data.json")) {
            File file = new File(fileName);
            Gson gson = new GsonBuilder().create();
            json = new ConstructJson(game);
            try(Writer writer = new FileWriter(file)) {
                gson.toJson(json, writer);
            }
        }
    }
    
    private boolean checkInt(int[] array, int num) {
        for(int i = 0; i < array.length; i++) {
            if(array[i] == num) 
                return true;
        }
        return false;
    }
    
    public int[] getScore(int i) {
        return game[i].getScore();
    }
    
    public boolean[] getResult(int i) {
        return game[i].getResult();
    }
    
    public int[] getGambling(int i) {
        return game[i].getGambling();
    }
}
    
class ConstructJson {
    private Game[] game;
    
    public ConstructJson(Game[] gm) {
        game = gm;
    }
    
    public Game[] getGame() {
        return game;
    }
}

class Game {
    private final int[] score;
    private final boolean[] result;
    private final int[] gambling;
    
    public Game(int[] scr, boolean[] rst, int[] gmb) {
        score = scr;
        result = rst;
        gambling = gmb;
    }
    
    public int[] getScore() {
        return score;
    }
    
    public boolean[] getResult() {
        return result;
    }
    
    public int[] getGambling() {
        return gambling;
    }
}
