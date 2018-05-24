package com.carlopantaleo.entitycomparator;

public class SampleEntity {
    private int intField;
    private String stringField;
    private NonComparableClass nonComparableField;
    private ComparableClass comparableField;
    private Object throwingExceptionField;

    public int getIntField() {
        return intField;
    }

    public void setIntField(int intField) {
        this.intField = intField;
    }

    public String getStringField() {
        return stringField;
    }

    public void setStringField(String stringField) {
        this.stringField = stringField;
    }

    public NonComparableClass getNonComparableField() {
        return nonComparableField;
    }

    public void setNonComparableField(NonComparableClass nonComparableField) {
        this.nonComparableField = nonComparableField;
    }

    public ComparableClass getComparableField() {
        return comparableField;
    }

    public void setComparableField(ComparableClass comparableField) {
        this.comparableField = comparableField;
    }

    public Object getThrowingExceptionField() throws Exception {
        throw new Exception("caught");
    }

    public void setThrowingExceptionField(Object throwingExceptionField) {
        this.throwingExceptionField = throwingExceptionField;
    }

    private Object getPrivateField() {
        return null;
    }

    public static class NonComparableClass {
        private int fooInt;

        public NonComparableClass(int fooInt) {
            this.fooInt = fooInt;
        }

        public int getFooInt() {
            return fooInt;
        }

        public void setFooInt(int fooInt) {
            this.fooInt = fooInt;
        }

        @Override
        public String toString() {
            return Integer.toString(Integer.MAX_VALUE - fooInt);
        }
    }

    public static class ComparableClass implements Comparable<ComparableClass> {
        private int barInt;

        public ComparableClass(int barInt) {
            this.barInt = barInt;
        }

        @Override
        public int compareTo(ComparableClass o) {
            return Integer.compare(this.barInt, o.barInt);
        }

        public int getBarInt() {
            return barInt;
        }

        public void setBarInt(int barInt) {
            this.barInt = barInt;
        }

        @Override
        public String toString() {
            return Integer.toString(Integer.MAX_VALUE - barInt);
        }
    }
}
