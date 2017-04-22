package com.nathanfei.laststand.framework;

import android.graphics.Canvas;

import com.nathanfei.laststand.objects.Button;

import java.util.LinkedList;

public class ObjHandler {

    private LinkedList<GameObject> listOfObjects, toAdd, toRemove;

    public ObjHandler(GameObject... objects) {
        listOfObjects = new LinkedList<>();
        toAdd = new LinkedList<>();
        toRemove = new LinkedList<>();
        for (GameObject object : objects) {
            listOfObjects.add(object);
        }
    }

    public LinkedList<GameObject> list() {
        return listOfObjects;
    }

    public int getSize() {
        return listOfObjects.size();
    }

    public void tick() {
        for (GameObject object : listOfObjects) {
            object.tick();
        }
        for (GameObject object : toAdd) {
            listOfObjects.add(object);
        }
        for (GameObject object : toRemove) {
            listOfObjects.remove(object);
        }
        toAdd.clear();
        toRemove.clear();
        System.out.println(listOfObjects.size());
    }

    public void render(Canvas canvas) {
        for (GameObject object : listOfObjects) {
            object.render(canvas);
        }
    }

    public void addObject(GameObject object) {
        toAdd.add(object);
    }

    public void removeObject(GameObject object) {
        toRemove.add(object);
    }

}
