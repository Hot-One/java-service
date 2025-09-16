package sud.uz.SudDemo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

import java.io.IOException;

@Configuration("webConfig")
public class WebConfig implements WebMvcConfigurer {
        @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations("file:./web/")
                .resourceChain(true)
                .addResolver(new PathResourceResolver() {
                    @Override
                    protected Resource getResource(String resourcePath, Resource location) throws IOException {
                        Resource requestedResource = location.createRelative(resourcePath);

                        if (requestedResource.exists() && requestedResource.isReadable()) {
                            return requestedResource;
                        }

                        if (resourcePath.startsWith("api/")) {
                            return null; 
                        }

                        return new FileSystemResource("./web/index.html");
                    }
                });
    }
}
