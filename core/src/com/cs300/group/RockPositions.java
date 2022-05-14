package com.cs300.group;

public class RockPositions {

    int x = 1;
    int y = 1;
    boolean isActive = false;
    RockPositions() {

    }

    public void putIsActive() {
        isActive = true;
    }

    public void putIsNotActive() {
        isActive = false;
    }

    public void setCoords(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean isHere(int x, int y) {
        if (this.x == x && this.y == y) {
            return true;
        }

        else {
            return false;
        }
    }
    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public boolean isActive() {
        return this.isActive;
    }
}
