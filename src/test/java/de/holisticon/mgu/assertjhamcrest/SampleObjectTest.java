package de.holisticon.mgu.assertjhamcrest;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.catchThrowable;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class SampleObjectTest {

    @Test
    public void nullAssertion(){
        // Given
        // When
        SampleObject nullObject = null;

        // Then
        assertThat(nullObject, nullValue());
        Assertions.assertThat(nullObject).isNull();
    }

    @Test
    public void equalsAssertion(){
        // Given
        // When
        SampleObject sampleObject = SampleObject.builder().name("aSampleObject").age(3).build();

        // Then
        assertThat(sampleObject, equalTo(sampleObject));
        Assertions.assertThat(sampleObject).isEqualTo(sampleObject);
    }

    @Test
    public void listAssertion_contain(){
        // Given
        SampleObject aSampleObject = SampleObject.builder().name("aSampleObject").age(3).build();
        SampleObject anotherSampleObject = SampleObject.builder().name("anotherSampleObject").age(4)
                .build();

        // When
        List<SampleObject> listOfSampleObjects = Arrays.asList(aSampleObject);

        // Then
        Assertions.assertThat(listOfSampleObjects)
                .contains(aSampleObject)
                .doesNotContain(anotherSampleObject);
        assertThat(listOfSampleObjects, allOf(hasItem(aSampleObject)));
    }

    @Test
    public void listAssertion_filteredOn(){
        // Given
        SampleObject aSampleObject = SampleObject.builder().name("aSampleObject").age(3).build();
        SampleObject anotherSampleObject = SampleObject.builder().name("anotherSampleObject").age(4)
                .build();

        // When
        List<SampleObject> listOfSampleObjects = Arrays.asList(aSampleObject, anotherSampleObject);

        // Then
        Assertions.assertThat(listOfSampleObjects)
                .filteredOn(sampleObject -> sampleObject.getName().contains("aSample"))
                .as("Check set of objects in list that match the filter")
                .containsOnly(aSampleObject);
    }

    @Test
    public void listAssertion_extracting(){
        // Given
        SampleObject aSampleObject = SampleObject.builder().name("aSampleObject").age(3).build();
        SampleObject anotherSampleObject = SampleObject.builder().name("anotherSampleObject").age(4)
                .build();

        // When
        List<SampleObject> listOfSampleObjects = Arrays.asList(aSampleObject, anotherSampleObject);

        // Then
        Assertions.assertThat(listOfSampleObjects)
                .extracting(sampleObject -> sampleObject.getName()).as("Check names of objects in list")
                .containsOnly("aSampleObject", "anotherSampleObject");
    }

    @Test
    public void softAssertion(){
        // Given
        SampleObject aSampleObject = SampleObject.builder().name("aSampleObject").age(3).build();

        // When
        SoftAssertions softly = new SoftAssertions();

        // Then
        softly.assertThat(aSampleObject.getName()).isEqualTo("anotherSampleObject");
        softly.assertThat(aSampleObject.getAge()).isEqualTo(4);
        // fails if:
        // softly.assertAll();
    }

    @Test
    public void exceptionAssertion() {
        // Given

        // When
        // Then
        Assertions.assertThatThrownBy(() -> { throw new Exception("Boom!"); })
                .isInstanceOf(Exception.class)
                .hasMessageContaining("Boom");
    }

    @Test
    public void exceptionAssertion_alternative() {
        // Given

        // When
        // Then
        Assertions.assertThatExceptionOfType(Exception.class).isThrownBy(() -> {throw new Exception("Boom!"); })
                .withMessageContaining("Boom");
    }

    @Test
    public void exceptionAssertion_bdd() {
        // Given

        // When
        Throwable thrown = catchThrowable(() -> { throw new Exception("Boom!"); } );

        // Then
        Assertions.assertThat(thrown).isInstanceOf(Exception.class)
            .hasMessageContaining("Boom");
    }

    @Test
    public void customAssertions() {
        // Given
        // When
        SampleObject aSampleObject = SampleObject.builder().name("aSampleObject").age(3).build();

        // Then
        my.customassertions.Assertions.assertThat(aSampleObject)
                .hasName("aSampleObject")
                .hasAge(3);
    }
}