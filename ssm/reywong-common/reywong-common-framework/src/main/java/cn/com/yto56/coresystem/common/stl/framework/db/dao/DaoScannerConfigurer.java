package cn.com.yto56.coresystem.common.stl.framework.db.dao;

import cn.com.yto56.coresystem.common.stl.framework.db.dao.annotation.JdbcDao;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.context.annotation.ScannedGenericBeanDefinition;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.core.type.filter.AssignableTypeFilter;
import org.springframework.core.type.filter.TypeFilter;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import static org.springframework.util.Assert.notNull;

/**
 * dao注解扫描类
 *
 * @author lihui
 *         2016-01-23
 */
public class DaoScannerConfigurer implements BeanDefinitionRegistryPostProcessor, InitializingBean, ApplicationContextAware, BeanNameAware {

    private String beanName;
    //扫描的包路径,可以使用<property name="basePackage" value=""/>覆盖
    private String basePackage = "cn.com.yto56.coresystem.*.*.dao,cn.com.yto56.coresystem.*.*.*.dao,cn.com.yto56.coresystem.*.*.*.*.dao";
    private ApplicationContext applicationContext;
    //默认dataSource属性
    private final String DEFAULT_DATASOURCE = "dataSource";
    // 配置了该注解
    private Class<? extends Annotation> annotationClass = JdbcDao.class;

    // 实现了该接口
    private Class<?> markerInterface;

    private BeanNameGenerator nameGenerator;

    private boolean includeAnnotationConfig = true;

    private Properties properties = new Properties();

    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

    }

    public void setBeanName(String name) {
        this.beanName = name;
    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public void afterPropertiesSet() throws Exception {
        notNull(this.basePackage, "Property 'basePackage' is required " + beanName);
        try {
            properties = applicationContext.getBean("configProperties", Properties.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        DaoClassPathScanner scan = new DaoClassPathScanner(registry);
        scan.setResourceLoader(this.applicationContext);
        scan.setBeanNameGenerator(this.nameGenerator);
        // 引入注解配置
        scan.setIncludeAnnotationConfig(this.includeAnnotationConfig);
        scan.registerFilters();
        String[] basePackages = StringUtils.tokenizeToStringArray(this.basePackage, ConfigurableApplicationContext.CONFIG_LOCATION_DELIMITERS);
        scan.scan(basePackages);
    }

    public String getBasePackage() {
        return basePackage;
    }

    public void setBasePackage(String basePackage) {
        this.basePackage = basePackage;
    }

    public Class<? extends Annotation> getAnnotationClass() {
        return annotationClass;
    }

    public void setAnnotationClass(Class<? extends Annotation> annotationClass) {
        this.annotationClass = annotationClass;
    }

    public String getBeanName() {
        return beanName;
    }

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }


    private class DaoClassPathScanner extends ClassPathBeanDefinitionScanner {

        public DaoClassPathScanner(BeanDefinitionRegistry registry) {
            super(registry, false);
        }

        @Override
        public Set<BeanDefinitionHolder> doScan(String... basePackages) {
            Set<BeanDefinitionHolder> beanDefinitions = super.doScan(basePackages);
            if (beanDefinitions.isEmpty()) {
                logger.warn("No dao was found in '"
                        + Arrays.toString(basePackages)
                        + "' package. Please check your configuration.");
            } else {
                for (BeanDefinitionHolder holder : beanDefinitions) {
                    GenericBeanDefinition definition = (GenericBeanDefinition) holder.getBeanDefinition();

                    if (logger.isDebugEnabled()) {
                        logger.debug("Creating dao with name '"
                                + holder.getBeanName() + "' and '"
                                + definition.getBeanClassName());
                    }

                    AnnotationMetadata metadata = ((ScannedGenericBeanDefinition) definition).getMetadata();
                    Map<String, Object> annotationAttributes = metadata.getAnnotationAttributes(annotationClass.getName());
                    String id = (String) annotationAttributes.get("dataSource");
                    if (id != null && id.length() > 0) {
                        if (id.startsWith("${") && id.endsWith("}")) {
                            id = id.replace("${", "").replace("}", "");
                            String propertyValue = properties.getProperty(id);
                            if (propertyValue != null && propertyValue.length() > 0) {
                                id = propertyValue;
                            }
                        }
                    }

                    definition.getPropertyValues().add(DEFAULT_DATASOURCE, new RuntimeBeanReference(id));
                }
            }
            return beanDefinitions;
        }

        @Override
        protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
            return (beanDefinition.getMetadata().isConcrete() && beanDefinition.getMetadata().isIndependent());
        }

        /**
         * {@inheritDoc}
         */
        @Override
        protected boolean checkCandidate(String beanName,
                                         BeanDefinition beanDefinition) throws IllegalStateException {
            if (super.checkCandidate(beanName, beanDefinition)) {
                return true;
            } else {
                logger.warn("Skipping DaoScann. with name '" + beanName
                        + "' and '" + beanDefinition.getBeanClassName()
                        + "' dao"
                        + ". Bean already defined with the same name!");
                return false;
            }
        }

        public void registerFilters() {
            boolean acceptAllInterfaces = true;

            // if specified, use the given annotation and / or marker interface
            if (DaoScannerConfigurer.this.annotationClass != null) {
                addIncludeFilter(new AnnotationTypeFilter(DaoScannerConfigurer.this.annotationClass));
                acceptAllInterfaces = false;
            }

            // override AssignableTypeFilter to ignore matches on the actual marker interface
            if (DaoScannerConfigurer.this.markerInterface != null) {
                addIncludeFilter(new AssignableTypeFilter(DaoScannerConfigurer.this.markerInterface) {
                    @Override
                    protected boolean matchClassName(String className) {
                        return false;
                    }
                });
                acceptAllInterfaces = false;
            }

            if (acceptAllInterfaces) {
                // default include filter that accepts all classes
                addIncludeFilter(new TypeFilter() {
                    public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory) throws IOException {
                        return true;
                    }
                });
            }

            // exclude package-info.java
            addExcludeFilter(new TypeFilter() {
                public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory) throws IOException {
                    String className = metadataReader.getClassMetadata().getClassName();
                    return className.endsWith("package-info");
                }
            });
        }
    }

}
