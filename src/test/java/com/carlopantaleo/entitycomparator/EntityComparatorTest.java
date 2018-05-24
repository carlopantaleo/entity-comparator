package com.carlopantaleo.entitycomparator;

import org.junit.Test;

import static org.junit.Assert.*;

public class EntityComparatorTest {
    @Test
    public void simpleComparationsWork() throws NoSuchMethodException {
        SampleEntity s1 = new SampleEntity();
        s1.setIntField(1);
        s1.setStringField("b");
        s1.setComparableField(new SampleEntity.ComparableClass(1));
        s1.setNonComparableField(new SampleEntity.NonComparableClass(1));

        SampleEntity s2 = new SampleEntity();
        s2.setIntField(2);
        s2.setStringField("a");
        s2.setComparableField(new SampleEntity.ComparableClass(2));
        s2.setNonComparableField(new SampleEntity.NonComparableClass(2));

        EntityComparator<SampleEntity> ec = new EntityComparator<>(SampleEntity.class);
        ec.addComparingProperty("intField");
        assertEquals(1, ec.compare(s2, s1));
        ec.addComparingProperty("stringField");
        assertEquals(1, ec.compare(s2, s1));

        ec = new EntityComparator<>(SampleEntity.class);
        ec.addComparingProperty("stringField");
        assertEquals(-1, ec.compare(s2, s1));

        ec = new EntityComparator<>(SampleEntity.class);
        ec.addComparingProperty("comparableField");
        assertEquals(1, ec.compare(s2, s1));

        ec = new EntityComparator<>(SampleEntity.class);
        ec.addComparingProperty("nonComparableField");
        assertEquals(-1, ec.compare(s2, s1));
        assertEquals(0, ec.compare(s1, s1));
    }

    @Test(expected = NoSuchMethodException.class)
    public void exceptionOnNonExistentField() throws Exception {
        EntityComparator<SampleEntity> ec = new EntityComparator<>(SampleEntity.class);
        ec.addComparingProperty("blablaField");
    }

    @Test
    public void exceptionOnExceptionMethod() throws Exception {
        EntityComparator<SampleEntity> ec = new EntityComparator<>(SampleEntity.class);
        ec.addComparingProperty("throwingExceptionField");
        try {
            ec.compare(new SampleEntity(), new SampleEntity());
        } catch (RuntimeException e) {
            assertEquals("caught", e.getCause().getMessage());
            return;
        }

        // Should never reach this point
        fail("No exception caught");
    }

    @Test(expected = NoSuchMethodException.class)
    public void exceptionOnPrivateMethod() throws Exception {
        EntityComparator<SampleEntity> ec = new EntityComparator<>(SampleEntity.class);
        ec.addComparingProperty("privateField");
    }
}