package io.jpyxie.python.script;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.ObjIntConsumer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.spy;

@ExtendWith(MockitoExtension.class)
class IterationPythonScriptBuilderTest {
    private IterationPythonScriptBuilder builder;
    private PythonScript script;

    @BeforeEach
    void setUp() {
        script = spy(PythonScript.asString("script", "import json\nimport os\nprint('hello')\nprint('world')"));
        builder = IterationPythonScriptBuilder.of(script);
    }

    @Test
    void of_shouldCreateBuilderWithScript() {
        IterationPythonScriptBuilder result = IterationPythonScriptBuilder.of(script);

        assertThat(result)
                .isNotNull();
        assertThat(result.getScript())
                .isSameAs(script);
    }

    @Test
    void iterateImports_withConsumer_shouldExecuteActionOnAllImportLines() {
        List<PythonImportLine> processedImports = new ArrayList<>();
        Consumer<PythonImportLine> action = processedImports::add;

        IterationPythonScriptBuilder result = builder.iterateImports(action);

        assertThat(result)
                .isSameAs(builder);
        assertThat(processedImports)
                .hasSize(2)
                .extracting(PythonImportLine::toPythonString)
                .containsExactly("import json", "import os");
    }

    @Test
    void iterateImports_withConsumerAndCondition_shouldExecuteActionWhenConditionIsTrue() {
        List<PythonImportLine> processedImports = new ArrayList<>();
        Consumer<PythonImportLine> action = processedImports::add;

        IterationPythonScriptBuilder result = builder.iterateImports(action, true);

        assertThat(result)
                .isSameAs(builder);
        assertThat(processedImports)
                .hasSize(2);
    }

    @Test
    void iterateImports_withConsumerAndCondition_shouldNotExecuteActionWhenConditionIsFalse() {
        List<PythonImportLine> processedImports = new ArrayList<>();
        Consumer<PythonImportLine> action = processedImports::add;

        IterationPythonScriptBuilder result = builder.iterateImports(action, false);

        assertThat(result)
                .isSameAs(builder);
        assertThat(processedImports)
                .isEmpty();
    }

    @Test
    void iterateImports_withObjIntConsumer_shouldExecuteActionWithIndex() {
        List<String> processedImports = new ArrayList<>();
        ObjIntConsumer<PythonImportLine> action = (importLine, index) -> 
            processedImports.add(index + ":" + importLine.toPythonString());

        IterationPythonScriptBuilder result = builder.iterateImports(action);

        assertThat(result)
                .isSameAs(builder);
        assertThat(processedImports)
                .hasSize(2)
                .containsExactly("0:import json", "1:import os");
    }

    @Test
    void iterateImports_withObjIntConsumerAndCondition_shouldExecuteActionWhenConditionIsTrue() {
        List<String> processedImports = new ArrayList<>();
        ObjIntConsumer<PythonImportLine> action = (importLine, index) -> 
            processedImports.add(index + ":" + importLine.toPythonString());

        IterationPythonScriptBuilder result = builder.iterateImports(action, true);

        assertThat(result)
                .isSameAs(builder);
        assertThat(processedImports)
                .hasSize(2);
    }

    @Test
    void iterateImports_withObjIntConsumerAndCondition_shouldNotExecuteActionWhenConditionIsFalse() {
        List<String> processedImports = new ArrayList<>();
        ObjIntConsumer<PythonImportLine> action = (importLine, index) -> 
            processedImports.add(index + ":" + importLine.toPythonString());

        IterationPythonScriptBuilder result = builder.iterateImports(action, false);

        assertThat(result)
                .isSameAs(builder);
        assertThat(processedImports)
                .isEmpty();
    }

    @Test
    void iterateCode_withConsumer_shouldExecuteActionOnAllCodeLines() {
        List<PythonCodeLine> processedCode = new ArrayList<>();
        Consumer<PythonCodeLine> action = processedCode::add;

        IterationPythonScriptBuilder result = builder.iterateCode(action);

        assertThat(result)
                .isSameAs(builder);
        assertThat(processedCode)
                .hasSize(2)
                .extracting(PythonCodeLine::toPythonString)
                .containsExactly("print('hello')", "print('world')");
    }

    @Test
    void iterateCode_withConsumerAndCondition_shouldExecuteActionWhenConditionIsTrue() {
        List<PythonCodeLine> processedCode = new ArrayList<>();
        Consumer<PythonCodeLine> action = processedCode::add;

        IterationPythonScriptBuilder result = builder.iterateCode(action, true);

        assertThat(result)
                .isSameAs(builder);
        assertThat(processedCode)
                .hasSize(2);
    }

    @Test
    void iterateCode_withConsumerAndCondition_shouldNotExecuteActionWhenConditionIsFalse() {
        List<PythonCodeLine> processedCode = new ArrayList<>();
        Consumer<PythonCodeLine> action = processedCode::add;

        IterationPythonScriptBuilder result = builder.iterateCode(action, false);

        assertThat(result)
                .isSameAs(builder);
        assertThat(processedCode)
                .isEmpty();
    }

    @Test
    void iterateCode_withObjIntConsumer_shouldExecuteActionWithIndex() {
        List<String> processedCode = new ArrayList<>();
        ObjIntConsumer<PythonCodeLine> action = (codeLine, index) -> 
            processedCode.add(index + ":" + codeLine.toPythonString());

        IterationPythonScriptBuilder result = builder.iterateCode(action);

        assertThat(result)
                .isSameAs(builder);
        assertThat(processedCode)
                .hasSize(2)
                .containsExactly("0:print('hello')", "1:print('world')");
    }

    @Test
    void iterateCode_withObjIntConsumerAndCondition_shouldExecuteActionWhenConditionIsTrue() {
        List<String> processedCode = new ArrayList<>();
        ObjIntConsumer<PythonCodeLine> action = (codeLine, index) -> 
            processedCode.add(index + ":" + codeLine.toPythonString());

        IterationPythonScriptBuilder result = builder.iterateCode(action, true);

        assertThat(result)
                .isSameAs(builder);
        assertThat(processedCode)
                .hasSize(2);
    }

    @Test
    void iterateCode_withObjIntConsumerAndCondition_shouldNotExecuteActionWhenConditionIsFalse() {
        List<String> processedCode = new ArrayList<>();
        ObjIntConsumer<PythonCodeLine> action = (codeLine, index) -> 
            processedCode.add(index + ":" + codeLine.toPythonString());

        IterationPythonScriptBuilder result = builder.iterateCode(action, false);

        assertThat(result)
                .isSameAs(builder);
        assertThat(processedCode)
                .isEmpty();
    }

    @Test
    void iterate_withIterableAndConsumer_shouldExecuteActionOnAllElements() {
        List<String> items = List.of("item1", "item2", "item3");
        List<String> processedItems = new ArrayList<>();
        Consumer<String> action = processedItems::add;

        IterationPythonScriptBuilder result = builder.iterate(items, action);

        assertThat(result)
                .isSameAs(builder);
        assertThat(processedItems)
                .isEqualTo(items);
    }

    @Test
    void iterate_withIterableAndConsumerAndCondition_shouldExecuteActionWhenConditionIsTrue() {
        List<String> items = List.of("item1", "item2");
        List<String> processedItems = new ArrayList<>();
        Consumer<String> action = processedItems::add;

        IterationPythonScriptBuilder result = builder.iterate(items, action, true);

        assertThat(result)
                .isSameAs(builder);
        assertThat(processedItems)
                .isEqualTo(items);
    }

    @Test
    void iterate_withIterableAndConsumerAndCondition_shouldNotExecuteActionWhenConditionIsFalse() {
        List<String> items = List.of("item1", "item2");
        List<String> processedItems = new ArrayList<>();
        Consumer<String> action = processedItems::add;

        IterationPythonScriptBuilder result = builder.iterate(items, action, false);

        assertThat(result)
                .isSameAs(builder);
        assertThat(processedItems)
                .isEmpty();
    }

    @Test
    void iterate_withListAndObjIntConsumer_shouldExecuteActionWithIndex() {
        List<String> items = List.of("item1", "item2", "item3");
        List<String> processedItems = new ArrayList<>();
        ObjIntConsumer<String> action = (item, index) -> 
            processedItems.add(index + ":" + item);

        IterationPythonScriptBuilder result = builder.iterate(items, action);

        assertThat(result)
                .isSameAs(builder);
        assertThat(processedItems)
                .hasSize(3)
                .containsExactly("0:item1", "1:item2", "2:item3");
    }

    @Test
    void iterate_withListAndObjIntConsumerAndCondition_shouldExecuteActionWhenConditionIsTrue() {
        List<String> items = List.of("item1", "item2");
        List<String> processedItems = new ArrayList<>();
        ObjIntConsumer<String> action = (item, index) -> 
            processedItems.add(index + ":" + item);

        IterationPythonScriptBuilder result = builder.iterate(items, action, true);

        assertThat(result)
                .isSameAs(builder);
        assertThat(processedItems)
                .hasSize(2)
                .containsExactly("0:item1", "1:item2");
    }

    @Test
    void iterate_withListAndObjIntConsumerAndCondition_shouldNotExecuteActionWhenConditionIsFalse() {
        List<String> items = List.of("item1", "item2");
        List<String> processedItems = new ArrayList<>();
        ObjIntConsumer<String> action = (item, index) -> 
            processedItems.add(index + ":" + item);

        IterationPythonScriptBuilder result = builder.iterate(items, action, false);

        assertThat(result)
                .isSameAs(builder);
        assertThat(processedItems)
                .isEmpty();
    }

    @Test
    void iterate_withEmptyList_shouldNotExecuteAction() {
        List<String> emptyItems = new ArrayList<>();
        List<String> processedItems = new ArrayList<>();
        Consumer<String> action = processedItems::add;

        IterationPythonScriptBuilder result = builder.iterate(emptyItems, action);

        assertThat(result)
                .isSameAs(builder);
        assertThat(processedItems)
                .isEmpty();
    }

    @Test
    void iterate_withEmptyListAndObjIntConsumer_shouldNotExecuteAction() {
        List<String> emptyItems = new ArrayList<>();
        List<String> processedItems = new ArrayList<>();
        ObjIntConsumer<String> action = (item, index) -> 
            processedItems.add(index + ":" + item);

        IterationPythonScriptBuilder result = builder.iterate(emptyItems, action);

        assertThat(result)
                .isSameAs(builder);
        assertThat(processedItems)
                .isEmpty();
    }

    @Test
    void onCondition_shouldExecuteActionWhenConditionIsTrue() {
        List<String> executedActions = new ArrayList<>();
        Runnable action = () -> executedActions.add("executed");

        IterationPythonScriptBuilder result = builder.onCondition(action, true);

        assertThat(result)
                .isSameAs(builder);
        assertThat(executedActions)
                .hasSize(1)
                .containsExactly("executed");
    }

    @Test
    void onCondition_shouldNotExecuteActionWhenConditionIsFalse() {
        List<String> executedActions = new ArrayList<>();
        Runnable action = () -> executedActions.add("executed");

        IterationPythonScriptBuilder result = builder.onCondition(action, false);

        assertThat(result)
                .isSameAs(builder);
        assertThat(executedActions)
                .isEmpty();
    }

    @Test
    void iterateImports_shouldWorkWithEmptyImportList() {
        PythonScript emptyScript = PythonScript.empty("empty");
        IterationPythonScriptBuilder emptyBuilder = IterationPythonScriptBuilder.of(emptyScript);
        List<PythonImportLine> processedImports = new ArrayList<>();
        Consumer<PythonImportLine> action = processedImports::add;

        IterationPythonScriptBuilder result = emptyBuilder.iterateImports(action);

        assertThat(result)
                .isSameAs(emptyBuilder);
        assertThat(processedImports)
                .isEmpty();
    }

    @Test
    void iterateCode_shouldWorkWithEmptyCodeList() {
        PythonScript emptyScript = PythonScript.empty("empty");
        IterationPythonScriptBuilder emptyBuilder = IterationPythonScriptBuilder.of(emptyScript);
        List<PythonCodeLine> processedCode = new ArrayList<>();
        Consumer<PythonCodeLine> action = processedCode::add;

        IterationPythonScriptBuilder result = emptyBuilder.iterateCode(action);

        assertThat(result)
                .isSameAs(emptyBuilder);
        assertThat(processedCode)
                .isEmpty();
    }

    @Test
    void iterateImports_shouldCreateCopyOfImportLinesToAvoidConcurrentModification() {
        List<PythonImportLine> processedImports = new ArrayList<>();
        Consumer<PythonImportLine> action = importLine -> {
            processedImports.add(importLine);
            // Modify the original list during iteration
            script.importLines().add(new PythonImportLine("import sys"));
        };

        IterationPythonScriptBuilder result = builder.iterateImports(action);

        assertThat(result)
                .isSameAs(builder);
        // Should only process the original 2 imports, not the one added during iteration
        assertThat(processedImports)
                .hasSize(2);
    }

    @Test
    void iterateCode_shouldCreateCopyOfCodeLinesToAvoidConcurrentModification() {
        List<PythonCodeLine> processedCode = new ArrayList<>();
        Consumer<PythonCodeLine> action = codeLine -> {
            processedCode.add(codeLine);
            // Modify the original list during iteration
            script.codeLines().add(new PythonCodeLine("print('new')"));
        };

        IterationPythonScriptBuilder result = builder.iterateCode(action);

        assertThat(result)
                .isSameAs(builder);
        // Should only process the original 2 code lines, not the one added during iteration
        assertThat(processedCode)
                .hasSize(2);
    }

    @Test
    void iterateImports_withObjIntConsumer_shouldProvideCorrectIndexes() {
        List<Integer> indexes = new ArrayList<>();
        ObjIntConsumer<PythonImportLine> action = (importLine, index) -> indexes.add(index);

        builder.iterateImports(action);

        assertThat(indexes)
                .containsExactly(0, 1);
    }

    @Test
    void iterateCode_withObjIntConsumer_shouldProvideCorrectIndexes() {
        List<Integer> indexes = new ArrayList<>();
        ObjIntConsumer<PythonCodeLine> action = (codeLine, index) -> indexes.add(index);

        builder.iterateCode(action);

        assertThat(indexes)
                .containsExactly(0, 1);
    }
}
