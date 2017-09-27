package Levels;

import Main.Game;

import java.util.ArrayList;
import java.util.Iterator;

public class LevelManager {

    private Game game;
    private ArrayList<Level> levels = new ArrayList<Level>();
    private Level level;

    public LevelManager(Game game){
        this.game = game;

        registerLevel(new TestLevel());
        registerLevel(new TestLevel2());
    }

    private void loadLevel(String name){
        if(!levelExists(name)){return;}

        // Delete current level
        game.handler.removeWorldEntities();

        for(Level l : levels){
            if(l.getName().equals(name)){
                level = l;
                level.initialize();
                level.setGame(game);
                level.spawn();
            }
        }
    }

    public void setLevel(String level){
        if(!levelExists(level)){return;}
        loadLevel(level);
    }

    public void registerLevel(Level level){

        // Remove level if it has the same name as the new level
        if(levelExists(level.getName())){
            Iterator it = levels.iterator();
            while(it.hasNext()){
                Level l = (Level)it.next();
                if(l.getName().equals(level.getName())){
                    it.remove();
                    break;
                }
            }
        }

        levels.add(level);
    }

    public Level getActiveLevel(){
        return level;
    }
    public boolean levelExists(String name){
        for(Level l : levels){
            if(l.getName().equals(name)){
                return true;
            }
        }
        return false;
    }

    public String[] getLevelNames(){
        String[] returns = new String[levels.size()];
        int c = 0;
        for(Level l : levels){
            returns[c] = l.getName();
            c++;
        }
        return returns;
    }

    public ArrayList<Level> getLevels(){
        return levels;
    }
}
