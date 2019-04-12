package sample;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Person implements Serializable {

    private String name;
    private String country;

    private int ballControl;
    private int hit;
    private int defense;
    private int playerNumber;
    private int games;
    private int goals;
    private boolean ready;


    public Person(){

    }
    public int getBallControl() {
        return ballControl;
    }

    public int getHit() {
        return hit;
    }

    public int getDefense() {
        return defense;
    }

    public String getCountry() {
        return country;
    }

    public void setBallControl(int ballControl) {
        this.ballControl = ballControl;
    }

    public int getGames() {
        return games;
    }

    public int getGoals() {
        return goals;
    }


    public Person(String name, Integer ballControl, Integer hit, Integer defense, String country, Integer playerNumber, Integer games, Integer goals){
        this.name = name;
        this.ballControl = ballControl;
        this.hit = hit;
        this.defense = defense;
        this.country = country;
        this.playerNumber = playerNumber;
        this.games = games;
        this.goals = goals;
        ready = false;
    }

    public boolean isReady() {
        return ready;
    }

    public void setReady(boolean ready) {
        this.ready = ready;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPlayerNumber() {
        return playerNumber;
    }

    public int avr(){
        return (ballControl+hit+defense)/3;
    }

    @Override
    public String toString() {
        return
                "'" + name + '\'' +
                ", avrSkill=" + avr()+"\n"
                ;
    }
}
