package org.solovyev.common.definitions;

import org.jetbrains.annotations.NotNull;
import org.solovyev.common.Identifiable;

public class Identity<T> implements Identifiable<T>, Cloneable {

    @NotNull
    private T id;

    public Identity(@NotNull T id) {
        this.id = id;
    }

    public Identity() {
    }

    @Override
    public T getId() {
        return id;
    }

    public void setId(@NotNull T id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Identity identity = (Identity) o;

        if (!id.equals(identity.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    protected Object clone() {
        try {
            // NOTE: id is not cloned
            return super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }
}