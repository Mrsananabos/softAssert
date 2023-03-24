import infrastructure.base.ApiTest
import net.javacrumbs.jsonunit.assertj.assertThatJson
import org.json.JSONObject
import org.junit.jupiter.api.Test

class Test: ApiTest() {

    @Test
    fun test() {
       val jsonObject = JSONObject("{\"a\": 1, \"ids\": [100, 200, 300]}")

        softAssertion {
            assertThatJson(jsonObject) {
                inPath("$.a").isIntegralNumber.isEqualTo(3)
                inPath("$.ids[1]").isIntegralNumber.isEqualTo(300)
                inPath("$.ids").isArray.isEqualTo(arrayOf(100, 200, 300))
            }
        }

    }
}