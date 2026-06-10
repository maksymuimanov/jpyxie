package io.jpyxie.python.autoconfigure;

import io.jpyxie.python.bind.GraalPythonDeserializer;
import io.jpyxie.python.bind.PythonDeserializer;
import io.jpyxie.python.environment.PythonEnvironment;
import io.jpyxie.python.executor.GraalPythonExecutor;
import io.jpyxie.python.executor.PythonExecutor;
import io.jpyxie.python.interpreter.*;
import lombok.SneakyThrows;
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Value;
import org.graalvm.polyglot.io.IOAccess;
import org.graalvm.python.embedding.VirtualFileSystem;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBooleanProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import java.util.Optional;

@AutoConfiguration
@EnableConfigurationProperties(GraalPyProperties.class)
@ConditionalOnBooleanProperty(name = "spring.python.executor.graalpy.enabled", matchIfMissing = true)
public class GraalPythonExecutorAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean(PythonDeserializer.class)
    public PythonDeserializer<Value> graalPythonDeserializer() {
        return new GraalPythonDeserializer();
    }

    @Bean
    @ConditionalOnMissingBean(IOAccess.class)
    public IOAccess ioAccess(GraalPyProperties properties) {
        GraalPyProperties.IO io = properties.getIo();
        return IOAccess.newBuilder(AbstractGraalInterpreterFactory.DEFAULT_IO_ACCESS)
                .allowHostFileAccess(io.isAllowHostFileAccess())
                .allowHostSocketAccess(io.isAllowHostSocketAccess())
                .build();
    }

    @Bean
    @ConditionalOnMissingBean({PythonInterpreterFactory.class, VirtualFileSystem.class})
    public PythonInterpreterFactory<Context> graalInterpreterFactory(PythonEnvironment pythonEnvironment,
                                                                     IOAccess ioAccess,
                                                                     GraalPyProperties properties) {
        return new GraalInterpreterFactory(
                pythonEnvironment,
                ioAccess,
                properties.getHostAccess().getValue(),
                properties.isAllowValueSharing(),
                properties.isAllowCreateProcess(),
                properties.isAllowExperimentalOptions(),
                properties.getAdditionalOptions()
        );
    }

    @SneakyThrows
    @Bean
    @ConditionalOnBooleanProperty(name = "spring.python.executor.graalpy.resources.enabled")
    @ConditionalOnMissingBean(VirtualFileSystem.class)
    public VirtualFileSystem virtualFileSystem(GraalPyProperties properties) throws ClassNotFoundException {
        GraalPyProperties.Resources resources = properties.getResources();
        VirtualFileSystem.Builder builder = VirtualFileSystem.newBuilder()
                .caseInsensitive(resources.isCaseSensitive())
                .allowHostIO(resources.getAllowHostIO())
                .unixMountPoint(resources.getUnixMountPoint())
                .windowsMountPoint(resources.getWindowsMountPoint());
        Optional.ofNullable(resources.getResourceDirectory())
                .ifPresent(builder::resourceDirectory);
        Optional.ofNullable(resources.getResourceLoadingClass())
                .map(Class::forName)
                .ifPresent(builder::resourceLoadingClass);
        return builder.build();
    }

    @Bean
    @ConditionalOnBean(VirtualFileSystem.class)
    @ConditionalOnMissingBean(PythonInterpreterFactory.class)
    public PythonInterpreterFactory<Context> graalPyResourcesInterpreterFactory(VirtualFileSystem virtualFileSystem,
                                                                                IOAccess ioAccess,
                                                                                GraalPyProperties properties) {
        return new GraalPyResourcesInterpreterFactory(
                virtualFileSystem,
                ioAccess,
                properties.getHostAccess().getValue(),
                properties.isAllowValueSharing(),
                properties.isAllowCreateProcess(),
                properties.isAllowExperimentalOptions(),
                properties.getAdditionalOptions()
        );
    }

    @Bean
    @ConditionalOnMissingBean(PythonExecutor.class)
    public PythonExecutor graalPythonExecutor(PythonInterpreterProvider<Context> graalInterpreterProvider,
                                              PythonDeserializer<Value> graalPythonDeserializer,
                                              GraalPyProperties properties) {
        return new GraalPythonExecutor(graalPythonDeserializer, graalInterpreterProvider, properties.isCached());
    }
}