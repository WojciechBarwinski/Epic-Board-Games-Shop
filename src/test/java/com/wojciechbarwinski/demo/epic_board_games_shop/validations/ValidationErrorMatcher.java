package com.wojciechbarwinski.demo.epic_board_games_shop.validations;

import lombok.RequiredArgsConstructor;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

@RequiredArgsConstructor
public class ValidationErrorMatcher extends TypeSafeMatcher<ValidationError> {

    private final String expectedFieldName;
    private final String expectedMessage;
    private final Object expectedRejectedValue;

    public static Matcher<ValidationError> matchesValidationError(String fieldName, String message, Object rejectedValue) {
        return new ValidationErrorMatcher(fieldName, message, rejectedValue);
    }

    @Override
    protected boolean matchesSafely(ValidationError error) {
        return expectedFieldName.equals(error.getFieldName()) &&
                expectedMessage.equals(error.getMessage()) &&
                expectedRejectedValue.equals(error.getRejectedValue());
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("a ValidationError with field name ")
                .appendValue(expectedFieldName)
                .appendText(", message ")
                .appendValue(expectedMessage)
                .appendText(", and rejected value ")
                .appendValue(expectedRejectedValue);
    }


}
