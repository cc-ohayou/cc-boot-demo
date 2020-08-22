package com.qunhe.toilet.facade.domain.common.util.log;

import lombok.Data;
import org.slf4j.Marker;

import java.util.Iterator;

/**
 * @AUTHOR CF
 * @DATE Created on 2018/12/28 17:43.
 */
@Data
public class MyMarker implements Marker{
    private String name;

    public MyMarker(String name) {
        this.name=name;
    }

    public static MyMarker getInstance(String name){
        return new MyMarker(name);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void add(Marker reference) {

    }

    @Override
    public boolean remove(Marker reference) {
        return false;
    }

    @Override
    public boolean hasChildren() {
        return false;
    }

    @Override
    public boolean hasReferences() {
        return false;
    }

    @Override
    public Iterator<Marker> iterator() {
        return null;
    }

    @Override
    public boolean contains(Marker other) {
        return false;
    }

    @Override
    public boolean contains(String name) {
        return false;
    }

    @Override
    public boolean equals(Object o) {
        return false;
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
