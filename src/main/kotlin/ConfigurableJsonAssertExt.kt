import me.zvq.framework.matchers.JsonSchemaMatcher
import net.javacrumbs.jsonunit.assertj.JsonAssert
import net.javacrumbs.jsonunit.assertj.JsonAssert.ConfigurableJsonAssert

/**
 * Расширение для JsonUnit ассерта добавляющий верификацию схемы
 * @param schemaResourcePath - путь до схемы в каталоге resources/schemas
 */
fun ConfigurableJsonAssert.isMatchWithJsonSchema(
    schemaResourcePath: String
): JsonAssert {
    return this.withMatcher(
        "jsonSchemeMatcher", JsonSchemaMatcher()
    ).isEqualTo(
        "#{json-unit.matches:jsonSchemeMatcher}$schemaResourcePath"
    )
}

