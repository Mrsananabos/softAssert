package infrastructure.base

import org.assertj.core.api.SoftAssertions


abstract class ApiTest() {

    fun softAssertion(softAssertion: SoftAssertions.() -> Unit) {
        SoftAssertions().apply(softAssertion).assertAll()
    }


}