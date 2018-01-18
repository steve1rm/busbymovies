package me.androidbox.busbymovies.basepresenter;

import android.view.View;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

public class BasePresenterImpTest {
    private BasePresenter basePresenter;

    @BeforeEach
    void setup() {
        basePresenter = new BasePresenterImp();
    }

    @Test
    void testBasePresenter_shouldNotBeNullValue() {
        assertNotNull(basePresenter);
    }

    @Test
    void testAttach_attachesView() {
        final View view = mock(View.class);

        basePresenter.attachView(view);

        assertEquals(view, basePresenter.getView());
    }

    @Test
    void testIsViewAttached_returnFalseWhenNotAttached() {
        assertFalse(basePresenter.isViewAttached());
    }

    @Test
    void testIsViewAttached_returnTrueWhenAttached() {
        final View view = mock(View.class);

        basePresenter.attachView(view);

        assertTrue(basePresenter.isViewAttached());
    }

    @Test
    void testDetachView_detachesView_whenViewNotAttached() {
        basePresenter.detachView();

        assertFalse(basePresenter.isViewAttached());
    }

    @Test
    void testDetachView_detachesView_whenAttached() {
        final View view = mock(View.class);

        basePresenter.attachView(view);
        basePresenter.detachView();

        assertFalse(basePresenter.isViewAttached());
    }
}
