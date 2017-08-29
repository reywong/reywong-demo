package cn.com.yto56.coresystem.common.stl.framework.quartz;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.core.type.filter.AssignableTypeFilter;
import org.springframework.core.type.filter.TypeFilter;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Set;

import static org.springframework.util.Assert.notNull;

/**
 * 扫描启动JobBean
 * @author lihui
 * 2016-12-16
 */
public class ScheduleScannerConfigurer implements
		BeanDefinitionRegistryPostProcessor, InitializingBean,
		ApplicationContextAware {
	
	//扫描的包路径,可以使用<property name="basePackage" value=""/>覆盖
    private String basePackage = "cn.com.yto56.coresystem.*.*.job,"
    						   + "cn.com.yto56.coresystem.*.*.*.job,"
    						   +"cn.com.yto56.coresystem.*.*.*.*.job";
    private ApplicationContext applicationContext;

    // 配置了该注解
    private Class<? extends Annotation> annotationClass = JobBean.class;
    
    // 实现了该接口
    private Class<?> markerInterface;
    
    private BeanNameGenerator nameGenerator;
    
    private boolean includeAnnotationConfig = true;
    
    private IScheduleService scheduleService;
    
	public void postProcessBeanFactory(ConfigurableListableBeanFactory arg0)
			throws BeansException {
		// TODO Auto-generated method stub
		
	}

	public IScheduleService getScheduleService() {
		return scheduleService;
	}

	public void setScheduleService(IScheduleService scheduleService) {
		this.scheduleService = scheduleService;
	}

	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext = applicationContext;
	}

	public void afterPropertiesSet() throws Exception {
		notNull(this.basePackage, "Property 'basePackage' is required " + this.getClass().getSimpleName());
	}

	public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry)
			throws BeansException {
		SchedulerPathScanner scan = new SchedulerPathScanner(registry);
        scan.setResourceLoader(this.applicationContext);
        scan.setBeanNameGenerator(this.nameGenerator);
        // 引入注解配置
        scan.setIncludeAnnotationConfig(this.includeAnnotationConfig);
        scan.registerFilters();
 
        String[] basePackages = org.springframework.util.StringUtils.tokenizeToStringArray(this.basePackage,
        		ConfigurableApplicationContext.CONFIG_LOCATION_DELIMITERS);
        scan.scan(basePackages);
	}

	private class SchedulerPathScanner extends ClassPathBeanDefinitionScanner{

		public SchedulerPathScanner(BeanDefinitionRegistry registry) {
			super(registry ,false);
		}
		
		@Override
        public Set<BeanDefinitionHolder> doScan(String... basePackages) {
            Set<BeanDefinitionHolder> beanDefinitions = super.doScan(basePackages);
            if (beanDefinitions.isEmpty()) {
                logger.warn("No JobBean was found in '"
                        + Arrays.toString(basePackages)
                        + "' package. Please check your configuration.");
            } else {
                for (BeanDefinitionHolder holder : beanDefinitions) {
                    GenericBeanDefinition definition = (GenericBeanDefinition) holder.getBeanDefinition();
                    
                    if (logger.isDebugEnabled()) {
                        logger.debug("Creating JobBean with name '"
                                + holder.getBeanName() + "' and '"
                                + definition.getBeanClassName());
                    }
                    String beanClassName = definition.getBeanClassName();
					try {
						scheduleService.createJob(beanClassName , true);
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
                }
            }
            return beanDefinitions;
        }
 
        @Override
        protected boolean isCandidateComponent(
                AnnotatedBeanDefinition beanDefinition) {
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
                logger.warn("Skipping JobBeanScann. with name '" + beanName
                        + "' and '" + beanDefinition.getBeanClassName()
                        + "' JobBean"
                        + ". Bean already defined with the same name!");
                return false;
            }
        }
 
        public void registerFilters() {
            boolean acceptAllInterfaces = true;
 
            // if specified, use the given annotation and / or marker interface
            if (ScheduleScannerConfigurer.this.annotationClass != null) {
              addIncludeFilter(new AnnotationTypeFilter(ScheduleScannerConfigurer.this.annotationClass));
              acceptAllInterfaces = false;
            }
 
            // override AssignableTypeFilter to ignore matches on the actual marker interface
            if (ScheduleScannerConfigurer.this.markerInterface != null) {
              addIncludeFilter(new AssignableTypeFilter(ScheduleScannerConfigurer.this.markerInterface) {
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
