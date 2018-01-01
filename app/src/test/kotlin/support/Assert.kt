package support

import android.view.View
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.equalTo


/**
 * Created by steve on 12/22/17.
 */
class Assert {

    companion object {
        fun viewIsGone(view: View) {
            assertThat(view.visibility, equalTo(View.GONE))
        }
     }

}