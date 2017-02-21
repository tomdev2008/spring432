package com.mvw.scanning;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.core.type.filter.TypeFilter;
import org.springframework.util.ClassUtils;

import com.mvw.scanning.annotation.ServiceLogic;


/**
 * 自动化框架
 * 
 * 1.自动扫描
 * 2.反射
 * 3.动态代理
 * 4.动态注入
 */
/**
 * spring 扫描工具
 * 
 * @author gaotingping
 *
 * 2016年8月1日 下午5:17:24
 */
public class ScannerUtil{

	private static final String RESOURCE_PATTERN = "/**/*.class";

	private ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();

	private List<String> packages = new ArrayList<String>();

	//new AnnotationTypeFilter(Component.class)
	private TypeFilter typeFilter = new AnnotationTypeFilter(ServiceLogic.class, false);

	private Set<Class<?>> classSet = new HashSet<Class<?>>();

	public Set<Class<?>> getEntry() throws IOException,ClassNotFoundException {
		this.classSet.clear();//每次获取都重新搞
		if (!this.packages.isEmpty()) {
			for (String pkg : this.packages) {
				String pattern = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX + ClassUtils.convertClassNameToResourcePath(pkg) + RESOURCE_PATTERN;
				Resource[] resources = this.resourcePatternResolver.getResources(pattern);
				MetadataReaderFactory readerFactory = new CachingMetadataReaderFactory(this.resourcePatternResolver);
				for (Resource resource : resources) {
					if (resource.isReadable()) {
						MetadataReader reader = readerFactory.getMetadataReader(resource);
						String className = reader.getClassMetadata().getClassName();
						if (matchesEntityTypeFilter(reader, readerFactory)) {
							this.classSet.add(Class.forName(className));
						}
					}
				}
			}
		}
		return this.classSet;
	}

	//过滤
	private boolean matchesEntityTypeFilter(MetadataReader reader,
			MetadataReaderFactory readerFactory) throws IOException {
		return typeFilter.match(reader, readerFactory);
	}
	
	//设置要扫描的包
	public void setPackages(List<String> packages) {
		this.packages = packages;
	}
}