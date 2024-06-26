package com.picross.collab.shared;

import java.util.Objects;

// The Position class
public record Position(int x, int y) {

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return x + " " + y;
    }
}
