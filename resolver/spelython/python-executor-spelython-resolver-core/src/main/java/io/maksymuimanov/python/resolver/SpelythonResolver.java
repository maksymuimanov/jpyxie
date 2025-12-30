package io.maksymuimanov.python.resolver;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.maksymuimanov.python.exception.PythonScriptException;
import io.maksymuimanov.python.script.BasicPythonScriptBuilder;
import io.maksymuimanov.python.script.PythonScript;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.expression.BeanFactoryResolver;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

/**
 * {@link PythonResolver} implementation that processes Spring Expression Language (SpEL)
 * expressions embedded within Python scripts.
 *
 * <p>This resolver allows embedding SpEL expressions directly into Python code. During
 * script resolution, it evaluates these expressions at runtime within a configured
 * SpEL evaluation context and replaces them with their JSON-serialized representations
 * for use in Python.</p>
 *
 * <p>The resolver automatically inserts the necessary Python JSON import statement if missing.
 * It supports passing external variables to the SpEL context via the {@code argumentSpec} map.
 * Errors during JSON serialization are wrapped and rethrown as {@link PythonScriptException}.</p>
 *
 * @see PythonResolver
 * @see PythonResolverHolder
 * @author w4t3rcs
 * @since 1.0.0
 */
@RequiredArgsConstructor
public class SpelythonResolver implements PythonResolver {
    public static final int PRIORITY = -100;
    private final String regex;
    private final String localVariableIndex;
    private final int positionFromStart;
    private final int positionFromEnd;
    private final ApplicationContext applicationContext;
    private final ObjectMapper objectMapper;

    /**
     * Resolves SpEL expressions within the given Python script by evaluating each
     * expression and replacing it with its JSON representation.
     *
     * <p>The method uses configured regex patterns and positional parameters to
     * identify expressions, then evaluates them against the SpEL context enriched
     * with the provided {@code argumentSpec} as variables.</p>
     *
     * @param pythonScript non-null Python script content possibly containing SpEL expressions
     * @param argumentSpec    nullable map of variables for SpEL evaluation context, keys are variable names, values are their corresponding objects. If null or empty, no variables are set.
     * @return non-null-resolved script with SpEL expressions replaced by JSON-wrapped results
     * @throws PythonScriptException if there are any exceptions during JSON serialization {@link PythonScriptException} is thrown
     */
    @Override
    public PythonScript resolve(PythonScript pythonScript, PythonArgumentSpec argumentSpec) {
        ExpressionParser parser = new SpelExpressionParser();
        StandardEvaluationContext context = new StandardEvaluationContext();
        if (!argumentSpec.isEmpty()) {
            argumentSpec.forEach((key, value) ->
                    parser.parseExpression(this.localVariableIndex + key)
                            .setValue(context, value));
        }
        context.setBeanResolver(new BeanFactoryResolver(applicationContext));
        return BasicPythonScriptBuilder.of(pythonScript)
                .appendImport(IMPORT_JSON)
                .replaceAllCode(this.regex,
                        this.positionFromStart,
                        this.positionFromEnd,
                        (group, result) -> {
                    try {
                        Expression expression = parser.parseExpression(group);
                        Object expressionValue = expression.getValue(context, Object.class);
                        String jsonResult = objectMapper.writeValueAsString(expressionValue).replace("'", "\\'");
                        if (jsonResult.startsWith("\"\\\"") && jsonResult.endsWith("\\\"\"")) {
                            int beginIndex = 3;
                            int endIndex = jsonResult.length() - beginIndex;
                            jsonResult = jsonResult.substring(beginIndex, endIndex);
                            result.append("'")
                                    .append(jsonResult)
                                    .append("'");
                        } else {
                            result.append("json.loads('")
                                    .append(jsonResult)
                                    .append("')");
                        }
                    } catch (JsonProcessingException e) {
                        throw new PythonScriptException(e);
                    }
                })
                .getScript();
    }

    @Override
    public int getPriority() {
        return PRIORITY;
    }
}