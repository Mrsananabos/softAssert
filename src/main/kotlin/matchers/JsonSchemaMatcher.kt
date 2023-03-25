package me.zvq.framework.matchers

import net.javacrumbs.jsonunit.core.ParametrizedMatcher
import net.javacrumbs.jsonunit.core.internal.Node
import org.apache.commons.io.FilenameUtils
import org.everit.json.schema.Schema
import org.everit.json.schema.ValidationException
import org.everit.json.schema.loader.SchemaClient
import org.everit.json.schema.loader.SchemaLoader
import org.hamcrest.BaseMatcher
import org.hamcrest.Description
import org.json.JSONArray
import org.json.JSONObject
import org.json.JSONTokener
import java.io.FileNotFoundException
import kotlin.properties.Delegates

/**
 * Матчер реализующий валидацию JSON тела ответа с JSONSchema
 */
class JsonSchemaMatcher : BaseMatcher<Any>(), ParametrizedMatcher {
    private var jsonSchemaPath: String by Delegates.notNull()
    private val exceptionMessages = StringBuilder()

    override fun describeTo(description: Description) {
        description.appendValue(jsonSchemaPath)
    }

    override fun describeMismatch(item: Any?, description: Description) {
        description
            .appendText("\n")
            .appendText("It is not matches with schema")
            .appendText(exceptionMessages.toString())
    }

    override fun matches(actual: Any): Boolean {
        if (actual !is Node.JsonMap && actual !is Node.JsonList) return false

        val schema = getJsonSchema(jsonSchemaPath)

        try {
            when (actual) {
                is Node.JsonMap -> schema.validate(JSONObject("${actual.wrappedNode}"))
                is Node.JsonList -> schema.validate(JSONArray("${actual.wrappedNode}"))
            }
        } catch (e: ValidationException) {
            exceptionMessages
                .append(e.message)
                .append(System.lineSeparator())

            e.allMessages.forEach(exceptionMessages::appendLine)
        }

        return exceptionMessages.isEmpty()
    }

    override fun setParameter(parameter: String) {
        this.jsonSchemaPath = parameter
    }

    private fun getJsonSchema(schemaResourcePath: String): Schema {
        val schemasRootDir = "schemas/"
        val filePath = FilenameUtils.normalize(schemasRootDir + schemaResourcePath, true)
        val classLoader = javaClass.classLoader
        val jsonSchemaData = classLoader.getResourceAsStream(filePath)
            ?: throw FileNotFoundException("File with path $filePath not found in Resources folder")

        val jsonSchemaObject = JSONObject(JSONTokener(jsonSchemaData))

        val schemaLoader = SchemaLoader.builder()
            .schemaClient(SchemaClient.classPathAwareClient())
            .resolutionScope("classpath://$schemasRootDir")
            .schemaJson(jsonSchemaObject)


        return schemaLoader.build().load().build()
    }
}