// app/src/androidTest/java/com/yourpackage/yourapp/CustomViewActions.java
package androidsamples.java.journalapp;

import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;

import android.view.View;
import android.widget.Button;

import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;

import org.hamcrest.Matcher;

public class CustomViewActions {

    public static ViewAction setText(final String text) {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return isAssignableFrom(Button.class); // Ensure it's a Button
            }

            @Override
            public String getDescription() {
                return "Set text on a Button";
            }

            @Override
            public void perform(UiController uiController, View view) {
                ((Button) view).setText(text); // Set the text
            }
        };
    }
}
