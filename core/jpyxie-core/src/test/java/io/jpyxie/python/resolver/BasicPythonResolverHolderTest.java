package io.jpyxie.python.resolver;

import io.jpyxie.python.exception.PythonScriptException;
import io.jpyxie.python.script.PythonScript;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BasicPythonResolverHolderTest {
    private BasicPythonResolverHolder resolverHolder;
    @Mock
    private PythonResolver resolver;
    @Mock
    private PythonScript pythonScript;
    private PythonArgumentSpec spec;
    private PythonArgumentSpec emptySpec;

    @BeforeEach
    void setUp() {
        resolverHolder = new BasicPythonResolverHolder(resolver);
        spec = PythonArgumentSpec.of("x", 1);
        emptySpec = PythonArgumentSpec.empty();
    }

    @Test
    void resolveAll_shouldResolveEachResolver() {
        when(resolver.resolve(pythonScript, emptySpec))
                .thenReturn(pythonScript);

        resolverHolder.resolveAll(pythonScript, emptySpec);

        verify(resolver)
                .resolve(pythonScript, emptySpec);
    }

    @Test
    void resolveAll_shouldFail_whenExceptionThrown() {
        doThrow(RuntimeException.class)
                .when(resolver)
                .resolve(pythonScript, emptySpec);

        assertThatThrownBy(() -> resolverHolder.resolveAll(pythonScript, emptySpec))
                .isInstanceOf(PythonScriptException.class);
        verify(resolver)
                .resolve(pythonScript, emptySpec);
    }

    @Test
    void resolveAll_withArguments_shouldResolveEachResolver() {
        when(resolver.resolve(pythonScript, spec))
                .thenReturn(pythonScript);

        resolverHolder.resolveAll(pythonScript, spec);

        verify(resolver)
                .resolve(pythonScript, spec);
    }

    @Test
    void resolveAll_withArguments_shouldFail_whenExceptionThrown() {
        doThrow(RuntimeException.class)
                .when(resolver)
                .resolve(pythonScript, spec);

        assertThatThrownBy(() -> resolverHolder.resolveAll(pythonScript, spec))
                .isInstanceOf(PythonScriptException.class);
        verify(resolver)
                .resolve(pythonScript, spec);
    }

    @Test
    void getResolvers_shouldReturnResolverList() {
        List<PythonResolver> resolvers = resolverHolder.getResolvers();

        assertThat(resolvers)
                .hasSize(1)
                .containsExactly(resolver);
    }

    @Test
    void forEach_shouldIterateOverResolvers() {
        resolverHolder.forEach(r ->
                assertThat(r)
                        .isEqualTo(resolver)
        );
    }
}
