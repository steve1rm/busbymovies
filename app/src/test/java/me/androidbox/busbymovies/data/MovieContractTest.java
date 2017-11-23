package me.androidbox.busbymovies.data;

import android.net.Uri;
import android.provider.BaseColumns;

import org.junit.Ignore;
import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import static org.junit.Assert.*;

/**
 * Created by steve on 3/25/17.
 */
public class MovieContractTest {

    @Test
    public void shouldOnlyHaveOneInnerClass() throws Exception {
        Class[] innerClasses = MovieContract.class.getDeclaredClasses();
        assertEquals("There should be only 1 inner class inside the contract class", 1, innerClasses.length);
    }

    @Test
    public void shouldHaveCorrectTypesForInnerClass() throws Exception {
        Class[] innerClasses = MovieContract.class.getDeclaredClasses();
        assertEquals("There should be only 1 inner class inside the contract class", 1, innerClasses.length);
        /* Get the inner class */
        Class innerClass = innerClasses[0];
        assertTrue("Should implement the BaseColumns interface", BaseColumns.class.isAssignableFrom(innerClass));
        assertTrue("Should have final for inner class", Modifier.isFinal(innerClass.getModifiers()));
        assertTrue("Should have static for inner class", Modifier.isStatic(innerClass.getModifiers()));
    }

    @Ignore("FIXME")
    @Test
    public void shouldHaveCorrectMemberForInnerClass() throws Exception {
        Class[] innerClasses = MovieContract.class.getDeclaredClasses();
        assertEquals("There should be only 1 inner class inside the contract class", 1, innerClasses.length);

        Class innerClass = innerClasses[0];
        Field[] allFields = innerClass.getDeclaredFields();
        assertEquals("Should only have 13 fields declared", 13, allFields.length);

        for(Field field  : allFields) {
            assertTrue("Should contain all String for types", field.getType() == String.class || field.getType() == Uri.class);
            assertTrue("Should contain all final for types", Modifier.isFinal(field.getModifiers()));
            assertTrue("Should contain all static for types", Modifier.isStatic(field.getModifiers()));
        }
    }
}