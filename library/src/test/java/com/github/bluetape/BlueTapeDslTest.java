package com.github.bluetape;

import android.content.Context;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.github.bluetape.exception.ViewNotFoundException;
import com.github.bluetape.function.BindingFunction;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class BlueTapeDslTest {

    @Mock
    Context context;
    @Mock
    View view;
    @Mock
    BindingFunction functionA;
    @Mock
    BindingFunction functionB;

    @Test
    public void composite() throws Exception {
        // When
        BlueTapeDsl
                .composite(
                        functionA,
                        functionB
                )
                .bind(view);

        // Then
        InOrder inOrder = inOrder(functionA, functionB);

        inOrder.verify(functionA).bind(view);
        inOrder.verify(functionB).bind(view);
    }

    @Test
    public void id() throws Exception {
        // Given
        int subViewId = 123;
        View subView = mock(View.class);

        //noinspection ResourceType
        given(view.findViewById(subViewId))
                .willReturn(subView);

        // When
        BlueTapeDsl
                .id(subViewId,
                        functionA,
                        functionB
                )
                .bind(view);

        // Then
        InOrder inOrder = inOrder(functionA, functionB);

        inOrder.verify(functionA).bind(subView);
        inOrder.verify(functionB).bind(subView);
    }

    @Test(expected = ViewNotFoundException.class)
    public void id_ViewNotFound() throws Exception {
        // Given
        int subViewId = 123;

        //noinspection ResourceType
        given(view.findViewById(subViewId))
                .willReturn(null);

        // When
        BlueTapeDsl
                .id(subViewId,
                        functionA,
                        functionB
                )
                .bind(view);

        // Then
        // Expect exception
    }

    @Test
    public void text_String() throws Exception {
        // Given
        TextView textView = mock(TextView.class);
        String expectedText = "expected";

        // When
        BlueTapeDsl
                .text(expectedText)
                .bind(textView);

        // Then
        verify(textView).setText(expectedText);
    }

    @Test
    public void text_Resource() throws Exception {
        // Given
        TextView textView = mock(TextView.class);
        int expectedResource = android.R.string.cancel;

        // When
        BlueTapeDsl
                .textResource(expectedResource)
                .bind(textView);

        // Then
        verify(textView).setText(expectedResource);
    }

    @Test
    public void visibility() throws Exception {
        // When
        BlueTapeDsl
                .visibility(View.GONE)
                .bind(view);

        // Then
        verify(view).setVisibility(View.GONE);
    }

    @Test
    public void visible_True() throws Exception {
        // When
        BlueTapeDsl
                .visible(true)
                .bind(view);

        // Then
        verify(view).setVisibility(View.VISIBLE);
    }

    @Test
    public void visible_False() throws Exception {
        // When
        BlueTapeDsl
                .visible(false)
                .bind(view);

        // Then
        verify(view).setVisibility(View.GONE);
    }

    @Test
    public void checked() throws Exception {
        // Given
        CheckBox checkBox = mock(CheckBox.class);

        // When
        BlueTapeDsl
                .checked(true)
                .bind(checkBox);

        // Then
        verify(checkBox).setChecked(true);
    }

}